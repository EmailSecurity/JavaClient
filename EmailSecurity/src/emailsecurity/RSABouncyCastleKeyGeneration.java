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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.bouncycastle.crypto.*;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;
import org.bouncycastle.util.io.pem.PemWriter;

public class RSABouncyCastleKeyGeneration {
    public RSABouncyCastleKeyGeneration(){ 
        File tempPublicKey = new File("public_keys" + File.separator + "emailsecure4393@gmail.com");
        File tempPrivateKey = new File("private_key" + File.separator + "id_rsa");
        if(!tempPublicKey.exists() && !tempPrivateKey.exists()){
    try{
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024);
            KeyPair key = keyGen.generateKeyPair();
            PrivateKey priv = key.getPrivate();
            PublicKey pub = key.getPublic();
            RSAPublicKey pubKey = (RSAPublicKey) key.getPublic();
            RSAPrivateKey priKey = (RSAPrivateKey) key.getPrivate();
            writeKeyFile(key, tempPublicKey, tempPrivateKey);
            String privateKey = new String(Base64.encode(priv.getEncoded(), 0,priv.getEncoded().length));
            String publicKey1 = new String(Base64.encode(pub.getEncoded(), 0,pub.getEncoded().length));
            String publicKey = new String(Base64.encode(publicKey1.getBytes(),0, publicKey1.getBytes().length));
            System.out.println("Public Key = " + publicKey1);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    
    }
    
    else {
    System.out.println("Keys Exist");
}
}
    private void writeKeyFile(KeyPair key, File publicKeyFile, File privateKeyFile) throws IOException {
        PrivateKey privateKey = key.getPrivate();
		PublicKey publicKey = key.getPublic();
 
		// Store Public Key.
		X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(
				publicKey.getEncoded());
		FileOutputStream fos = new FileOutputStream(publicKeyFile);
		fos.write(x509EncodedKeySpec.getEncoded());
		fos.close();
 
		// Store Private Key.
		PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(
				privateKey.getEncoded());
		fos = new FileOutputStream(privateKeyFile);
		fos.write(pkcs8EncodedKeySpec.getEncoded());
		fos.close();
    }
    
}
