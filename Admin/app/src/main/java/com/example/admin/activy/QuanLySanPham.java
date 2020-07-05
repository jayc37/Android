package com.example.admin.activy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.admin.R;

public class QuanLySanPham extends AppCompatActivity {
    Button btntk,btntsp,btndoips;
    public static String adminmd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quan_ly_san_pham);
        btntk = findViewById(R.id.btnthongkesanpham);
        btntsp = findViewById(R.id.btnthemsanpham);
        btndoips = findViewById(R.id.btnqltk);
        btntk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SanPhamActivity.class);
                startActivity(intent);
            }
        });
        btntsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ThemSanPham.class);
                startActivity(intent);
            }
        });
        btndoips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),QuanLyAdmin.class);
                startActivity(intent);
            }
        });
    }

}
