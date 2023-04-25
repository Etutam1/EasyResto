/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import UI.EasyRestoInterface;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;

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
    
    public boolean checkRegisteredWorker(String email) {
        boolean registered = false;
        try {
            ResultSet mysqlResult = mysqlSelect.executeQuery("SELECT EMAIL FROM TRABAJADORES");
            while (mysqlResult.next()) {
                registered = mysqlResult.getString("EMAIL").equals(email); 
            }
        } catch (SQLException ex) {
            Logger.getLogger(EasyRestoDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return registered;
    }
    
     public void getWorkerNameButton() {
        try {
            ResultSet workerNameResult = this.mysqlSelect.executeQuery("SELECT NOMBRE FROM TRABAJADORES");
            while (workerNameResult.next()) {
                this.easyRestoInterface.configWorkerButton(workerNameResult.getString("NOMBRE"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(EasyRestoDB.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean checkCorrectPassword(String emailOrName, String password) {
        
        boolean passwordMatchs = false;
        String instruction = "SELECT PASS FROM TRABAJADORES WHERE NOMBRE= ? OR EMAIL=?";
        try {
            PreparedStatement prep = this.easyRestoConnection.prepareStatement(instruction);
            prep.setString(1, emailOrName);
            prep.setString(2, emailOrName);
            ResultSet workerPassResult = prep.executeQuery();
            while(workerPassResult.next()){
                 passwordMatchs = workerPassResult.getString("PASS").equals(password);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EasyRestoDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return passwordMatchs;
    }

    public Worker getWorkerData(String inputText) {
        Worker worker = null;
        String instruction = "SELECT ID_TRABAJADOR, NOMBRE, APELLIDOS, NSS, EMAIL, TELEFONO, PASS, PERMISOS FROM TRABAJADORES WHERE EMAIL=? OR NOMBRE=?";
        try {
            PreparedStatement prep = this.easyRestoConnection.prepareStatement(instruction);
            prep.setString(1, inputText);
            prep.setString(2, inputText);
            ResultSet workerDataResult = prep.executeQuery();
            
            if (!workerDataResult.isBeforeFirst()) {
                System.out.println("null");
            }
            while (workerDataResult.next()) {
                System.out.println("1");
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

   
}
