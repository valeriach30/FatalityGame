/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication;

import fatalitygame.Main;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author vchin
 */
public class Cliente {
    public Socket socketRef;
    public Main refPantalla;
    public ThreadCliente hiloCliente;
    public String name;
    public int Ficha;
    
    public Cliente(Main refPantalla, int numFicha) {
        this.refPantalla = refPantalla;
        this.Ficha = numFicha;
        this.refPantalla.setRefCliente(this);
    }
    
    public void conectar(){
 
        try{
            this.refPantalla.setRefCliente(this);
            socketRef = new Socket("localhost", 35775);
            hiloCliente = new ThreadCliente(socketRef, refPantalla);
            hiloCliente.start();
            
            String nombre = JOptionPane.showInputDialog("Introduzca un Nick:");
            name = nombre;
            hiloCliente.writer.writeInt(1); //instruccion para el switch del thraed servidor
            hiloCliente.writer.writeUTF(nombre); //instruccion para el switch del thraed servidor
            hiloCliente.writer.writeInt(1500);
            hiloCliente.writer.writeInt(Ficha);
            refPantalla.setTitle(nombre);
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        
        
        
    }

    
}
