package id.hdnia.exampass;

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

class ListUjianAdapter extends RecyclerView.Adapter<ListUjianAdapter.LuaViewHolder> {
    private JSONArray mDataSet;
    public OnItemClickListener listener;

    public ListUjianAdapter(JSONArray myDataSet, OnItemClickListener l) {
        mDataSet = myDataSet;
        listener = l;
    }

    @NonNull
    @Override
    public LuaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_ujian_item, parent, false);
        return new LuaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LuaViewHolder holder, int position) {
        try {
            holder.tvMataKuliah.setText(mDataSet.getJSONObject(position).getString("nama_mk"));
            holder.tvRuangan.setText(mDataSet.getJSONObject(position).getString("ruangan"));
            holder.tvMulai.setText(mDataSet.getJSONObject(position).getString("mulai"));
            holder.tvSelesai.setText(mDataSet.getJSONObject(position).getString("selesai"));
            holder.tvTanggal.setText(mDataSet.getJSONObject(position).getString("tanggal"));
            holder.bind(mDataSet.getJSONObject(position), listener);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface RecyclerViewItemClick{
        public void onItemClickListener(JSONObject holder, int position);
    }

    @Override
    public int getItemCount() {
        return mDataSet.length();
    }

    public class LuaViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMataKuliah, tvRuangan, tvMulai, tvSelesai, tvTanggal;

        public LuaViewHolder(@NonNull View itemView) {
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
                    try {
                        listener.onItemClick(item);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(JSONObject item) throws JSONException;
    }
}
