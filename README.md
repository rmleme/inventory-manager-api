# inventory-manager-api

## Instructions

**Requirements:** docker, docker-compose, JDK 8, Maven.

1. Download the source code to your local box. Linux is recommended. 
2. Build the application with maven: `mvn install` (in the same directory of `pom.xml`).
3. Copy the generated file (`api-1.1.jar`) to `docker/build/`
4. Run `build.sh`. A new Docker image will be created.
5. Go to directory `docker/` and run `docker-compose up -d`
6. Three containers will be started: one for MySQL, another for this application and the third for [Product Catalog API](https://github.com/rmleme/product-catalog-api).
7. Once all containers are ready, the APIs may be accessed through the base URL `http://<IP>:9090/v1/api/inventory/items`
