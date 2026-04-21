import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class Relatorio {
    public static void exportar(List<Resultados> resultados) {
        try (
            FileWriter clientesWriter = new FileWriter("clientes.csv");
            FileWriter postosWriter = new FileWriter("postos.csv")
        ) {
            // Headers
            clientesWriter.append("simulacao_id,cliente_id,tempo_espera,tempo_atendimento,posto_id\n");
            postosWriter.append("simulacao_id,posto_id,clientes_atendidos,tempo_total_atendimento\n");

            // Dados
            for (Resultados res : resultados) {
                int simId = res.getId();

                // Clientes
                for (Cliente c : res.getClientes()) {
                    clientesWriter.append(String.valueOf(simId)).append(",");
                    clientesWriter.append(String.valueOf(c.getId())).append(",");
                    clientesWriter.append(String.valueOf(toSeconds(c.getTempoDeEspera()))).append(",");
                    clientesWriter.append(String.valueOf(toSeconds(c.getTempoDeAtendimento()))).append(",");
                    clientesWriter.append(String.valueOf(c.getPostoAtendidoId())).append("\n");
                }

                // Postos
                for (Posto p : res.getPostos()) {
                    postosWriter.append(String.valueOf(simId)).append(",");
                    postosWriter.append(String.valueOf(p.getId())).append(",");
                    postosWriter.append(String.valueOf(p.getClientesAtendidos())).append(",");
                    postosWriter.append(String.valueOf(p.getTempoTotalDeAtendimento())).append("\n");
                }
            }

            clientesWriter.flush();
            postosWriter.flush();

            System.out.println("Relatório gerado");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static long toSeconds(Duration d) {
        return (d != null) ? d.getSeconds() : 0;
    }
}
