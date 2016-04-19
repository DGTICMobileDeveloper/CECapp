package docencia.tic.unam.mx.cecapp.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ServerMapResponse {
    @SerializedName("response_code")
    int respCode;
    @SerializedName("response_msg")
    String respMsg;
    @SerializedName("response_data")
    RespData data;

    // Parseando la respuesta
    public static ServerMapResponse parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(response, ServerMapResponse.class);
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
        @SerializedName("filled")
        Boolean fill;
        @SerializedName("mapa")
        List<OldStand> standList;

        // public constructor is necessary for collections
        public RespData() {
            standList = new ArrayList<OldStand>();
        }

        public Boolean getFill() {
            return fill;
        }
        public List<OldStand> getOldStandList() {
            return standList;
        }
    }
}
