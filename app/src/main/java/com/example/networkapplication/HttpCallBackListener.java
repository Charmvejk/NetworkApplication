package com.example.networkapplication;

public interface HttpCallBackListener {
    void onFinish(String response);
    void onError(Exception e);
}
