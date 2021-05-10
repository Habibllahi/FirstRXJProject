package ng.com.codetrik.example;

import io.reactivex.Observable;

public class Main {
    public static void main(String[] args){
        connectableObservableRunAsColdObservable();
        /*
          1)  The whole idea around connectable observable is not to start emitting item to subscriber until connect() method is called
          2) or Automatically connect to start emitting item to subscribers when number of specified subscriber reached autoConnect(int noOfSubscriber)
            it default to 1 subscriber when no value is passed.
          3) it act HOT when connectable observer is created using publish() : it wont emmit to late subscriber(s)
          4) it act COLD when connectable observer is created using replay() : it will replay emission to late subscriber(S)
          5) However, both HOT and COLD connectable observer will COLDLY emit to all subscriber that subscribe before connection was made. 

          subscription is late for connectable subscriber when emission had already started by call of connect() or connection already get activated
          after the number of subscriber specified in autoConnect(int noOfSubscriber) reached
         */
    }

    private static void createColdObservable() {
                    var observable = Observable.just(1,2,3,4,5,6,7,8,9,10);
                    observable.subscribe(item ->System.out.println("observer 1: " + item));
                    pause(5000L);
                    observable.subscribe(item ->System.out.println("observer 2: " + item)); //this will also print from 1-10 despite it subscribed 5 seconds after
    }

    private static void connectableObservableWithAutoConnectRunAsHotObservable(){
            var connectableObservable = Observable.just(1,2,3,4,5,6,7,8,9,10).publish();
            /*
                Instructs the ConnectableObservable to automatically begin emitting the items from its underlying Observable to all its Observers
                when number of subscriber reaches 1
             */

            var observable =connectableObservable.autoConnect();
            observable.subscribe(item ->System.out.println("observer 1: " + item)); //emission begins and print all emitted item
            observable.subscribe(item ->System.out.println("observer 2: " + item)); //this wont print as it joint late, all item already emitted
            pause(1000l);
            observable.subscribe(item ->System.out.println("observer 3: " + item));//this wont print either as it joint late, all item already emitted
    }

    private static void connectableObservableWithAutoConnectRunAsColdObservable(){
        var connectableObservable = Observable.just(1,2,3,4,5,6,7,8,9,10).replay();
            /*
                Instructs the ConnectableObservable to automatically begin emitting the items from its underlying Observable to all its Observers
                when number of subscriber reaches 1
             */

        var observable =connectableObservable.autoConnect();
        observable.subscribe(item ->System.out.println("observer 1: " + item)); //emission begins and print all emitted item
        observable.subscribe(item ->System.out.println("observer 2: " + item)); //this will print as it joint late, observable will replay emission
        pause(1000l);
        observable.subscribe(item ->System.out.println("observer 3: " + item));//this will print as it joint late, observable will replay emission
    }

    private static void connectableObservableWithAutoConnectRunAsHotObservable(int noOfSubscriber){
        var connectableObservable = Observable.just(1,2,3,4,5,6,7,8,9,10).publish();
        /*
            Instructs the ConnectableObservable to automatically begin emitting the items from its underlying Observable to all its Observers
            when number of subscriber reaches number of specified subscribers
         */
        var observable =connectableObservable.autoConnect(noOfSubscriber);
        observable.subscribe(item ->System.out.println("observer 1: " + item));
        //if noOfSubscriber is 1, this wont print as it joint late, observable already complete emission
        observable.subscribe(item ->System.out.println("observer 2: " + item));
        pause(1000l);
        //if noOfSubscriber is 2, this wont print as it joint late, observable already complete emission
        observable.subscribe(item ->System.out.println("observer 3: " + item));
    }

    private static void connectableObservableWithAutoConnectRunAsColdObservable(int noOfSubscriber){
        var connectableObservable = Observable.just(1,2,3,4,5,6,7,8,9,10).replay();
        /*
            Instructs the ConnectableObservable to automatically begin emitting the items from its underlying Observable to all its Observers
            when number of subscriber reaches number of specified subscribers
         */
        var observable =connectableObservable.autoConnect(noOfSubscriber);
        observable.subscribe(item ->System.out.println("observer 1: " + item));
        //if noOfSubscriber is 1, this will print as it joint late, observable will replay emission
        observable.subscribe(item ->System.out.println("observer 2: " + item));
        pause(1000l);
        //if noOfSubscriber is 2, this will print as it joint late, observable will replay emission
        observable.subscribe(item ->System.out.println("observer 3: " + item));
    }

    private static void connectableObservableRunAsHotObservable() {
        var connectableObservable = Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).publish();
        /*
            Instructs the ConnectableObservable to begin emitting the items from its underlying Observable to all its Observers by only
            when connect() is called.
         */
        connectableObservable.subscribe(item -> System.out.println("observer 1: " + item));
        connectableObservable.subscribe(item -> System.out.println("observer 2: " + item));

        connectableObservable.connect();//emission only begin now
        connectableObservable.subscribe(item -> System.out.println("observer 3: " + item)); //this wont print as it subscribed late, all item emitted
    }

    private static void connectableObservableRunAsColdObservable() {
        var connectableObservable = Observable.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).replay();
        /*
            Instructs the ConnectableObservable to begin emitting the items from its underlying Observable to all its Observers by only
            when connect() is called.
         */
        connectableObservable.subscribe(item -> System.out.println("observer 1: " + item));
        connectableObservable.subscribe(item -> System.out.println("observer 2: " + item));

        connectableObservable.connect();//emission only begin now
        connectableObservable.subscribe(item -> System.out.println("observer 3: " + item)); //this will print as it subscribed late, observable replays emmission
    }

    public static void pause(long duration){
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
