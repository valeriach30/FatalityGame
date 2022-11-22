/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Comunicaciones;

import java.util.ArrayList;
//import Libreria.Juego.Jugador;
/**
 *
 * @author vchin
 */
public class Servidor implements iObserved{
    //public ArrayList<Jugador> jugadores;

    @Override
    public void notificarTodos(iClientMessage msg) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Hay que agregar aqui los jugadores que estan observando la partida
    @Override
    public void agregarObserver(iObserver observer) {
//        if(!jugadores.contains(observer)){
//            this.jugadores.add((Jugador) observer);
//        }
    }
    
}
