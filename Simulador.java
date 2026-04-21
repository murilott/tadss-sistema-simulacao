import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Simulador {
    private final int id;
    private final int numeroPostos;

    public Simulador(int id, int numeroPostos) {
        this.id = id;
        this.numeroPostos = numeroPostos;
    }

    public Resultados executar() throws InterruptedException {
        // Definição das variáveis iniciais

        Fila fila = new Fila();

        List<Posto> listaPostos = new ArrayList<>();
        List<Cliente> listaClientes = new ArrayList<>();

        // Scheduler que cria clientes
        ScheduledExecutorService clienteProdutor = Executors.newSingleThreadScheduledExecutor();

        Runnable receberCliente = new Runnable() {
            @Override
            public void run() {
                Cliente cliente = new Cliente(listaClientes.size() + 1);
                listaClientes.add(cliente);
                fila.receberCliente(cliente);

                int delay = ThreadLocalRandom.current().nextInt(Main.chegadaInicioClientes, Main.chegadaFimClientes);

                clienteProdutor.schedule(this, delay, TimeUnit.MILLISECONDS);
            }
        };

        clienteProdutor.schedule(receberCliente, 0, TimeUnit.SECONDS);

        // Scheduler que cria e dá start nos postos
        ScheduledExecutorService postoConsumidor = Executors.newScheduledThreadPool(numeroPostos);

        for (int i = 0; i < numeroPostos; i++) {
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
            System.out.println("(!) Encerrando expediente do simulador " + this.id + "...");
            clienteProdutor.shutdown();

            try {
                clienteProdutor.awaitTermination(1, TimeUnit.MINUTES);

                while (fila.temCliente()) {
                    Thread.sleep(100);
                }

                postoConsumidor.shutdown();
                postoConsumidor.awaitTermination(1, TimeUnit.MINUTES);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, Main.duracaoTotal, TimeUnit.MINUTES);

        clienteProdutor.awaitTermination(2, TimeUnit.HOURS);
        postoConsumidor.awaitTermination(2, TimeUnit.HOURS);

        return new Resultados(id, listaClientes, listaPostos);
    }
}
