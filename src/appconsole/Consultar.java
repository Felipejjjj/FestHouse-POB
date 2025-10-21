package appconsole;

import java.util.ArrayList;
import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import modelo.Convidado;
import modelo.Evento;
import repositorio.Util;

public class Consultar {

    public static void main(String[] args) {
        ObjectContainer manager = Util.conectarBanco();

        // Qual o evento da data X
        String DataX = "10/10/2025";
        Query queryEvento = manager.query();
        queryEvento.constrain(Evento.class);
        queryEvento.descend("data").constrain(DataX);
        List<Evento> eventosPorData = queryEvento.execute();

        if (eventosPorData.isEmpty()) {
            System.out.println("Evento não existe para essa data");
        } else {
            System.out.println("Evento(s) na data "+ DataX + ":");
            for (Evento e : eventosPorData) {
                System.out.println(e);
            }
        }

        // Quais os convidados do evento da data X
        Query queryConvidados = manager.query();
        queryConvidados.constrain(Convidado.class);
        queryConvidados.descend("evento").descend("data").constrain(DataX);
        List<Convidado> convidadosDoEvento = queryConvidados.execute();

        if (convidadosDoEvento.isEmpty()) {
            System.out.println("\nNenhum convidado encontrado para o evento na data " + DataX);
        } else {
            System.out.println("\nConvidado(s) do evento na data " + DataX + ":");
            for (Convidado c : convidadosDoEvento) {
                System.out.println(c);
            }
        }
       
        
        // Quais os eventos que têm mais de N convidados
        int N = 1;
        
        // Recupera todos os eventos
        List<Evento> todosEventos = manager.query(Evento.class);
        List<Evento> eventosComMaisDeNConvidados = new ArrayList<>();

        // Filtra em memória pelo tamanho da lista
        for (Evento e : todosEventos) {
            if (e.getListaConvidados().size() > N) {
                eventosComMaisDeNConvidados.add(e);
            }
        }

        if (eventosComMaisDeNConvidados.isEmpty()) {
            System.out.println("\nNenhum evento com mais de " + N + " convidados");
        } else {
            System.out.println("\nEvento(s) com mais de " + N + " convidados:");
            for (Evento e : eventosComMaisDeNConvidados) {
                System.out.println(e);
            }
        }

        // Desconectar do banco
        Util.desconectar();
    }
}
