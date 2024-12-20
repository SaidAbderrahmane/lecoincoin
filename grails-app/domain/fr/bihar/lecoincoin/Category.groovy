package fr.bihar.lecoincoin

import grails.rest.Resource

class Category {

    String name
    Category parent

    static hasMany = [saleAds: SaleAd, children: Category]
    static belongsTo = [parent: Category]

    static constraints = {
        name nullable: false, blank: false
        parent nullable: true  // Parent is optional, for root categories
    }

}
