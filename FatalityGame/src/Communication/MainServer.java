/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication;

import java.io.IOException;

/**
 *
 * @author vchin
 */
public class MainServer {
    public static void main(String[] args) throws Exception {
        ServerForm  pantalla = new ServerForm();
        Servidor srv = new Servidor(pantalla);
        pantalla.serverRef =srv;
        pantalla.setVisible(true);
        srv.runServer();   
    }
}
