package fr.bihar.lecoincoin

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class SaleAdController {

    SaleAdService saleAdService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        println saleAdService.list(params).get(0).metaClass.properties
        // respond saleAdService.list(params), model:[saleAdCount: saleAdService.count()]
    }

    def show(Long id) {
        respond saleAdService.get(id)
    }

    def create() {
        respond new SaleAd(params)
    }

    def save(SaleAd saleAd) {
        if (saleAd == null) {
            notFound()
            return
        }

        try {
            saleAdService.save(saleAd)
        } catch (ValidationException e) {
            respond saleAd.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'saleAd.label', default: 'SaleAd'), saleAd.id])
                redirect saleAd
            }
            '*' { respond saleAd, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond saleAdService.get(id), model: [categoryList: Category.list(), userList: User.list()]
    }

    def update(SaleAd saleAd) {
        if (saleAd == null) {
            notFound()
            return
        }

        try {
            /**
             * TODO: Il faut sauvegarder l'image envoyée depuis le formulaire
             * La sauvegarder en local puis créer une illustration sur le fichier
             * que vous avez sauvegardé. Enfin on ajoute l'illustration à l'annonce
              */
            saleAdService.save(saleAd)
        } catch (ValidationException e) {
            respond saleAd.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'saleAd.label', default: 'SaleAd'), saleAd.id])
                redirect saleAd
            }
            '*'{ respond saleAd, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        saleAdService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'saleAd.label', default: 'SaleAd'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'saleAd.label', default: 'SaleAd'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
