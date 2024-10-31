package fr.bihar.lecoincoin

class Illustration {

    String fileName

    static belongsTo = [saleAd: SaleAd]

    static constraints = {
        fileName nullable: false, blank: false
    }
}
