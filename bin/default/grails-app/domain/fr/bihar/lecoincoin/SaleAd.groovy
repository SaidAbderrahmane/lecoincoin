package fr.bihar.lecoincoin

/**
 * Represents a sale advertisement.
 */
class SaleAd {

    String title
    String description
    BigDecimal price
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
        price nullable: false, min: 0.0
        active nullable: false
    }

    static mapping = {
        description type: 'text'
    }

}
