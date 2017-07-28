package razon.nctballbooksfree.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.File;

import razon.nctballbooksfree.R;

public class PdfView extends AppCompatActivity {
    Integer pageNumber = Integer.valueOf(0);
    PDFView pdfView;

    public String folder = "NCTB";
    String fileName;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pdfview);
       // setupAdAtBottom();
        fileName = getIntent().getStringExtra("fileName");
        pdfView = (PDFView) findViewById(R.id.pdfView);
        String fileName = getIntent().getExtras().getString("fileName");
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folder + "/" + fileName);
        getSupportActionBar().setTitle(fileName);
        pdfView.fromFile(file).defaultPage(pageNumber.intValue()).enableAnnotationRendering(true).scrollHandle(new DefaultScrollHandle(this)).enableSwipe(true).load();
    }
}
