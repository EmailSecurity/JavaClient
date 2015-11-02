/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailsecurity;

/**
 *
 * @author akashsingh
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.mail.*;
import javax.mail.internet.*;

import java.util.*;
import javax.activation.*;

/**
 *
 * @author akashsingh
 */
public class SendEmailViaGmail {
    
    public SendEmailViaGmail(String toAddress,String subject,String body) {
      
      
      String to = toAddress;

     
      String from = "emailsecure4393@gmail.com"; //Please enter your gmail email address here
      final String username = "emailsecure4393";//Please enter your gmail user ID here
      final String password = "eternaldoom12";//Please enter your gmail password here

     
      String host = "smtp.gmail.com";

      Properties props = new Properties();
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.starttls.enable", "true");
      props.put("mail.smtp.host", host);
      props.put("mail.smtp.port", "587");

     
      Session session = Session.getInstance(props,
      new javax.mail.Authenticator() {
         protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(username, password);
         }
      });

      try {
         
         Message message = new MimeMessage(session);

       
         message.setFrom(new InternetAddress(from));

        
         message.setRecipients(Message.RecipientType.TO,
         InternetAddress.parse(to));

        
         message.setSubject(subject);

         
         message.setText(body);

       
         Transport.send(message);

         System.out.println("Sent message successfully....");

      } catch (MessagingException e) {
            throw new RuntimeException(e);
      }
   }
}

