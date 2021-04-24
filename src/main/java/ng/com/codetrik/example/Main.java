package ng.com.codetrik.example;

import io.reactivex.Observable;

import java.util.Arrays;

public class Main {
    public static void main(String[] args){
       createObservableWithJust();
       createObservableFromIterable();
       createObservableUsingCreate();
    }
    public static void createObservableWithJust(){
         var observable = Observable.just(1,2,3,4,5,6,7,8);
         observable.subscribe(System.out::println);
    }

    public static void createObservableFromIterable(){
        var list = Arrays.asList(9,10,11,12,13,14);
        var observable = Observable.fromIterable(list);
        observable.subscribe(System.out::println);
    }

    public static void createObservableUsingCreate(){
        var observable = Observable.create(emitter -> {
            emitter.onNext(15);
            emitter.onNext(16);
            emitter.onNext(17);
            emitter.onComplete();
        });
        observable.subscribe(System.out::println, error->System.out.println(error.getLocalizedMessage()),()->System.out.println("completed"));
    }
}
