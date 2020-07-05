package com.example.admin.activy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.adapter.AdminAdapter;
import com.example.admin.R;
import com.example.admin.adapter.AdminAdapter;
import com.example.admin.usetill.CheckConnection;
import com.example.admin.usetill.Server;

import java.util.HashMap;
import java.util.Map;

public class QuanLyAdmin extends AppCompatActivity {
    EditText  tendn,ps,psc;
    Button btnxn,btnhuy;
    public String tendangnhap ="";
    public String password ="";
    AdminAdapter adminAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_admin);
        tendn = findViewById(R.id.edttdn);
        ps = findViewById(R.id.editps);
        psc = findViewById(R.id.editpsll);
        btnxn = findViewById(R.id.btnxnad);
        btnhuy = findViewById(R.id.btnhuyad);
        btnhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnxn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tendn.getText().length() != 0 && ps.getText().length() != 0){
                    for (int i = 0; i<Admin.mangadmin.size();i++) {
                        if (tendn.getText().toString().trim().equals(Admin.mangadmin.get(i).username) && ps.getText().toString().trim().equals(Admin.mangadmin.get(i).password)){
                            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdandoipstaikhoan, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn đã đổi Password thành công!");
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }){
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    HashMap<String,String> hashMap = new HashMap<>();
                                    hashMap.put("password",psc.getText().toString());
                                    hashMap.put("tendn",tendn.getText().toString());
                                    return hashMap;
                                }
                            };
                            requestQueue.add(stringRequest);
                        }
                        else {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Tài khoản bạn vừa nhập không đúng!");
                        }
                    }
                }
                else {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn vui lòng nhập đầy đủ thông tin");
                }
            }
        });

    }
}
