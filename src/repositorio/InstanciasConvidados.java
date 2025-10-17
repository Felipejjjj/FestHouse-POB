package repositorio;

public class InstanciasConvidados {
    private int instancias;

    public InstanciasConvidados(int instancias) {
        this.instancias = instancias;
    }

    public int getInstancias() {
        return instancias;
    }

    public void acrescentarInstancia() {
        this.instancias += 1;
    }
}
