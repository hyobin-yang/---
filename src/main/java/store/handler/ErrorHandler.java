package store.handler;

public class ErrorHandler {

    public static void handle(Exception e) {
        System.out.println(e.getMessage());
    }

    private ErrorHandler(){
    }
}
