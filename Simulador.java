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

        System.out.println("(" + java.time.LocalDateTime.now() + ")" +
                "Iniciando simulador " + this.id +
                " com " + this.numeroPostos +
                " postos de atendimento.");

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

        // Scheduler que cria e dá start nos postos
        clienteProdutor.schedule(receberCliente, 0, TimeUnit.SECONDS);

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

        // Scheduler que executa a cada 30 minutos para mostrar o status atual do
        // simulador
        ScheduledExecutorService schedulerTemporizador = Executors.newScheduledThreadPool(1);

        Runnable tarefa = () -> {
            System.out.println("(" + java.time.LocalDateTime.now() + ")" +
                    " Simulador " + this.id + " | Status atual: " +
                    fila.tamanho() + " clientes na fila, " +
                    listaClientes.size() + " total de clientes criados, " +
                    listaPostos.size() + " postos de atendimento.");

            for (Posto posto : listaPostos) {
                posto.relatorio();
            }

            System.out.println("==== /// ====");
        };

        schedulerTemporizador.scheduleAtFixedRate(tarefa, 30, 30, TimeUnit.MINUTES);

        // Scheduler de controle e encerramento do simulador
        ScheduledExecutorService controle = Executors.newSingleThreadScheduledExecutor();

        controle.schedule(() -> {
            System.out.println("(!) Encerrando expediente do simulador " + this.id + "...");
            clienteProdutor.shutdown();

            try {
                clienteProdutor.awaitTermination(1, TimeUnit.MINUTES);

                while (fila.temCliente()) {
                    Thread.sleep(1000);
                }

                postoConsumidor.shutdown();
                postoConsumidor.awaitTermination(1, TimeUnit.MINUTES);

                schedulerTemporizador.shutdownNow();
                controle.shutdownNow();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, Main.duracaoTotal, TimeUnit.MINUTES);

        // Aguarda terminar completamente
        boolean terminou = controle.awaitTermination(Main.duracaoTotal + 2, TimeUnit.MINUTES);

        if (!terminou) {
            System.out.println("Forçando encerramento do simulador " + this.id);
            controle.shutdownNow();
        }

        System.out.println("Simulador " + this.id + " FINALIZADO");

        return new Resultados(id, listaClientes, listaPostos);
    }
}
