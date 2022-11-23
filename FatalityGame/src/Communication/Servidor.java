/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication;

import Libreria.Juego.Juego;
import Libreria.Juego.Jugador;
import fatalitygame.Controlador;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author vchin
 */
public class Servidor implements iObserved{
    ServerForm refPantalla;
    public ArrayList<ThreadServidor> conexiones;
    private boolean running = true;
    private ServerSocket srv;
    private int turno;
    private int FichaTurno;
    private boolean partidaIniciada = false;
    private final ArrayList<iObserver> observers = new ArrayList<iObserver>();
    public Controlador controlMain = new Controlador();
    
    public Servidor(ServerForm refPantalla) {
        this.refPantalla = refPantalla;
        conexiones = new ArrayList<ThreadServidor>();
        this.refPantalla.setSrv(this);
    }
    
    public void iniciarPartida(){
        this.partidaIniciada = true;
    }
    public void stopserver(){
        running = false;
    }
    public int getTurno() {
        return turno;
    }
    
    public void runServer() throws IOException{
        
        int contador = 0;
        srv = new ServerSocket(35775);
        
        while(running){
            refPantalla.addMensaje(".: Esperando conexiones");
            Socket refSocket = srv.accept();
            
            if(!partidaIniciada){
                refPantalla.addMensaje(".: Conexion realizada: " + (++contador));

                // Thread
                ThreadServidor newThread = new ThreadServidor(refSocket, this, conexiones.size());
                conexiones.add(newThread);
                
                newThread.start();
            }
            else
                refPantalla.addMensaje(".: Conexion denegada, partida ya inicio");
        }
    }

    @Override
    public void notificarTodos(String command, Object source) {
        for (iObserver observer : observers) {               
            observer.notificar(command, source);
        }
    }

    @Override
    public void agregarObserver(iObserver observer) {
        this.observers.add(observer);
    }
}
