/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Comunicaciones;

import Juego.Personaje.Personaje;
import fatalitygame.Main;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author vchin
 */
public class Cliente implements iObserver{
    Socket socketRef;
    Main refPantalla;
    public ThreadCliente hiloCliente;
    public String name;
    public ArrayList<Personaje> personajes;
    
    public Cliente(Main refPantalla, ArrayList<Personaje> personajes) {
        this.refPantalla = refPantalla;
        this.personajes = personajes;
    }
    
    public void conectar(){
 
        try{
            socketRef = new Socket("localhost", 35775);
            hiloCliente = new ThreadCliente(socketRef, refPantalla);
            hiloCliente.start();
            
            this.name = JOptionPane.showInputDialog("Introduzca su nickname:");
            
            hiloCliente.writer.writeInt(1); //instruccion para el switch del thraed servidor
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    @Override
    public void notificar(iClientMessage msg) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
