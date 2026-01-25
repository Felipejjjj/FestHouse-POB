package org.example.appconsole;

import org.example.requisito.Fachada;

public class Alterar {

    public static void main(String[] args) {

        System.out.println("==== ALTERAÇÕES ====");

        // Remover convidado do evento
        try {
            Fachada.removerConvidadoDoEvento("Rave", 1);
            System.out.println("Convidado ID 1 removido do evento 'Rave'.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Alterar data do evento
        try {
            Fachada.alterarDataEvento("Rave", "20/10/2025");
            System.out.println("Data do evento 'Rave' alterada para 20/10/2025.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Alterar nome do convidado
        try {
            Fachada.alterarNomeConvidado(2, "Eduardo");
            System.out.println("Nome do convidado ID 2 alterado.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Alterar nome do evento
        /* ALTERAÇÃO REMOVIDA
        try {
            Fachada.alterarNomeEvento("Rave", "Rave Internacional");
            System.out.println("Evento 'Rave' agora se chama 'Rave Internacional'.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        */

        System.out.println("\nfim do programa");
    }
}
