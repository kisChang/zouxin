package xin.kischang.zouxin.web;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author KisChang
 * @version 1.0
 */
public class ZhihuUtils {

    private static final OkHttpClient client = new OkHttpClient();

    public static final ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static String get(String url) throws IOException {
        System.out.println("Get >>" + url);
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        if (response.body() != null){
            return response.body().string();
        }else {
            return "Net Error...";
        }
    }

}
