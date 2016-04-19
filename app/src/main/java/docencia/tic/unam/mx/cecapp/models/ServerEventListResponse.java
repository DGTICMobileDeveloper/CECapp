package docencia.tic.unam.mx.cecapp.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class ServerEventListResponse {
    /**el Json manda { "events": [ ...]} pero por tenerlo claro llamo a la lista de eventos eventList
     * haciendo el @SerializedName("events") 'iguala' eventList->events
     */
    @SerializedName("response_code")
    int respCode;
    @SerializedName("response_msg")
    String respMsg;
    @SerializedName("response_data")
    RespData data;

    //Parseando la respuesta
    public static ServerEventListResponse parseJSON(String response) {
        String DATE_FORMAT = "yyyy-MM-dd HH:mm";

        Gson gson = new GsonBuilder().create();
        ServerEventListResponse serverEventListResponse = gson.fromJson(response, ServerEventListResponse.class);
        /**Lo pongo así para tenerlo más claro, sin embargo podría reducirse a:
         * return gson.fromJson(response, ServerEventListResponse.class);*/
        return serverEventListResponse;
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
        @SerializedName("current_page")
        int page;
        @SerializedName("current_")
        int pageCount;
        @SerializedName("total_events")
        int totalEvents;
        @SerializedName("events")
        List<EventoDeLista> eventList;

        // public constructor is necessary for collections
        public RespData() {
            eventList = new ArrayList<EventoDeLista>();
        }

        public int getPage() {
            return this.page;
        }
        public int getPageCount() {
            return this.pageCount;
        }
        public int getTotalEvents() {
            return this.totalEvents;
        }
        public List<EventoDeLista> getEventList(){
            return this.eventList;
        }
    }
}
