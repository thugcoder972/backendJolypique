# Backend - Jolypique

## Description
Ce projet représente le backend d'une application de billetterie pour les Jeux Olympiques, développé avec Spring Boot. Il inclut des fonctionnalités telles que l'authentification des utilisateurs, la gestion des billets, et la génération de QR codes pour les tickets.

## Fonctionnalités principales
- Authentification et autorisation avec Spring Security
- Gestion des utilisateurs (création, modification, suppression)
- Gestion des billets (création, modification, suppression)
- Vérification des billets via QR code (utilisation de JWT pour la sécurité)
- Statistiques de ventes pour l'administration

## Technologies utilisées
- **Backend** : Spring Boot, Spring Security
- **Base de données** : H2 (ou autre base de données selon votre choix, comme MySQL ou PostgreSQL)
- **Sécurité** : JSON Web Tokens (JWT) pour l'authentification
- **Génération de QR Code** : pour la validation des billets

## Prérequis
- JDK 11 ou supérieur
- Maven ou Gradle
- Base de données (H2 configurée par défaut et postgres)

## Installation
1. Clonez le projet :
   ```bash
   git clone https://github.com/thugcoder972/backendJolypique.git
   cd backendJolypique

2.Installez les dépendances avec Maven ou Gradle :

mvn install



3.Lancez le serveur Spring Boot :

mvn spring-boot:run

##Configuration de la base de données
Par défaut, ce projet utilise H2 en mémoire. Si vous souhaitez utiliser une autre base de données (par exemple, PostgresSQL), vous pouvez configurer les paramètres dans le fichier application.properties.


# Configuration de la base de données PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:54../postgres
spring.datasource.username=postgres
spring.datasource.password=your password
spring.datasource.driver-class-name=org.postgresql.Driver








Endpoints API
Authentification
POST /api/register: Enregistrement de l'utilisateur
CET /api/login : Authentification de l'utilisateur, retourne un token JWT.

Gestion des Achats
GET /api/users/{id} : Récupère l'achat d'un utilisateur par ID.
POST /api/users : Crée un nouvel achat  .

Gestion des Tickets
POST /api/tickets : Crée un nouveau billet.
GET /api/tickets/{id} : Récupère un billet par ID.
PUT /api/tickets/{id} : Mettre à jour un tickets existant 
DELETE /api/tickets/{id} : Supprime un billet.



Sécurité
Authentification via JWT : un token est généré lors de la connexion de l'utilisateur.
Sécurisation des requêtes avec Spring Security.
Protection contre les attaques CSRF et XSS.




Auteurs
Terry Marie-Sainte









