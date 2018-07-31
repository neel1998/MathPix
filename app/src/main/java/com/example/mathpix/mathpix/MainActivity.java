package com.example.mathpix.mathpix;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class MainActivity extends AppCompatActivity implements OnClickListener {

    Button btn;
    Button graph_btn;
    TextView textView;
    ImageView imageView;
    ProgressBar prog;
    Bitmap bm;
    String final_eq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn=(Button)findViewById(R.id.btn);
        graph_btn=(Button) findViewById(R.id.graph_btn);
        textView=(TextView)findViewById(R.id.text);
        imageView=(ImageView)findViewById(R.id.img);
        prog=(ProgressBar)findViewById(R.id.prog);

        btn.setOnClickListener(this);
        prog.setVisibility(View.GONE);
    }
    public void getGraph(View view){
        Intent i=new Intent(this,graph.class);
        i.putExtra("final_eq",final_eq);
        i.putExtra("temp_eq","5x +   14y= 23");
        startActivity(i);
    }

    @Override
    public void onClick(View view) {
        Intent i=new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i,1);
    }
    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        if(requestCode==1 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            Uri uri=data.getData();
            try {
                bm= MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
            } catch (IOException e) {}
            imageView.setImageBitmap(bm);

            prog.setVisibility(View.VISIBLE);
            MathTask mathTask=new MathTask();
            mathTask.execute();
        }
    }
    public class MathTask extends AsyncTask<String,Void,String>{


        @Override
        protected String doInBackground(String... string) {


            String s = "";
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            bm.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            byte[] bytes=byteArrayOutputStream.toByteArray();
            String eImg= android.util.Base64.encodeToString(bytes, android.util.Base64.DEFAULT);

            OkHttpClient client = new OkHttpClient();

            createRequest re=new createRequest(eImg);

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, new Gson().toJson(re));

            Request request = new Request.Builder()
                    .url("https://api.mathpix.com/v3/latex")
                    .addHeader("content-type", "application/json")
                    .addHeader("app_id", "neellovepainting_gmail_com")
                    .addHeader("app_key", "07fa866d34c765947ff7")
                    .post(body)
                    .build();
                try {
                    Response response = client.newCall(request).execute();
                    Log.d("response:",""+response);

                    ResponseBody responseBody=response.body();

                    String jsondata=responseBody.string();

                    JSONObject jsonObject=new JSONObject(jsondata);

                    Log.d("latex",""+jsonObject.getString("latex"));

                    s = jsonObject.getString("latex");
                    final_eq=s;
                } catch (IOException e) {
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            return s;

        }
        @Override
        protected void onPostExecute(String result)
        {
            textView.setText(result);

            prog.setVisibility(View.GONE);
        }
    }
}

