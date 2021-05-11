package way.kichain.padfileapp.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import way.kichain.padfileapp.R;
import way.kichain.padfileapp.model.VipModel;
import way.kichain.padfileapp.utils.FileUtils;

/**
 * @time:{2021/4/28}
 * @auhor:{ZhangXW}
 */
public class CuJInAdapter extends RecyclerView.Adapter<CuJInAdapter.VH> {
    private Context mContext;
    private List<VipModel> mFils = new ArrayList<>();
    private final Typeface mtypeface;
    public CuJInAdapter(Context context, List files) {
        mContext = context;
        mFils = files;
        mtypeface = Typeface.createFromAsset(mContext.getAssets(),"myfont.ttf");
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_vip_list_view, null);

        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        VipModel file = mFils.get(position);
        holder.tvTitle.setTypeface(mtypeface);
        holder.tv_content.setTypeface(mtypeface);
        holder.imgUser.setImageBitmap(FileUtils.getLoacalBitmap(file.getImageFile().getPath()));
        holder.tvTitle.setText(FileUtils.getFileNameNoExtension(file.getImageFile()));
        holder.tv_content.setText(FileUtils.ReadTxtFile(file.getDescFile()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
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
        TextView tv_content;
        ImageView imgUser;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tv_content = itemView.findViewById(R.id.tv_content);
            imgUser = itemView.findViewById(R.id.img_user);
        }
    }

    public onItemClickListener onItemClickListener;

    public void setOnItemClickListener(CuJInAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface onItemClickListener {
        void onClick(VipModel file);
    }
}
