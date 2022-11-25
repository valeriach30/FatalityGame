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
public class PassTurnCommand extends BaseCommand{
    public static final String COMMAND_NAME = "pass";       
    
    @Override       
    public String getCommandName() {           
        return COMMAND_NAME;   
    }       
    
    @Override       
    public ArrayList<String> execute(ArrayList<String> args, OutputStream out, ArrayList<ThreadServidor> conexiones) {           
        int turno = Integer.parseInt(args.get(0));
        if (++turno >= conexiones.size()){
            turno = 0;
        }
        
        for (int i = 0; i < conexiones.size(); i++) {
            ThreadServidor current = conexiones.get(i);
            current.notificar("pass", turno);
        }
        
        ArrayList<String> array = new ArrayList<String>();
        array.add(Integer.toString(turno));
        return array;   
    }
}
