package com.example.networkapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QingQiu2JieXiJsonActivity extends AppCompatActivity implements View.OnClickListener {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button sendRequest = (Button) findViewById(R.id.send_request);
        textView = (TextView) findViewById(R.id.request_text);
        sendRequest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send_request) {
            sendRequestWithOkHttp();
        }
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://news-at.zhihu.com/api/4/news/9660723")
                            .build();
                    Response response = client.newCall(request).execute();
                    String responseData = response.body().string();
                    parseJSONWithJSONObject(responseData);//调用json解析的方法
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithJSONObject(String jsonData){
        try{
//            JSONObject jsonObject = new JSONObject(jsonData);//新建json对象实例
//            JSONObject jsonObject1 = jsonObject.getJSONObject("section");
//            String name = jsonObject1.getString("id");//取得其名字的值，一般是字符串
//            showResponse(name);//设置ui变化

          //TODO 简单方法

            try{
                JSONObject jsonObject = new JSONObject(jsonData);
                JSONArray jsonObject1 = jsonObject.getJSONArray("results");
                Gson gson = new Gson();
                List<App> appList = gson.fromJson(jsonObject1.toString(),new TypeToken<List<App>>(){}.getType());
                for(App app : appList){
                    showResponse(app.get_id()+app.getTitle());
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(response);
            }
        });
    }
}
