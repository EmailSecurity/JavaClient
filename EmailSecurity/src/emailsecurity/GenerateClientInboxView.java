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


import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import static java.awt.GridBagConstraints.PAGE_START;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import javax.swing.*;


public class GenerateClientInboxView {
    int counter = 0;
    JPanel inbox = new JPanel(new BorderLayout());
    String[] fileNameForEachEmail = new String[1000];
    public GenerateClientInboxView() throws IOException{
    	
    	JLabel inboxLabel = new JLabel("Received Emails");
    	/*GridBagConstraints inboxConstraints = new GridBagConstraints();
    	inboxConstraints.weightx= inboxConstraints.weighty= 1.0;
            inboxConstraints.gridx = 0;
            inboxConstraints.gridy = 0;
            inboxConstraints.gridwidth = 0;
            inboxConstraints.anchor = GridBagConstraints.PAGE_START;
            inbox.add(inboxLabel, inboxConstraints);*/
        String[] subjectToDisplay = new String[1000];
            
            Files.walk(Paths.get("inbox/")).forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    this.counter++;
                }
            });
            System.out.println(counter);
            MimeMessage[] messages = new MimeMessage[counter];
            for(int i = 0; i<counter - 1; i++){
                fileNameForEachEmail[i] = "inbox" + File.separator + "inboxEmail" + i + ".eml";
                    try(FileInputStream inboxFile = new FileInputStream(fileNameForEachEmail[i])){
                        try {
                        Properties props = new Properties();

                        props.setProperty("mail.store.protocol", "imaps");

                        Session session = Session.getDefaultInstance(props, null);
                       
                        InputStream source = inboxFile;
                        messages[i] = new MimeMessage(session, source);
                        
                        subjectToDisplay[i] =  messages[i].getSubject().toString();
                        System.out.println("From : " + messages[i].getFrom()[0]);
                        System.out.println("--------------");
                        System.out.println("Body : " +  messages[i].getContent());
                        /*Properties props = new Properties();

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
                        for(int i = 0; i<messages.length; i++){
                            subjectToDisplay[i] = messages[i].getSubject();
                        }*/

                        } catch (NoSuchProviderException e) {
                           e.printStackTrace();
                        } catch (MessagingException e) {
                           e.printStackTrace();
                        } catch (Exception e) {
                           e.printStackTrace();
                        }
                        }
             
            JList listOfEmails = new JList(subjectToDisplay); 
            listOfEmails.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            MouseListener mouseListener = new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        String selectedItem = (String) listOfEmails.getSelectedValue();
                        int location = listOfEmails.getSelectedIndex();
                        System.out.println(location);
                        try {
                            DisplayContentOfEmail displaycontent = new DisplayContentOfEmail(selectedItem, location);
                        } catch (IOException ex) {
                            Logger.getLogger(GenerateClientInboxView.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            };
                
            listOfEmails.addMouseListener(mouseListener);
            /*inboxConstraints.gridx = 0;
            inboxConstraints.gridy = 1;*/
            JScrollPane inboxListScrollabel = new JScrollPane(listOfEmails);
            inbox.add(inboxLabel, BorderLayout.PAGE_START);
            inbox.add(inboxListScrollabel, BorderLayout.CENTER);
    }
}
	public JPanel returnInboxJFrameObject(){
		return this.inbox;
	}
}
