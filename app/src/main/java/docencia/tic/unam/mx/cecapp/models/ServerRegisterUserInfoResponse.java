package docencia.tic.unam.mx.cecapp.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class ServerRegisterUserInfoResponse {
    @SerializedName("response_code")
    int respCode;
    @SerializedName("response_msg")
    String respMsg;
    @SerializedName("response_data")
    RespData data;


    public static ServerRegisterUserInfoResponse parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(response, ServerRegisterUserInfoResponse.class);
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
        long id;

        public long getId() {
            return id;
        }
    }
}