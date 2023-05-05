/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import UI.EasyRestoInterface;
import java.awt.HeadlessException;
import java.awt.Point;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author a22lucasmpg
 */
public class Proxy {

    private EasyRestoDB easyRestoDb;
    private EasyRestoInterface easyRestoInterface;
    private Worker workerLogged;
    private Order currentOrder ;
    private final String clockOutQuery = "UPDATE HORARIOS_TRABAJADORES SET SALIDA=CURRENT_TIMESTAMP, TOTAL_JORNADA=TIMEDIFF(SALIDA, ENTRADA) WHERE ID_TRABAJADOR =? AND SALIDA IS NULL AND TIMEDIFF(CURRENT_TIMESTAMP, ENTRADA)< '10:00:00'";
    private final String clockOutRememberQuery = "SELECT * FROM HORARIOS_TRABAJADORES WHERE ID_TRABAJADOR=? AND TIMEDIFF(CURRENT_TIMESTAMP,(SELECT ENTRADA FROM HORARIOS_TRABAJADORES WHERE DATE(ENTRADA) = CURRENT_DATE AND ID_TRABAJADOR=?)) >= '07:55:00' AND SALIDA IS NULL";
    private final String permissionsQuery = "SELECT PERMISOS FROM TRABAJADORES WHERE EMAIL=?";
    private final String clockInQuery = "INSERT INTO HORARIOS_TRABAJADORES(ID_TRABAJADOR) VALUES (?)";
    private final String checkClockInQuery = "SELECT DATE(ENTRADA) FROM HORARIOS_TRABAJADORES WHERE ID_TRABAJADOR =?";
    private final String workerDataQuery = "SELECT ID_TRABAJADOR, NOMBRE, APELLIDOS, NSS, EMAIL, TELEFONO, PASS, PERMISOS FROM TRABAJADORES WHERE EMAIL=? OR ID_TRABAJADOR=?";
    private final String passwordQuery = "SELECT PASS FROM TRABAJADORES WHERE ID_TRABAJADOR= ? OR EMAIL=?";
    private final String activeWorkerNameIDQuery = "SELECT NOMBRE, ID_TRABAJADOR FROM TRABAJADORES WHERE ACTIVO IS TRUE";
    private final String activeEmailQuery = "SELECT EMAIL FROM TRABAJADORES WHERE ACTIVO IS TRUE";
    private final String tablesQuery = "SELECT ID_MESA,COORD_X,COORD_Y,CAPACIDAD,ICONO FROM MESAS ";
    private final String familyQuery = "SELECT NOMBRE FROM FAMILIAS ";
    private final String productQuery = "SELECT P.ID_PRODUCTO, P.NOMBRE, P.PRECIO FROM PRODUCTOS AS P INNER JOIN FAMILIAS AS F ON P.FAMILIA= F.ID_FAMILIA WHERE F.NOMBRE=? AND P.ACTIVO IS TRUE";
    private final String insertOrderQuery = "INSERT INTO PEDIDOS VALUES (default,?,?,default,current_timestamp(),default,default,default)";
    private final String currentOrderQuery = "SELECT ID_PEDIDO FROM PEDIDOS WHERE ID_MESA=? AND ESTADO_PEDIDO='ACTIVO'";
    private final String insertProductQuery = "INSERT INTO PEDIDOS_PRODUCTOS VALUES(?,?,?) ON DUPLICATE KEY UPDATE CANTIDAD=CANTIDAD+?";
//    private final String 
    private PreparedStatement clockOutRememberPrep;
    private PreparedStatement clockOutPrep;
    private PreparedStatement permissionsPrep;
    private PreparedStatement clockInPrep;
    private PreparedStatement checkClockInPrep;
    private PreparedStatement workerDataPrep;
    private PreparedStatement passwordPrep;
    private PreparedStatement activeWorkerNamePrep;
    private PreparedStatement activeEmailPrep;
    private PreparedStatement tablesPrep;
    private PreparedStatement familyProductPrep;
    private PreparedStatement productPrep;
    private PreparedStatement insertOrderPrep;
    private PreparedStatement currentOrderPrep;
    private PreparedStatement insertProductPrep;
    private ArrayList<Product> pendingProductsArray = new ArrayList<>();

    public Proxy(EasyRestoInterface easyRestoInterface) {
        this.easyRestoInterface = easyRestoInterface;
        this.easyRestoDb = new EasyRestoDB();
        this.initPreparedStatements();
    }

    private void initPreparedStatements() {
        try {
            clockOutRememberPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(clockOutRememberQuery);
            clockOutPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(clockOutQuery);
            permissionsPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(permissionsQuery);
            clockInPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(clockInQuery);
            checkClockInPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(checkClockInQuery);
            workerDataPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(workerDataQuery);
            passwordPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(passwordQuery);
            activeWorkerNamePrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(activeWorkerNameIDQuery);
            activeEmailPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(activeEmailQuery);
            tablesPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(tablesQuery);
            familyProductPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(familyQuery);
            productPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(productQuery);
            insertOrderPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(insertOrderQuery);
            currentOrderPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(currentOrderQuery);
            insertProductPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(insertProductQuery);
        } catch (SQLException ex) {
            Logger.getLogger(Proxy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean workerLogin(String workerID, char[] password) throws HeadlessException {
        if (!this.easyRestoInterface.checkEmptyWorkerPassField(new String(password))) {
            if (this.checkCorrectPassword(workerID, new String(password))) {
                this.workerLogged = this.getWorkerData(workerID);
                JOptionPane.showMessageDialog(this.easyRestoInterface, "BIENVENIDO A EASYRESTO!");
                return true;
            } else {
                JOptionPane.showMessageDialog(this.easyRestoInterface, "CONTRASEÑA INCORRECTA, VUELVE A INTENTARLO");
                this.easyRestoInterface.emptyPassFieldText(this.easyRestoInterface.getPasswordButtonPanelTextField());
            }
        } else {
            JOptionPane.showMessageDialog(this.easyRestoInterface, "POR FAVOR, INTRODUCE TU CONTRASEÑA");
        }
        return false;
    }

    public boolean adminSettingsLogin(String email, char[] password) throws HeadlessException {
        if (!this.easyRestoInterface.checkEmptyAdminLoginFields(email, new String(password))) {
            if (this.checkRegisteredWorker(email)) {
                if (this.checkAdminPermission(email)) {
                    if (this.checkCorrectPassword(email, new String(password))) {
                        this.workerLogged = this.getWorkerData(email);
                        JOptionPane.showMessageDialog(this.easyRestoInterface, "BIENVENIDO A EASYRESTO!");
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(this.easyRestoInterface, "USUARIO O CONTRASEÑA INCORRECTO, VUELVE A INTENTARLO");
                        this.easyRestoInterface.emptyPassFieldText(this.easyRestoInterface.getPassTextField());
                    }
                } else {
                    JOptionPane.showMessageDialog(this.easyRestoInterface, "NO DISPONES DE PERMISOS PARA ACCEDER");
                }
            } else {
                JOptionPane.showMessageDialog(this.easyRestoInterface, "EL CORREO INTRODUCIDO NO SE ENCUENTRA REGISTRADO");
                this.easyRestoInterface.emptyPassFieldText(this.easyRestoInterface.getPassTextField());
            }
        } else {
            JOptionPane.showMessageDialog(this.easyRestoInterface, "POR FAVOR RELLENA TODOS LOS CAMPOS");
        }
        return false;
    }

    private boolean checkRegisteredWorker(String email) {
        try {
            ResultSet activeEmailResult = this.activeEmailPrep.executeQuery();
            while (activeEmailResult.next()) {
                if (activeEmailResult.getString("EMAIL").equals(email)) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EasyRestoDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void getWorkerNameIDButton() {
        try {
            ResultSet activeWorkerNameResult = this.activeWorkerNamePrep.executeQuery();
            while (activeWorkerNameResult.next()) {
                this.easyRestoInterface.configWorkerButton(activeWorkerNameResult.getString("NOMBRE"), activeWorkerNameResult.getInt("ID_TRABAJADOR"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EasyRestoDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getTablesButton() {
        try {
            ResultSet tablesResult = this.tablesPrep.executeQuery();
            while (tablesResult.next()) {
                this.easyRestoInterface.configTableButton(
                        tablesResult.getInt("ID_MESA"),
                        new Point(tablesResult.getInt("COORD_X"), tablesResult.getInt("COORD_Y")),
                        tablesResult.getInt("CAPACIDAD"),
                        tablesResult.getString("ICONO"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Proxy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getProductFamilyButton() {
        try {
            ResultSet productFamilyResult = this.familyProductPrep.executeQuery();
            while (productFamilyResult.next()) {
                this.easyRestoInterface.configProductFamilyButton(productFamilyResult.getString("NOMBRE"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Proxy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void getProductButton(String familyName) {

        try {
            this.productPrep.setString(1, familyName);
            ResultSet productResult = this.productPrep.executeQuery();
            while (productResult.next()) {
                this.easyRestoInterface.configProductButton(productResult.getInt("ID_PRODUCTO"), productResult.getString("NOMBRE"), productResult.getDouble("PRECIO"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Proxy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean checkCorrectPassword(String emailOrID, String password) {
        boolean passwordMatchs = false;
        try {
            this.passwordPrep.setString(1, emailOrID);
            this.passwordPrep.setString(2, emailOrID);
            ResultSet workerPassResult = this.passwordPrep.executeQuery();
            while (workerPassResult.next()) {
                passwordMatchs = workerPassResult.getString("PASS").equals(password);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EasyRestoDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return passwordMatchs;
    }

    private Worker getWorkerData(String emailOrID) {
        Worker worker = null;
        try {
            this.workerDataPrep.setString(1, emailOrID);
            this.workerDataPrep.setString(2, emailOrID);
            ResultSet workerDataResult = this.workerDataPrep.executeQuery();
            while (workerDataResult.next()) {
                worker = new Worker(workerDataResult.getInt("ID_TRABAJADOR"),
                        workerDataResult.getString("NOMBRE"),
                        workerDataResult.getString("APELLIDOS"),
                        workerDataResult.getString("NSS"),
                        workerDataResult.getString("EMAIL"),
                        workerDataResult.getString("TELEFONO"),
                        workerDataResult.getString("PASS"),
                        workerDataResult.getString("PERMISOS"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EasyRestoDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return worker;
    }

    public boolean checkClockIn(int workerId) {
        try {
            this.checkClockInPrep.setString(1, String.valueOf(workerId));
            ResultSet clockInDayResult = this.checkClockInPrep.executeQuery();
            while (clockInDayResult.next()) {
                if (clockInDayResult.getString("DATE(ENTRADA)").equals(LocalDate.now().toString())) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Proxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void clockIn(int workerId) {
        try {
            clockInPrep.setString(1, String.valueOf(workerId));
            clockInPrep.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Proxy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean rememberClockOut(int workerId) {
        try {
            this.clockOutRememberPrep.setString(1, String.valueOf(workerId));
            this.clockOutRememberPrep.setString(2, String.valueOf(workerId));
            while (this.clockOutRememberPrep.executeQuery().next()) {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(Proxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void clockOut(int workerId) {
        try {
            this.clockOutPrep.setString(1, String.valueOf(workerId));
            this.clockOutPrep.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Proxy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean checkAdminPermission(String email) {
        try {
            this.permissionsPrep.setString(1, email);
            ResultSet permissionsResult = this.permissionsPrep.executeQuery();
            while (permissionsResult.next()) {
                if (permissionsResult.getString("PERMISOS").equals("ADMIN")) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Proxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void insertOrder(int workerID, int tableID) {
        try {
            this.insertOrderPrep.setString(1, String.valueOf(workerID));
            this.insertOrderPrep.setString(2, String.valueOf(tableID));
            this.insertOrderPrep.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Proxy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Order checkActiveOrderTable(int tableID) {
        try {
            this.currentOrderPrep.setString(1, String.valueOf(tableID));
            ResultSet orderResult = this.currentOrderPrep.executeQuery();
            while (orderResult.next()) {
                return new Order(orderResult.getInt("ID_PEDIDO"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Proxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

        public void addProductToArray(Product product) {
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
        
    public void sendPendingProducts() {
        Iterator<Product> iteratorProducts = this.pendingProductsArray.iterator();
        while (iteratorProducts.hasNext()) {
            Product productToSend = iteratorProducts.next();
            System.out.println("order:"+this.currentOrder.getOrderID()+ "productID:"+productToSend.getProductID()+ "quantity:"+productToSend.getProductQuantity());
            this.insertProduct(this.currentOrder.getOrderID(), productToSend.getProductID(), productToSend.getProductQuantity());
        }
    }

    private void insertProduct(int orderID, int productID, int productQuantity) {
        try {
            this.insertProductPrep.setString(1, String.valueOf(orderID));
            this.insertProductPrep.setString(2, String.valueOf(productID));
            this.insertProductPrep.setString(3, String.valueOf(productQuantity));
            this.insertProductPrep.setString(4, String.valueOf(productQuantity));
            this.insertProductPrep.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Proxy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    public EasyRestoDB getEasyRestoDb() {
        return easyRestoDb;
    }

    public void setEasyRestoDb(EasyRestoDB easyRestoDb) {
        this.easyRestoDb = easyRestoDb;
    }

    public EasyRestoInterface getEasyRestoInterface() {
        return easyRestoInterface;
    }

    public void setEasyRestoInterface(EasyRestoInterface easyRestoInterface) {
        this.easyRestoInterface = easyRestoInterface;
    }

    public Worker getWorkerLogged() {
        return workerLogged;
    }

    public void setWorkerLogged(Worker workerLogged) {
        this.workerLogged = workerLogged;
    }

    public PreparedStatement getClockOutRememberPrep() {
        return clockOutRememberPrep;
    }

    public void setClockOutRememberPrep(PreparedStatement clockOutRememberPrep) {
        this.clockOutRememberPrep = clockOutRememberPrep;
    }

    public PreparedStatement getClockOutPrep() {
        return clockOutPrep;
    }

    public void setClockOutPrep(PreparedStatement clockOutPrep) {
        this.clockOutPrep = clockOutPrep;
    }

    public PreparedStatement getPermissionsPrep() {
        return permissionsPrep;
    }

    public void setPermissionsPrep(PreparedStatement permissionsPrep) {
        this.permissionsPrep = permissionsPrep;
    }

    public PreparedStatement getClockInPrep() {
        return clockInPrep;
    }

    public void setClockInPrep(PreparedStatement clockInPrep) {
        this.clockInPrep = clockInPrep;
    }

    public PreparedStatement getCheckClockInPrep() {
        return checkClockInPrep;
    }

    public void setCheckClockInPrep(PreparedStatement checkClockInPrep) {
        this.checkClockInPrep = checkClockInPrep;
    }

    public PreparedStatement getWorkerDataPrep() {
        return workerDataPrep;
    }

    public void setWorkerDataPrep(PreparedStatement workerDataPrep) {
        this.workerDataPrep = workerDataPrep;
    }

    public PreparedStatement getPasswordPrep() {
        return passwordPrep;
    }

    public void setPasswordPrep(PreparedStatement passwordPrep) {
        this.passwordPrep = passwordPrep;
    }

    public PreparedStatement getActiveWorkerNamePrep() {
        return activeWorkerNamePrep;
    }

    public void setActiveWorkerNamePrep(PreparedStatement activeWorkerNamePrep) {
        this.activeWorkerNamePrep = activeWorkerNamePrep;
    }

    public PreparedStatement getActiveEmailPrep() {
        return activeEmailPrep;
    }

    public void setActiveEmailPrep(PreparedStatement activeEmailPrep) {
        this.activeEmailPrep = activeEmailPrep;
    }

    public PreparedStatement getTablesPrep() {
        return tablesPrep;
    }

    public void setTablesPrep(PreparedStatement tablesPrep) {
        this.tablesPrep = tablesPrep;
    }

    public PreparedStatement getFamilyProductPrep() {
        return familyProductPrep;
    }

    public void setFamilyProductPrep(PreparedStatement familyProductPrep) {
        this.familyProductPrep = familyProductPrep;
    }

    public PreparedStatement getProductPrep() {
        return productPrep;
    }

    public void setProductPrep(PreparedStatement productPrep) {
        this.productPrep = productPrep;
    }

    public PreparedStatement getInsertOrderPrep() {
        return insertOrderPrep;
    }

    public void setInsertOrderPrep(PreparedStatement insertOrderPrep) {
        this.insertOrderPrep = insertOrderPrep;
    }

    public ArrayList<Product> getPendingProductsArray() {
        return pendingProductsArray;
    }

    public void setPendingProductsArray(ArrayList<Product> pendingProductsArray) {
        this.pendingProductsArray = pendingProductsArray;
    }

}
