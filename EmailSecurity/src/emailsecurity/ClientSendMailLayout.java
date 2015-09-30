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
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

public class ClientSendMailLayout extends JFrame implements ActionListener {
    String toAddress, subject, body;
    JTextField toTextField, subjectTextField;
    JTextArea bodyTextField;
    JFrame composeWindow;
   public ClientSendMailLayout(JFrame composeWindow){
       
       composeWindow.setTitle("Compose");
       composeWindow.setSize(640, 640);
       composeWindow.setLayout(new GridBagLayout());
       GridBagConstraints constraints = new GridBagConstraints();
       constraints.anchor = GridBagConstraints.WEST;
       constraints.insets = new Insets(5, 5, 5, 5);
       
       composeWindow.setBackground(Color.GRAY);
       JLabel toLabel = new JLabel("To: ");
       JLabel subjectLabel = new JLabel("Subject: ");
       JLabel bodyLabel = new JLabel("Body: ");
       JButton sendButton = new JButton("SEND");
       toTextField = new JTextField(30);
       toTextField.setBackground(Color.white);
       toTextField.setEditable(true);
       toTextField.setText("<enter email address here>");
       toTextField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                toTextField.setText("");
            }
       });
       subjectTextField = new JTextField(30);
       subjectTextField.setBackground(Color.white);
       subjectTextField.setEditable(true);
       subjectTextField.setText("<enter subject here>");
       subjectTextField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                subjectTextField.setText("");
            }
       });
       bodyTextField = new JTextArea(10, 30);
       bodyTextField.setBackground(Color.white);
       bodyTextField.setEditable(true);
       bodyTextField.setText("<enter email body here>");
       bodyTextField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                bodyTextField.setText("");
            }
       });
       constraints.gridx = 0;
       constraints.gridy = 0;
       composeWindow.add(toLabel, constraints);
       constraints.gridx = 1;
       constraints.fill = GridBagConstraints.HORIZONTAL;
       composeWindow.add(toTextField);
       constraints.gridx = 0;
       constraints.gridy = 1;
       composeWindow.add(subjectLabel, constraints);
        
       constraints.gridx = 1;
       constraints.fill = GridBagConstraints.HORIZONTAL;
       composeWindow.add(subjectTextField, constraints);
       constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        constraints.fill = GridBagConstraints.BOTH;
        sendButton.setFont(new Font("Arial", Font.BOLD, 16));
        composeWindow.add(sendButton, constraints);
         
        sendButton.addActionListener(this);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridheight = 1;
        constraints.gridwidth = 3;
         
        constraints.gridy = 3;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
         
        composeWindow.add(new JScrollPane(bodyTextField), constraints);
        this.composeWindow = composeWindow;
       composeWindow.show();
       
   }
   @Override
        public void actionPerformed(ActionEvent e){
            this.toAddress = this.toTextField.getText();
            this.subject = this.subjectTextField.getText();
            this.body = this.bodyTextField.getText();
            new SendEmailViaGmail(this.toAddress, this.subject, this.body);
            this.composeWindow.dispatchEvent(new WindowEvent(this.composeWindow, WindowEvent.WINDOW_CLOSING));
       }
   
    
}
