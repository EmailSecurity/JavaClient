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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.DataInputStream;
import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;


public class DisplayContentOfEmail{
    //String body1;
   
    public DisplayContentOfEmail(String subject, int index) throws IOException{
        EmailSecurity.c1.emailBody.removeAll();
        try {
            updateBody(subject, index);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(DisplayContentOfEmail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(DisplayContentOfEmail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(DisplayContentOfEmail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(DisplayContentOfEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
        EmailSecurity.c1.emailBody.validate();
        EmailSecurity.c1.emailBody.repaint();
        EmailSecurity.c1.validate();
        EmailSecurity.c1.repaint();
        System.out.println(EmailSecurity.c1.emailBody);
        
    }

    DisplayContentOfEmail() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    public void updateBody (String subject, int index) throws IOException, InvalidKeyException, IllegalBlockSizeException, InvalidAlgorithmParameterException, BadPaddingException{
        
        //body1=AESCrypt.Decryption();
         //JPanel updatedBody = new JPanel(new GridBagLayout());
            MimeMessage message;
           GridBagConstraints bodyConstraints = new GridBagConstraints(); 
           //bodyConstraints.anchor = GridBagConstraints.WEST;
           //bodyConstraints.insets = new Insets(5, 5, 5, 5);
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
                        if(message.isMimeType("text/plain")){
                            bodyConstraints.gridx = 0;
                            bodyConstraints.gridy = 0;
                            JLabel fromLabel = new JLabel("From: ");
                            EmailSecurity.c1.emailBody.add(fromLabel, bodyConstraints);
                            bodyConstraints.gridx = 1;
                            bodyConstraints.gridy = 0;
                            JLabel fromField = new JLabel(message.getFrom()[0].toString());
                            EmailSecurity.c1.emailBody.add(fromField, bodyConstraints);
                            bodyConstraints.gridx = 0;
                            bodyConstraints.gridy = 1;
                            JLabel subjectLabel = new JLabel("Subject: ");
                            EmailSecurity.c1.emailBody.add(subjectLabel, bodyConstraints);
                            bodyConstraints.gridx = 1;
                            bodyConstraints.gridy = 1;
                            JLabel subjectField = new JLabel(message.getSubject().toString());
                            EmailSecurity.c1.emailBody.add(subjectField, bodyConstraints);
                            bodyConstraints.gridx = 0;
                            bodyConstraints.gridy = 2;
                            JLabel bodyLabel = new JLabel("Body: ");
                            EmailSecurity.c1.emailBody.add(bodyLabel, bodyConstraints);
                            
                            bodyConstraints.gridx = 1;
                            bodyConstraints.gridy = 2;
                            String temp = message.getContent().toString();
                            String messagesReturned = decryptBodyOfEmail(temp);
                            JLabel bodyField = new JLabel(messagesReturned);
                            //JLabel bodyField = new JLabel(body1);
                            
                            EmailSecurity.c1.emailBody.add(bodyField,  bodyConstraints);
                            System.out.println("Reached if");
                        }
                        else{
                            bodyConstraints.gridx = 0;
                            bodyConstraints.gridy = 0;
                            JLabel fromLabel = new JLabel("From: ");
                            EmailSecurity.c1.emailBody.add(fromLabel, bodyConstraints);
                            bodyConstraints.gridx = 1;
                            bodyConstraints.gridy = 0;
                            JLabel fromField = new JLabel(message.getFrom()[0].toString());
                            EmailSecurity.c1.emailBody.add(fromField, bodyConstraints);
                            bodyConstraints.gridx = 0;
                            bodyConstraints.gridy = 1;
                            JLabel subjectLabel = new JLabel("Subject: ");
                            EmailSecurity.c1.emailBody.add(subjectLabel, bodyConstraints);
                            bodyConstraints.gridx = 1;
                            bodyConstraints.gridy = 1;
                            JLabel subjectField = new JLabel(message.getSubject().toString());
                            EmailSecurity.c1.emailBody.add(subjectField, bodyConstraints);
                            bodyConstraints.gridx = 0;
                            bodyConstraints.gridy = 2;
                            JLabel bodyLabel = new JLabel("Body: ");
                            EmailSecurity.c1.emailBody.add(bodyLabel, bodyConstraints);
                            bodyConstraints.gridx = 1;
                            bodyConstraints.gridy = 2;
                            JLabel bodyField = new JLabel("Format NOT Supported");
                            EmailSecurity.c1.emailBody.add(bodyField,  bodyConstraints);
                        }

                        } catch (NoSuchProviderException e) {
                           e.printStackTrace();
                        } catch (MessagingException e) {
                           e.printStackTrace();
                        } catch (Exception e) {
                           e.printStackTrace();
                        }
                        }
        System.out.println("return");
        //return updatedBody;
    }
   public String decryptBodyOfEmail(String incomingMessage) throws UnsupportedEncodingException, DataLengthException, InvalidCipherTextException, Exception{
        String body1;
        AESBouncyCastle a = new AESBouncyCastle();
        int lengthOfIncoming = incomingMessage.length();
        //System.out.println("Length of message incoming =" + lengthOfIncoming);
        String RSAEncryptedKey = incomingMessage.substring(0, 172);
        String IVInString = incomingMessage.substring(175, 199);
        String lengthOfCipherText = incomingMessage.substring(199, 201);
        int lengthOfCipherTextInt = Integer.parseInt(lengthOfCipherText);
        System.out.println("Length of Cipher text= " + lengthOfCipherTextInt);
        String encryptedBodyInString = incomingMessage.substring(210, 210+lengthOfCipherTextInt);
        System.out.println("Incoming Encrypted Message:" + encryptedBodyInString);
        System.out.println("IV: " + IVInString);
        //System.out.println("Key: " + keyInString);
        System.out.println("body: " + encryptedBodyInString);
        byte[] decodedIV = Base64.getDecoder().decode(IVInString);
        SecretKey originalIV = new SecretKeySpec(decodedIV, "AES");
        //PublicKey publicKeyOfReceiver;
        PrivateKey privateKeyOfReceiver;
            
        //publicKeyOfReceiver = readRSAPublicKey();
        privateKeyOfReceiver = readRSAPrivateKey();
        //String publicKeyString = Base64.getEncoder().encodeToString(publicKeyOfReceiver.getEncoded());//readFileAsString("id_rsa.pub");
        String privateKeyString = Base64.getEncoder().encodeToString(privateKeyOfReceiver.getEncoded());//readFileAsString("id_rsa");
        RSABouncyCastle rsa = new RSABouncyCastle(); 
        byte[] decryptedKey = rsa.decrypt(Base64.getDecoder().decode(RSAEncryptedKey), privateKeyString);
        String decryptedKeyInString = Base64.getEncoder().encodeToString(decryptedKey);
            
       byte[] decodedKey = Base64.getDecoder().decode(decryptedKeyInString);
       SecretKey originalKey = new SecretKeySpec(decodedKey, "AES");
       //System.out.println("IV encoded: " + originalIV.getEncoded());
       //System.out.println("Key encoded: " + originalKey.getEncoded());
       a.setKeyAndIV(originalKey.getEncoded(), originalIV.getEncoded());
       byte[] encryptedBodyInBytes = Base64.getDecoder().decode(encryptedBodyInString);
       byte[] decryptedBodyInBytes = a.decrypt(encryptedBodyInBytes);
       String decryptedBodyInString = new String(decryptedBodyInBytes, "UTF-8");
       System.out.println("--------------");
       System.out.println("decrypted body: " + decryptedBodyInString);
       return decryptedBodyInString;  
   }
        public PublicKey readRSAPublicKey() throws Exception{
            File publicKeyFile = new File("id_rsa.pub");
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
        public PrivateKey readRSAPrivateKey() throws Exception{
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
}