package fr.bihar.lecoincoin

import grails.gorm.transactions.Transactional
import fr.bihar.lecoincoin.Category

/**
 * Service to initialize the database with some data
 */

@Transactional
class InitService {

    def init() {
        // Création des rôles
        def roleClient = new Role(authority: 'ROLE_CLIENT').save()
        def roleModo = new Role(authority: 'ROLE_MODO').save()
        def roleAdmin = new Role(authority: 'ROLE_ADMIN').save()
        // Création des utilisateurs
        def userClientInstance = new User(username: 'client',
                password: 'client',
                email: 'client@lecoincoin.fr',
                phone: '0123456789').save()
        def userModoInstance = new User(username: 'modo',
                password: 'modo',
                email: 'modo@lecoincoin.fr').save()
        def userAdminInstance = new User(username: 'admin',
                password: 'admin',
                email: 'admin@lecoincoin.fr').save()
        // Association des utilisateurs aux rôles
        UserRole.create(userClientInstance, roleClient, true)
        UserRole.create(userModoInstance, roleModo, true)
        UserRole.create(userAdminInstance, roleAdmin, true)

        assert User.count == 3
        assert Role.count == 3
        assert UserRole.count == 3

        // Pour chaque item de cette liste, je crée une catégorie
        ['Non classe', 'Immobilier', 'Véhicules', 'Locations de vacances', 'Emploi', 'Mode',
         'Maison & Jardin', 'Famille', 'Électronique', 'Loisirs', 'Autres'].eachWithIndex {
            String catName, Integer catIndex ->

            def categoryInstance = new Category(name: catName).save()

            // Pour chaque catégorie, je crée 4 sous-catégories
            ['SubCat1', 'SubCat2', 'SubCat3', 'SubCat4'].each {
                String subCatName -> new Category(name: subCatName, parent: categoryInstance).save()
            }

            //Dans chaque catégorie (parent) j'ajoute 5 annonces, l'annonce est liée à l'utilisateur "CLIENT" et fait référence à une illustration
            (1..5).each { Integer saleAdId ->
                // On crée l'adresse que l'on va utiliser dans l'annonce
                def addressInstance = new Address(address: 'Addresse', postCode: '012345', city: 'Ville', country: 'Pays')

                // Création de l'annonce
                def saleAdInstance = new SaleAd(title: "Title $catIndex $saleAdId", description: 'Description',
                        price: saleAdId * 100, address: addressInstance, author: userClientInstance)

                // On associe l'annonce à une catégorie
                categoryInstance.addToSaleAds(saleAdInstance)
            }

            // On save la catégorie, qui va save l'annonce, qui va save l'adresse ainsi que l'illustration
            categoryInstance.save(flush: true, failOnError: true)
         }

        // Pour chaque utilisateur, on ajoute un message à destination de tous les autres utilisateurs
        User.list().each {
            User userAuthor ->
                User.list().each {
                    User userDest ->
                        new Message(content: "Le message de $userAuthor.username à $userDest.username", author: userAuthor, dest: userDest).save()
                }
        }
    }

}
