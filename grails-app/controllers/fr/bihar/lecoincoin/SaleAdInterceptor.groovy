package fr.bihar.lecoincoin

import grails.plugin.springsecurity.SpringSecurityService


class SaleAdInterceptor {
    SpringSecurityService springSecurityService

    SaleAdInterceptor() {
        match(controller: 'saleAd', action: '(edit|update|delete)')
        match(controller : "api", action : "(saleAd|saleAds)")
    }

    boolean before() {
        if (springSecurityService.currentUser.getAuthorities().any { it.authority in ['ROLE_ADMIN', 'ROLE_MODO'] }) {
            return true
        }

        SaleAd saleAd = SaleAd.get(params.id as Long)
        if (saleAd?.author?.id != springSecurityService.currentUser.id) {
            flash.message = "You are not allowed to perform this action"
            redirect(controller: 'saleAd', action: 'index')
            return false
        }

        return true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
