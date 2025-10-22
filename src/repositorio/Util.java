package repositorio;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.EmbeddedObjectContainer;
import com.db4o.events.EventRegistry;
import com.db4o.events.EventRegistryFactory;
import com.db4o.query.Query;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

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
		ControleID.ativar(true, manager); 	// ativa geração de IDs automáticos para as classes com atributo "int id"
		return manager;
	}

	public static void desconectar() {
		if(manager!=null) {
			manager.close();
			manager=null;
		}
	}
	
	//métodos adicionais:
    public static void gravarObjeto(Object objeto) {
        manager.store(objeto);
        manager.commit();
    }

    public static void apagarObjeto(Object objeto) {
        manager.delete(objeto);
        manager.commit();
    }

    public static <T> List<T> getObjetos(Class<T> classe) {
        Query query = manager.query();
        query.constrain(classe);

        List<T> objetos = query.execute();
        return objetos;
    }

    public static void apagarEvento(Evento evento) {
        if (evento != null) {
            if (evento.getListaConvidados() != null) {
                for (Convidado c : evento.getListaConvidados()) {
                    apagarObjeto(c);
                }
                Cliente clienteEventoApagado = evento.getCliente();
                clienteEventoApagado.removerEvento(evento);
                gravarObjeto(clienteEventoApagado);
            }
            if(evento.getListaConvidados() != null) {
                evento.getListaConvidados().clear();
            }
            
            apagarObjeto(evento);
        }
    }

    public static void apagarCliente(Cliente cliente) {
		if (cliente != null) {
			if (cliente.getEventos() != null) {
				//é necessário apagar todos os eventos de uma cópia, pois se não buga o fluxo
				List<Evento> eventosParaApagar = new ArrayList<>(cliente.getEventos());
				for (Evento e : eventosParaApagar) {
					apagarEvento(e); 
				}
			}

			if(cliente.getEventos() != null) {
				cliente.getEventos().clear();
			}
			apagarObjeto(cliente);
		}
    }
}

//**********************************************
// classe interna 
// Controla a geração automatica de IDs para 
// as classes que possuem um atributo id
//**********************************************
class ControleID {
	private static ObjectContainer sequencia; // bd auxiliar de sequencias DE IDs
	private static TreeMap<String, RegistroID> registros = new TreeMap<String, RegistroID>(); //cache de registros de ids
	private static boolean salvar; // indica se precisa salvar os registros de id

	public static void ativar(boolean ativa, ObjectContainer manager) {
		if (!ativa)
			return; // controle de ids nao será feito
		if (manager == null)
			throw new RuntimeException("Ativar controle de id - manager desconhecido"); // desativado

		if (manager instanceof EmbeddedObjectContainer) {
			// banco de sequencia no local
			Db4oEmbedded.newConfiguration();
			sequencia = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "sequencia.db4o");
			//System.out.println("conectou sequencia local");
		} 
		// else {
		// 	// banco de sequencia no servidor remoto
		// 	String ipservidor = Util.getIPservidor();
		// 	sequencia = Db4oClientServer.openClient(Db4oClientServer.newClientConfiguration(), ipservidor, 35000,
		// 			"usuario0", "senha0");
		// 	//System.out.println("conectou no banco de sequencia remoto ip=" + ipservidor);
		// }
		lerRegistrosID(); // ler do banco os registros de id

		// CRIAR GERENTE DE TRIGGERS PARA O MANAGER
		EventRegistry eventRegistry = EventRegistryFactory.forObjectContainer(manager);

		// Resgistrar trigger "BEFORE PERSIST" causado pelo manager.store(objeto)
		eventRegistry.creating().addListener((event, args) -> {
			try {
				//System.out.println("trigger creating");
				Object objeto = args.object(); // objeto que esta sendo gravado
				Field field = objeto.getClass().getDeclaredField("id");
				if (field != null) { // tem campo id?
					String nomedaclasse = objeto.getClass().getName();
					RegistroID registro = obterRegistroID(nomedaclasse); // pega id da tabela
					registro.incrementarID(); // incrementa o id
					field.setAccessible(true); // habilita acesso ao campo id do objeto
					field.setInt(objeto, registro.getid()); // atualiza o id do objeto
					registros.put(nomedaclasse, registro); // atualiza tabela de id
					salvar = true;
				}
			} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			}
		});

		// Resgistrar trigger "AFTER COMMIT" causado pelo manager.commit()
		eventRegistry.created().addListener((event, args) -> {
			//System.out.println("trigger commit");
			salvarRegistrosID(); // salvar registros de id alterados 
		});

		// Resgistrar trigger "BEFORE CLOSE" causado pelo manager.close()
		eventRegistry.closing().addListener((event, args) -> {
			//System.out.println("trigger close");
			if (sequencia != null && !sequencia.ext().isClosed())
				sequencia.close(); // fecha o banco de sequencias
		});
	}

	private static void lerRegistrosID() {
		Query q = sequencia.query();
		q.constrain(RegistroID.class);
		List<RegistroID> resultados = q.execute();
		for (RegistroID reg : resultados) {
			//System.out.println("lendo do bd sequencia: " + reg);
			registros.put(reg.getNomedaclasse(), reg);
		}
		salvar = false;

	}

	private static void salvarRegistrosID() {
		if (salvar) {
			for (RegistroID reg : registros.values()) {
				if (reg.isModificado()) {
					//System.out.println("gravando no bd sequencia: " + reg);
					sequencia.store(reg);
					sequencia.commit();
					reg.setModificado(false);
				}
			}
			salvar = false;
		}
	}

	private static RegistroID obterRegistroID(String nomeclasse) {
		RegistroID reg = null;
		if (registros.containsKey(nomeclasse))
			reg = registros.get(nomeclasse);
		else
			reg = new RegistroID(nomeclasse); // aninhamento

		return reg;
	}

} // fim classe interna ControleID

// *************************************************************
// classe interna
// Encapsula o ultimo ID gerado para uma classe
// *************************************************************
class RegistroID {
	private String nomedaclasse;
	private int ultimoid;
	transient private boolean modificado = false; // nao sera persistido

	public RegistroID(String nomedaclasse) {
		this.nomedaclasse = nomedaclasse;
		this.ultimoid = 0;
	}

	public String getNomedaclasse() {
		return nomedaclasse;
	}

	public int getid() {
		return ultimoid;
	}

	public boolean isModificado() {
		return modificado;
	}

	public void setModificado(boolean modificado) {
		this.modificado = modificado;
	}

	public void incrementarID() {
		ultimoid++;
		setModificado(true);
	}

	@Override
	public String toString() {
		return "RegistroID [nomedaclasse=" + nomedaclasse + ", ultimoid=" + ultimoid + "]";
	}

} // fim classe RegistroID