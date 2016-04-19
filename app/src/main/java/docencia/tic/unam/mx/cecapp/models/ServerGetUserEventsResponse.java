package docencia.tic.unam.mx.cecapp.models;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ServerGetUserEventsResponse {
    @SerializedName("response_code")
    int respCode;
    @SerializedName("response_msg")
    String respMsg;
    @SerializedName("response_data")
    RespData data;


    public static ServerGetUserEventsResponse parseJSON(String response) {
        String DATE_FORMAT = "yyyy-MM-dd HH:mm";

        Gson gson = new GsonBuilder().create();
        return gson.fromJson(response, ServerGetUserEventsResponse.class);
    }

    public int getRespCode() {
        return this.respCode;
    }
    public String getRespMsg() {
        return this.respMsg;
    }
    public RespData getData() {
        return this.data;
    }

    public class RespData {
        @SerializedName("events")
        List<EventoDeLista> eventList;

        // public constructor is necessary for collections
        public RespData() {
            eventList = new ArrayList<EventoDeLista>();
        }

        public List<EventoDeLista> getEventList(){
            return this.eventList;
        }
    }
}
