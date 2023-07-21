package view;

import controller.Controlador;
import model.ConexionBD;
import model.Correo;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

public class Vista extends JFrame {

    private JButton enviarCorreosButton;
    private JButton detenerEnvioButton;
    private Controlador controlador;
    private boolean envioAutomaticoActivo;

    public Vista(Controlador controlador) {
        this.controlador = controlador;
        setTitle("Envío de correos");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        enviarCorreosButton = new JButton("Enviar correos");
        enviarCorreosButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (!envioAutomaticoActivo) {
                    controlador.iniciarEnvioAutomatico();
                    envioAutomaticoActivo = true;
                    enviarCorreosButton.setText("Enviando...");
                    enviarCorreosButton.setEnabled(false);
                }
            }
        });

        detenerEnvioButton = new JButton("Detener envío");
        detenerEnvioButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (envioAutomaticoActivo) {
                    controlador.detenerEnvioAutomatico();
                    envioAutomaticoActivo = false;
                    enviarCorreosButton.setText("Enviar correos");
                    enviarCorreosButton.setEnabled(true);
                }
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.add(enviarCorreosButton);
        panel.add(detenerEnvioButton);

        add(panel);
    }

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://127.0.0.1:3306/Datos_clientes_suscripcion";
        String user = "user";
        String password = "password";
        String smtpHost = "smtp.tu-servidor-smtp.com";
        int smtpPort = 587;
        String senderEmail = "senderEmail@gmail.com";
        String senderPassword = "senderPassword";
        Correo emailSender = new Correo(senderEmail, senderPassword, smtpHost, smtpPort);
        ConexionBD databaseConnector = new ConexionBD(url, user, password);
        Controlador controlador = new Controlador(emailSender, databaseConnector);
        Vista vista = new Vista(controlador);
        vista.setVisible(true);
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 406, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
