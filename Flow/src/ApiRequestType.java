import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum ApiRequestType {

    BACKEND_REQUEST,SCHEDULAR_REQUEST,NORMAL_REQUEST,EXASCALE_REQUEST,DEFAULT_REQUEST;


    private static final Map<String,ApiRequestType> LOOKUP = new ConcurrentHashMap<>();

    static{
        for(ApiRequestType apiRequestType : ApiRequestType.values()){
        LOOKUP.put(apiRequestType.toString(),apiRequestType);
        }
    }
}
