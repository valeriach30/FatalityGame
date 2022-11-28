/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Log;

import Log.BDServer;
import Log.TableRow;
import java.security.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Vale
 */
public class BDManager implements BDServer{

    private ArrayList<TableRow> tabla = new ArrayList<TableRow>();
    private AtomicInteger  idGenerator = new AtomicInteger(0);

    public BDManager() {
    }
    
    @Override
    public int insert(String json) {
        
        // Crear el timestamp
        Date date = new Date(System.currentTimeMillis());
        
        // Se inserta en la tabla
        try{
            TableRow nuevaFila = new TableRow(idGenerator.getAndIncrement(), json, date);
            tabla.add(nuevaFila);
            return nuevaFila.getId();
        } catch (Error e) {
            return -1;
        }
    }

    @Override
    public int delete(int id) {
        boolean success = false;
        // Buscar la fila
        for (int i = 0; i < tabla.size(); i++) {
            
            // Determinar si la fila es la que se quiere eliminar
            if(tabla.get(i).getId() == id){
                
                // Actualizar el timestamp
                Date date = new Date(System.currentTimeMillis());
                tabla.get(i).setCreateTS(date);
                
                // Eliminar fila
                tabla.remove(i);
                success = true;
            }
        }
        if(success == true){
            return id;
        }
        else{
            return -1;
        }
    }

    @Override
    public int update(int id, String json) {
        boolean success = false;
        // Buscar la fila
        for (int i = 0; i < tabla.size(); i++) {
            // Determinar si la fila es la que se quiere actualizar
            if(tabla.get(i).getId() == id){
                // Actualizar fila
                tabla.get(i).setJson(json);
                
                // Actualizar el timestamp
                Date date = new Date(System.currentTimeMillis());
                tabla.get(i).setCreateTS(date);
                
                success = true;
            }
        }
        if(success == true){
            return id;
        }
        else{
            return -1;
        }
    }
    @Override
    public void mostrarTabla(){
        System.out.println("--------------------------------Tabla--------------------------------");
        for (int i = 0; i < tabla.size(); i++) {
            System.out.println(tabla.get(i).toString());
        }
    }
}
