package fr.bihar.lecoincoin

import static org.springframework.http.HttpStatus.*

import grails.plugin.springsecurity.SpringSecurityService
import grails.validation.ValidationException

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
        // Create a new SaleAd from the submitted parameters
        println params
        SaleAd saleAd = new SaleAd()
        saleAd.title = params.title
        saleAd.description = params.description
        saleAd.price = params.price as BigDecimal
        saleAd.address = Address.get(params.address.id as Integer)
        saleAd.category = Category.get(params.category.id as Integer)
        saleAd.author = springSecurityService.currentUser

        // Handle file uploads if files were provided
        def uploadedFiles = request.getFiles('files')
        if (uploadedFiles?.empty) {
            flash.message = 'Please select files to upload.'
        } else {
            uploadedFiles.each { uploadedFile ->
                println uploadedFile
                if (!uploadedFile?.empty) {
                    try {
                        // Define the upload directory (can be configured in application.yml)
                        def uploadDir = grailsApplication.config.uploadDirectory ?: '/path/to/upload/directory'
                        def fileName = uploadedFile.originalFilename
                        def filePath = java.nio.file.Paths.get(uploadDir, fileName)

                        // Save the uploaded file to the file system
                        java.nio.file.Files.copy(uploadedFile.inputStream, filePath)

                        // Create an Illustration object and associate it with the SaleAd
                        def illustration = new Illustration(fileName: fileName, saleAd: saleAd)
                        illustration.save(flush: true)

                        // Add the illustration to the SaleAd's illustrations collection
                        saleAd.addToIllustrations(illustration)
                    } catch (IOException e) {
                        flash.message = "File upload failed: ${e.message}"
                        render(view: 'create', model: [saleAd: saleAd])
                        return
                    }
                }
            }
        }

        // Save the SaleAd object
        if (!saleAd.save()) {
            render(view: 'create', model: [saleAd: saleAd])
            return
        }

        // Redirect to the view or other action after successful save
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
