package com.example.json0302;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private TextView mTextView;
    private String jsonStr;
    private boolean IsClicked = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        btn1 = (Button) findViewById(R.id.jsonZuZhuang);
        btn2 = (Button) findViewById(R.id.jsonJieXijs);
        btn3 = (Button) findViewById(R.id.gsonJieXijs);
        mTextView = (TextView) findViewById(R.id.textView);

        Student s1 = new Student(1001,"李斯",21);
        Student s2 = new Student(1002,"韩寒",20);
        Student s3 = new Student(1003,"张翰",24);

        final Student[] stus = {s1,s2,s3};

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JSONArray array = new JSONArray();
                for(int i=0;i<stus.length;i++){
                    JSONObject stuObj = getStudentJsonObj(stus[i]);
                    array.put(stuObj);
                }
                JSONObject obj = new JSONObject();
                try {
                    //通过调用JSONObject类的put(Object key, Object value)方法，可以将一个Object对象以键值对的形式存入JSONObject对象
                    obj.put("stuList",array);

                } catch (JSONException e) {
                    Log.i("MainActivity",e.toString());
                }

                jsonStr = obj.toString();//通过调用JSONObject类的toString()方法，则可以将JSONObject对象转化为JSON数据。
                mTextView.setText(jsonStr);
                IsClicked  = true;

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(IsClicked){
                    try {
                        JSONObject obj = new JSONObject(jsonStr);
                        JSONArray array = obj.getJSONArray("stuList");
                        ArrayList<Student> stuList = new ArrayList<Student>();
                        for(int i=0;i<array.length();i++){
                            JSONObject stuObj = array.getJSONObject(i);
                            int id = stuObj.getInt("id");
                            String name = stuObj.getString("name");
                            int age = stuObj.getInt("age");
                            Student s = new Student(id,name,age);
                            stuList.add(s);
                        }

                    } catch (JSONException e) {
                        Log.i("MainActivity",e.toString());
                    }


                }


            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String jsonStr = "{\"id\":1005,\"name\":\"林曼丽\",\"age\":21}";
                Gson gson = new Gson();
                Student s = gson.fromJson(jsonStr,Student.class);
                mTextView.setText(s.getId()+" "+s.getName()+" "+s.getAge());

            }
        });


    }

    private JSONObject getStudentJsonObj(Student s){
        JSONObject obj = new JSONObject();
        try {
            obj.put("id",s.getId());
            obj.put("name",s.getName());
            obj.put("age",s.getAge());

        } catch (JSONException e) {
            Log.i("MainActivity",e.toString());

        }
        return obj;

    }
}
