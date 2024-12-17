package fr.bihar.lecoincoin

import grails.rest.Resource

class Category {

    String name
    Category parent

    static hasMany = [saleAds: SaleAd]

    static constraints = {
        name nullable: false, blank: false
        parent nullable: true
    }
}
