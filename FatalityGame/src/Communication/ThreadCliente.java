/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication;
import Juego.Armas.Arma;
import Juego.Personaje.Personaje;
import Libreria.Juego.Jugador;
import fatalitygame.Main;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author vchin
 */
public class ThreadCliente extends Thread{

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
    
    public ThreadCliente(Socket socketRef, Main refPantalla, Jugador jugador) throws IOException {
        this.socketRef = socketRef;
        Objectwriter = new ObjectOutputStream(socketRef.getOutputStream());
        Objectreader = new ObjectInputStream(socketRef.getInputStream());
        reader = new DataInputStream(socketRef.getInputStream());
        writer = new DataOutputStream(socketRef.getOutputStream());
        
        this.refPantalla = refPantalla;
        this.player = jugador;
        player.setActivo(true);
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
                        nombre = reader.readUTF();
                        this.player.setNombre(nombre);
                        refPantalla.pintarSiguienteTurno (reader.readUTF());
                        refPantalla.setTitle(nombre);
                        refPantalla.setVisible(true);
                        break;
                    //----------------------------COMMANDOS----------------------------
                    case 2:
                        String comando = reader.readUTF();
                        switch(comando){
                            case "attack":
                                if(player.isActivo()){
                                    String victima = reader.readUTF();
                                    String imagenAtacante = reader.readUTF();
                                    String arma = reader.readUTF();
                                    String tipoPersonaje = reader.readUTF();
                                    Integer danho = reader.readInt();
                                    refPantalla.ultimoAtaqueHecho(victima, imagenAtacante, arma, tipoPersonaje, danho);
                                }
                                break;
                            case "attackVictim":
                                if(player.isActivo()){
                                    String nombreAtacante = reader.readUTF();
                                    String imagen = reader.readUTF();
                                    String tipoAtacante = reader.readUTF();
                                    String jugadorAtacante = reader.readUTF();
                                    String arma = reader.readUTF();
                                    Integer danhoAtacante = reader.readInt();
                                    String ataque = reader.readUTF();
                                    boolean perdio = reader.readBoolean();
                                    
                                    ArrayList<Integer> indices = (ArrayList<Integer>) Objectreader.readObject();
                                    refPantalla.ultimoAtaqueRecibido(nombreAtacante, imagen, 
                                    tipoAtacante, jugadorAtacante, danhoAtacante, indices, arma);
                                    refPantalla.addMensaje(ataque);
                                    refPantalla.addLine();
                                    
                                    if(perdio){
                                        refPantalla.addMensaje("Ha perdido :(" + "\n>");
                                        refPantalla.desactivarBotones();
                                    }
                                }
                                break;
                            case "chat":
                                String mensaje = reader.readUTF();
                                refPantalla.addMensaje(mensaje);
                                break;
                            case "giveup":
                                player.setActivo(false);
                                refPantalla.addMensaje("Ya no puede participar en el juego" + "\n>");
                                refPantalla.desactivarConsola();
                                break;
                            case "groupexit":
                                if(player.isActivo()){
                                    String remitente = reader.readUTF();
                                    refPantalla.salidadGrupal(remitente);
                                }
                                break;
                            case "pass":
                                if(player.isActivo()){
                                    this.turnoActual = reader.readInt();
                                    refPantalla.pintarSiguienteTurno (reader.readUTF());
                                    refPantalla.addLine();
                                }
                                break;
                            case "privatechat":
                                String mensaje2 = reader.readUTF();
                                refPantalla.addMensaje(mensaje2);
                                break;
                            case "reload":
                                if(player.isActivo()){
                                    refPantalla.addMensaje("Armas recargadas!");
                                }
                                break;
                            case "select":
                                refPantalla.infoJugador(player);
                                break;    
                            case "wildcard":
                                if(player.isActivo()){
                                    refPantalla.addMensaje("Comodin ejecutandose/ejecutado");
                                }
                                break;
                            case "log":
                                refPantalla.addMensaje(reader.readUTF());
                                break;
                        }
                        break;
                    case 3:
                        refPantalla.addMensaje("No es su turno \n>");
                        break;
                    case 4:
                        refPantalla.addMensaje(reader.readUTF() + "\n>");
                        break;
                    case 5:
                        refPantalla.comodin();
                        break;
                    case 6:
                        refPantalla.addMensaje("No es su turno o no tiene un comodin habilitado\n>");
                    case 7:
                        refPantalla.ganador();
                        break;
                    case 8:
                        refPantalla.desactivarConsola();
                        refPantalla.avisoEmpate();
                    case 9:
                        refPantalla.actualizarStatus(reader.readUTF());
                        refPantalla.actualizarAgainst(reader.readUTF());
                        refPantalla.actualizarRanking(reader.readUTF());
                    default:
                        break;
                }
            } catch (Exception ex) {
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
}
