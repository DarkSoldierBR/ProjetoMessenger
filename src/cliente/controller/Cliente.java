package cliente.controller;

import static cliente.view.Mensagem.jTdigitamensagem;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {

    public static void main(String[] args) throws IOException, InterruptedException {

        boolean connect = false;
        int limite = 3;

        while (connect == false) {
            if (limite >= 1) {
                try {
                    Socket s = new Socket("localhost", 4999);

                    //int i = 1;
                    System.out.println("Procurando servidor para se conectar...");

                    if (s.isConnected()) {

                        System.out.println("Conectado...");

                        Scanner sc = new Scanner(System.in);

                        //Envia mensagem para o servidor
                        PrintWriter pr = new PrintWriter(s.getOutputStream());
                        int want = 1;
                        while (want == 1) {
                            //     System.out.println("Deseja enviar uma mensagem ? Sim=1");
                            //     want = Integer.parseInt(sc.nextLine());
                            //     pr.println(want);
                            // //    pr.flush();
                            System.out.println("Digite uma mensagem para o servidor");
                           
    String mensagem = jTdigitamensagem.getText();
                            pr.println(mensagem);
                            pr.flush();
                        }

//                        //Recebe menssagem para o servidor
//                        InputStreamReader in = new InputStreamReader(s.getInputStream());
//                        BufferedReader bf = new BufferedReader(in);
//                        String str = bf.readLine();
//                        System.out.println("Server: " + str);
                    } else {

                    }

                } catch (ConnectException ex) {
                    System.out.println("Conexão com o servidor recusada,uma nova tentativa sera feita");
                    limite--;
                    System.out.println("Tentativas restantes: " + limite);
                    Thread.sleep(5000);
                }
            } else {
                System.out.println("Limite de tentativas excedido");
                System.exit(0);
            }
        }
    }

    public void conectar() throws InterruptedException {
        boolean connect = false;
        int limite = 3;

        while (connect == false) {
            if (limite >= 1) {
                try {
                    Socket s = new Socket("localhost", 4999);

                    //int i = 1;
                    System.out.println("Procurando servidor para se conectar...");

                } catch (ConnectException ex) {
                    System.out.println("Conexão com o servidor recusada,uma nova tentativa sera feita");
                    limite--;
                    System.out.println("Tentativas restantes: " + limite);
                    Thread.sleep(5000);
                } catch (IOException ex) {
                    Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("Limite de tentativas excedido");
                System.exit(0);
            }
        }
    }
}

//    public void enviarMensagem(String mensagem) {
//if (s.isConnected()) {
//
//                      //  mensa connect = true;
//                        System.out.println("Conectado...");
//
//                        Scanner sc = new Scanner(System.in);
//
//                        //Envia mensagem para o servidor
//                        PrintWriter pr = new PrintWriter(s.getOutputStream());
//                        int want = 1;
//                        while (want == 1) {
//                            //     System.out.println("Deseja enviar uma mensagem ? Sim=1");
//                            //     want = Integer.parseInt(sc.nextLine());
//                            //     pr.println(want);
//                            // //    pr.flush();
//                            System.out.println("Digite uma mensagem para o servidor");
//                            String msg = sc.nextLine();
//                            pr.println(msg);
//                            pr.flush();
//                        }
//    }}
//}
