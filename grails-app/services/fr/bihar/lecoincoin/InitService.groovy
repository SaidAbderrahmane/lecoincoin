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

        def categoryMap = [
                'Non classé'           : [], // root category
                'Immobilier'           : ['Ventes', 'Locations', 'Commerces', 'Terrains'],
                'Véhicules'            : ['Voitures', 'Motos', 'Utilitaires', 'Accessoires'],
                'Locations de vacances': ['Maisons', 'Appartements', 'Chalets', 'Camping'],
                'Mode'                 : ['Vêtements', 'Chaussures', 'Accessoires', 'Bijoux'],
                'Maison & Jardin'      : ['Mobilier', 'Décoration', 'Jardinage', 'Bricolage'],
                'Famille'              : ['Enfants', 'Bébés', 'Jeux & Jouets', 'Puériculture'],
                'Électronique'         : ['Téléphones', 'Ordinateurs', 'Télévisions', 'Électroménager'],
                'Loisirs'              : ['Livres', 'Musique', 'Sports', 'Jeux vidéo'],
                'Autres'               : ['Objets divers', 'Collection', 'Antiquités', 'Insolite']
        ]

        categoryMap.each { String catName, List<String> subcategories ->
            def categoryInstance = new Category(name: catName).save()

            subcategories.each { String subCatName ->
                new Category(name: subCatName, parent: categoryInstance).save()
            }

            (1..5).each { Integer saleAdId ->
                def addressInstance = new Address(address: 'Addresse', postCode: '012345', city: 'Ville', country: 'Pays')
                def saleAdInstance = new SaleAd(
                        title: "Title ${catName} $saleAdId",
                        description: 'Description',
                        price: saleAdId * 100,
                        address: addressInstance,
                        author: userClientInstance
                )
                categoryInstance.addToSaleAds(saleAdInstance)
            }
        }

        // Pour chaque utilisateur, on ajoute un message à destination de tous les autres utilisateurs
        User.list().each { User userAuthor ->
            User.list().each { User userDest ->
                new Message(
                        content: "Le message de $userAuthor.username à $userDest.username",
                        author: userAuthor,
                        dest: userDest
                ).save()
            }
        }
    }

}
