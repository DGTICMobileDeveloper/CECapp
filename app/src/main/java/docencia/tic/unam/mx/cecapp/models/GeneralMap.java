package docencia.tic.unam.mx.cecapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GeneralMap {
    int id;
    String name;
    @SerializedName("permanentActivities")
    List<Stand> permanentActs;
    @SerializedName("activitiesByDate")
    List<DayStands> byDayActs;

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public List<Stand> getPermanentActs() {
        return permanentActs;
    }
    public List<DayStands> getByDayActs() {
        return byDayActs;
    }

    // Método para obtener todos los stands a dibujar (para un día especificado)
    public List<Stand> getAllStandsOnDay(String day){
        List<Stand> allStands = new ArrayList<Stand>();
        allStands.addAll(this.permanentActs);
        for(DayStands dayStands: this.byDayActs){
            if(dayStands.getDay().equals(day)){
                allStands.addAll(dayStands.getStandList());
                break;
            }
        }
        return allStands;
    }

    public class DayStands{
        String day;
        @SerializedName("locations")
        List<Stand> standList;

        // public constructor is necessary for collections
        public DayStands() {
            standList = new ArrayList<Stand>();
        }

        public String getDay() {
            return day;
        }
        public List<Stand> getStandList() {
            return standList;
        }
    }
}
