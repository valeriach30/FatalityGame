/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Comunicaciones;

import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author vchin
 */
public class Cliente implements iObserver{
    Socket socketRef;
    //ClientForm refPantalla;
    public ThreadCliente hiloCliente;
    public String name;
    
    public Cliente() {
        
    }
    
    public void conectar(){
 
        try{
//            socketRef = new Socket("localhost", 35775);
//            hiloCliente = new ThreadCliente(socketRef, refPantalla);
//            hiloCliente.start();
//            
//            String nombre = JOptionPane.showInputDialog("Introduzca un Nick:");
//            name = nombre;
//            hiloCliente.writer.writeInt(1); //instruccion para el switch del thraed servidor
//            hiloCliente.writer.writeUTF(nombre); //instruccion para el switch del thraed servidor
//            hiloCliente.writer.writeInt(1500);
//            hiloCliente.writer.writeInt(Ficha);
//            refPantalla.setTitle(nombre);
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
