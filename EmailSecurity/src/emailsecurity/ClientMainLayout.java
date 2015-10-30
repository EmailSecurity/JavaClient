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

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;



public class ClientMainLayout extends JFrame {
        public ClientMainLayout() {
            
            setTitle("Email Client");
            setSize(640, 480);
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent windowEvent){
                    System.exit(0);
                }        
            });
            JButton composeMail = new JButton("Compose");
            composeMail.addActionListener(new composeMailActionListener());
            JPanel toolBar = new JPanel();
            JPanel inbox = new JPanel();
            JPanel emailBody = new JPanel();
            JLabel toolBarLabel = new JLabel("Toolbar");
            JLabel inboxLabel = new JLabel("Received Emails");
            JLabel emailBodyLabel = new JLabel("Email Body");
            
            
            toolBar.add(toolBarLabel);
            toolBar.add(composeMail);
            inbox.add(inboxLabel);
            emailBody.add(emailBodyLabel);
            
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
