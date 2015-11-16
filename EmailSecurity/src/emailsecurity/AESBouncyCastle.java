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

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.BlockCipherPadding;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class AESBouncyCastle {
    private final BlockCipher AESCipher = new CBCBlockCipher(new AESEngine());
 
    private PaddedBufferedBlockCipher paddedBufferedBlockCipher;
    private CipherParameters ivAndKey;
    private BlockCipherPadding blockCipherPadding;
    public AESBouncyCastle(){
	    this.blockCipherPadding = new PKCS7Padding();
	    this.paddedBufferedBlockCipher = new PaddedBufferedBlockCipher(AESCipher, blockCipherPadding);
	}
    
 
    public void setKeyAndIV(byte[] key, byte[] iv) {
        this.ivAndKey = new ParametersWithIV(new KeyParameter(key), iv);
    }
 
    public byte[] encrypt(byte[] input)
            throws DataLengthException, InvalidCipherTextException {
        return processing(input, true);
    }
 
    public byte[] decrypt(byte[] input)
            throws DataLengthException, InvalidCipherTextException {
        return processing(input, false);
    }
 
    private byte[] processing(byte[] input, boolean encrypt)
            throws DataLengthException, InvalidCipherTextException {
 
        paddedBufferedBlockCipher.init(encrypt, ivAndKey);
 
        byte[] output = new byte[paddedBufferedBlockCipher.getOutputSize(input.length)];
        int bytesWrittenOut = paddedBufferedBlockCipher.processBytes(
            input, 0, input.length, output, 0);
        //System.out.println("\n Bytes written out = " + bytesWrittenOut);
        paddedBufferedBlockCipher.doFinal(output, bytesWrittenOut);
        //System.out.println("Reached processing func" + "input is: " + new String(input, "UTF-8") + "output is: "+ output.toString());
        
        return output;
 
    }
 
}

