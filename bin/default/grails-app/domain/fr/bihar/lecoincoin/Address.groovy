package fr.bihar.lecoincoin

class Address {

    String address
    String postCode
    String city
    String country

    static belongsTo = SaleAd

    static constraints = {
        address nullable: false, blank: false
        postCode nullable: false, blank: false, maxSize: 10
        city nullable: false, blank: false
        country nullable: false, blank: false
    }
}
