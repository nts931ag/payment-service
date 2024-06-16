package org.services.impl;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.entities.Bill;
import org.entities.Payment;
import org.entities.User;
import org.enums.BillState;
import org.enums.PaymentState;
import org.services.PaymentService;


/**
 *
 * @author ngus
 */
public class PaymentServiceImpl implements PaymentService {


    @Override
    public Bill findBillById(User user, Integer billId) {
        for (Bill bill : user.getBillList()) {
            if (bill.getId() == billId) {
                return bill;
            }
        }
        return null;}

    public void addBalanceForUser(User user, double amount) {
        user.addBalance(amount);
        System.out.println("Your available balance: " + user.getBalance());
    }

    public void payBill(User user, int billId) {
        Bill bill = findBillById(user, billId);
        if (bill != null && bill.getState() == BillState.NOT_PAID && user.getBalance() >= bill.getAmount()) {
            user.withdrawBalance(bill.getAmount());
            Payment payment = new Payment(bill, bill.getAmount(), LocalDate.now(), PaymentState.PROCESSED);
            bill.setPayment(payment);
            bill.setState(BillState.PAID);
            System.out.printf("Payment has been completed for Bill with id %s.\n" +
                    "Your current balance is: %s\n", billId, user.getBalance());
        } else if (bill == null) {
            System.out.println("Sorry! Not found a bill with such id");
        } else if (bill.getState() == BillState.PAID) {
            System.out.printf("Sorry! bill with Id %s is already paid", billId);
        } else {
            System.out.println("Sorry! Not enough balance to pay this bill");
        }
    }

    public void payBills(User user, List<Integer> billIds) {
        List<Bill> billNeedToPayList = user.getBillList().stream()
                .filter(bill -> billIds.contains(bill.getId()))
                .collect(Collectors.toList());

        if (checkPossibleToPayAllBill(user.getBalance(), billNeedToPayList)) {
            for (Bill bill : billNeedToPayList) {
                payBill(user, bill.getId());
            }
        } else {
            System.out.println("Sorry! Not enough fund to proceed with payment.");
        }

    }

    private boolean checkPossibleToPayAllBill(double userBalance, List<Bill> bills) {
        double totalAmountBill = bills.stream().map(Bill::getAmount).reduce(0.0, Double::sum);
        return userBalance >= totalAmountBill;
    }

    public List<Bill> listBill(User user) {
        return user.getBillList();
    }

    public List<Payment> listPayment(User user) {
        return user.getBillList().stream()
                .map(Bill::getPayment)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public List<Bill> listBillsSortedByDueDate(User user) {
        return user.getBillList().stream()
                .filter(bill -> bill.getState() == BillState.NOT_PAID)
                .sorted(Comparator.comparing(Bill::getDueDate)).collect(Collectors.toList());
    }

    public List<Bill> searchBillByProvider(User user, String provider) {
        return user.getBillList().stream()
                .filter(bill -> bill.getProvider().equalsIgnoreCase(provider)).collect(Collectors.toList());
    }

    public void schedulePaymentByBillId(User user, Integer billId, LocalDate scheduledDate) {
        Bill bill = findBillById(user, billId);

        if (bill == null) {
            System.out.println("Sorry! Not found a bill with such id");
            return;
        } else if (bill.getState() == BillState.PAID) {
            System.out.println("Sorry! Bill is already paid!");
            return;
        }

        Payment payment = bill.getPayment();
        if (payment == null) {
            System.out.println("Sorry! Not found a payment for bill with such id");
        } else {
            payment.setState(PaymentState.SCHEDULED);
            payment.setPaymentDate(scheduledDate);
            System.out.printf("Payment for bill id %s is scheduled on %s%n", bill.getId(), scheduledDate);
        }
    }
}
