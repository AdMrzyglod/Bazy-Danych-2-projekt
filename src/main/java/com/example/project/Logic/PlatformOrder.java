package com.example.project.Logic;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@Entity
@Table(name = "PLATFORM_ORDER")
public class PlatformOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int Order_ID;
    @OneToMany(mappedBy = "order",cascade = CascadeType.PERSIST)
    private List<OrderDetails> orderDetails;

    @ManyToOne
    @JoinColumn(name="user_id")
    private PlatformUser platformUser;

    private Timestamp orderDate;

    @PrePersist
    protected void onCreate() {
        this.orderDate = new Timestamp(System.currentTimeMillis());
    }

    public PlatformOrder(){
    }

    public PlatformOrder(PlatformUser platformUser){
        this.platformUser = platformUser;
        platformUser.addOrder(this);
        this.orderDetails = new ArrayList<OrderDetails>();
    }
    public void addDetailsToOrder(OrderDetails details){
        this.orderDetails.add(details);
    }

    public int getOrder_ID() {
        return Order_ID;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }
}
