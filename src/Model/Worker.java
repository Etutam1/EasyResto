/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author matut
 */
public class Worker extends User{

    
    private String nss;
    private String permissions;

    
    public Worker() {
    }

    
    public Worker( int id, String name, String lastname, String email, String phoneNumber, String permissions) {
        super(id, name, lastname, email, phoneNumber);
        this.permissions = permissions;
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
