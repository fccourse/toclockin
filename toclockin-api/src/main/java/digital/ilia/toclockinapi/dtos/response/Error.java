package digital.ilia.toclockinapi.dtos.response;

public class Error {

    private String mensagem;

    public Error(String userMessage) {
        this.mensagem = userMessage;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
