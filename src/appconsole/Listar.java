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
	/**
        Util.conectarBanco();
        System.out.println("conectado ao banco");
        
        List<Cliente> clientes = Util.getObjetos(Cliente.class);
        List<Localizacao> localizacoes = Util.getObjetos(Localizacao.class);
        List<Evento> eventos = Util.getObjetos(Evento.class);
        List<Convidado> convidados = Util.getObjetos(Convidado.class);

        System.out.println("Clientes:");
        System.out.println("-------");

        for( Cliente cliente : clientes ) {
            System.out.println(cliente);
        }

        System.out.println("\nLocalizações:");
        System.out.println("-------");

        for( Localizacao localizacao : localizacoes ) {
            System.out.println(localizacao);
        }

        System.out.println("\nEventos:");
        System.out.println("-------");
        for( Evento evento : eventos ) {
            System.out.println(evento);
        }

        System.out.println("\nConvidados:");
        System.out.println("-------");
        for( Convidado convidado : convidados ) {
            System.out.println(convidado);
        }

        System.out.println("\n");

        Util.desconectar();
        System.out.println("banco desconectado");
    }
}
**/