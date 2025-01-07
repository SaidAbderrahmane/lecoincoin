package fr.bihar.lecoincoin

import grails.plugin.springsecurity.SpringSecurityService


class CategoryInterceptor {

    SpringSecurityService springSecurityService

    CategoryInterceptor() {
        match(controller: 'category')
    }

    boolean before() {
        if (springSecurityService.currentUser.getAuthorities().any { it.authority in ['ROLE_ADMIN', 'ROLE_MODO'] }) {
            return true
        }

        return false
    }

    boolean after() { true }

    void afterView() {
        // no-op
    }
}
