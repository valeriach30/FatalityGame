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
import Log.BDManagerProxy;
import Modelo.Scores;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;

/**
 *
 * @author vchin
 */
public class ThreadServidor extends Thread implements iObserver{
    
    public int id;
    public BDManagerProxy bdManagerProxy = new BDManagerProxy();
    private Socket socketRef;
    public DataInputStream reader;
    public DataOutputStream writer;
    public ObjectInputStream Objectreader;
    public ObjectOutputStream Objectwriter;
    public String nombre;
    private boolean running = true;
    public Servidor server;
    private boolean comodinHabilitado = false;
    public long startTime = System.currentTimeMillis();
    public Scores scores; 
    
    public ThreadServidor(Socket socketRef, Servidor server, int id) throws IOException {
        this.socketRef = socketRef;
        this.reader = new DataInputStream(socketRef.getInputStream());
        this.writer = new DataOutputStream(socketRef.getOutputStream());
        this.Objectreader = new ObjectInputStream(socketRef.getInputStream());
        this.Objectwriter = new ObjectOutputStream(socketRef.getOutputStream());
        this.bdManagerProxy = new BDManagerProxy();
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
                        
                        scores = new Scores(nombre, 0,0,0,0,0,0);
                        
                        // Cargar scores
                        cargarScores();
                        
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
                                    
                                    bdManagerProxy.insert("{Command: Attack; -From " + nombre + " To "
                                    + jugadorEnemigo + " With Pj " + personaje + " With Wp " + arma + "}");
                                    
                                    server.controlMain.attack(nombre, jugadorEnemigo, personaje, arma);
                                    
                                    if(server.controlMain.lastArma != null){
                                        server.controlMain.lastArma.setAvailable(false);
                                    }
                                }
                                
                                else{
                                    bdManagerProxy.insert("{Command Attack Error: Not right turn}");
                                    error();
                                }
                                // Determinar si ya gano
                                boolean gano = server.controlMain.ganador(nombre);
                                if(gano){
                                    // Actualizar scores
                                    scores.setGanes(scores.getGanes() + 1);
                                    actualizarScores();
                                    writer.writeInt(7);
                                }
                                break;
                                
                            //----------------------------CHAT----------------------------
                            case "chat":
                                String mensaje = arrayComandos[1];
                                bdManagerProxy.insert("{Command: Chat; " + mensaje + " -From " + nombre +"}");
                                server.controlMain.chat(mensaje, nombre);
                                break;
                                
                            //----------------------------GIVEUP----------------------------
                            case "giveup":
                                bdManagerProxy.insert("{Command: GiveUp; -From " + nombre + "}");
                                server.controlMain.rendirse(nombre);
                                break;
                                
                            //----------------------------GROUP EXIT----------------------------
                            case "groupexit":
                                bdManagerProxy.insert("{Command: GruopExit; -From " + nombre + "}");
                                server.controlMain.salidaGrupal(nombre);
                                break;
                            
                            //----------------------------PASS----------------------------
                            case "pass":
                                if(this.id == server.getTurno()){
                                    bdManagerProxy.insert("{Command: Pass; turno " + server.getTurno() + "}");
                                    server.controlMain.pasarTurno(server.getTurno());
                                }
                                else{
                                    bdManagerProxy.insert("{Command Error: Not right turn}");
                                    error();
                                }
                                break;
                            
                            //----------------------------PRIVATE CHAT----------------------------
                            case "privatechat":
                                String jugador = arrayComandos[1];
                                String mensajePrivado = arrayComandos[2];
                                
                                bdManagerProxy.insert("{Command: Privatechat;" 
                                + mensajePrivado + " -From " + nombre + " -To " 
                                + jugador + "}");
                                
                                server.controlMain.chatPrivado(mensajePrivado, nombre, jugador);
                                break;
                                
                            //----------------------------RELOAD----------------------------
                            case "reload":
                                // Determinar si es su turno
                                if(this.id == server.getTurno()){
                                    bdManagerProxy.insert("{Command: Reload; -From " + nombre +"}");
                                    Integer respuesta = server.controlMain.recargar(nombre);
                                    if(respuesta == -1){
                                        writer.writeInt(4);
                                        writer.writeUTF("Alguna de las armas sigue disponible");
                                        bdManagerProxy.insert("{Command Error}");
                                    }
                                }
                                else{
                                    bdManagerProxy.insert("{Command Error}");
                                    error();
                                }
                                break;
                            
                            //----------------------------SELECT----------------------------
                            case "select":
                                String jugadorSeleccionado = arrayComandos[1];
                                bdManagerProxy.insert("{Command: Select; -From " + jugadorSeleccionado + "}");
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
                                        
                                        bdManagerProxy.insert("{Command: WildCard; -From " + nombre 
                                        + " With Pj " + personaje1 + " With Wp " + arma1 + " With Pj " + 
                                        personaje2 + " With Wp " + arma2 + " To " + victimaJ + "}");
                                        
                                        server.controlMain
                                        .comodinJugadores(nombre,victimaJ, personaje1, arma1,
                                        personaje2,arma2);
                                    }
                                    else{
                                        if(arrayComandos.length == 5){
                                            // Comodin dos armas
                                            victimaJ = arrayComandos[1];
                                            personaje1 = arrayComandos[2];
                                            arma1 = arrayComandos[3];
                                            arma2 = arrayComandos[4];
                                            
                                            bdManagerProxy.insert("{Command: WildCard; -From " + nombre 
                                            + " With Pj"+ personaje1 + " With Wp " + arma1 + " and " + arma2 
                                            + " To " + victimaJ + "}"); 
                                            
                                            server.controlMain.comodinArmas(nombre, 
                                            victimaJ, personaje1, arma1, arma2);
                                            
                                        }
                                    }
                                }
                                else{
                                    bdManagerProxy.insert("{Command Error}");
                                    error2();
                                }
                                // Determinar si ya gano
                                boolean gano2 = server.controlMain.ganador(nombre);
                                if(gano2){
                                    // Actualizar scores
                                    scores.setGanes(scores.getGanes() + 1);
                                    actualizarScores();
                                    writer.writeInt(7);
                                }
                                break;
                            //----------------------------DESACTIVAR----------------------------
                            case "desactivar":  
                                bdManagerProxy.insert("{Command: Desactivar}");
                                server.controlMain.desactivarArmas(nombre);
                                writer.writeInt(4);
                                writer.writeUTF("Armas desactivadas");
                                bdManagerProxy.insert("{Command Succesful}");
                                break;
                            case "log":
                                server.controlMain.log(nombre);
                                break;
                        }
                        // Actualizar los rankings
                        String resultado = scores.toString();
                        String against = server.controlMain.toStringScores(nombre);
                        writer.writeInt(9);
                        writer.writeUTF(resultado);
                        writer.writeUTF(against);
                        break;
                    //----------------------------OTROS----------------------------
                    case 3:
                        writer.writeInt(3);
                        break;
                    case 4:
                        server.controlMain.salir = true;
                        break;
                    case 5:
                        server.controlMain.salir = false;
                        break;
                    default:
                        break;
                }
            }  catch (IOException ex) {
                Logger.getLogger(ThreadServidor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ThreadServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
    
    // -------------------------------------- OBSERVER --------------------------------------
    @Override
    public void notificar(String command, Object source) {
        try {
            switch(command){

                // --------------------- CHAT ---------------------
                case "chat":
                    writer.writeInt(2);
                    writer.writeUTF("chat");
                    writer.writeUTF((String)source);
                    bdManagerProxy.insert("{Command Succesful}");
                    break;

                // --------------------- CHAT PRIVADO ---------------------
                case "privatechat":
                    writer.writeInt(2);
                    writer.writeUTF("privatechat");
                    writer.writeUTF((String)source);
                    bdManagerProxy.insert("{Command Succesful}");
                    break;

                // --------------------- SELECT ---------------------
                case "select":
                    writer.writeInt(2);
                    writer.writeUTF("select");
                    break;

                // --------------------- PASS ---------------------
                case "pass":
                    Integer turno = (Integer)source;
                    String nombreDelTurno = server.conexiones.get(server.getTurno()).nombre;
                    writer.writeInt(2);
                    writer.writeUTF("pass");
                    writer.writeInt(turno);
                    writer.writeUTF(nombreDelTurno);
                    bdManagerProxy.insert("{Command Succesful}");
                    break;

                // --------------------- GIVEUP ---------------------
                case "giveup":

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
                    
                    // Actualizar scores
                    scores.setRendiciones(scores.getRendiciones() + 1);
                    scores.setPerdidas(scores.getPerdidas() + 1);
                    actualizarScores();
                    
                    // Insertar al log
                    bdManagerProxy.insert("{Command Succesful}");
                    break;

                // --------------------- RELOAD ---------------------
                case "reload":
                    // Activar todas las armas
                    reload((String)source);
                    writer.writeInt(2);
                    writer.writeUTF("reload");
                    bdManagerProxy.insert("{Command Succesful}");
                    break;

                // --------------------- WILDCARD ---------------------
                case "wildcard":
                    atacar(source);
                    writer.writeInt(2);
                    writer.writeUTF("wildcard");
                    break;

                // --------------------- GROUP EXIT ---------------------
                case "groupexit":
                    String remitente = (String)source;
                    writer.writeInt(2);
                    writer.writeUTF("groupexit");
                    writer.writeUTF(remitente);
                    // Actualizar scores
                    scores.setRendiciones(scores.getRendiciones() + 1);
                    actualizarScores();
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
                
                // --------------------- LOG ---------------------
                case "log":
                    String log = bdManagerProxy.mostrarTabla();
                    writer.writeInt(2);
                    writer.writeUTF("log");
                    writer.writeUTF(log);
                    break;
                // --------------------- DEFAULT ---------------------
                default:
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(ThreadServidor.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            if(respuesta == 0){
                writer.writeInt(4);
                writer.writeUTF("Error: el arma ya fue usada");
                bdManagerProxy.insert("{Command Error: Weapon already used}"); 
            }
            else{
                if(respuesta == -1){
                    writer.writeInt(4);
                    writer.writeUTF("Error: el arma no existe");
                    bdManagerProxy.insert("{Command Error: Weapon does not exist}"); 
                }
                else{
                    if(respuesta == -2){
                        writer.writeInt(4);
                        writer.writeUTF("Error: el personaje no existe");
                        bdManagerProxy.insert("{Command Error: Character does not exist}"); 
                    }
                    else{
                        if(respuesta == -3){
                            writer.writeInt(4);
                            writer.writeUTF("Error: el jugador no existe");
                            bdManagerProxy.insert("{Command Error: Player does not exist}"); 
                        }
                        else{
                            if(respuesta == -4){
                                writer.writeInt(4);
                                writer.writeUTF("Error: el personaje esta muerto. No puede atacar");
                                bdManagerProxy.insert("{Command Error: Character dead}"); 
                            }
                            else{
                                // Ataque valido
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
                                Integer danho = server.controlMain.getDanho(victima, 
                                tipoPersonaje, respuesta);
                                writer.writeInt(danho);
                                bdManagerProxy.insert("{Command Succesful}"); 
                            }
                        }
                    }
                }
            }
        }catch(Exception e){

        }
    }
    
    private void atacarVictima(Object source) throws IOException{
        ArrayList<String> infoAtaque2 = (ArrayList<String>)source;
        String jugadorAtacante= infoAtaque2.get(0);
        String victima2= infoAtaque2.get(1);
        String personaje2= infoAtaque2.get(2);
        String arma2= infoAtaque2.get(3);
        String pasarTurno= infoAtaque2.get(4);
        
        Integer danho = server.controlMain.determinarAtaqueValido(jugadorAtacante, victima2, personaje2, arma2, 2);
        if(danho != -1 && danho != -2 && danho != -3 && danho != 0 && danho != -4){
            // ataque valido
            // Obtener las victimas
            String tipoAtacante = server.controlMain.personajeAtacante.getNombreCategoria();
            ArrayList<Personaje> victimas = server.controlMain.getVictimas(victima2, tipoAtacante, danho, jugadorAtacante);
            Personaje atacante = server.controlMain.personajeAtacante;
            atacante.setDamage(danho);
            atacante.setEnemigos(victimas);
            String resultado = atacante.atacar();
            // Determinar si todos los personajes murieron
            ArrayList<Integer> indices = server.controlMain.getIndicesVictimas(victima2, tipoAtacante);
            boolean victimaPerdedor = server.controlMain.perdedor(victima2, jugadorAtacante);
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
                if(pasarTurno == "2"){
                    server.controlMain.pasarTurno(server.getTurno());
                }
                if(victimaPerdedor){
                    // Eliminar (giveup)
                    server.controlMain.rendirse(victima2);
                }
            } catch (IOException ex) {
                Logger.getLogger(ThreadServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void error() {
        try {
            for (int i = 0; i < server.conexiones.size(); i++) {
                ThreadServidor current = server.conexiones.get(i);
                    if(i == this.id){
                        current.writer.writeInt(3);
                    }
            }
        }catch (IOException ex) {
           Logger.getLogger(ChatCommand.class.getName()).log(Level.SEVERE, null, ex);
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
    
    public boolean determinar(){
        System.out.println("-------------");
        System.out.println("salir: " +server.controlMain.salir);
        if(server.controlMain.salir){
            // todos dijeron que si
            bdManagerProxy.insert("{Command Succesful}");
            return true;
        }else{
            // alguno dijo que no
            bdManagerProxy.insert("{Command Not Succesful: Someone said no}");
            return false;
        }
    }

    private void cargarScores() throws IOException, ClassNotFoundException {
        String nombreArchivo = "/" + nombre +".txt";
        File f = new File(System.getProperty("user.dir") + nombreArchivo);
        
        
        // El jugador ya existe, entonces se cargan los datos
        if(f.exists()){
            
            FileInputStream fileIn = new FileInputStream(System.getProperty("user.dir") + nombreArchivo);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            Scores obj = (Scores) objectIn.readObject();
            this.scores = obj;
            server.controlMain.scores.add(obj);
        }
        
        // El jugador no existe, se crea el archivo
        else{
            FileOutputStream fileOut = new FileOutputStream(System.getProperty("user.dir") + nombreArchivo);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(scores);
            server.controlMain.scores.add(scores);
        }
        server.controlMain.actualizarScores();
    }
    
    public void actualizarScores() throws FileNotFoundException, IOException{
        String nombreArchivo = "/" + nombre +".txt";
        FileOutputStream fileOut = new FileOutputStream(System.getProperty("user.dir") + nombreArchivo);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(scores);
        
        // Actualizar score del controlador
        for (int i = 0; i < server.controlMain.scores.size(); i++) {
            Scores current = server.controlMain.scores.get(i);
            if(current.getNombre().equals(nombre)){
                current = this.scores;
            }
        }
    }
}
