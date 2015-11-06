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


public class EmailSecurity {

    /**
     * @param args the command line arguments
     */
    
    static ClientMainLayout c1;
    
    public static void main(String[] args) throws IOException {
        
        c1 = new ClientMainLayout();
        c1.show();
    }
    
    
}
