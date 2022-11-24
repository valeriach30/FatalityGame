/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication;

import Libreria.Juego.Jugador;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vchin
 */
public class ThreadServidor extends Thread implements iObserver{
    
    private int ficha;
    private int id;
    private Socket socketRef;
    public DataInputStream reader;
    public DataOutputStream writer;
    public ObjectInputStream Objectreader;
    public ObjectOutputStream Objectwriter;
    private String nombre;
    private boolean running = true;
    Servidor server;
    
    public ThreadServidor(Socket socketRef, Servidor server, int id) throws IOException {
        this.socketRef = socketRef;
        reader = new DataInputStream(socketRef.getInputStream());
        writer = new DataOutputStream(socketRef.getOutputStream());
        Objectreader = new ObjectInputStream(socketRef.getInputStream());
        Objectwriter = new ObjectOutputStream(socketRef.getOutputStream());
        this.server = server;
        this.id = id;
    }
    
    public void run (){
        
        int instruccionId = 1;
        while (running){
            try {
                instruccionId = reader.readInt(); // esperar hasta que reciba un entero
                System.out.println("instruccion server: " + instruccionId);
                switch (instruccionId){
                    //----------------------------INFO PERSONAL----------------------------
                    case 1:
                        
                        nombre = reader.readUTF();
                        Jugador player = (Jugador) Objectreader.readObject();
                        server.controlMain.agregarJugador(player);
                        for (int i = 0; i < server.controlMain.juego.getJugadores().size(); i++) {
                            System.out.println(server.controlMain.juego.getJugadores().get(i).toString());
                        }
                        writer.writeInt(1);
                        writer.writeInt(id);
                        writer.writeInt(server.getTurno());
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
                    case 3:
                        break;
                    default:
                        break;
                }
            } catch (IOException ex) {
                System.out.println("error servidor");
                System.out.println(ex.toString());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ThreadServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 

    @Override
    public void notificar(String command, Object source) {
        for (int i = 0; i < server.conexiones.size(); i++) {
            ThreadServidor current = server.conexiones.get(i);
            // hace algo aqui
        }
    }
}
