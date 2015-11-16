/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailsecurity;

/**
 *
 * @author abhi
 */
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security; 
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.xml.bind.DatatypeConverter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
 
public class AESCrypt {
    static String plainText;
    static byte[] plainBytesDecrypted = new byte[1024];
    static byte[] cipherBytes = new byte[1024];
    static SecretKey key;
    static Cipher cipher;
    public AESCrypt() throws NoSuchAlgorithmException{
    KeyGenerator generator = KeyGenerator.getInstance("AES");
    generator.init(128); // The AES key size in number of bits
    key = generator.generateKey();
    
    }
    public static byte[] encryption(String plainBytes){
        
        try{
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            
             System.out.println("Original string:"+new String(plainBytes));
            // Generate the key first
           // KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            //keyGen.init(128);  // Key size
            //Key key = keyGen.generateKey();
                
                System.out.println("AES Key (Hex Form):"+bytesToHex(key.getEncoded()));
            // Create Cipher instance and initialize it to encrytion mode
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding","BC");  // Transformation of the algorithm
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipherBytes = cipher.doFinal(plainBytes.getBytes());
             System.out.println("Encrypted data : "+new String(cipherBytes));
             
            // Reinitialize the Cipher to decryption mode
               
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return cipherBytes;
    }
    private static String  bytesToHex(byte[] hash) {

	        return DatatypeConverter.printHexBinary(hash);

    }
                
    public static String Decryption() throws InvalidKeyException, IllegalBlockSizeException, InvalidAlgorithmParameterException, BadPaddingException{
                    
            cipher.init(Cipher.DECRYPT_MODE,key, cipher.getParameters());
            plainBytesDecrypted = cipher.doFinal(cipherBytes);
             
            System.out.println("Decrypted data : "+new String(plainBytesDecrypted)); 
             // System.out.println();
            return(new String(plainBytesDecrypted));
    }
}