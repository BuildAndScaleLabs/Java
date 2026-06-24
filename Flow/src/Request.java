public class Request {

    private String requestId;
    private ApiRequestType apiRequestType;
    private Object data;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public ApiRequestType getApiRequestType() {
        return apiRequestType;
    }

    public void setApiRequestType(ApiRequestType apiRequestType) {
        this.apiRequestType = apiRequestType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
