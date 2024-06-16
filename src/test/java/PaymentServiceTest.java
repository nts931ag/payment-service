import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.entities.Bill;
import org.entities.Payment;
import org.entities.User;
import org.enums.BillState;
import org.enums.BillType;
import org.enums.PaymentState;
import org.junit.Before;
import org.junit.Test;
import org.services.PaymentService;
import org.services.impl.PaymentServiceImpl;

/**
 *
 * @author ngus
 */
public class PaymentServiceTest {
    private User user;
    private DateTimeFormatter dateFormatter;

    private PaymentService paymentService;

    @Before
    public void setUp() {
        user = new User();
        dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        paymentService = new PaymentServiceImpl();
    }

    @Test
    public void testAddFunds() {
        paymentService.addBalanceForUser(user,1000.0);
        assertEquals(1000.0, user.getBalance(), 0.01);

        paymentService.addBalanceForUser(user,500.0);
        assertEquals(1500.0, user.getBalance(), 0.01);
    }

    @Test
    public void testAddBill() {
        Bill bill = createBill(BillType.ELECTRIC, 200000, LocalDate.parse("25/10/2020", dateFormatter), "EVN HCMC");
        user.addBill(bill);

        assertEquals(1, user.getBillList().size());
        assertEquals(BillType.ELECTRIC, user.getBillList().get(0).getType());
    }

    private Bill createBill(BillType billType, double amount, LocalDate dueDate, String provider) {
        Bill bill = new  Bill(billType, 200000, dueDate, provider);
        Payment payment = new Payment(bill, amount, dueDate, PaymentState.PENDING);
        bill.setPayment(payment);
        return bill;
    }

    @Test
    public void testPayBill() {
        Bill bill = createBill(BillType.ELECTRIC, 200000, LocalDate.parse("25/10/2020", dateFormatter), "EVN HCMC");
        user.addBill(bill);
        paymentService.addBalanceForUser(user,300000);

        paymentService.payBill(user, bill.getId());

        assertEquals(100000.0, user.getBalance(), 0.01);
        assertEquals(BillState.PAID, bill.getState());
    }

    @Test
    public void testPayBillInsufficientFunds() {
        Bill bill = createBill(BillType.WATER, 175000, LocalDate.parse("30/10/2020", dateFormatter), "SAVACO HCMC");
        user.addBill(bill);
        paymentService.addBalanceForUser(user,100000);

        paymentService.payBill(user, bill.getId());

        assertEquals(100000.0, user.getBalance(), 0.01);
        assertEquals(BillState.NOT_PAID, bill.getState());
    }

    @Test
    public void testSchedulePayment() {
        Bill bill = createBill(BillType.INTERNET, 800000, LocalDate.parse("30/11/2020", dateFormatter), "VNPT");
        Payment payment2 = new Payment(bill, bill.getAmount(), bill.getDueDate(), PaymentState.PENDING);
        bill.setPayment(payment2);
        user.addBill(bill);

        LocalDate scheduleDate = LocalDate.parse("28/10/2020", dateFormatter);
        paymentService.schedulePaymentByBillId(user, bill.getId(), scheduleDate);

        LocalDate result = bill.getPayment().getPaymentDate();

        assertTrue(result.equals(scheduleDate));
    }

    @Test
    public void testSearchBillByProvider() {
        Bill bill1 = createBill(BillType.ELECTRIC, 200000, LocalDate.parse("25/10/2020", dateFormatter), "EVN HCMC");
        Bill bill2 = createBill(BillType.WATER, 175000, LocalDate.parse("30/10/2020", dateFormatter), "SAVACO HCMC");
        Bill bill3 = createBill(BillType.INTERNET, 800000, LocalDate.parse("30/11/2020", dateFormatter), "VNPT");

        user.addBill(bill1);
        user.addBill(bill2);
        user.addBill(bill3);

        long count = paymentService.searchBillByProvider(user, "VNPT").size();

        assertEquals(1, count);
    }
}
