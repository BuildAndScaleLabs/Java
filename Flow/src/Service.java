public class Service {

    public void processRequest(Request request) {

        switch (request.getApiRequestType()) {

            case BACKEND_REQUEST:
                System.out.println("Processing Backend Request");
                break;

            case SCHEDULAR_REQUEST:
                System.out.println("Processing Scheduler Request");
                break;

            case NORMAL_REQUEST:
                System.out.println("Processing Normal Request");
                break;

            case EXASCALE_REQUEST:
                System.out.println("Processing Exascale Request");
                break;

            default:
                System.out.println("Processing Default Request");
        }
    }
}