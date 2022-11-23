/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Communication;

/**
 *
 * @author vchin
 */
interface iObserved {
    public void notificarTodos(String command, Object source);
    public void agregarObserver(iObserver observer);
}
