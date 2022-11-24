/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication;
import Libreria.Juego.Jugador;
import fatalitygame.Main;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author vchin
 */
public class ThreadCliente extends Thread implements iObserved{

    private Socket socketRef;
    public DataInputStream reader;
    public DataOutputStream writer;
    public ObjectInputStream Objectreader;
    public ObjectOutputStream Objectwriter;
    private Jugador player;
    private String nombre;
    private boolean running = true;
    private Main  refPantalla;
    private int id ;
    private int turnoActual = 0;
    private int FichaActual;
    
    public ThreadCliente(Socket socketRef, Main refPantalla, Jugador jugador) throws IOException {
        this.socketRef = socketRef;
        Objectwriter = new ObjectOutputStream(socketRef.getOutputStream());
        Objectreader = new ObjectInputStream(socketRef.getInputStream());
        reader = new DataInputStream(socketRef.getInputStream());
        writer = new DataOutputStream(socketRef.getOutputStream());
        
        this.refPantalla = refPantalla;
        this.player = jugador;
    }
    
    public void run (){
        int instruccionId = 1;
        while (running){
            try {
                instruccionId = reader.readInt(); // esperar hasta que reciba un entero
                System.out.println("instruccion: " + instruccionId);
                switch (instruccionId){
                    case 1:
                        this.id = reader.readInt();
                        this.turnoActual = reader.readInt();
                        refPantalla.setID(id);
                        break;
                    //----------------------------COMMANDOS----------------------------
                    case 2:
                        String usuario = reader.readUTF();
                        String mensaje = reader.readUTF();
                        System.out.println("CLIENTE Recibido mensaje: " + mensaje);
                        refPantalla.addMensaje(usuario+"> " + mensaje);
                        break;
                    case 3:
                        break;
                    default:
                        break;
                }
            } catch (IOException ex) {
                System.out.println("error cliente");
                System.out.println(ex.toString());
            }
        }
    }

    public int getIdentificador() {
        return id;
    }

    public int getTurnoActual() {
        return turnoActual;
    }
    
    public int getFichaActual() {
        return FichaActual;
    }

    public void setFichaActual(int FichaActual) {
        this.FichaActual = FichaActual;
    }

    @Override
    public void notificarTodos(String command, Object source) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void agregarObserver(iObserver observer) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
