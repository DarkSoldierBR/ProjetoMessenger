package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mateus
 */
public class Conexao extends view.Mensagem{

    String host = "localhost";    //caminho do servidor do BD
    String database = "db_messenger";        //nome do seu banco de dados
    String url = "jdbc:mysql://" + host + "/" + database;
    String usuario = "root";        //nome de um usuário de seu BD      
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

        String sql = "INSERT INTO tbl_usuario (apelido,senha) VALUES('" + apelido + "','" + String.valueOf(senha) + "');";

        try {
            PreparedStatement pst;
            pst = conn.prepareCall(sql, 0, 0, 0);

            pst.setString(1, apelido);
            pst.setString(2, String.valueOf(senha));
            pst.execute();
            conn.close();

        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao cadastrar!\n" + ex);
        }

    }

    public void logar(String apelido, char[] senha) {
        Statement stm;
        model.UsuarioAutenticado usuarioautenticado = new model.UsuarioAutenticado();
        String sql = "select * from tbl_usuario where apelido like '" + apelido + "'";

        try {

            stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);

            if (rs.next()) {
                System.out.println("[Conexao]Usuario encontrado");
                if (rs.getString("senha").matches(String.valueOf(senha))) {
                    System.out.println("[Conexao]Senha confere");

                    usuarioautenticado.setAutenticado(true);
                    usuarioautenticado.setApelido(rs.getString("apelido"));
                    usuarioautenticado.setId(rs.getInt("id"));

                    System.out.println("[Conexao]O id do usuario é: " + usuarioautenticado.getId());
                    System.out.println("[Conexao]O apelido do usuario é: " + usuarioautenticado.getApelido());
                    System.out.println("[Conexao]Esta autenticado? " + usuarioautenticado.isAutenticado());
                } else {
                    System.out.println("[Conexao]Senha não confere");
                }

            } else {
                System.out.println("[Conexao]Usuario não encontrado");
            }
            conn.close();
            rs.close();
        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao logar!\n" + ex);
        }
    }

    public void enviaMensagem(String mensagem) {
        conectar();
        model.UsuarioAutenticado usuarioautenticado = new model.UsuarioAutenticado();

        String sql = "insert into tbl_mensagem (id_user,mensagem) values (?,?)";
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

    public void atualizar(view.Mensagem mensagem) {
        conectar();
       // model.Mensagem mensagem = new model.Mensagem();
        model.UsuarioAutenticado usuarioautenticado = new model.UsuarioAutenticado();
        view.Mensagem m = new view.Mensagem();
        mensagem.jTmostramensagem.setText("");
        
        String sql = "select * from tbl_mensagem";
        String texto = "";
        String usuario,horario,message;
        
        
            try {
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery(sql);
                while(rs.next()){
                    usuario = usuarioautenticado.getApelido();
                    horario = rs.getString(3);
                    message = rs.getString(4);
                    
                    System.out.println("[Conexao] Usuario: " + usuario);
                    System.out.println("[Conexao] Horario: " + horario);
                    System.out.println("[Conexao] Mensagem: " + message);
                    
                    texto = "["+horario+"] "+usuario+": "+message+"\n";
                    System.out.println("[Conexao] Texto: "+texto);
                    
                    //mensagem.setMensagem(message);
                    // mensagem.jTmostramensagem.setText("");
                    mensagem.jTmostramensagem.append(texto);
                }
                
            } catch (SQLException ex) {
                System.out.println("Ocorreu um erro ao enviar ao atualizar!\n" + ex);
            }
        
    }

}