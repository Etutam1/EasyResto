/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 * The abstract class User represents a user entity and implements the ExceptionReport interface.
 * 
 * This class serves as a base class for specific types of users and provides common attributes
 * and behaviors related to user entities. It also implements the ExceptionReport interface, which
 * allows for reporting and handling of exceptions within the User hierarchy.
 */
public abstract class User implements ExceptionReport {

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
     * Constructs a User object with the specified id, name, lastname, email,
     * password, and phoneNumber.
     *
     * @param id the id of the user.
     * @param name the name of the user.
     * @param lastname the lastname of the user.
     * @param email the email of the user.
     * @param password the password of the user.
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

    /**
     * Sends a message to the specified recipient.
     *
     * @param recipient the user to whom the message is being sent.
     * @param message the content of the message to be sent.
     */
    public abstract void sendMessage(User recipient, String message);

    /**
     * Checks if the user with the specified email has the permission.
     *
     * @param email the email of the user to check for permission.
     * @return true if the user with the given email has the permission, false
     * otherwise.
     */
    public abstract boolean hasPermission(String email);

    /**
     *
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public String getLastname() {
        return lastname;
    }

    /**
     *
     * @param lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     *
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
