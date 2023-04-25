/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import UI.EasyRestoInterface;
import java.awt.Dimension;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JButton;

/**
 *
 * @author matut
 */
public class EasyRestoDB {

    private Connection easyRestoConnection;
    private EasyRestoInterface easyRestoInterface;
    private Statement mysqlSelect;
    private String user;
    private String pass;
    private String server;
    private String port;
    private String dataBase;

    public EasyRestoDB(EasyRestoInterface easyRestoInterface) {
        this.easyRestoInterface = easyRestoInterface;
        this.easyRestoConnection = this.openConnection();
        try {
            this.mysqlSelect = this.easyRestoConnection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(EasyRestoDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Connection openConnection() {
        Connection conexionServer = null;

        try {
            conexionServer = DriverManager.getConnection("jdbc:mysql://localhost:3306/easyrestobd", "root", "root");
        } catch (SQLException ex) {
            Logger.getLogger(EasyRestoDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return conexionServer;
    }

    private boolean checkCorrectPasswordByEmail(String email, String password) {
        boolean passwordMatchs = false;
        try {
            ResultSet mysqlResult = mysqlSelect.executeQuery("SELECT PASS FROM TRABAJADORES WHERE EMAIL =" + "'" + email + "'");
            while (mysqlResult.next()) {
                if (mysqlResult.getString("PASS").equals(password)) {
                    return true;
                } else {
                    passwordMatchs = false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EasyRestoDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return passwordMatchs;
    }

    private boolean checkCorrectPasswordByButton(String workerName, String password) {
        boolean passwordMatchs = false;
        try {
            ResultSet mysqlResult = mysqlSelect.executeQuery("SELECT PASS FROM TRABAJADORES WHERE NOMBRE =" + "'" + workerName + "'");
            while (mysqlResult.next()) {
                if (mysqlResult.getString("PASS").equals(password)) {
                    return true;
                } else {
                    passwordMatchs = false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EasyRestoDB.class.getName()).log(Level.SEVERE, null, ex);
        }

        return passwordMatchs;
    }

    public boolean checkCorrectPassword(String email, char[] password, String workerName) {
        String passwordStr = new String(password);
        if (this.easyRestoInterface.getLoginButton().hasFocus() || this.easyRestoInterface.getPassTextField().hasFocus()) {
            return this.checkCorrectPasswordByEmail(email, passwordStr);
        }
        return checkCorrectPasswordByButton(workerName, passwordStr);
    }

    public boolean checkRegisteredWorker(String email) {
        boolean registered = false;
        try {
            ResultSet mysqlResult = mysqlSelect.executeQuery("SELECT EMAIL FROM TRABAJADORES");
            while (mysqlResult.next()) {
                if (mysqlResult.getString("EMAIL").equals(email)) {
                    return true;
                } else {
                    registered = false;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(EasyRestoDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return registered;
    }

    private Worker getWorkerDataByEmail(String email) {
        int id = 0;
        String name = null;
        String lastname = null;
        String nss = null;
        String workerEmail = null;
        String phoneNumber = null;
        String password = null;
        String permissions = null;
        try {

            ResultSet mysqlResult = mysqlSelect.executeQuery("SELECT ID_TRABAJADOR, NOMBRE, APELLIDOS, NSS, EMAIL, TELEFONO, PASS, PERMISOS FROM TRABAJADORES WHERE EMAIL='" + email + "'");
            while (mysqlResult.next()) {
                id = mysqlResult.getInt("ID_TRABAJADOR");
                name = mysqlResult.getString("NOMBRE");
                lastname = mysqlResult.getString("APELLIDOS");
                nss = mysqlResult.getString("NSS");
                workerEmail = mysqlResult.getString("EMAIL");
                phoneNumber = mysqlResult.getString("TELEFONO");
                password = mysqlResult.getString("PASS");
                permissions = mysqlResult.getString("PERMISOS");
            }
        } catch (SQLException ex) {
            Logger.getLogger(EasyRestoDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Worker(id, name, lastname, nss, workerEmail, phoneNumber, password, permissions);
    }

    private Worker getWorkerDataByName(String workerName) {
        int id = 0;
        String name = null;
        String lastname = null;
        String nss = null;
        String workerEmail = null;
        String phoneNumber = null;
        String password = null;
        String permissions = null;
        try {

            ResultSet mysqlResult = mysqlSelect.executeQuery("SELECT ID_TRABAJADOR, NOMBRE, APELLIDOS, NSS, EMAIL, TELEFONO, PASS, PERMISOS FROM TRABAJADORES WHERE NOMBRE='" + workerName + "'");
            while (mysqlResult.next()) {
                id = mysqlResult.getInt("ID_TRABAJADOR");
                name = mysqlResult.getString("NOMBRE");
                lastname = mysqlResult.getString("APELLIDOS");
                nss = mysqlResult.getString("NSS");
                workerEmail = mysqlResult.getString("EMAIL");
                phoneNumber = mysqlResult.getString("TELEFONO");
                password = mysqlResult.getString("PASS");
                permissions = mysqlResult.getString("PERMISOS");
            }

        } catch (SQLException ex) {
            Logger.getLogger(EasyRestoDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new Worker(id, name, lastname, nss, workerEmail, phoneNumber, password, permissions);
    }

    public Worker getWorkerData(String inputText) {

        if (this.easyRestoInterface.getLoginButton().hasFocus() || this.easyRestoInterface.getPassTextField().hasFocus()) {
            return this.getWorkerDataByEmail(inputText);
        }
        return this.getWorkerDataByName(inputText);
    }

    public void getWorkerNameButton() {

        try {
            ResultSet mysqlResult = this.mysqlSelect.executeQuery("SELECT NOMBRE FROM TRABAJADORES");
            while (mysqlResult.next()) {
                this.easyRestoInterface.configWorkerButton(mysqlResult.getString("NOMBRE"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EasyRestoDB.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
