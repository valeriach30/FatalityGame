/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author vchin
 */
class ThreadServidor extends Thread{
    
   private int ficha;
    private int dinero;
    private int propiedad;
    private int id;
    private Socket socketRef;
    public DataInputStream reader;
    public DataOutputStream writer;
    private String nombre;
    private boolean running = true;
    Servidor server;
    
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
                        nombre = reader.readUTF();
                        dinero = reader.readInt();
                        ficha = reader.readInt();
                        server.FichaConexiones[id] = ficha;
                        writer.writeInt(1);
                        writer.writeInt(id);
                        writer.writeInt(server.getTurno());
                        writer.writeUTF(server.conexiones.get(server.getTurno()).nombre);
                        
                        break;
                    
                    //----------------------------CHAT----------------------------
                    case 2:
                        String mensaje = reader.readUTF();
                        for (int i = 0; i < server.conexiones.size(); i++) {
                            ThreadServidor current = server.conexiones.get(i);
                            current.writer.writeInt(2);
                            current.writer.writeUTF(nombre);
                            current.writer.writeUTF(mensaje);
                        }
                        break;
                        
                }
            } catch (IOException ex) {
                
            }
        }
    } 
}
