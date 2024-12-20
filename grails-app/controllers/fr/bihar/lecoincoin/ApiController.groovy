package fr.bihar.lecoincoin

import grails.converters.JSON
import grails.converters.XML
import grails.plugin.springsecurity.annotation.Secured

@Secured('ROLE_ADMIN')

class ApiController {

    UserService userService


    def getBody(request, body) {
        if (request.getHeader('Content-Type')) {
            if (request.getHeader('Content-Type').contains("json")) body = request.getJSON()
            else if (request.getHeader('Content-Type').contains("xml")) body = request.getXML()
        }
        return body
    }

    def user() {
       
        if (!params.id)
            return response.status = 400

        def userInstance = User.get(params.id)
        if (!userInstance)
            return response.status = 404
       

        switch (request.method) {
            case 'GET':
                serializeThis(userInstance, request.getHeader("Accept"))
                break

            case 'PUT':
                userInstance.properties = request.JSON
                if (!userInstance.save()) {
                    render(status: 400, text: [status: 400, message: userInstance.errors] as JSON)
                }
                serializeThis(userInstance, request.getHeader("Accept"))
                break

            case 'PATCH':
              userInstance.properties = request.JSON
                if (!userInstance.save()) {
                    render(status: 400, text: [status: 400, message: userInstance.errors] as JSON)
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


    def users() {
        switch (request.method) {

            case 'GET':
                serializeThis(User.list(), request.getHeader("Accept"))
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

        if (!params.id)
            return response.status = 400
        def saleAdInstance = SaleAd.get(params.id)
        if (!saleAdInstance)
            return response.status = 404
        switch (request.method) {
            case 'GET':
                serializeThis(saleAdInstance, request.getHeader("Accept"))
                break
            case 'PUT':
                break
            case 'PATCH':
                break
            case 'DELETE':
                break
            default:
                 return response.status = 405
                break
        }
    }
    def saleAds() {

        switch (request.method) {
            case 'GET':
                serializeThis(SaleAd.list(), request.getHeader("Accept"))
                break
            case 'POST':
                break
            default:
                return response.status = 405
                break
        }
    }
    def category() {

        if (!params.id)
            return response.status = 400
        def categoryInstance = Category.get(params.id)
        if (!categoryInstance)
            return response.status = 404
        switch (request.method) {
            case 'GET':
                serializeThis(categoryInstance, request.getHeader("Accept"))
                break
            case 'PUT':
                break
            case 'PATCH':
                break
            case 'DELETE':
                break
            default:
                 return response.status = 405
                break
        }
    }
    def categories() {

        switch (request.method) {
            case 'GET':
                serializeThis(Category.list(), request.getHeader("Accept"))
                break
            case 'POST':
                break
            default:
                return response.status = 405
                break
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
                break

            case 'PATCH':
                break

            case 'DELETE':
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
            case "application/xml":
            case "text/xml":
            case "xml":
                render object as XML
                break
            case "application/json":
            case "text/json":
            case "json":
            default:
                render object as JSON
                break
        }
    }

}
