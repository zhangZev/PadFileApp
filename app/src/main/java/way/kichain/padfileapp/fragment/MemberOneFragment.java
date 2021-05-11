package way.kichain.padfileapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import way.kichain.padfileapp.InfosActivity;
import way.kichain.padfileapp.R;
import way.kichain.padfileapp.VipActivity;
import way.kichain.padfileapp.adapter.MemberAdapter;
import way.kichain.padfileapp.adapter.VipAdapter;
import way.kichain.padfileapp.model.VipModel;
import way.kichain.padfileapp.utils.FileUtils;

import static way.kichain.padfileapp.MainActivity.fileDirs;
import static way.kichain.padfileapp.MainActivity.memberFiles;

public class MemberOneFragment extends Fragment {

    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_member_one,null);
        mRecyclerView = view.findViewById(R.id.recycler_view);
        initView();
        return view;
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        //获取党员内容
        File file_home = new File(FileUtils.getSDPath()  + fileDirs[3]+memberFiles[0]);
        List<File> vipFiles = FileUtils.listFilesInDir(file_home, false);
        List<VipModel> mData = new ArrayList<>();
        for (File file : vipFiles) {
            //遍历内部文件夹
            VipModel model = new VipModel();
            List<File> dataFiles = FileUtils.listFilesInDir(file.getPath(), false);
            for (File datafile : dataFiles) {
                if (datafile.getName().contains("jpg") || datafile.getName().contains("png")
                        || datafile.getName().contains("JPG")|| datafile.getName().contains("PNG")) {
                    model.setImageFile(datafile);
                }else if(datafile.getName().contains("简介")){
                    model.setDescFile(datafile);
                }else {
                    model.setContentFile(datafile);
                }
            }
            mData.add(model);
        }
        MemberAdapter mAdapter = new MemberAdapter(getContext(), mData);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new MemberAdapter.onItemClickListener() {
            @Override
            public void onClick(VipModel file) {
                Intent intent = new Intent();
                intent.setClass(getContext(), InfosActivity.class);
                intent.putExtra("INTENT_DATA",file.getContentFile());
                startActivity(intent);
            }
        });
    }
}
