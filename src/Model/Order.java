/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author matut
 */
public class Order {
    
    
    private int orderID;
    private ArrayList<Product> pendingProductsArray = new ArrayList<>();
    
    public Order(int orderID) {
        this.orderID = orderID;
    }

    public Order() {
    }
    
    
     public void addProductToPendingArray(Product product) {
        Iterator<Product> iteratorProducts = this.pendingProductsArray.iterator();
        boolean found = false;
        while (iteratorProducts.hasNext()) {
            Product pendingProduct = iteratorProducts.next();
            if (pendingProduct.getProductID() == product.getProductID()) {
                pendingProduct.setProductQuantity(pendingProduct.getProductQuantity() + 1);
                found = true;
            }
        }
        if (!found) {
            this.pendingProductsArray.add(product);
        }
    }

    public int removeProductFromPendingArray(Product product, int quantityToRemove) {
        Iterator<Product> iteratorProducts = this.pendingProductsArray.iterator();
        boolean removeProduct = false;
        Product productToRemove = null;
        while (iteratorProducts.hasNext() && !removeProduct) {
            Product iteratorProduct = iteratorProducts.next();
            if (iteratorProduct.getProductID() == product.getProductID()) {
                if (iteratorProduct.getProductQuantity() <= quantityToRemove) {
                    removeProduct = true;
                    productToRemove = iteratorProduct;
                } else {
                    iteratorProduct.setProductQuantity(iteratorProduct.getProductQuantity() - quantityToRemove);
                }
            }
        }
        if (removeProduct) {
            this.pendingProductsArray.remove(productToRemove);
            if (productToRemove.getProductQuantity() < quantityToRemove) {
                int pendingToRemove = quantityToRemove - productToRemove.getProductQuantity();
                return pendingToRemove;
            }
        }
        return 0;
    }

    public ArrayList<Product> getPendingProductsArray() {
        return pendingProductsArray;
    }

    public void setPendingProductsArray(ArrayList<Product> pendingProductsArray) {
        this.pendingProductsArray = pendingProductsArray;
    }
    
    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

}
