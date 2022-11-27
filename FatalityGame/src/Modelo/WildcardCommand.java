/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Communication.ThreadServidor;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vchin
 */
public class WildcardCommand extends BaseCommand{
    public static final String COMMAND_NAME = "wildcard";       
    
    @Override       
    public String getCommandName() {           
        return COMMAND_NAME;   
    }       
    
    @Override       
    public ArrayList<String> execute(ArrayList<String> args, OutputStream out, ArrayList<ThreadServidor> conexiones) {           
        String atacante= args.get(0);
        String victima= args.get(1);
        String personaje1= args.get(2);
        String arma1= args.get(3);
        String personaje2= args.get(4);
        String arma2= args.get(5);
        String determinante= args.get(6);
        
        if(determinante == "1"){
            // Doble ataque
            for (int i = 0; i < conexiones.size(); i++) {
                ThreadServidor current = conexiones.get(i);
                if(current.nombre.equals(atacante)){
                    // Primer ataque
                    ArrayList<String> infoAtaque = new ArrayList<String>();
                    infoAtaque.add(victima);
                    infoAtaque.add(personaje1);
                    infoAtaque.add(arma1);
                    current.notificar("wildcard", infoAtaque);
                    
                }
                // Thread servidor de la victima
                else{
                    if(current.nombre.equals(victima)){
                        ArrayList<String> infoAtaque = new ArrayList<String>();
                        infoAtaque.add(atacante);
                        infoAtaque.add(victima);
                        infoAtaque.add(personaje1);
                        infoAtaque.add(arma1);
                        infoAtaque.add("1");
                        current.notificar("attackVictim", infoAtaque);
                    }
                }
            }
            
            // Delay para observar el cambio en la pantalla
            try {
                TimeUnit.SECONDS.sleep(15);
            } catch (InterruptedException ex) {
                System.out.println("error");
                Logger.getLogger(WildcardCommand.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("---------SEGUNDO ATAQUE-----------");
            // Segundo ataque
            for (int i = 0; i < conexiones.size(); i++) {
                ThreadServidor current = conexiones.get(i);
                if(current.nombre.equals(atacante)){
                    // Segundo ataque
                    ArrayList<String> infoAtaque2 = new ArrayList<String>();
                    infoAtaque2.add(victima);
                    infoAtaque2.add(personaje2);
                    infoAtaque2.add(arma2);
                    current.notificar("wildcard", infoAtaque2);
                    
                }
                // Thread servidor de la victima
                else{
                    if(current.nombre.equals(victima)){
                        ArrayList<String> infoAtaque2 = new ArrayList<String>();
                        infoAtaque2.add(atacante);
                        infoAtaque2.add(victima);
                        infoAtaque2.add(personaje2);
                        infoAtaque2.add(arma2);
                        infoAtaque2.add("2");
                        current.notificar("attackVictim", infoAtaque2);
                    }
                }
            }
                    
        }
        else{
            
        }
        
        ArrayList<String> array = new ArrayList<String>();
        return array;  
    }
}
