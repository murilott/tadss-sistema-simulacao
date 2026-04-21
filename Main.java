import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    // Definição dos tempos (tempo rápido - tempo normal)
    public static final int chegadaInicioClientes = 5_000; // 83 ms - 5_000 ms (s)
    public static final int chegadaFimClientes = 50_000; // 833 ms - 50_000 ms (s)

    public static final int atendimentoInicio = 30_000; // 500 ms - 30_000 ms (s)
    public static final int atendimentoFim = 120_000; // 2_000 ms - 120_000 ms (s)

    public static final int duracaoTotal = 120; // 2 m - 120 m

    // Habilite para mostrar em tempo real as interações dos postos e filas
    public static final boolean logs = false; 

    public static void main(String[] args) throws InterruptedException {

        // Defina o número de simulações
        int numSimulacoes = 6;

        ExecutorService pool = Executors.newFixedThreadPool(numSimulacoes);

        List<Future<Resultados>> futures = new ArrayList<>();

        System.out.println("(" + java.time.LocalDateTime.now() + ")" + 
            "Iniciando " + numSimulacoes + 
            " simulações para o tempo total de " + duracaoTotal + " minutos."
        );

        // Realiza as simulações em paralelo
        for (int n = 1; n <= numSimulacoes; n++) {
            Simulador sim = new Simulador(n, n);

            Future<Resultados> future = pool.submit(() -> sim.executar());

            futures.add(future);
        }

        List<Resultados> resultados = new ArrayList<>();

        // Obtem os resultados
        for (Future<Resultados> future : futures) {
            try {
                Resultados res = future.get();
                resultados.add(res);

                res.relatorioPostos();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        pool.shutdown();

        Relatorio.exportar(resultados);

        System.out.println("Fim do programa.");
    }
}