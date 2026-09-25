### MVN

mvn quarkus:add-extension -Dextensions="quarkus-smallrye-openapi"
openapi : localhost:8080/swagger-ui
http://localhost:8080/q/swagger-ui/
mvn quarkus:add-extension -Dextensions="quarkus-rest-jackson"
mvn quarkus:add-extension -Dextensions="quarkus-hibernate-validator" // dto
quarkus extension add quarkus-container-image-docker // extension docker automatique
mvn quarkus:add-extension -Dextensions='smallrye-jwt,smallrye-jwt-build' // JWT
### Git
git config pull.rebase false

### VsStudioCode quand y'a un bug d'affichage d'erreur (import / bug)
cmd + shit + p:  Clean Java Language Server Workspace
cmd + shit + p:  Java: Update Project
cmd + . pour que vscode propose lui meme les dépendances java, afin que j'affecte les dépendances de jakarta plutot que sprint ou une autre version de java


### Terminal Java & mvn
mvn clean compile

### Références
[Quarkus datasource pgsql](https://quarkus.io/guides/datasource/)
[Quarkus hibernate + panache & dependances](https://quarkus.io/guides/hibernate-orm-panache/)
[Quarkus installation](https://quarkus.io/guides/getting-started/)
[Quarkus analogie spring boot](https://quarkus.io/guides/cdi/)
[Quarkus API REST](https://quarkus.io/guides/rest/)
[hibernate](https://hibernate.org/)
[hibernate repository (apparement le css c'est pas pour les dev)](https://jakarta.ee/specifications/data/1.0/apidocs/jakarta.data/module-summary.html)
[hibernate tags](https://docs.hibernate.org/orm/6.6/javadocs/org/hibernate/boot/model/naming/ImplicitNamingStrategy.html)
[Quarkus basic auth](https://quarkus.io/guides/security-getting-started-tutorial/)
[Quarkus hash](https://quarkus.io/guides/security-jpa/)
[Java http standards](https://docs.oracle.com/javaee/7/api/javax/ws/rs/core/Response.Status.html) 
[Java JWT functions](https://docs.spring.io/spring-security/site/docs/current/api/org/springframework/security/oauth2/jwt/Jwt.html)