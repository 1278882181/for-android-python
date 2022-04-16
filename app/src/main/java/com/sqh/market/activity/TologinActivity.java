package com.sqh.market.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.chaquo.python.Kwarg;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.sqh.market.MyApplication;
import com.sqh.market.R;
import com.sqh.market.constant.Constants;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TologinActivity extends AppCompatActivity {

    public Button login;
    public Button sign;
    private static String publickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC0+iOBxTOu8+JD4NFLSveF81UBl+LIFg8fujKS\n" +
            "jLdq6H0hJzxE04vrIBNC8l1n0a9/3vZYDEh2vVpSOxgMB+NnJ9MA4fnFyN9gt544ffJtGHxlX2\n" +
            "cMzHsjvjbediQVQ/DwhI3LxeUccNrVYvRifAJFXdPyCCbcFIZEL3Le7aoQIDAQAB";
    private static String privatekey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALT6I4HFM67z4kPg0UtK94XzVQGX\n" +
            "4sgWDx+6MpKMt2rofSEnPETTi+sgE0LyXWfRr3/e9lgMSHa9WlI7GAwH42cn0wDh+cX+/I32C3nj\n" +
            "h98m0YfGVfZwzMeyO+Nt52JBVD8PCEjcvF5Rxw2tVi9GJ8AkVd0/IIJtwUhkQvct7tqhAgMBAAEC\n" +
            "gYBWc+PAWz8MRW8CNARnLmG9+HbUMwVfxcHlyyHbhiMcjAEydDbDPjdp08G26eZ2XvR/7Aasg\n" +
            "JeZev+IF5ULItmwOE24ejRgDn9WiuptIikPyB2PHjDNbHj01rowZqgYaAYgZAcbAufDnAH18W9Y9\n" +
            "lpiKWuCVt+4U3saEEEEmXQJBAPFTWeuwa4jEW8XoyGGA0U8piRsod0tOozsY6jFFn4PAnuVLXgjx\n" +
            "//82kA8zWq6jG580bmXs9TxMXJT34VSbWRMCQQC/+1v3TpyDuTM2HngrEGx+8GaF6HuKX82HqOo1\n" +
            "l9tFUXTYBgQajH0DfKMfb7I+10bcbZhDD/nxt82a0Vq59Qf7AkBZN8yFsyp+XMHlECOM0JgRYTS8\n" +
            "IqC/6am1PofOgYPwrLTR5qQ0QGu5Sc3GoODEsa6TlEnASvN7QGv18xKOOgSbAkBu72KPH3/Vpfjv\n" +
            "tt+dktn+6A92JYbtyLpNP9BGs79i6NTp12Abrz61+xmPms2W/nLWAQZRCjRV9u36Hiuc9PhtAkEA\n" +
            "v1GnYW37fuqhS/VYRD30WiXQ4a1AGHNpsdbHEd0qWoGtWDfuuxVxB3pf6LFbea6RpsNvOjJqdHC1\n" +
            "L+KbNA9UbQ==";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_1);
        initview();
    }

    public void initview(){
        login = (Button) this.findViewById(R.id.btn_confirm);
        sign = (Button) this.findViewById(R.id.btn_register);

        initlistener();
    }

    public void initlistener() {
        login.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                handle();
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TologinActivity.this,TosignupActivity.class);
                startActivity(intent);
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

        login.setOnClickListener(
                v ->
                {
                    new Thread(()-> {
                        OkHttpClient okHttpClient = new OkHttpClient();
                        String name = ((EditText) findViewById(R.id.et_username)).getText().toString();
                        String password = ((EditText) findViewById(R.id.et_password)).getText().toString();
                        try {
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
                                    .url(Constants.login_url)
                                    .post(RequestBody.create(MediaType.parse("application/json"),json))
                                    .build();
                            try (Response response = okHttpClient.newCall(request).execute()) {
                                // List<User> users = JSONArray.parseArray(response.body().string(),User.class);
                                Looper.prepare();
                                if(Boolean.parseBoolean(response.body().string()))
                                {
                                    Toast.makeText(this,"登录失败",Toast.LENGTH_SHORT).show();
                                    MyApplication myApplication = (MyApplication) this.getApplicationContext();
                                    myApplication.setNumber(1);
                                }
                                else
                                {
                                    Toast.makeText(this,"登录成功",Toast.LENGTH_SHORT).show();
                                }
                                Looper.loop();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }




                    }).start();
                }
        );

    }
}
