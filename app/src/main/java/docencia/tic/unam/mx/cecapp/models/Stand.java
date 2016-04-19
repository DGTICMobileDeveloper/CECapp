package docencia.tic.unam.mx.cecapp.models;


import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Stand {
    int id;
    Boundaries boundaries;
    //RgbColor rgbColor;
    int[] rgbColor;
    SimpleActivity activity;
    @SerializedName("activities")
    List<SimpleActivity> activityList;

    // public constructor is necessary for collections
    public Stand() {
        activityList = new ArrayList<SimpleActivity>();
    }

    public int getId() {
        return id;
    }
    public Boundaries getBoundaries() {
        return boundaries;
    }
    public int[] getRgbColor() {
        return rgbColor;
    }
    public SimpleActivity getActivity() {
        return activity;
    }
    public List<SimpleActivity> getActivityList() {
        return activityList;
    }
    //    public RgbColor getRgbColor() {
//        return rgbColor;
//    }

    public class Boundaries {
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

    public class RgbColor {
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

    public class SimpleActivity{
        int id;
        String name;
        String schedule;

        public int getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public String getSchedule() {
            return schedule;
        }
    }
}
