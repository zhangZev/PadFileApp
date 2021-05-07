package way.kichain.padfileapp.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.wang.avi.AVLoadingIndicatorView;
import com.wang.avi.indicators.BallSpinFadeLoaderIndicator;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;

import way.kichain.padfileapp.R;

/**
 * 开发者: ZhangZev
 * 时间: 2019/1/22
 * 描述：
 */
public class LoadingDialog extends Dialog {
    private Context mContext;
    AVLoadingIndicatorView indicator;

    public LoadingDialog(@NonNull Context context) {
        super(context,R.style.LoadingDialog);
        mContext = context;
    }

    public LoadingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_loading, null);
        indicator = view.findViewById(R.id.indicator);
        indicator.setIndicator(new BallSpinFadeLoaderIndicator());
        indicator.show();
        setCancelable(true);
        setContentView(view);
    }


    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(indicator != null){
           try{
               indicator.hide();
           } catch (Exception e){

           }
        }
    }
}
