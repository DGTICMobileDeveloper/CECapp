package docencia.tic.unam.mx.cecapp.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class ServerRegisterUserToEventResponse {
    @SerializedName("response_code")
    int respCode;
    @SerializedName("response_msg")
    String respMsg;
    @SerializedName("response_data")
    RespData data;


    public static ServerRegisterUserToEventResponse parseJSON(String response) {
        String DATE_FORMAT = "yyyy-MM-dd HH:mm";

        Gson gson = new GsonBuilder().create();
        return gson.fromJson(response, ServerRegisterUserToEventResponse.class);
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
        @SerializedName("ex")
        String msg;

        public String getMsg() {
            return msg;
        }
    }
}