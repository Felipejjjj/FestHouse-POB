package appconsole;
/**********************************
 * IFPB - SI
 * Persistencia de Objetos
 * Adaptado por ChatGPT
 **********************************/

import requisito.Fachada;

public class Alterar {

    public static void main(String[] args) {

        System.out.println("==== ALTERAÇÕES ====");

        // Remover convidado do evento
        try {
            Fachada.removerConvidadoDoEvento("Rave", 1);
            System.out.println("Convidado ID 1 removido do evento 'Rave'.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Alterar data do evento
        try {
            Fachada.alterarDataEvento("Rave", "20/10/2025");
            System.out.println("Data do evento 'Rave' alterada para 20/10/2025.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Alterar nome do convidado
        try {
            Fachada.alterarNomeConvidado(2, "NovoNomeConvidado");
            System.out.println("Nome do convidado ID 2 alterado.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // Alterar nome do evento
        try {
            Fachada.alterarNomeEvento("Rave", "Rave Internacional");
            System.out.println("Evento 'Rave' agora se chama 'Rave Internacional'.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("\nfim do programa");
    }
}



/**

package appconsole;

import java.util.List;

import modelo.Convidado;
import modelo.Evento;
import repositorio.Util;

public class Alterar {

	public static void main(String[] args) {
		Util.conectarBanco();
		System.out.println("conectado ao banco");

		 List<Evento> eventos = Util.getObjetos(Evento.class);
		 List<Convidado> convidados = Util.getObjetos(Convidado.class);
			
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
			Util.gravarObjeto(eventoEncontrado);
			System.out.println("Convidado " + convidadoParaRemover.getNome() + " removido do evento.");
		}

		Util.desconectar();
		System.out.println("banco desconectado");
	}

}
**/