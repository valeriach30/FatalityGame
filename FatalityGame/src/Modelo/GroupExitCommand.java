/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Communication.ThreadServidor;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author vchin
 */
public class GroupExitCommand extends BaseCommand{
    public static final String COMMAND_NAME = "groupexit";       
    
    @Override       
    public String getCommandName() {           
        return COMMAND_NAME;   
    }       
    
    @Override       
    public ArrayList<String> execute(ArrayList<String> args, OutputStream out, ArrayList<ThreadServidor> conexiones) {           
        String nombre= args.get(0);
        ThreadServidor remitente = null;
        for (int i = 0; i < conexiones.size(); i++) {
            ThreadServidor current = conexiones.get(i);
            // Envia la invitacion a todos menos al que la envio
            if(!current.nombre.equals(nombre)){
                current.notificar("groupexit", nombre);
            }
            else{
                remitente= conexiones.get(i);
            }
        }
        try {
            TimeUnit.SECONDS.sleep(18);
        } catch (InterruptedException ex) {
            System.out.println("error");
            Logger.getLogger(WildcardCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("-------------determinar----------");
        boolean respuesta = remitente.determinar();
        
        if(respuesta){
            // Deshabilitar todas las pantallas y anunciar empate
            for (int i = 0; i < conexiones.size(); i++) {
                ThreadServidor current = conexiones.get(i);
                try {
                    current.writer.writeInt(8);
                } catch (IOException ex) {
                    Logger.getLogger(GroupExitCommand.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        else{
            for (int i = 0; i < conexiones.size(); i++) {
                ThreadServidor current = conexiones.get(i);
                try {
                    current.writer.writeInt(4);
                    current.writer.writeUTF("Alguien ha rechazado la salida mutua. No se llevo acabo");
                } catch (IOException ex) {
                    Logger.getLogger(GroupExitCommand.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        ArrayList<String> array = new ArrayList<String>();
        return array;  
    }
}
