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
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;

public class SendKeyWithPassword extends JFrame implements ActionListener {
    String toAddress, subject, body;
    JTextField toTextField, subjectTextField;
    JTextArea bodyTextField;
    JFrame composeWindow;
    String ss;
    String from;
    
   public SendKeyWithPassword(JFrame composeWindow){
       
       composeWindow.setTitle("Compose");
       composeWindow.setSize(640, 640);
       composeWindow.setLayout(new GridBagLayout());
       GridBagConstraints constraints = new GridBagConstraints();
       constraints.anchor = GridBagConstraints.WEST;
       constraints.insets = new Insets(5, 5, 5, 5);
       
       composeWindow.setBackground(Color.GRAY);
       JLabel toLabel = new JLabel("To: ");
       JLabel subjectLabel = new JLabel("Subject: ");
       JLabel bodyLabel = new JLabel("Password: ");
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
       subjectTextField.setEditable(false);
       subjectTextField.setText("RSA Key");
       /*subjectTextField.addMouseListener(new MouseAdapter(){
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
       });*/
       bodyTextField = new JTextArea(10, 30);
       bodyTextField.setBackground(Color.white);
       bodyTextField.setEditable(true);
       bodyTextField.setText("<enter password here>");
       bodyTextField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(bodyTextField.getText().equals("<enter password here>")){
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
            KeyGenerator saltSeed;
            SecretKey salt;
            KeyGenerator iv;
            try {
                fetchAccountDetails();
            } catch (IOException ex) {
                Logger.getLogger(SendKeyWithPassword.class.getName()).log(Level.SEVERE, null, ex);
            }
            try{
                saltSeed = KeyGenerator.getInstance("AES");
                saltSeed.init(128);
                salt = saltSeed.generateKey();
            
                byte[] ivValue = new byte[16];
                SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                KeySpec keyspec = new PBEKeySpec(this.bodyTextField.getText().toCharArray(), salt.getEncoded(), 1000, 128);
                SecretKey sKey = factory.generateSecret(keyspec);
                System.out.println(sKey.getClass().getName());
                System.out.println(Arrays.toString(sKey.getEncoded()));
                
                iv = KeyGenerator.getInstance("AES");
                iv.init(128);
                SecretKey siv = iv.generateKey();
                AESBouncyCastle a = new AESBouncyCastle();
                a.setKeyAndIV(sKey.getEncoded(), siv.getEncoded());
                PublicKey publicKeyOfReceiver;
                //PrivateKey privateKeyOfReceiver;
                try {
                    publicKeyOfReceiver = readRSAPublicKey("public_keys" + File.separator + this.from);
                    String publicKeyString = Base64.getEncoder().encodeToString(publicKeyOfReceiver.getEncoded());//
                    System.out.println("Size of public key being sent in text/string = " + publicKeyString.length());
                    System.out.println("public key in string" + publicKeyString);
                    byte[] publicKeyInBytes = Base64.getDecoder().decode(publicKeyString);//publicKeyString.getBytes("UTF-8");
                    byte[] encPublicKeyInBytes = a.encrypt(publicKeyInBytes);
                    String encPublicKeyInString = Base64.getEncoder().encodeToString(encPublicKeyInBytes);
                    
                    String saltInString, ivInString;
                    saltInString = Base64.getEncoder().encodeToString(salt.getEncoded());
                    ivInString = Base64.getEncoder().encodeToString(siv.getEncoded());
                    this.body = ivInString + saltInString + encPublicKeyInString.length() + encPublicKeyInString;
                    
                    
                    System.out.println("\n---------\n" + this.body + "\nlength of message outgoing= " + encPublicKeyInString.length() + "\nEncrypted Message: " + encPublicKeyInString);
                    if(this.toAddress.equals("<enter email address here>")){
                        System.out.println("Please enter a valid email address");
                    }
                    else{
                        try {
                            new SendEmailViaGmail(this.toAddress, this.subject, this.body);
                        } catch (IOException ex) {
                            Logger.getLogger(ClientSendMailLayout.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        this.composeWindow.dispatchEvent(new WindowEvent(this.composeWindow, WindowEvent.WINDOW_CLOSING));

                    }
                }
                catch(Exception e2){
                    e2.printStackTrace();
                }
                
            }    
            catch(Exception ec){
                ec.printStackTrace();
            }
            
            
            
        
    }
        public PublicKey readRSAPublicKey(String publicKeyFileOfReceiver) throws Exception{
            File publicKeyFile = new File(publicKeyFileOfReceiver);
            if(!publicKeyFile.exists()){
                System.out.println("Public key of recipient not found. Unable to encrypt");
                new SendEmailViaGmail(this.toAddress, this.subject, this.bodyTextField.getText());
                this.composeWindow.dispatchEvent(new WindowEvent(this.composeWindow, WindowEvent.WINDOW_CLOSING));
            }
            FileInputStream pubFS = new FileInputStream(publicKeyFile);
            DataInputStream pubDS = new DataInputStream(pubFS);
            byte[] keyBytes = new byte[(int)publicKeyFile.length()];
            pubDS.readFully(keyBytes);
            pubDS.close();
            
            X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey publicKey = kf.generatePublic(spec);
            
            return publicKey;
        }
    public void fetchAccountDetails() throws IOException{
        
        try(BufferedReader br = new BufferedReader(new FileReader("accountDetailsFile.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            int i = 0;
            while (i==0 && line != null){
                
                    this.from = line;
                
                i++;
            }
            String everything = sb.toString();
            System.out.println(this.from);// + this.username + this.password);
        }
        
        
    }
}
