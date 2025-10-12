package repositorio;

import java.util.List;

import com.db4o.ObjectContainer;
import com.db4o.query.Query;

public class Repositorio {
    //atributos
    private static ObjectContainer manager = Util.conectarBanco();

    //conecta ao banco toda vez que a classe é chamada.
    static {
        manager = Util.conectarBanco();
    }

    //métodos
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
}
