package appconsole;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Candidate;
import com.db4o.query.Evaluation;
import com.db4o.query.Query;

import modelo.Convidado;
import modelo.Evento;
import repositorio.Util;

public class Consultar {

    public static void main(String[] args) {
        ObjectContainer manager = Util.conectarBanco();

        System.out.println("\n---Listar eventos da data X");
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
        
        System.out.println("\n---Listar convidados do evento da data X");
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
       
        System.out.println("\n---listar eventos com tem mais de N convidados");
        // Criando a classe para uso do filtro
        class Filtro implements Evaluation {
            private int n;

            public Filtro(int n) {
                this.n = n;
            }

           
            public void evaluate(Candidate candidate) {
                Evento e = (Evento) candidate.getObject();
                if (e.getListaConvidados().size() > n)
                    candidate.include(true);
                else
                    candidate.include(false);
            }
        }
        
        //criando a query para consulta
        int N = 1;
        Query queryEventosFiltro = manager.query();
        queryEventosFiltro.constrain(Evento.class);
        queryEventosFiltro.constrain(new Filtro(N)); // aplica o filtro

        List<Evento> eventosComMaisDeNConvidados = queryEventosFiltro.execute();

        if (eventosComMaisDeNConvidados.isEmpty()) {
            System.out.println("Nenhum evento com mais de " + N + " convidados");
        } else {
            System.out.println("Evento(s) com mais de " + N + " convidados:");
            for (Evento e : eventosComMaisDeNConvidados) {
                System.out.println(e);
            }
        }


        // Desconectar do banco
        Util.desconectar();
    }
}
