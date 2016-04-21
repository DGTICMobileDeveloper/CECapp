package docencia.tic.unam.mx.cecapp.models;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Evento {
    long id;
    String name;
    @SerializedName("started_day")
    Date startDate;
    //String subtitle;
    @SerializedName("last_day")
    Date endDate;
    //int span;
    @SerializedName("abstract")
    String description;
    String url;
    @SerializedName("fb")
    String faceBook;
    @SerializedName("tw")
    String twitter;
    @SerializedName("yt")
    String youTube;
    int duration;
    /** Lista de programas del evento (por día)
    @SerializedName("activities")
    List<Activity> activityList;*/
    @SerializedName("organizers")
    List<Organizer> organizerList;
    @SerializedName("sponsors")
    List<Sponsor> sponsorList;
    @SerializedName("subjects")
    List<Subject> subjectList;
    Map map;
    @SerializedName("program")
    List<Program> programList;


    // public constructor is necessary for collections
    public Evento() {
        organizerList = new ArrayList<Organizer>();
        sponsorList = new ArrayList<Sponsor>();
        subjectList = new ArrayList<Subject>();
        //activityList = new ArrayList<Activity>();
        programList = new ArrayList<Program>();
    }

    public long getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public Date getStartDate(){
        return this.startDate;
    }
    public Date getEndDate(){
        return this.endDate;
    }
    public String getDescription(){
        return this.description;
    }
    public String getFaceBook() {
        return this.faceBook;
    }
    public String getTwitter() {
        return this.twitter;
    }
    public String getUrl() {
        return this.url;
    }
    public String getYouTube() {
        return this.youTube;
    }
    public int getDuration(){
        return this.duration;
    }
    public List<Organizer> getOrganizerList(){
        return this.organizerList;
    }
    public List<Sponsor> getSponsorList(){
        return this.sponsorList;
    }
    public List<Subject> getSubjectList(){
        return this.subjectList;
    }
    public Map getMap(){
        return this.map;
    }
    /*public List<Activity> getActivityList(){
        return this.activityList;
    }*/
    public List<Program> getProgramList() {
        return this.programList;
    }

    public int getActivityIdByPosition(int pos){
        Program.Activity act = getActivityByPosition(pos);
        if(act != null) {
            return act.getId();
        } else {
            return -1;
        }
    }

    public Program.Activity getActivityByPosition(int pos){
        List<Program.Activity> activityList;
        Program.Activity act = null;
        int x = 1;
        for (Program program: programList) {
            activityList = program.getActivityList();
            for (Program.Activity activity: activityList) {
                if(x == pos){
                    act = activity;
                }
                x++;
            }
        }
        return act;
    }

    public String getActivityNameByPosition(int pos){
        Program.Activity act = getActivityByPosition(pos);
        if(act != null) {
            return act.getName();
        } else {
            return "";
        }
    }

    public class Organizer{
        @SerializedName("nombre")
        String name;
        @SerializedName("url")
        String link;
        @SerializedName("fb")
        String faceBook;
        @SerializedName("tw")
        String twitter;
        @SerializedName("yt")
        String youTube;
        String address;
        @SerializedName("logo")
        String logoUrl;
        @SerializedName("last_update")
        String lastUpdate;

        public String getName() {
            return this.name;
        }
        public String getLink() {
            return this.link;
        }
        public String getFaceBook() {
            return faceBook;
        }
        public String getTwitter() {
            return twitter;
        }
        public String getYouTube() {
            return youTube;
        }
        public String getAddress() {
            return address;
        }
        public String getLogoUrl() {
            return logoUrl;
        }
        public String getLastUpdate() {
            return lastUpdate;
        }
    }

    public class Sponsor{
        @SerializedName("nombre")
        String name;
        @SerializedName("url")
        String link;
        @SerializedName("fb")
        String faceBook;
        @SerializedName("tw")
        String twitter;
        @SerializedName("yb")
        String youTube;
        String address;
        @SerializedName("logo")
        String logoUrl;
        @SerializedName("last_update")
        String lastUpdate;

        public String getName() {
            return this.name;
        }
        public String getLink() {
            return this.link;
        }
        public String getFaceBook() {
            return faceBook;
        }
        public String getTwitter() {
            return twitter;
        }
        public String getYouTube() {
            return youTube;
        }
        public String getAddress() {
            return address;
        }
        public String getLogoUrl() {
            return logoUrl;
        }
        public String getLastUpdate() {
            return lastUpdate;
        }
    }

    public class Subject{
        int id;
        String name;

        public int getId() {
            return id;
        }
        public String getName() {
            return name;
        }
    }

    public class Map{
        /** El mapa
         * Se relaciona directamente con el contenido de subjectList
         */
        @SerializedName("fixedAreas")
        ConsAreas consAreas;
        @SerializedName("generalArea")
        List<ExpoArea> expoAreaList;

        public Map(){
            expoAreaList = new ArrayList<ExpoArea>();
        }

        public ConsAreas getConsAreas() {
            return this.consAreas;
        }
        public List<ExpoArea> getExpoAreaList() {
            return this.expoAreaList;
        }

        class ConsAreas{
            /** Las salas que no cambian de lugar en el CEC
             * area_1 -> sala 1
             * area_2 -> sala 2
                     *
                     *
             * area_7 -> sala 7
             * El número que pase es el tema del 'Área' (dependiendo de la posición en subjectList)
             * -1 significa que no aplica (no se usará dicha sala)
             */
            int area_1;
            int area_2;
            int area_3;
            int area_4;
            int area_5;
            int area_6;
            int area_7;

            public int getArea_1() {
                return this.area_1;
            }
            public int getArea_2() {
                return this.area_2;
            }
            public int getArea_3() {
                return this.area_3;
            }
            public int getArea_4() {
                return this.area_4;
            }
            public int getArea_5() {
                return this.area_5;
            }
            public int getArea_6() {
                return this.area_6;
            }
            public int getArea_7() {
                return this.area_7;
            }

        }
        public class ExpoArea{
            int subject;
            int id;
            @SerializedName("X")
            int x;
            @SerializedName("Y")
            int y;
            int spanX;
            int spanY;

            public int getSubject() {
                return this.subject;
            }
            public int getId() {
                return this.id;
            }
            public int getX() {
                return this.x;
            }
            public int getY() {
                return this.y;
            }
            public int getSpanX() {
                return this.spanX;
            }
            public int getSpanY() {
                return this.spanY;
            }
        }
    }

    /*public class Activity {
        int id;
        String name;
        int space;
        String type;
        @SerializedName("initial_date")
        Date startTime;
        @SerializedName("final_date")
        Date endTime;
        @SerializedName("abstract")
        String description;
        String presentation;
        @SerializedName("aditional_info")
        String extraInfo;

        public int getId() {
            return id;
        }
        public String getName() {
            return name;
        }
        public int getSpace() {
            return space;
        }
        public String getType() {
            return type;
        }
        public Date getStartTime() {
            return startTime;
        }
        public Date getEndTime() {
            return endTime;
        }
        public String getDescription() {
            return description;
        }
        public String getPresentation() {
            return presentation;
        }
        public String getExtraInfo() {
            return extraInfo;
        }
    }*/
    public class Program{
        String date;
        @SerializedName("activities")
        List<Activity> activityList;

        public Program(){
            activityList = new ArrayList<Activity>();
        }

        public String getDate() {
            return date;
        }
        public List<Activity> getActivityList() {
            return this.activityList;
        }

        public class Activity{
            int id;
            String name;
            String type;
            @SerializedName("initial_time")
            String startTime;
            @SerializedName("final_time")
            String endTime;
            @SerializedName("abstract")
            String description;
            String presentation;
            @SerializedName("aditional_info")
            String extraInfo;

            public int getId() {
                return id;
            }
            public String getName() {
                return name;
            }
            public String getType() {
                return type;
            }
            public String getStartTime() {
                return startTime;
            }
            public String getEndTime() {
                return endTime;
            }
            public String getDescription() {
                return description;
            }
            public String getPresentation() {
                return presentation;
            }
            public String getExtraInfo() {
                return extraInfo;
            }
        }
    }
}
