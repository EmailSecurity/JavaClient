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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;


public class DisplayContentOfEmail {
   
    public DisplayContentOfEmail(String subject, int index) throws IOException{
        EmailSecurity.c1.emailBody.removeAll();
        updateBody(subject, index);
        EmailSecurity.c1.emailBody.validate();
        EmailSecurity.c1.emailBody.repaint();
        EmailSecurity.c1.validate();
        EmailSecurity.c1.repaint();
        System.out.println(EmailSecurity.c1.emailBody);
        
    }
    public void updateBody (String subject, int index) throws IOException{
        //JPanel updatedBody = new JPanel(new GridBagLayout());
            MimeMessage message;
           GridBagConstraints bodyConstraints = new GridBagConstraints(); 
           //bodyConstraints.anchor = GridBagConstraints.WEST;
           //bodyConstraints.insets = new Insets(5, 5, 5, 5);
            String fileName = "inbox" + File.separator + "inboxEmail" + index + ".eml";
                    try(FileInputStream inboxFile = new FileInputStream(fileName)){
                        try {
                        Properties props = new Properties();

                        props.setProperty("mail.store.protocol", "imaps");

                        Session session = Session.getDefaultInstance(props, null);
                       
                        InputStream source = inboxFile;
                        message = new MimeMessage(session, source);
                        
                        String subjectToDisplay =  message.getSubject().toString();
                        System.out.println("From : " + message.getFrom()[0]);
                        System.out.println("--------------");
                        System.out.println("Body : " +  message.getContent());
                        if(message.isMimeType("text/plain")){
                            bodyConstraints.gridx = 0;
                            bodyConstraints.gridy = 0;
                            JLabel fromLabel = new JLabel("From: ");
                            EmailSecurity.c1.emailBody.add(fromLabel, bodyConstraints);
                            bodyConstraints.gridx = 1;
                            bodyConstraints.gridy = 0;
                            JLabel fromField = new JLabel(message.getFrom()[0].toString());
                            EmailSecurity.c1.emailBody.add(fromField, bodyConstraints);
                            bodyConstraints.gridx = 0;
                            bodyConstraints.gridy = 1;
                            JLabel subjectLabel = new JLabel("Subject: ");
                            EmailSecurity.c1.emailBody.add(subjectLabel, bodyConstraints);
                            bodyConstraints.gridx = 1;
                            bodyConstraints.gridy = 1;
                            JLabel subjectField = new JLabel(message.getSubject().toString());
                            EmailSecurity.c1.emailBody.add(subjectField, bodyConstraints);
                            bodyConstraints.gridx = 0;
                            bodyConstraints.gridy = 2;
                            JLabel bodyLabel = new JLabel("Body: ");
                            EmailSecurity.c1.emailBody.add(bodyLabel, bodyConstraints);
                            bodyConstraints.gridx = 1;
                            bodyConstraints.gridy = 2;
                            JLabel bodyField = new JLabel(message.getContent().toString());
                            EmailSecurity.c1.emailBody.add(bodyField,  bodyConstraints);
                            System.out.println("Reached if");
                        }
                        else{
                            bodyConstraints.gridx = 0;
                            bodyConstraints.gridy = 0;
                            JLabel fromLabel = new JLabel("From: ");
                            EmailSecurity.c1.emailBody.add(fromLabel, bodyConstraints);
                            bodyConstraints.gridx = 1;
                            bodyConstraints.gridy = 0;
                            JLabel fromField = new JLabel(message.getFrom()[0].toString());
                            EmailSecurity.c1.emailBody.add(fromField, bodyConstraints);
                            bodyConstraints.gridx = 0;
                            bodyConstraints.gridy = 1;
                            JLabel subjectLabel = new JLabel("Subject: ");
                            EmailSecurity.c1.emailBody.add(subjectLabel, bodyConstraints);
                            bodyConstraints.gridx = 1;
                            bodyConstraints.gridy = 1;
                            JLabel subjectField = new JLabel(message.getSubject().toString());
                            EmailSecurity.c1.emailBody.add(subjectField, bodyConstraints);
                            bodyConstraints.gridx = 0;
                            bodyConstraints.gridy = 2;
                            JLabel bodyLabel = new JLabel("Body: ");
                            EmailSecurity.c1.emailBody.add(bodyLabel, bodyConstraints);
                            bodyConstraints.gridx = 1;
                            bodyConstraints.gridy = 2;
                            JLabel bodyField = new JLabel("Format NOT Supported");
                            EmailSecurity.c1.emailBody.add(bodyField,  bodyConstraints);
                        }

                        } catch (NoSuchProviderException e) {
                           e.printStackTrace();
                        } catch (MessagingException e) {
                           e.printStackTrace();
                        } catch (Exception e) {
                           e.printStackTrace();
                        }
                        }
        System.out.println("return");
        //return updatedBody;
    }
}