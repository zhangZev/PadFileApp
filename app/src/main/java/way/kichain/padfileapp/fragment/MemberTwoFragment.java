package way.kichain.padfileapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import way.kichain.padfileapp.InfosActivity;
import way.kichain.padfileapp.R;
import way.kichain.padfileapp.adapter.MemberAdapter;
import way.kichain.padfileapp.model.VipModel;
import way.kichain.padfileapp.utils.DocumentFormatConvertUtils;
import way.kichain.padfileapp.utils.FileUtils;
import way.kichain.padfileapp.utils.WebShowUtils;

import static way.kichain.padfileapp.MainActivity.fileDirs;
import static way.kichain.padfileapp.MainActivity.memberFiles;

public class MemberTwoFragment extends Fragment {

    WebView webview;
    PDFView pdfView;
    private View view;
    private WebShowUtils webShowUtils;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_member_two, null);
        initView();
        return view;
    }

    private void initView() {
        webview = view.findViewById(R.id.webview);
        pdfView = view.findViewById(R.id.pdfView);
        initWeb();
        //获取党员内容
        try {
            File file_home = new File(FileUtils.getSDPath() + fileDirs[3] + memberFiles[1]);
            if (file_home.exists()) {
                List<File> vipFiles = FileUtils.listFilesInDir(file_home, false);
                if (vipFiles != null) {
                    if (vipFiles.size() > 0) {
                        File file = vipFiles.get(1);
                        webShowUtils.showSdView(file);
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    private void initWeb() {
        webShowUtils = new WebShowUtils(getContext(),webview, pdfView);
    }

}
