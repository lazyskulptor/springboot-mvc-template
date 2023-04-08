# Spring Boot Web MVC Template

### How to package
 ```bash  
 ./gradlew bootJar # package as jar
 
 ./gradlew jibDockerBuild # build container image in local
 
 ./gradlew jib # build container image in remote place
 ```

### How to run
```bash
./gradlew # will run with h2 database

# When Container is created
# Environment Value referenced in app.yml file
docker run -itd --name boot-demo lazyskulptor/boot-template # run with h2 database

docker-compose -f src/main/docker/app.yml up -d # run with mysql
```

---
## Security Integration
- [with user table and JWT](https://github.com/lazyskulptor/springboot-mvc-template/tree/feat-security-jwt)
- [with Oauth2 by Keycloak](https://github.com/lazyskulptor/springboot-mvc-template/tree/feat-security-oauth2)
