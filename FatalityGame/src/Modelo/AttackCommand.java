/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Communication.ThreadServidor;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 *
 * @author vchin
 */
public class AttackCommand extends BaseCommand{
    public static final String COMMAND_NAME = "attack";       
    
    @Override       
    public String getCommandName() {           
        return COMMAND_NAME;   
    }       
    
    @Override       
    public ArrayList<String> execute(ArrayList<String> args, OutputStream out, ArrayList<ThreadServidor> conexiones) {           
        String atacante= args.get(0);
        String victima= args.get(1);
        String personaje= args.get(2);
        String arma= args.get(3);
        
        for (int i = 0; i < conexiones.size(); i++) {
            ThreadServidor current = conexiones.get(i);
            // Thread servidor del atacante
            if(current.nombre.equals(atacante)){
                ArrayList<String> infoAtaque = new ArrayList<String>();
                infoAtaque.add(victima);
                infoAtaque.add(personaje);
                infoAtaque.add(arma);
                current.notificar("attack", infoAtaque);
            }
            // Thread servidor de la victima
            else{
                if(current.nombre.equals(victima)){
                    ArrayList<String> infoAtaque = new ArrayList<String>();
                    infoAtaque.add(atacante);
                    infoAtaque.add(victima);
                    infoAtaque.add(personaje);
                    infoAtaque.add(arma);
                    infoAtaque.add("2");
                    current.notificar("attackVictim", infoAtaque);
                }
            }
        }
        
        ArrayList<String> array = new ArrayList<String>();
        return array;  
    }
    
    public void determinarVictimas(){
        
    }
    
    
}
