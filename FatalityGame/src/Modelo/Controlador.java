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
        for (int i = 0; i < server.conexiones.size(); i++) {
            ThreadServidor current = server.conexiones.get(i);
            current.writer.writeInt(2);
            current.writer.writeUTF(nombre);
            current.writer.writeUTF(mensaje);
        }
    }
}
