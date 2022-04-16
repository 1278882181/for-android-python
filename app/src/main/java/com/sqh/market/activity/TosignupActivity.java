package com.sqh.market.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.chaquo.python.Kwarg;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.sqh.market.R;
import com.sqh.market.constant.Constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;




import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TosignupActivity extends AppCompatActivity {
    public Button signup;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initview();
        //handle();
    }

    public void initview(){
        signup = (Button) this.findViewById(R.id.btn_confirm1);
        initlistener();
    }

    public void initlistener() {
        signup.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                handle();
            }
        });
    }

    void initPython(){
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handle () {

                    new Thread(()-> {
                        OkHttpClient okHttpClient = new OkHttpClient();
                        String name = ((EditText) findViewById(R.id.re_username)).getText().toString();
                        String password = ((EditText) findViewById(R.id.re_password1)).getText().toString();
                        String phonenumber = ((EditText) findViewById(R.id.re_phonenumber)).getText().toString();
                        System.out.println(name);
                        System.out.println(password);
                        System.out.println(phonenumber);
                        initPython();
                        Python py = Python.getInstance();
                        PyObject obj1 = py.getModule("to_aes").callAttr("akey");
                        String akey = obj1.toJava(String.class);
                        PyObject obj2 = py.getModule("to_aes").callAttr("getresult",new Kwarg("ori", name),new Kwarg("key", akey));
                        String enname = obj2.toJava(String.class);
                        PyObject obj3 = py.getModule("to_aes").callAttr("getresult",new Kwarg("ori",password),new Kwarg("key", akey));
                        String enpassword = obj3.toJava(String.class);
                        PyObject obj4 = py.getModule("to_aes").callAttr("getresult",new Kwarg("ori", "name"),new Kwarg("key", akey));
                        String s2 = obj4.toJava(String.class);
                        PyObject obj5 = py.getModule("to_rsa").callAttr("t_result",new Kwarg("ori", akey));
                        String s1 = obj5.toJava(String.class);


                        String json = "{\n" +
                                "  \"s1\": \"" + s1 + "\",\n" +
                                "  \"name\": \"" + enname + "\",\n" +
                                "  \"password\": \"" + enpassword+ "\",\n" +
                                "  \"s2\": \"" + s2 + "\",\n"+
                                "  \"s3\": \"" + s2 + "\",\n"+
                                "  \"s4\": \"" + s2 + "\"\n"+
                                "}";
                        Request request = new Request.Builder()
                                .url(Constants.signup_url)
                                .post(RequestBody.create(MediaType.parse("application/json"),json))
                                .build();

                        try (Response response = okHttpClient.newCall(request).execute()) {
                            // List<User> users = JSONArray.parseArray(response.body().string(),User.class);
                            Looper.prepare();
                            if(Boolean.parseBoolean(response.body().string()))
                            {
                                Toast.makeText(this,"注册失败",Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(this,"注册成功",Toast.LENGTH_SHORT).show();
                            }
                            Looper.loop();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }).start();


    }







}
