package com.example.admin.activy;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.R;
import com.example.admin.adapter.LoaispAdapter;
import com.example.admin.adapter.SanphamAdapter;
import com.example.admin.model.Giohang;
import com.example.admin.model.Loaisp;
import com.example.admin.model.Sanpham;
import com.example.admin.usetill.CheckConnection;
import com.example.admin.usetill.Server;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    NavigationView navigationView;
    ListView listView;
    DrawerLayout drawerLayout;
    ArrayList<Loaisp> mangloaisp;
    ArrayList<Sanpham> mangsanphammoinhat;
    LoaispAdapter loaispAdapter;
    SanphamAdapter sanphamAdapter;
    int id =0;
    String tenloaisph = "";
    String hinhanhloaisp = "";
    public static ArrayList<Giohang> manggiohang;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Anhxa();
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            ActionBar();
            getDuLieuLoaisp();
        }else {
            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manugiohang,menu);
        return true;
    }



    private void getDuLieuLoaisp() {
        final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdanloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i =0;i<response.length();i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        id = jsonObject.getInt("id");
                        tenloaisph = jsonObject.getString("tenloaisp");
                        hinhanhloaisp = jsonObject.getString("hinhloaisp");
                        mangloaisp.add(new Loaisp(id,tenloaisph,hinhanhloaisp));
                        loaispAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                mangloaisp.add(new Loaisp(0,"Liên Hệ","https://www.pinclipart.com/picdir/big/366-3660822_contact-us-lyca-mobile-contact-clipart.png"));
                mangloaisp.add(new Loaisp(0,"Thông Tin","https://www.pinclipart.com/picdir/big/66-662706_online-sources-sources-information-icon-clipart.png"));
                mangloaisp.add(new Loaisp(0,"Các Đơn Hàng Của Bạn","https://www.pinclipart.com/picdir/big/19-190811_customer-order-orders-icon-clipart.png"));
                mangloaisp.add(new Loaisp(0,"Admin","https://www.pinclipart.com/picdir/big/199-1997885_software-development-clipart-system-admin-admin-icon-png.png"));
                loaispAdapter.notifyDataSetChanged();
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonArrayRequest);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void ActionBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Shop PandC");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                drawerLayout.openDrawer(GravityCompat.START);}
        });
    }
    private void Anhxa(){
        mangloaisp = new ArrayList<>();
        mangloaisp.add(new Loaisp(0,"Trang Chính","https://www.pinclipart.com/picdir/big/350-3503267_online-shopping-at-eze-bazaar-flat-home-icon.png"));
        loaispAdapter = new LoaispAdapter(mangloaisp,getApplicationContext());
        listView.setAdapter(loaispAdapter);
        mangsanphammoinhat = new ArrayList<>();
        sanphamAdapter = new SanphamAdapter(getApplicationContext(),mangsanphammoinhat);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(sanphamAdapter);
        if(manggiohang != null){}else { manggiohang = new ArrayList<>();}
    }
}
