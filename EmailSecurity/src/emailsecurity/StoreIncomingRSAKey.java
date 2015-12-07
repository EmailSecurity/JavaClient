/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailsecurity;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Properties;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;

/**
 *
 * @author akashsingh
 */
public class StoreIncomingRSAKey extends JFrame implements ActionListener{
    JTextField passwordField, resultField;
    JFrame composeWindow;
    int index;
    public StoreIncomingRSAKey(JFrame composeWindow, int index){
      
       this.index = index;
       composeWindow.setTitle("Authentication");
       composeWindow.setSize(700, 170);
       composeWindow.setLayout(new GridBagLayout());
       GridBagConstraints constraints = new GridBagConstraints();
       constraints.anchor = GridBagConstraints.WEST;
       constraints.insets = new Insets(5, 5, 5, 5);
       
       composeWindow.setBackground(Color.GRAY);
       JLabel toLabel = new JLabel("Password: ");
       JLabel subjectLabel = new JLabel("Result: ");
       
       JButton sendButton = new JButton("Authenticate");
       passwordField = new JTextField(30);
       passwordField.setBackground(Color.white);
       passwordField.setEditable(true);
       passwordField.setText("<enter password here>");
       passwordField.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(passwordField.getText().equals("<enter password here>")){
                    passwordField.setText("");
                }
            }   
       });
       resultField = new JTextField(30);
       resultField.setBackground(Color.white);
       resultField.setEditable(false);
       resultField.setText("");
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
       
      constraints.gridx = 0;
       constraints.gridy = 0;
       composeWindow.add(toLabel, constraints);
       constraints.gridx = 1;
       constraints.weightx = 1.0;
       //constraints.weighty = 1.0;
       constraints.fill = GridBagConstraints.HORIZONTAL;
       composeWindow.add(passwordField);
       constraints.gridx = 0;
       constraints.gridy = 1;
       composeWindow.add(subjectLabel, constraints);
        
       constraints.gridx = 1;
       constraints.fill = GridBagConstraints.HORIZONTAL;
       composeWindow.add(resultField, constraints);
       constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridheight = 2;
        constraints.fill = GridBagConstraints.BOTH;
        sendButton.setFont(new Font("Arial", Font.BOLD, 14));
        composeWindow.add(sendButton, constraints);
         
        sendButton.addActionListener(this);
        /*constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridheight = 1;
        constraints.gridwidth = 3;
         
        constraints.gridy = 3;
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;*/
         
        //composeWindow.add(new JScrollPane(bodyTextField), constraints);
        this.composeWindow = composeWindow;
       composeWindow.show();
       
   
    }
    @Override
        public void actionPerformed(ActionEvent e){
            MimeMessage message;
            String fileName = "inbox" + File.separator + "inboxEmail" + index + ".eml";
                    try(FileInputStream inboxFile = new FileInputStream(fileName)){
                        try {
                        Properties props = new Properties();

                        props.setProperty("mail.store.protocol", "imaps");

                        Session session = Session.getDefaultInstance(props, null);
                       
                        InputStream source = inboxFile;
                        message = new MimeMessage(session, source);
                        
                        String subjectToDisplay =  message.getSubject().toString();
                        System.out.println("From : " + message.getFrom()[0]);
                        System.out.println("--------------");
                        System.out.println("Body : " +  message.getContent());
                        decryptPublicKeyFromMessage(message.getContent().toString(), message.getFrom()[0].toString());
                        }
                        catch(Exception e1){
                            e1.printStackTrace();
                        }
                    }  
                    catch(Exception e1){
                            e1.printStackTrace();
                        }
        }
        void decryptPublicKeyFromMessage(String incomingMessage, String from) throws NoSuchAlgorithmException, InvalidKeySpecException, DataLengthException, InvalidCipherTextException, Exception{
            AESBouncyCastle a = new AESBouncyCastle();
            int lengthOfIncoming = incomingMessage.length();
            String IVInString = incomingMessage.substring(0, 24);
            String SaltInString = incomingMessage.substring(24, 48);
            String lengthOfCipherText = incomingMessage.substring(48, 51);
            int lengthOfCipherTextInt = Integer.parseInt(lengthOfCipherText);
            System.out.println("Length of Cipher text= " + lengthOfCipherTextInt);
            String encryptedBodyInString = incomingMessage.substring(51, 51+lengthOfCipherTextInt);
            System.out.println("Incoming Encrypted Message:" + encryptedBodyInString);
            System.out.println("IV: " + IVInString);
            //System.out.println("Key: " + keyInString);
            byte[] decodedSalt = Base64.getDecoder().decode(SaltInString);
            SecretKey salt = new SecretKeySpec(decodedSalt, "AES");
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            KeySpec keyspec = new PBEKeySpec(this.passwordField.getText().toCharArray(), salt.getEncoded(), 10000, 128);
            SecretKey sKey = factory.generateSecret(keyspec);
            byte[] decodedIV = Base64.getDecoder().decode(IVInString);
            SecretKey sIV = new SecretKeySpec(decodedIV, "AES");
            a.setKeyAndIV(sKey.getEncoded(), sIV.getEncoded());
            byte[] decryptedBody = a.decrypt(Base64.getDecoder().decode(encryptedBodyInString));
            String decryptedBodyInString = Base64.getEncoder().encodeToString(decryptedBody);
            this.resultField.setText("Success");
            System.out.println("Decrypted public key as received - " + decryptedBodyInString);
            System.out.println("Decrypted public key edited - " + decryptedBodyInString.substring(0, 216));
            String publicKeyInString = decryptedBodyInString.substring(0, 216);
            File publicKeyFile = new File("public_keys" + File.separator + from);
            try{
            byte[] byteKey = Base64.getDecoder().decode(publicKeyInString);
            X509EncodedKeySpec X509publicKey = new X509EncodedKeySpec(byteKey);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PublicKey publicKeyReceived = kf.generatePublic(X509publicKey);
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyReceived.getEncoded());
		FileOutputStream fos = new FileOutputStream(publicKeyFile);
		fos.write(x509EncodedKeySpec.getEncoded());
		fos.close();
           
            /*PrivateKey privateKey = readRSAPrivateKey();
            PublicKey tempPublic = readRSAPublicKey("temp_key_file");
            RSABouncyCastle rsa = new RSABouncyCastle(); 
            String tempString = "Akash SIngh";
            byte[] tempbyte = tempString.getBytes("UTF-8");
            byte[] tempEncrypted = rsa.encrypt(tempbyte, Base64.getEncoder().encodeToString(tempPublic.getEncoded()));
            byte[] tempdecrypted = rsa.decrypt(tempEncrypted, Base64.getEncoder().encodeToString(privateKey.getEncoded()));
            System.out.println(new String(tempdecrypted, "UTF-8"));*/
             }
            catch(Exception e2){
                e2.printStackTrace();
            }
        }
        /*public PrivateKey readRSAPrivateKey() throws Exception{
            File privateKeyFile = new File("private_key" + File.separator + "id_rsa");
            FileInputStream priFS = new FileInputStream(privateKeyFile);
            DataInputStream priDS = new DataInputStream(priFS);
            byte[] keyBytesPrivate = new byte[(int)privateKeyFile.length()];
            priDS.readFully(keyBytesPrivate);
            priDS.close();

            PKCS8EncodedKeySpec spec2 = new PKCS8EncodedKeySpec(keyBytesPrivate);
            KeyFactory kf2 = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = kf2.generatePrivate(spec2);
            return privateKey;
        }
        public PublicKey readRSAPublicKey(String publicKeyFileOfReceiver) throws Exception{
            File publicKeyFile = new File(publicKeyFileOfReceiver);
            /*if(!publicKeyFile.exists()){
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
        }*/
}
