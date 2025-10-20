package repositorio;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;

import modelo.Cliente;
import modelo.Convidado;
import modelo.Localizacao;
import modelo.Evento;

public class Util {
	private static ObjectContainer manager;

	public static ObjectContainer conectarBanco(){
		if (manager != null)
			return manager;		//ja tem uma conexao

		//---------------------------------------------------------------
		//configurar, criar e abrir banco local (pasta do projeto)
		//---------------------------------------------------------------
		EmbeddedConfiguration config =  Db4oEmbedded.newConfiguration(); 
		config.common().messageLevel(0);  // mensagens na tela 0(desliga),1,2,3...
		
		// habilitar cascata na alteração, remoção e leitura
		config.common().objectClass(Cliente.class).cascadeOnUpdate(true);
		config.common().objectClass(Cliente.class).cascadeOnDelete(true);
		config.common().objectClass(Cliente.class).cascadeOnActivate(true);
		
		config.common().objectClass(Evento.class).cascadeOnDelete(false);
		config.common().objectClass(Evento.class).cascadeOnUpdate(true);
		config.common().objectClass(Evento.class).cascadeOnActivate(true);
		
		config.common().objectClass(Convidado.class).cascadeOnUpdate(true);
		config.common().objectClass(Convidado.class).cascadeOnDelete(false);
		config.common().objectClass(Convidado.class).cascadeOnActivate(true);
		
		config.common().objectClass(Localizacao.class).cascadeOnUpdate(true);
		config.common().objectClass(Localizacao.class).cascadeOnDelete(false);
		config.common().objectClass(Localizacao.class).cascadeOnActivate(true);
		
		//conexao local
		manager = Db4oEmbedded.openFile(config, "banco.db4o");
		return manager;
	}

	public static void desconectar() {
		if(manager!=null) {
			manager.close();
			manager=null;
		}
	}
}

