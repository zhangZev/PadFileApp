package way.kichain.padfileapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import way.kichain.padfileapp.R;

/**
 * @time:{2021/4/28}
 * @auhor:{ZhangXW}
 */
public class AdvanceAdapter extends RecyclerView.Adapter<AdvanceAdapter.VH> {
    private Context mContext;
    private List<File> mFils = new ArrayList<>();
    public AdvanceAdapter(Context context, List<File> files) {
        mContext = context;
        mFils = files;
    }



    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_advance_view,null);

        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        File file = mFils.get(position);
        holder.tvTitle.setText(file.getName());
        holder.tvTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListener!=null){
                    onItemClickListener.onClick(mFils.get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mFils.size();
    }

    public class VH extends RecyclerView.ViewHolder {

        TextView tvTitle;
        public VH(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
        }
    }
    public  onItemClickListener onItemClickListener;

    public void setOnItemClickListener(AdvanceAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface onItemClickListener{
        void onClick(File file);
    }
}
