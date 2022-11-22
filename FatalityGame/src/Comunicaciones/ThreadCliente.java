/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Comunicaciones;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author vchin
 */
public class ThreadCliente {
    private Socket socketRef;
    public DataInputStream reader;
    public DataOutputStream writer;
    
    public ThreadCliente(Socket socketRef) throws IOException {
        this.socketRef = socketRef;
        reader = new DataInputStream(socketRef.getInputStream());
        writer = new DataOutputStream(socketRef.getOutputStream());
        //this.refPantalla = refPantalla;
    }
    public void run (){
        
    }
}
