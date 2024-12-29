package fr.bihar.lecoincoin

import grails.converters.JSON
import grails.converters.XML
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import org.springframework.web.multipart.MultipartHttpServletRequest

@Transactional
@Secured('ROLE_ADMIN')

class ApiController {

    static responseFormats = ['json', 'xml']

    SaleAdService saleAdService
    CategoryController categoryController
    IllustrationService illustrationService
    // Ressource User Singleton
    // GET, PUT, PATCH, DELETE /api/user/{id}

    UserService userService
    MessageService messageService


    def getBody(request, body) {
        if (request.getHeader('Content-Type')) {
            if (request.getHeader('Content-Type').contains("json")) body = request.getJSON()
            else if (request.getHeader('Content-Type').contains("xml")) body = request.getXML()
        }
        return body
    }

    def user() {
        // On vérifie qu'il y a bien un param "id" sans quoi erreur car nous sommes dans le traitement d'une ressource singleton
        if (!params.id) {
            return response.status = 400
        }

        // On vérifie que l'instance d'User désigné corresponde bien à un utilisateur existant
        def userInstance = User.get(params.id)
        if (!userInstance) {
            return response.status = 404
        }
        // A ce stade on sait que l'on a un utilisateur bien identifié
        switch (request.method) {
            case 'GET':
                serializeThis(userInstance, request.getHeader('Accept'))
                break

            case 'PUT':
                userInstance.properties = request.JSON
                if (!userInstance.save()) {
                    render userInstance.errors, status: 400
                }
                serializeThis(userInstance, request.getHeader("Accept"))
                break

            case 'PATCH':
              userInstance.properties = request.JSON
                if (!userInstance.save()) {
                    render userInstance.errors, status: 400
                }
                serializeThis(userInstance, request.getHeader("Accept"))
                break

            case 'DELETE':
                def id = userInstance.id
                User.withTransaction { status ->
                    UserRole.where { user == User.get(id) }.deleteAll()
                    Message.where { author == User.get(id) }.deleteAll()
                    Message.where { dest == User.get(id)}.deleteAll()
                    userService.delete(userInstance.id)
                }

                response.setStatus(204)
                render ( text: "User deleted")
                break

            default:
                 return response.status = 405
                break
        }
        return response.status = 406
    }
    // Ressource Users Collection
    // GET, POST /api/users
    def users() {
        switch (request.method) {

            case 'GET':
                serializeThis(User.list(), request.getHeader('Accept'))
                break

            case 'POST':
                def userInstance = new User(request.JSON)
                if (!userInstance.save()) {
                    render userInstance.errors, status: 400
                }
                response.setStatus(201)
                serializeThis(userInstance, request.getHeader("Accept"))
                break

            default:
                return response.status = 405
                break
        }
        return response.status = 406
    }

    def saleAd() {
        if (!params.id) {
            return response.status = 400
        }

        SaleAd saleAd = SaleAd.get(params.id as int)
        if (!saleAd) {
            return response.status = 404
        }

        switch (request.method) {
            //            done
            case 'GET':
                def responseObject = [
                        id: saleAd.id,
                        dateCreated: saleAd.dateCreated,
                        lastUpdated: saleAd.lastUpdated,
                        price: saleAd.price,
                        active: saleAd.active,
                        illustrations: saleAd.illustrations*.collect {
                            illustrationService.getIllustrationUrl(it)
                        } ?: [illustrationService.getDefaultIllustrationUrl()],
                        author: [id: saleAd.author.id],
                        title: saleAd.title,
                        address: saleAd.address.toString(),
                        category: saleAd.category.name,
                        description: saleAd.description
                ]
                respond responseObject
                return
            //            done
            case ['PUT', 'PATCH'] as Set:
                saleAd.properties = request.JSON
                if (!saleAd.validate()) {
                    response.status = 422
                    respond saleAd.errors
                    return
                }
                //                if request has files
                try {
                    if (request instanceof MultipartHttpServletRequest) {
                        def uploadedFiles = (request  as MultipartHttpServletRequest).getFiles('files')

                        if (!uploadedFiles.empty) {
                            println "Processing ${uploadedFiles.size()} uploaded files"
                            def illustrations = illustrationService.createMany(uploadedFiles)
                            illustrations.each { illustration ->
                                saleAd.addToIllustrations(illustration)
                            }
                        }
                    }
                } catch (ValidationException e) {
                    log.error("Error updating SaleAd: ${e.message}", e)
                    response.status = 422
                    respond saleAd.errors
                    return
                }


                if (!saleAd.save(flush: true)) {
                    return response.status = 422
                }
                response.status = 200
                respond saleAd
                return
            // done
            case 'DELETE':
                try {
                    saleAd.delete(flush: true)
                    return response.status = 204
                } catch (Exception e) {
                    return response.status = 500
                }
            default:
                return response.status = 405
        }
        return response.status = 406
    }

    def saleAds() {
        switch (request.method) {
            case 'GET':
                // Fetch all SaleAd objects
                def saleAds = SaleAd.list()
                respond SaleAd.list(params)
                break
            case 'POST':
                def newSaleAd = new SaleAd(request.JSON)
                if (!newSaleAd.validate()) {
                    response.status = 422
                    respond newSaleAd.errors
                }

                // if request has files
                try {
                    if (request instanceof MultipartHttpServletRequest) {
                        def uploadedFiles = (request  as MultipartHttpServletRequest).getFiles('files')

                        if (!uploadedFiles.empty) {
                            println "Processing ${uploadedFiles.size()} uploaded files"
                            def illustrations = illustrationService.createMany(uploadedFiles)
                            illustrations.each { illustration ->
                                saleAd.addToIllustrations(illustration)
                            }
                        }
                    }
                } catch (ValidationException e) {
                    log.error("Error updating SaleAd: ${e.message}", e)
                    response.status = 422
                    respond newSaleAd.errors
                    return
                }

                if (!newSaleAd.save(flush: true)) {
                    response.status = 500
                    respond newSaleAd.errors
                    return
                }

                response.status = 201
                respond newSaleAd
                break
            default:
                response.status = 405
        }
    }

    def category() {
        if (!params.id) {
            return response.status = 400
        }

        Category category = Category.get(params.id as int)
        if (!category) {
            return response.status = 404
        }

        switch (request.method) {
            case 'GET':
                respond category
                return
            case ['PUT', 'PATCH'] as Set:
                category.properties = request.JSON
                if (!category.validate()) {
                    response.status = 422
                    respond category.errors
                    return
                }
                if (!category.save(flush: true)) {
                    response.status = 500
                    respond category.errors
                    return
                }
                response.status = 200
                respond category
                return
            case 'DELETE':
                // if root category, forbid deletion
                if (category.id == 1) {
                    response.status = 403
                    return
                }
                // if parent, set its children's parent attribute to null
                category.children.each { it.parent = null }
                category.children*.save()

                // move all sale ads to root category
                Category root = Category.get(1)
                category.saleAds.each { it.category = root }
                category.saleAds*.save()

                category.delete(flush: true)

                return response.status = 204

            default:
                return response.status = 405
        }
    }
    def categories() {
        switch (request.method) {
            case 'GET':
                respond Category.list(params)
                return
            case 'POST':
                def newCategory = new Category(request.JSON)
                if (!newCategory.validate()) {
                    response.status = 422
                    respond newCategory.errors
                    return
                }
                if (!newCategory.save(flush: true)) {
                    response.status = 500
                    respond newCategory.errors
                    return
                }
                response.status = 201
                respond newCategory
                return
            default:
                return response.status = 405
        }
    }
    def message() {

        if (!params.id)
            return response.status = 400
        def messageInstance = Message.get(params.id)
        if (!messageInstance)
            return response.status = 404
        switch (request.method) {

            case 'GET':
                serializeThis(messageInstance, request.getHeader("Accept"))
                break

            case 'PUT':
                def messageBody = getBody(request, request.JSON)
                if (!messageInstance.update(messageBody.content, messageBody.title)) {
                    response.status = 400
                    render messageInstance.errors as JSON
                    return
                }                
                break

            case 'PATCH':
                break

            case 'DELETE':
            try{
                messageService.delete(messageInstance.id)
                response.setStatus(204)
                render(status: 204, text: 'Message deleted')
                }
                catch (e) {
                    response.status = 400
                    render text: e.message, model: [exception: e]
                }
                break

            default:
                return response.status = 405
                break
        }
    }

    def messages() {

        switch (request.method) {

            case 'GET':
                serializeThis(Message.list(), request.getHeader("Accept"))
                break

            case 'POST':
            
                def messageInstance = new Message(request.JSON)
                messageInstance.author = User.get(messageInstance.author.id)
                messageInstance.dest = User.get(messageInstance.dest.id)
                if (!messageInstance.save(flush: true)) {
                    response.status = 400
                    render messageInstance.errors as JSON
                    return
                }
                serializeThis(messageInstance, request.getHeader("Accept"))
                break

            default:
                return response.status = 405
                break
        }
    }

    def serializeThis(Object object, String acceptHeader)
    {
        switch (acceptHeader)
        {
            case 'application/xml':
            case 'text/xml':
            case 'xml':
                render object as XML
                break
            case 'application/json':
            case 'text/json':
            case 'json':
            default:
                render object as JSON
                break
        }
    }

}
