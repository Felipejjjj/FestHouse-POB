package org.example.repositorio;

import java.util.List;

import jakarta.persistence.TypedQuery;
import org.example.modelo.Convidado;
import org.example.util.Util;

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
                "SELECT c FROM Convidado c WHERE c.nome like :n",
                Convidado.class
        );
        query.setParameter("n", "%" + parteNome + "%");

        return query.getResultList();
    }

    public List<Convidado> lerPorEvento(String nomeEvento) {
        TypedQuery<Convidado> query = Util.getManager().createQuery(
                "SELECT c FROM Convidado c WHERE c.evento.nome = :n",
                Convidado.class
        );
        query.setParameter("n", nomeEvento);

        return query.getResultList();
    }
}