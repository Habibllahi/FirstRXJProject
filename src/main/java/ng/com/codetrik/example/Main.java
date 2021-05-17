package ng.com.codetrik.example;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MICROSECONDS;

public class Main {
    public static void main(String[] args){
        /*
        defer() is use to create an Observable that provides newly created observable to each observer that subscribe.
        the defer accepts a supplier which is use to create new Observable using any of the possible ways of creating Observables


         */
        var obj = Observable.defer(()-> {
            System.out.println("new observable");
            return Observable.just(1,2,3,4,5,6,7,8,9);
        });
        obj.subscribe(System.out::println);
        obj.subscribe(System.out::println);


    }


}
