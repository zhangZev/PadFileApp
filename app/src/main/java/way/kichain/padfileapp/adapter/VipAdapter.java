package way.kichain.padfileapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import way.kichain.padfileapp.R;
import way.kichain.padfileapp.model.VipModel;
import way.kichain.padfileapp.utils.FileUtils;

/**
 * @time:{2021/4/28}
 * @auhor:{ZhangXW}
 */
public class VipAdapter extends RecyclerView.Adapter<VipAdapter.VH> {
    private Context mContext;
    private List<VipModel> mFils = new ArrayList<>();
    public VipAdapter(Context context, List files) {
        mContext = context;
        mFils = files;
    }



    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_vip_list_view,null);

        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        VipModel file = mFils.get(position);
        holder.imgUser.setImageBitmap(FileUtils.getLoacalBitmap(file.getImageFile().getPath()));
        holder.tvTitle.setText(file.getContentFile().getName());
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
        ImageView imgUser;
        public VH(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            imgUser = itemView.findViewById(R.id.img_user);
        }
    }
    public  onItemClickListener onItemClickListener;

    public void setOnItemClickListener(VipAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface onItemClickListener{
        void onClick(VipModel file);
    }
}
