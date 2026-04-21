import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Main {
    public static final int chegadaInicioClientes = 83; // 5
    public static final int chegadaFimClientes = 833; // 50

    public static final int atendimentoInicio = 500; // 30_000
    public static final int atendimentoFim = 2_000; // 120_000

    public static final int duracaoTotal = 2; // minutos // 120

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

                int delay = ThreadLocalRandom.current().nextInt(chegadaInicioClientes, chegadaFimClientes);

                clienteProdutor.schedule(this, delay, TimeUnit.MILLISECONDS);
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

            try {
                clienteProdutor.awaitTermination(1, TimeUnit.MINUTES);

                while (fila.temCliente()) {
                    Thread.sleep(100);
                }

                postoConsumidor.shutdown();
                postoConsumidor.awaitTermination(1, TimeUnit.MINUTES);

                for (Posto posto : listaPostos) {
                    posto.relatorio();
                }

                for (Cliente cliente : listaClientes) {
                    cliente.relatorio();
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }, duracaoTotal, TimeUnit.MINUTES);
    }
}