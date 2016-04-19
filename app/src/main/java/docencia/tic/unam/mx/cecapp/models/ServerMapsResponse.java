package docencia.tic.unam.mx.cecapp.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ServerMapsResponse {
    @SerializedName("response_code")
    int respCode;
    @SerializedName("response_msg")
    String respMsg;
    @SerializedName("response_data")
    RespData data;

    // Parseando la respuesta
    public static ServerMapsResponse parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(response, ServerMapsResponse.class);
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
        @SerializedName("maps")
        List<GeneralMap> mapList;

        // public constructor is necessary for collections
        public RespData() {
            mapList = new ArrayList<GeneralMap>();
        }

        public List<GeneralMap> getMapList() {
            return mapList;
        }
    }
}
