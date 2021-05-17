package ng.com.codetrik.example;

import io.reactivex.subjects.AsyncSubject;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

public class Main {
    public static void main(String[] args){

        publishSubject();
        replaySubject();
        behaviorSubject();
        asyncSubject();
    }

    private static void asyncSubject() {
        /*
            Async subject will publish only the last emitted event before onComplete is called, irrespective of when the subscriber subscribes.
        */
        var source = AsyncSubject.<Integer>create();
        source.onNext(0);
        source.subscribe((item)-> System.out.println("BehaviorSubject's subscriber:1 receives -> "+item+ " on "  + Thread.currentThread().getName() + " thread"),System.out::println,()->System.out.println("\n")); //will receive 4
        source.onNext(1);//will not be emitted to
        source.onNext(2);
        source.subscribe((item)-> System.out.println("BehaviorSubject's subscriber:2 receives -> "+item + " on " + Thread.currentThread().getName() + " thread"),System.out::println,()->System.out.println("\n")); //will receive 4
        source.onNext(3);
        source.onNext(4);
        source.onComplete();
    }

    private static void behaviorSubject() {
        /*
            behaviour subject will publish all event emitted after subscription just like publishSubject, however it will push the last event emitted
            before subscription.
         */
        var source = BehaviorSubject.<Integer>create();
        source.onNext(0);
        source.subscribe((item)-> System.out.println("BehaviorSubject's subscriber:1 receives -> "+item+ " on "  + Thread.currentThread().getName() + " thread"),System.out::println,()->System.out.println("\n")); //will receive 0,1,2,3,4
        source.onNext(1);//will not be emitted to
        source.onNext(2);
        source.subscribe((item)-> System.out.println("BehaviorSubject's subscriber:2 receives -> "+item + " on " + Thread.currentThread().getName() + " thread"),System.out::println,()->System.out.println("\n")); //will receive 2,3,4
        source.onNext(3);
        source.onNext(4);
        source.onComplete();

    }

    private static void replaySubject() {
        /*
            ReplaySubject as Observable will push all emitted events before subscription and after subscription. It does this by caching of previously
            emitted events before subscription and the push the cache to subscriber upon subscribing before it begin to pushing event emitted after
            subscription.

         */

        var source = ReplaySubject.<Integer>create();
        source.subscribe((item)-> System.out.println("ReplaySubject's subscriber:1 receives -> "+item+ " on "  + Thread.currentThread().getName() + " thread"),System.out::println,()->System.out.println("\n")); //will receive 1,2,3,4
        source.onNext(1);//will not be emitted to
        source.onNext(2);
        source.subscribe((item)-> System.out.println("ReplaySubject's subscriber:2 receives -> "+item + " on " + Thread.currentThread().getName() + " thread"),System.out::println,()->System.out.println("\n")); //will receive 1,2,3,4
        source.onNext(3);
        source.onNext(4);
        source.onComplete();
    }


    private static void publishSubject() {
        /*
            PublishSubject as Observable will push all emitted events after subscription
        */
        var source = PublishSubject.<Integer>create();

        source.subscribe((item)-> System.out.println("PublishSubject's subscriber:1 receives -> "+item+ " on "  + Thread.currentThread().getName() + " thread"),System.out::println,()->System.out.println("\n ")); //will receive 1,2,3,4
        source.onNext(1);//will not be emitted to
        source.onNext(2);
        source.subscribe((item)-> System.out.println("PublishSubject's subscriber:2 receives -> "+item + " on " + Thread.currentThread().getName() + " thread"),System.out::println,()->System.out.println("\n ")); //will receive 3,4
        source.onNext(3);
        source.onNext(4);
        source.onComplete();
    }

}
