package za.ac.mycput.musicalnote_backend.Service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import za.ac.mycput.musicalnote_backend.Domain.Order;
import za.ac.mycput.musicalnote_backend.Domain.Payment;
import za.ac.mycput.musicalnote_backend.Repository.PaymentRepository;

import java.util.Date;
import java.util.Optional;
import java.util.NoSuchElementException;

public class PaymentServiceTest {

    @InjectMocks
    private PaymentService paymentService;

    @Mock
    private PaymentRepository paymentRepository;

    private Payment testPayment;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup a test payment
        testPayment = new Payment();
        testPayment.setPaymentId(1L);
        testPayment.setAmount(200.0f);
        testPayment.setPaymentDate(new Date());
        testPayment.setOrder(new Order()); // Assuming Order is properly initialized
    }

    @Test
    public void testCreatePayment_Success() {
        // Arrange
        when(paymentRepository.save(testPayment)).thenReturn(testPayment);

        // Act
        Payment result = paymentService.createPayment(testPayment);

        // Assert
        assertNotNull(result);
        assertEquals(testPayment.getPaymentId(), result.getPaymentId());
        assertEquals(testPayment.getAmount(), result.getAmount());
        verify(paymentRepository, times(1)).save(testPayment);
    }

    @Test
    public void testGetPaymentById_Success() {
        // Arrange
        when(paymentRepository.findById(1L)).thenReturn(Optional.of(testPayment));

        // Act
        Payment result = paymentService.getPaymentById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(testPayment.getPaymentId(), result.getPaymentId());
    }

    @Test
    public void testGetPaymentById_NotFound() {
        // Arrange
        when(paymentRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        NoSuchElementException thrown = assertThrows(NoSuchElementException.class, () -> {
            paymentService.getPaymentById(1L);
        });
        assertEquals("Payment with ID 1 not found", thrown.getMessage());
    }
}

