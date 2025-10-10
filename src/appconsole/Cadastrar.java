package appconsole;

import modelo.Cliente;
import modelo.Convidado;
import modelo.Evento;
import modelo.Localizacao;

public class Cadastrar {

	public static void main(String[] args) {
		//Criação de Objetos:
		
		//Localização
		Localizacao localizacaoCliente1 = new Localizacao(21.4555, 34.5999);
		Localizacao localizacaoCliente2 = new Localizacao(48.861589103892655, 2.337612948841596);
		
		//Clientes
		Cliente cliente1 = new Cliente("1234", "João", localizacaoCliente1);
		Cliente cliente2 = new Cliente("5678", "Luana", localizacaoCliente1);
		
		//Eventos
		Evento evento1 = new Evento("10/10/2025", "Rave", cliente1);
		Evento evento2= new Evento("11/10/2025", "Visita ao Museu", cliente2);

		//Convidados
		Convidado convidado1 = new Convidado(1, "João", "1234", evento1);
		Convidado convidado2 = new Convidado(2, "Luana", "3456", evento1);
		Convidado convidado3 = new Convidado(2, "Isaac", "1122", evento2);
		Convidado convidado4 = new Convidado(2, "Espingardina", "2211", evento2);
		
		
		
		//testes
		System.out.println("=-=-Localizações=-=-");
		System.out.println(localizacaoCliente1);
		System.out.println(localizacaoCliente2);
		System.out.println("\n-=-=Clientes-=-=");
		System.out.println(cliente1);
		System.out.println(cliente2);
		System.out.println("\n-=-=Eventos-=-=");
		System.out.println(evento1);
		System.out.println(evento2);
		System.out.println("\n-=-=Convidados-=-=");
		System.out.println(convidado1);
		System.out.println(convidado2);
		System.out.println(convidado3);
		System.out.println(convidado4);
	}
}
