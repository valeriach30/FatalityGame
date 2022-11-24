/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication;

import Libreria.Juego.Jugador;
import Modelo.ChatCommand;
import fatalitygame.Main;
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
    
    private int id;
    private Socket socketRef;
    public DataInputStream reader;
    public DataOutputStream writer;
    public ObjectInputStream Objectreader;
    public ObjectOutputStream Objectwriter;
    public String nombre;
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
                    
                    //----------------------------COMMANDOS----------------------------
                    case 2:
                        String[] arrayComandos = (String[]) Objectreader.readObject();
                        System.out.println(arrayComandos[0]);
                        switch(arrayComandos[0]){
                            case "attack":
                                break;
                            case "chat":
                                String mensaje = arrayComandos[1];
                                server.controlMain.chat(mensaje, nombre);
                                break;
                            case "giveup":
                                server.controlMain.rendirse(nombre);
                                break;
                            case "groupexit":
                                break;
                            case "pass":
                                if(this.id == server.getTurno()){
                                    server.controlMain.pasarTurno(server.getTurno());
                                }
                                else{
                                    for (int i = 0; i < server.conexiones.size(); i++) {
                                        ThreadServidor current = server.conexiones.get(i);
                                        try {
                                            if(i == this.id){
                                                current.writer.writeInt(3);
                                            }
                                        } catch (IOException ex) {
                                            Logger.getLogger(ChatCommand.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                }
                                break;
                            case "privatechat":
                                String jugador = arrayComandos[1];
                                String mensajePrivado = arrayComandos[2];
                                server.controlMain.chatPrivado(mensajePrivado, nombre, jugador);
                                break;
                            case "reload":
                                break;
                            case "select":
                                String jugadorSeleccionado = arrayComandos[1];
                                server.controlMain.seleccionarJugador(jugadorSeleccionado);
                                break;    
                            case "wildcard":
                                break;
                        }
                        break;
                    case 3:
                        System.out.println("instruccion 3!!!");
                        writer.writeInt(3);
                        
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
