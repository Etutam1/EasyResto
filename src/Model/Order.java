/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *  Represents an order in the system.
 * @author a22lucasmpg
 */
public class Order {

    private int orderID;
    private ArrayList<Product> subOrderpendingProductsArray = new ArrayList<>();
    private ArrayList<Product> pendingProductsArray = new ArrayList<>();
    private ArrayList<Product> totalProductsArray = new ArrayList<>();
    private int pax;
    
    /**
     * Constructs an Order object with the specified order ID.
     *
     * @param orderID the ID of the order.
     */
    public Order(int orderID) {
        this.orderID = orderID;
    }
    
    /**
     * Constructs an empty Order object.
     */
    public Order() {
    }
    
    /**
     * Constructs an Order object with the specified order ID and number of guests (pax).
     *
     * @param orderID the ID of the order.
     * @param pax     the number of guests for the order.
     */
    public Order(int orderID, int pax) {
        this.orderID = orderID;
        this.pax = pax;
    }
    
    /**
     * Adds a product to the pending products array. If the product already exists in the array, the quantity is increased.
     *
     * @param product the product to add.
     * @param array   the array of pending products.
     */
    public void addProductToPendingArray(Product product, ArrayList<Product> array) {
        Iterator<Product> iteratorProducts = array.iterator();
        boolean found = false;
        while (iteratorProducts.hasNext()) {
            Product pendingProduct = iteratorProducts.next();
            if (pendingProduct.getProductID() == product.getProductID()) {
                pendingProduct.setProductQuantity(pendingProduct.getProductQuantity() + 1);
                found = true;
            }
        }
        if (!found) {
            array.add(product);
        }
    }
    
    /**
     * Removes a specified quantity of a product from the pending products array. If the product quantity becomes zero, it is removed from the array.
     *
     * @param product         the product to remove.
     * @param quantityToRemove the quantity of the product to remove.
     * @param array           the array of pending products.
     * @return the remaining quantity to remove if the product quantity is less than the requested quantity, otherwise 0.
     */
    public int removeProductFromPendingArray(Product product, int quantityToRemove, ArrayList<Product> array) {
        Iterator<Product> iteratorProducts = array.iterator();
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
            array.remove(productToRemove);
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

    public int getPax() {
        return pax;
    }

    public void setPax(int pax) {
        this.pax = pax;
    }

    public ArrayList<Product> getSubOrderpendingProductsArray() {
        return subOrderpendingProductsArray;
    }

    public void setSubOrderpendingProductsArray(ArrayList<Product> subOrderpendingProductsArray) {
        this.subOrderpendingProductsArray = subOrderpendingProductsArray;
    }

    public ArrayList<Product> getTotalProductsArray() {
        return totalProductsArray;
    }

    public void setTotalProductsArray(ArrayList<Product> totalProductsArray) {
        this.totalProductsArray = totalProductsArray;
    }

}
