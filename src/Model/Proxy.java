/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import UI.EasyRestoInterface;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Point;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;
import java.time.LocalDate;
import javax.swing.JButton;

/**
 *
 * @author a22lucasmpg
 */
public class Proxy {

    private EasyRestoDB easyRestoDb;
    private EasyRestoInterface easyRestoInterface;
    private Worker workerLogged;
    private final String clockOutQuery = "UPDATE HORARIOS_TRABAJADORES SET SALIDA=CURRENT_TIMESTAMP, TOTAL_JORNADA=TIMEDIFF(SALIDA, ENTRADA) WHERE ID_TRABAJADOR =? AND SALIDA IS NULL AND TIMEDIFF(CURRENT_TIMESTAMP, ENTRADA)< '10:00:00'";
    private final String clockOutRememberQuery = "SELECT * FROM HORARIOS_TRABAJADORES WHERE ID_TRABAJADOR=? AND TIMEDIFF(CURRENT_TIMESTAMP,(SELECT ENTRADA FROM HORARIOS_TRABAJADORES WHERE DATE(ENTRADA) = CURRENT_DATE AND ID_TRABAJADOR=?)) >= '07:55:00' AND SALIDA IS NULL";
    private final String permissionsQuery = "SELECT PERMISOS FROM TRABAJADORES WHERE EMAIL=?";
    private final String clockInQuery = "INSERT INTO HORARIOS_TRABAJADORES(ID_TRABAJADOR) VALUES (?)";
    private final String checkClockInQuery = "SELECT DATE(ENTRADA) FROM HORARIOS_TRABAJADORES WHERE ID_TRABAJADOR =?";
    private final String workerDataQuery = "SELECT ID_TRABAJADOR, NOMBRE, APELLIDOS, NSS, EMAIL, TELEFONO, PASS, PERMISOS FROM TRABAJADORES WHERE EMAIL=? OR ID_TRABAJADOR=?";
    private final String passwordQuery = "SELECT PASS FROM TRABAJADORES WHERE ID_TRABAJADOR= ? OR EMAIL=?";
    private final String activeWorkerNameIDQuery = "SELECT NOMBRE, ID_TRABAJADOR FROM TRABAJADORES WHERE ACTIVO IS TRUE";
    private final String activeEmailQuery = "SELECT EMAIL FROM TRABAJADORES WHERE ACTIVO IS TRUE";
    private final String tablesQuery = "SELECT * FROM MESAS ";
    private final String familyProductQuery= "SELECT * FROM FAMILIAS ";
    private final String productQuery= "SELECT * FROM PRODUCTOS AS P INNER JOIN FAMILIAS AS F ON P.FAMILIA= F.ID_FAMILIA WHERE F.NOMBRE=?";
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
            familyProductPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(familyProductQuery);
            productPrep = this.easyRestoDb.getEasyRestoConnection().prepareStatement(productQuery);
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
                        new Point(tablesResult.getInt("COORD_X"),tablesResult.getInt("COORD_Y")),
                        tablesResult.getInt("CAPACIDAD"),
                        tablesResult.getString("ICONO"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Proxy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getProductFamilyButton(){
        try {
            ResultSet productFamilyResult = this.familyProductPrep.executeQuery();
            while(productFamilyResult.next()){
                this.easyRestoInterface.configProductFamilyButton(productFamilyResult.getString("NOMBRE"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Proxy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void getProductButton(String familyName){
        
        try {
            this.productPrep.setString(1,familyName);
            ResultSet productResult = this.productPrep.executeQuery();
            while(productResult.next()){
               this.easyRestoInterface.configProductButton(productResult.getString("NOMBRE"),productResult.getDouble("PRECIO")); 
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

}
