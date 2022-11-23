/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication;

import Juego.Personaje.Personaje;
import Libreria.Juego.Jugador;
import fatalitygame.Main;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author vchin
 */
public class Cliente implements iObserver{
    public Socket socketRef;
    public Main refPantalla;
    public ThreadCliente hiloCliente;
    public String name;
    public Jugador player;
    private static Cliente client;
    private ArrayList<Personaje> personajes = new ArrayList<Personaje>();
    
    public Cliente(){
    }
    
    public Cliente(Main refPantalla) {
        this.refPantalla = refPantalla;
        this.refPantalla.setRefCliente(this);
        Jugador player = new Jugador();
        this.player = player;
    }
    public Cliente(Main refPantalla, ArrayList<Personaje> personajes) {
        this.refPantalla = refPantalla;
        this.refPantalla.setRefCliente(this);
        // Crear jugador
        Jugador player = new Jugador();
        player.setPersonajes(personajes);
        this.player = player;
    }
    public static Cliente getInstance(Main refPantalla) throws UnknownHostException{
        if(client==null){
            client = new Cliente();
            client.refPantalla = refPantalla;
        }
        return client;
    }
    
    public void conectar(){
 
        try{
            this.refPantalla.setRefCliente(this);
            
            socketRef = new Socket("localhost", 35775);
            hiloCliente = new ThreadCliente(socketRef, refPantalla, player);
            hiloCliente.start();
            
            String nombre = JOptionPane.showInputDialog("Nickname:");
            this.name = nombre;
            this.player.setNombre(nombre);
            hiloCliente.writer.writeInt(1); //instruccion para el switch del thraed servidor
            hiloCliente.writer.writeUTF(nombre); //instruccion para el switch del thraed servidor
            hiloCliente.Objectwriter.writeObject(player);
            refPantalla.setTitle(nombre);
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void notificar(String command, Object source) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
