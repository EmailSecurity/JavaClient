/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailsecurity;

/**
 *
 * @author akashsingh
 * @author abhijeetranadive
 */

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import static java.awt.GridBagConstraints.PAGE_START;
import java.awt.GridBagLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import javax.swing.*;



public class ClientMainLayout extends JFrame {
        public ClientMainLayout() throws IOException {
            
            setTitle("Email Client");
            setSize(640, 480);
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent windowEvent){
                    System.exit(0);
                }        
            });
            JButton composeMail = new JButton("Compose");
            composeMail.addActionListener(new composeMailActionListener());
            JButton receiveMail = new JButton("Fetch Mail");
            receiveMail.addActionListener(new ReceiveMailActionListener());
            JPanel toolBar = new JPanel();
            JPanel inbox = new JPanel(new GridBagLayout());
            JPanel emailBody = new JPanel();
            JLabel toolBarLabel = new JLabel("Toolbar");
            JLabel inboxLabel = new JLabel("Received Emails");
            JLabel emailBodyLabel = new JLabel("Email Body");
            GridBagConstraints inboxConstraints = new GridBagConstraints();
            
            toolBar.add(toolBarLabel);
            toolBar.add(composeMail);
            toolBar.add(receiveMail);
            inboxConstraints.weightx= inboxConstraints.weighty= 1.0;
            inboxConstraints.gridx = 0;
            inboxConstraints.gridy = 0;
            inboxConstraints.gridwidth = 0;
            inboxConstraints.anchor = GridBagConstraints.PAGE_START;
            inbox.add(inboxLabel, inboxConstraints);
            emailBody.add(emailBodyLabel);
            String[] subjectToDisplay = new String[1000];
             try(FileInputStream inboxFile = new FileInputStream("inboxEmails.eml")){
                    try {
                    /*Properties props = new Properties();

                    props.setProperty("mail.store.protocol", "imaps");

                    Session session = Session.getDefaultInstance(props, null);
                   
                    InputStream source = inboxFile;
                    MimeMessage message = new MimeMessage(session, source);
                 
                    subjectToDisplay[0] =  message.getSubject().toString();
                    System.out.println("From : " + message.getFrom()[0]);
                    System.out.println("--------------");
                    System.out.println("Body : " +  message.getContent());*/
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
                    for(int i = 0; i<messages.length; i++){
                        subjectToDisplay[i] = messages[i].getSubject();
                    }

                    } catch (NoSuchProviderException e) {
                       e.printStackTrace();
                    } catch (MessagingException e) {
                       e.printStackTrace();
                    } catch (Exception e) {
                       e.printStackTrace();
                    }
                    }
             
            JList listOfEmails = new JList(subjectToDisplay); 
            
            inboxConstraints.gridx = 0;
            inboxConstraints.gridy = 1;
            JScrollPane inboxListScrollabel = new JScrollPane(listOfEmails);
            
            inbox.add(inboxListScrollabel, inboxConstraints);
            JSplitPane splitPaneOne = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, inbox, emailBody);
            splitPaneOne.setOneTouchExpandable(false);
            splitPaneOne.setDividerSize(20);
            JSplitPane splitPaneTwo = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
                    true, toolBar, splitPaneOne);
            splitPaneTwo.setOneTouchExpandable(false);
            splitPaneTwo.setEnabled(false);
            splitPaneTwo.setDividerLocation(50);
            splitPaneTwo.setDividerSize(0);
            
            getContentPane().add(splitPaneTwo);
            
            
    }
}
