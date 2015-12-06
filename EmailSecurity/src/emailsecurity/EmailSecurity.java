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
import java.io.IOException;
import javafx.application.Application;
import javax.swing.*;
import java.io.File;


public class EmailSecurity {

    /**
     * @param args the command line arguments
     */
    
    static ClientMainLayout c1;
    
    public static void main(String[] args) throws IOException {
        System.out.println("Creating required directories");
        File inboxDirectory, publicKeysDirectory, privateKeyDirectory;
        inboxDirectory = new File("inbox");
        if(!inboxDirectory.exists()){
            inboxDirectory.mkdir();
        }
        else {
            System.out.println("inbox exists");
        }
        publicKeysDirectory = new File("public_keys");
        if(!publicKeysDirectory.exists()){
            publicKeysDirectory.mkdir();
        }
        else {
            System.out.println("public_keys exists");
        }
        privateKeyDirectory = new File("private_key");
        if(!privateKeyDirectory.exists()){
            privateKeyDirectory.mkdir();
        }
        else {
            System.out.println("private_key exists");
        }
        RSABouncyCastleKeyGeneration a = new RSABouncyCastleKeyGeneration();
        c1 = new ClientMainLayout();
        c1.show();
    }
    
    
}
