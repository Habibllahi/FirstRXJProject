package ng.com.codetrik.example;

import io.reactivex.Completable;
import io.reactivex.Single;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args){
      single(Arrays.asList("am singular"));
      writeToDatabase("pas").subscribe(()->System.out.println("completed"),error->System.out.println(error));
    }

    private static void single(List<String> source){
        /*
            *Single in RXJava is use as replacement for Future<T>. See it like a lazy equivalent of Future<T>
            *Single will push a single data by calling the onSuccess() and will throw error by calling the onError() method
            *It can be subscribed to like an observable and wont execute its enclosing codes until it being subscribed to because Single is as well lazy
         */
        var single = Single.<String>create(emitter -> {
           if(source.size()==1)
               emitter.onSuccess(source.get(0));
           else
               emitter.onError(new Exception("The source can only have single data"));
        }).subscribe(data->System.out.println(data),error->System.out.println(error));
    }

    private static Completable writeToDatabase(String demoState){
        /*
        completable is like Single<Void>. Use when we just want to indicate if a task is run to completion or failed.
        It is LAZY as well.
        A good example (not demonstrated here) is if we want to asynchronously perform an action on the server, we want to notify if successful or failed
         */
        return Completable.create(emitter -> {
            if (demoState.equals("pass"))
                //do somethings
                emitter.onComplete();
            else
                emitter.onError(new Throwable("task was not successfully executed"));
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
