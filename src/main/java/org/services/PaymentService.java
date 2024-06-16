package org.services;

import java.time.LocalDate;
import java.util.List;

import org.entities.Bill;
import org.entities.Payment;
import org.entities.User;

/**
 *
 * @author ngus
 */
public interface PaymentService {
    Bill findBillById(User user, Integer billId);
    void addBalanceForUser(User user, double amount);
    void payBill(User user, int billId);
    void payBills(User user, List<Integer> billIds);
    List<Bill> listBill(User user);
    List<Payment> listPayment(User user);
    List<Bill> listBillsSortedByDueDate(User user);
    List<Bill> searchBillByProvider(User user, String provider);
    void schedulePaymentByBillId(User user, Integer billId, LocalDate scheduledDate);
}
