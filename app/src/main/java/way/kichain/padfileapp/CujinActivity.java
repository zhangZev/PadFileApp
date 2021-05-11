package way.kichain.padfileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import way.kichain.padfileapp.fragment.CujintThreeFragment;
import way.kichain.padfileapp.fragment.CujintoneFragment;
import way.kichain.padfileapp.fragment.CujintwoFragment;
import way.kichain.padfileapp.fragment.MemberOneFragment;
import way.kichain.padfileapp.fragment.MemberTwoFragment;

public class CujinActivity extends BaseActivity {

    private FragmentTransaction transaction;
    private FragmentManager manager;
    private CujintoneFragment oneFragment;
    private CujintwoFragment twoFragment;
    private CujintThreeFragment threeFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cujin);
        ButterKnife.bind(this);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        oneFragment = new CujintoneFragment();
        twoFragment = new CujintwoFragment();
        threeFragment = new CujintThreeFragment();
        transaction.add(R.id.frag, oneFragment);
        transaction.add(R.id.frag, twoFragment);
        transaction.add(R.id.frag, threeFragment);
        transaction.show(oneFragment);
        transaction.hide(twoFragment);
        transaction.hide(threeFragment);
        transaction.commit();
    }

    @OnClick({R.id.img_one,R.id.img_two,R.id.img_three})
    public void onClick(View v){
        transaction = manager.beginTransaction();
        switch (v.getId()){
            case R.id.img_one:
                transaction.show(oneFragment);
                transaction.hide(twoFragment);
                transaction.hide(threeFragment);
                break;
            case R.id.img_two:
                transaction.show(twoFragment);
                transaction.hide(oneFragment);
                transaction.hide(threeFragment);
                break;
            case R.id.img_three:
                transaction.show(threeFragment);
                transaction.hide(oneFragment);
                transaction.hide(twoFragment);
                break;
        }
        transaction.commit();
    }
}