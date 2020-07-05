package com.example.admin.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.R;
import com.example.admin.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class TatCaSanPhamAdapter extends BaseAdapter {

    Context context;
    ArrayList<Sanpham> arrayListtatcasanpham;

    public TatCaSanPhamAdapter(Context context, ArrayList<Sanpham> arrayListtatcasanpham) {
        this.context = context;
        this.arrayListtatcasanpham = arrayListtatcasanpham;
    }


    @Override
    public int getCount() {
        return arrayListtatcasanpham.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayListtatcasanpham.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.dong_cua_tat_ca_san_pham, null);
        TextView tvTendienthoai = view.findViewById(R.id.textviewtentatcasanpham);
        TextView tvGiadienthoai = view.findViewById(R.id.textviewgiatatcasanpham);
        TextView tvMotadienthoai = view.findViewById(R.id.textviewmotatatcasanpham);
        ImageView imgdienthoai = view.findViewById(R.id.imageviewtatcasanpham);
        tvTendienthoai.setText(arrayListtatcasanpham.get(i).getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tvGiadienthoai.setText("Giá: " + decimalFormat.format(arrayListtatcasanpham.get(i).getGiasanpham()) + " Đ");
        tvMotadienthoai.setMaxLines(2);
        tvMotadienthoai.setEllipsize(TextUtils.TruncateAt.END);
        tvMotadienthoai.setText(arrayListtatcasanpham.get(i).getMotasanpham());
        Picasso.get().load(arrayListtatcasanpham.get(i).getHinhanhsanpham())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(imgdienthoai);
//        view.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                final AlertDialog.Builder buidlder = new AlertDialog.Builder(context);
//                buidlder.setMessage("Bạn có chắc chắn muốn sửa sản phẩm này không ?");
//                buidlder.setIcon(android.R.drawable.ic_delete);
//                buidlder.setTitle("Xác nhận sửa");
//                buidlder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        SanPhamActivity.tatCaSanPhamAdapter.notifyDataSetChanged();
//                    }
//                });
//                buidlder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int j) {
//                        Intent intent = new Intent(context, ChiTietSanPham.class);
//                        intent.putExtra("thongtinsanpham",SanPhamActivity.mangtcsp.get(i));
//                        context.startActivity(intent);
//                    }
//                });
//                AlertDialog alertDialog = buidlder.create();
//                alertDialog.show();
//                return true;
//            }
//        });
        return view;
    }

    @Override
    public CharSequence[] getAutofillOptions() {
        return new CharSequence[0];
    }

}
