/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Communication;

import Communication.Servidor;
import Communication.ThreadServidor;
import Communication.iObserved;
import Communication.iObserver;
import Juego.Armas.Arma;
import Juego.Personaje.Personaje;
import Libreria.Juego.Juego;
import Libreria.Juego.Jugador;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import Log.BDManagerProxy;
import Modelo.CommandManager;
import Modelo.ICommand;
import Modelo.Scores;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author vchin
 */
public class Controlador implements iObserved{
    
    public Juego juego = new Juego();
    public ArrayList<iObserver> observers;
    public ArrayList<Scores> scores = new ArrayList<Scores>();
    public ArrayList<String> nombres = null;
    private Servidor server;
    public DataInputStream reader;
    public DataOutputStream writer;
    public ObjectInputStream Objectreader;
    public ObjectOutputStream Objectwriter;
    public CommandManager manager = CommandManager.getIntance(); 
    public Personaje personajeAtacante;
    public Arma lastArma;
    public boolean salir = false;
    
    public Controlador(Servidor server)throws IOException, FileNotFoundException, ClassNotFoundException{
        this.server = server;
        cargarScores();
    }
    
    
    // ----------------------------------COMANDOS----------------------------------
    
    public void chat(String mensaje, String nombre) throws IOException{
        ArrayList<String> commandArgs = new ArrayList<String>();
        commandArgs.add(mensaje);
        commandArgs.add(nombre);
        ICommand command = manager.getCommand("chat");   
        command.execute(commandArgs, System.out, server.conexiones);        
    }
    
    public void chatPrivado(String mensaje, String nombre, String jugador){
        ArrayList<String> commandArgs = new ArrayList<String>();
        commandArgs.add(mensaje);
        commandArgs.add(nombre);
        commandArgs.add(jugador);
        ICommand command = manager.getCommand("privatechat");   
        command.execute(commandArgs, System.out, server.conexiones);        
    }

    public void pasarTurno(int turno) {
        server.setTurno(turno++);
        ArrayList<String> commandArgs = new ArrayList<String>();
        commandArgs.add(Integer.toString(turno));
        ICommand command = manager.getCommand("pass");   
        ArrayList<String> resultados = command.execute(commandArgs, System.out, server.conexiones);        
    }
    
    public void seleccionarJugador(String jugador){
        ArrayList<String> commandArgs = new ArrayList<String>();
        commandArgs.add(jugador);
        ICommand command = manager.getCommand("select");   
        command.execute(commandArgs, System.out, server.conexiones);        
    }
    
    public void rendirse(String nombre){
        ArrayList<String> commandArgs = new ArrayList<String>();
        commandArgs.add(nombre);
        ICommand command = manager.getCommand("giveup");  
        command.execute(commandArgs, System.out, server.conexiones);        
    }
    
    public Integer recargar(String nombre) {
        Jugador jugador = getJugador(nombre);
        boolean bandera = false;
        // Determinar si todas las armas del jugador estan deshabilitadas
        for (int j = 0; j < jugador.getPersonajes().size(); j++) {
            Personaje personajeAct = jugador.getPersonajes().get(j);
            
            // Armas
            Arma armaActual = null;
            for (int k = 0; k < personajeAct.getArmas().size(); k++) {
                // Obtener el arma 
                armaActual = personajeAct.getArmas().get(k);
                if(armaActual.isAvailable()){
                    bandera = true;
                }
            }
        }
        // Bandera falsa -> ningun arma esta activa
        if(!bandera){
            ArrayList<String> commandArgs = new ArrayList<String>();
            commandArgs.add(nombre);
            ICommand command = manager.getCommand("reload");  
            command.execute(commandArgs, System.out, server.conexiones);    
            return 0;
        }
        // Bandera verdadera -> algun arma esta activa
        else{
            // error
            return -1;
        }
    }
    
    public void salidaGrupal(String nombre) {
        ArrayList<String> commandArgs = new ArrayList<String>();
        commandArgs.add(nombre);
        ICommand command = manager.getCommand("groupexit"); 
        command.execute(commandArgs, System.out, server.conexiones); 
    }
    
    public void comodinJugadores(String atacante, String victimaJ, String personaje1, String arma1, String personaje2, String arma2){
        
        ArrayList<String> commandArgs = new ArrayList<String>();
        commandArgs.add(atacante);
        commandArgs.add(victimaJ);
        commandArgs.add(personaje1);
        commandArgs.add(arma1);
        commandArgs.add(personaje2);
        commandArgs.add(arma2);
        commandArgs.add("1");
        ICommand command = manager.getCommand("wildcard");   
        command.execute(commandArgs, System.out, server.conexiones);  
    }
    
    public void comodinArmas(String atacante, String victimaJ, String personaje1, String arma1, String arma2){
        
        ArrayList<String> commandArgs = new ArrayList<String>();
        commandArgs.add(atacante);
        commandArgs.add(victimaJ);
        commandArgs.add(personaje1);
        commandArgs.add(arma1);
        commandArgs.add("");
        commandArgs.add(arma2);
        commandArgs.add("2");
        ICommand command = manager.getCommand("wildcard"); 
        command.execute(commandArgs, System.out, server.conexiones);  
    }

    public void attack(String nombre, String jugadorEnemigo, String personaje, String arma) {
        ArrayList<String> commandArgs = new ArrayList<String>();
        commandArgs.add(nombre);
        commandArgs.add(jugadorEnemigo);
        commandArgs.add(personaje);
        commandArgs.add(arma);
        ICommand command = manager.getCommand("attack");   
        command.execute(commandArgs, System.out, server.conexiones);        
    }
    
    
    public void log(String nombre) {
        ArrayList<String> commandArgs = new ArrayList<String>();
        commandArgs.add(nombre);
        ICommand command = manager.getCommand("log");   
        command.execute(commandArgs, System.out, server.conexiones); 
    }
    // ----------------------------------OBSERVER----------------------------------
    
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

    
    // ----------------------------------FUNCIONES DE APOYO----------------------------------
    
    public void agregarJugador(Jugador jugador){ 
        if(juego.getJugadores() != null){
            juego.getJugadores().add(jugador);
        } else{
            ArrayList<Jugador> Jugadores = new ArrayList<Jugador>();
            Jugadores.add(jugador);
            juego.setJugadores(Jugadores);
        }
    }
    
    public Integer determinarAtaqueValido(String nombre, String victima, String personaje, String arma, Integer pasada) {
        Jugador jugadorAct = null;
        for (int i = 0; i < juego.getJugadores().size(); i++) {
            // Jugador actual
            if(juego.getJugadores().get(i).getNombre().equals(nombre)){
                jugadorAct = juego.getJugadores().get(i);
            }
        }
        if(jugadorAct != null){
            // Determinar si tiene ese personaje
            Personaje personajeAct = null;
            for (int j = 0; j < jugadorAct.getPersonajes().size(); j++) {
                if(jugadorAct.getPersonajes().get(j).getNombre().equals(personaje)){
                    // Determinar si el personaje esta vivo
                    personajeAct = jugadorAct.getPersonajes().get(j);
                    if(personajeAct.getVida() < 0){
                        // ataque invalido: el personaje esta muerto
                        personajeAct = null;
                        return -4;
                    }
                }
            }

            Arma armaActual = null;
            if(personajeAct != null){
                for (int k = 0; k < personajeAct.getArmas().size(); k++) {
                    // Obtener el arma 
                    if(personajeAct.getArmas().get(k).getName().equals(arma)){
                        armaActual = personajeAct.getArmas().get(k);
                    }
                }
                if(armaActual != null){
                    if(armaActual.isAvailable()){
                        lastArma = armaActual;
                        // ataque 
                        personajeAtacante = personajeAct;
                        // determinar la cantidad de danho que le va a hacer a los personajes
                        Integer danhoFinal = getDanho(personajeAct, armaActual);
                        return danhoFinal;
                    }
                    else{
                        return 0;
                    }
                }else{
                    // ataque invalido: el arma no existe
                    return -1;
                }
            }
            else{
                // ataque invalido: el personaje no existe
                return -2;
            }
        }
        else{
            // ataque invalido: el jugador no existe
            return -3;
        }
    }
    
    public Jugador getJugador(String nombre){
        Jugador jugadorAct = null;
        for (int i = 0; i < juego.getJugadores().size(); i++) {
            // Jugador actual
            if(juego.getJugadores().get(i).getNombre().equals(nombre)){
                jugadorAct = juego.getJugadores().get(i);
            }
        }
        return jugadorAct;
    }
    
    public Integer getDanho(Personaje per, Arma arma){
        String tipo = per.getNombreCategoria();
        Integer danho = 0;
        switch(tipo){
            case "fuego":
                danho = arma.getDanhos().get(0);
                break;
            case "aire":
                danho = arma.getDanhos().get(1);
                break;
            case "agua":
                danho = arma.getDanhos().get(2);
                break;
            case "magia blanca":
                danho = arma.getDanhos().get(3);
                break;
            case "magia negra":
                danho = arma.getDanhos().get(4);
                break;
            case "electricidad":
                danho = arma.getDanhos().get(5);
                break;
            case "hielo":
                danho = arma.getDanhos().get(6);
                break;
            case "acid":
                danho = arma.getDanhos().get(7);
                break;
            case "espiritualidad":
                danho = arma.getDanhos().get(8);
                break;
            case "hierro":
                danho = arma.getDanhos().get(9);
                break;
        }
        return danho;
    }
    
    public ArrayList<Personaje> getVictimas(String nombreJugador, String categoria, 
        Integer danho, String atacante) throws IOException{
        ArrayList<Personaje> victimas = new ArrayList<Personaje>();
        Jugador victimaJugador = getJugador(nombreJugador);
        for (int i = 0; i < victimaJugador.getPersonajes().size(); i++) {
            String tipoCatPerActual = victimaJugador.getPersonajes().get(i).getNombreCategoria();
            if(tipoCatPerActual.equals(categoria) && victimaJugador.getPersonajes().get(i).getVida() > 0){
                victimas.add(victimaJugador.getPersonajes().get(i));
            }
        }
        
        // Actualizar los scores del atacante
        actualizarAtaques(atacante, danho, victimas.size());
        return victimas;
    }
    
    public Integer getDanho(String nombreJugador, String categoria, Integer danho){
        Integer cantidadVictimas = 0;
        Jugador victimaJugador = getJugador(nombreJugador);
        for (int i = 0; i < victimaJugador.getPersonajes().size(); i++) {
            String tipoCatPerActual = victimaJugador.getPersonajes().get(i).getNombreCategoria();
            if(tipoCatPerActual.equals(categoria) && victimaJugador.getPersonajes().get(i).getVida() > 0){
                cantidadVictimas++;
            }
        }
        if(cantidadVictimas == 0){
            cantidadVictimas = 1;
        }
        return danho * cantidadVictimas;
    }
    
    public ArrayList<Integer> getIndicesVictimas(String nombreJugador, String categoria){
        ArrayList<Integer> indices = new ArrayList<Integer>();
        
        Jugador victimaJugador = getJugador(nombreJugador);
        for (int i = 0; i < victimaJugador.getPersonajes().size(); i++) {
            String tipoCatPerActual = victimaJugador.getPersonajes().get(i).getNombreCategoria();
            if(tipoCatPerActual.equals(categoria)){
                indices.add(i);
            }
        }
        return indices;
    }
    
    public boolean nombreValido(String nombre){
        
        if(nombres != null){
            if(nombres.contains(nombre)){
                return false;
            }
        }
        return true;
    }
    
    public void agregarNombre(String nombreP){
        if(nombres == null){
            ArrayList<String> nombre = new ArrayList<String>(); 
            nombre.add(nombreP);
            nombres = nombre;
        }
        else{
            nombres.add(nombreP);
        }
    }
    
    public void eliminarJugador(String nombreJ){
        for (int i = 0; i < juego.getJugadores().size(); i++) {
            // Jugador actual
            if(juego.getJugadores().get(i).getNombre().equals(nombreJ)){
                juego.getJugadores().remove(i);
            }
        }
    }
    
    public void actualizarIndices(){
        for (int i = 0; i < server.conexiones.size(); i++) {
            ThreadServidor current = server.conexiones.get(i);
            if(current.id != 0){
                current.id--;
            }
        }
    }

    public void desactivarArmas(String nombreJugador) {
        Jugador jugador = getJugador(nombreJugador);
        // Buscar todos los personajes y activarles todas las armas
        for (int j = 0; j < jugador.getPersonajes().size(); j++) {
            Personaje personajeAct = jugador.getPersonajes().get(j);
            // Armas
            Arma armaActual = null;
            for (int k = 0; k < personajeAct.getArmas().size(); k++) {
                // Obtener el arma 
                armaActual = personajeAct.getArmas().get(k);
                armaActual.setAvailable(false);
            }
        }
    }

    public boolean perdedor(String victima, String atacante) throws IOException {
        Integer contador = 0;
        
        // Determinar la cantidad de personajes que murieron
        Jugador victimaJugador = getJugador(victima);
        for (int i = 0; i < victimaJugador.getPersonajes().size(); i++) {
            String tipoCatPerActual = victimaJugador.getPersonajes().get(i).getNombreCategoria();
            if(victimaJugador.getPersonajes().get(i).getVida() < 0){
                contador++;
            }
        }
        // Actualizar los scores del atacante
        actualizarMuertes(atacante, contador);
        
        if(contador == victimaJugador.getPersonajes().size()){
            // Todos los personajes estan muertos

            return true;
        }
        else{
            // No todos los personajes han muerto
            return false;
        }
    }

    
    public boolean ganador(String nombre){
        if(server.conexiones.get(0).nombre != null){
            if(server.conexiones.size() == 1 && server.conexiones.get(0).nombre.equals(nombre)){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    public void actualizarMuertes(String nombre, Integer contador) throws IOException{
        for (int i = 0; i < server.conexiones.size(); i++) {
            ThreadServidor current = server.conexiones.get(i);
            if(current.nombre.equals(nombre)){
                current.scores.setMuertes(current.scores.getMuertes() + contador);
                current.actualizarScores();
            }
        }
        actualizarScores();
    }
    
    public void actualizarAtaques(String nombre, Integer danho, Integer cantidadVictimas) throws IOException{
        Integer porcentajeDanho = danho * cantidadVictimas;
        
        for (int i = 0; i < server.conexiones.size(); i++) {
            ThreadServidor current = server.conexiones.get(i);
            if(current.nombre.equals(nombre)){
                if(porcentajeDanho < 100){
                    // ataque fallido
                    current.scores.setAtaquesFracasados(current.scores.getAtaquesFracasados() + 1);
                }
                else{
                    // ataque exitoso
                    current.scores.setAtaquesExitosos(current.scores.getAtaquesExitosos() + 1);
                }
                current.actualizarScores();
            }
        }
        actualizarScores();
    }

    private void cargarScores() throws FileNotFoundException, IOException, ClassNotFoundException {
        File f = new File(System.getProperty("user.dir") + "/scores.txt");
        
        // El archivo ya existe, entonces se cargan los datos
        if(f.exists()){
            
            FileInputStream fileIn = new FileInputStream(System.getProperty("user.dir") + "/scores.txt");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            ArrayList<Scores> obj = (ArrayList<Scores>) objectIn.readObject();
            this.scores = obj;
        }
        
        // El archivo no existe, se crea
        else{
            FileOutputStream fileOut = new FileOutputStream(System.getProperty("user.dir") + "/scores.txt");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(scores);
        }
    }
    
    public void actualizarScores() throws FileNotFoundException, IOException{
        FileOutputStream fileOut = new FileOutputStream(System.getProperty("user.dir") + "/scores.txt");
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(scores);
    }
    
    public String toStringScores(String nombre){
        String resultado = "";
        for (int i = 0; i < this.scores.size(); i++) {
            Scores current = scores.get(i);
            if(current.getNombre().equals(nombre)){
            }
            else{
                resultado += "\nJUGADOR: " + current.getNombre();
                resultado += "\n" + current.toString();
            }
            
        }
        return resultado;
    }

    String ranking() {
        String resultado = "";
        HashMap<String, Float> rank = new HashMap<String, Float>();

        for (int i = 0; i < scores.size(); i++) {
            String nombre = scores.get(i).getNombre();
            Integer muertes = scores.get(i).getMuertes();
            if(muertes == 0){
                muertes = 1;
            }
            float calificacion = (float)scores.get(i).getGanes() / (float)muertes;
            rank.put(nombre, calificacion);
        }
        
        LinkedHashMap<String, Float> sortedMap = new LinkedHashMap<>();
        ArrayList<Float> list = new ArrayList<>();
       
        for (Map.Entry<String, Float> entry : rank.entrySet()) {
            list.add(entry.getValue());
        }
        list.sort(Collections.reverseOrder());
        for (Float num : list) {
            for (Entry<String, Float> entry : rank.entrySet()) {
                if (entry.getValue().equals(num)) {
                    sortedMap.put(entry.getKey(), num);
                }
            }
        }
        Integer contador = 0;
        for(String key: sortedMap.keySet()){
            Float valor = sortedMap.get(key);
            if(contador <= 10){
                resultado += "\n" + (contador + 1) + ". " + key + " - " + valor + " - ";
            }
            contador += 1;
        }
        return resultado;
    }
}
