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
public class HomeImageAdapter extends RecyclerView.Adapter<HomeImageAdapter.VH> {
    private Context mContext;
    private List<File> mFils = new ArrayList<>();
    public HomeImageAdapter(Context context, List files) {
        mContext = context;
        mFils = files;
    }



    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_home_image,null);

        return new VH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        File file = mFils.get(position);
        holder.imgUser.setImageBitmap(FileUtils.getLoacalBitmap(file.getPath()));
        holder.imgUser.setOnClickListener(new View.OnClickListener() {
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

        ImageView imgUser;
        public VH(@NonNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.img_user);
        }
    }
    public  onItemClickListener onItemClickListener;

    public void setOnItemClickListener(HomeImageAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface onItemClickListener{
        void onClick(File file);
    }
}
