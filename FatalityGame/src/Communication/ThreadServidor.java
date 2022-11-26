/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication;

import Juego.Armas.Arma;
import Juego.Personaje.Personaje;
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
                        writer.writeInt(1);
                        writer.writeInt(id);
                        writer.writeInt(server.getTurno());
                        break;
                    
                    //----------------------------COMMANDOS----------------------------
                    case 2:
                        String[] arrayComandos = (String[]) Objectreader.readObject();
                        switch(arrayComandos[0]){
                            case "attack":
                                String jugadorEnemigo = arrayComandos[1];
                                String personaje = arrayComandos[2];
                                String arma = arrayComandos[3];
                                server.controlMain.attack(nombre, jugadorEnemigo, personaje, arma);
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
        switch(command){
            case "chat":
                try {
                    writer.writeInt(2);
                    writer.writeUTF("chat");
                    writer.writeUTF((String)source);
                } catch (IOException ex) {
                    Logger.getLogger(ThreadServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "privatechat":
                try {
                    writer.writeInt(2);
                    writer.writeUTF("privatechat");
                    writer.writeUTF((String)source);
                } catch (IOException ex) {
                    Logger.getLogger(ThreadServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "select":
                try {
                    writer.writeInt(2);
                    writer.writeUTF("select");
                } catch (IOException ex) {
                    Logger.getLogger(ThreadServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "pass":
                try{
                    writer.writeInt(2);
                    writer.writeUTF("pass");
                    writer.writeInt((Integer)source);
                } catch (IOException ex) {
                    Logger.getLogger(ThreadServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "giveup":
                break;
            case "reload":
                break;
            case "wildcard":
                break;
            case "groupexit":
                break;
            // Ataque del enemigo
            case "attack":
                // Info
                ArrayList<String> infoAtaque = (ArrayList<String>)source;
                String victima= infoAtaque.get(0);
                String personaje= infoAtaque.get(1);
                String arma= infoAtaque.get(2);
                Integer respuesta = determinarAtaqueValido(nombre, victima, personaje, arma);
                
                try{
                    writer.writeInt(2);
                    writer.writeUTF("attack");
                    writer.writeUTF(victima);
                    writer.writeUTF(personaje);
                    writer.writeUTF(arma);
                } catch (IOException ex) {
                    Logger.getLogger(ThreadServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            default:
                break;
        }
    }

    private Integer determinarAtaqueValido(String nombre, String victima, String personaje, String arma) {
        // Determinar aca si el jugador tiene ese personaje y si el arma esta activa
        // si no es asi entonces writer.writeInt(4) y va al thread lciente para escribir el error
        Jugador jugadorAct = null;
        for (int i = 0; i < server.controlMain.juego.getJugadores().size(); i++) {
            // Jugador actual
            if(server.controlMain.juego.getJugadores().get(i).getNombre().equals(nombre)){
                jugadorAct = server.controlMain.juego.getJugadores().get(i);
            }
        }
        
        // Determinar si tiene ese personaje
        Personaje personajeAct = null;
        for (int j = 0; j < jugadorAct.getPersonajes().size(); j++) {
            if(jugadorAct.getPersonajes().get(j).getNombre().equals(personaje)){
                personajeAct = jugadorAct.getPersonajes().get(j);
            }
        }
        
            
        if(personajeAct != null){
            for (int k = 0; k < personajeAct.getArmas().size(); k++) {
                Arma armaActual = personajeAct.getArmas().get(k);
                // Desactivar arma
                if(armaActual.getName().equals(arma)){
                    armaActual.setAvailable(false);
                }
            }
        }
//        else{
//            // error
//            return -1;
               // writer.writeint
//        }
        return -1;
    }
}
