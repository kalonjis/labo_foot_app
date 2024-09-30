# LaboFootApp

![LaboFootApp Logo](https://yourlogo.com/logo.png) *(Si vous avez un logo, ajoutez-le ici)*

## Description

**LaboFootApp** est une application de gestion de tournois de football. Elle permet aux organisateurs de créer des tournois, gérer les équipes, suivre les scores des matchs, et assurer un suivi en temps réel de l'état des tournois. L'application s'adresse aussi bien aux amateurs qu'aux professionnels du football qui souhaitent organiser des compétitions de manière efficace et intuitive.

## Table des Matières

- [Fonctionnalités](#fonctionnalités)
- [Technologies Utilisées](#technologies-utilisées)
- [Installation](#installation)
- [Configuration](#configuration)
- [Usage](#usage)
- [Tests](#tests)
- [Contributions](#contributions)
- [Licence](#licence)
- [Contact](#contact)

## Fonctionnalités

- **Création de Tournois** : Créez de nouveaux tournois, définissez leurs statuts (en construction, en cours, annulé...).
- **Gestion des Matchs** : Ajoutez des matchs, modifiez les scores (si le match est en cours) et gérez les différentes phases.
- **Suivi des Équipes** : Suivez les performances des équipes, gérez leur inscription et leurs scores.
- **Validation Personnalisée** : Vérification des statuts via des annotations personnalisées pour éviter les erreurs de saisie.

## Technologies Utilisées

- **Java 17**
- **Spring Boot** (MVC, Data JPA, Validation)
- **Hibernate Validator** pour la validation des entités
- **PostgreSQL** pour la base de données
- **Maven** pour la gestion des dépendances
- **Lombok** pour simplifier le code Java
- **Jakarta Validation API** pour la validation des données

## Installation

Suivez ces étapes pour cloner et exécuter le projet sur votre machine locale.

### Prérequis

- **Java 17** ou version supérieure
- **Maven** installé
- **PostgreSQL** configuré

### Étapes d'Installation

1. Clonez le dépôt :

   ```bash
   git clone https://github.com/kalonjis/labo_foot_app.git

2. Accédez au répertoire du projet :

   ```bash
   cd labo_foot_app

3. Modifiez les propriétés de connexion à la base de données dans src/main/resources/application.properties :

   ```bash
   spring.datasource.url=jdbc:postgresql://localhost:5432/labo_foot_app
   spring.datasource.username=YOUR_DB_USERNAME
   spring.datasource.password=YOUR_DB_PASSWORD

4. Compilez et exécutez l'application :

   ```bash
   mvn clean install
   mvn spring-boot:run

### Configuration

Pour configurer l'application, vous pouvez modifier le fichier application.properties. Voici quelques paramètres importants :

- Port par défaut : L'application est accessible par défaut sur le port 8080. Vous pouvez modifier cela avec :

   ```bash
   server.port=YOUR_PORT

## Usage

Une fois l'application démarrée, utilisez l'API pour gérer vos tournois. Voici quelques exemples de points de terminaison :

Créer un tournoi : POST /api/tournament
Modifier le statut d'un tournoi : PUT /api/tournament/status/{id}
Ajouter un match : POST /api/match
Pour explorer l'ensemble des API, vous pouvez également accéder à la documentation via Swagger (si configuré) sur /swagger-ui.html.

## Contributions
Les contributions sont les bienvenues ! Pour contribuer :

1. Forkez le projet.
2. Clonez le dépôt sur votre machine locale.
3. Créez une branche pour vos modifications (git checkout -b feature/nouvelle-fonctionnalite).
4. Commitez vos changements (git commit -m 'Ajout d'une nouvelle fonctionnalité').
5. Pushez vers votre dépôt (git push origin feature/nouvelle-fonctionnalite).
6. Créez une Pull Request.

## Licence
Ce projet est sous licence MIT - voir le fichier LICENSE pour plus de détails.

## Contact
- Auteurs : Kalonjis, Anzuka
- GitHub : kalonjis, anzuka
- Email : votre_email@example.com
- Pour toute question ou suggestion, n'hésitez pas à ouvrir une issue sur ce dépôt.