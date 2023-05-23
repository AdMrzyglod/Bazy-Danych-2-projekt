package com.example.project.Logic;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;


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
    private float money;

    @OneToMany
    @JoinColumn(name = "USER_ID")
    private List<PlatformOrder> platformOrders;

    @OneToMany
    @JoinColumn(name = "USER_ID")
    private List<Payment> payments;

    @OneToMany(mappedBy = "user")
    private List<GameCode> gameCodes;

    @OneToMany(mappedBy = "user")
    private List<UserTournament> userTournaments;


    public PlatformUser(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.money=0;
        this.platformOrders = new ArrayList<PlatformOrder>();
        this.gameCodes= new ArrayList<GameCode>();
        this.userTournaments= new ArrayList<UserTournament>();
        this.payments= new ArrayList<Payment>();
        setMoney(10000);
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


    public float getMoney(){
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public void updateMoney(float cash){
        out.println(cash+ "  "+money+"  " + (money+cash));
        this.money+=cash;
        out.println(money);
    }

    public boolean canBuy(float cash){
        return this.money-cash>0 ? true : false;
    }

    public void addOrder(PlatformOrder platformOrder){
        this.platformOrders.add(platformOrder);
    }

    public int getUser_ID() {
        return User_ID;
    }

    public List<PlatformOrder> getPlatformOrders() {
        return platformOrders;
    }

    public List<GameCode> getGameCodes() {
        return gameCodes;
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
