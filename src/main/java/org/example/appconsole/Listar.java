package org.example.appconsole;

import java.util.List;

import org.example.modelo.Cliente;
import org.example.modelo.Convidado;
import org.example.modelo.Evento;
import org.example.requisito.Fachada;


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
