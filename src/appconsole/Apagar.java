package appconsole;

import java.util.List;

import modelo.Evento;
import repositorio.Repositorio;

public class Apagar {

	public static void main(String[] args) {
		Repositorio.conectar();
		System.out.println("conectado ao banco");

		List<Evento> eventos = Repositorio.getObjetos(Evento.class);
		//remover evento "Visita ao museu"
		for (Evento e : eventos) {
			if (e.getNome().equals("Visita ao Museu")) {
				System.out.println("Apagando evento: " + e.getNome());
				System.out.println("Removendo " + e.getListaConvidados().size() + " convidados relacionados");
				Repositorio.apagarEvento(e);
				System.out.println("Evento apagado com sucesso");
				
			}
		}

		Repositorio.desconectar();
		System.out.println("banco desconectado");
	}
}
