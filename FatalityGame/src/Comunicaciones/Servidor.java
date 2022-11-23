/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Comunicaciones;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
//import Libreria.Juego.Jugador;
/**
 *
 * @author vchin
 */
public class Servidor implements iObserved{
    //public ArrayList<Jugador> jugadores;
    public ArrayList<ThreadServidor> conexiones;
    private boolean running = true;
    private ServerSocket srv;
    
    @Override
    public void notificarTodos(iClientMessage msg) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Hay que agregar aqui los jugadores que estan observando la partida
    @Override
    public void agregarObserver(iObserver observer) {
//        if(!jugadores.contains(observer)){
//            this.jugadores.add((Jugador) observer);
//        }
    }
    public void runServer() throws IOException{
        
        int contador = 0;
        srv = new ServerSocket(35775);
        System.out.println("hola???");
        while(running){
            System.out.println("entre");
            Socket refSocket = srv.accept();
            // Thread
            ThreadServidor newThread = new ThreadServidor(refSocket, this, conexiones.size());
            conexiones.add(newThread);
            newThread.start();
            System.out.println("conexiones:" + conexiones.toString());
        }
    }

}
