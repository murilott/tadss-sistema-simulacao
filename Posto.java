
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import exercicio06.Prato;

public class Posto implements Runnable {
    private Integer id;
    private Fila filaDeEspera;

    private boolean estaAtendendo;

    private Integer clientesAtendidos;
    private Integer tempoTotalDeAtendimento;;

    public Posto(Integer id, Fila filaDeEspera) {
        this.id = id;
        this.filaDeEspera = filaDeEspera;

        this.estaAtendendo = false;
        this.clientesAtendidos = 0;
        this.tempoTotalDeAtendimento = 0;
    }

    @Override
    public void run() {
        while (filaDeEspera.temCliente()) {
            Cliente cliente;
            synchronized (filaDeEspera) {
                cliente = filaDeEspera.removerCliente();
            }

            atenderCliente(cliente);

            clientesAtendidos++;
        }
    }

    public synchronized void atenderCliente(Cliente cliente) {
        System.out.println("(#) Posto " + this.id + " | Atendendo cliente " + cliente.getId());

        // int tempoDeAtendimento = ThreadLocalRandom.current().nextInt(30_000, 120_000);
        int tempoDeAtendimento = ThreadLocalRandom.current().nextInt(10_000, 15_000);

        try {
            Thread.sleep(tempoDeAtendimento);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.tempoTotalDeAtendimento += tempoDeAtendimento;
        cliente.setTempoDeAtendimento(tempoDeAtendimento);

        System.out.println("(~) Posto " + this.id + " | Fim do atendimento do cliente " + cliente.getId() +
                " (tempo de atendimento: " + tempoDeAtendimento/1000 + " s)" 
                // + "\n"
            );
    }

    public void relatorio() {
        System.out.println("Posto " + id);
        System.out.println("-> Total de clientes atendidos: " + clientesAtendidos);
        System.out.println("-> Tempo total de atendimento: " + tempoTotalDeAtendimento/1000 + " s");
    }

    public boolean ocupado() {
        return estaAtendendo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
