package way.kichain.padfileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.LinearLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import way.kichain.padfileapp.adapter.AdvanceAdapter;
import way.kichain.padfileapp.adapter.VipAdapter;
import way.kichain.padfileapp.model.VipModel;
import way.kichain.padfileapp.utils.FileUtils;

import static way.kichain.padfileapp.MainActivity.fileDirs;

public class VipActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_bg)
    LinearLayout ll_bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        if (!TextUtils.isEmpty(homeImage)){
            ll_bg.setBackground(new BitmapDrawable(FileUtils.getLoacalBitmap(homeImage)));
        }
        //获取首页内容
        File file_home = new File(FileUtils.getSDPath()  + fileDirs[2]);
        List<File> vipFiles = FileUtils.listFilesInDir(file_home, false);
        List<VipModel> mData = new ArrayList<>();
        for (File file : vipFiles) {
            //遍历内部文件夹
            VipModel model = new VipModel();
            List<File> dataFiles = FileUtils.listFilesInDir(file.getPath(), false);
            for (File datafile : dataFiles) {
                if (datafile.getName().contains("jpg") || datafile.getName().contains("png")
                        || datafile.getName().contains("JPG")) {
                    model.setImageFile(datafile);
                }else if(datafile.getName().contains("简介")){
                    model.setDescFile(datafile);
                }else {
                    model.setContentFile(datafile);
                }
            }
            mData.add(model);
        }
        VipAdapter mAdapter = new VipAdapter(this, mData);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new VipAdapter.onItemClickListener() {
            @Override
            public void onClick(VipModel file) {
                Intent intent = new Intent();
                intent.setClass(VipActivity.this,InfosActivity.class);
                intent.putExtra("INTENT_DATA",file.getContentFile());
                startActivity(intent);
            }
        });
    }
}