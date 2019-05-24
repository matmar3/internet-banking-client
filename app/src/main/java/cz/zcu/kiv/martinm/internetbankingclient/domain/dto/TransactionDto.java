package cz.zcu.kiv.martinm.internetbankingclient.domain.dto;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionDto {

    private String receiverAccountNumber;

    private BigDecimal sentAmount;

    private String senderAccountNumber;

    private String dueDate;

    private String constantSymbol;

    private String variableSymbol;

    private String specificSymbol;

    private String message;

    public String getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public void setReceiverAccountNumber(String receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }

    public BigDecimal getSentAmount() {
        return sentAmount;
    }

    public void setSentAmount(BigDecimal sentAmount) {
        this.sentAmount = sentAmount;
    }

    public String getSenderAccountNumber() {
        return senderAccountNumber;
    }

    public void setSenderAccountNumber(String senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getConstantSymbol() {
        return constantSymbol;
    }

    public void setConstantSymbol(String constantSymbol) {
        this.constantSymbol = constantSymbol;
    }

    public String getVariableSymbol() {
        return variableSymbol;
    }

    public void setVariableSymbol(String variableSymbol) {
        this.variableSymbol = variableSymbol;
    }

    public String getSpecificSymbol() {
        return specificSymbol;
    }

    public void setSpecificSymbol(String specificSymbol) {
        this.specificSymbol = specificSymbol;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
