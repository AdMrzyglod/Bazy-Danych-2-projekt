package com.example.project.Logic.DatabaseClasses;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "PLATFORM_USER")
@Inheritance(strategy = InheritanceType.JOINED)
public class PlatformUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int User_ID;
    private String username;
    private String password;
    private String email;
    private BigDecimal money;

    @OneToMany
    @JoinColumn(name = "USER_ID")
    private List<PlatformOrder> platformOrders;

    @OneToMany
    @JoinColumn(name = "USER_ID")
    private List<Payment> payments;

    @OneToMany(mappedBy = "user")
    private List<ActiveCode> activeCodes;

    @OneToMany(mappedBy = "user")
    private List<UserTournament> userTournaments;

    @Version
    @Column(name = "version", nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private int version;



    public PlatformUser(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.money=BigDecimal.valueOf(0);
        this.platformOrders = new ArrayList<PlatformOrder>();
        this.activeCodes= new ArrayList<ActiveCode>();
        this.userTournaments= new ArrayList<UserTournament>();
        this.payments= new ArrayList<Payment>();
        setMoney(BigDecimal.valueOf(10000));
        this.version=0;
    }

    public PlatformUser() {

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }


    public BigDecimal getMoney(){
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public void updateMoney(BigDecimal cash){
        this.money=money.add(cash);
    }

    public boolean canBuy(BigDecimal cash){
        return this.money.subtract(cash).compareTo(BigDecimal.valueOf(0))>0 ? true : false;
    }

    public void addOrder(PlatformOrder platformOrder){
        this.platformOrders.add(platformOrder);
    }

    public void addActiveCode(ActiveCode activeCode){
        this.activeCodes.add(activeCode);
    }

    public int getUser_ID() {
        return User_ID;
    }

    public List<PlatformOrder> getPlatformOrders() {
        return platformOrders;
    }

    public List<ActiveCode> getActiveCodes() {
        return activeCodes;
    }

    public List<UserTournament> getUserTournaments() {
        return userTournaments;
    }

    public void addUserTournament(UserTournament userTournament){
        this.getUserTournaments().add(userTournament);
    }
    public void addPaymentToUser(Payment payment){
        this.payments.add(payment);
    }

    public List<Payment> getPayments() {
        return payments;
    }
}
