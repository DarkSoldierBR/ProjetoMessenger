/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package splash;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JWindow;
import javax.swing.SwingConstants;

/**
 *
 * @author Aluno
 */
public class Splash {
    private int largura = 400;
    private int altura = 300;
    private int tempo = 5000;
    private String caminho = "/splash/loading.gif";
    
    public void Splash(){
        JWindow janelaSplash = new JWindow();
        
        janelaSplash.getContentPane().add(new JLabel(
                "Conectando ao servidor...",
                //new ImageIcon(getClass().getResource(caminho)),
                SwingConstants.CENTER));
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimesion = toolkit.getScreenSize();
        
        janelaSplash.setBounds(
                (dimesion.width - largura)/2,
                (dimesion.height - altura)/2,
                largura,
                altura);
        
        janelaSplash.setVisible(true);
        
        try{
      //  //    view.Login login = new view.Login();
      
        cliente.view.Mensagem mensagem = new cliente.view.Mensagem();
            Thread.sleep(tempo);
            
            
            janelaSplash.dispose();
           mensagem.setVisible(true);
        }catch(Exception e){
            System.out.println("Ocorreu um erro:\n "+e);
        }
    }
}

