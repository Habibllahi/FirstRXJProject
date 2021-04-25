package ng.com.codetrik.example;

import io.reactivex.Observable;

public class Main {
    public static void main(String[] args){
        //pass an Observable that invokes observer's onError() upon the observer subscription
        var observable = Observable.just(1, 2, 3, 4, 5);
        throwException(observable.error(new Exception("An error"))); //(1) and (2) will print same hash code, the observable wont emit items
        throwException(observable.error(()->new Exception("An Error")));// (1) and (2) will print different hash code,the observable wont emit items
    }
    private static void throwException(Observable observable){
        //create an Observable that invokes observer's onError() upon the observer subscription
        observable.subscribe(System.out::println, throwable -> System.out.println("Error1 "+throwable.hashCode())); //(1)
        observable.subscribe(System.out::println, throwable -> System.out.println("Error2 "+throwable.hashCode())); //(2)
    }

}
