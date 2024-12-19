package fr.bihar.lecoincoin

import static org.springframework.http.HttpStatus.*
import grails.converters.JSON
import java.security.MessageDigest

import grails.plugin.springsecurity.SpringSecurityService
import grails.validation.ValidationException

import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class SaleAdController {

    SaleAdService saleAdService
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

    // def save() {
    //     def saleAd = new SaleAd(params)
    //     if (saleAd == null) {
    //         notFound()
    //         return
    //     }

    //     try {
    //         saleAdService.save(saleAd)
    //     } catch (ValidationException e) {
    //         respond saleAd.errors, view:'create'
    //         return
    //     }

    //     request.withFormat {
    //         form multipartForm {
    //             flash.message = message(
    //                 code: 'default.created.message',
    //                 args: [message(code: 'saleAd.label', default: 'SaleAd'), saleAd.id]
    //             )
    //             redirect saleAd
    //         }
    //         '*' { respond saleAd, [status: CREATED] }
    //     }
    // }

    def save() {
        log.info('Started saving SaleAd')

        SaleAd saleAd = new SaleAd()
        saleAd.title = params.title
        saleAd.description = params.description
        saleAd.price = params.price as BigDecimal
        saleAd.address = Address.get(params.address.id as Integer)
        saleAd.category = Category.get(params.category.id as Integer)
        saleAd.author = springSecurityService.currentUser

        if (!saleAd.save()) {
            log.error("Failed to save SaleAd: ${saleAd.errors}")
            flash.message = 'Error saving SaleAd'
            render(view: 'create', model: [saleAd: saleAd])
            return
        }
        log.info("SaleAd saved successfully: ${saleAd.id}")

        // Get the uploaded files from the request
        def uploadedFiles = request.getFiles('files')

        String uploadDir = grailsApplication.config.illustrations.basePath
        File dir = new File(uploadDir)

        // Check and create the upload directory if it doesn't exist
        if (!dir.exists()) {
            log.debug("Creating upload directory: ${dir}")
            if (!dir.mkdirs()) {
                log.error("Failed to create upload directory: ${dir}")
                flash.message = 'Failed to create the upload directory.'
                render(view: 'create', model: [saleAd: saleAd])
                return
            }
        }

        uploadedFiles.each { uploadedFile ->
            log.debug("Processing file: ${uploadedFile.originalFilename}")

            def md5Digest = MessageDigest.getInstance('MD5')
            def originalFilenameBytes = uploadedFile.originalFilename.bytes
            def hashedFilename = md5Digest.digest(originalFilenameBytes).encodeHex().toString()

            // add the file extension to the hashed filename
            def fileExtension = uploadedFile.originalFilename.tokenize('.').last()
            hashedFilename = "${hashedFilename}.${fileExtension}"

            log.debug("Hashed filename: ${hashedFilename}")

            File file = new File(dir, hashedFilename)

            if (!file.createNewFile()) {
                log.error("Failed to create file: ${hashedFilename}")
                flash.message = "Failed to create file ${hashedFilename}."
                render(view: 'create', model: [saleAd: saleAd])
                return
            }

            try {
                uploadedFile.transferTo(file.toPath())
                log.debug("File saved: ${file.absolutePath}")

                Illustration illustration = new Illustration()
                illustration.fileName = hashedFilename

                saleAd.addToIllustrations(illustration)
            } catch (Exception e) {
                log.error("Error saving file ${hashedFilename}: ${e.message}", e)
                flash.message = "Error saving file ${hashedFilename}."
                render(view: 'create', model: [saleAd: saleAd])
                return
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
