package model;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Correo {

    private String senderEmail;
    private String senderPassword;
    private String smtpHost;
    private int smtpPort;
    private Session session;

    public Correo(String senderEmail, String senderPassword, String smtpHost, int smtpPort) {
        this.senderEmail = senderEmail;
        this.senderPassword = senderPassword;
        this.smtpHost = smtpHost;
        this.smtpPort = smtpPort;

        Properties props = new Properties();
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });
    }

    public void sendEmail(String recipientID, String recipientEmail, String recipientName, String productName, String recipientPrecio, String recipientFecha, String recipientReno) throws MessagingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
        message.setSubject("Renovación de servicio: ¡Mantén tu servicio actualizado!");
        String mensajeConte = "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>"
                + "</head>"
                + "<body>"
                + "<h4>Estimado(a) " + recipientName + " </h4>\n"
                + "<p>Espero que estés disfrutando de nuestro servicio y que esté satisfaciendo tus necesidades empresariales.<br> \n"
                + "Nos complace anunciar que se acerca la fecha de vencimiento de tu suscripción y queremos recordarte <br> \n"
                + "la importancia de renovarla para que continúes disfrutando de los beneficios y las últimas actualizaciones.<br> \n"
                + "Renovar antes del " + recipientFecha + " de este mes <br></p>\n"
                + "<h5>Beneficios de la renovación: </h5>\n"
                + "<p> -1 Acceso continuo a todas las características y funcionalidades de nuestros productos.<br> \n"
                + " -2 Actualizaciones regulares que incluyen mejoras de rendimiento y nuevas características.<br> \n"
                + " -3 Soporte técnico prioritario a través de nuestro equipo de atención al cliente.<br> \n"
                + "Para renovar tu suscripción, simplemente haz clic en el siguiente enlace:<br></p>\n"
                + "<a href=\"https://paginawebtest1htmlandcss.on.drv.tw/Pagina_Web/\" style=\"background-color: #4CAF50; color: white; padding: 10px 20px; text-decoration: none;\">Visitar sitio web</a>"
                + "<p>Si tienes alguna pregunta o necesitas ayuda adicional, no dudes en contactar a nuestro equipo de soporte.<br> \n"
                + "Agradecemos tu apoyo continuo y esperamos seguir brindándote un servicio excepcional en el futuro.<br> \n"
                + "Saludos cordiales,<br> \n"
                + "Empresa ------ <br> \n"
                + "Servicio de " + productName + "<br> \n"
                + "Valor para la renovación del servicio es de: " + recipientPrecio + "<br></p>\n"
                + "</body>"
                + "</html>";
        message.setContent(mensajeConte, "text/html; charset=utf-8");

        Transport.send(message);

        System.out.println("Correo enviado a " + recipientEmail);
    }
}
