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

/**
 *
 * @author vchin
 */
public class ThreadCliente extends Thread implements iObserver{

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
                                    refPantalla.addLine();
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
                                    ArrayList<Integer> indices = (ArrayList<Integer>) Objectreader.readObject();
                                    refPantalla.ultimoAtaqueRecibido(nombreAtacante, imagen, 
                                    tipoAtacante, jugadorAtacante, danhoAtacante, indices, arma);
                                }
                                break;
                            case "chat":
                                String mensaje = reader.readUTF();
                                refPantalla.addMensaje(mensaje);
                                break;
                            case "giveup":
                                player.setActivo(false);
                                // tal vez haya que agregar alguna logica aca para eliminar 
                                // el jugador de las conexiones y para que no haya errores
                                // en el turno
                                refPantalla.addMensaje("Ya no puede participar en el juego" + "\n>");
                                break;
                            case "groupexit":
                                if(player.isActivo()){
                                    // groupexit
                                }
                                break;
                            case "pass":
                                if(player.isActivo()){
                                    this.turnoActual = reader.readInt(); 
                                }
                                break;
                            case "privatechat":
                                String mensaje2 = reader.readUTF();
                                refPantalla.addMensaje(mensaje2);
                                break;
                            case "reload":
                                if(player.isActivo()){
                                    // reload
                                }
                                break;
                            case "select":
                                refPantalla.infoJugador(player);
                                break;    
                            case "wildcard":
                                if(player.isActivo()){
                                    // wildcard
                                }
                                break;
                        }
                        break;
                    case 3:
                        refPantalla.addMensaje("No es su turno \n>");
                        break;
                    case 4:
                        refPantalla.addMensaje(reader.readUTF() + "\n>");
                        break;
                    default:
                        break;
                }
            } catch (IOException ex) {
                System.out.println("error cliente");
                System.out.println(ex.toString());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ThreadCliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public int getIdentificador() {
        return id;
    }

    public int getTurnoActual() {
        return turnoActual;
    }

   @Override
    public void notificar(String command, Object source) {
        // hace algo de la pantalla aca
    }
    
}
