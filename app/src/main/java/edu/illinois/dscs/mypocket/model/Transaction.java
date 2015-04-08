package edu.illinois.dscs.mypocket.model;

import java.util.Date;

/**
 * Simple class that has all attributes of a transaction.
 *
 * @author Cassio
 * @version 1.0
 */
public class Transaction {
    private String transactionName;
    private String description;
    private double value;
    private Date creationDate;
    private Category transactionCategory;
    private Account parentAccount;

    /**
     * Creates a new transaction object.
     *
     * @param transactionName the name of the transaction.
     * @param description     additional description of the transaction.
     * @param value           the value of the transaction.
     * @param creationDate    the creation date of the transaction (not necessarily today's date).
     */
    public Transaction(String transactionName, String description, double value, Date creationDate) {
        this.transactionName = transactionName;
        this.description = description;
        this.value = value;
        this.creationDate = creationDate;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
