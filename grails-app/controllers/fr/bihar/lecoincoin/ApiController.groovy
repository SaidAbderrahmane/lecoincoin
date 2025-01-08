package fr.bihar.lecoincoin

import grails.converters.JSON
import grails.converters.XML
import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.multipart.MultipartHttpServletRequest

@Transactional
@Secured(['ROLE_ADMIN', 'ROLE_MODO'])
class ApiController {

    static responseFormats = ['json', 'xml']

    SaleAdService saleAdService
    IllustrationService illustrationService
    UserService userService
    MessageService messageService
    SpringSecurityService springSecurityService

    def getBody(request, body) {
        if (request.getHeader('Content-Type')) {
            if (request.getHeader('Content-Type').contains('json')) body = request.getJSON()
            else if (request.getHeader('Content-Type').contains('xml')) body = request.getXML()
        }
        return body
    }

    def user() {
        if (!params.id) {
            return response.status = 400
        }
        def userInstance = User.get(params.id)
        if (!userInstance) {
            return response.status = 404
        }

        switch (request.method) {
            case 'GET':
                serializeThis(userInstance, request.getHeader('Accept'))
                break

            case 'PUT':
                userInstance.properties = request.JSON
                if (!userInstance.validate()) {
                    response.status = 422
                    respond userInstance.errors
                }
                if (!userInstance.save()) {
                    render userInstance.errors, status: 400
                }
                serializeThis(userInstance, request.getHeader('Accept'))
                break

            case 'PATCH':
                userInstance.properties = request.JSON
                if (!userInstance.save()) {
                    render userInstance.errors, status: 400
                }
                serializeThis(userInstance, request.getHeader('Accept'))
                break

            case 'DELETE':
                def id = userInstance.id
                User.withTransaction { status ->
                    UserRole.where { user == User.get(id) }.deleteAll()
                    Message.where { author == User.get(id) }.deleteAll()
                    Message.where { dest == User.get(id) }.deleteAll()
                    userService.delete(userInstance.id)
                }

                response.setStatus(204)
                render(text: 'User deleted')
                break

            default:
                return response.status = 405
                break
        }
        return response.status = 406
    }

    def users() {
        switch (request.method) {
            case 'GET':
                serializeThis(User.list(), request.getHeader('Accept'))
                break

            case 'POST':
                def userInstance = new User(request.JSON)
                if (!userInstance.validate()) {
                    response.status = 422
                    respond userInstance.errors
                }
                if (!userInstance.save()) {
                    render userInstance.errors, status: 400
                }
                response.setStatus(201)
                serializeThis(userInstance, request.getHeader('Accept'))
                break

            default:
                return response.status = 405
                break
        }
        return response.status = 406
    }

    def saleAd() {
        if (!params.id) {
            response.status = 400
            respond(['message': 'Missing id'])
            return
        }

        SaleAd saleAd = SaleAd.get(params.id as int)
        if (!saleAd) {
            response.status = 404
            respond(['message': 'SaleAd not found'])
            return
        }

        switch (request.method) {
        //            done
            case 'GET':
                def responseObject = [
                        id           : saleAd.id,
                        dateCreated  : saleAd.dateCreated,
                        lastUpdated  : saleAd.lastUpdated,
                        price        : saleAd.price,
                        active       : saleAd.active,
                        illustrations: saleAd.illustrations*.collect {
                            illustrationService.getIllustrationUrl(it)
                        } ?: [illustrationService.getDefaultIllustrationUrl()],
                        author       : saleAd.author.username,
                        title        : saleAd.title,
                        address      : saleAd.address.toString(),
                        category     : saleAd.category.name,
                        description  : saleAd.description
                ]
                respond responseObject
                return response.status = 200
                //            done
            case ['PUT', 'PATCH'] as Set:
                saleAd.properties = request.JSON

                if (request instanceof MultipartHttpServletRequest) {
                    ArrayList<MultipartFile> uploadedFiles = request.getFiles('files') ?: []
                    uploadedFiles = illustrationService.validateMany(uploadedFiles) ?: []

                    if (!uploadedFiles.empty) {
                        def illustrations = illustrationService.createMany(uploadedFiles)
                        illustrations.each { illustration ->
                            saleAd.addToIllustrations(illustration)
                        }
                    }
                }

                if (!saleAd.validate()) {
                    respond saleAd.errors
                    return response.status = 400
                }

                if (!saleAdService.save(saleAd)) {
                    respond saleAd.errors
                    return response.status = 422
                }

                respond saleAd
                return response.status = 200

                // done
            case 'DELETE':
                try {
                    saleAdService.delete(saleAd.id)
                    response.status = 204
                    respond('message': 'SaleAd deleted')
                    return
                } catch (Exception e) {
                    response.status = 400
                    respond('message': e.message)
                    return
                }
                break
            default:
                return response.status = 405
        }
    }

    def saleAds() {
        switch (request.method) {
            case 'GET':
                def saleAds = SaleAd.list()
                respond SaleAd.list(params)
                break
            case 'POST':
                def newSaleAd = new SaleAd(request.JSON)
                newSaleAd.author = springSecurityService.currentUser as User

                if (request instanceof MultipartHttpServletRequest) {
                    ArrayList<MultipartFile> uploadedFiles = request.getFiles('files') ?: []
                    uploadedFiles = illustrationService.validateMany(uploadedFiles) ?: []

                    if (!uploadedFiles.empty) {
                        def illustrations = illustrationService.createMany(uploadedFiles)
                        illustrations.each { illustration ->
                            saleAd.addToIllustrations(illustration)
                        }
                    }
                }

                if (!newSaleAd.validate()) {
                    response.status = 422
                    respond newSaleAd.errors
                }

                if (!newSaleAd.save(flush: true)) {
                    response.status = 400
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
            response.status = 400
            return
        }

        Category category = Category.get(params.id as int)
        if (!category) {
            response.status = 404
            return
        }

        switch (request.method) {
            case 'GET':
                respond category
                return response.status = 200
            case ['PUT', 'PATCH'] as Set:
                category.properties = request.JSON
                if (!category.validate()) {
                    respond category.errors
                    response.status = 422
                    return
                }

                if (!category.save(flush: true)) {
                    respond category.errors
                    return response.status = 400
                }

                respond category
                return response.status = 200

            case 'DELETE':
                // if root category, forbid deletion
                if (category.id == 1) {
                    return response.status = 403
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
                    respond newCategory.errors
                    return response.status = 422
                }
                if (!newCategory.save(flush: true)) {
                    respond newCategory.errors
                    return response.status = 400
                }
                respond newCategory
                return response.status = 201

            default:
                return response.status = 405
        }
    }

    def message() {
        if (!params.id) {
            return response.status = 400
        }
        def messageInstance = Message.get(params.id)
        if (!messageInstance) {
            return response.status = 404
        }
        switch (request.method) {
            case 'GET':
                serializeThis(messageInstance, request.getHeader('Accept'))
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
                def messageBody = getBody(request, request.JSON)
                messageInstance.properties = messageBody
                if (!messageInstance.save(flush: true)) {
                    response.status = 400
                    render messageInstance.errors as JSON
                    return
                }
                response.status = 200
                serializeThis(messageInstance, request.getHeader('Accept'))
                break

            case 'DELETE':
                try {
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
                serializeThis(Message.list(), request.getHeader('Accept'))
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
                response.setStatus(201)
                serializeThis(messageInstance, request.getHeader('Accept'))
                break

            default:
                return response.status = 405
                break
        }
    }

    def serializeThis(Object object, String acceptHeader) {
        switch (acceptHeader) {
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
