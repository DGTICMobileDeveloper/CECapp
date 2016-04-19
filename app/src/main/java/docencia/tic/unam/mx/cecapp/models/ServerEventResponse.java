package docencia.tic.unam.mx.cecapp.models;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.zip.DataFormatException;


public class ServerEventResponse {
    @SerializedName("response_code")
    int respCode;
    @SerializedName("response_msg")
    String respMsg;
    @SerializedName("response_data")
    Evento event;


    //Parseando la respuesta
    public static ServerEventResponse parseJSON(String response) {
        String DATE_FORMAT = "yyyy-MM-dd HH:mm";

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setDateFormat(DATE_FORMAT);
        //gsonBuilder.registerTypeAdapter(Date.class, new DateTypeAdapter());
        Gson gson = gsonBuilder.create();
        return gson.fromJson(response, ServerEventResponse.class);
    }

    public int getRespCode() {
        return respCode;
    }
    public String getRespMsg() {
        return respMsg;
    }
    public Evento getEvent(){
        return this.event;
    }

    /*
    private static class DateTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
        private final DateFormat dateFormat;
        private final DateFormat dateFormatHour;

        private DateTypeAdapter() {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            dateFormat.setTimeZone(TimeZone.getDefault());
            dateFormatHour = new SimpleDateFormat("HH:mm", Locale.getDefault());
            dateFormat.setTimeZone(TimeZone.getDefault());
        }

        @Override public synchronized JsonElement serialize(Date date, Type type,
                                                            JsonSerializationContext jsonSerializationContext) {
            return new JsonPrimitive(dateFormat.format(date));
        }

        @Override public synchronized Date deserialize(JsonElement jsonElement, Type type,
                                                       JsonDeserializationContext jsonDeserializationContext) {
            try {
                return dateFormat.parse(jsonElement.getAsString());
            } catch (ParseException e) {
                throw new JsonParseException(e);
            }
        }
    }*/
}
