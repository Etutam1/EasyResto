        package Model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 * Represents a product in the system.
 * @author a22lucasmpg
 */
public class Product {
    
    
    private int productID;
    private String productName;
    private int productQuantity =1;
    private double productPrice;
    
    
    /**
     * Constructs a new Product object with the specified ID, name, and price.
     *
     * @param id     the ID of the product
     * @param name   the name of the product
     * @param price  the price of the product
     */
    public Product(int id, String name, double price) {
        this.productID = id;
        this.productName = name;
        this.productPrice= price;
    }
    
     /**
     * Constructs a new empty Product object.
     */
    public Product() {
    }
    
     /**
     * Constructs a new Product object with the specified name and quantity.
     *
     * @param name      the name of the product
     * @param quantity  the quantity of the product
     */
    public Product(String name, int quantity){
        this.productName = name;
        this.productQuantity = quantity;
    }
    
    /**
     * Constructs a new Product object with the specified ID and quantity.
     *
     * @param productID   the ID of the product
     * @param quantity    the quantity of the product
     */
    public Product(int productID, int quantity) {
        this.productID = productID;
        this.productQuantity = quantity;
    }
    
    /**
     *
     * @return
     */
    public int getProductID() {
        return productID;
    }

    /**
     *
     * @param productID
     */
    public void setProductID(int productID) {
        this.productID = productID;
    }

    /**
     *
     * @return
     */
    public String getProductName() {
        return productName;
    }

    /**
     *
     * @param productName
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     *
     * @return
     */
    public int getProductQuantity() {
        return productQuantity;
    }

    /**
     *
     * @param productQuantity
     */
    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    /**
     *
     * @return
     */
    public double getProductPrice() {
        return productPrice;
    }

    /**
     *
     * @param productPrice
     */
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    
    
}
