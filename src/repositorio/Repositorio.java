package repositorio;

import java.util.ArrayList;
import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

import modelo.Convidado;
import modelo.Evento;
import modelo.Cliente;

public class Repositorio {
    //atributos
    private static ObjectContainer manager;

    //métodos
    public static void conectar() {
        if (manager == null) {
            manager = Util.conectarBanco(); 
        }
    }

    public static void desconectar() {
        if (manager != null) {
            manager.close();
            manager = null;
        }
    }

    public static void gravarObjeto(Object objeto) {
        manager.store(objeto);
        manager.commit();
    }

    public static void apagarObjeto(Object objeto) {
        manager.delete(objeto);
        manager.commit();
    }

    public static <T> List<T> getObjetos(Class<T> classe) {
        Query query = manager.query();
        query.constrain(classe);

        List<T> objetos = query.execute();
        return objetos;
    }

    public static void apagarEvento(Evento evento) {
        if (evento != null) {
            if (evento.getListaConvidados() != null) {
                for (Convidado c : evento.getListaConvidados()) {
                    apagarObjeto(c);
                }
                Cliente clienteEventoApagado = evento.getCliente();
                clienteEventoApagado.removerEvento(evento);
                gravarObjeto(clienteEventoApagado);
            }
            if(evento.getListaConvidados() != null) {
                evento.getListaConvidados().clear();
            }
            
            apagarObjeto(evento);
        }
    }

    public static void apagarCliente(Cliente cliente) {
            if (cliente != null) {
                if (cliente.getEventos() != null) {

                    //é necessário apagar todos os eventos de uma cópia, pois se não buga o fluxo
                    List<Evento> eventosParaApagar = new ArrayList<>(cliente.getEventos());
                    for (Evento e : eventosParaApagar) {
                        apagarEvento(e); 
                    }
                }

                if(cliente.getEventos() != null) {
                    cliente.getEventos().clear();
                }
                apagarObjeto(cliente);
            }
        }
}
