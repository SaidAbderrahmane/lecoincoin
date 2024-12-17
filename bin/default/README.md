
# Projet "Lecoincoin"

## Contexte du Projet

Dans ce projet, vous devrez réaliser un site de petites annonces, similaire à "Leboncoin", en utilisant le framework **Grails v5.3.6**. L'objectif est de mettre en place une interface d'administration et une API REST pour gérer les annonces, les utilisateurs et autres entités clés de la plateforme.

## Spécifications Techniques

### Environnement

- **Framework** : Grails v5.3.6
- **Sécurité** : 
	- Gestion des utilisateurs et des rôles via le plugin Spring Security Core
	- Gestion de la sécurité pour l'API REST via Spring Security REST

### Modélisation des Entités

Vous devrez modéliser les entités suivantes :

1. **User** : Représente un utilisateur du site.
2. **Role** : Définit les différents rôles des utilisateurs.
3. **SaleAd** : Représente une annonce de vente.
4. **Category** : Permet de classer les annonces par catégorie.
5. **Illustration** : Gère les images associées aux annonces (avec support d’upload d’images côté serveur).
6. **Message** : Permet aux utilisateurs de communiquer entre eux via des discussions privées.
7. **Address** : Fournit des informations d'adresse pour une annonce.

Les entités **User** et **Role** seront gérées par **Spring Security** pour assurer la sécurité de l'application.

Les **Roles** accessible seront : 
- ADMIN_ROLE : Il pourra tout faire
- MODO_ROLE : Il pourra tout consulter et modifier
- USER_ROLE : Il pourra consulter les annonces, modifier **ses** annonces, ainsi que **son** profil 

### Objectifs

#### 1. Interface d'Administration

L’interface d’administration doit permettre les opérations de **CRUD** (Create, Read, Update, Delete) pour chaque entité, avec des formulaires structurés regroupant les entités qui font sens. Par exemple :

- **User et Role** : Formulaire pour la gestion des utilisateurs et de leurs rôles.
- **SaleAd et Illustration** : Formulaire pour la gestion des annonces et des illustrations associées.

Les images uploadées doivent être stockées côté serveur et associées aux annonces via l'entité **Illustration**.

#### 2. API REST

L’API REST doit être conçue pour interagir avec les entités de manière cohérente et sécurisée :

- **Endpoints pour les collections** (ex. `/saleAds`) :
  - `GET` : Récupérer une liste d'entités.
  - `POST` : Créer une nouvelle entité dans la collection.

- **Endpoints pour les singletons** (ex. `/saleAds/{id}`) :
  - `GET` : Récupérer une entité spécifique.
  - `PUT` : Mettre à jour entièrement une entité existante.
  - `PATCH` : Mettre à jour partiellement une entité existante.
  - `DELETE` : Supprimer une entité.

L'API doit retourner des codes **HTTP** appropriés pour chaque action (ex. 200 pour succès, 404 pour non trouvé, 201 pour création, etc.).

#### 3. Documentation de l'API

Chaque endpoint doit être **documenté** pour faciliter son utilisation. La documentation doit inclure :

- Descriptions des paramètres requis.
- Exemples de requêtes.
- Codes de réponse attendus.


## Travail à Rendre

Vous devrez fournir :

1. **Code Source du Projet** : Incluant l'interface d'administration et l'API REST.
2. **Documentation pour l'API** : Un document ou fichier de documentation (ex. OpenAPI ou Swagger) décrivant les endpoints de l'API.
3. **Tests de l'API** : Des tests sont attendus pour l'API, sous la forme que vous souhaitez, sachant qu'on utilise communément POSTMAN pour ce genre de choses.
4. **Readme** : Un readme qui contiendra les informations relatives à votre projet, il décrira également ce que vous avez réalisé et présentera un guide expliquant comment lancer le projet en local.
5. **Soutenance** : Vous devrez préparer une soutenance qui sera présentée devant la classe afin que l'on puisse apprécier ensemble la qualité de votre travail, la date de la soutenance vous sera transmise plus tard.

## Critères d'Évaluation

1. **Interface d'administration** : Fonctionnement des opérations CRUD sur l'interface d'administration. (sur 25)
2. **Gestion des Images** : Bon fonctionnement de l'upload et du stockage des images côté serveur. (sur 15)
3. **API** : Respect des spécifications pour l'API REST (endpoints, codes HTTP, documentation). (sur 30)
4. **Readme** : Clarté du readme. (sur 10)
5. **Soutenance** : Qualité de la présentation que vous ferez qui devra être préparée et fluide. (sur 10)
6. **Finition globale** : Qualité du code métier et des interfaces. (sur 10)

## Dos and Don'ts
1. Vous pouvez utiliser un template pour l'interface d'administration
2. Vous pouvez utiliser du code que vous trouver sous réserve de le comprendre et d'être capable de me l'expliquer
3. Tout plagiat sera sanctionné d'une note nulle pour toutes les personnes concernées
