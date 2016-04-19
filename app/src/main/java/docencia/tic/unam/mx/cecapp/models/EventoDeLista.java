package docencia.tic.unam.mx.cecapp.models;


import com.google.gson.annotations.SerializedName;

public class EventoDeLista {
    long id;
    String name;
    @SerializedName("started_day")
    String startDate;
    int signedUp;

    public long getId() {
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public String getStartDate(){
        return this.startDate;
    }
    public int getSignedUp() {
        return signedUp;
    }

    public boolean isSignedUp() {
        return (signedUp>0);
    }
}
