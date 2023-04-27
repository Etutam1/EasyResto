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

   
    
    private Statement mysqlSelect;
    private String user;
    private String pass;
    private String server;
    private String port;
    private String dataBase;

    public EasyRestoDB() {
        
       
    }

    public Connection openConnection() {
        Connection conexionServer = null;
        try {
            conexionServer = DriverManager.getConnection("jdbc:mysql://localhost:3306/easyrestobd", "root", "root");
        } catch (SQLException ex) {
            Logger.getLogger(EasyRestoDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conexionServer;
    }

  

    public Statement getMysqlSelect() {
        return mysqlSelect;
    }

    public void setMysqlSelect(Statement mysqlSelect) {
        this.mysqlSelect = mysqlSelect;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }
    
   
}
