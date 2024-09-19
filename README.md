
# RMIT COSC2299 SEPT Major Project

# Group Information

## Group-P08-07

## Members
* Thomas Saleh (s4006031)
* Vincent Dao (s4008040)
* Marko Cukanic (s4007708)
* Tony Thai (s4002970)
* Kenny Le (s4002811)

## Records

* Github repository: https://github.com/cosc2299-2024/team-project-group-p08-07
* Scrum Board:  https://github.com/orgs/cosc2299-2024/projects/38/views/1
* Communication Tool: Microsoft Teams 

See [Instructions](INSTRUCTIONS.md)

Run with 
./mvnw clean spring-boot:run
to run
docker-compose up
if you make changes to code
docker-compose up --build

database details:
docker exec -it team-project-group-p08-07-db-1 mysql -u vetuser -p
use vetcaredb
username vetuser
password for mysql 1234

http://localhost:8080/assignPrescription

TO ASSIGN PRESCRIPTION WILL IMPLEMENT VET LOG IN