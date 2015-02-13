package smartfriend.util.email.emailmanagement;

import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import smartfriend.util.email.dto.EmailDTO;

/**
 *
 * @author Nilaksha
 */
public class EmailEngine {

    public static boolean sendMail(EmailDTO emailDTO) throws MessagingException {

        final String username = "nilakshaperera@gmail.com";
        final String password = "wildflower@79333";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session;
        session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        Message message = new MimeMessage(session);
        
        //message.setFrom(new InternetAddress("nilakshaperera@gmail.com"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailDTO.getParentEmailAdd()));
        message.setSubject(emailDTO.getMsgSubject());

        // Create the message body
        BodyPart messageBodyPart = new MimeBodyPart();
        
        messageBodyPart.setText(emailDTO.getMsgBody());

        // Create multiple parts
        Multipart multipart = new MimeMultipart();

        multipart.addBodyPart(messageBodyPart);

        messageBodyPart = new MimeBodyPart();
        
        String filename = emailDTO.getAttachmentPath();
        DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(filename);
        multipart.addBodyPart(messageBodyPart);

        // Send the complete message parts
        message.setContent(multipart);
        Transport.send(message);

        System.out.println("Done");
        return true;
    }

    public static void main(String[] args) {
        
        EmailDTO emailDTO = new EmailDTO();
        
        emailDTO.setAttachmentPath("src/smartfriend/reports/pdf/rpt1-2015-02-09.pdf");
        emailDTO.setParentEmailAdd("isuuom2011@gmail.com");
        emailDTO.setMsgSubject("Number Learning Report");
        emailDTO.setMsgBody("Dear Parent," + "\n\nYor childs Number Learning Report is attached herewith.");
        
        try {
            EmailEngine.sendMail(emailDTO);
        } catch (MessagingException ex) {
            //Logger.getLogger(EmailEngine.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Try again");
        }
    }
}
