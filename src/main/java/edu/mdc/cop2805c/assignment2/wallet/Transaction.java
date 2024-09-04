package edu.mdc.cop2805c.assignment2.wallet;

import edu.mdc.cop2805c.assignment2.base.WalletStorable;

public class Transaction<T extends WalletStorable> {

    // Holds information about a crypto transaction.
    private TransactionType type;
    private T holding;
    String fromAddress;
    String toAddress;

    public Transaction(TransactionType type, T holding, String fromAddress, String toAddress) {
        this.type = type;
        this.holding = holding;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
    }

    public TransactionType getType() {
        return this.type;
    }

    public void setType(TransactionType value) {
        this.type = value;
    }

    public T getHoldings() {
        return this.holding;
    }

    public void setHoldings(T value) {
        this.holding = value;
    }

    public String getFromAddress() {
        return this.fromAddress;
    }

    public void setFromAddress(String value) {
        this.fromAddress = value;
    }

    public String getToAddress() {
        return this.toAddress;
    }

    public void setToAddress(String value) {
        this.toAddress = value;
    }
}
