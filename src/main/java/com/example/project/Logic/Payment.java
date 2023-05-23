package com.example.project.Logic;


import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int payment_ID;

    @ManyToOne
    @JoinColumn(name="user_id")
    private PlatformUser platformUser;

    private float amount;

    private String bankAccountNumber;

    private Timestamp date;
    private String title;
    private boolean isVerified;

    public Payment() {
    }

    public Payment(float amount, String bankAccountNumber, Timestamp date, String title) {
        this.amount = amount;
        this.bankAccountNumber = bankAccountNumber;
        this.date = date;
        this.title = title;
        this.isVerified=false;
    }

    public int getPayment_ID() {
        return payment_ID;
    }

    public float getAmount() {
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
