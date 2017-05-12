package univ.ajou.godoriapp.util;

import android.content.Context;
import android.util.Log;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;

public class HttpConnector {

    private OkHttpClient httpClient;
    private Request request;
    private String endpoint, urlpath;
    private JSONObject param;
    private Context context;
    private Call call;
    public static final String TAG = "HttpConnector";

    public HttpConnector() {
        httpClient = new OkHttpClient();
    }

    public HttpConnector(Context c) {
        httpClient = new OkHttpClient();
        context = c;
    }

    public void setUrlpath(String path) {
        urlpath = path;
    }

    public void setEndpoint(String url) {
        endpoint = url;
    }

    public void setParam(JSONObject jsonParam) {
        param = jsonParam;
    }

    public void get(final HttpCallback callback) {
        String paramUrl = "?";
        int paramSize = 0;
        int count = 0;
        if (param != null) {
            paramSize = param.length();
            Iterator<String> it = param.keys();
            while (it.hasNext()) {
                count++;
                try {
                    String key = it.next();
                    String value = param.getString(key);
                    paramUrl += key + "=" + value;
                    if (count != paramSize) {
                        paramUrl += "&";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        if (endpoint != null) {
            endpoint += paramUrl;
        }
        Log.e("HttpConnector", "Endpoint : " + endpoint);

        Request.Builder builder = new Request.Builder();
        request = builder.url(endpoint).build();

        call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                callback.onFailure(0, e.getMessage());
                Log.e("HttpConnector", "FAIL EXCEPTION : " + e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String res = response.body().string();
                Log.e("HttpConnector", "Http Response : " + res);
                callback.onResponse(0, "", res);
            }
        });
    }

    public void post(final HttpCallback callback) {
        FormEncodingBuilder formBuilder = new FormEncodingBuilder();
        String paramUrl = "";
        int paramSize = 0;
        int count = 0;
        if (param != null) {
            paramSize = param.length();
            Iterator<String> it = param.keys();
            while (it.hasNext()) {
                count++;
                if (count == 1) {
                    paramUrl += "?";
                }
                try {
                    String key = it.next();
                    String value = param.getString(key);
                    formBuilder.add(key, value);
                    paramUrl += key + "=" + value;
                    if (count != paramSize) {
                        paramUrl += "&";
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } else {
            formBuilder.add("", "");
        }
        Log.e("HttpConnector", "Endpoint : " + endpoint + paramUrl);

        Request.Builder builder = new Request.Builder();
        request = builder.url(endpoint).post(formBuilder.build()).build();

        call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            public void onFailure(Request request, IOException e) {
                callback.onFailure(0, e.getMessage());
                Log.e("HttpConnector", "FAIL EXCEPTION : " + e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String res = response.body().string();
                Log.e("HttpConnector", "Http Response : " + res);
                callback.onResponse(0, "", res);
            }
        });
    }

    final MediaType MEDIA_TYPE_GIF = MediaType.parse("image/gif");
    final MediaType MEDIA_TYPE_JPG = MediaType.parse("image/jpg");
    final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    public void postFile(final HttpCallback callback) {
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);

        if (param != null) {
            Iterator<String> it = param.keys();
            while (it.hasNext()) {
                try {
                    String key = it.next();
                    String value = param.getString(key);
                    builder.addFormDataPart(key, value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        String contentType = "";
        if (urlpath != null) {
            File sourceFile = new File(urlpath);
            try {
                contentType = sourceFile.toURI().toURL().openConnection().getContentType();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            builder.addFormDataPart("path", sourceFile.getName(), RequestBody.create(MediaType.parse(contentType), sourceFile));
        }
        RequestBody requestBody = builder.build();
        request = new Request.Builder().url(endpoint).post(requestBody).build();

        call = httpClient.newCall(request);
        call.enqueue(new Callback() {
            public void onFailure(Request request, IOException e) {
                callback.onFailure(0, e.getMessage());
                Log.e("HttpConnector", "FAIL EXCEPTION : " + e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String res = response.body().string();
                Log.e("HttpConnector", "Http Response : " + res);
                callback.onResponse(0, "", res);
            }
        });
    }

    public void cancel() {
        if (call != null) {
            call.cancel();
        }
    }
}
