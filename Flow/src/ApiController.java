public class ApiController {

   private final  Service service;

    public ApiController(Service service){
        this.service = service;
    }
    public  void backendRequest(){
        Request request = new Request();
        System.out.println("==============Sending request to backend===============");
        request.setRequestId("asdfasdfasc");
        request.setApiRequestType(ApiRequestType.BACKEND_REQUEST);
        service.processRequest(request);
    }
}
