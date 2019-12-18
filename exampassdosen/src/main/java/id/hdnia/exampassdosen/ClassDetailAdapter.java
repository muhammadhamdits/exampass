package id.hdnia.exampassdosen;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ClassDetailAdapter extends RecyclerView.Adapter<ClassDetailAdapter.ClassDetailViewHolder> {
    private JSONArray classDetail;
    private OnItemClickListener onItemClickListener;

    public ClassDetailAdapter(JSONArray data, OnItemClickListener listener){
        classDetail = data;
        onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ClassDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_class, parent, false);
        return new ClassDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassDetailViewHolder holder, int position) {
        try {
            holder.tvMataKuliah.setText(classDetail.getJSONObject(position).getString("nama"));
            holder.tvRuangan.setText(classDetail.getJSONObject(position).getString("nim"));
            holder.tvMulai.setText("");
            holder.tvSelesai.setText("");
            holder.tvTanggal.setText("");
            holder.bind(classDetail.getJSONObject(position), onItemClickListener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return classDetail.length();
    }

    public class ClassDetailViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMataKuliah, tvRuangan, tvMulai, tvSelesai, tvTanggal;

        public ClassDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMataKuliah = itemView.findViewById(R.id.tv_matakuliah);
            tvRuangan = itemView.findViewById(R.id.tv_ruangan);
            tvMulai = itemView.findViewById(R.id.tv_mulai);
            tvSelesai = itemView.findViewById(R.id.tv_selesai);
            tvTanggal = itemView.findViewById(R.id.tv_tanggal);
        }

        public void bind(final JSONObject item, final ClassDetailAdapter.OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(JSONObject item);
    }
}
