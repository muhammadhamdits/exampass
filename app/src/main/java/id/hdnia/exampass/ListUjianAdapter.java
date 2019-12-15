package id.hdnia.exampass;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class ListUjianAdapter extends RecyclerView.Adapter<ListUjianAdapter.LuaViewHolder> {
    private JSONArray mDataSet;

    public ListUjianAdapter(JSONArray myDataSet) {
        mDataSet = myDataSet;
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
            holder.txtNama.setText(mDataSet.getJSONObject(position).getString("nama_mk"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mDataSet.length();
    }

    public class LuaViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNama;

        public LuaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNama = itemView.findViewById(R.id.txt_nama_mahasiswa);
        }
    }
}
