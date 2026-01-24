package org.example.appconsole;

import org.example.requisito.Fachada;

public class Apagar {

    public Apagar() {

        try {
            //apagar convidado pelo ID
            Fachada.apagarConvidado(3);
            System.out.println("apagou convidado de ID 3");

            Fachada.apagarConvidado(4);
            System.out.println("apagou convidado de ID 4");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("fim do programa");
    }

    //=================================================
    public static void main(String[] args) {
        new Apagar();
    }
}
