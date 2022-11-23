/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication;

import fatalitygame.Configuracion;

/**
 *
 * @author vchin
 */
public class MainCliente {
    public static void main(String[] args) {
        try{
        Configuracion pantalla = new Configuracion();
        pantalla.setVisible(true); 
        }
        catch(Exception e){
            
        }
    }
}
