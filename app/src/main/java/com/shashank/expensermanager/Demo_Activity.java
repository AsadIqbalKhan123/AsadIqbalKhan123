package com.shashank.expensermanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Demo_Activity extends AppCompatActivity {


    private static final String TAG = "Demo_Activity";

    EditText et_name, et_email, et_update_name, et_update_email;
    Button add, btn_update, btn_cancel;
    RecyclerView recyclerView;
    MyAdapter adapter;

    List<UserData> list = new ArrayList<>();


    AlertDialog.Builder builder;

    AlertDialog dialog;

    String name;
    int imageView1;

    Button button;

    JSONObject saved = new JSONObject();
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_);

        et_name = (EditText) findViewById(R.id.et_name);


        add = findViewById(R.id.btn_add);

        recyclerView = findViewById(R.id.recycler_view);

        init();
        Intent intent = getIntent();
        if (intent.getIntExtra("postion", -1) != -1) {
            try {
                String s = et_name.getText().toString();
                if (!preferences.getString("saved", "").equals(""))
                    saved = new JSONObject(preferences.getString("saved", ""));
                et_name.setText(saved.getString("saved" + intent.getIntExtra("postion", 0)));
                s = saved.getString("saved" + intent.getIntExtra("postion", 0));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        button = findViewById(R.id.Save_btn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = et_name.getText().toString();

                if (!s.equals("")) {
                    try {
                        if (!preferences.getString("saved", "").equals(""))
                            saved = new JSONObject(preferences.getString("saved", ""));

                        saved.put("saved" + saved.length(), s);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.d("testing", saved + "");
                    editor.putString("saved", saved.toString());
                    editor.apply();
                    et_name.setText("");
                    Intent intent1 = new Intent(Demo_Activity.this, Add_Category.class);
                    startActivity(intent1);
                    Toast.makeText(Demo_Activity.this, "Data is Added", Toast.LENGTH_SHORT).show();
                }
            }
        });


//        button = findViewById(R.id.bid);

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Intent intent = new Intent(Demo_Activity.this, Proposition_Activity.class);
//                intent.putExtra("Myvalue", currentJob);
//
//                startActivity(intent);
//
//            }
//        });

//        list = (List<UserData>) getIntent().getExtras().getSerializable(String.valueOf(1));

        Toolbar toolbar = findViewById(R.id.toolbar00);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyAdapter(list, getApplicationContext());
        recyclerView.setAdapter(adapter);


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                openAct();

                perforLogin();


            }

//            private void openAct() {
//
//                Intent intent = new Intent(Demo_Activity.this, Add_Category.class);
//                intent.putExtra(EXTRA_TEXT, name);
//
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//
//            }
        });

        adapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void OnItemClick(int position, UserData userData) {
                builder = new AlertDialog.Builder(Demo_Activity.this);
                builder.setTitle("Update Data ");
                builder.setIcon(R.drawable.ic_baseline_add_24);
                builder.setCancelable(false);
                View view = LayoutInflater.from(Demo_Activity.this).inflate(R.layout.dialog_update, null, false);
                InitUpdateDialog(position, view);
                builder.setView(view);
                dialog = builder.create();
                dialog.show();

            }
        });


    }

    private void init() {

        preferences = getSharedPreferences("text", Context.MODE_PRIVATE);
        editor = preferences.edit();
        et_update_name = findViewById(R.id.et_update_name);


    }


    private void InitUpdateDialog(final int position, View view) {

        et_update_name = view.findViewById(R.id.et_update_name);
        btn_update = view.findViewById(R.id.btn_update_user);
        btn_cancel = view.findViewById(R.id.btn_update_cancel);

        et_update_name.setText(name);


        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                name = et_update_name.getText().toString();


                UserData userData = new UserData();

                userData.setName(name);
                userData.setImgName(imageView1);
//                userData.setImgName();

                adapter.UpdateData(position, userData);
                Toast.makeText(Demo_Activity.this, "User Updated..", Toast.LENGTH_SHORT).show();
                dialog.dismiss();


            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void perforLogin() {

        String name = et_name.getText().toString();


        if (name.isEmpty()) {
            et_name.setError("Enter the Correct the Email");
            et_name.requestFocus();

        }

        UserData userData = new UserData();

        userData.setName(name);


        list.add(userData);
        adapter.notifyDataSetChanged();
        Toast.makeText(Demo_Activity.this, "Add Successfully item...", Toast.LENGTH_SHORT).show();

        et_name.setText("");

    }
}