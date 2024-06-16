package org.entities;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author ngus
 */
public class User {
    private double balance;
    private List<Bill> billList;

    public User() {
        this.balance = 0.0;
        this.billList = new ArrayList<>();
    }

    public double getBalance() { return balance; }
    public void addBalance(double amount) { this.balance += amount; }
    public void withdrawBalance(double amount) {this.balance -= amount;}
    public List<Bill> getBillList() { return billList; }

    public void addBill(Bill bill) {
        billList.add(bill);
    }

    public void removeBill(int billId) {
        billList.removeIf(bill -> bill.getId() == billId);
    }
}