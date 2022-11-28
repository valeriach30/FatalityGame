/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.HashMap;

/**
 *
 * @author vchin
 */
public class CommandManager {
    private static CommandManager commandManager;       
    private static final HashMap<String, Class<? extends ICommand>> COMMANDS =          
            new HashMap<String, Class<? extends ICommand>>();       
    
    private CommandManager() {           
        registCommand(AttackCommand.COMMAND_NAME, AttackCommand.class);           
        registCommand(ChatCommand.COMMAND_NAME, ChatCommand.class);           
        registCommand(GiveUpCommand.COMMAND_NAME, GiveUpCommand.class);           
        registCommand(GroupExitCommand.COMMAND_NAME, GroupExitCommand.class);           
        registCommand(PassTurnCommand.COMMAND_NAME, PassTurnCommand.class);
        registCommand(PrivateChatCommand.COMMAND_NAME, PrivateChatCommand.class);
        registCommand(ReloadCommand.COMMAND_NAME, ReloadCommand.class);
        registCommand(SelectPlayerCommand.COMMAND_NAME, SelectPlayerCommand.class);
        registCommand(WildcardCommand.COMMAND_NAME, WildcardCommand.class);
        registCommand(LogCommand.COMMAND_NAME, LogCommand.class);
    }      
    
    public static synchronized CommandManager getIntance() {           
        if (commandManager == null) {               
            commandManager = new CommandManager();   
        }
        return commandManager;   
    }       
    
    public ICommand getCommand(String commandName) {           
        if (COMMANDS.containsKey(commandName.toUpperCase())) {               
            try {                   
                return COMMANDS.get(commandName.toUpperCase()).newInstance();
            } catch (Exception e) {   e.printStackTrace();                   
            return new ErrorCommand();   
            }           
        } 
        else {
            return new NotFoundCommand();   
        }   
    }       
    
    public void registCommand(String commandName, Class<? extends ICommand> command) {   
        COMMANDS.put(commandName.toUpperCase(), command);   
    }
}
