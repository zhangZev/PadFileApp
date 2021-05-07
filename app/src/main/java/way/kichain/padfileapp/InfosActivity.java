package way.kichain.padfileapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import way.kichain.padfileapp.utils.DocumentFormatConvertUtils;
import way.kichain.padfileapp.utils.FileUtils;

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
        initWeb();
        File file = (File) getIntent().getSerializableExtra("INTENT_DATA");
        showSdView(file);
    }

    private void initWeb() {
        webview.getSettings().setAllowFileAccess(true);// 设置允许访问文件数据
        webview.getSettings().setSupportZoom(false);//支持放大网页功能
        webview.getSettings().setBuiltInZoomControls(false);//支持缩小网页功能
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setJavaScriptEnabled(true);//支持JAVA
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setTextZoom(100);
    }

    private void showSdView(File file) {
        File dirFile = new File(DocumentFormatConvertUtils.htmlPath);
        if (file.exists()) {
            FileUtils.deleteDirOrFile(dirFile);
        }
        String url = "file:///"+DocumentFormatConvertUtils.htmlPath+DocumentFormatConvertUtils.htmlName;
        // 创建File对象
        if(file.getName().endsWith(".doc") || file.getName().endsWith(".DOC")){
            pdfView.setVisibility(View.GONE);
            webview.setVisibility(View.VISIBLE);
            DocumentFormatConvertUtils.doc2html(file.getPath(),file.getName());
            webview.loadUrl(url);
        }else if(file.getName().endsWith(".docx") || file.getName().endsWith(".DOCX")){
            pdfView.setVisibility(View.GONE);
            webview.setVisibility(View.VISIBLE);
            DocumentFormatConvertUtils.docx2html(file.getPath(),file.getName());
            webview.loadUrl(url);
        }else {
            webview.setVisibility(View.GONE);
            pdfView.setVisibility(View.VISIBLE);
            pdfView.fromFile(file).load();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}