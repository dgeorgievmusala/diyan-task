# REST API Automation Project

## Project Overview

This project is built for automating the REST API testing of the Books and Authors endpoints available at Fake REST API.
It uses RESTAssured for API requests, TestNG for test management, and Allure Reports for generating detailed test
reports. The project follows SOLID principles, ensuring scalability, flexibility, and maintainability.

## Key Features

* **Modular Structure:** The project is structured to follow the Single Responsibility Principle (SRP) and other SOLID
  principles, with clearly defined responsibilities for configurations, models, repositories, requests, and
  verifications.
* **Multi-Environment Support:** Configurations are environment-specific and stored in separate application.properties
  files for each environment.
* **Request Encapsulation:** Each API request (GET, POST, PUT, DELETE) is encapsulated within a dedicated class in the
  requests package.
* **Test Data Handling:** The repository pattern is used to provide the test data needed for the test execution.
* **Parameterized Tests:** Data providers are used to run parameterized tests for improved coverage and reusability.
* **Allure Reports:** The project integrates with Allure to provide detailed reports on test results.

## Project Structure

```src
├── main
│   ├── java
│   │   ├── configs              # Configurations for different environments
│   │   ├── models               # POJO models representing API data structures
│   │   ├── repositories         # Repositories to provide test data for tests
│   │   ├── requests             # Classes encapsulating GET, POST, PUT, DELETE requests
│   └── resources
│       
├── test
├── java
│   ├── constants            # Constants used in tests (e.g., endpoints, status codes)
│   ├── data_providers       # Data providers for TestNG parameterized tests
│   ├── tests                # Test classes for Books and Authors API
│   ├── verifications        # Verification logic for assertions and validation
└── resources
    ├── data                 # Default valid existent Books and Authors
    ├──testng.xml           # TestNG suite configuration
    └── application.properties  # Environment-specific configurations (URL, etc.)
```

## Key Folders and Files

* **configs/**: Contains configuration files (like application.properties) to store environment-specific URLs and other
  settings.
* **models/**: Contains POJO classes representing the data models (Books, Authors, etc.), used for serialization and
  deserialization of API responses.
* **repositories/**: Implements the repository pattern, used to provide data required for testing, like test data for
  POST or PUT requests.
* **requests/**: This folder contains classes that encapsulate the logic for API requests (GET, POST, PUT, DELETE). Each
  method is mapped to a specific API endpoint.
* **constants/**: Contains constant values for various API resources like error messages.
* **data_providers/**: Contains TestNG DataProviders for parameterized testing, facilitating multiple sets of test data
  for different test cases.
* **tests/**: Contains the actual test classes which use TestNG framework to execute tests on the Books and Authors
  endpoints.
* **verifications/**: Includes helper classes for performing assertions and other verification logic in the tests.

## Dependencies

### Maven Dependencies:

* **RESTAssured:** For sending and verifying HTTP requests.
* **TestNG:** For test execution and management.
* **Allure:** For generating rich and interactive test reports.
* **Jackson:** For serializing and deserializing Java objects from/to JSON format.
* **Lombok:** To reduce boilerplate code by generating getters, setters, constructors, etc.

Here is a snippet of the pom.xml with the updated dependencies:

```
<dependencies>
    <!-- REST Assured for API testing -->
    <dependency>
        <groupId>io.rest-assured</groupId>
        <artifactId>rest-assured</artifactId>
        <version>4.4.0</version>
    </dependency>
    
    <!-- TestNG for test management -->
    <dependency>
        <groupId>org.testng</groupId>
        <artifactId>testng</artifactId>
        <version>7.4.0</version>
        <scope>test</scope>
    </dependency>

    <!-- Allure Reports for Test Reporting -->
    <dependency>
        <groupId>io.qameta.allure</groupId>
        <artifactId>allure-testng</artifactId>
        <version>2.14.0</version>
    </dependency>

    <!-- Jackson for JSON Processing -->
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.12.1</version>
    </dependency>

    <!-- Lombok for reducing boilerplate code -->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <version>1.18.20</version>
        <scope>provided</scope>
    </dependency>
</dependencies>

<!-- Maven Allure Plugin for generating reports -->
<build>
    <plugins>
        <plugin>
            <groupId>io.qameta.allure</groupId>
            <artifactId>allure-maven</artifactId>
            <version>2.10.0</version>
            <configuration>
                <suiteXmlFiles>
                    <suiteXmlFile>src/test/resources/testng.xml</suiteXmlFile>
                </suiteXmlFiles>
                <systemProperties>
                    <property>
                        <name>allure.results.directory</name>
                        <value>${project.build.directory}/allure-results</value>
                    </property>
                </systemProperties>
            </configuration>
        </plugin>
    </plugins>
</build>

```

## How to Run the Project

### Prerequisites:

**Java:** Version 11 or higher

**Maven:** For project build and dependency management

**Allure CLI:** For generating and viewing test reports

### Setup Instructions:

1. Clone the Repository:

```
git clone https://github.com/indiraAleaga/deysi-task.git
```

2. Navigate to the Project Directory:

```
cd deysi-task
```

### Running Tests:

1. Execute Tests with Maven:

```
mvn clean test
```

This command will compile the project, execute the TestNG tests, and generate Allure results in the *
*target/allure-results* directory.

2. Generate and Serve Allure Report:

```
mvn allure:serve
```

This command uses the Maven Allure plugin to generate the report from the results and automatically open it in your
default web browser.

# GitHub Actions CI Pipeline

This project also integrates with GitHub Actions to automate the execution of tests on every **push** or **pull**
request to the **main** branch or **feature** branches (feat/*). The CI pipeline runs the tests, generates Allure
reports, and deploys them to GitHub Pages.

Here is the pipeline configuration:

```
name: API Tests

on:
  push:
    branches:
      - main
      - feat/*
  pull_request:
    branches:
      - main

jobs:
  test:
    runs-on: ubuntu-latest

    steps:
      - name: Checkout repository
        uses: actions/checkout@v2

      - name: Set up JDK 11
        uses: actions/setup-java@v2
        with:
          java-version: '11'
          distribution: 'adopt'

      - name: Cache Maven dependencies
        uses: actions/cache@v2

```
