# Bill Payment System

This project is a bill payment system that allows users to manage their bills and make payments. It is implemented in Java and uses JUnit for unit testing.

## Project Structure

The project contains the following main components:

- **Bill**: Represents a bill with type, amount, due date, provider, and state.
- **Payment**: Represents a payment with a bill, amount, payment date, and state.
- **User**: Represents a user with a balance and a list of bills.
- **PaymentServiceImpl**: Implements the payment service with methods to add balance, pay bills, list bills, and schedule payments.

Unit Tests
Unit tests are written using JUnit. The tests cover the core functionalities of each class and method. They can be found in the src/test/java directory.

Running Tests
To run the tests, use your IDE's built-in test runner, or use Maven from the command line:

mvn test
Getting Started
Prerequisites
Maven
Building the Project
Clone the repository:

git clone <repository-url>
cd <repository-directory>
Build the project using Maven:

mvn clean install
Running the Application
Run the application using your IDE or from the command line:

mvn exec:java -Dexec.mainClass="com.example.Main"
