package appconsole;

import java.util.List;

import modelo.Cliente;
import modelo.Convidado;
import modelo.Evento;
import requisito.Fachada;


public class Listar {
	
	public Listar() {
		try {
			System.out.println("Listagem de Clientes:");
			for(Cliente cli : Fachada.listarClientes())
				System.out.println(cli);
			
			System.out.println("Listagem de Convidados:");
			for(Convidado c : Fachada.listarConvidados())
				System.out.println(c);
			
			System.out.println("Listagem de Eventos:");
			for(Evento e : Fachada.listarEventos())
				System.out.println(e);
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	

	
	public static void main(String[] args) {
		new Listar();
	}

}
