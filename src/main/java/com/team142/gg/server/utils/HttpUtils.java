/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.team142.gg.server.utils;

import com.team142.gg.server.controller.PostOffice;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *
 * @author just1689
 */
public class HttpUtils {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static String post(String url, String json) throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();

    }

    public static void postSilently(String url, String json) {
        try {
            post(url, json);
        } catch (IOException ex) {
            Logger.getLogger(HttpUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public static void postSilentlyAsync(String url, String json) {
        Reporter.REPORT_THREAD_POOL.execute(() -> postSilently(url, json));

    }

}
