/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comunicaciones;

import Comunicaciones.iClientMessage;
/**
 *
 * @author vchin
 */
public interface iObserved {
    public void notifyAll(iClientMessage msg);
    public void addObserver(iObserver observer);
}
