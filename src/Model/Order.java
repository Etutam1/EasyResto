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
    private ArrayList<Product> subOrderpendingProductsArray = new ArrayList<>();
    private ArrayList<Product> pendingProductsArray = new ArrayList<>();
    private ArrayList<Product> totalProductsArray = new ArrayList<>();
    private int pax;

    public Order(int orderID) {
        this.orderID = orderID;
    }

    public Order() {
    }

    public Order(int orderID, int pax) {
        this.orderID = orderID;
        this.pax = pax;
    }

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

    public void addProductToTotalArray(Product product) {
        this.totalProductsArray.add(product);
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
