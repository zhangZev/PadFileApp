package way.kichain.padfileapp;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.shehuan.niv.NiceImageView;
import com.zyq.easypermission.EasyPermission;
import com.zyq.easypermission.EasyPermissionHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JzvdStd;
import way.kichain.padfileapp.model.HomeModel;
import way.kichain.padfileapp.utils.FileUtils;
import way.kichain.padfileapp.utils.RecyclerItemDecoration;
import way.kichain.padfileapp.views.LoadingDialog;


public class MainActivity extends BaseActivity {
    /**
     * 需要申请的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    public static String[] fileDirs = {"/首页", "/促进会概况", "/会员风采", "/党支部建设"};
    public static String imageDir = "/images";
    public static String[] memberFiles={"/党员活动","/支部信息"};
    public static String[] CujinFiles={"/简介","/组织机构","/协会动态"};
    private LinearLayout ll_bg;
    private JzvdStd videoView;
    private RecyclerView mRecyclerView;

    @BindViews({R.id.img_one, R.id.img_two, R.id.img_three
            , R.id.img_fore, R.id.img_five, R.id.img_six})
    List<NiceImageView> mImageviews;
    private List<File> mImages;
    private HomeModel homeModel;
    LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loadingDialog = new LoadingDialog(this);

        ll_bg = findViewById(R.id.ll_bg);
        videoView = findViewById(R.id.view_video);
        mRecyclerView = findViewById(R.id.img_rechcler);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new RecyclerItemDecoration(20, 10, 3));
        homeModel = new HomeModel();
        checkPermissions();
    }

    private void checkPermissions() {
        if (EasyPermission.build().hasPermission(this, needPermissions)) {
            //gotoCreateFile();
            handler.sendEmptyMessage(0);
        } else {
            EasyPermission.build().requestPermission(MainActivity.this, needPermissions);
        }

    }

    /**
     * 初始化文件夹
     */
    private void initFile() {
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
        //创建党支部下党员活动、支部信息
        for (int i = 0; i <memberFiles.length ; i++) {
            File file_member = new File(FileUtils.getSDPath() + fileDirs[3] + memberFiles[i]);
            if (!file_member.exists()) {
                file_member.mkdir();
            }
        }
        for (int i = 0; i <CujinFiles.length ; i++) {
            File file_cujin = new File(FileUtils.getSDPath() + fileDirs[1] + CujinFiles[i]);
            if (!file_cujin.exists()) {
                file_cujin.mkdir();
            }
        }
        gotoFile();
    }

    private void gotoFile() {
        //获取首页内容
        File file_home = new File(FileUtils.getSDPath() + fileDirs[0]);
        List<File> mHomeFils = FileUtils.listFilesInDir(file_home, false);

        if (mHomeFils != null) {
            for (File f : mHomeFils) {
                String name = f.getName().toLowerCase();
                /*if (FileUtils.isDir(f)) {
                    List<File> mImages = FileUtils.listFilesInDir(f, false);
                    showImages(mImages);
                    model.setImages(mImages);
                    HomeImageAdapter homeImageAdapter = new HomeImageAdapter(this, mImages);
                    mRecyclerView.setAdapter(homeImageAdapter);
                } else */
                if (name.endsWith(".mp4") || name.endsWith(".MP4")) {
                    homeModel.setVideos(f);
                } else if (name.endsWith(".jpg") || name.endsWith(".jpeg")
                        || name.endsWith(".png")) {
                    homeModel.setBgFile(f);
                    homeImage = homeModel.bgFile.getPath();
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
        if (mVipFils != null) {
            //遍历获取会员中文件夹
            for (File file : mVipFils) {
                List<File> mDataFiles = FileUtils.listFilesInDir(file, false);
                for (File image : mDataFiles) {
                    String name = image.getName().toLowerCase();
                    if (name.endsWith(".jpg") || name.endsWith(".jpeg")
                            || name.endsWith(".png")) {
                        mImages.add(image);
                    }
                }
            }
            homeModel.setImages(mImages);
        }
        handler.sendEmptyMessage(1);
    }

    private void showImages(List<File> mImages) {
        for (int i = 0; i < mImages.size(); i++) {
            if (i >= 6) {
                return;
            }
            //mImageviews.get(i).setImageBitmap(FileUtils.getLoacalBitmap(mImages.get(i).getPath()));
            Glide.with(this)
                    .load(mImages.get(i))
                    .into( mImageviews.get(i));
        }
    }

    //从视频中截取图片
    public static Bitmap getImageFromVideo(String videoPath) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(videoPath);
        return media.getFrameAtTime();
    }

    @OnClick({R.id.btn_one, R.id.btn_two,R.id.btn_three})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_one:
                //简介
                startActivity(new Intent(this, CujinActivity.class));
                break;
            case R.id.btn_two:
                //会员
                startActivity(new Intent(this, VipActivity.class));
                break;
            case R.id.btn_three:
                startActivity(new Intent(this, MemberActivity.class));
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //使用EasyPermissionHelper注入回调
        EasyPermissionHelper.getInstance().onRequestPermissionsResult(requestCode, permissions, grantResults, this);
        checkPermissions();
    }

    //消息处理者,创建一个Handler的子类对象,目的是重写Handler的处理消息的方法(handleMessage())
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    loadingDialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            initFile();
                        }
                    }).start();
                    break;
                case 1:
                    showFiles();
                    loadingDialog.dismiss();
                    break;
            }
        }
    };

    private void showFiles() {
        if(homeModel.getVideos()!=null){
            videoView.setUp(homeModel.getVideos().getPath(), "title");
            videoView.posterImageView.setImageBitmap(getImageFromVideo(homeModel.getVideos().getPath()));
        }
        if (homeModel.getBgFile()!=null){
            ll_bg.setBackground(new BitmapDrawable(FileUtils.getLoacalBitmap(homeModel.getBgFile().getPath())));
        }
        if(homeModel.getImages()!=null){
            showImages(homeModel.getImages());
        }
    }


}