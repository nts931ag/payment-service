package org.entities;

import java.time.LocalDate;

import org.enums.PaymentState;


/**
 *
 * @author ngus
 */
public class Payment {
    private static int nextId = 1;
    private Integer id;
    private Bill bill;
    private Double amount;

    private LocalDate paymentDate;
    private PaymentState state;

    public Payment(Bill bill, Double amount, LocalDate paymentDate, PaymentState state) {
        this.id = nextId++;
        this.bill = bill;
        this.state = state;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public PaymentState getState() {
        return state;
    }

    public void setState(PaymentState state) {
        this.state = state;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }
}
