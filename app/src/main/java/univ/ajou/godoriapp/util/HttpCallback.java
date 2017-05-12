package univ.ajou.godoriapp.util;

public interface HttpCallback {
    void onFailure(int code, String msg);
    void onResponse(int code, String msg, String data);
}
