package repositorio;

import java.util.ArrayList;

import java.util.List;
import com.db4o.query.Query;
import modelo.Cliente;
import modelo.Evento;
import util.Util;
import repositorio.EventoRepositorio;

public class ClienteRepositorio extends CRUDRepositorio<Cliente> {

    @Override
    public Cliente ler(Object chave) {
        String cpf = (String) chave;
        
        Query q = Util.getManager().query();
        q.constrain(Cliente.class);
        q.descend("cpf").constrain(cpf);
        
        List<Cliente> resultados = q.execute();
        
        if (resultados.size() > 0) {
            return resultados.get(0);
        } else {
            return null;
        }
    }
    

    @Override
    public void apagar(Cliente cliente) {
		if (cliente != null) {
			if (cliente.getEventos() != null) {
				//é necessário apagar todos os eventos de uma cópia, pois se não buga o fluxo
				List<Evento> eventosParaApagar = new ArrayList<>(cliente.getEventos());
				for (Evento e : eventosParaApagar) {
                    EventoRepositorio eventoRepositorio = new EventoRepositorio();
					eventoRepositorio.apagar(e);
				}
			}

			if(cliente.getEventos() != null) {
				cliente.getEventos().clear();
			}
            Util.getManager().delete(cliente);
            Util.getManager().commit();
		}
    }
}