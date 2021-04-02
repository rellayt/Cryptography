package pl.mainpackage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BBS {
    private long p, q, N, x;
    public List<Long> generatedNumbersArray = new ArrayList<>();
    public List<Integer> generatedBitsArray = new ArrayList<>();
    BBS(){
        this.p = 107;
        this.q = 127;
        this.x = 12;
        this.N = p * q;
    }

    BBS(long p, long q, long x){
        this.setParameters(p, q, x);
    }

    void setParameters(long p, long q, long x){
        this.p = p;
        this.q = q;
        this.x = x;
        this.N = p * q;
    }

    void generateRandomValues(int length){
        for(int i = 0; i < length; i++){
            this.x = (this.x * this.x) % this.N;
            this.generatedNumbersArray.add(this.x);
        }
        generatedBitsArray = generatedNumbersArray.stream()
                .map(number -> (int)(number % 2))
                .collect(Collectors.toList());
    }


}
