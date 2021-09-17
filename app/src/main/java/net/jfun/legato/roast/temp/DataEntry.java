package net.jfun.legato.roast.temp;

import java.io.Serializable;

public class DataEntry implements Serializable {

    private float x;
    private float y;

    public DataEntry(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }
}
