package servidor.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Servidor extends Thread {

    boolean ativo = true;
    Thread t;

    public void startThread() {

        // cria a thread
        t = new Thread(this, "Admin Thread");

        // informa que o thread foi criado
        System.out.println("thread  = " + t);

        // chama a função run()
        t.start();
    }

    public void stopThread() {

//        System.out.println("Interronpendo "+t.getName());
        ativo = false;
        try {
            t.isAlive();
            System.out.println("Não foi possivel encerrar a thread");

        } catch (Exception e) {
            System.out.println("A Thread foi encerrada com sucesso !");
        }

    }

    public void run() {
        while (ativo) {
            servidor.view.Main m = new servidor.view.Main();
            System.out.println(m.jTtexto.getClass());

            int port = 4999; //porta do socket

            try {

                ServerSocket ss = new ServerSocket(port);
                //if(!ss.isClosed()){
                    //m.jLiconStatus.setIcon("src/rocket.png");
                    //ImageIcon valid = new ImageIcon(getClass().getResource("/src/rocket.png"));
//                    BufferedImage images = (ImageIO.read(new File("icones/error.png")));
                  //  m.jLiconStatus.setIcon(new ImageIcon("../src/rocket.png"));
                   m.jLiconStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/error.png")));
                    m.jLiconStatus.repaint();
              //  }
                m.jTtexto.append("Esperando Conexao na Porta " + port);
                System.out.println("Esperando Conexao na Porta " + port);
                int id = 0;
                while (true) {

                    Socket s = ss.accept();
                    System.out.println(s.hashCode());
                    if (s.isConnected()) {

                        id++;

                        m.jTtexto.append("Client" + id + " connected(" + s.getLocalAddress() + ")");
                        System.out.println("Client" + id + " connected(" + s.getLocalAddress() + ")");
                        //   ClientHandler clientthread = new ClientHandler();
                        InputStreamReader in = new InputStreamReader(s.getInputStream());
                        BufferedReader bf = new BufferedReader(in);

                        while (s.isConnected()) {

                            String str = bf.readLine();

                            m.jTtexto.append("Client " + id + ": " + str);
                            System.out.println("Client " + id + ": " + str);
                        }
                    }
                }

            } catch (IOException ex) {
                m.jTtexto.append("Não foi possivel iniciar o servidor " + ex);
                System.out.println("Não foi possivel iniciar o servidor " + ex);
               //   m.jLiconStatus.setIcon("src/rocket.png");
                Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                stopThread(); //encerra a thread
            }
        }

    }

}
