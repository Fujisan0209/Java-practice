enum Oparator {
    PLUS { int apply(int a, int b) { return a + b; }},
    MINUS { int apply(int a, int b) { return a - b; }},
    TIMES { int apply(int a, int b) { return a * b; }},
    DIVIDE { int apply(int a, int b) { 
        if(b == 0) throw new IllegalArgumentException("divide byi zero");
        return a / b; }};
    abstract int apply(int a, int b);
}


public class OperatorLab {
    
}
