package way.kichain.padfileapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;


import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import way.kichain.padfileapp.adapter.AdvanceAdapter;
import way.kichain.padfileapp.utils.FileUtils;

import static way.kichain.padfileapp.MainActivity.fileDirs;

/**
 * 促进会概况
 */
public class AdvancementActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.pdfView)
    PDFView pdfView;
    private StringBuilder strBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advancement);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        getFiles();
    }

    private void getFiles() {
        //获取首页内容
        File file_home = new File(FileUtils.FILE_DIR + fileDirs[1]);
        List<File> mHomeFils = FileUtils.listFilesInDir(file_home, false);
        AdvanceAdapter mAdapter = new AdvanceAdapter(this, mHomeFils);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new AdvanceAdapter.onItemClickListener() {
            @Override
            public void onClick(File file) {
                pdfView.fromFile(file).load();
            }
        });

    }
}