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
import javax.swing.*;


public class ClientMainLayout extends JFrame {
        public ClientMainLayout() {
            setTitle("Email Client");
            setSize(640, 480);
         
            JPanel toolBar = new JPanel();
            JPanel inbox = new JPanel();
            JPanel emailBody = new JPanel();
            JLabel toolBarLabel = new JLabel("Toolbar");
            JLabel inboxLabel = new JLabel("Received Emails");
            JLabel emailBodyLabel = new JLabel("Email Body");

            toolBar.add(toolBarLabel);
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
