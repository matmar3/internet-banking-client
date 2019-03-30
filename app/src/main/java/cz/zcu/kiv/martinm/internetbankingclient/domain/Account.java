package cz.zcu.kiv.martinm.internetbankingclient.domain;

import java.math.BigDecimal;

public class Account implements DataTransferObject<Integer> {

    public Account() {
    }

    public Account(String currency, String accountNumber, String cardNumber, User user) {
        this.currency = currency;
        this.accountNumber = accountNumber;
        this.cardNumber = cardNumber;
        this.balance = new BigDecimal(0);
        this.user = user;
    }

    private Integer id;

    private String currency;

    private String accountNumber;

    private String cardNumber;

    private BigDecimal balance;

    private User user;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
