package way.kichain.padfileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;


import com.aspose.words.Document;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import way.kichain.padfileapp.adapter.AdvanceAdapter;
import way.kichain.padfileapp.utils.DocumentFormatConvertUtils;
import way.kichain.padfileapp.utils.FileUtils;

import static way.kichain.padfileapp.MainActivity.fileDirs;

/**
 * 促进会概况
 */
public class AdvancementActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.pdfView)
    PDFView pdfView;
    private StringBuilder strBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advancement);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        webview.getSettings().setAllowFileAccess(true);// 设置允许访问文件数据
        webview.getSettings().setSupportZoom(false);//支持放大网页功能
        webview.getSettings().setBuiltInZoomControls(false);//支持缩小网页功能
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setJavaScriptEnabled(true);//支持JAVA
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setTextZoom(100);
        getFiles();
    }

    private void getFiles() {
        //获取首页内容
        File file_home = new File(FileUtils.getSDPath() + fileDirs[1]);
        List<File> mHomeFils = FileUtils.listFilesInDir(file_home, false);
        AdvanceAdapter mAdapter = new AdvanceAdapter(this, mHomeFils);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AdvanceAdapter.onItemClickListener() {
            @Override
            public void onClick(File file) {
                showSdView(file);
                //pdfView.loadDataWithBaseURL(url,style,"text/html","utf-8", null);
            }
        });
        if(mHomeFils!=null && mHomeFils.size()>0){
            showSdView(mHomeFils.get(0));
        }
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


}