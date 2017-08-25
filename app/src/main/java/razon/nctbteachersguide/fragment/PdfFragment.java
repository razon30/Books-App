package razon.nctbteachersguide.fragment;

import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;

import java.io.File;

import razon.nctbteachersguide.R;
import razon.nctbteachersguide.activity.HomeActivity;
import razon.nctbteachersguide.utils.ClassificationNode;

public class PdfFragment extends Fragment {

    Integer pageNumber = Integer.valueOf(0);
    PDFView pdfView;

    public String folder = "NCTB";
    String fileName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pdf, container, false);

        HomeActivity.CurrentPage = ClassificationNode.PDF_VIew;

        fileName = getArguments().getString("fileName");

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folder + "/" + fileName);


//        Uri path = Uri.fromFile(file);
//        try {
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setDataAndType(path, "application/pdf");
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            startActivity(intent);
//        } catch (ActivityNotFoundException e) {
//            Toast.makeText(getActivity(), "PDF Reader application is not installed in your device", Toast.LENGTH_SHORT).show();
////            tv_loading
////                    .setError("PDF Reader application is not installed in your device");
//        }
//

        pdfView = (PDFView) view.findViewById(R.id.pdfView);
       // String fileName = getIntent().getExtras().getString("fileName");
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(fileName);
        pdfView.fromFile(file).defaultPage(pageNumber.intValue()).enableAnnotationRendering(true)
                .scrollHandle(new DefaultScrollHandle(getActivity())).enableSwipe(true).load();

        return view;
    }
    public PdfFragment() {
        // Required empty public constructor
    }


}
