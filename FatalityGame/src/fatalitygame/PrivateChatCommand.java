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
public class PrivateChatCommand extends BaseCommand{
    public static final String COMMAND_NAME = "privateChat";       
    
    @Override       
    public String getCommandName() {           
        return COMMAND_NAME;   
    }       
    
    @Override       
    public ArrayList<String> execute(ArrayList<String> args, OutputStream out) {           
        ArrayList<String> array = new ArrayList<String>();
        return array;  
    }
}
