package org.example.appconsole;
/*
import modelo.Cliente;
import modelo.Convidado;
import modelo.Evento;
import modelo.Localizacao;
import repositorio.Util;
*/

import requisito.Fachada;

public class Cadastrar {

        public Cadastrar() {

        try {
            // ---- CLIENTES ----
        	System.out.println("cadastrando clientes...");
        	
            Fachada.criarCliente("1234", "Victor", 21.4555, 34.5999);
            Fachada.criarCliente("5678", "Luana", 48.861589103892655, 2.337612948841596);
            Fachada.criarCliente("9101", "Roberto", 35.65861534152576, 139.7454328963661);

            // ---- EVENTOS ----
            System.out.println("cadastrando eventos...");
            
            Fachada.criarEvento("10/10/2025", "Rave", "1234");
            Fachada.criarEvento("11/10/2025", "Visita ao Museu", "5678");
            Fachada.criarEvento("25/11/2025", "Visita à torre de Tokyo", "9101");

            // ---- CONVIDADOS ----
            System.out.println("cadastrando convidados...");
            
            Fachada.criarConvidado("Melquisedeque", "Rave");
            Fachada.criarConvidado("Milla", "Rave");
            Fachada.criarConvidado("Felipe", "Visita ao Museu");
            Fachada.criarConvidado("Espingardina", "Visita ao Museu");
            Fachada.criarConvidado("Murilo", "Visita à torre de Tokyo");
            Fachada.criarConvidado("Joel", "Visita à torre de Tokyo");
            Fachada.criarConvidado("Isaac", "Visita à torre de Tokyo");
            Fachada.criarConvidado("Arthur", "Visita à torre de Tokyo");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
        System.out.println("==== CADASTROS REALIZADOS COM SUCESSO ====");

    }
       
        public static void main(String[] args) {
        	new Cadastrar();
        }
}
