/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication;

import Libreria.Juego.Juego;
import Libreria.Juego.Jugador;
import Modelo.Controlador;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.Timer;

/**
 *
 * @author vchin
 */
public class Servidor {
    ServerForm refPantalla;
    public ArrayList<ThreadServidor> conexiones;
    private boolean running = true;
    private ServerSocket srv;
    private int turno;
    public boolean partidaIniciada = false;
    public Controlador controlMain = new Controlador(this);
    
    public Servidor(ServerForm refPantalla) throws IOException{
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
    public void setTurno(int nuevoT){
        nuevoT += 1;
        if (nuevoT >= conexiones.size()){
            this.turno = 0;
        }else{
            this.turno = nuevoT;
        }
    }
    
    public void timerComodin(){
        
    }
    public void runServer() throws IOException{
        
        int contador = 0;
        srv = new ServerSocket(35775);
        
        while(running){
            refPantalla.addMensaje(".: Esperando conexiones");
            Socket refSocket = srv.accept();
            if(!partidaIniciada){
                timerComodin();
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

}
