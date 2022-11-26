/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package fatalitygame;


import Modelo.Controlador;
import Communication.Cliente;
import Juego.Armas.Arma;
import Juego.Personaje.Personaje;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
/**
 *
 * @author vchin
 */
public class Configuracion extends javax.swing.JFrame {
    private int cantidadPersonajes = 0;
    private ArrayList<Arma> armasPersonaje = new ArrayList<Arma>();
    private ArrayList<Personaje> personajes = new ArrayList<Personaje>();
    private String tipoActual;
    private ArrayList<Integer> danhos = new ArrayList<Integer>();
    
    public Configuracion(){
        initComponents();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        titulo1 = new javax.swing.JLabel();
        imagentxt = new javax.swing.JTextField();
        lblTitulo16 = new javax.swing.JLabel();
        fuegobtn = new javax.swing.JButton();
        hierrobtn = new javax.swing.JButton();
        airebtn = new javax.swing.JButton();
        aguabtn = new javax.swing.JButton();
        magiabbtn = new javax.swing.JButton();
        magianbtn = new javax.swing.JButton();
        electricidadbtn = new javax.swing.JButton();
        hielobtn = new javax.swing.JButton();
        acidbtn = new javax.swing.JButton();
        espiritualidadbtn = new javax.swing.JButton();
        crearbtn = new javax.swing.JButton();
        weapontxt = new javax.swing.JTextField();
        personaje4 = new javax.swing.JLabel();
        personaje1 = new javax.swing.JLabel();
        personaje3 = new javax.swing.JLabel();
        personaje2 = new javax.swing.JLabel();
        titulo2 = new javax.swing.JLabel();
        nombretxt = new javax.swing.JTextField();
        agregarArmabtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setLayout(null);

        titulo1.setFont(new java.awt.Font("Matura MT Script Capitals", 1, 48)); // NOI18N
        titulo1.setForeground(new java.awt.Color(255, 186, 8));
        titulo1.setText("choosen characters");
        jPanel1.add(titulo1);
        titulo1.setBounds(580, 420, 440, 80);

        imagentxt.setBackground(new java.awt.Color(51, 51, 51));
        imagentxt.setFont(new java.awt.Font("OCR A Extended", 0, 24)); // NOI18N
        imagentxt.setForeground(new java.awt.Color(185, 185, 185));
        imagentxt.setText("Image");
        imagentxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imagentxtActionPerformed(evt);
            }
        });
        jPanel1.add(imagentxt);
        imagentxt.setBounds(230, 120, 200, 40);

        lblTitulo16.setBackground(new java.awt.Color(255, 255, 255));
        lblTitulo16.setFont(new java.awt.Font("OCR A Extended", 0, 24)); // NOI18N
        lblTitulo16.setForeground(new java.awt.Color(244, 140, 6));
        lblTitulo16.setText("Type");
        jPanel1.add(lblTitulo16);
        lblTitulo16.setBounds(20, 170, 80, 40);

        fuegobtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fire.png"))); // NOI18N
        fuegobtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fuegobtnActionPerformed(evt);
            }
        });
        jPanel1.add(fuegobtn);
        fuegobtn.setBounds(20, 210, 90, 90);

        hierrobtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/iron.jpeg"))); // NOI18N
        hierrobtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hierrobtnActionPerformed(evt);
            }
        });
        jPanel1.add(hierrobtn);
        hierrobtn.setBounds(420, 320, 90, 90);

        airebtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/air.jpg"))); // NOI18N
        airebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                airebtnActionPerformed(evt);
            }
        });
        jPanel1.add(airebtn);
        airebtn.setBounds(120, 210, 90, 90);

        aguabtn.setBackground(new java.awt.Color(255, 255, 255));
        aguabtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/water.png"))); // NOI18N
        aguabtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aguabtnActionPerformed(evt);
            }
        });
        jPanel1.add(aguabtn);
        aguabtn.setBounds(220, 210, 90, 90);

        magiabbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/whitemagic.jpg"))); // NOI18N
        magiabbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                magiabbtnActionPerformed(evt);
            }
        });
        jPanel1.add(magiabbtn);
        magiabbtn.setBounds(320, 210, 90, 90);

        magianbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/blackmagic.jpg"))); // NOI18N
        magianbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                magianbtnActionPerformed(evt);
            }
        });
        jPanel1.add(magianbtn);
        magianbtn.setBounds(420, 210, 90, 90);

        electricidadbtn.setBackground(new java.awt.Color(255, 255, 255));
        electricidadbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/electricity.jpg"))); // NOI18N
        electricidadbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                electricidadbtnActionPerformed(evt);
            }
        });
        jPanel1.add(electricidadbtn);
        electricidadbtn.setBounds(20, 320, 90, 90);

        hielobtn.setBackground(new java.awt.Color(255, 255, 255));
        hielobtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ice.png"))); // NOI18N
        hielobtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hielobtnActionPerformed(evt);
            }
        });
        jPanel1.add(hielobtn);
        hielobtn.setBounds(120, 320, 90, 90);

        acidbtn.setBackground(new java.awt.Color(255, 255, 255));
        acidbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/acid.jpg"))); // NOI18N
        acidbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acidbtnActionPerformed(evt);
            }
        });
        jPanel1.add(acidbtn);
        acidbtn.setBounds(220, 320, 90, 90);

        espiritualidadbtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/spirit.jpg"))); // NOI18N
        espiritualidadbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                espiritualidadbtnActionPerformed(evt);
            }
        });
        jPanel1.add(espiritualidadbtn);
        espiritualidadbtn.setBounds(320, 320, 90, 90);

        crearbtn.setBackground(new java.awt.Color(232, 93, 4));
        crearbtn.setFont(new java.awt.Font("OCR A Extended", 0, 24)); // NOI18N
        crearbtn.setForeground(new java.awt.Color(0, 0, 0));
        crearbtn.setText("Create Character");
        crearbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crearbtnActionPerformed(evt);
            }
        });
        jPanel1.add(crearbtn);
        crearbtn.setBounds(220, 500, 280, 40);

        weapontxt.setBackground(new java.awt.Color(51, 51, 51));
        weapontxt.setFont(new java.awt.Font("OCR A Extended", 0, 24)); // NOI18N
        weapontxt.setForeground(new java.awt.Color(185, 185, 185));
        weapontxt.setText("Weapon");
        weapontxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                weapontxtActionPerformed(evt);
            }
        });
        jPanel1.add(weapontxt);
        weapontxt.setBounds(20, 440, 210, 40);
        jPanel1.add(personaje4);
        personaje4.setBounds(830, 230, 200, 200);
        jPanel1.add(personaje1);
        personaje1.setBounds(620, 20, 200, 200);
        jPanel1.add(personaje3);
        personaje3.setBounds(620, 230, 200, 200);
        jPanel1.add(personaje2);
        personaje2.setBounds(830, 20, 200, 200);

        titulo2.setFont(new java.awt.Font("Matura MT Script Capitals", 1, 48)); // NOI18N
        titulo2.setForeground(new java.awt.Color(208, 0, 0));
        titulo2.setText("Choose your fighters");
        jPanel1.add(titulo2);
        titulo2.setBounds(10, 20, 510, 80);

        nombretxt.setBackground(new java.awt.Color(51, 51, 51));
        nombretxt.setFont(new java.awt.Font("OCR A Extended", 0, 24)); // NOI18N
        nombretxt.setForeground(new java.awt.Color(185, 185, 185));
        nombretxt.setText("CharacterName");
        nombretxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombretxtActionPerformed(evt);
            }
        });
        jPanel1.add(nombretxt);
        nombretxt.setBounds(10, 120, 210, 40);

        agregarArmabtn.setBackground(new java.awt.Color(232, 93, 4));
        agregarArmabtn.setFont(new java.awt.Font("OCR A Extended", 0, 24)); // NOI18N
        agregarArmabtn.setForeground(new java.awt.Color(0, 0, 0));
        agregarArmabtn.setText("Add Weapon");
        agregarArmabtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarArmabtnActionPerformed(evt);
            }
        });
        jPanel1.add(agregarArmabtn);
        agregarArmabtn.setBounds(20, 500, 190, 40);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logoMK2.gif"))); // NOI18N
        jPanel1.add(jLabel1);
        jLabel1.setBounds(120, 80, 740, 420);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1044, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void imagentxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imagentxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_imagentxtActionPerformed

    private void weapontxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_weapontxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_weapontxtActionPerformed

    private void hierrobtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hierrobtnActionPerformed
        tipoActual = "hierro";
        disableTypes();
    }//GEN-LAST:event_hierrobtnActionPerformed

    private void crearbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crearbtnActionPerformed
        if(cantidadPersonajes != 4){
            
            // Aumentar contador
            cantidadPersonajes += 1;
            
            // Obtener la ruta de la imagen
            String ruta = imagentxt.getText();
            // Obtener el nombre del personaje
            String nombre = nombretxt.getText();
            if(armasPersonaje.size()==5){            
                switch(cantidadPersonajes){
                    case 1:
                        personaje1.setIcon(new javax.swing.ImageIcon(getClass().getResource(ruta))); 
                        ArrayList<Arma> armasPer1 = (ArrayList<Arma>) armasPersonaje.clone();
                        // Crear el personaje
                        Personaje nuevoPer = new Personaje();
                        nuevoPer.setArmas(armasPer1);
                        nuevoPer.setApariencia(ruta);
                        nuevoPer.setNombre(nombre);
                        nuevoPer.setNombreCategoria(tipoActual);
                        nuevoPer.setVida(100);
                        personajes.add(nuevoPer);
                        reset();
                        break;
                    case 2:
                        personaje2.setIcon(new javax.swing.ImageIcon(getClass().getResource(ruta))); 
                        ArrayList<Arma> armasPer2 = (ArrayList<Arma>) armasPersonaje.clone();
                        // Crear el personaje
                        Personaje nuevoPer2 = new Personaje();
                        nuevoPer2.setArmas(armasPer2);
                        nuevoPer2.setApariencia(ruta);
                        nuevoPer2.setNombre(nombre);
                        nuevoPer2.setNombreCategoria(tipoActual);
                        nuevoPer2.setVida(100);
                        personajes.add(nuevoPer2);
                        reset();
                        break;
                    case 3:
                        personaje3.setIcon(new javax.swing.ImageIcon(getClass().getResource(ruta))); 
                        ArrayList<Arma> armasPer3 = (ArrayList<Arma>) armasPersonaje.clone();
                        // Crear el personaje
                        Personaje nuevoPer3 = new Personaje();
                        nuevoPer3.setArmas(armasPer3);
                        nuevoPer3.setApariencia(ruta);
                        nuevoPer3.setNombre(nombre);
                        nuevoPer3.setNombreCategoria(tipoActual);
                        nuevoPer3.setVida(100);
                        personajes.add(nuevoPer3);
                        reset();
                        break;
                    case 4:
                        personaje4.setIcon(new javax.swing.ImageIcon(getClass().getResource(ruta))); 
                        ArrayList<Arma> armasPer4 = (ArrayList<Arma>) armasPersonaje.clone();
                        // Crear el personaje
                        Personaje nuevoPer4 = new Personaje();
                        nuevoPer4.setArmas(armasPer4);
                        nuevoPer4.setApariencia(ruta);
                        nuevoPer4.setNombre(nombre);
                        nuevoPer4.setNombreCategoria(tipoActual);
                        nuevoPer4.setVida(100);
                        personajes.add(nuevoPer4);
                        reset();
                        break;
                    default:
                        break;
                }
            }
            else{
                JOptionPane.showMessageDialog(null, "Debe crear 5 armas primero", "Error", JOptionPane.OK_OPTION);
            }
        }
        else{
            // Ir a la pantalla cliente
            this.setVisible(false);
            Main pant1 = new Main(this, true);
            Cliente c = new Cliente(pant1, personajes);
            c.conectar();
            pant1.cargarPersonajes();
            pant1.setVisible(true);
        }
        
    }//GEN-LAST:event_crearbtnActionPerformed

    private void fuegobtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fuegobtnActionPerformed
        tipoActual = "fuego";
        disableTypes();
    }//GEN-LAST:event_fuegobtnActionPerformed

    private void airebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_airebtnActionPerformed
        tipoActual = "aire";
        disableTypes();
    }//GEN-LAST:event_airebtnActionPerformed

    private void aguabtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aguabtnActionPerformed
        tipoActual = "agua";
        disableTypes();
    }//GEN-LAST:event_aguabtnActionPerformed

    private void magiabbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_magiabbtnActionPerformed
        tipoActual = "magia blanca";
        disableTypes();
    }//GEN-LAST:event_magiabbtnActionPerformed

    private void magianbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_magianbtnActionPerformed
        tipoActual = "magia negra";
        disableTypes();
    }//GEN-LAST:event_magianbtnActionPerformed

    private void electricidadbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_electricidadbtnActionPerformed
        tipoActual = "electricidad";
        disableTypes();
    }//GEN-LAST:event_electricidadbtnActionPerformed

    private void hielobtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hielobtnActionPerformed
        tipoActual = "hielo";
        disableTypes();
    }//GEN-LAST:event_hielobtnActionPerformed

    private void acidbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acidbtnActionPerformed
        tipoActual = "acid";
        disableTypes();
    }//GEN-LAST:event_acidbtnActionPerformed

    private void espiritualidadbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_espiritualidadbtnActionPerformed
        tipoActual = "espiritualidad";
        disableTypes();
    }//GEN-LAST:event_espiritualidadbtnActionPerformed

    private void nombretxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombretxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombretxtActionPerformed

    private void agregarArmabtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarArmabtnActionPerformed
        if(armasPersonaje.size() < 5){
            generarDanhos();
            ArrayList<Integer> danhoscopia = (ArrayList<Integer>) danhos.clone();
            Arma nuevaAr = new Arma();
            nuevaAr.setName(weapontxt.getText());
            nuevaAr.setAvailable(true);
            nuevaAr.setDanhos(danhoscopia);
            armasPersonaje.add(nuevaAr);
            danhos.removeAll(danhos);
        }
        else{
            JOptionPane.showMessageDialog(null, "Solo puede crear 5 armas", "Error", JOptionPane.OK_OPTION);
            agregarArmabtn.setEnabled(false);
        }
        
    }//GEN-LAST:event_agregarArmabtnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Configuracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Configuracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Configuracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Configuracion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Configuracion().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton acidbtn;
    private javax.swing.JButton agregarArmabtn;
    private javax.swing.JButton aguabtn;
    private javax.swing.JButton airebtn;
    private javax.swing.JButton crearbtn;
    private javax.swing.JButton electricidadbtn;
    private javax.swing.JButton espiritualidadbtn;
    private javax.swing.JButton fuegobtn;
    private javax.swing.JButton hielobtn;
    private javax.swing.JButton hierrobtn;
    private javax.swing.JTextField imagentxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblTitulo16;
    private javax.swing.JButton magiabbtn;
    private javax.swing.JButton magianbtn;
    private javax.swing.JTextField nombretxt;
    private javax.swing.JLabel personaje1;
    private javax.swing.JLabel personaje2;
    private javax.swing.JLabel personaje3;
    private javax.swing.JLabel personaje4;
    private javax.swing.JLabel titulo1;
    private javax.swing.JLabel titulo2;
    private javax.swing.JTextField weapontxt;
    // End of variables declaration//GEN-END:variables

    private void disableTypes() {
        fuegobtn.setEnabled(false);
        airebtn.setEnabled(false);
        aguabtn.setEnabled(false);
        magiabbtn.setEnabled(false);
        magianbtn.setEnabled(false);
        electricidadbtn.setEnabled(false);
        hielobtn.setEnabled(false);
        acidbtn.setEnabled(false);
        espiritualidadbtn.setEnabled(false);
        hierrobtn.setEnabled(false);
    }
    
    private void reset() {
        fuegobtn.setEnabled(true);
        airebtn.setEnabled(true);
        aguabtn.setEnabled(true);
        magiabbtn.setEnabled(true);
        magianbtn.setEnabled(true);
        electricidadbtn.setEnabled(true);
        hielobtn.setEnabled(true);
        acidbtn.setEnabled(true);
        espiritualidadbtn.setEnabled(true);
        hierrobtn.setEnabled(true);
        agregarArmabtn.setEnabled(true);
        tipoActual = "";
        armasPersonaje.removeAll(armasPersonaje);
    }
    
    private void generarDanhos(){
        int min = 20;  
        int max = 100;  
        for (int i = 0; i < 10; i++) {
            int random = (int)(Math.random()*(max-min+1)+min);  
            danhos.add(random);
        }
    }
}
