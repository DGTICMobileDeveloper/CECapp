package docencia.tic.unam.mx.cecapp.auxiliares;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.util.List;

import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class AuxAsyncHttp extends RequestParams {
//    BasicNameValuePair basicNameValuePair;
    RequestParams requestParams;
    public AuxAsyncHttp(RequestParams rp){
        this.requestParams = rp;
    }


    public String getParamString1(RequestParams req){
        return this.getParamString();
    }

    public String getParamString2(AuxAsyncHttp req){
        return req.getParamString();
    }

    public List<BasicNameValuePair> getParamsList1(){
        return this.getParamsList();
    }
    public List<BasicNameValuePair> getParamsList1(AuxAsyncHttp req){
        return req.getParamsList();
    }
}
