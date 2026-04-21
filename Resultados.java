import java.util.List;

public class Resultados {
    private int id;
    private List<Cliente> clientes;
    private List<Posto> postos;

    public Resultados(int id, List<Cliente> clientes, List<Posto> postos) {
        this.id = id;
        this.clientes = clientes;
        this.postos = postos;
    }

    public void relatorioPostos() {
        System.out.println("Resultados dos postos da simulação " + this.id + ":");

        for (Posto posto : postos) {
            posto.relatorio();
        }

        System.out.println("===============");
    }

    public void relatorioClientes() {
        System.out.println("Resultados dos clientes da simulação " + this.id + ":");

        for (Cliente cliente : clientes) {
            cliente.relatorio();
        }

        System.out.println("===============");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(List<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Posto> getPostos() {
        return postos;
    }

    public void setPostos(List<Posto> postos) {
        this.postos = postos;
    }
}
