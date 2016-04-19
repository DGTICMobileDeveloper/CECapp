package docencia.tic.unam.mx.cecapp.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


public class ServerInterestListResponse {
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
    public static ServerInterestListResponse parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(response, ServerInterestListResponse.class);
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
        @SerializedName("subjects")
        List<ItemListaInteres> subjectList;

        // public constructor is necessary for collections
        public RespData() {
            subjectList = new ArrayList<ItemListaInteres>();
        }

        public List<ItemListaInteres> getSubjectList() {
            return subjectList;
        }
    }
}