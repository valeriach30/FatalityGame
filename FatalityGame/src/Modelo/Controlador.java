/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Communication.Servidor;
import Communication.ThreadServidor;
import Communication.iObserved;
import Communication.iObserver;
import Juego.Armas.Arma;
import Juego.Personaje.Personaje;
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
    public ArrayList<String> nombres = null;
    private Servidor server;
    public DataInputStream reader;
    public DataOutputStream writer;
    public ObjectInputStream Objectreader;
    public ObjectOutputStream Objectwriter;
    CommandManager manager = CommandManager.getIntance(); 
    public Personaje personajeAtacante;

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
    
    
    public Integer determinarAtaqueValido(String nombre, String victima, String personaje, String arma, Integer pasada) {
        Jugador jugadorAct = null;
        for (int i = 0; i < juego.getJugadores().size(); i++) {
            // Jugador actual
            if(juego.getJugadores().get(i).getNombre().equals(nombre)){
                jugadorAct = juego.getJugadores().get(i);
            }
        }
        if(jugadorAct != null){
            // Determinar si tiene ese personaje
            Personaje personajeAct = null;
            for (int j = 0; j < jugadorAct.getPersonajes().size(); j++) {
                if(jugadorAct.getPersonajes().get(j).getNombre().equals(personaje)){
                    personajeAct = jugadorAct.getPersonajes().get(j);
                }
            }

            Arma armaActual = null;
            if(personajeAct != null){
                for (int k = 0; k < personajeAct.getArmas().size(); k++) {
                    // Obtener el arma 
                    if(personajeAct.getArmas().get(k).getName().equals(arma)){
                        armaActual = personajeAct.getArmas().get(k);
                    }
                }
                if(armaActual != null){
                    if(armaActual.isAvailable()){
                        if(pasada == 2){
                            armaActual.setAvailable(false);
                        }
                        // ataque 
                        personajeAtacante = personajeAct;
                        // determinar la cantidad de danho que le va a hacer a los personajes
                        Integer danhoFinal = getDanho(personajeAct, armaActual);
                        return danhoFinal;
                    }
                    else{
                        return 0;
                    }
                }else{
                    // ataque invalido: el arma no existe
                    return -1;
                }
            }
            else{
                // ataque invalido: el personaje no existe
                return -2;
            }
        }
        else{
            // ataque invalido: el jugador no existe
            return -3;
        }
    }
    
    public Jugador getJugador(String nombre){
        Jugador jugadorAct = null;
        for (int i = 0; i < juego.getJugadores().size(); i++) {
            // Jugador actual
            if(juego.getJugadores().get(i).getNombre().equals(nombre)){
                jugadorAct = juego.getJugadores().get(i);
            }
        }
        return jugadorAct;
    }
    
    public Integer getDanho(Personaje per, Arma arma){
        String tipo = per.getNombreCategoria();
        Integer danho = 0;
        switch(tipo){
            case "fuego":
                danho = arma.getDanhos().get(0);
                break;
            case "aire":
                danho = arma.getDanhos().get(1);
                break;
            case "agua":
                danho = arma.getDanhos().get(2);
                break;
            case "magia blanca":
                danho = arma.getDanhos().get(3);
                break;
            case "magia negra":
                danho = arma.getDanhos().get(4);
                break;
            case "electricidad":
                danho = arma.getDanhos().get(5);
                break;
            case "hielo":
                danho = arma.getDanhos().get(6);
                break;
            case "acid":
                danho = arma.getDanhos().get(7);
                break;
            case "espiritualidad":
                danho = arma.getDanhos().get(8);
                break;
            case "hierro":
                danho = arma.getDanhos().get(9);
                break;
        }
        return danho;
    }
    
    public ArrayList<Personaje> getVictimas(String nombreJugador, String categoria){
        ArrayList<Personaje> victimas = new ArrayList<Personaje>();
        Jugador victimaJugador = getJugador(nombreJugador);
        for (int i = 0; i < victimaJugador.getPersonajes().size(); i++) {
            String tipoCatPerActual = victimaJugador.getPersonajes().get(i).getNombreCategoria();
            if(tipoCatPerActual.equals(categoria)){
                victimas.add(victimaJugador.getPersonajes().get(i));
            }
        }
        return victimas;
    }
    
    public ArrayList<Integer> getIndicesVictimas(String nombreJugador, String categoria){
        ArrayList<Integer> indices = new ArrayList<Integer>();
        
        Jugador victimaJugador = getJugador(nombreJugador);
        for (int i = 0; i < victimaJugador.getPersonajes().size(); i++) {
            String tipoCatPerActual = victimaJugador.getPersonajes().get(i).getNombreCategoria();
            if(tipoCatPerActual.equals(categoria)){
                indices.add(i);
            }
        }
        return indices;
    }
    
    public boolean nombreValido(String nombre){
        
        if(nombres != null){
            System.out.println(nombres.toString());
            if(nombres.contains(nombre)){
                return false;
            }
        }
        return true;
    }
    
    public void agregarNombre(String nombreP){
        if(nombres == null){
            ArrayList<String> nombre = new ArrayList<String>(); 
            nombre.add(nombreP);
            nombres = nombre;
        }
        else{
            nombres.add(nombreP);
        }
    }
    
    public void eliminarJugador(String nombreJ){
        for (int i = 0; i < juego.getJugadores().size(); i++) {
            // Jugador actual
            if(juego.getJugadores().get(i).getNombre().equals(nombreJ)){
                juego.getJugadores().remove(i);
            }
        }
    }
    
    public void actualizarIndices(){
        for (int i = 0; i < server.conexiones.size(); i++) {
            ThreadServidor current = server.conexiones.get(i);
            if(current.id != 0){
                current.id--;
            }
        }
    }
}
