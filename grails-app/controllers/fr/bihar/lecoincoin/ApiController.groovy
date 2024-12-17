package fr.bihar.lecoincoin

import grails.converters.JSON
import grails.converters.XML
import grails.plugin.springsecurity.annotation.Secured

@Secured('ROLE_ADMIN')
class ApiController {
    // Ressource User Singleton
    // GET, PUT, PATCH, DELETE /api/user/{id}
    def user() {
        // On vérifie qu'il y a bien un param "id" sans quoi erreur car nous sommes dans le traitement d'une ressource singleton
        if (!params.id)
            return response.status = 400
        // On vérifie que l'instance d'User désigné corresponde bien à un utilisateur existant
        def userInstance = User.get(params.id)
        if (!userInstance)
            return response.status = 404
        // A ce stade on sait que l'on a un utilisateur bien identifié
        switch (request.method) {
            case 'GET':
                serializeThis(userInstance, request.getHeader("Accept"))
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
        return response.status = 406
    }
    // Ressource Users Collection
    // GET, POST /api/users
    def users() {
        switch (request.method) {
            case 'GET':
                serializeThis(User.list(), request.getHeader("Accept"))
                break
            case 'POST':
                break
            default:
                return response.status = 405
                break
        }
        return response.status = 406
    }
    def saleAd() {
    }
    def saleAds() {
    }
    def category() {
    }
    def categories() {
    }
    def message() {
    }
    def messages() {
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