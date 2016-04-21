package docencia.tic.unam.mx.cecapp.models;


import com.google.gson.Gson;
        import com.google.gson.GsonBuilder;
        import com.google.gson.annotations.SerializedName;

        import java.util.ArrayList;
        import java.util.List;

public class ServerSingleMapResponse {
    @SerializedName("response_code")
    int respCode;
    @SerializedName("response_msg")
    String respMsg;
    @SerializedName("response_data")
    RespData data;

    // Parseando la respuesta
    public static ServerSingleMapResponse parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(response, ServerSingleMapResponse.class);
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
        int map;
        @SerializedName("location")
        Stand stand;

        public int getMap() {
            return map;
        }
        public Stand getStand() {
            return stand;
        }
    }
}