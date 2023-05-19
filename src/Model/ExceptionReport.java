/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;



/**
 *
 * @author matut
 */
public interface ExceptionReport {

    
 /**
  * Trata la exception recibida guardando el nombre de la exception, fecha y causa en un fichero .txt
  * @param exception  exception a reportar 
  */
    public void reportException(Exception exception);
       
}
