package way.kichain.padfileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import way.kichain.padfileapp.fragment.MemberOneFragment;
import way.kichain.padfileapp.fragment.MemberTwoFragment;

public class MemberActivity extends BaseActivity {


    private FragmentTransaction transaction;
    private FragmentManager manager;
    private MemberOneFragment oneFragment;
    private MemberTwoFragment twoFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        ButterKnife.bind(this);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        oneFragment = new MemberOneFragment();
        twoFragment = new MemberTwoFragment();
        transaction.add(R.id.frag, oneFragment);
        transaction.add(R.id.frag, twoFragment);
        transaction.show(oneFragment);
        transaction.hide(twoFragment);
        transaction.commit();
    }

    @OnClick({R.id.img_one,R.id.img_two})
    public void onClick(View v){
        transaction = manager.beginTransaction();
        switch (v.getId()){
            case R.id.img_one:
                transaction.show(oneFragment);
                transaction.hide(twoFragment);
                break;
            case R.id.img_two:
                transaction.show(twoFragment);
                transaction.hide(oneFragment);
                break;
        }
        transaction.commit();
    }
}