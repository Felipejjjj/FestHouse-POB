package repositorio;

import java.util.List;

import com.db4o.query.Query;

import modelo.Cliente;
import modelo.Convidado;
import modelo.Evento;
import util.Util;

public class EventoRepositorio extends CRUDRepositorio<Evento> {

    @Override
    public Evento ler(Object chave) {
        String nome = (String) chave;
        
        Query q = Util.getManager().query();
        q.constrain(Evento.class);
        q.descend("nome").constrain(nome);
        
        List<Evento> resultados = q.execute();
        
        if (resultados.size() > 0) {
            return resultados.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void apagar(Evento evento) {
        if (evento != null) {
            if (evento.getListaConvidados() != null) {
                for (Convidado c : evento.getListaConvidados()) {
                    Util.getManager().delete(c);
                    Util.getManager().commit();
                }
                Cliente clienteEventoApagado = evento.getCliente();
                clienteEventoApagado.removerEvento(evento);
                Util.getManager().store(clienteEventoApagado);
                Util.getManager().commit();
            }
            if(evento.getListaConvidados() != null) {
                evento.getListaConvidados().clear();
            }
            
            Util.getManager().delete(evento);
            Util.getManager().commit();
        }
    }
}