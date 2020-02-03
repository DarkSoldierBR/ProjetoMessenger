/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the templte in the editor.
 */
package splash;

import java.util.logging.Level;
import java.util.logging.Logger;

public class main{
/**
 *
 * @author mateus
 */
public void Splash(){ 
    NewSplash newSplash = new NewSplash();
    int i = 0;
  
    newSplash.setVisible(true);
    try {
        Thread.sleep(5000);
    } catch (InterruptedException ex) {
        Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
    }
    newSplash.dispose();
    }
   

}

