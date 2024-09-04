package za.ac.mycput.musicalnote_backend.Factory;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import za.ac.mycput.musicalnote_backend.Domain.Order;
import za.ac.mycput.musicalnote_backend.Domain.Payment;
import za.ac.mycput.musicalnote_backend.util.Helper;

import java.util.Date;

public class PaymentFactoryTest {

    @Test
    public void testBuildPayment_Success() {
        Order order = new Order();
        Long paymentId = 1L;
        Date paymentDate = new Date();
        float amount = 100.0f;
        String paymentMethod = "Credit Card";
        String status = "Completed";

        Payment payment = PaymentFactory.buildPayment(paymentId, order, paymentDate, amount, paymentMethod, status);

        Assertions.assertNotNull(payment);
        Assertions.assertEquals(paymentId, payment.getPaymentId());
        Assertions.assertEquals(order, payment.getOrder());
        Assertions.assertEquals(paymentDate, payment.getPaymentDate());
        Assertions.assertEquals(amount, payment.getAmount());
        Assertions.assertEquals(paymentMethod, payment.getPaymentMethod());
        Assertions.assertEquals(status, payment.getStatus());
    }

    @Test
    public void testBuildPayment_InvalidPaymentId() {

        Order order = new Order();
        Date paymentDate = new Date();
        float amount = 100.0f;
        String paymentMethod = "Credit Card";
        String status = "Completed";

        Payment payment = PaymentFactory.buildPayment(null, order, paymentDate, amount, paymentMethod, status);

        Assertions.assertNull(payment);
    }

    @Test
    public void testBuildPayment_InvalidOrder() {

        Long paymentId = 1L;
        Date paymentDate = new Date();
        float amount = 100.0f;
        String paymentMethod = "Credit Card";
        String status = "Completed";

        Payment payment = PaymentFactory.buildPayment(paymentId, null, paymentDate, amount, paymentMethod, status);
        Assertions.assertNull(payment);
    }

    @Test
    public void testBuildPayment_InvalidPaymentDate() {

        Order order = new Order();
        Long paymentId = 1L;
        float amount = 100.0f;
        String paymentMethod = "Credit Card";
        String status = "Completed";

        Payment payment = PaymentFactory.buildPayment(paymentId, order, null, amount, paymentMethod, status);

        Assertions.assertNull(payment);
    }

    @Test
    public void testBuildPayment_InvalidAmount() {

        Order order = new Order();
        Long paymentId = 1L;
        Date paymentDate = new Date();
        float amount = -10.0f;
        String paymentMethod = "Credit Card";
        String status = "Completed";

        Payment payment = PaymentFactory.buildPayment(paymentId, order, paymentDate, amount, paymentMethod, status);
        Assertions.assertNull(payment);
    }

    @Test
    public void testBuildPayment_InvalidPaymentMethod() {

        Order order = new Order();
        Long paymentId = 1L;
        Date paymentDate = new Date();
        float amount = 100.0f;
        String status = "Completed";

        Payment payment = PaymentFactory.buildPayment(paymentId, order, paymentDate, amount, null, status);

        Assertions.assertNull(payment);
    }

    @Test
    public void testBuildPayment_InvalidStatus() {
        Order order = new Order();
        Long paymentId = 1L;
        Date paymentDate = new Date();
        float amount = 100.0f;
        String paymentMethod = "Credit Card";

        Payment payment = PaymentFactory.buildPayment(paymentId, order, paymentDate, amount, paymentMethod, null);
        Assertions.assertNull(payment);
    }
}
