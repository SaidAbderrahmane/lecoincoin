package fr.bihar.lecoincoin

/**
 * Represents an illustration associated with a sale advertisement.
 */
class Illustration {

    String fileName

    static belongsTo = [saleAd: SaleAd]

    static constraints = {
        fileName nullable: false, blank: false, maxSize: 255
    }

}
