package way.kichain.padfileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;

public class BaseActivity extends AppCompatActivity {
    public static String homeImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersionBar.with(this).init();

    }

    @Override
    protected void onStart() {
        super.onStart();
        ImageView img_back = findViewById(R.id.img_back);
        ImageView img_home = findViewById(R.id.img_home);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BaseActivity.this instanceof MainActivity) {
                    exit();
                    return;
                }
                onBackPressed();
            }
        });
        img_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (BaseActivity.this instanceof MainActivity) {
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    public boolean isExit = false;
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            isExit = false;
        }

    };

    public void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            System.exit(0);
        }
    }

}