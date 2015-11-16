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
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;

public class ClientSendMailLayout extends JFrame implements ActionListener {
    String toAddress, subject, body;
    JTextField toTextField, subjectTextField;
    JTextArea bodyTextField;
    JFrame composeWindow;
    String ss;
    
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
                if(toTextField.getText().equals("<enter email address here>")){
                    toTextField.setText("");
                }
            }   
       });
       subjectTextField = new JTextField(30);
       subjectTextField.setBackground(Color.white);
       subjectTextField.setEditable(true);
       subjectTextField.setText("<enter subject here>");
       subjectTextField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(subjectTextField.getText().equals("<enter subject here>")){
                    System.out.println("Entered if");
                    subjectTextField.setText("");
                }
               else{
                    System.out.println("Couldn't enter if");
                }
            }
       });
       bodyTextField = new JTextArea(10, 30);
       bodyTextField.setBackground(Color.white);
       bodyTextField.setEditable(true);
       bodyTextField.setText("<enter email body here>");
       bodyTextField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(bodyTextField.getText().equals("<enter email body here>")){
                    bodyTextField.setText("");  
                }
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
            //this.body = this.bodyTextField.getText();
            KeyGenerator key;
            KeyGenerator iv;
            byte[] ivValue = new byte[16];
            byte[] ivTemp = new byte[16];
            //SecretKey sKey;
        try {
            key = KeyGenerator.getInstance("AES");
            key.init(128);
            SecretKey sKey = key.generateKey();
            iv = KeyGenerator.getInstance("AES");
            iv.init(128);
            SecretKey siv = iv.generateKey();
            
            System.out.println("IVTEMP = " + ivTemp);
            AESBouncyCastle a = new AESBouncyCastle();
            a.setKeyAndIV(sKey.getEncoded(), siv.getEncoded());
           
            String messageInString = this.bodyTextField.getText();
            byte[] messageInBytes = messageInString.getBytes("UTF-8");
            
            byte[] encMessageInBytes = a.encrypt(messageInBytes);
            System.out.println("Length of Enc Message = " + encMessageInBytes.length);
            String encMessageInString = Base64.getEncoder().encodeToString(encMessageInBytes);//new String(encMessageInBytes, "UTF-8");
            String temp = new String(messageInBytes, "UTF-8");
            for(int i = 0; i<messageInBytes.length; i++){
                System.out.println(messageInBytes[i]);
            }
            System.out.println(temp);
            this.body = Base64.getEncoder().encodeToString(siv.getEncoded())+ "---" + Base64.getEncoder().encodeToString(sKey.getEncoded()) + encMessageInString.length() + "Separator" + encMessageInString;
            
            
            /*------------------For Troubleshooting AES Decryption Errors-------------------
            String ivInString = Base64.getEncoder().encodeToString(siv.getEncoded());
            String keyInString = Base64.getEncoder().encodeToString(sKey.getEncoded());
            byte[] decodedIV = Base64.getDecoder().decode(ivInString);
            SecretKey originalIV = new SecretKeySpec(decodedIV, "AES");
            byte[] decodedKey = Base64.getDecoder().decode(ivInString);
            SecretKey originalKey = new SecretKeySpec(decodedKey, "AES");
            AESBouncyCastle a2 = new AESBouncyCastle();
            a2.setKeyAndIV(sKey.getEncoded(), siv.getEncoded());
            String temp2 = new String(a2.decrypt(Base64.getDecoder().decode(encMessageInString)));
            -----------------------------------------------------------------------------------------*/
            System.out.println("\n---------\n" + this.body + "\nlength of message outgoing= " + encMessageInString.length() + "\nEncrypted Message: " + encMessageInString); // + "\nDecrypted = " + temp2);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ClientSendMailLayout.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DataLengthException ex) {
            Logger.getLogger(ClientSendMailLayout.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidCipherTextException ex) {
            Logger.getLogger(ClientSendMailLayout.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(ClientSendMailLayout.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            if(this.toAddress.equals("<enter email address here>")){
                System.out.println("Please enter a valid email address");
            }
            else{
                new SendEmailViaGmail(this.toAddress, this.subject, this.body);
                this.composeWindow.dispatchEvent(new WindowEvent(this.composeWindow, WindowEvent.WINDOW_CLOSING));
                
            }
       }
   
    
}
