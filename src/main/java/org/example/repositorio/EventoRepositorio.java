package org.example.repositorio;

import java.util.List;

import jakarta.persistence.TypedQuery;
import org.example.modelo.Evento;
import org.example.util.Util;

public class EventoRepositorio extends CRUDRepositorio<Evento> {
	@Override
	public Evento ler(Object chave) {
		String nome = (String) chave;
		
		return Util.getManager().find(Evento.class, nome);
	}
	
	@Override
	public List<Evento> listar() {
		TypedQuery<Evento> query = Util.getManager().createQuery(
				"SELECT e from Evento e",
				Evento.class
		);
		return query.getResultList();
	}
	
	//consultas
	
	public List<Evento> lerPorNome(String parteNome) {
	    TypedQuery<Evento> query = Util.getManager().createQuery(
	            "SELECT e FROM Evento e WHERE e.nome LIKE :nome",
	            Evento.class
	        );
	        query.setParameter("nome", "%" + parteNome + "%");
	        
	        return query.getResultList();
	}
	
	public List<Evento> lerPorData(String data) {
		TypedQuery<Evento> query = Util.getManager().createQuery(
	            "SELECT e FROM Evento e WHERE e.data = :data",
	            Evento.class
	        );
	        query.setParameter("data", data);
		
	        return query.getResultList();
	}
	
	public List<Evento> lerMaisDeNConvidados(int n) {
		TypedQuery<Evento> query = Util.getManager().createQuery(
	            "SELECT e FROM Evento e WHERE SIZE(e.listaConvidados) > :n",
	            Evento.class
	        );
	        query.setParameter("n", n);
	        
	        return query.getResultList();
	}
}
