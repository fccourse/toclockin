package digital.ilia.toclockinapi.dtos.response;

public class Error {

    private String userMessage;
    private String errorMessage;

    public Error(String userMessage, String errorMessage) {
        this.userMessage = userMessage;
        this.errorMessage = errorMessage;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
