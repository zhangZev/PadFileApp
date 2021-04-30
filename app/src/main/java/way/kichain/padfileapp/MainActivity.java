package way.kichain.padfileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.zyq.easypermission.EasyPermission;
import com.zyq.easypermission.EasyPermissionHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JzvdStd;
import way.kichain.padfileapp.adapter.HomeImageAdapter;
import way.kichain.padfileapp.model.HomeModel;
import way.kichain.padfileapp.utils.FileUtils;
import way.kichain.padfileapp.utils.RecyclerItemDecoration;


public class MainActivity extends AppCompatActivity {
    /**
     * 需要申请的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    public static String[] fileDirs = {"/首页", "/促进会概况", "/会员风采", "/党支部建设"};
    public static String imageDir = "/images";
    private LinearLayout ll_bg;
    private JzvdStd videoView;
    private RecyclerView mRecyclerView;

    @BindViews({R.id.img_one, R.id.img_two, R.id.img_three
            , R.id.img_fore, R.id.img_five, R.id.img_six})
    List<ImageView> mImageviews;
    private List<File> mImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ll_bg = findViewById(R.id.ll_bg);
        videoView = findViewById(R.id.view_video);
        mRecyclerView = findViewById(R.id.img_rechcler);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new RecyclerItemDecoration(20, 10, 3));

        checkPermissions();
    }

    private void checkPermissions() {
        if (EasyPermission.build().hasPermission(this, needPermissions)) {
            gotoCreateFile();
        } else {
            EasyPermission.build().requestPermission(MainActivity.this, needPermissions);
        }

    }

    private void gotoCreateFile() {
        File file = new File(FileUtils.getSDPath());
        if (!file.exists()) {// 判断当前目录是否存在，存在返回true,否则返回false
            file.mkdir();
        }
        for (int i = 0; i < fileDirs.length; i++) {
            File file_child = new File(FileUtils.getSDPath() + fileDirs[i]);
            if (!file_child.exists()) {
                file_child.mkdir();
            }
        }
        //创建首页下images
        File file_child = new File(FileUtils.getSDPath() + fileDirs[0] + imageDir);
        if (!file_child.exists()) {
            file_child.mkdir();
        }
        //获取首页内容
        File file_home = new File(FileUtils.getSDPath() + fileDirs[0]);
        List<File> mHomeFils = FileUtils.listFilesInDir(file_home, false);
        HomeModel model = new HomeModel();
        if (mHomeFils != null) {
            for (File f : mHomeFils) {
                String name = f.getName().toLowerCase();
                /*if (FileUtils.isDir(f)) {
                    List<File> mImages = FileUtils.listFilesInDir(f, false);
                    showImages(mImages);
                    model.setImages(mImages);
                    HomeImageAdapter homeImageAdapter = new HomeImageAdapter(this, mImages);
                    mRecyclerView.setAdapter(homeImageAdapter);
                } else */if (name.endsWith(".mp4") || name.endsWith(".MP4")) {
                    model.setVideos(f);
                    videoView.setUp(f.getPath(), "title");
                    videoView.posterImageView.setImageBitmap(getImageFromVideo(f.getPath()));
                } else if (name.endsWith(".jpg") || name.endsWith(".jpeg")
                        || name.endsWith(".png")) {
                    model.setBgFile(f);
                    ll_bg.setBackground(new BitmapDrawable(FileUtils.getLoacalBitmap(f.getPath())));
                }
            }
        }
        //获取会员信息
        getImagesVip();
    }

    /**
     * 获取会员头像
     */
    private void getImagesVip() {
        List<File> mImages = new ArrayList<>();
        File fileVip = new File(FileUtils.getSDPath() + fileDirs[2]);
        List<File> mVipFils = FileUtils.listFilesInDir(fileVip, false);
        if(mVipFils!=null){
            //遍历获取会员中文件夹
            for (File file:mVipFils) {
                List<File> mDataFiles = FileUtils.listFilesInDir(file, false);
                for (File image:mDataFiles) {
                    String name = image.getName().toLowerCase();
                    if (name.endsWith(".jpg") || name.endsWith(".jpeg")
                            || name.endsWith(".png")){
                        mImages.add(image);
                    }
                }
            }
            showImages(mImages);
        }
    }

    private void showImages(List<File> mImages) {
        for (int i=0;i<mImages.size();i++){
            if(i>=6){
                return;
            }
            mImageviews.get(i).setImageBitmap(FileUtils.getLoacalBitmap(mImages.get(i).getPath()));
        }

    }

    //从视频中截取图片
    public static Bitmap getImageFromVideo(String videoPath) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(videoPath);
        return media.getFrameAtTime();
    }

    @OnClick({R.id.btn_one, R.id.btn_two})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_one:
                //首页
                startActivity(new Intent(this, AdvancementActivity.class));
                break;
            case R.id.btn_two:
                //首页
                startActivity(new Intent(this, VipActivity.class));
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //使用EasyPermissionHelper注入回调
        EasyPermissionHelper.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}