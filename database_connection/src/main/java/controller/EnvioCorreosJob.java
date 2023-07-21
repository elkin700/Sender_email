package controller;

import java.sql.SQLException;
import model.Correo;
import model.ConexionBD;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class EnvioCorreosJob implements Job {

    private Correo emailSender;
    private ConexionBD databaseConnector;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        try {
            emailSender = new Correo("elkinvasquez1013@gmail.com", "rmjjvdzjtzurvmbe", "smtp.tu-servidor-smtp.com", 587);
            databaseConnector = new ConexionBD("jdbc:mysql://127.0.0.1:3306/Datos_clientes_suscripcion", "user", "password");

            Controlador controlador = new Controlador(emailSender, databaseConnector);
            controlador.enviarCorreos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
