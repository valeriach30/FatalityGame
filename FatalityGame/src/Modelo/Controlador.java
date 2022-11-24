/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Communication.Servidor;
import Communication.ThreadServidor;
import Libreria.Juego.Juego;
import Libreria.Juego.Jugador;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 *
 * @author vchin
 */
public class Controlador {
    public Juego juego = new Juego();
    public ArrayList<ThreadServidor> conexiones;
    private Servidor server;
    public DataInputStream reader;
    public DataOutputStream writer;
    public ObjectInputStream Objectreader;
    public ObjectOutputStream Objectwriter;
    CommandManager manager = CommandManager.getIntance(); 

    public Controlador(Servidor server){
        this.server = server;
    }
    
    void crear() {
        // nada
    }
    
    public void agregarJugador(Jugador jugador){
        if(juego.getJugadores() != null){
            juego.getJugadores().add(jugador);
        } else{
            ArrayList<Jugador> Jugadores = new ArrayList<Jugador>();
            Jugadores.add(jugador);
            juego.setJugadores(Jugadores);
        }
    }
    
    public void chat(String mensaje, String nombre) throws IOException{
        ArrayList<String> commandArgs = new ArrayList<String>();
        commandArgs.add(mensaje);
        commandArgs.add(nombre);
        ICommand command = manager.getCommand("chat");   
        command.execute(commandArgs, System.out, server.conexiones);        
    }
    
    public void chatPrivado(String mensaje, String nombre, String jugador){
        ArrayList<String> commandArgs = new ArrayList<String>();
        commandArgs.add(mensaje);
        commandArgs.add(nombre);
        commandArgs.add(jugador);
        ICommand command = manager.getCommand("privatechat");   
        command.execute(commandArgs, System.out, server.conexiones);        
    }

    public void pasarTurno(int turno) {
        server.setTurno(turno++);
        ArrayList<String> commandArgs = new ArrayList<String>();
        commandArgs.add(Integer.toString(turno));
        ICommand command = manager.getCommand("pass");   
        ArrayList<String> resultados = command.execute(commandArgs, System.out, server.conexiones);        
    }
    
    public void seleccionarJugador(String jugador){
        ArrayList<String> commandArgs = new ArrayList<String>();
        commandArgs.add(jugador);
        ICommand command = manager.getCommand("select");   
        command.execute(commandArgs, System.out, server.conexiones);        
    }
}
