package za.ac.mycput.musicalnote_backend.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import za.ac.mycput.musicalnote_backend.Domain.Payment;
import za.ac.mycput.musicalnote_backend.Service.PaymentService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PaymentControllerTest {

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private PaymentService paymentService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testCreatePayment_Success() throws Exception {
        Payment payment = new Payment();
        payment.setPaymentId(1L);
        payment.setAmount(100.0f);

        when(paymentService.createPayment(any(Payment.class))).thenReturn(payment);

        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payment)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.paymentId").value(1L))
                .andExpect(jsonPath("$.amount").value(100.0f));
    }

    @Test
    public void testCreatePayment_BadRequest() throws Exception {
        Payment payment = new Payment();

        when(paymentService.createPayment(any(Payment.class))).thenThrow(new IllegalArgumentException());

        mockMvc.perform(post("/api/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payment)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testGetPaymentById_Success() throws Exception {
        Payment payment = new Payment();
        payment.setPaymentId(1L);
        payment.setAmount(100.0f);

        when(paymentService.getPaymentById(1L)).thenReturn(payment);

        mockMvc.perform(get("/api/payments/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentId").value(1L))
                .andExpect(jsonPath("$.amount").value(100.0f));
    }

    @Test
    public void testGetPaymentById_NotFound() throws Exception {
        when(paymentService.getPaymentById(1L)).thenThrow(new RuntimeException("Payment not found with id: 1"));

        mockMvc.perform(get("/api/payments/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdatePayment_Success() throws Exception {
        Payment payment = new Payment();
        payment.setPaymentId(1L);
        payment.setAmount(100.0f);

        when(paymentService.updatePayment(any(Payment.class))).thenReturn(payment);

        mockMvc.perform(put("/api/payments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentId").value(1L))
                .andExpect(jsonPath("$.amount").value(100.0f));
    }

    @Test
    public void testUpdatePayment_NotFound() throws Exception {
        Payment payment = new Payment();

        when(paymentService.updatePayment(any(Payment.class))).thenThrow(new RuntimeException("Payment not found with id: 1"));

        mockMvc.perform(put("/api/payments/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payment)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeletePayment_Success() throws Exception {
        doNothing().when(paymentService).deletePayment(1L);

        mockMvc.perform(delete("/api/payments/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeletePayment_NotFound() throws Exception {
        doThrow(new RuntimeException("Payment not found with id: 1")).when(paymentService).deletePayment(1L);

        mockMvc.perform(delete("/api/payments/1"))
                .andExpect(status().isNotFound());
    }
}

