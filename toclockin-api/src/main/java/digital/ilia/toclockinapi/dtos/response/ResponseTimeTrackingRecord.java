package digital.ilia.toclockinapi.dtos.response;

public class ResponseTimeTrackingRecord {

    private String mensagem;

    public ResponseTimeTrackingRecord(String message) {
        this.mensagem = message;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}