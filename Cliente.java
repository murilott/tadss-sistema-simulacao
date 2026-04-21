import java.time.Duration;

public class Cliente implements Runnable{
    private Integer id;

    private Duration tempoDeEspera;
    private Duration tempoDeAtendimento;
    private int postoAtendidoId;

    public Cliente(Integer id) {
        this.id = id;

        this.tempoDeEspera = Duration.ZERO;
        this.tempoDeAtendimento = Duration.ZERO;
        this.postoAtendidoId = 0;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void relatorio() {
        System.out.println("Cliente " + this.id + 
            " | Tempo de espera/atendimento: " + tempoDeEspera.toSeconds() + 
            " / " + tempoDeAtendimento.toSeconds() + 
            " s"
        );
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Duration getTempoDeEspera() {
        return tempoDeEspera;
    }

    public void setTempoDeEspera(long tempoDeEspera) {
        this.tempoDeEspera = Duration.ofSeconds(tempoDeEspera/1000);
    }

    public Duration getTempoDeAtendimento() {
        return tempoDeAtendimento;
    }

    public void setTempoDeAtendimento(int tempoDeAtendimento) {
        this.tempoDeAtendimento = Duration.ofSeconds(tempoDeAtendimento/1000);
    }

    public int getPostoAtendidoId() {
        return postoAtendidoId;
    }

    public void setPostoAtendidoId(int postoAtendidoId) {
        this.postoAtendidoId = postoAtendidoId;
    }
}
