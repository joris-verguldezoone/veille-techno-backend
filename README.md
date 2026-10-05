# Kanban Mastermind - Backend API

Backend robuste et performant propulsé par **Quarkus** (Supersonic Subatomic Java) pour une application de gestion de type Kanban (Tableaux, Colonnes, Cartes). Il intègre une base de données PostgreSQL gérée via Hibernate avec le pattern Panache, et une sécurisation complète par jetons JWT.

---

## Démarrage Rapide

### Prérequis

* Java 17+
* Maven
* PostgreSQL (ou Docker pour lancer une instance locale)

### Lancer le projet en mode développement

Ce mode active le rechargement à chaud (Live Coding) et les Dev Services de Quarkus (qui montent une BDD temporaire si aucune n'est configurée).

```bash
# Nettoyer et compiler le projet
mvn clean compile

# Lancer l'application
./mvnw quarkus:dev

```

---

## Stack Technique & Extensions

Le projet utilise les extensions Quarkus suivantes (ajoutables via la CLI Quarkus ou Maven) :

| Extension | Commande d'installation | Description |
| --- | --- | --- |
| **OpenAPI & Swagger** | `mvn quarkus:add-extension -Dextensions="quarkus-smallrye-openapi"` | Documentation de l'API. Accessible sur `http://localhost:8080/q/swagger-ui/` |
| **REST Jackson** | `mvn quarkus:add-extension -Dextensions="quarkus-rest-jackson"` | Sérialisation et désérialisation JSON (API REST). |
| **Hibernate Validator** | `mvn quarkus:add-extension -Dextensions="quarkus-hibernate-validator"` | Validation des requêtes entrantes (DTOs). |
| **Security JWT** | `mvn quarkus:add-extension -Dextensions="smallrye-jwt,smallrye-jwt-build"` | Génération et validation des jetons d'authentification. |
| **Docker Build** | `quarkus extension add quarkus-container-image-docker` | Création automatique d'images Docker. |
| **Observabilité** | `quarkus extension add micrometer-opentelemetry` | Métriques, logs et télémétrie. |

---

## 🛠️ Astuces Développeur (Developer Experience)

### Configuration Git

Pour éviter les conflits complexes lors des `pull` :

```bash
git config pull.rebase false

```

### Dépannage Visual Studio Code (Java)

Si VS Code affiche des erreurs "fantômes" sur les imports ou perd la synchronisation avec Maven :

1. **Nettoyer le cache Java :** `Cmd + Shift + P` > *Clean Java Language Server Workspace*
2. **Forcer la mise à jour Maven :** `Cmd + Shift + P` > *Java: Update Project*
3. **Imports automatiques :** Utilisez `Cmd + .` (Quick Fix) pour que VS Code propose lui-même les dépendances. **Attention :** Privilégiez toujours les dépendances `jakarta.*` (standard Java EE/Quarkus) plutôt que `spring.*` ou d'anciennes versions.

---

## Ressources & Documentation

Une liste de références utiles pour comprendre l'architecture du projet :

**Écosystème Quarkus :**

* [Installation & Getting Started](https://quarkus.io/guides/getting-started/)
* [Datasource PostgreSQL](https://quarkus.io/guides/datasource/)
* [Hibernate ORM + Panache](https://quarkus.io/guides/hibernate-orm-panache/)
* [Créer des API REST](https://quarkus.io/guides/rest/)
* [Injection de dépendances (Analogie Spring Boot / CDI)](https://quarkus.io/guides/cdi/)
* [Sécurité basique](https://quarkus.io/guides/security-getting-started-tutorial/) et [Hash des mots de passe (Bcrypt)](https://quarkus.io/guides/security-jpa/)

**Java & Hibernate :**

* [Site officiel Hibernate](https://hibernate.org/)
* [Spécifications Jakarta Data](https://jakarta.ee/specifications/data/1.0/apidocs/jakarta.data/module-summary.html) *(spoiler : le CSS n'est visiblement pas fait pour les devs back)*
* [Hibernate Naming Strategies (Tags)](https://docs.hibernate.org/orm/6.6/javadocs/org/hibernate/boot/model/naming/ImplicitNamingStrategy.html)
* [Unique Constraints en JPA](https://www.baeldung.com/jpa-unique-constraints) (Ex: `@Table(uniqueConstraints = { @UniqueConstraint(name = "...", columnNames = { "..." }) })`)
* [Mécanisme de Dirty Checking](https://docs.hibernate.org/orm/current/userguide/html_single/#flushing-order) (Modification de l'état interne propagée en BDD lors du flush)
* [Standards HTTP Java (Response.Status)](https://docs.oracle.com/javaee/7/api/javax/ws/rs/core/Response.Status.html)
* [Fonctions JWT (Spring / Java)](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/jwt/Jwt.html)

---

## 🗺️ Roadmap & TODO List

### Architecture Backend

* [ ] **Migrations de base de données :** Intégrer **Flyway** pour versionner les schémas de base de données.
* [ ] **Refactoring (Single Responsibility) :** Réfléchir à l'ajout d'une couche `Model` dédiée pour soulager les `Services` actuels et améliorer la séparation des responsabilités.

### Frontend (Next.js) & Intégration

* [ ] Mettre en place un système de tickets (Jira, mapping US -> Tickets) pour le suivi des tâches frontend.
* [ ] Sécuriser les routes côté client (Middlewares Next.js).
* [ ] Gérer proprement le cycle de vie du token JWT dans le front (Expiration, rafraîchissement silencieux, déconnexion).

## 📡 Référence de l'API (Endpoints)

L'API complète est documentée et testable interactivement via **Swagger UI** (`http://localhost:8080/q/swagger-ui/`). 
*Note : À l'exception des routes d'authentification, toutes les requêtes nécessitent un header d'authentification valide : `Authorization: Bearer <token>`.*

### Authentification (`Auth Resource`)
| Méthode | Route | Description |
| :--- | :--- | :--- |
| `POST` | `/api/auth/login` | Connexion et génération du token JWT |
| `POST` | `/api/auth/register` | Création d'un nouveau compte utilisateur |

### Utilisateurs (`User Resource`)
| Méthode | Route | Description |
| :--- | :--- | :--- |
| `GET` | `/api/users/me` | Récupérer les informations de l'utilisateur connecté |
| `GET` | `/api/users` | Récupérer tous les utilisateurs (Nécessite droits Admin) |
| `PATCH` | `/api/users/me/name` | Modifier le nom de l'utilisateur connecté |
| `PUT` | `/api/users/me/password` | Changer le mot de passe de l'utilisateur connecté |
| `PATCH` | `/api/users/{userId}/role` | Modifier le rôle d'un utilisateur (Droits Admin) |

### Tableaux (`Board Resource`)
| Méthode | Route | Description |
| :--- | :--- | :--- |
| `GET` | `/api/boards` | Récupérer tous les tableaux de l'utilisateur connecté |
| `POST` | `/api/boards` | Créer un nouveau tableau |
| `PATCH` | `/api/boards/{boardId}` | Modifier les informations d'un tableau |
| `DELETE` | `/api/boards/{id}` | Supprimer un tableau de manière permanente |

### Colonnes (`Board Column Resource`)
| Méthode | Route | Description |
| :--- | :--- | :--- |
| `GET` | `/api/board-columns/board/{boardId}` | Récupérer toutes les colonnes d'un tableau spécifique |
| `POST` | `/api/board-columns` | Créer une nouvelle colonne |
| `PATCH` | `/api/board-columns/{columnId}` | Renommer ou mettre à jour une colonne |
| `DELETE` | `/api/board-columns/{columnId}` | Supprimer une colonne (et ses cartes enfants) |

### Cartes (`Card Resource`)
| Méthode | Route | Description |
| :--- | :--- | :--- |
| `GET` | `/api/cards/column/{columnId}` | Récupérer les cartes d'une colonne |
| `POST` | `/api/cards/column/{columnId}` | Créer une nouvelle tâche/carte |
| `PATCH` | `/api/cards/{cardId}` | Modifier le contenu d'une carte (titre, description) |
| `PATCH` | `/api/cards/{cardId}/move/{newColumnId}`| Déplacer une carte vers une autre colonne (Drag & Drop) |
| `DELETE` | `/api/cards/{cardId}` | Supprimer une carte |

## 🦸‍♂️ Pourquoi avoir choisi Quarkus ?

Bien que Spring Boot soit le leader historique du marché Java, le choix de **Quarkus** s'est imposé sur ce projet pour allier confort de développement et performances de pointe :

* **Une courbe d'apprentissage fluide (Spring-friendly) :** Quarkus réemploie la grande majorité des concepts et paradigmes de Spring (injection de dépendances CDI, architecture REST). La similarité est telle qu'une grande partie des connaissances et même de la documentation de Spring Boot sont transposables directement sous Quarkus.
* **Hibernate sous stéroïdes avec Panache :** Le projet utilise Hibernate pour l'ORM avec des migrations automatiques. Mais la véritable force de Quarkus réside dans sa surcouche **Panache**, qui rend l'ORM drastiquement plus accessible, lisible et rapide à implémenter.
* **Architecture flexible et Domain-Centric :** Le framework encourage une conception centrée sur le domaine métier (`Resources` pour l'API, `Services` pour la logique). Il offre une liberté totale pour découpler l'application en ajoutant des couches de **DTOs** (pour isoler les contrats d'API), des **Mappers/Pipes** (pour la conversion de données), et le pattern **Repository** (via `PanacheRepository`) pour isoler l'accès aux données.
* **Cloud-Native & Déploiement express :** Quarkus brille par son approche "Developer Joy". La génération d'images Docker et la création de certificats sont intégrées nativement. Il est possible de packager et tester un environnement de type production en un temps record, sans écrire de configurations laborieuses.
* **Performances Supersoniques :** Pensé dès le départ pour le Cloud et les microservices, Quarkus tient ses promesses face aux frameworks traditionnels : des temps de démarrage quasi instantanés et des temps de réponse **2 à 3 fois plus rapides** que Spring Boot, le tout avec une empreinte mémoire minimale.