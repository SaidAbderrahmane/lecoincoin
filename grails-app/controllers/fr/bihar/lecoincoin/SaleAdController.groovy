package fr.bihar.lecoincoin

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import org.springframework.web.multipart.MultipartFile

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN',"ROLE_CLIENT","ROLE_MODO"])
class SaleAdController {

    UserService userService
    SaleAdService saleAdService
    IllustrationService illustrationService
    SpringSecurityService springSecurityService

    static allowedMethods = [save: 'POST', update: 'PUT', delete: 'DELETE']

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond saleAdService.list(params), model: [saleAdCount: saleAdService.count()]
    }

    def show(Long id) {
        respond saleAdService.get(id)
    }

    def create() {
        respond new SaleAd(params), model: [categoryList: Category.list(), addressList: Address.list()]
    }

    def save() {
        log.info('Started saving SaleAd')
        println(params)
        SaleAd saleAd = new SaleAd(
                title: params.title,
                description: params.description,
                price: params.price as BigDecimal,
                author: (springSecurityService.currentUser as User),
                category: Category.get(params.category.id as Integer),
                address: new Address(
                        address: params.address.address,
                        postCode: params.address.postCode,
                        city: params.address.city,
                        country: params.address.country
                ),
        )

        ArrayList<MultipartFile> uploadedFiles = request.getFiles('files') ?: []
        uploadedFiles = illustrationService.validateMany(uploadedFiles) ?: []

        if (!uploadedFiles.empty) {
            def illustrations = illustrationService.createMany(uploadedFiles)
            illustrations.each { illustration ->
                saleAd.addToIllustrations(illustration)
            }
        }

        try {
            saleAdService.save(saleAd)
        } catch (ValidationException e) {
            respond saleAd.errors, view: 'create'
            return
        }

        flash.message = 'Sale Ad saved successfully'
        redirect(action: 'show', id: saleAd.id)
    }


    def edit(Long id) {
        respond saleAdService.get(id), model: [categoryList: Category.list(), userList: User.list()]
    }


    def update(SaleAd saleAd) {
        if (saleAd == null) {
            notFound()
            return
        }

        ArrayList<MultipartFile> uploadedFiles = request.getFiles('files') ?: []
        uploadedFiles = illustrationService.validateMany(uploadedFiles) ?: []

        if (!uploadedFiles.empty) {
            def illustrations = illustrationService.createMany(uploadedFiles)
            illustrations.each { illustration ->
                saleAd.addToIllustrations(illustration)
            }
        }

        try {
            saleAdService.save(saleAd)
        } catch (ValidationException e) {
            log.error("Error while updating SaleAd: ${e.message}")
            respond saleAd.errors, view: 'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(
                        code: 'default.updated.message',
                        args: [saleAd.id]
                )
                redirect saleAd
            }
            '*' { respond saleAd, [status: OK] }
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
                redirect action: 'index', method: 'GET'
            }
            '*' { render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'saleAd.label', default: 'SaleAd'), params.id])
                redirect action: 'index', method: 'GET'
            }
            '*' { render status: NOT_FOUND }
        }
    }

}
