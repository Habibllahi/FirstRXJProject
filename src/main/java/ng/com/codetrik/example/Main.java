package ng.com.codetrik.example;

import io.reactivex.Observable;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args){
     var list = Arrays.asList(1,2,3,4,5,6,7,8,9,0);
      var unCachedObservable = unCachedObservable(list);
      var cachedObservable = cachedObservable(list);

        cachedObservable.subscribe((item)->{
          System.out.println(Thread.currentThread().getName() + " : " + item + ": subscriber 1");
      },System.out::println);

        cachedObservable.subscribe((item)->{
            System.out.println(Thread.currentThread().getName() + " : " + item + ": subscriber 2");
        },System.out::println);
    }

    private static Observable<BigInteger> infiniteStream(){
        return Observable.<BigInteger>create(
                emitter -> new Thread(()->{

                    BigInteger i = BigInteger.ZERO;
                    while (!emitter.isDisposed()){
                        emitter.onNext(i);
                        i = i.add(BigInteger.ONE);
                    }
                }).start()
        );
    }

    private static Observable<Integer> cachedObservable(List<Integer> source){
        /*
        when an observable is cached, the RX will not create new instance of the ObservableOnSubscribe object (implemented via the lambda) every time
        a new subscription is made. It create the new instance of the ObservableOnSubscribe object once and cache the outcome. This outcome is provided
        to new subscriber
         */
        return Observable.<Integer>create(
                emitter -> {
                    System.out.println("thread created");
                    source.forEach((listItem)->emitter.onNext(listItem));

                }
        ).cache();
    }

    private static Observable<Integer> unCachedObservable(List<Integer> source){
        /*
        when an observable is not cached, the RX will create new instance of the ObservableOnSubscribe object (implemented via the lambda) every time
        a new subscription is made. By doing so it can emit for each subscriber independently
         */
        return Observable.<Integer>create(
                emitter -> {
                    System.out.println("thread created");
                    source.forEach((listItem)->emitter.onNext(listItem));

                }
        );
    }
    private static void pause(long l) {
        try {
            Thread.sleep(l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void noMore(){
        System.out.println("completed");
    }
}
