package iset.project.checkinpromobile.User;

public class Apis {
    //private static final String Url="http://10.0.2.2:8080";
    private static final String Url="http://192.168.1.254:8080";
    public static UserService getService(){
        return  Client.getRetrofit(Url).create(UserService.class);
    }
}
