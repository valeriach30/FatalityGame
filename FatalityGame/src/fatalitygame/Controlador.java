/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fatalitygame;

import Libreria.Juego.Juego;
import Libreria.Juego.Jugador;
import java.util.ArrayList;

/**
 *
 * @author vchin
 */
public class Controlador {
    public Juego juego = new Juego();
    
    void crear() {
        // nada
    }
    
    public void agregarJugador(Jugador jugador){
        if(juego.getJugadores() != null){
            juego.getJugadores().add(jugador);
        } else{
            ArrayList<Jugador> Jugadores = new ArrayList<Jugador>();
            Jugadores.add(jugador);
            juego.setJugadores(Jugadores);
        }
    }
    
}
