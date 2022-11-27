/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Communication;

import Juego.Armas.Arma;
import Juego.Personaje.Personaje;
import Libreria.Juego.Jugador;
import Modelo.ChatCommand;
import fatalitygame.Main;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import static java.lang.Math.random;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author vchin
 */
public class ThreadServidor extends Thread implements iObserver{
    
    public int id;
    private Socket socketRef;
    public DataInputStream reader;
    public DataOutputStream writer;
    public ObjectInputStream Objectreader;
    public ObjectOutputStream Objectwriter;
    public String nombre;
    private boolean running = true;
    Servidor server;
    private boolean comodinHabilitado = false;
    long startTime = System.currentTimeMillis();
    
    
    public ThreadServidor(Socket socketRef, Servidor server, int id) throws IOException {
        this.socketRef = socketRef;
        reader = new DataInputStream(socketRef.getInputStream());
        writer = new DataOutputStream(socketRef.getOutputStream());
        Objectreader = new ObjectInputStream(socketRef.getInputStream());
        Objectwriter = new ObjectOutputStream(socketRef.getOutputStream());
        this.server = server;
        this.id = id;
    }
    
    public void run (){
        
        int instruccionId = 1;
        while (running){
            try {
                instruccionId = reader.readInt(); // esperar hasta que reciba un entero
                System.out.println("instruccion server: " + instruccionId);
                
                // Timer
                long elapsedTime = System.currentTimeMillis() - startTime;
                long elapsedSeconds = elapsedTime / 1000;
                long elapsedMinutes = elapsedSeconds / 60;
                
                // Numero random
                Random random=new Random();
                int randomNumber=(random.nextInt(65536)-32768);
                
                // Dar comodin
                if(elapsedMinutes >= 1 && comodinHabilitado == false /*&& randomNumber > 0*/){ // CAMBIAR A 5 MINUTOS Y HABILITAR RANDOM
                    comodinHabilitado = true;
                    writer.writeInt(5);
                    startTime = System.currentTimeMillis();
                }

                switch (instruccionId){
                    //----------------------------INICIO----------------------------
                    case 1:
                        nombre = JOptionPane.showInputDialog("Nickname:");
                        while(server.controlMain.nombreValido(nombre) == false){
                            nombre = JOptionPane.showInputDialog("Nickname:");
                        }
                        server.controlMain.agregarNombre(nombre);
                        
                        Jugador player = (Jugador) Objectreader.readObject();
                        player.setNombre(nombre);
                        player.setActivo(true);
                        server.controlMain.agregarJugador(player);
                        writer.writeInt(1);
                        writer.writeInt(id);
                        writer.writeInt(server.getTurno());
                        writer.writeUTF(nombre);
                        writer.writeUTF(server.conexiones.get(server.getTurno()).nombre);
                        break;
                    
                    //----------------------------COMMANDOS----------------------------
                    case 2:
                        String[] arrayComandos = (String[]) Objectreader.readObject();
                        
                        switch(arrayComandos[0]){
                            
                            //----------------------------ATACAR----------------------------
                            case "attack":
                                if(this.id == server.getTurno()){
                                    String jugadorEnemigo = arrayComandos[1];
                                    String personaje = arrayComandos[2];
                                    String arma = arrayComandos[3];
                                    server.controlMain.attack(nombre, jugadorEnemigo, personaje, arma);
                                    if(server.controlMain.lastArma != null){
                                        server.controlMain.lastArma.setAvailable(false);
                                    }
                                }
                                else{
                                    error();
                                }
                                break;
                                
                            //----------------------------CHAT----------------------------
                            case "chat":
                                String mensaje = arrayComandos[1];
                                server.controlMain.chat(mensaje, nombre);
                                break;
                                
                            //----------------------------GIVEUP----------------------------
                            case "giveup":
                                server.controlMain.rendirse(nombre);
                                break;
                                
                            //----------------------------GROUP EXIT----------------------------
                            case "groupexit":
                                server.controlMain.salidaGrupal(nombre);
                                break;
                            
                            //----------------------------PASS----------------------------
                            case "pass":
                                if(this.id == server.getTurno()){
                                    server.controlMain.pasarTurno(server.getTurno());
                                }
                                else{
                                    error();
                                }
                                break;
                            
                            //----------------------------PRIVATE CHAT----------------------------
                            case "privatechat":
                                String jugador = arrayComandos[1];
                                String mensajePrivado = arrayComandos[2];
                                server.controlMain.chatPrivado(mensajePrivado, nombre, jugador);
                                break;
                                
                            //----------------------------RELOAD----------------------------
                            case "reload":
                                // Determinar si es su turno
                                if(this.id == server.getTurno()){
                                    Integer respuesta = server.controlMain.recargar(nombre);
                                    if(respuesta == -1){
                                        try {
                                            writer.writeInt(4);
                                            writer.writeUTF("Alguna de las armas sigue disponible");
                                        } catch (IOException ex) {
                                            Logger.getLogger(ThreadServidor.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }
                                }
                                else{
                                    error();
                                }
                                break;
                            
                            //----------------------------SELECT----------------------------
                            case "select":
                                String jugadorSeleccionado = arrayComandos[1];
                                server.controlMain.seleccionarJugador(jugadorSeleccionado);
                                break;    
                                
                            //----------------------------WILDCARD----------------------------
                            case "wildcard":
                                // Determinar si es su turno
                                String victimaJ;
                                String personaje1;
                                String personaje2;
                                String arma1;
                                String arma2;
                                if(this.id == server.getTurno() && comodinHabilitado == true){
                                    // Determinar si esta atacando con dos personajes o con dos armas
                                    if(arrayComandos.length == 6){
                                        // Comodin: dos jugadores
                                        victimaJ = arrayComandos[1];
                                        personaje1 = arrayComandos[2];
                                        arma1 = arrayComandos[3];
                                        personaje2 = arrayComandos[4];
                                        arma2 = arrayComandos[5];
                                        server.controlMain.comodinJugadores(nombre, victimaJ, personaje1, arma1, personaje2, arma2);
                                    }
                                    else{
                                        if(arrayComandos.length == 5){
                                            // Comodin dos armas
                                            victimaJ = arrayComandos[1];
                                            personaje1 = arrayComandos[2];
                                            arma1 = arrayComandos[3];
                                            arma2 = arrayComandos[4];
                                            server.controlMain.comodinArmas(nombre, victimaJ, personaje1, arma1, arma2);
                                        }
                                    }
                                }
                                else{
                                    error2();
                                }
                                break;
                            //----------------------------DESACTIVAR----------------------------
                            case "desactivar":
                                server.controlMain.desactivarArmas(nombre);
                                writer.writeInt(4);
                                writer.writeUTF("Armas desactivadas");
                                break;
                        }
                        break;
                    //----------------------------OTROS----------------------------
                    case 3:
                        writer.writeInt(3);
                        break;
                    default:
                        break;
                }
            } catch (IOException ex) {
                System.out.println("error servidor");
                System.out.println(ex.toString());
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ThreadServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
    
    // -------------------------------------- OBSERVER --------------------------------------
    @Override
    public void notificar(String command, Object source) {
        switch(command){
            // --------------------- CHAT ---------------------
            case "chat":
                try {
                    writer.writeInt(2);
                    writer.writeUTF("chat");
                    writer.writeUTF((String)source);
                } catch (IOException ex) {
                    Logger.getLogger(ThreadServidor.class.getName())
                          .log(Level.SEVERE, null, ex);
                }
                break;
            
            // --------------------- CHAT PRIVADO ---------------------
            case "privatechat":
                try {
                    writer.writeInt(2);
                    writer.writeUTF("privatechat");
                    writer.writeUTF((String)source);
                } catch (IOException ex) {
                    Logger.getLogger(ThreadServidor.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
                break;
                
            // --------------------- SELECT ---------------------
            case "select":
                try {
                    writer.writeInt(2);
                    writer.writeUTF("select");
                } catch (IOException ex) {
                    Logger.getLogger(ThreadServidor.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
                break;
            
            // --------------------- PASS ---------------------
            case "pass":
                try{
                    Integer turno = (Integer)source;
                    String nombreDelTurno = server.conexiones.get(server.getTurno()).nombre;
                    writer.writeInt(2);
                    writer.writeUTF("pass");
                    writer.writeInt(turno);
                    writer.writeUTF(nombreDelTurno);
                } catch (IOException ex) {
                    Logger.getLogger(ThreadServidor.class.getName())
                    .log(Level.SEVERE, null, ex);
                }
                break;
                
            // --------------------- GIVEUP ---------------------
            case "giveup":
                try {
                    // Eliminar de las conexiones
                    Integer indice = (Integer)source;
                    writer.writeInt(2);
                    writer.writeUTF("giveup");

                    // Eliminar conexion
                    ArrayList<ThreadServidor> nuevasConexiones;
                    nuevasConexiones = new ArrayList<ThreadServidor>();
                    for (int i = 0; i < server.conexiones.size(); i++) {
                        if(i != indice){
                            nuevasConexiones.add(server.conexiones.get(i));
                        }
                    }
                    server.conexiones=(ArrayList<ThreadServidor>) nuevasConexiones.clone();
                    
                    // Eliminar de los jugadores del juego
                    server.controlMain.eliminarJugador(nombre);
                    
                    // Actualizar id para cada thread servidor 
                    server.controlMain.actualizarIndices();
                    
                    // Pasar turno
                    server.controlMain.pasarTurno(server.getTurno());
                } catch (IOException ex) {
                    Logger.getLogger(ThreadServidor.class.getName())
                    .log(Level.SEVERE, null, ex);
                }
                break;
                  
            // --------------------- RELOAD ---------------------
            case "reload":
                // Activar todas las armas
                reload((String)source);
                try {
                    writer.writeInt(2);
                    writer.writeUTF("reload");
                } catch (IOException ex) {
                    Logger.getLogger(ThreadServidor.class.getName())
                    .log(Level.SEVERE, null, ex);
                }
                break;
                
            // --------------------- WILDCARD ---------------------
            case "wildcard":
                break;
                
            // --------------------- GROUP EXIT ---------------------
            case "groupexit":
                break;
                
            // --------------------- ATTACK ---------------------
            // Ataque del atacante
            case "attack":
                atacar(source);
                break;
            // Ataque de la victima
            case "attackVictim":
                atacarVictima(source);
                break;
                
            // --------------------- DEFAULT ---------------------
            default:
                break;
        }
    }
    
    // ----------------------------------------- FUNCIONES DE APOYO -----------------------------------------
    private void atacar(Object source){
        // Info
        ArrayList<String> infoAtaque = (ArrayList<String>)source;
        String victima= infoAtaque.get(0);
        String personaje= infoAtaque.get(1);
        String arma= infoAtaque.get(2);
        Integer respuesta = server.controlMain.determinarAtaqueValido(nombre,
                            victima, personaje, arma, 1);
        System.out.println("respuesta: " + respuesta);
        if(respuesta == 0){
            try {
                writer.writeInt(4);
                writer.writeUTF("Error: el arma ya fue usada");
            } catch (IOException ex) {
                Logger.getLogger(ThreadServidor.class.getName())
                .log(Level.SEVERE, null, ex);
            }
        }
        else{
            if(respuesta == -1){
                try {
                    writer.writeInt(4);
                    writer.writeUTF("Error: el arma no existe");
                } catch (IOException ex) {
                    Logger.getLogger(ThreadServidor.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
            }
            else{
                if(respuesta == -2){
                    try {
                        writer.writeInt(4);
                        writer.writeUTF("Error: el personaje no existe");
                    } catch (IOException ex) {
                        Logger.getLogger(ThreadServidor.class.getName())
                                .log(Level.SEVERE, null, ex);
                    }
                }
                else{
                    if(respuesta == -3){
                        try {
                            writer.writeInt(4);
                            writer.writeUTF("Error: el jugador no existe");
                        } catch (IOException ex) {
                            Logger.getLogger(ThreadServidor.class.getName())
                                    .log(Level.SEVERE, null, ex);
                        }
                    }
                    else{
                        if(respuesta == -4){
                            try {
                                writer.writeInt(4);
                                writer.writeUTF("Error: el personaje esta muerto. No puede atacar");
                            } catch (IOException ex) {
                                Logger.getLogger(ThreadServidor.class
                                .getName()).log(Level.SEVERE, null, ex);
                            }    
                        }
                        else{
                            // Ataque valido
                            try{
                                String tipoPersonaje = server.controlMain
                                        .personajeAtacante.getNombreCategoria();
                                String imagenAtacante = server.controlMain
                                        .personajeAtacante.getApariencia();
                                writer.writeInt(2);
                                writer.writeUTF("attack");
                                writer.writeUTF(victima);
                                writer.writeUTF(imagenAtacante);
                                writer.writeUTF(arma);
                                writer.writeUTF(tipoPersonaje);
                                writer.writeInt(respuesta);
                            } catch (IOException ex) {
                                Logger.getLogger(ThreadServidor.class
                                .getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
        }
    }
    
    private void atacarVictima(Object source){
        ArrayList<String> infoAtaque2 = (ArrayList<String>)source;
        String jugadorAtacante= infoAtaque2.get(0);
        String victima2= infoAtaque2.get(1);
        String personaje2= infoAtaque2.get(2);
        String arma2= infoAtaque2.get(3);
        Integer danho = server.controlMain.determinarAtaqueValido(jugadorAtacante, victima2, personaje2, arma2, 2);
        if(danho != -1 && danho != -2 && danho != -3 && danho != 0 && danho != -4){
            // ataque valido
            // Obtener las victimas
            String tipoAtacante = server.controlMain.personajeAtacante.getNombreCategoria();
            ArrayList<Personaje> victimas = server.controlMain.getVictimas(victima2, tipoAtacante);
            Personaje atacante = server.controlMain.personajeAtacante;
            atacante.setDamage(danho);
            atacante.setEnemigos(victimas);
            String resultado = atacante.atacar();
            // Determinar si todos los personajes murieron
            ArrayList<Integer> indices = server.controlMain.getIndicesVictimas(victima2, tipoAtacante);
            boolean victimaPerdedor = server.controlMain.perdedor(victima2);
            try{
                String imagenAtacante = atacante.getApariencia();
                writer.writeInt(2);
                writer.writeUTF("attackVictim");
                writer.writeUTF(atacante.getNombre());
                writer.writeUTF(imagenAtacante);
                writer.writeUTF(tipoAtacante);
                writer.writeUTF(jugadorAtacante);
                writer.writeUTF(arma2);
                writer.writeInt(danho);
                writer.writeUTF(resultado);
                writer.writeBoolean(victimaPerdedor);
                Objectwriter.writeObject(indices);
                // Pasar de turno cuando ataca
                server.controlMain.pasarTurno(server.getTurno());
            } catch (IOException ex) {
                Logger.getLogger(ThreadServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void error() {
        for (int i = 0; i < server.conexiones.size(); i++) {
            ThreadServidor current = server.conexiones.get(i);
            try {
                if(i == this.id){
                    current.writer.writeInt(3);
                }
            } catch (IOException ex) {
                Logger.getLogger(ChatCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private void error2() {
        for (int i = 0; i < server.conexiones.size(); i++) {
            ThreadServidor current = server.conexiones.get(i);
            try {
                if(i == this.id){
                    current.writer.writeInt(6);
                }
            } catch (IOException ex) {
                Logger.getLogger(ChatCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    private void reload(String nombreJugador){
        Jugador jugador = server.controlMain.getJugador(nombreJugador);
        // Buscar todos los personajes y activarles todas las armas
        for (int j = 0; j < jugador.getPersonajes().size(); j++) {
            Personaje personajeAct = jugador.getPersonajes().get(j);
            // Armas
            Arma armaActual = null;
            for (int k = 0; k < personajeAct.getArmas().size(); k++) {
                // Obtener el arma 
                armaActual = personajeAct.getArmas().get(k);
                armaActual.setAvailable(true);
            }
        }
    }
}
