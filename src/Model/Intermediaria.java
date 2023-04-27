/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import UI.EasyRestoInterface;

/**
 *
 * @author a22lucasmpg
 */
public class Intermediaria {
    
    private EasyRestoDB easyRestoDb;
    private EasyRestoInterface easyRestoInterface ;

    public Intermediaria(EasyRestoInterface easyRestoInterface) {
        this.easyRestoInterface=easyRestoInterface;
        this.easyRestoDb= new EasyRestoDB();
    }
    
    
    
    
}
