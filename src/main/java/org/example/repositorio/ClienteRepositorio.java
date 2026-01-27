package org.example.repositorio;

import java.util.List;

import jakarta.persistence.TypedQuery;
import org.example.modelo.Cliente;
import org.example.util.Util;

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