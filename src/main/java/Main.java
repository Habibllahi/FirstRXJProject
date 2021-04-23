import io.reactivex.Observable;

public class Main {
    public static void main(String[] args){
        var disposable = Observable.just(1,2,3,45,6,7,8).subscribe(System.out::println);
    }
}
