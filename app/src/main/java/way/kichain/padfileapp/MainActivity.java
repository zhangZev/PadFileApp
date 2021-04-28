package way.kichain.padfileapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.zyq.easypermission.EasyPermission;
import com.zyq.easypermission.EasyPermissionHelper;

import java.io.File;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import way.kichain.padfileapp.utils.FileUtils;


public class MainActivity extends AppCompatActivity {
    /**
     * 需要申请的权限数组
     */
    protected String[] needPermissions = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
    };

    public static String[] fileDirs = {"/首页","/促进会概况","/会员风采","/党支部建设"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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
        File file = new File(FileUtils.FILE_DIR);
        if (!file.exists()) {// 判断当前目录是否存在，存在返回true,否则返回false
            file.mkdir();
        }
        for (int i = 0; i <fileDirs.length ; i++) {
            File file_child = new File(FileUtils.FILE_DIR+fileDirs[i]);
            if(!file_child.exists()){
                file_child.mkdir();
            }
        }
        //获取首页内容
        File file_home = new File(FileUtils.FILE_DIR+fileDirs[0]);
        List<File> mHomeFils = FileUtils.listFilesInDir(file_home,false);
        for (File f:mHomeFils) {
            Log.e("===file",f.getName());
        }
    }

    @OnClick({R.id.btn_one,R.id.btn_two})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_one:
                //首页
                startActivity(new Intent(this,AdvancementActivity.class));
                break;
            case R.id.btn_two:
                //首页
                startActivity(new Intent(this,VipActivity.class));
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