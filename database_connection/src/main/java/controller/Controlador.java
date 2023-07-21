package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.mail.MessagingException;
import javax.swing.JOptionPane;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import model.ConexionBD;
import model.Correo;
import view.Vista;

public class Controlador {

    private Correo emailSender;
    private ConexionBD databaseConnector;
    private Scheduler scheduler;
    public Vista vista;

    public Controlador(Correo emailSender, ConexionBD databaseConnector) {
        this.emailSender = emailSender;
        this.databaseConnector = databaseConnector;
    }

    public void iniciarEnvioAutomatico() {
        try {
            JobDetail job = JobBuilder.newJob(EnvioCorreosJob.class).build();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("envioCorreosTrigger", "envioCorreosGroup")
                    .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(10))
                    .build();

            scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void detenerEnvioAutomatico() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void enviarCorreos() {
        try {
            ResultSet rs = databaseConnector.executeQuery("SELECT ID, NameC, EmailC, ProduC, Precio, fecha_vencimiento, estado_renovacion FROM datos_cliente WHERE estado_renovacion = 'No'");
            while (rs.next()) {
                String recipientID = rs.getString("ID");
                String recipientName = rs.getString("NameC");
                String recipientEmail = rs.getString("EmailC");
                String productName = rs.getString("ProduC");
                String recipientPrecio = rs.getString("Precio");
                String recipientFecha = rs.getString("fecha_vencimiento");
                String recipientReno = rs.getString("estado_renovacion");

                emailSender.sendEmail(recipientID, recipientEmail, recipientName, productName, recipientPrecio, recipientFecha, recipientReno);
            }
            JOptionPane.showMessageDialog(vista, "Se han enviado los correos correctamente");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error al acceder a la base de datos");
        } catch (MessagingException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Error al enviar los correos");
        } finally {
            try {
                databaseConnector.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
