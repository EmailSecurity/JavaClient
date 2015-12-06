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
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
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
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;

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
            KeyGenerator mKey;
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
            mKey = KeyGenerator.getInstance("AES");
            mKey.init(128);
            SecretKey macKey = mKey.generateKey();
            
            Digest digest = new SHA256Digest();
            HMac hmac = new HMac(digest);
            hmac.init(new KeyParameter(macKey.getEncoded()));
            
            System.out.println("IVTEMP = " + ivTemp);
            AESBouncyCastle a = new AESBouncyCastle();
            a.setKeyAndIV(sKey.getEncoded(), siv.getEncoded());
           
            String messageInString = Base64.getEncoder().encodeToString(macKey.getEncoded()) + this.bodyTextField.getText();
            byte[] messageInBytes = messageInString.getBytes("UTF-8");
            
            byte[] encMessageInBytes = a.encrypt(messageInBytes);
            System.out.println("Length of Enc Message = " + encMessageInBytes.length);
            String encMessageInString = Base64.getEncoder().encodeToString(encMessageInBytes);//new String(encMessageInBytes, "UTF-8");
            String temp = new String(messageInBytes, "UTF-8");
            for(int i = 0; i<messageInBytes.length; i++){
                System.out.println(messageInBytes[i]);
            }
            System.out.println(temp);
            hmac.update(Base64.getDecoder().decode(encMessageInString), 0, Base64.getDecoder().decode(encMessageInString).length);
            byte[] macTagInBytes = new byte[digest.getDigestSize()];
            hmac.doFinal(macTagInBytes, 0);
            String tagInString = Base64.getEncoder().encodeToString(macTagInBytes);
            //this.body = Base64.getEncoder().encodeToString(siv.getEncoded())+ "---" + Base64.getEncoder().encodeToString(sKey.getEncoded()) + encMessageInString.length() + "Separator" + encMessageInString;
            System.out.println("Size of tag in string = " + tagInString.length());
            
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
            
            
            
                PublicKey publicKeyOfReceiver;
                //PrivateKey privateKeyOfReceiver;
                try {
                    publicKeyOfReceiver = readRSAPublicKey("public_keys" + File.separator + this.toAddress);
                    //privateKeyOfReceiver = readRSAPrivateKey();
                    String publicKeyString = Base64.getEncoder().encodeToString(publicKeyOfReceiver.getEncoded());//readFileAsString("id_rsa.pub");
                    //String privateKeyString = Base64.getEncoder().encodeToString(privateKeyOfReceiver.getEncoded());//readFileAsString("id_rsa");
                    
                    
                    
                    RSABouncyCastle rsa = new RSABouncyCastle();
                    
                    //String tempString = "Akash SIngh";
                    //byte[] byteArrayOfKey = tempString.getBytes("UTF-8");//sKey.getEncoded();
                    byte[] encryptedKey = rsa.encrypt(sKey.getEncoded(), publicKeyString);
                    String encryptedKeyInString = Base64.getEncoder().encodeToString(encryptedKey);
                    System.out.println("RSA Encrypted key = " + encryptedKeyInString);
                    //byte[] encryptedMacKey = rsa.encrypt(macKey.getEncoded(), publicKeyString);
                    //String encryptedMacKeyInString = Base64.getEncoder().encodeToString(encryptedMacKey);
                    
                    //byte[] decryptedKey = rsa.decrypt(encryptedKey, privateKeyString);
                    //String decryptedKeyInString = Base64.getEncoder().encodeToString(decryptedKey);//new String(decryptedKey, "UTF-8");//Base64.getEncoder().encodeToString(decryptedKey);
                    //System.out.println("RSA Devrypted Key = " + decryptedKeyInString);
                    //System.out.println("Size of encrypted String = " + encryptedKeyInString.length());
                    String sizeOfEncMessage = String.format("%04d", encMessageInString.length());
                    //System.out.println("Size of key = " + encryptedKeyInString.length() + "Size of mac key = " + encryptedMacKeyInString.length() + "Size of tag" + tagInString.length());
                    this.body = encryptedKeyInString + "*#*" + tagInString + "*#*" + Base64.getEncoder().encodeToString(siv.getEncoded()) + "*#*" + sizeOfEncMessage + "*#*" + encMessageInString;
                    System.out.println("Total message length = " + this.body.length());
                } catch (Exception ex) {
                    Logger.getLogger(ClientSendMailLayout.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            
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
                try {
                    new SendEmailViaGmail(this.toAddress, this.subject, this.body);
                } catch (IOException ex) {
                    Logger.getLogger(ClientSendMailLayout.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.composeWindow.dispatchEvent(new WindowEvent(this.composeWindow, WindowEvent.WINDOW_CLOSING));
                
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
        public PrivateKey readRSAPrivateKey() throws Exception{
            File privateKeyFile = new File("id_rsa");
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
        public String readFileAsString(String filePath)
        throws java.io.IOException{
        StringBuffer fileData = new StringBuffer(1000);
        BufferedReader reader = new BufferedReader(
                new FileReader(filePath));
        char[] buf = new char[1024];
        int numRead=0;
        while((numRead=reader.read(buf)) != -1){
            String readData = String.valueOf(buf, 0, numRead);
            fileData.append(readData);
            buf = new char[1024];
        }
        reader.close();
        System.out.println(fileData.toString());
        return fileData.toString();
    }
    
}
