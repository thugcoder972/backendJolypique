package com.mysycorp.Backendjo.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentRequest {
    private UUID orderId;
    private String paymentMethod;
    private BigDecimal amount;
    private String currency = "EUR";
    private String cardNumber;
    private String cardExpiry;  // Format MM/YY
    private String cardCvv;
    private String clientEmail;

    // Constructeurs
    public PaymentRequest() {}

    public PaymentRequest(UUID orderId, String paymentMethod, BigDecimal amount) {
        this.orderId = orderId;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
    }

    // Getters et Setters
    public UUID getOrderId() { return orderId; }
    public void setOrderId(UUID orderId) { this.orderId = orderId; }
    
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    
    public String getCardNumber() { return cardNumber; }
    public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
    
    // Ajout spécifique pour corriger l'erreur getExpiryDate()
    public String getCardExpiry() { return cardExpiry; }
    public String getExpiryDate() { return cardExpiry; } // Alias pour compatibilité
    
    public void setCardExpiry(String cardExpiry) { this.cardExpiry = cardExpiry; }
    
    // Ajout spécifique pour corriger l'erreur getCvv()
    public String getCardCvv() { return cardCvv; }
    public String getCvv() { return cardCvv; } // Alias pour compatibilité
    
    public void setCardCvv(String cardCvv) { this.cardCvv = cardCvv; }
    
    public String getClientEmail() { return clientEmail; }
    public void setClientEmail(String clientEmail) { this.clientEmail = clientEmail; }
}