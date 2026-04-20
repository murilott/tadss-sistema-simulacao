import java.time.Duration;

public class Cliente implements Runnable{
    private Integer id;
    private Duration tempoDeEspera;
    private Duration tempoDeAtendimento;

    public Cliente(Integer id) {
        this.id = id;
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet.");
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
}
