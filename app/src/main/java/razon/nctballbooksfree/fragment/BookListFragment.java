package razon.nctballbooksfree.fragment;


import android.app.DownloadManager;
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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import me.drakeet.materialdialog.MaterialDialog;
import razon.nctballbooksfree.BuildConfig;
import razon.nctballbooksfree.R;
import razon.nctballbooksfree.activity.HomeActivity;
import razon.nctballbooksfree.adapter.AdapterBookListRecycler;
import razon.nctballbooksfree.model.Book;
import razon.nctballbooksfree.utils.ClassificationNode;
import razon.nctballbooksfree.utils.ClickListener;
import razon.nctballbooksfree.utils.MyBookListRecyclerView;
import razon.nctballbooksfree.utils.NetworkAvailable;
import razon.nctballbooksfree.utils.RecyclerTOuchListener;

import static android.app.DownloadManager.STATUS_FAILED;
import static android.app.DownloadManager.STATUS_PAUSED;
import static android.app.DownloadManager.STATUS_PENDING;
import static android.app.DownloadManager.STATUS_RUNNING;
import static android.app.DownloadManager.STATUS_SUCCESSFUL;
import static android.content.Context.DOWNLOAD_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookListFragment extends Fragment {

    MyBookListRecyclerView recyclerView;
    AdapterBookListRecycler adapter;
    LinearLayoutManager staggeredGridLayoutManager;
    ArrayList<Book> classList;
    String id;

    public File file;
    public String folder = "NCTB";
    String fileName = "book";
    String url;

    DownloadManager downloadManager;
    long downloadId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_book_list, container, false);

        id = getArguments().getString("id");

        HomeActivity.CurrentPage = ClassificationNode.BOOK_LIST;

        if (id.equals("all")) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("All Books");
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Class " + id);
        }

        classList = new ArrayList<>();
        populateData(id);
        recyclerView = (MyBookListRecyclerView) view.findViewById(R.id.book_list_recycler);
        adapter = new AdapterBookListRecycler(classList, getActivity());
        staggeredGridLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTOuchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onCLick(View v, int position) {

                downloadOfShow(classList.get(position));

            }

            @Override
            public void onLongClick(View v, int position) {

            }
        }));

        Log.d("idfg", classList.size() + "");
        return view;
    }

    private void downloadOfShow(Book book) {

        url = book.getUrl();
        fileName = book.getFileName();

        downloadManager = (DownloadManager) getActivity().getSystemService(DOWNLOAD_SERVICE);

        File direct = new File(Environment.getExternalStorageDirectory() + "/" + folder);
        if (!direct.exists()) {
            direct.mkdirs();
        }
        try {
            this.file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folder + "/" + fileName);
            if (file.exists()) {

                PdfFragment bookListFragment = new PdfFragment();
                Bundle bundle = new Bundle();
                bundle.putString("fileName", fileName);
                bookListFragment.setArguments(bundle);
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.fade_out);
                transaction.replace(R.id.fragment_container, bookListFragment).addToBackStack("pdf");
                transaction.commit();

//                Intent pass = new Intent(getActivity(), PdfView.class);
//                pass.putExtra("fileName", fileName);
//                startActivity(pass);
                return;
            }

            if (new NetworkAvailable().isNetworkAvailable(getActivity())) {

                final MaterialDialog materialDialog = new MaterialDialog(getActivity());
                materialDialog.setMessage("The file 'NCTB/" + fileName + "' do not exists. Need to download.\nSize:" + book.getSize());

                materialDialog.setPositiveButton("Download", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        downloadFile(url, fileName);
                        materialDialog.dismiss();
                    }
                });
                materialDialog.setNegativeButton("Cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        materialDialog.dismiss();
                    }
                });

                materialDialog.show();

            }


        } catch (ActivityNotFoundException ignored) {
            Log.d("ignored1", ignored.getMessage());
            Toast.makeText(getActivity(), ignored.getMessage(), Toast.LENGTH_SHORT).show();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    private void downloadFile(String url, String fileName) {

        Uri uri = Uri.parse(url);

        try {
            Request request = new DownloadManager.Request(uri);
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            // request.setDescription("");
            request.setTitle("Downloading: " + fileName);
            request.setDestinationInExternalPublicDir("/" + folder, fileName);
            request.setAllowedOverRoaming(false);
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
            downloadId = downloadManager.enqueue(request);
            getActivity().registerReceiver(downloadReceiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        } catch (Exception ignored) {
            Log.d("ignored2", ignored.getMessage());
            Toast.makeText(getActivity(), ignored.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }

    public BroadcastReceiver downloadReceiver = new BroadcastReceiver() {
        public void onReceive(Context arg0, Intent arg1) {
            DownloadManager.Query q = new DownloadManager.Query();
            q.setFilterById(downloadId);
            Cursor cursor = downloadManager.query(q);
            if (cursor.moveToFirst()) {
                String message = BuildConfig.FLAVOR;
                int status = cursor.getInt(cursor.getColumnIndex("status"));
                if (status == STATUS_SUCCESSFUL) {
                    message = "Download successful";

                    file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folder + "/" + fileName);
                    if (file.exists()) {

                        PdfFragment bookListFragment = new PdfFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString("fileName", fileName);
                        bookListFragment.setArguments(bundle);
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.fade_out);
                        transaction.replace(R.id.fragment_container, bookListFragment).addToBackStack("pdf");
                        transaction.commit();

//                Intent pass = new Intent(getActivity(), PdfView.class);
//                pass.putExtra("fileName", fileName);
//                startActivity(pass);
                        return;
                    }

                } else if (status == STATUS_RUNNING) {
                    message = "Downloading";
                } else if (status == STATUS_PENDING) {
                    message = "Download Pending";
                } else if (status == STATUS_PAUSED) {
                    message = "Download Paused";
                } else if (status == STATUS_FAILED) {
                    message = "Download failed";
                }
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }
        }
    };

    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(downloadReceiver, new IntentFilter("android.intent.action.DOWNLOAD_COMPLETE"));
    }

    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(this.downloadReceiver);
    }

    private void populateData(String id) {

        Realm realm = Realm.getDefaultInstance();
        RealmResults<Book> realmResults;

        if (id.equals("all")) {
            realmResults = realm.where(Book.class).findAll();
        } else {
            realmResults = realm.where(Book.class).equalTo("cls", id).findAll();
        }
        classList.addAll(realmResults);
        //  realm.close();

    }


    public BookListFragment() {
        // Required empty public constructor
    }


}
