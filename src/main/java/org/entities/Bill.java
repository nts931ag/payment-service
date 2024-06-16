package org.entities;

import java.time.LocalDate;

import org.enums.BillState;
import org.enums.BillType;


/**
 *
 * @author ngus
 */
public class Bill {
    private static int nextId = 1;
    private int id;
    private BillType type;
    private double amount;
    private LocalDate dueDate;
    private BillState state;
    private String provider;

    private Payment payment;

    public Bill(BillType type, double amount, LocalDate dueDate, String provider) {
        this.id = nextId++;
        this.type = type;
        this.amount = amount;
        this.dueDate = dueDate;
        this.state = BillState.NOT_PAID;
        this.provider = provider;
    }

    public int getId() { return id; }
    public BillType getType() { return type; }
    public void setType(BillType type) {this.type = type; }
    public double getAmount() { return amount; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public LocalDate getDueDate() { return dueDate; }
    public BillState getState() { return state; }
    public void setState(BillState state) { this.state = state; }
    public void setProvider(String provider) { this.provider = provider; }
    public String getProvider() { return provider; }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    @Override
    public String toString() {
        return "Bill No. " + id + ". " + type +
                "\tAmount: " + amount +
                "\tDue Date: " + dueDate +
                "\tState: " + state +
                "\tProvider: " + provider + "\n";
    }
}