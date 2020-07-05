package com.example.admin.activy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.R;
import com.example.admin.adapter.AdminAdapter;
import com.example.admin.model.Adminmd;
import com.example.admin.usetill.CheckConnection;
import com.example.admin.usetill.Server;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Admin extends AppCompatActivity {
    EditText tendn,pw;
    Button dn,qv;
    public String tendangnhap ="";
    public String password ="";
    public static ArrayList<Adminmd> mangadmin;
    AdminAdapter adminAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Anhxa();
        getData();
        EnventButton();
    }

    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdanadmin, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if(response != null){
                    try {
                        for(int i = 0;i<response.length();i++) {
                            JSONObject jsonObject = response.getJSONObject(i);
                            tendangnhap = jsonObject.getString("username");
                            password = jsonObject.getString("password");
                            mangadmin.add(new Adminmd(tendangnhap,password));
                            adminAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        } , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void EnventButton() {
        qv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        dn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tendn.getText().length() != 0 && pw.getText().length() != 0){
                    for (int i = 0; i<mangadmin.size();i++) {
                        if (tendn.getText().toString().trim().equals(mangadmin.get(i).username) && pw.getText().toString().trim().equals(mangadmin.get(i).password)){
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Đăng nhập thành công!");
                            Intent intent = new Intent(getApplicationContext(),QuanLySanPham.class);
                            startActivity(intent);
                            tendn.setText("");
                            pw.setText("");
                        }
                    }
                }
                else {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn vui lòng nhập đầy đủ thông tin");
                }

            }
        });
    }


    private void Anhxa() {
        tendn = findViewById(R.id.tendangnhap);
        pw=findViewById(R.id.password);
        dn = findViewById(R.id.dangnhap);
        qv = findViewById(R.id.quayve);
        mangadmin = new ArrayList<>();
        adminAdapter = new AdminAdapter(getApplicationContext(),mangadmin);
    }
}
