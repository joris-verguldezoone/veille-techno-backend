# Gitflow classique (Vincent Driessen)
## Gitflow et Bonnes Pratiques de Commits 

Ce document décrit une organisation Git simple et efficace (Gitflow simplifié),
ainsi que les bonnes pratiques pour rédiger des commits clairs et professionnels
sur GitHub.

---

## 1) Principes généraux

- Une branche = un objectif clair
- Un commit = une seule idée
- Pas de commit massif “tout-en-un”
- Toujours passer par des Pull Requests (PR)
- Historique lisible et maintenable dans le temps

---

## 2) Branches principales

### `main`
- Branche **stable**
- Contient uniquement du code prêt pour la production
- Aucun commit direct
- Mise à jour uniquement via Pull Request

### `develop`
- Branche principale de développement
- Toutes les fonctionnalités y sont fusionnées
- Peut être instable temporairement

---

## 3) Branches temporaires

| Type     | Convention de nommage        | Usage |
|--------|------------------------------|------|
| Feature | `feature/nom-fonctionnalite` | Nouvelle fonctionnalité |
| Fix  | `fix/nom-du-bug`          | Correction de bug |
| Release | `release/x.y.z`              | Préparation d’une release |
| Hotfix  | `hotfix/urgent`              | Correctif rapide en production |
| Documentation  | `docs/nom-des-docs`              | Ajout de process ou fichiers documentaire |
| Chore  | `chore/nom-des-fonctionnalités`              | Ajout de structure de framework ou de librairies |

### Règles
- Une branche = une seule fonctionnalité ou correction
- La branche est supprimée après le merge

---

## 4) Convention de commits (Conventional Commits)

### Format recommandé
`<type>(<scope optionnel>): <description courte>`

**Exemple :** `feat(api): ajout de la route de connexion`

### Les types de commits standards

| Type | Description | Exemple |
|---|---|---|
| **`feat`** | Ajout d'une nouvelle fonctionnalité | `feat(auth): ajout du bouton de connexion` |
| **`fix`** | Correction d'un bug | `fix(nav): correction du lien brisé` |
| **`docs`** | Modification de la documentation | `docs(readme): ajout des instructions d'installation` |
| **`style`** | Formatage du code (espaces, virgules, etc.) sans impact sur l'exécution | `style(css): formatage du fichier selon les règles ESLint` |
| **`refactor`**| Réécriture du code sans ajouter de fonctionnalité ni corriger de bug | `refactor(auth): simplification de la logique de validation` |
| **`perf`** | Amélioration des performances | `perf(db): ajout d'un index pour accélérer la requête` |
| **`test`** | Ajout, modification ou correction de tests | `test(user): ajout des tests unitaires pour la création d'utilisateur` |
| **`chore`** | Tâches de maintenance, mises à jour d'outils, structure | `chore(deps): mise à jour de la librairie React` |
| **`build`** | Modification du système de build ou des dépendances (Maven, npm, etc.) | `build(npm): ajout du script de déploiement` |
| **`ci`** | Modification des fichiers d'intégration continue (GitHub Actions, GitLab CI) | `ci(github): ajout du workflow de test automatique` |
| **`revert`** | Annulation stricte d'un commit précédent | `revert: "feat(auth): ajout de la connexion Google"` |

---

## 5) La règle des 5 commits par branche

### Objectif
- Découper une fonctionnalité en plusieurs étapes claires
- Faciliter la revue de code
- Permettre un revert simple
- Garder un historique propre

### Exemple : `feature/login`

1. `feat(auth): create login form`
2. `feat(auth): connect login form to API`
3. `fix(auth): correct password validation`
4. `test(auth): add unit tests for login`
5. `docs(auth): update README for login`

> Ce n’est pas une règle stricte, mais un **bon objectif** à viser.

---

## 6) Workflow Gitflow concret

### Initialisation

```bash
git init
git checkout -b main
git commit -m "chore: initial commit"
git checkout -b develop
```

### exemple 

git add .
git commit -m "feat(auth): create login form"

git add .
git commit -m "feat(auth): connect login form to API"

git add .
git commit -m "test(auth): add login tests" 

### A retenir

main → production

develop → développement

feature/* → nouvelles fonctionnalités

bugfix/* → corrections

Commits petits, clairs, typés

~5 commits par fonctionnalité

Pull Requests obligatoires

### Bonnes habitudes 

- Tester avant de commit

- Relire ses messages de commit

- Ne jamais commit du code cassé

- Préférer plusieurs petits commits à un gros