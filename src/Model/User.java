/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 * Represents a user in the system.
 *
 * @author a22lucasmpg
 */
public abstract class User {

    private int id;
    private String name;
    private String lastname;
    private String email;
    private String password;
    private String phoneNumber;

    /**
     * Constructs an empty User object.
     */
    public User() {
    }

    /**
     * Constructs a User object with the specified id, name, lastname, email,
     * and phoneNumber.
     *
     * @param id the id of the user.
     * @param name the name of the user.
     * @param lastname the lastname of the user.
     * @param email the email of the user.
     * @param phoneNumber the phone number of the user.
     */
    public User(int id, String name, String lastname, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    
    /**
     * Constructs a User object with the specified id, name, lastname, email, password, and phoneNumber.
     *
     * @param id          the id of the user.
     * @param name        the name of the user.
     * @param lastname    the lastname of the user.
     * @param email       the email of the user.
     * @param password    the password of the user.
     * @param phoneNumber the phone number of the user.
     */
    public User(int id, String name, String lastname, String email, String password, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
