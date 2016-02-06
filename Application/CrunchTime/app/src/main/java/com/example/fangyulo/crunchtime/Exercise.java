package com.example.fangyulo.crunchtime;

/**
 * Created by fangyulo on 1/31/16.
 */
public class Exercise {

    private int img;
    private String type;
    private String amt;
    private int unit;

    public Exercise(int img, String type, String amt, int unit) {
        super();
        this.set_img(img);
        this.set_type(type);
        this.set_amt(amt);
        this.set_unit(unit);
    }

    public int get_img() {
        return img;
    }
    public void set_img(int img) {
        this.img = img;
    }

    public String get_type() {
        return type;
    }
    public void set_type(String type) {
        this.type = type;
    }

    public String get_amt() {
        return amt;
    }
    public void set_amt(String amt) {
        this.amt = amt;
    }

    public int get_unit() {
        return unit;
    }

    public void set_unit(int unit) {
        this.unit = unit;
    }
}
