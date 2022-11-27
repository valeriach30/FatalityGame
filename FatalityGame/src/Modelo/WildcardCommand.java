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
            
        }
        else{
            
        }
        
        ArrayList<String> array = new ArrayList<String>();
        return array;  
    }
}
