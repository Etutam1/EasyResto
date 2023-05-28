/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

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

    /**
     * Constructs an empty Worker object.
     */
    public Worker() {
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

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNss() {
        return nss;
    }

    public void setNss(String nss) {
        this.nss = nss;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

}
