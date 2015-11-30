/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailsecurity;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.encodings.OAEPEncoding;
import org.bouncycastle.crypto.engines.RSAEngine;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import sun.misc.BASE64Decoder;

/**
 *
 * @author akashsingh
 */
public class RSABouncyCastle {
    AsymmetricBlockCipher oaep = new OAEPEncoding(new RSAEngine());
    BASE64Decoder base64Decoder = new BASE64Decoder();
    public byte[] encrypt(byte[] plainText, String key) throws InvalidCipherTextException, IOException{
        
       AsymmetricKeyParameter publicKey = (AsymmetricKeyParameter) PublicKeyFactory.createKey(base64Decoder.decodeBuffer(key));
       oaep.init(true, publicKey);
       byte[] encryptedText = oaep.processBlock(plainText, 0, plainText.length);
       return encryptedText;
    }
    public byte[] decrypt(byte[] cipherText, String key) throws InvalidCipherTextException, IOException{
        AsymmetricKeyParameter privateKey = (AsymmetricKeyParameter) PrivateKeyFactory.createKey(base64Decoder.decodeBuffer(key));
        oaep.init(false, privateKey);
        byte[] plainText = oaep.processBlock(cipherText, 0, cipherText.length);
        return plainText;
    }
}
