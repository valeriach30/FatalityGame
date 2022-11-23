/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fatalitygame;

import java.io.OutputStream;
import java.util.ArrayList;

/**
 *
 * @author vchin
 */
public abstract class BaseCommand  implements ICommand {
    
    
    @Override       
    public abstract String getCommandName();       
    
    @Override       
    public abstract ArrayList<String> execute(ArrayList<String> args, OutputStream out);       
    
    public void write(OutputStream out, String message) {           
        try {   
            out.write(message.getBytes());   
            out.flush();           
        } 
        catch (Exception e) {   
            e.printStackTrace();   
        }   
    }
}
