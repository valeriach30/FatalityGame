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
public class NotFoundCommand extends BaseCommand{
    public static final String COMMAND_NAME = "NOT FOUND";       
    
    @Override       public String getCommandName() {           
        return COMMAND_NAME;   
    }       
    
    @Override       
    public ArrayList<String> execute(ArrayList<String> args, OutputStream out, ArrayList<ThreadServidor> conexiones) {  
       ArrayList<String> array = new ArrayList<String>();
       array.add("Comando no encontrado");
       return array;   
    }
}
