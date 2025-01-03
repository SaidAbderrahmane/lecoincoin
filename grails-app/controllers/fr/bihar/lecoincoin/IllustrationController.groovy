package fr.bihar.lecoincoin

import grails.gorm.transactions.Transactional
import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured


@Transactional
@Secured(['ROLE_ADMIN'])
class IllustrationController {

    IllustrationService illustrationService
    SpringSecurityService springSecurityService

    def index() {}

    def delete() {
        // check if salead belongs to current user
        def saleAd = SaleAd.get(params.saleAdId as Long)
        if (saleAd.author != springSecurityService.currentUser) {
            flash.message = "You are not allowed to delete this illustration."
            redirect(action: 'edit', controller: 'saleAd', id: params.saleAdId)
            return
        }
        illustrationService.delete(params.id as Long)
        flash.message = "Illustration deleted successfully."
        println("redirecting to saleAd edit ${params.saleAdId}")
        redirect(action: 'edit', controller: 'saleAd', id: params.saleAdId)
    }
}
