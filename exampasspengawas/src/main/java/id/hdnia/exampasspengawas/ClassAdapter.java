package id.hdnia.exampasspengawas;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.ClassViewHolder> {
    private JSONArray classes;
    private OnItemClickListener onItemClickListener;

    public ClassAdapter(JSONArray data, OnItemClickListener listener){
        this.classes = data;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_class, parent, false);
        return new ClassViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {
        try {
            holder.tvMataKuliah.setText(classes.getJSONObject(position).getString("nama_kelas"));
            holder.tvRuangan.setText(classes.getJSONObject(position).getString("ruangan"));
            holder.tvMulai.setText(classes.getJSONObject(position).getString("mulai"));
            holder.tvSelesai.setText(" - "+classes.getJSONObject(position).getString("selesai"));
            holder.tvTanggal.setText(classes.getJSONObject(position).getString("tanggal_ujian"));
            holder.bind(classes.getJSONObject(position), onItemClickListener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return classes.length();
    }

    public class ClassViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMataKuliah, tvRuangan, tvMulai, tvSelesai, tvTanggal;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMataKuliah = itemView.findViewById(R.id.tv_matakuliah);
            tvRuangan = itemView.findViewById(R.id.tv_ruangan);
            tvMulai = itemView.findViewById(R.id.tv_mulai);
            tvSelesai = itemView.findViewById(R.id.tv_selesai);
            tvTanggal = itemView.findViewById(R.id.tv_tanggal);
        }

        public void bind(final JSONObject item, final OnItemClickListener l){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    l.onItemClick(item);
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(JSONObject item);
    }
}