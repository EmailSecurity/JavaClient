/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package emailsecurity;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 *
 * @author akashsingh
 */
public class SendPublicKeysActionListener extends JFrame implements ActionListener{
    public void actionPerformed(ActionEvent receiveButtonClick){
        SendKeyWithPassword f1 = new SendKeyWithPassword(new JFrame());

    }
}
