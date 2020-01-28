package servidor.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor extends Thread {

    
    
     public void run(){

        int port = 4999;

         try {
             ServerSocket ss = new ServerSocket(port);
             System.out.println("Esperando Conexao na Porta " + port);
             
              
        int id = 0;
              while (true) {

           

                Socket s = ss.accept();
                  System.out.println(s.hashCode());
                if (s.isConnected()) {

                    id++;

                    System.out.println("Client" + id + " connected(" + s.getLocalAddress() + ")");

                    
                 //   ClientHandler clientthread = new ClientHandler();
                    InputStreamReader in = new InputStreamReader(s.getInputStream());
                    BufferedReader bf = new BufferedReader(in);
                    
                    if(s.isConnected()){
                    while(true){
                        
                    String str = bf.readLine();

                    System.out.println("Client " + id + ": " + str);
                }}}}
         } catch (IOException ex) {
             Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
         }

     
   }

}