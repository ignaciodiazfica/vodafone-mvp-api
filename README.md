# Vodafone MVP API

**Description**

This is a Spring Boot application that provides a REST API to get the temperature from OpenMeteo API.

## Requirements

- Java 17
- Docker
- Docker Compose
- MongoDB
- Kafka
- Maven
## Installation
1. **Clone the repository**
```bash
git clone https://github.com/ignaciodiaz/vodafone-mvp-api.git
```
2. **Build the project**
```bash
cd vodafone-mvp-api
mvn clean package -DskipTests
```
3. **Run the application**
```bash
docker-compose up --build
```
4. **Access the application**
- Open your browser and go to `http://localhost:8080/api-docs`, you should see the Swagger UI with the API documentation.

## Usage
### Endpoints
The application provides the following endpoints:

- **GET /api/v1/temperature**
  - Description: Get the temperature from OpenMeteo API.
  - Parameters:
    - **lat**: Latitude of the location.
    - **lon**: Longitude of the location.
    - Example: `http://localhost:8080/api/v1/temperature?lat=30.0&lon=20.0`
  - Response:
    - Status Code: 200
    - Body:
      ```json
      {
        "longitude": 20.0,
        "latitude": 30.0,
        "temperature": 10.5
      }
      ```
      
- **DELETE /api/v1/temperature**
    - Description: Delete the temperature from the cache.
    - Parameters:
    - **lat**: Latitude of the location.
    - **lon**: Longitude of the location.
    - Example: `http://localhost:8080/api/v1/temperature?lat=30.0&lon=20.0`
    - Response:
      - Status Code: 204
      - Body: No content

### Testing
To test the application, you can run the following command:
```bash
mvn test
```
