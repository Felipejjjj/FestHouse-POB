package repositorio;

import java.util.List;

import com.db4o.query.Query;
import modelo.Convidado;
import util.Util;

public class ConvidadoRepositorio extends CRUDRepositorio<Convidado> {

    @Override
    public Convidado ler(Object chave) {
        // Assume-se que a chave passada seja um Integer ou int
        int id = (int) chave;
        
        Query q = Util.getManager().query();
        q.constrain(Convidado.class);
        q.descend("id").constrain(id);
        
        List<Convidado> resultados = q.execute();
        
        if (resultados.size() > 0) {
            return resultados.get(0);
        } else {
            return null;
        }
    }
}