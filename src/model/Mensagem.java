/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author mateus
 */
public class Mensagem {
    static String mensagem;

    public static String getMensagem() {
        return mensagem;
    }

    public static void setMensagem(String mensagem) {
        Mensagem.mensagem = mensagem;
    }
}
   