package appconsole;

import modelo.Cliente;
import modelo.Convidado;
import modelo.Evento;
import modelo.Localizacao;

public class Cadastrar {

	public static void main(String[] args) {
		//Criação de Objetos:
		
		//Localização
		Localizacao localizacaoCliente = new Localizacao(21.4555, 34.5999);
		
		//Clientes
		Cliente cliente1 = new Cliente("1234", "João", localizacaoCliente);
		
		//Eventos
		Evento evento1 = new Evento("10/10/2025", "Rave", cliente1);

		//Convidados
		Convidado convidado1 = new Convidado(1, "João", "1234", evento1);
		Convidado convidado2 = new Convidado(2, "Luana", "3456", evento1);
		
		System.out.println(localizacaoCliente);
		System.out.println(cliente1);
		System.out.println(evento1);
		System.out.println(convidado1);
		System.out.println(convidado2);
	}

}
