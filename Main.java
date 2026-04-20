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

        // cria clientes
        ScheduledExecutorService clienteProdutor = Executors.newSingleThreadScheduledExecutor();

        Runnable receberCliente = new Runnable() {
            @Override
            public void run() {
                Cliente cliente = new Cliente(listaClientes.size() + 1);
                listaClientes.add(cliente);
                fila.receberCliente(cliente);

                int delay = ThreadLocalRandom.current().nextInt(2, 10);

                clienteProdutor.schedule(this, delay, TimeUnit.SECONDS);
            }
        };

        clienteProdutor.schedule(receberCliente, 0, TimeUnit.SECONDS);

        // cria e dá start nos postos
        ScheduledExecutorService postoConsumidor = Executors.newScheduledThreadPool(3);

        for (int i = 0; i < numPostos; i++) {
            Posto posto = new Posto(i + 1, fila);
            listaPostos.add(posto);

            postoConsumidor.scheduleAtFixedRate(
                    posto,
                    0,
                    1,
                    TimeUnit.SECONDS);
        }

        ScheduledExecutorService controle = Executors.newSingleThreadScheduledExecutor();

        controle.schedule(() -> {
            System.out.println("(!) Encerrando expediente...");

            clienteProdutor.shutdown();
            postoConsumidor.shutdown();
            controle.shutdown();
        }, 60, TimeUnit.SECONDS);

        for (Posto posto : listaPostos) {
            posto.relatorio();
        }

        for (Cliente cliente : listaClientes) {
            cliente.relatorio();
        }

        // Runnable receberCliente = new Runnable() {
        // @Override
        // public void run() {
        // Cliente cliente = new Cliente(listaClientes.size() + 1);
        // listaClientes.add(cliente);
        // fila.receberCliente(cliente);

        // int delay = ThreadLocalRandom.current().nextInt(5, 31);

        // scheduler.schedule(this, delay, TimeUnit.SECONDS);
        // }
        // };

        // scheduler.schedule(receberCliente, 0, TimeUnit.SECONDS);

        /*
         * // gera clientes
         * for (int i = 0; i < numClientes; i++) {
         * Cliente cliente = new Cliente(i + 1);
         * listaClientes.add(cliente);
         * }
         * 
         * // recebe os clientes na fila
         * for (Cliente cliente : listaClientes) {
         * fila.receberCliente(cliente);
         * }
         * 
         * // gera postos
         * for (int i = 0; i < numPostos; i++) {
         * Posto posto = new Posto(i + 1, fila);
         * listaPostos.add(posto);
         * }
         * 
         * // inicia os postos
         * for (int i = 0; i < numPostos; i++) {
         * listaThreads[i] = Thread.ofPlatform()
         * .name("Posto-" + listaPostos.get(i).getId())
         * .start(listaPostos.get(i));
         * }
         * 
         * // termina os postos
         * for (Thread t : listaThreads) {
         * try {
         * t.join();
         * } catch (InterruptedException e) {
         * Thread.currentThread().interrupt();
         * }
         * }
         * 
         * // gera os relatórios
         * for (Posto posto : listaPostos) {
         * posto.relatorio();
         * }
         * 
         * // gera os relatórios
         * for (Cliente cliente : listaClientes) {
         * cliente.relatorio();
         * }
         */
    }
}