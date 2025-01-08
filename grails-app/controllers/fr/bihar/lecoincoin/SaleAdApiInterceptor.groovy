package fr.bihar.lecoincoin

import grails.plugin.springsecurity.SpringSecurityService

class SaleAdApiInterceptor {
    SpringSecurityService springSecurityService

    SaleAdApiInterceptor() {
        match(controller: "api", action: "(saleAd|saleAds)")
    }

    boolean before() {
        if (springSecurityService.currentUser.getAuthorities().any { it.authority in ['ROLE_ADMIN', 'ROLE_MODO'] }) {
            return true
        }

        switch (request.method) {
            case ['PATCH', 'PUT', 'DELETE'] as Set:
                def saleAd = SaleAd.get(params.id as Long)
                if (saleAd?.author?.id != springSecurityService.currentUser.id) {
                    log.error("You are not allowed to perform this action")
                    return false
                }
                break
        }

        return true
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }

}
