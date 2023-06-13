package com.example.project.Logic.DatabaseClasses;


import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int payment_ID;

    @ManyToOne
    @JoinColumn(name="user_id")
    private PlatformUser platformUser;

    private BigDecimal amount;

    private String bankAccountNumber;

    private Timestamp date;
    private String title;
    private boolean isVerified;

    @Version
    @Column(name = "version", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private int version;


    public Payment() {
    }

    public Payment(BigDecimal amount, String bankAccountNumber, Timestamp date, String title) {
        this.amount = amount;
        this.bankAccountNumber = bankAccountNumber;
        this.date = date;
        this.title = title;
        this.isVerified=false;
        this.version=0;
    }

    public int getPayment_ID() {
        return payment_ID;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public PlatformUser getPlatformUser() {
        return platformUser;
    }

    public void setPlatformUser(PlatformUser platformUser) {
        this.platformUser = platformUser;
    }
}
