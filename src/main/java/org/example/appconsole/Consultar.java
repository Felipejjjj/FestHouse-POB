package org.example.appconsole;

import org.example.modelo.Cliente;
import org.example.modelo.Convidado;
import org.example.modelo.Evento;
import org.example.requisito.Fachada;

public class Consultar {

    public Consultar() {

        try {

            // ------------------------- CLIENTES -----------------------------
            System.out.println("\nClientes com nome contendo 'ro': ");
            for (Cliente c : Fachada.consultarClientesPorNome("ro"))
                System.out.println(c);

            // -------------------------- EVENTOS -----------------------------
            System.out.println("\nEventos com nome contendo 'ra': ");
            for (Evento e : Fachada.consultarEventosPorNome("ra"))
                System.out.println(e);

            System.out.println("\nEventos na data 10/10/2025: ");
            for (Evento e : Fachada.consultarEventosPorData("10/10/2025"))
                System.out.println(e);

            System.out.println("\nEventos com mais de 2 convidados: ");
            for (Evento e : Fachada.consultarEventosComMaisDeNConvidados(2))
                System.out.println(e);

            // ------------------------ CONVIDADOS ----------------------------
            System.out.println("\nConvidados com nome contendo 'a': ");
            for (Convidado c : Fachada.consultarConvidadosPorNome("a"))
                System.out.println(c);

            System.out.println("\nConvidados do evento 'Rave': ");
            for (Convidado c : Fachada.consultarConvidadosDeEvento("Rave"))
                System.out.println(c);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nfim do programa");
    }

    public static void main(String[] args) {
        new Consultar();
    }
}
