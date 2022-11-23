/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication;

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
public class Cliente implements IObserved{
    public Socket socketRef;
    public Main refPantalla;
    public ThreadCliente hiloCliente;
    public String name;
    public int Ficha;
    private static Cliente client;
    private final List<iObserver> observers = new ArrayList<>();
    
    public Cliente(){
    }
    
    public Cliente(Main refPantalla, int numFicha) {
        this.refPantalla = refPantalla;
        this.Ficha = numFicha;
        this.refPantalla.setRefCliente(this);
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
            hiloCliente = new ThreadCliente(socketRef, refPantalla);
            hiloCliente.start();
            
            String nombre = JOptionPane.showInputDialog("Introduzca un Nick:");
            name = nombre;
            hiloCliente.writer.writeInt(1); //instruccion para el switch del thraed servidor
            hiloCliente.writer.writeUTF(nombre); //instruccion para el switch del thraed servidor
            refPantalla.setTitle(nombre);
            
        }
        catch(Exception e){
            System.out.println(e.getMessage());
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
