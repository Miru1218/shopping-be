package com.shoppingbe.shoppingbe.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

//@Entity的Bean是告訴Spring這是數據模型層的宣告
@Entity
//@Table name: Table的name對映到資料庫中的資料表名稱
@Table(name = "order_detail")
@Getter
@Setter
@NoArgsConstructor
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //    @Column name: 對應到Table的欄位中的欄位名稱
    @Column(name = "id")
    private int id;
    @Column(name = "order_id")
    private int orderId;
    @Column(name = "product_id")
    private int productId;
    @Column(name = "qty")
    private int qty;
    @Column(name = "items_price")
    private int price;
    @Column(name = "tax_price")
    private double taxPrice;
    @Column(name = "total_price")
    private double totalPrice;
}
