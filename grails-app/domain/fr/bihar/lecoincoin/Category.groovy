package fr.bihar.lecoincoin

class Category {

    String name
    Category parent

    static hasMany = [saleAds: SaleAd, children: Category]
    static belongsTo = [parent: Category]

    static constraints = {
        name nullable: false, blank: false
        parent nullable: true  // Parent is optional, for root categories
    }

    Boolean isRoot() {
        return id == 1
    }

}
