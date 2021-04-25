package ng.com.codetrik.example;

import io.reactivex.Observable;

public class Main {
    public static void main(String[] args){
        createObserverUsingRangeAsColdObservable();
    }
    public static void createObserverUsingRangeAsColdObservable(){
        var observer = Observable.range(0,10);
        observer.subscribe((item)->System.out.println("observer 1 : "+ item));
        pause(5000L);
        observer.subscribe((item)->System.out.println("observer 2 : "+ item));
    }

    private static void pause(long l) {
        try {
            Thread.sleep(l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
