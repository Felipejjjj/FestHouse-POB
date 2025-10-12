package appconsole;

import modelo.Cliente;
import modelo.Convidado;
import modelo.Evento;
import modelo.Localizacao;
import repositorio.Repositorio;

public class Cadastrar {

	public static void main(String[] args) {
		//Criação de Objetos:
		
		//Localização
		Localizacao localizacaoCliente1 = new Localizacao(21.4555, 34.5999);
		Localizacao localizacaoCliente2 = new Localizacao(48.861589103892655, 2.337612948841596);
		Localizacao localizacaoCliente3 = new Localizacao(35.65861534152576, 139.7454328963661);
		
		//Clientes
		Cliente cliente1 = new Cliente("1234", "Victor", localizacaoCliente1);
		Cliente cliente2 = new Cliente("5678", "Luana", localizacaoCliente2);
		Cliente cliente3 = new Cliente("9101", "Roberto", localizacaoCliente3);
		
		//Eventos
		Evento evento1 = new Evento("10/10/2025", "Rave", cliente1);
		Evento evento2= new Evento("11/10/2025", "Visita ao Museu", cliente2);
		Evento evento3 = new Evento("25/11/2025", "Visita à torre de Tokyo", cliente3);

		//Convidados
		Convidado convidado1 = new Convidado(1, "Melquisedeque", "1234", evento1);
		Convidado convidado2 = new Convidado(2, "Milla", "3456", evento1);
		Convidado convidado3 = new Convidado(3, "Felipe", "1122", evento2);
		Convidado convidado4 = new Convidado(4, "Espingardina", "2211", evento2);
		Convidado convidado5 = new Convidado(5, "Murilo", "4321", evento3);
		Convidado convidado6 = new Convidado(6, "Joel", "6543", evento3);
		
		//gravando objetos no banco
		Repositorio.gravarObjeto(evento1);
		Repositorio.gravarObjeto(evento2);
		Repositorio.gravarObjeto(evento3);
		
		System.out.println("objetos gravados");
	}
}
