/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Log;

/**
 *
 * @author Vale
 */
public interface BDServer {
    public int insert(String json);
    public int delete(int id);
    public int update(int id, String json);
    public void mostrarTabla();
}
