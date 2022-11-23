/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package fatalitygame;

import Communication.Cliente;
import Juego.Personaje.Personaje;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author vchin
 */
public class Main extends javax.swing.JDialog {

    public Cliente refCliente;
    private int id;
    
    public Main(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        consola = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        titulo2 = new javax.swing.JLabel();
        titulo3 = new javax.swing.JLabel();
        titulo4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        enemiestxta = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        rankingtxta = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        statustxta = new javax.swing.JTextArea();
        titulo5 = new javax.swing.JLabel();
        attackername = new javax.swing.JLabel();
        atacante = new javax.swing.JLabel();
        victim4 = new javax.swing.JLabel();
        porcentajelbl = new javax.swing.JLabel();
        victim1 = new javax.swing.JLabel();
        victim2 = new javax.swing.JLabel();
        victim3 = new javax.swing.JLabel();
        titulo6 = new javax.swing.JLabel();
        atacante1 = new javax.swing.JLabel();
        personajeN3 = new javax.swing.JLabel();
        weaponused = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        titulo7 = new javax.swing.JLabel();
        personaje4btn = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        personajeN1 = new javax.swing.JLabel();
        personajeN4 = new javax.swing.JLabel();
        personajeN2 = new javax.swing.JLabel();
        prc3 = new javax.swing.JLabel();
        prc1 = new javax.swing.JLabel();
        prc2 = new javax.swing.JLabel();
        prc4 = new javax.swing.JLabel();
        personaje1btn = new javax.swing.JButton();
        personaje2btn = new javax.swing.JButton();
        personaje3btn = new javax.swing.JButton();
        consola2 = new javax.swing.JScrollPane();
        consolaTxta = new javax.swing.JTextArea();
        typename = new javax.swing.JLabel();
        chatbtn = new javax.swing.JButton();

        consola.setBackground(new java.awt.Color(0, 0, 0));
        consola.setColumns(20);
        consola.setForeground(new java.awt.Color(51, 102, 255));
        consola.setRows(5);
        jScrollPane1.setViewportView(consola);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 0, 0));
        jPanel1.setLayout(null);

        titulo2.setFont(new java.awt.Font("Matura MT Script Capitals", 1, 48)); // NOI18N
        titulo2.setForeground(new java.awt.Color(208, 0, 0));
        titulo2.setText("Enemies");
        jPanel1.add(titulo2);
        titulo2.setBounds(10, 170, 210, 80);

        titulo3.setFont(new java.awt.Font("Matura MT Script Capitals", 1, 48)); // NOI18N
        titulo3.setForeground(new java.awt.Color(255, 129, 10));
        titulo3.setText("Last attack made");
        jPanel1.add(titulo3);
        titulo3.setBounds(320, 290, 420, 80);

        titulo4.setFont(new java.awt.Font("Matura MT Script Capitals", 1, 48)); // NOI18N
        titulo4.setForeground(new java.awt.Color(208, 0, 0));
        titulo4.setText("Ranking");
        jPanel1.add(titulo4);
        titulo4.setBounds(10, 0, 210, 80);

        enemiestxta.setBackground(new java.awt.Color(0, 0, 0));
        enemiestxta.setColumns(20);
        enemiestxta.setFont(new java.awt.Font("OCR A Extended", 0, 18)); // NOI18N
        enemiestxta.setForeground(new java.awt.Color(255, 255, 255));
        enemiestxta.setRows(5);
        jScrollPane2.setViewportView(enemiestxta);

        jPanel1.add(jScrollPane2);
        jScrollPane2.setBounds(10, 250, 280, 100);

        rankingtxta.setBackground(new java.awt.Color(0, 0, 0));
        rankingtxta.setColumns(20);
        rankingtxta.setFont(new java.awt.Font("OCR A Extended", 0, 18)); // NOI18N
        rankingtxta.setForeground(new java.awt.Color(255, 255, 255));
        rankingtxta.setRows(5);
        jScrollPane3.setViewportView(rankingtxta);

        jPanel1.add(jScrollPane3);
        jScrollPane3.setBounds(10, 80, 280, 90);

        statustxta.setBackground(new java.awt.Color(0, 0, 0));
        statustxta.setColumns(20);
        statustxta.setFont(new java.awt.Font("OCR A Extended", 0, 18)); // NOI18N
        statustxta.setForeground(new java.awt.Color(255, 255, 255));
        statustxta.setRows(5);
        jScrollPane4.setViewportView(statustxta);

        jPanel1.add(jScrollPane4);
        jScrollPane4.setBounds(10, 440, 280, 90);

        titulo5.setFont(new java.awt.Font("Matura MT Script Capitals", 1, 48)); // NOI18N
        titulo5.setForeground(new java.awt.Color(208, 0, 0));
        titulo5.setText("My Status");
        jPanel1.add(titulo5);
        titulo5.setBounds(10, 360, 380, 80);

        attackername.setBackground(new java.awt.Color(0, 51, 204));
        attackername.setFont(new java.awt.Font("OCR A Extended", 0, 24)); // NOI18N
        attackername.setText("Name");
        jPanel1.add(attackername);
        attackername.setBounds(470, 90, 210, 40);

        atacante.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/kitana.png"))); // NOI18N
        jPanel1.add(atacante);
        atacante.setBounds(550, 360, 200, 200);

        victim4.setFont(new java.awt.Font("OCR A Extended", 0, 24)); // NOI18N
        victim4.setForeground(new java.awt.Color(255, 255, 255));
        victim4.setText("Victim 4");
        jPanel1.add(victim4);
        victim4.setBounds(330, 250, 210, 40);

        porcentajelbl.setFont(new java.awt.Font("OCR A Extended", 0, 24)); // NOI18N
        porcentajelbl.setForeground(new java.awt.Color(255, 255, 255));
        porcentajelbl.setText("Name");
        jPanel1.add(porcentajelbl);
        porcentajelbl.setBounds(470, 480, 90, 40);

        victim1.setFont(new java.awt.Font("OCR A Extended", 0, 24)); // NOI18N
        victim1.setForeground(new java.awt.Color(255, 255, 255));
        victim1.setText("Victim 1");
        jPanel1.add(victim1);
        victim1.setBounds(330, 130, 210, 40);

        victim2.setFont(new java.awt.Font("OCR A Extended", 0, 24)); // NOI18N
        victim2.setForeground(new java.awt.Color(255, 255, 255));
        victim2.setText("Victim 2");
        jPanel1.add(victim2);
        victim2.setBounds(330, 170, 210, 40);

        victim3.setFont(new java.awt.Font("OCR A Extended", 0, 24)); // NOI18N
        victim3.setForeground(new java.awt.Color(255, 255, 255));
        victim3.setText("Victim 3");
        jPanel1.add(victim3);
        victim3.setBounds(330, 210, 210, 40);

        titulo6.setFont(new java.awt.Font("Matura MT Script Capitals", 1, 48)); // NOI18N
        titulo6.setForeground(new java.awt.Color(255, 186, 8));
        titulo6.setText("Your Team");
        jPanel1.add(titulo6);
        titulo6.setBounds(790, 0, 300, 80);

        atacante1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/kotal.png"))); // NOI18N
        jPanel1.add(atacante1);
        atacante1.setBounds(570, 80, 200, 200);

        personajeN3.setFont(new java.awt.Font("OCR A Extended", 0, 24)); // NOI18N
        personajeN3.setForeground(new java.awt.Color(255, 255, 255));
        personajeN3.setText("Personaje 3");
        jPanel1.add(personajeN3);
        personajeN3.setBounds(790, 520, 210, 40);

        weaponused.setFont(new java.awt.Font("OCR A Extended", 0, 24)); // NOI18N
        weaponused.setForeground(new java.awt.Color(255, 255, 255));
        weaponused.setText("Name");
        jPanel1.add(weaponused);
        weaponused.setBounds(470, 380, 140, 40);

        jLabel5.setFont(new java.awt.Font("OCR A Extended", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Weapon:");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(330, 380, 110, 40);

        jLabel6.setFont(new java.awt.Font("OCR A Extended", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Harm:");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(330, 480, 130, 40);

        jLabel7.setFont(new java.awt.Font("OCR A Extended", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Type:");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(330, 430, 110, 40);

        titulo7.setFont(new java.awt.Font("Matura MT Script Capitals", 1, 48)); // NOI18N
        titulo7.setForeground(new java.awt.Color(255, 129, 10));
        titulo7.setText("Last attack");
        jPanel1.add(titulo7);
        titulo7.setBounds(330, 0, 300, 80);

        personaje4btn.setBackground(new java.awt.Color(0, 0, 0));
        personaje4btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/erronblack.png"))); // NOI18N
        personaje4btn.setPreferredSize(new java.awt.Dimension(200, 200));
        personaje4btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                personaje4btnActionPerformed(evt);
            }
        });
        jPanel1.add(personaje4btn);
        personaje4btn.setBounds(1080, 320, 200, 200);

        jLabel8.setBackground(new java.awt.Color(0, 51, 204));
        jLabel8.setFont(new java.awt.Font("OCR A Extended", 0, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 129, 10));
        jLabel8.setText("Attacker");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(330, 90, 210, 40);

        personajeN1.setFont(new java.awt.Font("OCR A Extended", 0, 24)); // NOI18N
        personajeN1.setForeground(new java.awt.Color(255, 255, 255));
        personajeN1.setText("Personaje 1");
        jPanel1.add(personajeN1);
        personajeN1.setBounds(790, 280, 200, 40);

        personajeN4.setFont(new java.awt.Font("OCR A Extended", 0, 24)); // NOI18N
        personajeN4.setForeground(new java.awt.Color(255, 255, 255));
        personajeN4.setText("Personaje 4");
        jPanel1.add(personajeN4);
        personajeN4.setBounds(1080, 520, 200, 40);

        personajeN2.setFont(new java.awt.Font("OCR A Extended", 0, 24)); // NOI18N
        personajeN2.setForeground(new java.awt.Color(255, 255, 255));
        personajeN2.setText("Personaje 2");
        jPanel1.add(personajeN2);
        personajeN2.setBounds(1080, 280, 200, 40);

        prc3.setFont(new java.awt.Font("OCR A Extended", 0, 24)); // NOI18N
        prc3.setForeground(new java.awt.Color(255, 255, 255));
        prc3.setText("100%");
        jPanel1.add(prc3);
        prc3.setBounds(1000, 390, 70, 40);

        prc1.setFont(new java.awt.Font("OCR A Extended", 0, 24)); // NOI18N
        prc1.setForeground(new java.awt.Color(255, 255, 255));
        prc1.setText("100%");
        jPanel1.add(prc1);
        prc1.setBounds(1000, 150, 70, 40);

        prc2.setFont(new java.awt.Font("OCR A Extended", 0, 24)); // NOI18N
        prc2.setForeground(new java.awt.Color(255, 255, 255));
        prc2.setText("100%");
        jPanel1.add(prc2);
        prc2.setBounds(1290, 160, 70, 40);

        prc4.setFont(new java.awt.Font("OCR A Extended", 0, 24)); // NOI18N
        prc4.setForeground(new java.awt.Color(255, 255, 255));
        prc4.setText("100%");
        jPanel1.add(prc4);
        prc4.setBounds(1290, 400, 70, 40);

        personaje1btn.setBackground(new java.awt.Color(0, 0, 0));
        personaje1btn.setForeground(new java.awt.Color(0, 0, 0));
        personaje1btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/kitana.png"))); // NOI18N
        personaje1btn.setPreferredSize(new java.awt.Dimension(200, 200));
        personaje1btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                personaje1btnActionPerformed(evt);
            }
        });
        jPanel1.add(personaje1btn);
        personaje1btn.setBounds(790, 80, 200, 200);

        personaje2btn.setBackground(new java.awt.Color(0, 0, 0));
        personaje2btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/scorpion.png"))); // NOI18N
        personaje2btn.setPreferredSize(new java.awt.Dimension(200, 200));
        personaje2btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                personaje2btnActionPerformed(evt);
            }
        });
        jPanel1.add(personaje2btn);
        personaje2btn.setBounds(1080, 80, 200, 200);

        personaje3btn.setBackground(new java.awt.Color(0, 0, 0));
        personaje3btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/kronika.png"))); // NOI18N
        personaje3btn.setPreferredSize(new java.awt.Dimension(200, 200));
        personaje3btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                personaje3btnActionPerformed(evt);
            }
        });
        jPanel1.add(personaje3btn);
        personaje3btn.setBounds(790, 320, 200, 200);

        consolaTxta.setBackground(new java.awt.Color(0, 0, 0));
        consolaTxta.setColumns(20);
        consolaTxta.setFont(new java.awt.Font("OCR A Extended", 0, 18)); // NOI18N
        consolaTxta.setForeground(new java.awt.Color(0, 102, 255));
        consolaTxta.setRows(5);
        consolaTxta.setText(">");
        consola2.setViewportView(consolaTxta);

        jPanel1.add(consola2);
        consola2.setBounds(0, 560, 1360, 130);

        typename.setFont(new java.awt.Font("OCR A Extended", 0, 24)); // NOI18N
        typename.setForeground(new java.awt.Color(255, 255, 255));
        typename.setText("Name");
        jPanel1.add(typename);
        typename.setBounds(470, 430, 90, 40);

        chatbtn.setText("chat");
        chatbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chatbtnActionPerformed(evt);
            }
        });
        jPanel1.add(chatbtn);
        chatbtn.setBounds(310, 530, 72, 23);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1354, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 664, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void personaje3btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_personaje3btnActionPerformed
        Personaje per3 = refCliente.player.getPersonajes().get(2);
        CharacterCard card = new CharacterCard(per3);
        card.setVisible(true);
    }//GEN-LAST:event_personaje3btnActionPerformed

    private void personaje4btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_personaje4btnActionPerformed
        Personaje per4 = refCliente.player.getPersonajes().get(3);
        CharacterCard card = new CharacterCard(per4);
        card.setVisible(true);
    }//GEN-LAST:event_personaje4btnActionPerformed

    private void chatbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chatbtnActionPerformed
        try {
            refCliente.hiloCliente.writer.writeInt(2);
            refCliente.hiloCliente.writer.writeUTF("hola a todos");
        } catch (IOException ex) {
            System.out.println("error en pantalla");
        }
        
    }//GEN-LAST:event_chatbtnActionPerformed

    private void personaje1btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_personaje1btnActionPerformed
        Personaje per1 = refCliente.player.getPersonajes().get(0);
        CharacterCard card = new CharacterCard(per1);
        card.setVisible(true);
    }//GEN-LAST:event_personaje1btnActionPerformed

    private void personaje2btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_personaje2btnActionPerformed
        Personaje per2 = refCliente.player.getPersonajes().get(1);
        CharacterCard card = new CharacterCard(per2);
        card.setVisible(true);
    }//GEN-LAST:event_personaje2btnActionPerformed

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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Main dialog = new Main(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel atacante;
    private javax.swing.JLabel atacante1;
    private javax.swing.JLabel attackername;
    private javax.swing.JButton chatbtn;
    private javax.swing.JTextArea consola;
    private javax.swing.JScrollPane consola2;
    private javax.swing.JTextArea consolaTxta;
    private javax.swing.JTextArea enemiestxta;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JButton personaje1btn;
    private javax.swing.JButton personaje2btn;
    private javax.swing.JButton personaje3btn;
    private javax.swing.JButton personaje4btn;
    private javax.swing.JLabel personajeN1;
    private javax.swing.JLabel personajeN2;
    private javax.swing.JLabel personajeN3;
    private javax.swing.JLabel personajeN4;
    private javax.swing.JLabel porcentajelbl;
    private javax.swing.JLabel prc1;
    private javax.swing.JLabel prc2;
    private javax.swing.JLabel prc3;
    private javax.swing.JLabel prc4;
    private javax.swing.JTextArea rankingtxta;
    private javax.swing.JTextArea statustxta;
    private javax.swing.JLabel titulo2;
    private javax.swing.JLabel titulo3;
    private javax.swing.JLabel titulo4;
    private javax.swing.JLabel titulo5;
    private javax.swing.JLabel titulo6;
    private javax.swing.JLabel titulo7;
    private javax.swing.JLabel typename;
    private javax.swing.JLabel victim1;
    private javax.swing.JLabel victim2;
    private javax.swing.JLabel victim3;
    private javax.swing.JLabel victim4;
    private javax.swing.JLabel weaponused;
    // End of variables declaration//GEN-END:variables

    public void addMensaje(String string) {
        consolaTxta.setText(string);
    }

    public void setRefCliente(Cliente cliente) {
        this.refCliente = cliente;
    }
    public void setID(int num){
       this.id = num;
    }
    
    public void cargarPersonajes(){
        // Cargar personajes en la interfaz
        Personaje per1 = refCliente.player.getPersonajes().get(0);
        personajeN1.setText(per1.getNombre());
        victim1.setText(per1.getNombre());
        personaje1btn.setIcon(new javax.swing.ImageIcon(getClass().getResource(per1.getApariencia()))); 
        
        
        Personaje per2 = refCliente.player.getPersonajes().get(1);
        personajeN2.setText(per2.getNombre());
        victim2.setText(per2.getNombre());
        personaje2btn.setIcon(new javax.swing.ImageIcon(getClass().getResource(per2.getApariencia()))); 
        
        Personaje per3 = refCliente.player.getPersonajes().get(2);
        personajeN3.setText(per3.getNombre());
        victim3.setText(per3.getNombre());
        personaje3btn.setIcon(new javax.swing.ImageIcon(getClass().getResource(per3.getApariencia())));
        
        Personaje per4 = refCliente.player.getPersonajes().get(3);
        personajeN4.setText(per4.getNombre());
        victim4.setText(per4.getNombre());
        personaje4btn.setIcon(new javax.swing.ImageIcon(getClass().getResource(per4.getApariencia()))); 
    }
}
