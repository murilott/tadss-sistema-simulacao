import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static final int chegadaInicioClientes = 83; // 5
    public static final int chegadaFimClientes = 833; // 50

    public static final int atendimentoInicio = 500; // 30_000
    public static final int atendimentoFim = 2_000; // 120_000

    public static final int duracaoTotal = 2; // minutos // 120

    // Habilite para mostrar em tempo real as interações
    public static final boolean logs = false; 

    public static void main(String[] args) throws InterruptedException {

        int numPostos = 5;
        ExecutorService pool = Executors.newFixedThreadPool(4);

        List<Future<Resultados>> futures = new ArrayList<>();

        for (int n = 1; n <= numPostos; n++) {
            Simulador sim = new Simulador(n, n);

            Future<Resultados> future = pool.submit(() -> sim.executar());

            futures.add(future);
        }

        List<Resultados> resultados = new ArrayList<>();

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

        // List<Resultados> resultados = new ArrayList<>();

        // for (int n = 1; n <= 10; n++) {

        //     Simulador sim = new Simulador(n, n);

        //     Resultados res = sim.executar();

        //     resultados.add(res);

        //     res.relatorioPostos();
        // }
    }
}