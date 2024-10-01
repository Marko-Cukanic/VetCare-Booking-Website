# RMIT COSC2299 SEPT Major Project Instructions

**DO NOT MODIFY THIS FILE**

## IMPORTANT

- You are not allowed to make this repository public or share its content out side of the github organization created for this course.

## Setup your environment 
You will need to have in your system

- Java 17.0 or higher
- Apache Maven
- IDE or Editor

Other tools will be required to complete the project (e.g., Docker)

## Run Instructions

- Confirm you can run your application 
```shell
./mvnw spring-boot:run
```
- Open in your browser [http://localhost:8080](http://localhost:8080)

- You will see a page with the message "Whitelabel Error Page"

- You can use the lectorials material to start developing your web application.

Run with 
docker-compose up
if you make changes to code
docker-compose up --build

database details:
docker exec -it team-project-group-p08-07-db-1 mysql -u vetuser -p
use vetcaredb
username vetuser
password for mysql 1234

To assign Prescriptions as a vet
Sign in with admin account 
username admin@vetcare.com
password admin
Then go to prescription page and assign prescription should appear







