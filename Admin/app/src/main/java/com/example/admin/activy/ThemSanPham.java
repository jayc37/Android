package com.example.admin.activy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.admin.R;
import com.example.admin.usetill.CheckConnection;
import com.example.admin.usetill.Server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ThemSanPham extends AppCompatActivity implements View.OnClickListener {
    EditText edttsp,giasp,motasp;
    Spinner spinnertsp;
    Button btnthem,btntrove,btnchoose;
    ImageView imageView;
    Bitmap bitmap;

    final int IMG_REQUEST=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_san_pham);
        Anhxa();
        CatchEventSpinner();
        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ThemSanPham.this, QuanLySanPham.class);
                startActivity(intent);
            }
        });
        if(CheckConnection.haveNetworkConnection(getApplicationContext())){
            EventButton();
            Anhxa();
            btnchoose.setOnClickListener(this);
        }else {CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");}
    }
    private void CatchEventSpinner() {
        String[] list = new String[]{"Điện Thoại","LapTop"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,list);
        spinnertsp.setAdapter(arrayAdapter);
    }

    private void EventButton() {
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Tensp = edttsp.getText().toString().trim();
                final String Giasp = giasp.getText().toString().trim();
                final String Motasp = motasp.getText().toString().trim();
                final String Loaisp = spinnertsp.getSelectedItem().toString();


                if(Tensp.length()>0&& Giasp.length()>0 && Motasp.length()>0 && Loaisp.length()>0){
                    upLoadImg();
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanthemsanpham, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn đã thêm sản phẩm thành công");
                            Intent intent = new Intent(getApplicationContext(),QuanLySanPham.class);
                            startActivity(intent);
                            CheckConnection.ShowToast_Short(getApplicationContext(),"Mời bạn tiếp tục thêm mặt hàng");
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            String loai ="0";
                            if(Loaisp == "Điện Thoại"){
                                loai = "1";}
                            else if (Loaisp == "LapTop"){
                                loai = "2";}
                            String Hinhanhsp[] = Tensp.split("\\s");
                            String tenhinh="";
                            for(String w:Hinhanhsp){
                                tenhinh +=w;
                            }
                            HashMap<String,String> hashMap = new HashMap<String,String>();
                            hashMap.put("tensanpham",Tensp);
                            hashMap.put("giasanpham",Giasp);
                            hashMap.put("hinhanhsanpham","https://cuahangdientuonline12.000webhostapp.com/image/"+tenhinh+".jpg");
                            hashMap.put("motasanpham",Motasp);
                            hashMap.put("idloaisanpham",loai);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Bạn hãy kiểm tra lại thông tin");
                }
            }
        });

    }

    private void Anhxa() {
        edttsp = findViewById(R.id.editnhaptensanpham);
        giasp = findViewById(R.id.editnhapgiasanpham);
        motasp = findViewById(R.id.editnhapmotasanpham);
        spinnertsp = findViewById(R.id.spinnerthemsanpham);
        btnthem = findViewById(R.id.btnthem);
        btntrove = findViewById(R.id.btntrovetsp);
        btnchoose = findViewById(R.id.btnchoose);
        imageView = findViewById(R.id.imgviewthemsanpham);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.btnchoose:
                selectImage();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == IMG_REQUEST && resultCode == RESULT_OK && data!=null){
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                imageView.setImageBitmap(bitmap);
                imageView.setVisibility(View.VISIBLE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void upLoadImg() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdanupload, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                imageView.setImageResource(0);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String tenha = edttsp.getText().toString().trim();
                String  tam[] = tenha.split("\\s");
                String hinhanhluuten = "";
                for(String w:tam){
                    hinhanhluuten +=w;
                }
                HashMap<String,String> param = new HashMap<>();
                param.put("tenhinhanh",hinhanhluuten);
                param.put("img",imgToString(bitmap));

                return param;
            }
        };
        MySingleton.getInstance(ThemSanPham.this).addToRequest(stringRequest);
    }
    private String imgToString(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }
    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }
}
