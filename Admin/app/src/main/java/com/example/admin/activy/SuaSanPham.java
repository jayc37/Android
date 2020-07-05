package com.example.admin.activy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.example.admin.model.Sanpham;
import com.example.admin.usetill.CheckConnection;

import com.example.admin.usetill.Server;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class SuaSanPham extends AppCompatActivity implements OnClickListener {
    EditText edttsp, giasp, motasp;
    Spinner spinnertsp;
    Button btnthem, btntrove, btnchoose;
    ImageView imageView;
    Bitmap bitmap;

    final int IMG_REQUEST = 1;
    public static int id = 0;
    String Tenchitiet = "";
    int Giachitiet = 0;
    String Hinhanhchitiet = "";
    String Motachitiet = "";
    int idsanpham = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_san_pham);
        Anhxa();
        CatchEventSpinner();
        getInformation();
        btntrove.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        if (CheckConnection.haveNetworkConnection(SuaSanPham.this)) {
            EventButton();
            Anhxa();
            btnchoose.setOnClickListener(this);
        } else {
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
        }
    }


    private void getInformation() {
        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinsanphamsua");
        id = sanpham.getID();
        Tenchitiet = sanpham.getTensanpham();
        Giachitiet = sanpham.getGiasanpham();
        Hinhanhchitiet = sanpham.getHinhanhsanpham();
        Motachitiet = sanpham.getMotasanpham();
        idsanpham = sanpham.getIDloaisanpham();
        spinnertsp.setSelection(idsanpham - 1);
        edttsp.setText(Tenchitiet);
        DecimalFormat decimalFormat = new DecimalFormat("#########");
        giasp.setText(decimalFormat.format(Giachitiet));
        motasp.setText(Motachitiet);
        Picasso.get().load(Hinhanhchitiet)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(imageView);
    }

    private void CatchEventSpinner() {
        String[] list = new String[]{"Điện Thoại", "LapTop"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, list);
        spinnertsp.setAdapter(arrayAdapter);
    }

    private void Anhxa() {
        edttsp = findViewById(R.id.editsuatensanpham);
        giasp = findViewById(R.id.editsuagiasanpham);
        motasp = findViewById(R.id.editsuamotasanpham);
        spinnertsp = findViewById(R.id.spinnersuasanpham);
        btnthem = findViewById(R.id.btnthemcuasua);
        btntrove = findViewById(R.id.btntrovetspcuasua);
        btnchoose = findViewById(R.id.btnchoosecuasua);
        imageView = findViewById(R.id.imgviewsuasanpham);
    }

    private void EventButton() {
        btnthem.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                final String Tensp = edttsp.getText().toString().trim();
                final String Giasp = giasp.getText().toString().trim();
                final String Motasp = motasp.getText().toString().trim();
                final String Loaisp = spinnertsp.getSelectedItem().toString();


                if (Tensp.length() > 0 && Giasp.length() > 0 && Motasp.length() > 0 && Loaisp.length() > 0) {
                    upLoadImg();
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdansuasanpham, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn đã sửa sản phẩm thành công");
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            CheckConnection.ShowToast_Short(getApplicationContext(), "Mời bạn tiếp tục kiểm kê mặt hàng");
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            String loai = "0";
                            if (Loaisp == "Điện Thoại") {
                                loai = "1";
                            } else if (Loaisp == "LapTop") {
                                loai = "2";
                            }
                            String Hinhanhsp[] = Tensp.split("\\s");
                            String tenhinh = "";
                            for (String w : Hinhanhsp) {
                                tenhinh += w;
                            }
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("id", Integer.toString(id));
                            hashMap.put("tensanpham", Tensp);
                            hashMap.put("giasanpham", Giasp);
                            hashMap.put("hinhanhsanpham", "https://cuahangdientuonline12.000webhostapp.com/image/" + tenhinh + ".jpg");
                            hashMap.put("motasanpham", Motasp);
                            hashMap.put("idloaisanpham", loai);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                } else {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại thông tin");
                }
            }
        });

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnchoosecuasua:
                selectImage();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imageView.setImageBitmap(bitmap);
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
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String tenha = edttsp.getText().toString().trim();
                String tam[] = tenha.split("\\s");
                String hinhanhluuten = "";
                for (String w : tam) {
                    hinhanhluuten += w;
                }
                HashMap<String, String> param = new HashMap<>();
                param.put("tenhinhanh", hinhanhluuten);
                param.put("img", imgToString(bitmap));

                return param;
            }
        };
        MySingleton.getInstance(SuaSanPham.this).addToRequest(stringRequest);
    }

    private String imgToString(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte, Base64.DEFAULT);
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMG_REQUEST);
    }
}
