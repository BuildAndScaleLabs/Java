class Main{
    public static void main(String[] args){

        Service service = new Service();

        ApiController controller =
                new ApiController(service);

        controller.backendRequest();
    }
}