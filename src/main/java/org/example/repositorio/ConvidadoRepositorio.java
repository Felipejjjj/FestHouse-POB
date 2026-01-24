package org.example.repositorio;

import java.util.ArrayList;
import java.util.List;

//import com.db4o.query.Query;

import jakarta.persistence.TypedQuery;
import org.example.modelo.Convidado;
import org.example.util.Util;

//public class ConvidadoRepositorio extends CRUDRepositorio<Convidado> {
//
//    @Override
//    public Convidado ler(Object chave) {
//        // Assume-se que a chave passada seja um Integer ou int
//        int id = (int) chave;
//
//        Query q = Util.getManager().query();
//        q.constrain(Convidado.class);
//        q.descend("id").constrain(id);
//
//        List<Convidado> resultados = q.execute();
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
//     * CONSULTAS DE CONVIDADO
//     *
//     **********************************************************/
//
//    public List<Convidado> lerPorNome(String parteNome) {
//        Query q = Util.getManager().query();
//        q.constrain(Convidado.class);
//        q.descend("nome").constrain(parteNome).like();
//        return new ArrayList<>(q.execute());
//    }
//
//
//    public List<Convidado> lerPorEvento(String nomeEvento) {
//        Query q = Util.getManager().query();
//        q.constrain(Convidado.class);
//        q.descend("evento").descend("nome").constrain(nomeEvento);
//        return new ArrayList<>(q.execute());
//    }
//
//}

public class ConvidadoRepositorio extends CRUDRepositorio<Convidado> {
    @Override
    public Convidado ler(Object chave) {
        int id = (int) chave;

        return Util.getManager().find(Convidado.class, id);
    }

    @Override
    public List<Convidado> listar() {
        TypedQuery<Convidado> query = Util.getManager().createQuery(
                "SELECT c from Convidado c",
                Convidado.class
        );
        return query.getResultList();
    }

    // consultas

    public List<Convidado> lerPorNome(String parteNome) {
        TypedQuery<Convidado> query = Util.getManager().createQuery(
                "SELECT c FROM convidado c WHERE c.nome like :n",
                Convidado.class
        );
        query.setParameter("n", "%" + parteNome + "%");

        return query.getResultList();
    }

    public List<Convidado> lerPorEvento(String nomeEvento) {
        TypedQuery<Convidado> query = Util.getManager().createQuery(
                "SELECT c FROM convidado c WHERE c.evento.nome = :n",
                Convidado.class
        );
        query.setParameter("n", nomeEvento);

        return query.getResultList();
    }
}