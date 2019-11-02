package main;

public class CustomPair<T1, T2> {
    T1 a;
    T2 b;

    public CustomPair(T1 aa, T2 bb){
        a = aa;
        b = bb;
    }

    public T1 getKey(){
        return a;
    }

    public T2 getValue(){
        return b;
    }
}
