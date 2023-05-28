/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 * Represents the database connection for EasyResto application.
 *
 * @author a22lucasmpg
 */
public class EasyRestoDB implements ExceptionReport {

  
    private Connection easyRestoConnection;
    private Statement mysqlSelect;

    /**
     * Constructs a new EasyRestoDB instance and initializes the database
     * connection. This constructor establishes a connection to the database
     * using the provided connection parameters. It also creates a statement for
     * executing SQL queries.
     *
     * @throws SQLException if a database access error occurs
     */
    public EasyRestoDB() {
        try {
            this.easyRestoConnection = this.openConnection();
            this.mysqlSelect = this.easyRestoConnection.createStatement();
        } catch (SQLException ex) {
            this.reportException(ex);
        }
    }

    private Connection openConnection() {
        Connection conexionServer = null;
        try {
            conexionServer = DriverManager.getConnection("jdbc:mysql://localhost:3306/easyrestobd", "root", "root");
        } catch (SQLException ex) {
            this.reportException(ex);
        }
        return conexionServer;
    }

    public Statement getMysqlSelect() {
        return mysqlSelect;
    }

    public void setMysqlSelect(Statement mysqlSelect) {
        this.mysqlSelect = mysqlSelect;
    }

    public Connection getEasyRestoConnection() {
        return easyRestoConnection;
    }

    public void setEasyRestoConnection(Connection easyRestoConnection) {
        this.easyRestoConnection = easyRestoConnection;
    }

    @Override
    public void reportException(Exception exception) {
        PrintWriter salida = null;

        try {
            salida = new PrintWriter(new FileWriter("Exceptions.txt", true));
            salida.write("Se ha producido la excepcion:" + exception.toString() + "\n Fecha: " + new Date().toString() + "\n\n");

        } catch (FileNotFoundException ex2) {
            JOptionPane.showMessageDialog(null, "NO SE HA PODIDO REPORTAR UN PROBLEMA");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "NO SE HA PODIDO REPORTAR UN PROBLEMA");
        } finally {

            if (salida != null) {
                salida.close();
            }
        }
    }

}
