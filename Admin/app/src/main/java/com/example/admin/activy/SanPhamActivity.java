package com.example.admin.activy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.R;
import com.example.admin.adapter.TatCaSanPhamAdapter;
import com.example.admin.model.Sanpham;
import com.example.admin.usetill.CheckConnection;
import com.example.admin.usetill.Server;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SanPhamActivity extends AppCompatActivity {

    Toolbar toolbartcsp;
    ListView listViewtcsp;
    public static TatCaSanPhamAdapter tatCaSanPhamAdapter;
    public static ArrayList<Sanpham> mangtcsp;
    int page = 1;
    View footerview;
    myHandler myhandler;
    boolean isloading = false;
    boolean limitdata = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);
        Anhxa();
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            ActionToolBar();
            getData(page);
            Loadmoredata();
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra kết nối");
        }
    }


    private void Loadmoredata() {
//        listViewtcsp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent intent = new Intent(getApplicationContext(),ChiTietSanPham.class);
//                intent.putExtra("thongtinsanpham",mangtcsp.get(i));
//                startActivity(intent);
//            }
//        });
        listViewtcsp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
                final AlertDialog.Builder buidlder = new AlertDialog.Builder(SanPhamActivity.this);
                buidlder.setMessage("Bạn muốn thay đổi điều gì về sản phẩm này!!");
                buidlder.setIcon(android.R.drawable.ic_dialog_info);
                buidlder.setTitle("Thông tin");
                buidlder.setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        tatCaSanPhamAdapter.notifyDataSetChanged();
                    }
                });
                buidlder.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        final Sanpham sp = mangtcsp.get(i);
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanxoasanpham, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                HashMap<String,String> hashMap = new HashMap<>();
                                hashMap.put("id",Integer.toString(sp.getID()));
                                return hashMap;
                            }
                        };
                        requestQueue.add(stringRequest);
                        finish();
                        startActivity(getIntent());
                    }
                });
                buidlder.setNeutralButton("Sửa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int j) {
                        Intent intent = new Intent(getApplicationContext(),SuaSanPham.class);
                        intent.putExtra("thongtinsanphamsua",mangtcsp.get(i));
                        startActivity(intent);
                    }
                });
                AlertDialog alertDialog = buidlder.create();
                alertDialog.show();
            }
        });
        listViewtcsp.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItem, int totalItem) {
                if (firstItem + visibleItem == totalItem && totalItem != 0 && isloading == false && limitdata == false) {
                    isloading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }

            }
        });
    }

    private void getData(int Page) {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdantatcasanpham + String.valueOf(Page), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                int ID = 0;
                String Tensanpham = "";
                Integer Giasanpham = 0;
                String Hinhanhsanpham = "";
                String Motasanpham = "";
                int IDloaisanpham = 0;
                if (requestQueue != null) {
                    listViewtcsp.removeFooterView(footerview);
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            Tensanpham = jsonObject.getString("tensanpham");
                            Giasanpham = jsonObject.getInt("giasanpham");
                            Hinhanhsanpham = jsonObject.getString("hinhanhsanpham");
                            Motasanpham = jsonObject.getString("motasanpham");
                            IDloaisanpham = jsonObject.getInt("idloaisanpham");
                            mangtcsp.add(new Sanpham(ID, Tensanpham, Giasanpham, Hinhanhsanpham, Motasanpham, IDloaisanpham));
                            tatCaSanPhamAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                } else {
                    limitdata = true;
                    listViewtcsp.removeFooterView(footerview);
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Đã hết dữ liệu");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonArrayRequest);

    }

    private void ActionToolBar() {
        setSupportActionBar(toolbartcsp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbartcsp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbartcsp = findViewById(R.id.toolbartatcasanpham);
        listViewtcsp = findViewById(R.id.listviewtatcasanpham);
        mangtcsp = new ArrayList<>();
        tatCaSanPhamAdapter = new TatCaSanPhamAdapter(SanPhamActivity.this, mangtcsp);
        listViewtcsp.setAdapter(tatCaSanPhamAdapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.processbar, null);
        myhandler = new myHandler();
    }

    public class myHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0:
                    listViewtcsp.addFooterView(footerview);
                    break;
                case 1:
                    getData(++page);
                    isloading = false;
                    break;
            }
            super.handleMessage(msg);
        }
    }

    public class ThreadData extends Thread {
        @Override
        public void run() {
            myhandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = myhandler.obtainMessage(1);
            myhandler.sendMessage(message);
            super.run();
        }
    }
}
