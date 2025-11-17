Banking System Simulator (Monolithic Version)

A Java Spring Boot application simulating a banking system with account management and transaction processing.

Table of Contents

Project Overview

Architecture

API Endpoints

Setup Instructions

Technologies Used

License

Project Overview

This project is a monolithic banking system simulator built using Spring Boot and MongoDB.
It supports:

Account creation, update, and deletion

Deposit, withdrawal, and transfer transactions

Transaction history retrieval

Exception handling and validation

Architecture

The project follows a layered architecture:

Controller Layer
    └── Handles API requests and responses
Service Layer
    └── Business logic (AccountService, TransactionService)
Repository Layer
    └── Database operations (MongoDB)
Model Layer
    └── Domain models (Account, Transaction)


Flow Example (Account Update):
PATCH /api/account/update → Controller → Service → Repository → Database → Service → Controller → Response

| **Endpoint**                                | **Method** | **Request Body**                                                    | **Response**                                                                                                      | **Description**                         |
| ------------------------------------------- | ---------- | ------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------- | --------------------------------------- |
| `/api/account/create`                       | POST       | `{ "holderName": "John Doe" }`                                      | `201 Created`<br>`{ "accountNumber": "ACC123", "holderName": "John Doe", "balance": 0 }`                          | Create a new account                    |
| `/api/account/update`                       | PATCH      | `{ "accountNumber": "ACC123", "holderName": "John Smith" }`         | `200 OK`<br>`{ "accountNumber": "ACC123", "holderName": "John Smith", "balance": 1000 }`                          | Update account details                  |
| `/api/account/{accountNumber}`              | DELETE     | N/A                                                                 | `204 No Content` or `404 Not Found`                                                                               | Delete an account (soft delete) 
| `/api/account/{accountNumber}`              | GET        | N/A                                                                 | `200 OK`<br>`{ "accountNumber": "ACC123", "holderName": "John Doe", "balance": 1000 }`                            | Retrieve account details                |
| `/api/account/{accountNumber}/transactions` | GET        | N/A                                                                 | `200 OK`<br>`[ { "transactionId": "TXN001", "type": "DEPOSIT", "amount": 1000, "date": "2025-11-17T12:34:56" } ]` | Retrieve all transactions of an account |
| `/api/account/deposit`                      | POST       | `{ "accountNumber": "ACC123", "amount": 500 }`                      | `200 OK`<br>`{ "transactionId": "TXN002", "type": "DEPOSIT", "amount": 500 }`                                     | Deposit money into an account           |
| `/api/account/withdraw`                     | POST       | `{ "accountNumber": "ACC123", "amount": 200 }`                      | `200 OK`<br>`{ "transactionId": "TXN003", "type": "WITHDRAW", "amount": 200 }`                                    | Withdraw money from an account          |
| `/api/account/transfer`                     | POST       | `{ "fromAccount": "ACC123", "toAccount": "ACC124", "amount": 300 }` | `200 OK`<br>`{ "transactionId": "TXN004", "type": "TRANSFER", "amount": 300 }`                                    | Transfer money between accounts         |


Note: Adjust request/response examples according to your implementation (soft delete, balances, etc.).

Setup Instructions
Prerequisites

Java 17+

Maven

MongoDB

Steps

Clone the repository:

git clone https://github.com/Ehshanulla/BankingSystemSimulatorMonolithicVersion.git
cd BankingSystemSimulatorMonolithicVersion


Build the project:

mvn clean install


Run the application:

mvn spring-boot:run


The API will be available at:

http://localhost:8080/api/account


Configure MongoDB connection in application.properties if needed:

spring.data.mongodb.uri=mongodb://localhost:27017/bankingdb

Technologies Used

Spring Boot

Spring Data MongoDB

Java 17

Maven

MongoDB
