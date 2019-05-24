package cz.zcu.kiv.martinm.internetbankingclient.domain;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionTemplate implements DataTransferObject<Integer> {

    private Integer id;

    private String templateName;

    private String receiverAccountNumber;

    private BigDecimal sentAmount;

    private String senderAccountNumber;

    private Date dueDate;

    private String constantSymbol;

    private String variableSymbol;

    private String specificSymbol;

    private String message;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTemplateName() {
        return templateName;
    }

    public String getReceiverAccountNumber() {
        return receiverAccountNumber;
    }

    public BigDecimal getSentAmount() {
        return sentAmount;
    }

    public String getSenderAccountNumber() {
        return senderAccountNumber;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public String getConstantSymbol() {
        return constantSymbol;
    }

    public String getVariableSymbol() {
        return variableSymbol;
    }

    public String getSpecificSymbol() {
        return specificSymbol;
    }

    public String getMessage() {
        return message;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public void setReceiverAccountNumber(String receiverAccountNumber) {
        this.receiverAccountNumber = receiverAccountNumber;
    }

    public void setSentAmount(BigDecimal sentAmount) {
        this.sentAmount = sentAmount;
    }

    public void setSenderAccountNumber(String senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setConstantSymbol(String constantSymbol) {
        this.constantSymbol = constantSymbol;
    }

    public void setVariableSymbol(String variableSymbol) {
        this.variableSymbol = variableSymbol;
    }

    public void setSpecificSymbol(String specificSymbol) {
        this.specificSymbol = specificSymbol;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
