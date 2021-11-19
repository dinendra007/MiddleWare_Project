# PetStore Application

## Packaging and running the application

Build an uber-jar_, execute the following command:

    ./gradlew build -Dquarkus.package.type=uber-jar

To run the application:

To launch the test page, open your browser at the following URL

    http://localhost:8080/v1/pets

## Curl Commands to run the applications

### GET all pets
curl --location --request GET 'http://localhost:8080/v1/pets' \--data-raw ''

### GET pets by petID
curl --location --request GET 'http://localhost:8080/v1/pets/1' \--data-raw ''

### GET pets by name or age
curl --location --request GET 'http://localhost:8080/v1/pets/findByName/Dolly' </br>
curl --location --request GET 'http://localhost:8080/v1/pets/findByAge/3'

### UPDATE pets
curl --location --request PUT 'http://localhost:8080/v1/pets/updatePet/1' \
--header 'Content-Type: application/json' \
--data-raw ' {
        "petAge": 3,
        "petId": 8,
        "petName": "Dolly",
        "petType": "Dog"
    }'

### Delete Pets
curl --location --request DELETE 'http://localhost:8080/v1/pets/deletePet/8' \--data-raw ''
