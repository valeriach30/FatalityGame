/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Communication.Servidor;
import Communication.ThreadServidor;
import Communication.iObserved;
import Communication.iObserver;
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
public class Controlador implements iObserved{
    public Juego juego = new Juego();
    public ArrayList<iObserver> observers;
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
    
    public void rendirse(String nombre){
        ArrayList<String> commandArgs = new ArrayList<String>();
        commandArgs.add(nombre);
        ICommand command = manager.getCommand("giveup");   
        command.execute(commandArgs, System.out, server.conexiones);        
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

    public void attack(String nombre, String jugadorEnemigo, String personaje, String arma) {
        // calcula el dano aca?
        ArrayList<String> commandArgs = new ArrayList<String>();
        commandArgs.add(nombre);
        commandArgs.add(jugadorEnemigo);
        commandArgs.add(personaje);
        commandArgs.add(arma);
        ICommand command = manager.getCommand("attack");   
        command.execute(commandArgs, System.out, server.conexiones);        
    }
}
