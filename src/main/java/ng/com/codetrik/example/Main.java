package ng.com.codetrik.example;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

import java.util.Arrays;

public class Main {
    public static void main(String[] args){
       createObservableWithJust();
       createObservableFromIterable();
       createObservableUsingCreate();
       var observable = Observable.just(1,2,3,4,5,6,7,8,9,10);
       //To create Observer, we need to implement Observer interface. This is no functional interface hence lambda cant help, we are left with using
        //anonymous inner class or an inner class that implements the interface
        observable.subscribe(new Main.Observer1());
    }
    public static void createObservableWithJust(){
         var observable = Observable.just(1,2,3,4,5,6,7,8); //maximum emitted item is 10 when just() is use to create Observable
         var disposable = observable.subscribe(System.out::println);
    }

    public static void createObservableFromIterable(){
        var list = Arrays.asList(9,10,11,12,13,14);
        var observable = Observable.fromIterable(list);
        var disposable = observable.subscribe(System.out::println);
        disposable.dispose();
    }

    public static void createObservableUsingCreate(){
        var observable = Observable.create(emitter -> {
            emitter.onNext(15);
            emitter.onNext(16);
            emitter.onNext(17);
            emitter.onComplete();
        });
        var disposable = observable.subscribe(System.out::println, error->System.out.println(error.getLocalizedMessage()),()->System.out.println("completed"));
    }

    private static class Observer1 implements Observer<Integer>{

        /**
         * Provides the Observer with the means of cancelling (disposing) the
         * connection (channel) with the Observable in both
         * synchronous (from within ) and asynchronous manner.
         *
         * @param d the Disposable instance whose {@link Disposable#dispose()} can
         *          be called anytime to cancel the connection
         * @since 2.0
         */
        @Override
        public void onSubscribe(@NonNull Disposable d) {
                System.out.println(this.getClass().getSimpleName() + " unsubscribed from listening to the observable");
        }

        /**
         * Provides the Observer with a new item to observe.
         * <p>
         * The {@link Observable} may call this method 0 or more times.
         * <p>
         * The {@code Observable} will not call this method again after it calls either {@link #onComplete} or
         * {@link #onError}.
         *
         * @param integer the item emitted by the Observable
         */
        @Override
        public void onNext(@NonNull Integer integer) {
            System.out.println(integer);
        }

        /**
         * Notifies the Observer that the {@link Observable} has experienced an error condition.
         * <p>
         * If the {@link Observable} calls this method, it will not thereafter call {@link #onNext} or
         * {@link #onComplete}.
         *
         * @param e the exception encountered by the Observable
         */
        @Override
        public void onError(@NonNull Throwable e) {
            System.out.println(e.getLocalizedMessage());
        }

        /**
         * Notifies the Observer that the {@link Observable} has finished sending push-based notifications.
         * <p>
         * The {@link Observable} will not call this method if it calls {@link #onError}.
         */
        @Override
        public void onComplete() {
            System.out.println("completed");
        }
    }

}
