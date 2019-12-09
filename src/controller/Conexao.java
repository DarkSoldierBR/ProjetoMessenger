package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mateus
 */
public class Conexao {

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
        } catch (SQLException ex) {
            System.out.println("Ocorreu um erro ao conectar!\n" + ex);
        }
    }
}
