# Bill Payment System

This project is a bill payment system that allows users to manage their bills and make payments. It is implemented in Java and uses JUnit for unit testing.

## Project Structure

The project contains the following main components:

- **Bill**: Represents a bill with type, amount, due date, provider, and state.
- **Payment**: Represents a payment with a bill, amount, payment date, and state.
- **User**: Represents a user with a balance and a list of bills.
- **PaymentServiceImpl**: Implements the payment service with methods to add balance, pay bills, list bills, and schedule payments.

## Classes and Methods

### Bill

```java
public class Bill {
    // Constructors
    public Bill(BillType type, double amount, LocalDate dueDate, String provider);

    // Getters and Setters
    public BillType getType();
    public double getAmount();
    public LocalDate getDueDate();
    public BillState getState();
    public String getProvider();
    public void setAmount(double amount);
    public void setDueDate(LocalDate dueDate);
    public void setState(BillState state);
    public void setProvider(String provider);
}
Payment
java
Copy code
public class Payment {
    // Constructors
    public Payment(Bill bill, double amount, LocalDate paymentDate, PaymentState state);

    // Getters and Setters
    public Bill getBill();
    public double getAmount();
    public LocalDate getPaymentDate();
    public PaymentState getState();
    public void setAmount(double amount);
    public void setPaymentDate(LocalDate paymentDate);
    public void setState(PaymentState state);
}
User
java
Copy code
public class User {
    // Constructors
    public User();

    // Methods
    public void addBalance(double amount);
    public void withdrawBalance(double amount);
    public double getBalance();
    public void addBill(Bill bill);
    public void removeBill(UUID billId);
    public List<Bill> getBillList();
}
PaymentServiceImpl
java
Copy code
public class PaymentServiceImpl implements PaymentService {
    // Methods
    public void addBalanceForUser(User user, double amount);
    public void payBill(User user, UUID billId);
    public void payBills(User user, List<UUID> billIds);
    public List<Bill> listBillsSortedByDueDate(User user);
    public List<Bill> searchBillByProvider(User user, String provider);
    public void schedulePaymentByBillId(User user, UUID billId, LocalDate scheduledDate);
}
Unit Tests
Unit tests are written using JUnit 5. The tests cover the core functionalities of each class and method. They can be found in the src/test/java directory.

Running Tests
To run the tests, use your IDE's built-in test runner, or use Maven from the command line:

bash
Copy code
mvn test
Getting Started
Prerequisites
Maven
Building the Project
Clone the repository:

bash
Copy code
git clone <repository-url>
cd <repository-directory>
Build the project using Maven:

bash
Copy code
mvn clean install
Running the Application
Run the application using your IDE or from the command line:

bash
Copy code
mvn exec:java -Dexec.mainClass="com.example.Main"
