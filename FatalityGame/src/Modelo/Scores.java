/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Libreria.Juego.Jugador;
import java.io.Serializable;

/**
 *
 * @author vchin
 */
public class Scores implements Serializable{
    private String nombre;
    private int ataquesExitosos;
    private int ataquesFracasados;
    private int muertes;
    private int ganes;
    private int perdidas;
    private int rendiciones;
    private static final long serialVersionUID = 6529685098267757690L;
    
    public Scores(String nombre, int ataquesExitosos, int ataquesFracasados, 
    int muertes, int ganes, int perdidas, int rendiciones) {
        this.nombre = nombre;
        this.ataquesExitosos = ataquesExitosos;
        this.ataquesFracasados = ataquesFracasados;
        this.muertes = muertes;
        this.ganes = ganes;
        this.perdidas = perdidas;
        this.rendiciones = rendiciones;
    }

    public Scores() {
    }

    public int getAtaquesExitosos() {
        return ataquesExitosos;
    }

    public void setAtaquesExitosos(int ataquesExitosos) {
        this.ataquesExitosos = ataquesExitosos;
    }

    public int getAtaquesFracasados() {
        return ataquesFracasados;
    }

    public void setAtaquesFracasados(int ataquesFracasados) {
        this.ataquesFracasados = ataquesFracasados;
    }

    public int getMuertes() {
        return muertes;
    }

    public void setMuertes(int muertes) {
        this.muertes = muertes;
    }

    public int getGanes() {
        return ganes;
    }

    public void setGanes(int ganes) {
        this.ganes = ganes;
    }

    public int getPerdidas() {
        return perdidas;
    }

    public void setPerdidas(int perdidas) {
        this.perdidas = perdidas;
    }

    public int getRendiciones() {
        return rendiciones;
    }

    public void setRendiciones(int rendiciones) {
        this.rendiciones = rendiciones;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        String resultado = "Wins: " + ganes;
        resultado += "\nLoses: " + perdidas;
        resultado += "\nSuccess Attack: " + ataquesExitosos;
        resultado += "\nFailed Attack: " + ataquesFracasados;
        resultado += "\nDeaths: " + muertes;
        resultado += "\nGiveups: " + rendiciones;
        
        return resultado;
    }
    
}
