
package controller;

import java.util.List;
import model.ConexionBD;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class Controlador  {

    public void scheduleEmails() {
        try {
            // Obtenemos la lista de clientes de la base de datos
            ConexionBD customerDAO = new ConexionBD();
            List<String> datos_cliente = customerDAO.getdatos_cliente();
            
            // Creamos el job que se encargará de enviar los correos electrónicos
            JobDetail job = JobBuilder.newJob(ControladosJob.class)
                    .withIdentity("EmailJob", "group1")
                    .build();
            
            // Configuramos el trigger para que se ejecute cada día a las 8:00 AM
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "group1")
                    .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(8, 0))
                    .build();
            
            // Programamos el job para que se ejecute con el trigger configurado
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
            
            // Enviamos un mensaje indicando que la programación de los correos electrónicos ha sido exitosa
            System.out.println("Los correos electrónicos han sido programados correctamente");
        } catch (SchedulerException ex) {
            ex.printStackTrace();
        }
    }
    
}

