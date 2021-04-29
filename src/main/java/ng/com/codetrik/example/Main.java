package ng.com.codetrik.example;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Flow;

public class Main {
    public static void main(String[] args){
        legalWayOfPerformingConcurrencyInRX();
    }

    public static void observableOperatorSync1(){
        //Observable by default work in synchronous blocking mode in a way it emits, perform operation function and  listened to by observer
        //subscribe() method is run on same thread the observable [emitter.onNext(),emitter.onError()|emitter.onComplete()] and operator(s) is called.
        //recall that emitter.onXXX() method called by the observable in turn executes the corresponding subscribed Observer.onXXX()
        /*
emitter.onNext()->operator()->subscribe()->emitter.onNext()->operator()->subscribe()->{[emitter.onError() | emitter.onComplete()]->operator()->subscribe()}
         */
        Observable.create(emitter -> Arrays.asList(1,2,3,4,5).forEach(integer -> {
            System.out.println("observable emits on "+Thread.currentThread().getName()+ " thread");
            emitter.onNext(integer);
        }))
                .map(item-> "Operator performed on "+Thread.currentThread().getName()+ " thread and worked on item "+item)
                .subscribe(item->{
                    System.out.println(item);
                    System.out.println("operator output received on "+Thread.currentThread().getName() + " thread by the observer");
                });
    }
    public static void observableOperatorSync2(){
        //Observable by default work in synchronous blocking mode in a way it emits, perform operation function and  listened to by observer
        //subscribe() method is run on same thread the observable [emitter.onNext(),emitter.onError()|emitter.onComplete()] and operator(s) is called.
        //recall that emitter.onXXX() method called by the observable in turn executes the corresponding subscribed Observer.onXXX()
        /*
emitter.onNext()->operator()->subscribe()->emitter.onNext()->operator()->subscribe()->{[emitter.onError() | emitter.onComplete()]->operator()->subscribe()}
         */
        Observable.create(emitter -> {

            //lets try to do emission from a custom thread and see if the observer received from the main thread or this custom thread
            new Thread(()->{
                Arrays.asList(1,2,3,4,5).forEach(integer -> {
                    System.out.println("observable emits on "+Thread.currentThread().getName()+ " thread");
                    emitter.onNext(integer);
                });
            },"fetch").start();
        })
                .map(item-> "Operator performed on "+Thread.currentThread().getName()+ " thread and worked on item "+item).
                subscribe(item->{
                    System.out.println(item);
                    System.out.println("operator output received on "+Thread.currentThread().getName() + " thread by the observer");
                });
    }
    public static void observableSync1(){
        //Observable by default work in synchronous blocking mode in a way it emits and listened to by observer
        //subscribe() method is run on same thread the observable [emitter.onNext(),emitter.onError()|emitter.onComplete()] is called.
        //recall that emitter.onXXX() method called by the observable in turn executes the corresponding subscribed Observer.onXXX()
        // emitter.onNext()->subscribe()->emitter.onNext()->subscribe()->{[emitter.onError() | emitter.onComplete()]->subscribe()}
        Observable.create(emitter -> Arrays.asList(1,2,3,4,5).forEach(integer -> {
            System.out.println("observable emits on "+Thread.currentThread().getName()+ " thread");
            emitter.onNext(integer);
        })).subscribe(item->{
            System.out.println("observer received on "+Thread.currentThread().getName() + " thread");
            System.out.println(item);
        });
    }

    public static void observableSync2(){
        //Observable by default work in synchronous blocking mode in a way it emits and listened to by observer
        //subscribe() method is run on same thread the observable [emitter.onNext(),emitter.onError()|emitter.onComplete()] is called.
        //recall that emitter.onXXX() method called by the observable in turn executes the corresponding subscribed Observer.onXXX()
        // emitter.onNext()->subscribe()->emitter.onNext()->subscribe()->{[emitter.onError() | emitter.onComplete()]->subscribe()}
        Observable.create(emitter -> {

            //lets try to do emission from a custom thread and see if the observer received from the main thread or this custom thread
            new Thread(()->{
                Arrays.asList(1,2,3,4,5).forEach(integer -> {
                    System.out.println("observable emits on "+Thread.currentThread().getName()+ " thread");
                    emitter.onNext(integer);
                });
            },"fetch").start();
        }).subscribe(item->{
            System.out.println("observer received on "+Thread.currentThread().getName() + " thread");
            System.out.println(item);
        });
    }

    private static void IllegalWayOfPerformingConcurrencyInRX(){
        /*
            The salient contract of an Observable is that it will never emmit onNext(), onError(), onComplete() event concurrently for same observable
            i.e, Same observer object will not be currently emitting onNext(), onError() and onComplete() from different thread
         */

        //ILLEGAL code DON'T CODE LIKE THIS AS RESULT IS UNPREDICTABLE DUE TO RACE OF THREAD

            Observable.create(s -> {
        // Thread A
                new Thread(() -> {
                    s.onNext("one");
                    s.onNext("two");
                    s.onComplete();
                }).start();
        // Thread B
                new Thread(() -> {
                    s.onNext("three");
                    s.onNext("four");
                    s.onComplete();
                }).start();
            }).subscribe(item->{
                System.out.println("observer received on "+Thread.currentThread().getName() + " thread");
                System.out.println(item);
            });

    }

    private static void legalWayOfPerformingConcurrencyInRX(){
        /*
            The salient contract of an Observable is that it will never emmit onNext(), onError(), onComplete() event concurrently for same observable
            i.e, Same observer object will not be currently emitting onNext(), onError() and onComplete() from different thread.

            So how do we take advantage of concurrency in RXJava. A single observable stream is always serialized but each observable stream can operate
            independently of one another

            Hence two or more observable stream can be merge together to yield a single observable stream. Then an observer can then subscribe to this
            resulting observable stream.


         */


        Observable<String> a = Observable.create(s -> {
            new Thread(() -> {
                s.onNext("one");
                s.onNext("two");
                s.onComplete();
            }).start();
        });
        Observable<String> b = Observable.create(s -> {
            new Thread(() -> {
                s.onNext("three");
                s.onNext("four");
                s.onComplete();
            }).start();
        });
        // this subscribes to a and b concurrently, and merges into a third sequential stream
        Observable.merge(a,b).subscribe(item->{
            System.out.println("observer received on "+Thread.currentThread().getName() + " thread");
            System.out.println(item);
        });
    }

    private static void pause(long l) {
        try {
            Thread.sleep(l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
