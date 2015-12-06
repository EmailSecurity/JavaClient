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
import javax.swing.*;



public class ClientMainLayout extends JFrame {
    String[] fileNameForEachEmail = new String[1000];
    int counter = 0;
    JPanel emailBody;
    JLabel emailBodyLabel;
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
            JButton sendPublicKey = new JButton("Send Keys");
            sendPublicKey.addActionListener(new SendPublicKeysActionListener());
            JPanel toolBar = new JPanel();
            /*JPanel inbox = new JPanel(new GridBagLayout());*/
            this.emailBody = new JPanel(new GridBagLayout());
            JLabel toolBarLabel = new JLabel("Toolbar");
            /*JLabel inboxLabel = new JLabel("Received Emails");*/
            this.emailBodyLabel = new JLabel("Email Body");
            /*GridBagConstraints inboxConstraints = new GridBagConstraints();*/
            
            toolBar.add(toolBarLabel);
            toolBar.add(composeMail);
            toolBar.add(receiveMail);
            toolBar.add(sendPublicKey);
            
            this.emailBody.add(emailBodyLabel);
            
            GenerateClientInboxView inboxInit = new GenerateClientInboxView();
            JPanel inbox = inboxInit.returnInboxJFrameObject();
            JSplitPane splitPaneOne = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true, inbox, this.emailBody);
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

