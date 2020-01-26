package org.techtown.orderapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button login_button, join_button;
    private String mJsonString;
    private Boolean idResult=false;
    private Boolean pwResult=false;
    public static String ID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_button =(Button)findViewById(R.id.login);
        join_button=(Button)findViewById(R.id.join);
        final EditText editText_id=findViewById(R.id.login_id);
        final EditText editText_pw=findViewById(R.id.login_pw);

        login_button.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {



                String id=editText_id.getText().toString();
                String pw=editText_pw.getText().toString();

                GetData task=new GetData();
                AlertDialog.Builder Dialog= new AlertDialog.Builder(MainActivity.this);

                Dialog.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

                if(id.isEmpty()) {
                    Dialog.setMessage("아이디를 입력해주세요")
                            .show();

                }else if(pw.isEmpty()){
                    Dialog.setMessage("비밀번호를 입력해주세요")
                            .show();
                }else{
                    task.execute(id,pw);
                }


            }
        });


        join_button.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getApplicationContext(),JoinActivity.class);
                startActivity(intent);
            }
        });
    }


    class GetData extends AsyncTask<String,Void,String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {

            progressDialog=ProgressDialog.show(MainActivity.this,"기다려주세요",null,true,true);
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            mJsonString=s;

            try {
                JSONObject jsonObject = new JSONObject(mJsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("root");
                JSONObject item=jsonArray.getJSONObject(0);

                idResult = Boolean.parseBoolean(item.getString("idResult"));
                pwResult = Boolean.parseBoolean(item.getString("pwResult"));
                ID=item.getString("ID");
            }catch (Exception e){

            }

            if(idResult && pwResult){
                EditText editText_id=findViewById(R.id.login_id);
                Intent intent= new Intent(getApplicationContext(),HomeActivity.class);
//                HomeFragment homeFragment= new HomeFragment();
//                Bundle bundle= new Bundle(1);
//                bundle.putString("id",editText_id.getText().toString());
//                homeFragment.setArguments(bundle);
                startActivity(intent);
            } else{
                AlertDialog.Builder dialog= new AlertDialog.Builder(MainActivity.this);
                if(!idResult){
                    dialog.setMessage("아이디가 존재하지 않습니다");
                } else{
                    dialog.setMessage("비밀번호가 틀렸습니다.");
                }
                dialog.setPositiveButton("확인", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }) .show();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            String id=strings[0];
            String password=strings[1];
            String serverURL="http://52.78.91.73/order/login.php";
            String postParameters = "id="+id +"&password=" +password;
            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection =(HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();

                OutputStream outputStream=httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();

                int responseStatusCode= httpURLConnection.getResponseCode();
                //Log.d(TAG,"response code - "+responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode==HttpURLConnection.HTTP_OK){
                    inputStream=httpURLConnection.getInputStream();
                }
                else{
                    inputStream=httpURLConnection.getErrorStream();
                }

                InputStreamReader inputStreamReader=new InputStreamReader(inputStream,"UTF-8");
                BufferedReader bufferedReader=new BufferedReader(inputStreamReader);

                StringBuilder sb=new StringBuilder();
                String line=null;

                while((line = bufferedReader.readLine()) !=null){
                    sb.append(line);
                }

                bufferedReader.close();
                return sb.toString().trim();


            }catch(Exception e){
                return null;
            }
        }
    }


}