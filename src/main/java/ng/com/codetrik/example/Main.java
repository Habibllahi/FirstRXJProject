package ng.com.codetrik.example;

import io.reactivex.Observable;

public class Main {
    public static void main(String[] args){
        createObservableUsingNever();
    }
    private static void createObservableUsingEmpty(){
        //create an Observable that emits no items to the Observer and immediately invokes its onComplete method.
        var observable = Observable.empty();
        observable.subscribe(System.out::println,System.out::println,()->System.out.println("Completed"));
    }

    private static void createObservableUsingNever(){
        //create an Observable that never sends any items or notifications to an Observer.
        var observable = Observable.never();
        observable.subscribe(System.out::println,System.out::println,()->System.out.println("Completed"));
    }

    private static void createObservableUsingError(){
        //create an Observable that invokes an Observer's onError method when the Observer subscribes to it.
        var observable1 = Observable.error(()->new Exception("each subscriber will have unique instance of error passed to it"));
        var observable2 = Observable.error(new Exception("each subscriber will have same instance of error passed to it"));
        observable1.subscribe(System.out::println,System.out::println,()->System.out.println("Completed"));
        observable1.subscribe(System.out::println,System.out::println,()->System.out.println("Completed"));
        observable2.subscribe(System.out::println,System.out::println,()->System.out.println("Completed"));
        observable2.subscribe(System.out::println,System.out::println,()->System.out.println("Completed"));
    }

}
