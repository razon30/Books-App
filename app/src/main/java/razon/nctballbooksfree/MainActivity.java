package razon.nctballbooksfree;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import razon.nctballbooksfree.Model.Book;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bookList1 = new ArrayList<>();
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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Old");


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    String name = dataSnapshot1.child("name").getValue().toString();
                    String url = dataSnapshot1.child("url").getValue().toString();
                    String writter = dataSnapshot1.child("author").getValue().toString();
                    String size = dataSnapshot1.child("size").getValue().toString();
                    String clas = dataSnapshot1.child("cls").getValue().toString();
                    String id = i + "";

                    if (clas.equals("1")) {
                        clas = "One";
                    }
                    if (clas.equals("2")) {
                        clas = "Two";
                    }
                    if (clas.equals("3")) {
                        clas = "Three";
                    }
                    if (clas.equals("4")) {
                        clas = "Four";
                    }
                    if (clas.equals("5")) {
                        clas = "Five";
                    }
                    if (clas.equals("6")) {
                        clas = "Six";
                    }
                    if (clas.equals("7")) {
                        clas = "Seven";
                    }
                    if (clas.equals("8")) {
                        clas = "Eight";
                    }
                    if (clas.equals("910")) {
                        clas = "Nine-Ten";
                    }
                    if (clas.equals("1112")) {
                        clas = "Eleven-Twelve";
                    }


                    Book book = new Book(name,url,writter,size,clas,id);

                    bookList1.add(book);

                    i++;

                }

                FirebaseDatabase database1 = FirebaseDatabase.getInstance();
                DatabaseReference myRef1 = database1.getReference("books");
                myRef1.setValue(bookList1);

                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
