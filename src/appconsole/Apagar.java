package appconsole;

import requisito.Fachada;

public class Apagar {

    public Apagar() {

        try {
            //apagar convidado pelo ID
            Fachada.apagarConvidado(3);
            System.out.println("apagou convidado de ID 3");

            Fachada.apagarConvidado(4);
            System.out.println("apagou convidado de ID 4");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("fim do programa");
    }

    //=================================================
    public static void main(String[] args) {
        new Apagar();
    }
}


/*
package appconsole;

import java.util.List;

import modelo.Evento;
import repositorio.Util;

public class Apagar {

	public static void main(String[] args) {
		Util.conectarBanco();
		System.out.println("conectado ao banco");

		List<Evento> eventos = Util.getObjetos(Evento.class);
		//remover evento "Visita ao museu"
		for (Evento e : eventos) {
			if (e.getNome().equals("Visita ao Museu")) {
				System.out.println("Apagando evento: " + e.getNome());
				System.out.println("Removendo " + e.getListaConvidados().size() + " convidados relacionados");
				Util.apagarEvento(e);
				System.out.println("Evento apagado com sucesso");
				
			}
		}

		Util.desconectar();
		System.out.println("banco desconectado");
	}
}
**/