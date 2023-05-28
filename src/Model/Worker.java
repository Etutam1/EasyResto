/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Controller.Proxy;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 * Represents a worker in the system, extending the User class.
 *
 * @author a22lucasmpg
 */
public class Worker extends User {

    private String dni;
    private String nss;
    private String permissions;
    private int active;
    private Proxy proxy;

    /**
     * Constructs an empty Worker object.
     */
    public Worker() {
    }

    /**
     * Constructs a Worker object with the specified Proxy.
     *
     * @param proxy the Proxy object associated with the Worker.
     */
    public Worker(Proxy proxy) {
        this.proxy = proxy;
    }

    /**
     * Constructs a Worker object with the specified id, name, lastname, email,
     * phoneNumber, and permissions.
     *
     * @param id the id of the worker.
     * @param name the name of the worker.
     * @param lastname the lastname of the worker.
     * @param email the email of the worker.
     * @param phoneNumber the phone number of the worker.
     * @param permissions the permissions of the worker.
     */
    public Worker(int id, String name, String lastname, String email, String phoneNumber, String permissions) {
        super(id, name, lastname, email, phoneNumber);
        this.permissions = permissions;
    }

    /**
     * Constructs a Worker object with the specified id, name, lastname, dni,
     * nss, email, phoneNumber, password, permissions, and active status.
     *
     * @param id the id of the worker.
     * @param name the name of the worker.
     * @param lastname the lastname of the worker.
     * @param dni the DNI (national identity document) of the worker.
     * @param nss the NSS (social security number) of the worker.
     * @param email the email of the worker.
     * @param phoneNumber the phone number of the worker.
     * @param password the password of the worker.
     * @param permissions the permissions of the worker.
     * @param active the active status of the worker.
     */
    public Worker(int id, String name, String lastname, String dni, String nss, String email, String phoneNumber, String password, String permissions, int active) {
        super(id, name, lastname, email, password, phoneNumber);
        this.dni = dni;
        this.nss = nss;
        this.permissions = permissions;
        this.active = active;
    }

    /**
     * Constructs a Worker object with the specified id, name, lastname, dni,
     * nss, email, phoneNumber, permissions, and active status.
     *
     * @param id the id of the worker.
     * @param name the name of the worker.
     * @param lastname the lastname of the worker.
     * @param dni the DNI (national identity document) of the worker.
     * @param nss the NSS (social security number) of the worker.
     * @param email the email of the worker.
     * @param phoneNumber the phone number of the worker.
     * @param permissions the permissions of the worker.
     * @param active the active status of the worker.
     */
    public Worker(int id, String name, String lastname, String dni, String nss, String email, String phoneNumber, String permissions, int active) {
        super(id, name, lastname, email, phoneNumber);
        this.dni = dni;
        this.nss = nss;
        this.permissions = permissions;
        this.active = active;
    }

    @Override
    public void sendMessage(User recipient, String message) {

    }

    /**
     * Checks if the user with the specified email has the permission.
     *
     * @param email the email of the user to check for permission.
     * @return true if the user with the given email has the permission, false
     * otherwise.
     *
     */
    @Override
    public boolean hasPermission(String email) {
        try {
            this.proxy.getCheckPermissionsPrep().setString(1, email);
            ResultSet permissionResult = this.proxy.getCheckPermissionsPrep().executeQuery();
            while (permissionResult.next()) {
                if (permissionResult.getString(1).equals("ADMIN")) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            this.reportException(ex);
        }
        return false;
    }

    /**
     * Reports the given exception by writing it to a log file.
     *
     * @param exception the exception to be reported.
     */
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

    /**
     *
     * @return
     */
    public int getActive() {
        return active;
    }

    /**
     *
     * @param active
     */
    public void setActive(int active) {
        this.active = active;
    }

    /**
     *
     * @return
     */
    public String getDni() {
        return dni;
    }

    /**
     *
     * @param dni
     */
    public void setDni(String dni) {
        this.dni = dni;
    }

    /**
     *
     * @return
     */
    public String getNss() {
        return nss;
    }

    /**
     *
     * @param nss
     */
    public void setNss(String nss) {
        this.nss = nss;
    }

    /**
     *
     * @return
     */
    public String getPermissions() {
        return permissions;
    }

    /**
     *
     * @param permissions
     */
    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

}
