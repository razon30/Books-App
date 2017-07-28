package razon.nctballbooksfree.activity;

import android.app.DownloadManager;
import android.app.DownloadManager.Query;
import android.app.DownloadManager.Request;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import razon.nctballbooksfree.BuildConfig;
import razon.nctballbooksfree.R;
import razon.nctballbooksfree.model.Book;

import static android.app.DownloadManager.STATUS_FAILED;
import static android.app.DownloadManager.STATUS_PAUSED;
import static android.app.DownloadManager.STATUS_PENDING;
import static android.app.DownloadManager.STATUS_RUNNING;
import static android.app.DownloadManager.STATUS_SUCCESSFUL;

public class MainActivity extends AppCompatActivity {

    ArrayList<Book> bookList1;
//    ArrayList<Book> bookList2;
//    ArrayList<Book> bookList3;
//    ArrayList<Book> bookList4;
//    ArrayList<Book> bookList5;
//    ArrayList<Book> bookList6;
//    ArrayList<Book> bookList7;
//    ArrayList<Book> bookList8;
//    ArrayList<Book> bookList910;
//    ArrayList<Book> bookList1112;

    String url = "https://archive.org/download/ebooks_17/Biology-1-12-17.pdf";
//    public File file;
//    public String folder = "NCTB_Books";
//    long did;
//    DownloadManager dManager;

    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
        public void onReceive(Context arg0, Intent arg1) {
            Query q = new Query();
            q.setFilterById(did);
            Cursor cursor = dManager.query(q);
            if (cursor.moveToFirst()) {
                String message = BuildConfig.FLAVOR;
                int status = cursor.getInt(cursor.getColumnIndex("status"));
                if (status == STATUS_SUCCESSFUL) {
                    message = "Download successful";
                }  else if (status == STATUS_RUNNING) {
                    message = "Downloading";
                } else if (status == STATUS_PENDING) {
                    message = "Download Pending";
                } else if (status == STATUS_PAUSED) {
                    message = "Download Paused";
                }
                else if (status == STATUS_FAILED) {
                    message = "Download failed";
                }
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }
        }
    };

    public File file;
    public String folder = "NCTB";
    String fileName = "Biology-1-12-17.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dManager = (DownloadManager)getSystemService(DOWNLOAD_SERVICE);

        File direct = new File(Environment.getExternalStorageDirectory() + "/" + folder);
        if (!direct.exists()) {
            direct.mkdirs();
        }
        try {
            this.file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folder + "/" + fileName);
            if (file.exists()) {
                Intent pass = new Intent(this, PdfView.class);
                pass.putExtra("fileName", fileName);
                startActivity(pass);
                return;
            }
            downloadFile(url, fileName);
        } catch (ActivityNotFoundException ignored) {
            Log.d("ignored1", ignored.getMessage());
            Toast.makeText(this, ignored.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception e2) {
            e2.printStackTrace();
        }









//        Medescope.getInstance(this)
//                .enqueue("123456", url,
//                        "Biology-1-12-17.pdf",
//                        "Biology 1st",
//                        "{some:'samplejson'}"
//                );

       // Download_Read(url, "Biology-1-12-17.pdf");

    //   bookList1 = new ArrayList<>();
//        bookList2 = new ArrayList<>();
//        bookList3 = new ArrayList<>();
//        bookList4 = new ArrayList<>();
//        bookList5 = new ArrayList<>();
//        bookList6 = new ArrayList<>();
//        bookList7 = new ArrayList<>();
//        bookList8 = new ArrayList<>();
//        bookList910 = new ArrayList<>();
//        bookList1112 = new ArrayList<>();

        // Write a message to the database
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("books");
//
//
//        final ProgressDialog progressDialog = new ProgressDialog(this);
//        progressDialog.show();
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                int i = 0;
//                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
//
//                    String name = dataSnapshot1.child("name").getValue().toString();
//                    String url = dataSnapshot1.child("url").getValue().toString().split("~")[0];
//                    String writter = dataSnapshot1.child("writter").getValue().toString();
//                    String size = dataSnapshot1.child("size").getValue().toString();
//                    String clas = dataSnapshot1.child("cls").getValue().toString();
//                    String id = dataSnapshot1.child("id").getValue().toString();
//
//                    String fileName = dataSnapshot1.child("url").getValue().toString().split("~")[1];
//

//
//                    if (clas.equals("1")) {
//                        clas = "One";
//                    }
//                    if (clas.equals("2")) {
//                        clas = "Two";
//                    }
//                    if (clas.equals("3")) {
//                        clas = "Three";
//                    }
//                    if (clas.equals("4")) {
//                        clas = "Four";
//                    }
//                    if (clas.equals("5")) {
//                        clas = "Five";
//                    }
//                    if (clas.equals("6")) {
//                        clas = "Six";
//                    }
//                    if (clas.equals("7")) {
//                        clas = "Seven";
//                    }
//                    if (clas.equals("8")) {
//                        clas = "Eight";
//                    }
//                    if (clas.equals("910")) {
//                        clas = "Nine-Ten";
//                    }
//                    if (clas.equals("1112")) {
//                        clas = "Eleven-Twelve";
//                    }


//                    Book book = new Book(name,url,writter,size,clas,id,fileName);
//
//                    bookList1.add(book);
//
//                    i++;
//
//                }
//
//                FirebaseDatabase database1 = FirebaseDatabase.getInstance();
//                DatabaseReference myRef1 = database1.getReference("Books");
//                myRef1.setValue(bookList1);
////
//                progressDialog.dismiss();
////
//           }
////
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
////
//            }
//        });
//



    }
    DownloadManager dManager;
    long did;

    public void downloadFile(String fileUrl, String fileName) {
        Uri uri = Uri.parse(url);

        try {
            Request request = new Request(uri);
            request.setNotificationVisibility(Request.VISIBILITY_VISIBLE);
            request.setDescription("From: zoaddar.org");
            request.setTitle("Downloading: " + fileName);
            request.setDestinationInExternalPublicDir("/" + folder, fileName);
            request.setAllowedOverRoaming(false);
            request.setAllowedNetworkTypes(Request.NETWORK_MOBILE | Request.NETWORK_WIFI);
            did = dManager.enqueue(request);
            registerReceiver(downloadReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        } catch (Exception ignored) {
            Log.d("ignored2", ignored.getMessage());
            Toast.makeText(this, ignored.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    protected void onResume() {
        super.onResume();
        registerReceiver(downloadReceiver, new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
    }

    protected void onPause() {
        super.onPause();
        unregisterReceiver(this.downloadReceiver);
    }


//    public void Download_Read(String fileUrl, String fileName) {
//        File direct = new File(Environment.getExternalStorageDirectory() + "/" + this.folder);
//        if (!direct.exists()) {
//            direct.mkdirs();
//        }
//
//        downloadFile(fileUrl, fileName);
//
//        try {
//            this.file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + this.folder + "/" + fileName);
//            if (this.file.exists()) {
//                Intent pass = new Intent(this, PdfView.class);
//                pass.putExtra("fileName", fileName);
//                startActivity(pass);
//                return;
//            }
//            downloadFile(fileUrl, fileName);
//        } catch (ActivityNotFoundException e) {
//        } catch (Exception e2) {
//            e2.printStackTrace();
//        }
//    }
//
//    public void downloadFile(String fileUrl, String fileName) {
////        if (fileUrl.equals(BuildConfig.FLAVOR) || !isNetworkAvailable()) {
////            Toast.makeText(getApplicationContext(), "Device might not be connected to Internet", 1).show();
////            return;
////        }
//        try {
//            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileUrl));
//            request.setNotificationVisibility(1);
//            request.setDescription("From: zoaddar.org");
//            request.setTitle("Downloading: " + fileName);
//            request.setDestinationInExternalPublicDir("/" + this.folder, fileName);
//            this.did = this.dManager.enqueue(request);
//        } catch (Exception e) {
//        }
//    }
//
//    protected void onResume() {
//        super.onResume();
//        registerReceiver(this.downloadReceiver, new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
//    }
//
//    protected void onPause() {
//        super.onPause();
//        unregisterReceiver(this.downloadReceiver);
//    }
//
//    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
//        public void onReceive(Context arg0, Intent arg1) {
//            DownloadManager.Query q = new DownloadManager.Query();
//            q.setFilterById(new long[]{MainActivity.this.did});
//            Cursor cursor = MainActivity.this.dManager.query(q);
//            if (cursor.moveToFirst()) {
//                String message = BuildConfig.FLAVOR;
//                int status = cursor.getInt(cursor.getColumnIndex("status"));
//                if (status == 8) {
//                    message = "Download successful";
//                } else if (status == 16) {
//                    message = "Download failed";
//                }
//                Toast.makeText(MainActivity.this.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
//            }
//        }
//    };

}
