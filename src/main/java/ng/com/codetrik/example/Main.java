package ng.com.codetrik.example;

import io.reactivex.Observable;

import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MICROSECONDS;

public class Main {
    public static void main(String[] args){
        createObservableByInterval();
    }

    private static void createObservableByTimer(){
        Observable
                .timer(10, TimeUnit.SECONDS)
                .subscribe(Main::log);
    }

    private static void createObservableByInterval(){
        Observable
                .interval(1_000_000 / 60, MICROSECONDS)
                .subscribe(Main::log);
    }

    private static void log(Long zero) {
        System.out.println(zero);
    }
}
