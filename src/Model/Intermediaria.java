/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import UI.EasyRestoInterface;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

/**
 *
 * @author a22lucasmpg
 */
public class Intermediaria {

    private EasyRestoDB easyRestoDb;
    private EasyRestoInterface easyRestoInterface;
    private Connection easyRestoConnection;
    private Statement mysqlSelect;
    private Worker workerLogged;

    public Intermediaria(EasyRestoInterface easyRestoInterface) {
        this.easyRestoInterface = easyRestoInterface;
        this.easyRestoDb = new EasyRestoDB();
        this.easyRestoConnection = this.easyRestoDb.openConnection();
        try {
            this.mysqlSelect = this.easyRestoConnection.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(Intermediaria.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean workerLogin(String workerName, char[] password) throws HeadlessException {
        if (!this.easyRestoInterface.checkEmptyWorkerPassField(new String(password))) {
            if (this.checkCorrectPassword(workerName, new String(password))) {
                this.workerLogged = this.getWorkerData(workerName);
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

    public void adminSettingsLogin(String email, char[] password) throws HeadlessException {
        if (!this.easyRestoInterface.checkEmptyAdminLoginFields(email, new String(password))) {
            if (this.checkRegisteredWorker(email)) {
                if (this.checkCorrectPassword(email, new String(password))) {
                    this.workerLogged = this.getWorkerData(email);
                    System.out.println(this.workerLogged.getEmail());
                    JOptionPane.showMessageDialog(this.easyRestoInterface, "BIENVENIDO A EASYRESTO!");
                } else {
                    JOptionPane.showMessageDialog(this.easyRestoInterface, "USUARIO O CONTRASEÑA INCORRECTO, VUELVE A INTENTARLO");
                    this.easyRestoInterface.emptyPassFieldText(this.easyRestoInterface.getPassTextField());
                }
            } else {
                JOptionPane.showMessageDialog(this.easyRestoInterface, "EL CORREO INTRODUCIDO NO SE ENCUENTRA REGISTRADO");
                this.easyRestoInterface.emptyPassFieldText(this.easyRestoInterface.getPassTextField());
            }
        } else {
            JOptionPane.showMessageDialog(this.easyRestoInterface, "POR FAVOR RELLENA TODOS LOS CAMPOS");
        }
    }

    public boolean checkRegisteredWorker(String email) {
        boolean registeredWorker = false;
        try {
            ResultSet mysqlResult = this.easyRestoConnection.createStatement().executeQuery("SELECT EMAIL FROM TRABAJADORES");
            while (mysqlResult.next()) {
                registeredWorker = mysqlResult.getString("EMAIL").equals(email);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EasyRestoDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return registeredWorker;
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
            while (workerPassResult.next()) {
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

    public Connection getEasyRestoConnection() {
        return easyRestoConnection;
    }

    public void setEasyRestoConnection(Connection easyRestoConnection) {
        this.easyRestoConnection = easyRestoConnection;
    }

    public Statement getMysqlSelect() {
        return mysqlSelect;
    }

    public void setMysqlSelect(Statement mysqlSelect) {
        this.mysqlSelect = mysqlSelect;
    }

    public Worker getWorkerLogged() {
        return workerLogged;
    }

    public void setWorkerLogged(Worker workerLogged) {
        this.workerLogged = workerLogged;
    }

}
