        package Model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author a22lucasmpg
 */
public class Product {
    
    
    private int productID;
    private String productName;
    private int productQuantity =1;
    private double productPrice;

    public Product(int id, String name, double price) {
        this.productID = id;
        this.productName = name;
        this.productPrice= price;
    }

    public Product() {
    }
    
    public Product(String name, int quantity){
        this.productName = name;
        this.productQuantity = quantity;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    
    
}
