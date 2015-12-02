package com.packageHesab;

/**
 * Created by omid on 11/13/15.
 */
public abstract class Seporde{
    Double Ir;
    Double Db;
    Double Dd;
    Double Pi;
    public Double returnPi(){
        Pi= (Dd*Db*Ir)/36500;
        return Pi;
    }
    Seporde(Double x, Double y, double z){
        this.Dd= x; this.Db=y; this.Ir= z;
    }
}

class Qarz extends Seporde{
    public Qarz(Double x,Double y){
        super(x,y, (double) 0);
    }
}

class ShortTerm extends Seporde{
    public ShortTerm(Double x, Double y ){
        super(x,y, (double) 10);
    }
}
class LongTerm extends Seporde {
    public LongTerm(Double x, Double y) {
        super(x, y, (double) 20);
    }
}
class Hesab{
    private int customernum;
    private Double pi;

    Hesab(int x1, Double returned) {
        this.customernum=x1; this.pi=returned;
    }
    public int getCN() {
        return customernum;
    }
    public Double getPi() {
        return pi;
    }
}
