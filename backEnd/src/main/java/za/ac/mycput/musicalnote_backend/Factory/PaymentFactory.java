package za.ac.mycput.musicalnote_backend.Factory;

import za.ac.mycput.musicalnote_backend.Domain.Order;
import za.ac.mycput.musicalnote_backend.Domain.Payment;
import za.ac.mycput.musicalnote_backend.util.Helper;

import java.util.Date;

public class PaymentFactory {

    public static Payment buildPayment(
            Long paymentId,
            Order order,
            Date paymentDate,
            float amount,
            String paymentMethod,
            String status) {

        if (Helper.isNullOrEmpty(paymentId) ||
                Helper.isNullOrEmpty(order) ||
                Helper.isNullOrEmpty(paymentDate) ||
                amount < 0.0f ||
                Helper.isNullOrEmpty(paymentMethod) ||
                Helper.isNullOrEmpty(status)) {
            return null;
        }

        return new Payment.Builder()
                .setPaymentId(paymentId)
                .setOrder(order)
                .setPaymentDate(paymentDate)
                .setAmount(amount)
                .setPaymentMethod(paymentMethod)
                .setStatus(status)
                .build();
    }
}
