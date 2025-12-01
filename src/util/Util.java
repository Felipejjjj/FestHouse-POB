package util;

import java.util.Properties;
import javax.swing.JOptionPane;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ClientConfiguration;

// Importando o novo modelo
import modelo.Cliente;
import modelo.Convidado;
import modelo.Localizacao;
import modelo.Evento;

public class Util {
    private static ObjectContainer manager;
    private static String ipservidor;

    public static void conectar() {
        try {
            // Obter o IP do servidor ou localhost do arquivo de propriedades
            Properties props = new Properties();
            props.load(Util.class.getResourceAsStream("/util/ip.properties")); // carrega o arquivo de propriedades
            ipservidor = props.getProperty("ipatual");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ip incorreto=" + ipservidor + "\n" + e.getMessage());
            System.exit(0);
        }

        // Abrir conexão com o banco
        if (ipservidor.equals("localhost"))
            conectarBancoLocal(); // Banco local (pasta do projeto)
        else
            conectarBancoRemoto(); // Banco remoto (precisa de um servidor ativo)

        // Ativar controle de IDs automáticos
        ControleID.ativar(manager); // Ativa a geração de IDs automáticos para as classes com atributo "int id"
    }

    private static void conectarBancoLocal() {
        if (manager != null)
            return; // Já tem uma conexão

        // Configurar, criar e abrir banco local (pasta do projeto)
        EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
        config.common().messageLevel(0); // Mensagens na tela 0(desliga),1,2,3...

        // Habilitar cascata na alteração, remoção e leitura para o novo modelo
        config.common().objectClass(Cliente.class).cascadeOnDelete(false);
        config.common().objectClass(Cliente.class).cascadeOnUpdate(true);
        config.common().objectClass(Cliente.class).cascadeOnActivate(true);
        
        config.common().objectClass(Evento.class).cascadeOnDelete(false);
        config.common().objectClass(Evento.class).cascadeOnUpdate(true);
        config.common().objectClass(Evento.class).cascadeOnActivate(true);

        config.common().objectClass(Convidado.class).cascadeOnDelete(false);
        config.common().objectClass(Convidado.class).cascadeOnUpdate(true);
        config.common().objectClass(Convidado.class).cascadeOnActivate(true);
        
        config.common().objectClass(Localizacao.class).cascadeOnDelete(false);
        config.common().objectClass(Localizacao.class).cascadeOnUpdate(true);
        config.common().objectClass(Localizacao.class).cascadeOnActivate(true);

        // Conexão local
        try {
            manager = Db4oEmbedded.openFile(config, "banco.db4o");
            // System.out.println("Conectado ao banco " + ipservidor);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar ao banco local \n" + e.getMessage());
            System.exit(0);
        }
    }

    private static void conectarBancoRemoto() {
        if (manager != null)
            return; // Já tem uma conexão

        // Configurar e conectar banco remoto
        ClientConfiguration config = Db4oClientServer.newClientConfiguration();
        config.common().messageLevel(0); // 0,1,2,3...

        // Habilitar cascata na alteração, remoção e leitura para o novo modelo
        config.common().objectClass(Cliente.class).cascadeOnDelete(false);
        config.common().objectClass(Cliente.class).cascadeOnUpdate(true);
        config.common().objectClass(Cliente.class).cascadeOnActivate(true);

        config.common().objectClass(Evento.class).cascadeOnDelete(false);
        config.common().objectClass(Evento.class).cascadeOnUpdate(true);
        config.common().objectClass(Evento.class).cascadeOnActivate(true);

        config.common().objectClass(Convidado.class).cascadeOnDelete(false);
        config.common().objectClass(Convidado.class).cascadeOnUpdate(true);
        config.common().objectClass(Convidado.class).cascadeOnActivate(true);

        config.common().objectClass(Localizacao.class).cascadeOnDelete(false);
        config.common().objectClass(Localizacao.class).cascadeOnUpdate(true);
        config.common().objectClass(Localizacao.class).cascadeOnActivate(true);

        // Conexão client-server
        try {
            manager = Db4oClientServer.openClient(config, ipservidor, 34000, "usuario1", "senha1");
            // System.out.println("Conectado ao banco " + manager);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Erro ao conectar ao banco remoto ip=" + ipservidor + "\n" + e.getMessage());
            System.exit(0);
        }
    }

    public static void desconectar() {
        if (manager != null) {
            manager.close();
            manager = null;
        }
    }

    public static ObjectContainer getManager() {
        return manager;
    }

    public static String getIPservidor() {
        return ipservidor;
    }
}
