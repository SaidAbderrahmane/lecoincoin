# ReadMe: Plateforme de Petites Annonces

## Table des Matières
1. [Introduction](#introduction)
2. [Fonctionnalités Clés](#fonctionnalités-clés)
   - [Interface d'Administration](#interface-dadministration)
   - [API REST](#api-rest)
   - [Documentation de l'API](#documentation-de-lapi)
3. [Structure des Entités](#structure-des-entités)
4. [Prérequis](#prérequis)
5. [Lancer le Projet en Local](#lancer-le-projet-en-local)
6. [Documentation de l'API](#documentation-de-lapi-1)
7. [Tests](#tests)
8. [Captures d'Écran](#captures-décran)
9. [Auteurs](#auteurs)
10. [Soutenance](#soutenance)
11. [Notes](#notes)

---

## Introduction
Ce projet est une plateforme de petites annonces inspirée de "Leboncoin", réalisée avec le framework Grails v5.3.6. Elle propose une interface d'administration et une API REST pour gérer les utilisateurs, les annonces, le categories d'annonces, les images d'annonces, et la messagerie entre utilsateurs.

---

## Fonctionnalités Clés
### Interface d'Administration
L'interface d'administration permet de :
- Gérer les utilisateurs et leurs rôles.
- Créer, lire, mettre à jour et supprimer des annonces.
- Associer des images aux annonces via un système d'upload.
- Classer les annonces par catégorie.
- Configurer les informations d'adresse pour chaque annonce.
- Accéder à une messagerie privée entre utilisateurs.

![Capture d'écran - Interface d'Administration](mockup_admin_overview.png)

### API REST
L'API REST fournit des fonctionnalités robustes pour interagir avec les entités :
- **Gestion des utilisateurs** :
  - Récupération de la liste des utilisateurs.
  - Création, mise à jour, et suppression d'utilisateurs.
  - Attribution de rôles.
- **Gestion des annonces** :
  - Récupération de toutes les annonces ou d'une annonce spécifique.
  - Création, modification et suppression d'annonces.
- **Gestion des catégories** :
  - Récupération de la liste des catégories.
  - Ajout, édition et suppression de catégories.
- **Gestion des messages** :
  - Envoi de messages privés.
  - Récupération des messages liés à un utilisateur.
- **Gestion des adresses** :
  - Ajout et modification des adresses pour les annonces.

![Capture d'écran - Endpoint d'annonces](mockup_saleads_api.png)

### Documentation de l'API
- Une documentation Postman détaillée est incluse.
- Exemples de requêtes pour chaque endpoint.

![Capture d'écran - Swagger Documentation](mockup_swagger.png)

---

## Structure des Entités
### User
- **Propriétés** : `id`, `username`, `password`, `email`, `roles`.
- **Rôles disponibles** : `ADMIN_ROLE`, `MODO_ROLE`, `USER_ROLE`.

### Role
- **Propriétés** : `id`, `roleName`.

### SaleAd
- **Propriétés** : `id`, `title`, `description`, `price`, `category`, `user`, `illustrations`.

### Category
- **Propriétés** : `id`, `name`.

### Illustration
- **Propriétés** : `id`, `imagePath`, `saleAd`.

### Message
- **Propriétés** : `id`, `sender`, `receiver`, `content`, `timestamp`.

### Address
- **Propriétés** : `id`, `street`, `city`, `zipCode`, `country`.

---

## Prérequis
### Logiciels nécessaires
- Java 11+
- Grails v5.3.6
- IDE compatible (IntelliJ, VS Code, etc.)

### Installation des dépendances
Exécutez la commande suivante dans le répertoire racine du projet :
```bash
grails dependencies
```

---

## Lancer le Projet en Local
1. Clonez le dépôt :
```bash
git clone https://github.com/BIHAR-ESTIA/grails-et-rest-sm.git
```
2. Configurez les paramètres de la base de données dans le fichier `application.yml`.
3. Lancez le serveur Grails :
```bash
grails run-app
```
4. Accédez à l'application sur [http://localhost:8080](http://localhost:8080).

---

## Documentation de l'API
### Exemples de Requêtes
#### Récupérer toutes les annonces
**GET** `/saleAds`
- Réponse :
```json
[
  {
    "id": 1,
    "title": "Voiture d'occasion",
    "price": 5000
  }
]
```

#### Créer une annonce
**POST** `/saleAds`
- Corps :
```json
{
  "title": "Chaise de bureau",
  "description": "Chaise ergonomique, très bon état",
  "price": 150,
  "categoryId": 3
}
```
- Réponse :
```json
{
  "id": 5,
  "message": "Annonce créée avec succès."
}
```

#### Document Postman
Le fichier `collection.json` est disponible dans le répertoire `src/main/docs/`.

![Capture d'écran - Exemple de requête API](mockup_api_request.png)

---

## Tests
### Tester l'API avec Postman
1. Importez la collection Postman depuis `src/tests/Postman/collection.json`.
2. Configurez l'URL de base dans les variables d'environnement.
3. Exécutez les requêtes prédéfinies pour valider les endpoints.

### Tests Unitaires
Lancez les tests avec :
```bash
grails test-app
```

![Capture d'écran - Résultats des tests](mockup_tests_results.png)

---

## Captures d'Écran
### Interface d'administration
![Capture d'écran 1](mockup_admin_overview.png)

### Gestion des utilisateurs
![Capture d'écran 2](mockup_user_management.png)

### Gestion des annonces
![Capture d'écran 3](mockup_salead_management.png)

### Messagerie privée
![Capture d'écran 4](mockup_messaging.png)

---

## Auteurs
- [Said Abderrahmane](https://github.com/saidabderrahmane)

- [ Mahdi DADDI HAMMOU](https://github.com/mahdidhammou)