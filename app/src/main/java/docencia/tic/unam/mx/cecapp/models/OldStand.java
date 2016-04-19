package docencia.tic.unam.mx.cecapp.models;


import com.google.gson.annotations.SerializedName;

public class OldStand {
    @SerializedName("filled")
    Boolean fill;
    int id;
    Coordenadas coordenadas;
    Color color;

    public Boolean getFill() {
        return fill;
    }
    public int getId() {
        return id;
    }
    public Coordenadas getCoordenadas() {
        return coordenadas;
    }
    public Color getColor() {
        return color;
    }

    public class Coordenadas {
        int x;
        int y;
        int w;
        int h;

        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
        public int getW() {
            return w;
        }
        public int getH() {
            return h;
        }
    }

    public class Color {
        int alpha;
        @SerializedName("R")
        byte r;
        @SerializedName("G")
        byte g;
        @SerializedName("B")
        byte b;

        public int getAlpha() {
            return alpha;
        }
        public byte getR() {
            return r;
        }
        public byte getG() {
            return g;
        }
        public byte getB() {
            return b;
        }
    }
}
