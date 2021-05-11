package way.kichain.padfileapp.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

import way.kichain.padfileapp.views.LoadingDialog;

public class WebShowUtils {
    public WebView webview;
    public PDFView pdfView;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1 || msg.what == 0) {
                webview.loadUrl(url);
                mdialog.dismiss();
            }
        }
    };
    private OnLoadListener listener;
    private String url;
    private final LoadingDialog mdialog;

    public interface OnLoadListener {
        void onLoad();//回调方法
    }

    public void setOnLoadListener(OnLoadListener listener) {
        this.listener = listener;
    }

    public WebShowUtils(Context context,WebView webview, PDFView pdfView) {
        this.webview = webview;
        this.pdfView = pdfView;
        mdialog = new LoadingDialog(context);
        webview.getSettings().setAllowFileAccess(true);// 设置允许访问文件数据
        webview.getSettings().setSupportZoom(false);//支持放大网页功能
        webview.getSettings().setBuiltInZoomControls(false);//支持缩小网页功能
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setJavaScriptEnabled(true);//支持JAVA
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webview.getSettings().setTextZoom(100);
        webview.setBackgroundColor(0); // 设置背景色
        webview.getBackground().setAlpha(0);

    }

    public void showSdView(File file) {
        if(mdialog!=null){
            mdialog.show();
        }
                 /*File dirFile = new File(DocumentFormatConvertUtils.htmlPath);
        if (file.exists()) {
            FileUtils.deleteDirOrFile(dirFile);
        }*/
        url = "file:///" + DocumentFormatConvertUtils.htmlPath + DocumentFormatConvertUtils.htmlName;
        // 创建File对象
        if (file.getName().endsWith(".doc") || file.getName().endsWith(".DOC")) {
            pdfView.setVisibility(View.GONE);
            webview.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    DocumentFormatConvertUtils.doc2html(file.getPath(), file.getName());
                    handler.sendEmptyMessage(0);
                }
            }).start();
        } else if (file.getName().endsWith(".docx") || file.getName().endsWith(".DOCX")) {
            pdfView.setVisibility(View.GONE);
            webview.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    DocumentFormatConvertUtils.docx2html(file.getPath(), file.getName());
                    handler.sendEmptyMessage(1);
                }
            }).start();
        } else {
            webview.setVisibility(View.GONE);
            pdfView.setVisibility(View.VISIBLE);
            pdfView.fromFile(file).load();
        }

    }

}
