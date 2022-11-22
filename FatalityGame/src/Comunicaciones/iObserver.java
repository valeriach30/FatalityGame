/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comunicaciones;

/**
 *
 * @author vchin
 */
public interface iObserver {
    public void notificar(iClientMessage msg);
}
