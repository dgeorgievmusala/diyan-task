# 📘 Diyan API Test Project

Automated API Testing Framework built with **Java 21**, **TestNG**, **REST Assured**, **Allure**, and **GitHub Actions**.

---

## 👤 Author

**Diyan Georgiev**  
📧 diyan.georgiev@musala.com

---

## 🚀 Tech Stack

- Java 21
- Maven
- TestNG
- REST Assured
- Java Faker
- Gson & Jackson
- Allure Reports
- GitHub Actions CI

---

## 🛠️ Prerequisites

- Java **21** installed
- Maven 3.8+
- IDE (e.g. IntelliJ, Eclipse)
- Git

---

## 📦 Install Dependencies

```bash
mvn clean install
```

---

## 🧪 Run Tests

```bash
mvn clean test

Dev environment
 mvn clean test -Denv=dev

Prod environment
 mvn clean test -Denv=prod
 
```

To run specific TestNG groups:
There is no groups added at the moment but test can be separated in to groups like smoke, regression, flaky 
```bash
mvn clean test -Dgroups=
```

---

## 📊 Generate Allure Report

Serve the report locally:

```bash
allure serve target/allure-report

```

> Requires [Allure CLI](https://docs.qameta.io/allure/#_installing_a_commandline)

---

## ☁️ GitHub Actions

CI is automatically triggered on:

- Pushes to `main`
- Pull requests to `main`

It includes:
- Build & test using Java 21
- Upload of Allure test results in separate git page
https://dgeorgievmusala.github.io/diyan-task/#suites

---

## ⚙️ Java Version

This project targets **Java 21**.

Check your version:

```bash
java -version
```

Expected output:

```
java version "21" ...
```

If not installed, download it from: https://jdk.java.net/21/

---

## 📂 Project Structure

```
src/
├── main/
│   └── java/
│       └── utils/
│           └── ConfigLoader.java
├── test/
│   ├── java/
│   │   ├── tests/
│   │   ├── requests/
│   │   ├── models/
│   │   ├── payloads/
│   │   └── utils/
│   └── resources/
│       ├── authors.json
│       ├── books.json
│       └── config.properties
        └── testng.xml
| .gitignore
| pom.xml
| README.md
```

