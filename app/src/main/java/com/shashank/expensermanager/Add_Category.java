package com.shashank.expensermanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Add_Category extends AppCompatActivity {


    private static final String TAG = "Add_Category";

    FloatingActionButton floatingActionButtonbutton;

    ImageView imageView;
    TextView tv1;
    RecyclerView recyclerView;
    SharedPreferences preferences;
    JSONObject saved;
    Adapter adapter;

    List<UserData> Mylist = new ArrayList<UserData>();

    AlertDialog.Builder builder;

    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__category);


        floatingActionButtonbutton = findViewById(R.id.fab2);
//        imageView = findViewById(R.id.imageView);
//        tv1 = findViewById(R.id.textView2);
//        ArrayList<String> mName = new ArrayList<>();
//        mName.add("Asad");
//        mName.add("khan");
//        mName.add("Hey Pick the Call ");
//        mName.add(" Honda ");
//        mName.add(" Samsung ");
//        mName.add("Asad");
//        mName.add("khan");
//        mName.add("Hey Pick the Call ");
//        mName.add(" Honda ");
//        mName.add(" Samsung ");


        recyclerView = findViewById(R.id.job_recycler);


        ReturnData();


        preferences = getSharedPreferences("text", Context.MODE_PRIVATE);
        Log.d("Testing Add", preferences.getString("saved", ""));

        try {
            saved = new JSONObject(preferences.getString("saved", ""));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new Adapter(Mylist, getApplicationContext());

        recyclerView.setAdapter(adapter);


//        Intent intent = getIntent();
//
//
//        String text = intent.getStringExtra(Demo_Activity.EXTRA_TEXT);
//        tv1.setText(text);


//        imageView.setImageResource(getIntent().getIntExtra("imgName", 1));
//
//        tv1.setText(getIntent().getStringExtra("text"));

        floatingActionButtonbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Add_Category.this, Demo_Activity.class);

//                intent.putExtra("Myvalue", 1);

                startActivity(intent);


//                Add_Cat_Fragrament add_cat_fragrament = new Add_Cat_Fragrament();
//
//                getSupportFragmentManager().beginTransaction().replace(R.id.container1, add_cat_fragrament).commit();


            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        adapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void OnItemClick(int position, UserData userData) {
                builder = new AlertDialog.Builder(Add_Category.this);
                builder.setTitle("Update Data ");
                builder.setIcon(R.drawable.ic_baseline_add_24);
                builder.setCancelable(false);
                View view = LayoutInflater.from(Add_Category.this).inflate(R.layout.dialog_update, null, false);
                builder.setView(view);
                dialog = builder.create();
                dialog.show();
                dialog.dismiss();

            }
        });


    }


    private void ReturnData() {


        UserData userData = new UserData();


        Mylist.add(userData);
        userData.getName();


    }


    private class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

        //        ArrayList<String> mlist;
        List<UserData> list;
        ItemClickListener itemClickListener;

        Context context;

        public Adapter(List<UserData> list, Context context) {
            this.list = list;
            this.context = context;
        }


        @NonNull
        @Override
        public Adapter.Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View view = LayoutInflater.from(Add_Category.this).inflate(R.layout.new_item, viewGroup, false);
            Holder holder = new Holder(view);

//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view1) {
//                    int position = recyclerView.getChildPosition(view1);
//                    Intent intent0 = new Intent(Add_Category.this, Demo_Activity.class);
//                    intent0.putExtra("position", position);
//                    startActivity(intent0);
//                }
//            });
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull Adapter.Holder holder, @SuppressLint("RecyclerView") final int position) {


            UserData job = list.get(holder.getAdapterPosition());

            holder.textView.setText(job.getName());


            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    list.remove(position);
                    notifyDataSetChanged();
//                    adapter.notifyDataSetChanged();
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.OnItemClick(position, job);
                }
            });


            try {
                holder.textView.setText(saved.getString("saved" + position));
            } catch (JSONException e) {
                e.printStackTrace();
            }


//            holder.imageView2.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View view) {
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(Add_Category.this)
//                            .setTitle("Delete Catorgery")
//                            .setMessage("Are you want to sure Delete")
//                            .setIcon(R.drawable.ic_baseline_delete_forever_)
//                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int which) {
//                                    list.remove(position);
//                                    notifyDataSetChanged();
//                                    notifyItemRemoved(position);
//
//                                }
//                            })
//                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//
//                                }
//                            });
//                    builder.show();
//
//                    return true;
//                }
//            });


        }

        @Override
        public int getItemCount() {
            return list.size();
        }


        public void setOnItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }


        public class Holder extends RecyclerView.ViewHolder {
            TextView textView, delete;
            ImageView imageView, imageView2;

            public Holder(View itemView) {
                super(itemView);

                imageView = itemView.findViewById(R.id.image_1);
                textView = itemView.findViewById(R.id.text_mode1);
                delete = itemView.findViewById(R.id.tv_delete_item1);

            }
        }
    }


//    @Override
//    public void onBackPressed() {
//
//
//        Toolbar toolbar = findViewById(R.id.toolbar0);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//
//            }
//        });
//
//
//    }
}