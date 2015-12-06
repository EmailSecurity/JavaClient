/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailsecurity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
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
    String[] fileNameForEachEmail = new String[1000];
    File[] filesForEachEmail = new File[1000];
    String from, username, password;
    public void fetchAccountDetails(){
        
        try(BufferedReader br = new BufferedReader(new FileReader("accountDetailsFile.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            int i = 0;
            while (i<3 && line != null){
                if(i==0){
                    this.from = line;
                }
                else if(i==1){
                    this.username = line;
                }
                else if(i==2){
                    this.password = line;
                }
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
                i++;
            }
            String everything = sb.toString();
            System.out.println(this.from + this.username + this.password);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public FetchEmailFromServer(){
        
		String host = "pop.gmail.com";// change accordingly
                String mailStoreType = "pop3";
                String username = this.from;//"emailsecure4393@gmail.com";// change accordingly
                String password = this.password;//"eternaldoom12";// change accordingly

                check(host, mailStoreType, username, password);
    }
    public void check(String host, String storeType, String user, String password) {
            fetchAccountDetails();
		try {
                    System.out.println(this.from);
                    System.out.println(this.password);
            //create properties field
            Properties props = new Properties();

            props.setProperty("mail.store.protocol", "imaps");

            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imaps");
            store.connect("imap.gmail.com", this.from, this.password);
            //create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            Message[] messages = emailFolder.getMessages();
            System.out.println("messages.length---" + messages.length);
            for(int i = 0; i<messages.length; i++){
                fileNameForEachEmail[i] = "inbox" + File.separator + "inboxEmail" + i + ".eml";
                filesForEachEmail[i] = new File(fileNameForEachEmail[i]);
                System.out.println("Creating file");
            }
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
               for(int i = messages.length-1; i>=0; i--){ 
                try (ObjectOutputStream objOut = new ObjectOutputStream(new
                    FileOutputStream(filesForEachEmail[messages.length - (i+1)]))) {
                        messages[i].writeTo(objOut);
                    
                    }
                }
                EmailSecurity.c1.setVisible(false);
                ClientMainLayout c2 = new ClientMainLayout();
                EmailSecurity.c1 = c2;
                EmailSecurity.c1.show();
               }
               /*catch(Exception e){
                   e.printStackTrace();
               }
                   
            }*/
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
