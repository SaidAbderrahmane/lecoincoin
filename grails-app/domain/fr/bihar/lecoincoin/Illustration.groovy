package fr.bihar.lecoincoin

class Illustration {

    byte[] fileData

    static belongsTo = [saleAd: SaleAd]

    static constraints = {
        fileData(nullable: false, maxSize: 10 * Math.pow(1024, 2))
    }

}
