/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Comunicaciones;

import java.io.IOException;

/**
 *
 * @author vchin
 */
public class ServidorMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        //ServerForm  pantalla = new ServerForm();
        Servidor srv = new Servidor();
        //pantalla.setVisible(true);
        srv.runServer();   
    }
    
}
