/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import Model.EasyRestoDB;
import Model.ExceptionReport;
import Model.Order;
import Model.Product;
import Model.Worker;
import UI.EasyRestoInterface;
import java.awt.HeadlessException;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.time.LocalDate;
import java.util.Date;
import java.util.Iterator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author a22lucasmpg
 */
public class Proxy implements ExceptionReport {

    private EasyRestoDB easyRestoDb;
    private EasyRestoInterface easyRestoInterface;
    private Worker workerLogged;
    private Order currentOrder;
    private final String clockOutQuery = "UPDATE HORARIOS_TRABAJADORES SET SALIDA=CURRENT_TIMESTAMP, TOTAL_JORNADA=TIMEDIFF(SALIDA, ENTRADA) WHERE ID_TRABAJADOR =? AND SALIDA IS NULL AND TIMEDIFF(CURRENT_TIMESTAMP, ENTRADA)< '10:00:00'";
    private final String clockOutRememberQuery = "SELECT ID_REGISTRO_HORARIO FROM HORARIOS_TRABAJADORES WHERE ID_TRABAJADOR=? AND TIMEDIFF(CURRENT_TIMESTAMP,(SELECT ENTRADA FROM HORARIOS_TRABAJADORES WHERE DATE(ENTRADA) = CURRENT_DATE AND ID_TRABAJADOR=?)) >= '07:55:00' AND SALIDA IS NULL";
    private final String clockInQuery = "INSERT INTO HORARIOS_TRABAJADORES(ID_TRABAJADOR) VALUES (?)";
    private final String checkClockInQuery = "SELECT DATE(ENTRADA) FROM HORARIOS_TRABAJADORES WHERE ID_TRABAJADOR =?";
    private final String workerDataQuery = "SELECT ID_TRABAJADOR, NOMBRE, APELLIDOS, EMAIL, TELEFONO, PERMISOS FROM TRABAJADORES WHERE EMAIL=? OR ID_TRABAJADOR=?";
    private final String passwordWorkerQuery = "SELECT COMPROBAR_CONTRASEÑA(?,?)";
    private final String passwordAdminQuery = "SELECT COMPROBAR_CONTRASEÑA_PERMISOS_ADMIN(?,?)";
    private final String activeWorkerNameIDQuery = "SELECT NOMBRE, ID_TRABAJADOR FROM TRABAJADORES WHERE ACTIVO IS TRUE";
    private final String activeEmailQuery = "SELECT EMAIL FROM TRABAJADORES WHERE ACTIVO IS TRUE";
    private final String tablesQuery = "SELECT ID_MESA,COORD_X,COORD_Y,CAPACIDAD,ICONO FROM MESAS ";
    private final String familyQuery = "SELECT NOMBRE FROM FAMILIAS";
    private final String productQuery = "SELECT P.ID_PRODUCTO, P.NOMBRE, P.PRECIO, P.URL_IMAGEN FROM PRODUCTOS AS P INNER JOIN FAMILIAS AS F ON P.FAMILIA= F.ID_FAMILIA WHERE F.NOMBRE=? AND P.ACTIVO IS TRUE";
    private final String insertOrderQuery = "INSERT INTO PEDIDOS VALUES (DEFAULT,?,?,?,CURRENT_TIMESTAMP(),DEFAULT,DEFAULT,DEFAULT)";
    private final String currentOrderQuery = "SELECT ID_PEDIDO , COMENSALES_ACTUALES FROM PEDIDOS WHERE ID_MESA=?";
    private final String insertProductQuery = "INSERT INTO PEDIDOS_PRODUCTOS VALUES(?,?,?) ON DUPLICATE KEY UPDATE CANTIDAD=CANTIDAD+?";
    private final String closeOrderQuery = "CALL CERRAR_MESA(?,?)";
    private final String removeOrderQuery = "DELETE FROM PEDIDOS WHERE ID_PEDIDO=?";
    private final String orderProductsQuery = "SELECT P.NOMBRE, P.PRECIO, PP.CANTIDAD , PP.ID_PRODUCTO FROM PEDIDOS_PRODUCTOS AS PP INNER JOIN PRODUCTOS AS P ON PP.ID_PRODUCTO=P.ID_PRODUCTO WHERE ID_PEDIDO=?";
    private final String totalOrderQuery = "SELECT TOTAL_PEDIDO(?) AS TOTAL";
    private final String removeProductQuery = "CALL BORRAR_PRODUCTO(?,?,?)";
    private final String insertNewWorkerQuery = "INSERT INTO TRABAJADORES VALUES (DEFAULT,?,?,?,?,?,?,SHA(?),?,DEFAULT)";
    private final String checkNewWorkerDataQuery = "SELECT COMPROBAR_NUEVO_TRABAJADOR(?,?,?,?,?,?)";

    private PreparedStatement clockOutRememberPrep;
    private PreparedStatement clockOutPrep;
    private PreparedStatement clockInPrep;
    private PreparedStatement checkClockInPrep;
    private PreparedStatement workerDataPrep;
    private PreparedStatement passwordWorkerPrep;
    private PreparedStatement passwordAdminPrep;
    private PreparedStatement activeWorkerNamePrep;
    private PreparedStatement activeEmailPrep;
    private PreparedStatement tablesPrep;
    private PreparedStatement familyProductPrep;
    private PreparedStatement productPrep;
    private PreparedStatement insertOrderPrep;
    private PreparedStatement currentOrderPrep;
    private PreparedStatement insertProductPrep;
    private PreparedStatement closeOrderPrep;
    private PreparedStatement orderProductsPrep;
    private PreparedStatement totalOrderPrep;
    private PreparedStatement removeProductPrep;
    private PreparedStatement removeOrderPrep;
    private PreparedStatement insertNewWorkerPrep;
    private PreparedStatement checkNewWorkerPrep;

    public Proxy(EasyRestoInterface easyRestoInterface) {
        this.easyRestoInterface = easyRestoInterface;
        this.easyRestoDb = new EasyRestoDB();
        this.initPreparedStatements();
    }

    private void initPreparedStatements() {
        try {
            clockOutRememberPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(clockOutRememberQuery);
            clockOutPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(clockOutQuery);
            clockInPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(clockInQuery);
            checkClockInPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(checkClockInQuery);
            workerDataPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(workerDataQuery);
            passwordWorkerPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(passwordWorkerQuery);
            passwordAdminPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(passwordAdminQuery);
            activeWorkerNamePrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(activeWorkerNameIDQuery);
            activeEmailPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(activeEmailQuery);
            tablesPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(tablesQuery);
            familyProductPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(familyQuery);
            productPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(productQuery);
            insertOrderPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(insertOrderQuery, Statement.RETURN_GENERATED_KEYS);
            currentOrderPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(currentOrderQuery);
            insertProductPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(insertProductQuery);
            closeOrderPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(closeOrderQuery);
            orderProductsPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(orderProductsQuery);
            totalOrderPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(this.totalOrderQuery);
            removeProductPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(this.removeProductQuery);
            removeOrderPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(this.removeOrderQuery);
            insertNewWorkerPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(this.insertNewWorkerQuery);
            checkNewWorkerPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(this.checkNewWorkerDataQuery);
        } catch (SQLException ex) {
            this.reportException(ex);
        }
    }

    public boolean login(boolean admin, String emailOrID, char[] password) {
        if (admin) {
            if (this.adminSettingsLogin(emailOrID, password)) {
                return true;
            }
        } else {
            if (this.workerLogin(emailOrID, password)) {
                return true;
            }
        }
        return false;
    }

    public boolean handleRequest(String request, String familyName, int Id) {
        switch (request) {
            case "getWorkerButton" ->
                this.getWorkerButton();
            case "getTablesButton" ->
                this.getTablesButton();
            case "getProductFamilyButton" ->
                this.getProductFamilyButton();
            case "getProductButton" ->
                this.getProductButton(familyName);
            case "checkClockIn" -> {
                if (!this.checkClockIn(Id)) {
                    return false;
                }
            }
            case "clockIn" ->
                this.clockIn(Id);
            case "rememberClockOut" -> {
                if (!this.rememberClockOut(Id)) {
                    return false;
                }
            }
            case "clockOut" ->
                this.clockOut(Id);

            case "closeOrder" -> {
                System.out.println("CLOSE");
                this.closeOrder(Id);
            }
            case "removeOrder" ->
                this.removeOrder(Id);
        }
        return true;
    }

    public boolean checkExistentWorkerData() {
        try {
            this.checkNewWorkerPrep.setString(1, this.easyRestoInterface.getNewWorkerNameField().getText());
            this.checkNewWorkerPrep.setString(2, this.easyRestoInterface.getNewWorkerLastNameField().getText());
            this.checkNewWorkerPrep.setString(3, this.easyRestoInterface.getNewWorkerDniField().getText());
            this.checkNewWorkerPrep.setString(4, this.easyRestoInterface.getNewWorkerNssField().getText());
            this.checkNewWorkerPrep.setString(5, this.easyRestoInterface.getNewWorkerEmailField().getText());
            this.checkNewWorkerPrep.setString(6, this.easyRestoInterface.getNewWorkerPhoneField().getText());
            ResultSet workerDataResult = this.checkNewWorkerPrep.executeQuery();
            while (workerDataResult.next()){
               return workerDataResult.getBoolean(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Proxy.class.getName()).log(Level.SEVERE, null, ex);
//            this.reportException(ex);
        }
        return false;
    }
    

    public void insertNewWorker() {
        try {
            this.insertNewWorkerPrep.setString(1, this.easyRestoInterface.getNewWorkerNameField().getText());
            this.insertNewWorkerPrep.setString(2, this.easyRestoInterface.getNewWorkerLastNameField().getText());
            this.insertNewWorkerPrep.setString(3, this.easyRestoInterface.getNewWorkerDniField().getText());
            this.insertNewWorkerPrep.setString(4, this.easyRestoInterface.getNewWorkerNssField().getText());
            this.insertNewWorkerPrep.setString(5, this.easyRestoInterface.getNewWorkerEmailField().getText());
            this.insertNewWorkerPrep.setString(6, this.easyRestoInterface.getNewWorkerPhoneField().getText());
            this.insertNewWorkerPrep.setString(7, this.easyRestoInterface.getNewWorkerPassField().getText());
            this.insertNewWorkerPrep.setString(8, this.easyRestoInterface.getNewWorkerPermissionsComboBox().getSelectedItem().toString());
            this.insertNewWorkerPrep.executeUpdate();
        } catch (SQLException ex) {
            this.reportException(ex);
        }
    }

    private boolean workerLogin(String workerID, char[] password) throws HeadlessException {
        if (!this.easyRestoInterface.checkEmptyWorkerPassField(new String(password))) {
            if (this.checkCorrectPassword(workerID, new String(password), false)) {
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

    private boolean adminSettingsLogin(String email, char[] password) throws HeadlessException {
        if (!this.easyRestoInterface.checkEmptyAdminLoginFields(email, new String(password))) {
            if (this.checkRegisteredWorker(email)) {
                if (this.checkCorrectPassword(email, new String(password), true)) {
                    this.workerLogged = this.getWorkerData(email);
                    JOptionPane.showMessageDialog(this.easyRestoInterface, "BIENVENIDO A EASYRESTO!");
                    return true;
                } else {
                    JOptionPane.showMessageDialog(this.easyRestoInterface, "O USUARIO O CONTRASEÑA INCORRECTOS");
                    this.easyRestoInterface.emptyPassFieldText(this.easyRestoInterface.getPassTextField());
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
            this.reportException(ex);
        }
        return false;
    }

    private boolean getWorkerButton() {
        try {
            ResultSet activeWorkerNameResult = this.activeWorkerNamePrep.executeQuery();
            while (activeWorkerNameResult.next()) {
                this.easyRestoInterface.configWorkerButton(activeWorkerNameResult.getString("NOMBRE"), activeWorkerNameResult.getInt("ID_TRABAJADOR"));
            }
        } catch (SQLException ex) {
            this.reportException(ex);
        }
        return true;

    }

    private boolean getTablesButton() {
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
            this.reportException(ex);
        }
        return true;
    }

    private boolean getProductFamilyButton() {
        try {
            ResultSet productFamilyResult = this.familyProductPrep.executeQuery();
            while (productFamilyResult.next()) {
                this.easyRestoInterface.configProductFamilyButton(productFamilyResult.getString("NOMBRE"));
            }
        } catch (SQLException ex) {
            this.reportException(ex);
        }
        return true;
    }

    private boolean getProductButton(String familyName) {

        try {
            this.productPrep.setString(1, familyName);
            ResultSet productResult = this.productPrep.executeQuery();
            while (productResult.next()) {
                this.easyRestoInterface.configProductButton(productResult.getInt("ID_PRODUCTO"), productResult.getString("NOMBRE"), productResult.getDouble("PRECIO"), productResult.getString("URL_IMAGEN"));
            }
        } catch (SQLException ex) {
            this.reportException(ex);
        }
        return true;
    }

    private boolean checkCorrectPassword(String emailOrID, String password, boolean admin) {
        try {
            if (!admin) {
                this.passwordWorkerPrep.setInt(1, Integer.parseInt(emailOrID));
                this.passwordWorkerPrep.setString(2, password);
                ResultSet passwordWorkerResult = this.passwordWorkerPrep.executeQuery();
                while (passwordWorkerResult.next()) {
                    if (passwordWorkerResult.getBoolean(1)) {
                        return true;
                    }
                }
            } else {
                this.passwordAdminPrep.setString(1, emailOrID);
                this.passwordAdminPrep.setString(2, password);
                ResultSet passwordAdminResult = this.passwordAdminPrep.executeQuery();
                while (passwordAdminResult.next()) {
                    if (passwordAdminResult.getBoolean(1)) {
                        return true;
                    }
                }
            }

        } catch (SQLException ex) {
            this.reportException(ex);
        }
        return false;
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
                        workerDataResult.getString("EMAIL"),
                        workerDataResult.getString("TELEFONO"),
                        workerDataResult.getString("PERMISOS"));
            }
        } catch (SQLException ex) {
            this.reportException(ex);
        }
        return worker;
    }

    private boolean checkClockIn(int workerId) {
        try {
            this.checkClockInPrep.setInt(1, workerId);
            ResultSet clockInDayResult = this.checkClockInPrep.executeQuery();
            while (clockInDayResult.next()) {
                if (clockInDayResult.getString("DATE(ENTRADA)").equals(LocalDate.now().toString())) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            this.reportException(ex);
        }
        return false;
    }

    private void clockIn(int workerId) {
        try {
            clockInPrep.setInt(1, workerId);
            clockInPrep.executeUpdate();
        } catch (SQLException ex) {
            this.reportException(ex);
        }
    }

    private boolean rememberClockOut(int workerId) {
        try {
            this.clockOutRememberPrep.setInt(1, workerId);
            this.clockOutRememberPrep.setInt(2, workerId);
            while (this.clockOutRememberPrep.executeQuery().next()) {
                return true;
            }
        } catch (SQLException ex) {
            this.reportException(ex);
        }
        return false;
    }

    private boolean clockOut(int workerId) {
        try {
            this.clockOutPrep.setInt(1, workerId);
            this.clockOutPrep.executeUpdate();
        } catch (SQLException ex) {
            this.reportException(ex);
        }
        return true;
    }

    public void generateOrder(int workerID, int tableID, int pax) {
        try {
            this.insertOrderPrep.setInt(1, workerID);
            this.insertOrderPrep.setInt(2, tableID);
            this.insertOrderPrep.setInt(3, pax);
            this.insertOrderPrep.executeUpdate();
        } catch (SQLException ex) {
            this.reportException(ex);
        }
    }

    public int generateSubOrder(int workerID, int tableID, int pax) {

        try {
            this.insertOrderPrep.setInt(1, workerID);
            this.insertOrderPrep.setInt(2, tableID);
            this.insertOrderPrep.setInt(3, pax);
            this.insertOrderPrep.executeUpdate();

            ResultSet subOrderIDResult = this.insertOrderPrep.getGeneratedKeys();
            while (subOrderIDResult.next()) {
                return subOrderIDResult.getInt(1);
            }
        } catch (SQLException ex) {
            this.reportException(ex);
        }
        return 0;
    }

    public int getOrderID(int tableID) {
        try {
            this.currentOrderPrep.setInt(1, tableID);
            ResultSet orderResult = this.currentOrderPrep.executeQuery();
            while (orderResult.next()) {
                return orderResult.getInt("ID_PEDIDO");
            }
        } catch (SQLException ex) {
            this.reportException(ex);
        }
        return 0;
    }

    public Order getOrderData(int tableID) {
        try {
            this.currentOrderPrep.setInt(1, tableID);
            ResultSet orderResult = this.currentOrderPrep.executeQuery();
            while (orderResult.next()) {
                return new Order(orderResult.getInt("ID_PEDIDO"), orderResult.getInt("COMENSALES_ACTUALES"));
            }
        } catch (SQLException ex) {
            this.reportException(ex);
        }
        return null;
    }

    public void removeProductFromOrder(Product product, int quantity) {
        try {
            this.removeProductPrep.setInt(1, this.currentOrder.getOrderID());
            this.removeProductPrep.setInt(2, product.getProductID());
            this.removeProductPrep.setInt(3, quantity);
            this.removeProductPrep.executeQuery();
        } catch (SQLException ex) {
            this.reportException(ex);
        }
    }

    public boolean sendPendingProducts(ArrayList<Product> array, int orderID) {
        Iterator<Product> iteratorProducts = array.iterator();
        while (iteratorProducts.hasNext()) {
            Product productToSend = iteratorProducts.next();
            System.out.println("order:" + orderID + "productID:" + productToSend.getProductID() + "quantity:" + productToSend.getProductQuantity());
            this.insertProduct(orderID, productToSend.getProductID(), productToSend.getProductQuantity());
        }
        return true;
    }

    private void insertProduct(int orderID, int productID, int productQuantity) {
        try {
            this.insertProductPrep.setInt(1, orderID);
            this.insertProductPrep.setInt(2, productID);
            this.insertProductPrep.setInt(3, productQuantity);
            this.insertProductPrep.setInt(4, productQuantity);
            this.insertProductPrep.executeUpdate();
        } catch (SQLException ex) {
            this.reportException(ex);
        }
    }

    private void closeOrder(int orderID) {
        try {
            if (this.easyRestoInterface.getChargeOrderCardPayment().hasFocus() || this.easyRestoInterface.getChargeOrderCardPaymentButton1().hasFocus()) {

                System.out.println("TARJETA");
                this.closeOrderPrep.setString(1, "TARJETA");
            } else {
                System.out.println("EFECTIVO");
                this.closeOrderPrep.setString(1, "EFECTIVO");
            }
            this.closeOrderPrep.setInt(2, orderID);
            this.closeOrderPrep.executeUpdate();
        } catch (SQLException ex) {
            this.reportException(ex);
        }
    }

    private boolean removeOrder(int orderID) {
        try {
            this.removeOrderPrep.setInt(1, orderID);
            this.removeOrderPrep.executeUpdate();
        } catch (SQLException ex) {
            this.reportException(ex);
        }
        return true;
    }

    public boolean getOrderProducts(int orderID, JTable table) {
        DefaultTableModel tableModel = (DefaultTableModel) table.getModel();
        try {
            this.orderProductsPrep.setInt(1, orderID);
            ResultSet orderProductsResult = this.orderProductsPrep.executeQuery();
            while (orderProductsResult.next()) {
                Object[] productRow = {orderProductsResult.getString("NOMBRE"), orderProductsResult.getDouble("PRECIO"), orderProductsResult.getInt("CANTIDAD"), orderProductsResult.getInt("ID_PRODUCTO")};
                tableModel.addRow(productRow);
            }
        } catch (SQLException ex) {
            this.reportException(ex);
        }
        return true;
    }

    public double getTotalOrder(int orderID) {
        try {
            this.totalOrderPrep.setInt(1, orderID);
            ResultSet totalOrderResult = this.totalOrderPrep.executeQuery();
            while (totalOrderResult.next()) {
                return totalOrderResult.getDouble("TOTAL");
            }
        } catch (SQLException ex) {
            this.reportException(ex);
        }
        return 0.0;
    }

    @Override
    public void reportException(Exception exception) {
        PrintWriter salida = null;

        try {
            salida = new PrintWriter(new FileWriter("Exceptions.txt", true));
            salida.write("Se ha producido la excepcion:" + exception.toString() + "\n Fecha: " + new Date().toString() + "\n\n");

        } catch (FileNotFoundException ex2) {
            JOptionPane.showMessageDialog(this.easyRestoInterface, "NO SE HA PODIDO REPORTAR UN PROBLEMA");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this.easyRestoInterface, "NO SE HA PODIDO REPORTAR UN PROBLEMA");
        } finally {

            if (salida != null) {
                salida.close();
            }
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
        return passwordWorkerPrep;
    }

    public void setPasswordPrep(PreparedStatement passwordPrep) {
        this.passwordWorkerPrep = passwordPrep;
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
}
