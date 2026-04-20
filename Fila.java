import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Fila {
    private Deque<Cliente> clientes = new LinkedList<>();
    private Long tempoDeEspera;

    public synchronized void receberCliente(Cliente cliente) {
        clientes.addLast(cliente);
        cliente.setTempoDeEspera(System.currentTimeMillis()); // define tempo atual para ser usado depois
        
        System.out.println("(+) Entrou na fila o cliente " + cliente.getId());
        // TODO: marcar hora que cliente entrou na fila
    }

    public synchronized Cliente removerCliente() {
        if (clientes.isEmpty()) {
            try {
                System.out.println("Fila de espera vazia, aguardando clientes...");
                wait(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        if (!clientes.isEmpty()) {
            Cliente cliente = clientes.pollFirst();

            long fim = System.currentTimeMillis();
            long tempoDeEspera = fim - cliente.getTempoDeEspera().toMillis();

            cliente.setTempoDeEspera(tempoDeEspera);

            System.out.println("(-) Saiu da fila o cliente " + cliente.getId() + 
                " (tempo de espera: " + tempoDeEspera/1000 + " s)"
            );
            // TODO: marcar hora que cliente saiu da fila e calcular tempo de espera
            
            return cliente;
        } else {
            return null;
        }
    }

    public synchronized boolean temCliente() {
        return !clientes.isEmpty();
    }

    public synchronized int tamanho() {
        return clientes.size();
    }
}
