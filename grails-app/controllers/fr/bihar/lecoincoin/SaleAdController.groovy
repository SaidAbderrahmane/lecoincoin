package fr.bihar.lecoincoin

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException

import static org.springframework.http.HttpStatus.*

@Secured(['ROLE_ADMIN'])
class SaleAdController {

    UserService userService
    SaleAdService saleAdService
    IllustrationService illustrationService
    SpringSecurityService springSecurityService

    static allowedMethods = [save: 'POST', update: 'PUT', delete: 'DELETE']

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond saleAdService.list(params), model:[saleAdCount: saleAdService.count()]
    }

    def show(Long id) {
        respond saleAdService.get(id)
    }

    def create() {
        respond new SaleAd(params), model: [categoryList: Category.list(), addressList: Address.list()]
    }

    def save() {
        log.info('Started saving SaleAd')

        SaleAd saleAd = new SaleAd()
        saleAd.title = params.title
        saleAd.description = params.description
        saleAd.price = params.price as BigDecimal
        saleAd.address = Address.get(params.address.id as Integer)
        saleAd.category = Category.get(params.category.id as Integer)
        saleAd.author = springSecurityService.currentUser

        def uploadedFiles = request.getFiles('files')
        println "Uploaded files list: ${uploadedFiles}"
        println "Number of uploaded files: ${uploadedFiles.size()}"

        uploadedFiles.each { file ->
            println "File original name: ${file.originalFilename}"
            println "File content type: ${file.contentType}"
            println "File size (bytes): ${file.size}"
            println "File class: ${file.class.name}"
        }

        if (uploadedFiles.size() > 1) {
            log.info("Processing ${uploadedFiles.size()} uploaded files")
            def illustrations = illustrationService.createMany(uploadedFiles)
            illustrations.each { illustration ->
                saleAd.addToIllustrations(illustration)
            }
        }

        saleAdService.save(saleAd)
        log.info("SaleAd saved successfully: ${saleAd.id}")

        flash.message = 'Sale Ad saved successfully'
        log.info("Redirecting to show page for SaleAd with id: ${saleAd.id}")
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

        try {
            def uploadedFiles = request.getFiles('files')

//            Bizarre case where uploadedFiles size is one when no file is uploaded
            if (uploadedFiles.size() > 1) {
                log.info("Processing ${uploadedFiles.size()} uploaded files")
                def illustrations = illustrationService.createMany(uploadedFiles)
                illustrations.each { illustration ->
                    saleAd.addToIllustrations(illustration)
                }
            }
            saleAdService.save(saleAd)
        } catch (ValidationException e) {
            log.error("Error updating SaleAd: ${e.message}", e)
            respond saleAd.errors, view:'edit'
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
                redirect action:'index', method:'GET'
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
