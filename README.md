# Route Finder Application

## Description
This is a simple Spring Boot service that calculates the shortest possible land route from one country to another based on border information. The service exposes a REST endpoint that accepts country codes and returns a list of countries representing the route. If no land route is possible, the service returns an error.

## Features
- Find the shortest land route between two countries.
- Returns a single valid route, or an error if no route exists.
- Countries are identified by their `cca3` code.

## Prerequisites
- Java 17+
- Maven 3.8+

## Getting Started

### Clone the Repository
```bash
git clone https://github.com/AndRRei/route-calculator.git
cd route-calculator
```

### Build the Application
```bash
mvn clean install
```

### Run the Application
```bash
mvn spring-boot:run
```


Test the Application Once the application is running, you can test it by sending HTTP GET requests to the /routing/{origin}/{destination} endpoint.

### Example request:
```bash
curl http://localhost:8080/routing/CZE/ITA
```

### Example response:
```bash
{
"route": ["CZE", "AUT", "ITA"]
}
```
### Endpoints
```bash
GET /routing/{origin}/{destination}
```

### Technologies Used
Spring Boot
Maven
Jackson (for JSON parsing)
