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

#### How to run with Keycloak
```bash
 # run keycloak with port 9080, default username and password is admin / admin
 docker-compose -f src/main/docker/keycloak.yml up -d
 # run project with dev profile
 ./gradlew
```
- after booting services, access any endpoint on port 8080
- login with username `admin`, password `admin` in redirected login page.
- page will show up with token
- endpoint `/api` can be called with `GET` method and Bearer Authorization token
