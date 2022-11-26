/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Communication.ThreadServidor;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vchin
 */
public class GiveUpCommand extends BaseCommand{
    public static final String COMMAND_NAME = "giveup";       
    
    @Override       
    public String getCommandName() {           
        return COMMAND_NAME;   
    }       
    
    @Override       
    public ArrayList<String> execute(ArrayList<String> args, OutputStream out, ArrayList<ThreadServidor> conexiones) {           
        String nombre= args.get(0);
        
        for (int i = 0; i < conexiones.size(); i++) {
            ThreadServidor current = conexiones.get(i);
            if(current.nombre.equals(nombre)){
                current.notificar("giveup", i);
            }
        }
        ArrayList<String> array = new ArrayList<String>();
        return array;  
    }
}
