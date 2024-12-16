package fr.bihar.lecoincoin

class SaleAd {
    String title
    String description
    Float price
    Date dateCreated
    Date lastUpdated
    Boolean active = Boolean.FALSE
    Address address
    Category category

    static hasMany = [illustrations: Illustration]

    static belongsTo = [author: User]

    static constraints = {
        title nullable: false, blank: false, maxSize: 100
        description nullable: false, blank: false
        price nullable: false, min: 0F, scale: 2
        active nullable: false
    }

    static mapping = {
        description type: 'text'
    }
}