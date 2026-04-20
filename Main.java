import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import exercicio06.Secador;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // definição dos tamanhos

        Fila fila = new Fila();
        int numPostos = 3;
        int numClientes = 8;

        List<Posto> listaPostos = new ArrayList<>();
        List<Cliente> listaClientes = new ArrayList<>();
        Thread[] listaThreads = new Thread[numPostos];

        // gera clientes
        for (int i = 0; i < numClientes; i++) {
            Cliente cliente = new Cliente(i + 1);
            listaClientes.add(cliente);
        }

        // recebe os clientes na fila
        for (Cliente cliente : listaClientes) {
            fila.receberCliente(cliente);
        }

        // gera postos
        for (int i = 0; i < numPostos; i++) {
            Posto posto = new Posto(i + 1, fila);
            listaPostos.add(posto);
        }

        // inicia os postos
        for (int i = 0; i < numPostos; i++) {
            listaThreads[i] = Thread.ofPlatform()
                    .name("Posto-" + listaPostos.get(i).getId())
                    .start(listaPostos.get(i));
        }

        // termina os postos
        for (Thread t : listaThreads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // gera os relatórios
        for (Posto posto : listaPostos) {
            posto.relatorio();
        }

        // gera os relatórios
        for (Cliente cliente : listaClientes) {
            cliente.relatorio();
        }
    }
}