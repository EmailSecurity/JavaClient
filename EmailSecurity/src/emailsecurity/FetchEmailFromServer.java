/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailsecurity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;

/**
 *
 * @author akashsingh
 */
public class FetchEmailFromServer {
    public FetchEmailFromServer(){
        
		String host = "pop.gmail.com";// change accordingly
                String mailStoreType = "pop3";
                String username = "emailsecure4393@gmail.com";// change accordingly
                String password = "eternaldoom12";// change accordingly

                check(host, mailStoreType, username, password);
    }
    public void check(String host, String storeType, String user, String password) {

		try {

            //create properties field
            Properties props = new Properties();

            props.setProperty("mail.store.protocol", "imaps");

            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", "emailsecure4393@gmail.com", "eternaldoom12");
            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            System.out.println("messages.length---" + messages.length);
            
            for (int i = 0, n = messages.length; i < n; i++) {
               Message message = messages[i];
               System.out.println("---------------------------------");
               System.out.println("Email Number " + (i + 1));
               System.out.println("Subject: " + message.getSubject());
              
               System.out.println("From: " + message.getFrom()[0]);
               if (message.isMimeType("text/plain")) {
                System.out.println("Text: " + (String)message.getContent());
                
               }

            }
            try {
                
                try (ObjectOutputStream objOut = new ObjectOutputStream(new
                    FileOutputStream(new File("inboxEmails.eml")))) {
                   
                    for (Message message : messages) {
                        message.writeTo(objOut);
                        System.out.println("Writing to file");
                    }
                }
               
            }
        catch (Exception e) {
            e.printStackTrace();
        }
            //close the store and folder objects
            emailFolder.close(false);
            store.close();

            } catch (NoSuchProviderException e) {
               e.printStackTrace();
            } catch (MessagingException e) {
               e.printStackTrace();
            } catch (Exception e) {
               e.printStackTrace();
            }

	}
}
