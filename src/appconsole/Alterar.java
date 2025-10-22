package appconsole;

import java.util.List;

import modelo.Convidado;
import modelo.Evento;
import repositorio.Repositorio;

public class Alterar {

	public static void main(String[] args) {
		Repositorio.conectar();
		System.out.println("conectado ao banco");

		 List<Evento> eventos = Repositorio.getObjetos(Evento.class);
		 List<Convidado> convidados = Repositorio.getObjetos(Convidado.class);
			
		 Evento eventoEncontrado = null;
		 for (Evento e : eventos) {
		     if (e.getNome().equals("Rave")) {
		         eventoEncontrado = e;
		         break;
		     }
		 }

		 System.out.println(
		     eventoEncontrado != null
		         ? "Evento encontrado: " + eventoEncontrado.getNome()
		         : "Evento não encontrado"
		 );
		 
		 Convidado convidadoParaRemover = null;
		 for (Convidado c : convidados) {
			 if (c.getId() == 1) { //Convidado: Melquisedeque
				 convidadoParaRemover = c;
				 break;
			 }
			 
		 }
		 if (convidadoParaRemover == null) {
	            System.out.println("Convidado não encontrado no evento.");
	        }
		else {
			//removendo convidado do evento
			eventoEncontrado.removerConvidado(convidadoParaRemover);

			//atualizando o evento no banco
			Repositorio.gravarObjeto(eventoEncontrado);
			System.out.println("Convidado " + convidadoParaRemover.getNome() + " removido do evento.");
		}

		Repositorio.desconectar();
		System.out.println("banco desconectado");
	}

}
