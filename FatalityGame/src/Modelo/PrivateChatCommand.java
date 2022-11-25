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
public class PrivateChatCommand extends BaseCommand{
    public static final String COMMAND_NAME = "privatechat";       
    
    @Override       
    public String getCommandName() {           
        return COMMAND_NAME;   
    }       
    
    @Override       
    public ArrayList<String> execute(ArrayList<String> args, OutputStream out, ArrayList<ThreadServidor> conexiones) {           
        String mensaje= args.get(0);
        String nombre= args.get(1);
        String jugador= args.get(2);
        
        for (int i = 0; i < conexiones.size(); i++) {
            ThreadServidor current = conexiones.get(i);
            if(current.nombre.equals(jugador)){
                String textoMsj = nombre+" (Private) - " + mensaje + "\n>";
                current.notificar("privatechat", textoMsj);
            }
            else{
                if(current.nombre.equals(nombre)){
                    String textoMsj = "Private message sent to " + jugador + " - " + mensaje + "\n>";
                    current.notificar("privatechat", textoMsj);
                }
            }
        }
        ArrayList<String> array = new ArrayList<String>();
        return array;    
    }
}
