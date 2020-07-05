package com.example.admin.activy;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.admin.R;
import com.example.admin.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSanPham extends AppCompatActivity {

    Toolbar toolbarct;
    ImageView imageViewct;
    TextView txtten,txtgia,txtmota;
   // EditText spinner;
    Spinner spinner;
    Button dathang;
    int id = 0;
    String Tenchitiet ="";
    int Giachitiet = 0;
    String Hinhanhchitiet ="";
    String Motachitiet ="";
    int idsanpham =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        Anhxa();
        ActionToolbar();
        getInformation();
        CatchEventSpinner();
    }


    private void CatchEventSpinner() {
        Integer[] soluong = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this,R.layout.support_simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }
// nhập số lượng sản phẩm.
//    private void EventButton() {
//        dathang.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(MainActivity.manggiohang.size()>0){
//                    int sl = Integer.parseInt(spinner.getText().toString());
//                    boolean exit = false;
//                    for(int i = 0; i<MainActivity.manggiohang.size();i++){
//                        if(MainActivity.manggiohang.get(i).getIdsp() == id){
//                            MainActivity.manggiohang.get(i).setSoluongsp(MainActivity.manggiohang.get(i).getSoluongsp()+ sl);
////                            if(MainActivity.manggiohang.get(i).getSoluongsp()> 10){
////                                MainActivity.manggiohang.get(i).setSoluongsp(10);
////                            }
//                            MainActivity.manggiohang.get(i).setGiasp(Giachitiet * MainActivity.manggiohang.get(i).getSoluongsp());
//                            exit = true;
//                        }
//                    }
//                    if (exit == false){
//                        int soluong = Integer.parseInt(spinner.getText().toString());
//                        long Giamoi = soluong * Giachitiet;
//                        MainActivity.manggiohang.add(new Giohang(id,Tenchitiet,Giamoi,Hinhanhchitiet,soluong));
//
//                    }
//                }
//                else{
//                    int soluong = Integer.parseInt(spinner.getText().toString());
//                    long Giamoi = soluong * Giachitiet;
//                    MainActivity.manggiohang.add(new Giohang(id,Tenchitiet,Giamoi,Hinhanhchitiet,soluong));
//
//                }
//                Intent intent = new Intent(getApplicationContext(), GioHang.class);
//                startActivity(intent);
//            }
//        });
//    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.manugiohang,menu);
        return true;
    }
    private void getInformation() {
        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanpham.getID();
        Tenchitiet = sanpham.getTensanpham();
        Giachitiet = sanpham.getGiasanpham();
        Hinhanhchitiet = sanpham.getHinhanhsanpham();
        Motachitiet = sanpham.getMotasanpham();
        idsanpham = sanpham.getIDloaisanpham();
        txtten.setText(Tenchitiet);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgia.setText("Giá: "+decimalFormat.format(Giachitiet) + " Đ");
        txtmota.setText(Motachitiet);
        Picasso.get().load(Hinhanhchitiet)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(imageViewct);
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarct);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarct.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbarct = findViewById(R.id.toolbarchitietsanpham);
        imageViewct = findViewById(R.id.imgviewchitietsanpham);
        txtten = findViewById(R.id.textviewtenchitietsanpham);
        txtgia = findViewById(R.id.textviewgiachitietsanpham);
        txtmota = findViewById(R.id.textviewmotachitietsanpham);
        spinner = findViewById(R.id.spinner);
        dathang = findViewById(R.id.buttondathang);
    }
}
