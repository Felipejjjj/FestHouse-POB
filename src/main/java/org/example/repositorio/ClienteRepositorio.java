package org.example.repositorio;

import java.util.ArrayList;

import java.util.List;
//import com.db4o.query.Query;

import jakarta.persistence.TypedQuery;
import org.example.modelo.Cliente;
import org.example.modelo.Evento;
import org.example.util.Util;
//import org.example.repositorio.EventoRepositorio;

//public class ClienteRepositorio extends CRUDRepositorio<Cliente> {
//
//    @Override
//    public Cliente ler(Object chave) {
//        String cpf = (String) chave;
//
//        Query q = Util.getManager().query();
//        q.constrain(Cliente.class);
//        q.descend("cpf").constrain(cpf);
//
//        List<Cliente> resultados = q.execute();
//
//        if (resultados.size() > 0) {
//            return resultados.get(0);
//        } else {
//            return null;
//        }
//    }
//
//    /**********************************************************
//     *
//     * CONSULTAS DE CLIENTE
//     *
//     **********************************************************/
//
//    public List<Cliente> lerPorNome(String parteNome) {
//        Query q = Util.getManager().query();
//        q.constrain(Cliente.class);
//        q.descend("nome").constrain(parteNome).like();
//        return new ArrayList<>(q.execute());
//    }
//
//
//    @Override
//    public void apagar(Cliente cliente) {
//		if (cliente != null) {
//			if (cliente.getEventos() != null) {
//				//é necessário apagar todos os eventos de uma cópia, pois se não buga o fluxo
//				List<Evento> eventosParaApagar = new ArrayList<>(cliente.getEventos());
//				for (Evento e : eventosParaApagar) {
//                    EventoRepositorio eventoRepositorio = new EventoRepositorio();
//					eventoRepositorio.apagar(e);
//				}
//			}
//
//			if(cliente.getEventos() != null) {
//				cliente.getEventos().clear();
//			}
//            Util.getManager().delete(cliente);
//            Util.getManager().commit();
//		}
//    }
//}

public class ClienteRepositorio extends CRUDRepositorio<Cliente> {
    @Override
    public Cliente ler(Object chave) {
        // No JPA, o método find já busca pelo @id (achei importante anotar isso)
        String cpf = (String) chave;

        return Util.getManager().find(Cliente.class, cpf);
    }

    @Override
    public List<Cliente> listar() {
        TypedQuery<Cliente> query = Util.getManager().createQuery(
                "SELECT c FROM Cliente c",
                Cliente.class);

        return query.getResultList();
    }

    // Consultas específicas

    public List<Cliente> lerPorNome(String parteNome) {
        TypedQuery<Cliente> query = Util.getManager().createQuery(
                "SELECT c FROM Cliente c WHERE c.nome LIKE :n",
                Cliente.class);
        query.setParameter("n", "%" + parteNome + "%");

        return query.getResultList();
    }

    // métodos

    @Override
    public void apagar(Cliente cliente) {
        // No JPA, a entidade precisa estar gerenciada (managed) para poder ser removida.
        // Se não estiver no contexto atual, é necessário fazer um merge antes.
        if (!Util.getManager().contains(cliente)) {
            cliente = Util.getManager().merge(cliente);
        }

        // detalhe: a classe tem "orphanRemoval = true", então o JPA remove órfãos automaticamente sem ter que percorrer a lista igual antes
        Util.getManager().remove(cliente);
    }
}