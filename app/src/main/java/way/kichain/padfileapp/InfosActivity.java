package way.kichain.padfileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import way.kichain.padfileapp.utils.WebShowUtils;

public class InfosActivity extends BaseActivity {
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.pdfView)
    PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infso);
        ButterKnife.bind(this);
        File file = (File) getIntent().getSerializableExtra("INTENT_DATA");
        WebShowUtils webShowUtils = new WebShowUtils(this,webview,pdfView);
        webShowUtils.showSdView(file);
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}