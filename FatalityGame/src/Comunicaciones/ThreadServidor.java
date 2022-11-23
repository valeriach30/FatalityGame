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
public class ThreadServidor extends Thread{
    private Socket socketRef;
    public DataInputStream reader;
    public DataOutputStream writer;
    private boolean running =true;
    Servidor server;
    int id;
    
    public ThreadServidor(Socket socketRef, Servidor server, int id) throws IOException {
        this.socketRef = socketRef;
        reader = new DataInputStream(socketRef.getInputStream());
        writer = new DataOutputStream(socketRef.getOutputStream());
        this.server = server;
        this.id = id;
    }
    public void run (){
        int instruccionId = 1;
        while (running){
            try {
                instruccionId = reader.readInt(); // esperar hasta que reciba un entero
                
                switch (instruccionId){
                    //----------------------------INFO PERSONAL----------------------------
                    case 1:
                        System.out.println("hola!! estoy en el trhead del servidor");
                        break;
                    default:
                        break;
                }
                } catch (IOException ex) {
                
            }
        }
    } 
}
