/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Log;

//import com.mycompany.proxypattern.BDServer;
import Log.BDManager;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Vale
 */
public class BDManagerProxy implements BDServer {
    
    private BDManager bdManager;
    private FileWriter loginicial;
    private FileWriter logfinal;
    
    public BDManagerProxy() throws IOException {
    }
    
    private void agregarRegistroInicial(String accion, String valores) throws IOException{
        Date date = new Date(System.currentTimeMillis());
        loginicial = new FileWriter(System.getProperty("user.dir") + "/loginicial.txt", true);
        loginicial.write("\n" + date.toString() + accion + "tabla values" + valores);
        loginicial.close();
    }
    
    private void agregarRegistroFinal(String evaluado) throws IOException {
        Date date = new Date(System.currentTimeMillis());
        logfinal = new FileWriter(System.getProperty("user.dir") + "/logfinal.txt", true);
        logfinal.write("\n" + date.toString() + evaluado);
        logfinal.close();
    }
    
    @Override
    public int insert(String json) {
        if(bdManager == null){
            bdManager = new BDManager();
        }
        
        // Crear el log inicial
        try {
            agregarRegistroInicial(" insert into ", json);
        } catch (IOException ex) {
            Logger.getLogger(BDManagerProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Insertar
        int id = bdManager.insert(json);
        
        // Crear el log final
        
        if(id == -1){
            try {
                agregarRegistroFinal("fail");
            } catch (IOException ex) {
                Logger.getLogger(BDManagerProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            try {
                agregarRegistroFinal("success");
            } catch (IOException ex) {
                Logger.getLogger(BDManagerProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return id;
    }

    @Override
    public int delete(int id) {
        if(bdManager == null){
            bdManager = new BDManager();
        }
        
        // Crear el log inicial
        try {
            agregarRegistroInicial(" delete ", " row with id: " + id);
        } catch (IOException ex) {
            Logger.getLogger(BDManagerProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Eliminar
        int idFila = bdManager.delete(id);
        
        // Crear el log final
        if(idFila == -1){
            try {
                agregarRegistroFinal("fail");
            } catch (IOException ex) {
                Logger.getLogger(BDManagerProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            try {
                agregarRegistroFinal("success");
            } catch (IOException ex) {
                Logger.getLogger(BDManagerProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return idFila;
    }

    @Override
    public int update(int id, String json) {
        if(bdManager == null){
            bdManager = new BDManager();
        }
        
        // Crear el log inicial
        try {
            agregarRegistroInicial(" update ", json);
        } catch (IOException ex) {
            Logger.getLogger(BDManagerProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // Actualizar
        int idFila =bdManager.update(id, json);
        
        // Crear el log final
        
        if(idFila == -1){
            try {
                agregarRegistroFinal("fail");
            } catch (IOException ex) {
                Logger.getLogger(BDManagerProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            try {
                agregarRegistroFinal("success");
            } catch (IOException ex) {
                Logger.getLogger(BDManagerProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return idFila;
    }
    
    @Override
    public void mostrarTabla(){
        bdManager.mostrarTabla();
    }
    
}
