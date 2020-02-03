package cliente.controller;

import cliente.view.Login;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mateus
 */
public class Conexao extends cliente.view.Mensagem {

    String host = "localhost";    //caminho do servidor do BD
    String database = "db_messenger";        //nome do seu banco de dados
    String url = "jdbc:mysql://" + host + "/" + database;
    String usuario = "messenger";        //nome de um usuário de seu BD      
    String senha = "1234";      //sua senha de acesso
    Connection conn;

    public void conectar() {

        try {
            conn = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conectado com sucesso!");

        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao conectar!\n" + ex);

        }

    }

    public void cadastrar(String apelido, char[] senha) {
        conectar();

        String sql1 = "select * from tbl_usuario where apelido like '" + apelido + "'";
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql1);

            if (rs.next()) { //verifica se o apelido ja esta cadastrado
                System.out.println("[Conexao]Este apelido ja esta sendo usado,tente outro");
                JOptionPane.showMessageDialog(rootPane, "Este apelido ja esta sendo usado,tente outro"); //exibe alerta cadastrado com sucesso

            } else {

                String Senha = String.valueOf(senha);
                MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
                byte messageDigest[] = algorithm.digest(Senha.getBytes("UTF-8"));

                StringBuilder hexString = new StringBuilder();
                for (byte b : messageDigest) {
                    hexString.append(String.format("%02X", 0xFF & b));
                }

                String senhahex = hexString.toString();

                String sql = "INSERT INTO tbl_usuario (apelido,senha) VALUES('" + apelido + "','" + senhahex + "');";

                st.executeUpdate(sql); //insere os dados no bd

                conn.close(); //fecha a conexao

                JOptionPane.showMessageDialog(rootPane, "Usuario Cadastrado com sucesso"); //exibe alerta cadastrado com sucesso

                cliente.view.Login login = new cliente.view.Login();
                login.setVisible(true); //chama a janela login

            }
        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao cadastrar!\n" + ex);
            JOptionPane.showMessageDialog(rootPane, "Ocorreu um erro ao cadastrar");

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void logar(String apelido, char[] senha) {
        Statement stm;

      //  cliente.view.Login login = new cliente.view.Login();
        cliente.model.UsuarioAutenticado usuarioautenticado = new cliente.model.UsuarioAutenticado();
        String sql = "select * from tbl_usuario where apelido like '" + apelido + "'";

        try {

            stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            if (rs.next()) {
                System.out.println("[Conexao]Usuario encontrado");

                String Senha = String.valueOf(senha);
                MessageDigest algorithm = MessageDigest.getInstance("SHA-256");
                byte messageDigest[] = algorithm.digest(Senha.getBytes("UTF-8"));

                StringBuilder hexString = new StringBuilder();
                for (byte b : messageDigest) {
                    hexString.append(String.format("%02X", 0xFF & b));
                }

                String senhahex = hexString.toString();

                if (rs.getString("senha").matches(senhahex)) {
                    System.out.println("[Conexao]Senha confere");

                    usuarioautenticado.setAutenticado(true);
                    usuarioautenticado.setApelido(rs.getString("apelido"));
                    usuarioautenticado.setId(rs.getInt("id"));

                    System.out.println("[Conexao]O id do usuario é: " + usuarioautenticado.getId());
                    System.out.println("[Conexao]O apelido do usuario é: " + usuarioautenticado.getApelido());
                    System.out.println("[Conexao]Esta autenticado? " + usuarioautenticado.isAutenticado());
                } else {
                    System.out.println("[Conexao]Senha não confere");
                    JOptionPane.showMessageDialog(rootPane, "Senha não confere");
                    

                }

            } else {
                System.out.println("[Conexao]Usuario não encontrado");
                JOptionPane.showMessageDialog(rootPane, "Usuario não encontrado");
                

            }
            conn.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao logar!\n" + ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void enviaMensagem(String mensagem) {
        //      view.Mensagem Mensagem = new view.Mensagem();
        //      view.Cadastrar Cadastrar = new view.Cadastrar();

        conectar();
        cliente.model.UsuarioAutenticado usuarioautenticado = new cliente.model.UsuarioAutenticado();

        String sql = "insert into tbl_mensagem (id_user,mensagem) values (?,?)";

        // if(usuarioautenticado.isAutenticado()){
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, usuarioautenticado.getId());
            ps.setString(2, mensagem);
            ps.execute();
            System.out.println("[Conexao]Mensagem enviada com sucesso!");
        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao enviar a mensagem!\n" + ex);
        }

    }

    public void atualizar(cliente.view.Mensagem mensagem) {
        conectar();
        // model.Mensagem mensagem = new model.Mensagem();
        cliente.model.UsuarioAutenticado usuarioautenticado = new cliente.model.UsuarioAutenticado();
        cliente.view.Mensagem m = new cliente.view.Mensagem();
        mensagem.jTmostramensagem.setText("");

        String sql = "select * from tbl_mensagem";
        String texto = "";
        String usuario, horario, message;

        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                usuario = usuarioautenticado.getApelido();
                horario = rs.getString(3);
                message = rs.getString(4);

                System.out.println("[Conexao] Usuario: " + usuario);
                System.out.println("[Conexao] Horario: " + horario);
                System.out.println("[Conexao] Mensagem: " + message);

                texto = "[" + horario + "] " + usuario + ": " + message + "\n";
                System.out.println("[Conexao] Texto: " + texto);

                //mensagem.setMensagem(message);
                // mensagem.jTmostramensagem.setText("");
                mensagem.jTmostramensagem.append(texto);
            }

        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao enviar ao atualizar!\n" + ex);
        }

    }

}
