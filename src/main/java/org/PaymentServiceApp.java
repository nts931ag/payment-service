package org;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.entities.Bill;
import org.entities.Payment;
import org.entities.User;
import org.enums.BillType;
import org.enums.PaymentState;
import org.services.PaymentService;
import org.services.impl.PaymentServiceImpl;


/**
 *
 * @author ngus
 */
public class PaymentServiceApp {
    private static User user = new User();

    private static PaymentService paymentService = new PaymentServiceImpl();
    private static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static void initData() {
        BillType type1 = BillType.ELECTRIC;
        double amount1 = 200000;
        LocalDate dueDate1 = LocalDate.parse("25/10/2020", dateFormatter);
        String provider1 = "EVN HCMC";
        Bill bill1 = new Bill(type1, amount1, dueDate1, provider1);
        Payment payment1 = new Payment(bill1, amount1, dueDate1, PaymentState.PENDING);
        bill1.setPayment(payment1);


        BillType type2 = BillType.WATER;
        double amount2 = 175000;
        LocalDate dueDate2 = LocalDate.parse("30/10/2020", dateFormatter);
        String provider2 = "SAVACO HCMC";
        Bill bill2 = new Bill(type2, amount2, dueDate2, provider2);
        Payment payment2 = new Payment(bill2, amount2, dueDate2, PaymentState.PENDING);
        bill2.setPayment(payment2);


        BillType type3 = BillType.INTERNET;
        double amount3 = 800000;
        LocalDate dueDate3 = LocalDate.parse("30/11/2020", dateFormatter);
        String provider3 = "VNPT";
        Bill bill3 = new Bill(type3, amount3, dueDate3, provider3);
        Payment payment3 = new Payment(bill3, amount3, dueDate3, PaymentState.PENDING);
        bill3.setPayment(payment3);

        user.getBillList().addAll(Arrays.asList(bill1, bill2, bill3));

    }

    public static void main(String[] args) {

        initData();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("$ ");
            String input = scanner.nextLine();
            if (input.trim().isEmpty()) continue;

            String[] commandParts = input.split(" ");
            String command = commandParts[0].toUpperCase();

            switch (command) {
                case "CASH_IN":
                    if (commandParts.length > 1) {
                        double amount = Double.parseDouble(commandParts[1]);
                        paymentService.addBalanceForUser(user, amount);
                    }
                    break;
                case "CREATE_BILL": {
                    try {
                        BillType type = BillType.fromValue(commandParts[1]);
                        double amount = Double.parseDouble(commandParts[2]);
                        LocalDate dueDate = LocalDate.parse(commandParts[3], dateFormatter);
                        String provider = Stream.of(commandParts).skip(4).collect(Collectors.joining(" "));
                        Bill bill = new Bill(type, amount, dueDate, provider);
                        Payment payment = new Payment(bill, amount, dueDate, PaymentState.PENDING);
                        bill.setPayment(payment);
                        user.addBill(bill);
                        break;
                    } catch (Exception exception) {
                        System.out.println("INVALID INPUT. FOLLOWING THE VALID INPUT: CREATE_BILL yourBillType yourAmount yourDueDate(dd/mm/yyyy) yourProvider");
                        break;
                    }
                }
                case "DELETE_BILL": {
                    Integer billId = Integer.parseInt(commandParts[1]);
                    Bill bill = paymentService.findBillById(user, billId);
                    if (bill == null) {
                        System.out.printf("Bill id %s is not exist", billId);
                        break;
                    }
                    user.removeBill(bill.getId());
                    break;
                }
                case "UPDATE_TYPE_BILL": {
                    Integer billId = Integer.parseInt(commandParts[1]);
                    Bill bill = paymentService.findBillById(user, billId);
                    if (bill == null) {
                        System.out.printf("Bill id %s is not exist", billId);
                        break;
                    }
                    BillType type = BillType.fromValue(commandParts[2]);
                    bill.setType(type);
                    System.out.printf("Bill id %s is updated successfully", billId);
                    break;
                }
                case "UPDATE_AMOUNT_BILL": {
                    Integer billId = Integer.parseInt(commandParts[1]);
                    Bill bill = paymentService.findBillById(user, billId);
                    if (bill == null) {
                        System.out.printf("Bill id %s is not exist", billId);
                        break;
                    }
                    Double amount = Double.parseDouble(commandParts[2]);
                    bill.setAmount(amount);
                    System.out.printf("Bill id %s is updated successfully", billId);
                    break;
                }
                case "UPDATE_DUE_DATE_BILL": {
                    Integer billId = Integer.parseInt(commandParts[1]);
                    Bill bill = paymentService.findBillById(user, billId);
                    if (bill == null) {
                        System.out.printf("Bill id %s is not exist", billId);
                        break;
                    }
                    LocalDate dueDate = LocalDate.parse(commandParts[2], dateFormatter);
                    bill.setDueDate(dueDate);
                    System.out.printf("Bill id %s is updated successfully", billId);
                    break;
                }
                case "UPDATE_PROVIDER_BILL": {
                    Integer billId = Integer.parseInt(commandParts[1]);
                    Bill bill = paymentService.findBillById(user, billId);
                    if (bill == null) {
                        System.out.printf("Bill id %s is not exist", billId);
                        break;
                    }
                    bill.setProvider(commandParts[2]);
                    System.out.printf("Bill id %s is updated successfully", billId);
                    break;
                }
                case "SEARCH_BILL": {
                    Bill bill = paymentService.findBillById(user, Integer.parseInt(commandParts[1]));
                    if (bill == null) {
                        System.out.printf("Bill id %s is not exist", Integer.parseInt(commandParts[1]));
                        break;
                    }
                    System.out.println(bill);
                    break;
                }
                case "SEARCH_BILL_BY_PROVIDER":
                    if (commandParts.length > 1) {
                        String provider = Arrays.stream(commandParts).skip(1).collect(Collectors.joining(" "));
                        List<Bill> bills = paymentService.searchBillByProvider(user, provider);
                        System.out.printf("%-8s %-12s %-12s %-12s %-8s %-12s%n",
                                "BILL No.", "Type", "Amount", "Due Date", "State", "provider");
                        int billNo = 1;
                        for (Bill bill : bills) {
                            System.out.printf("%-8s %-12s %-12s %-12s %-8s %-12s%n", billNo++, bill.getType(), bill.getAmount(), bill.getDueDate(), bill.getState(), bill.getProvider());
                        }
                    }
                    break;
                case "LIST_BILL":
                    printBillList(paymentService.listBill(user));
                    break;
                case "PAY":
                    int length = commandParts.length;
                    if (length == 2) {
                        paymentService.payBill(user, Integer.parseInt(commandParts[1]));
                    } else if (length > 2){
                        List<Integer> ids = Arrays.stream(commandParts).skip(1).map(Integer::parseInt).collect(Collectors.toList());
                        paymentService.payBills(user, ids);
                    }
                    break;
                case "DUE_DATE":
                    printBillList(paymentService.listBillsSortedByDueDate(user));
                    break;
                case "SCHEDULE":
                    if (commandParts.length > 2) {
                        int billId = Integer.parseInt(commandParts[1]);
                        LocalDate paymentDate = LocalDate.parse(commandParts[2], dateFormatter);
                        paymentService.schedulePaymentByBillId(user, billId, paymentDate);
                    }
                    break;
                case "LIST_PAYMENT":
                    printPaymentList(paymentService.listPayment(user));
                    break;
                case "EXIT":
                    System.out.println("Good bye!");
                    return;
                default:
                    System.out.println("Unknown command");
            }
        }
    }

    public static void printBillList(List<Bill> bills) {
        System.out.printf("%-8s %-12s %-12s %-12s %-8s %-12s%n",
                "BILL No.", "Type", "Amount", "Due Date", "State", "provider");
        int billNo = 1;
        for (Bill bill : bills) {
            System.out.printf("%-8s %-12s %-12s %-12s %-8s %-12s%n", billNo++, bill.getType(), bill.getAmount(), bill.getDueDate(), bill.getState(), bill.getProvider());
        }
    }

    public static void printPaymentList(List<Payment> payments) {
        System.out.printf("%-4s %-10s %-13s %-12s %-7s%n",
                "No.", "Amount", "Payment Date", "State", "Bill Id");
        int paymentNo = 1;
        for (Payment payment : payments) {
            System.out.printf("%-4s %-10s %-13s %-12s %-7s%n", paymentNo++, payment.getAmount(), payment.getPaymentDate(), payment.getState(), payment.getBill().getId()
            );
        }

    }
}