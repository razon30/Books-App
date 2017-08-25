package razon.nctbteachersguide.activity;

import android.app.ProgressDialog;
import android.util.Log;

import com.daimajia.androidanimations.library.Techniques;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.viksaa.sssplash.lib.activity.AwesomeSplash;
import com.viksaa.sssplash.lib.cnst.Flags;
import com.viksaa.sssplash.lib.model.ConfigSplash;

import io.realm.Realm;
import razon.nctbteachersguide.R;
import razon.nctbteachersguide.model.Book;
import razon.nctbteachersguide.utils.NetworkAvailable;
import razon.nctbteachersguide.utils.Paths;
import razon.nctbteachersguide.utils.ShowNetworkError;
import razon.nctbteachersguide.utils.SimpleActivityTransition;

public class SplashActivity extends AwesomeSplash {


    Realm realm;
    ValueEventListener valueEventListener;
    DatabaseReference databaseReference;

    @Override
    public void initSplash(ConfigSplash configSplash) {

   /* you don't have to override every property */

        configSplash.setBackgroundColor(R.color.colorPrimary); //any color you want form colors.xml
        configSplash.setAnimCircularRevealDuration(500); //int ms
        configSplash.setRevealFlagX(Flags.REVEAL_RIGHT);  //or Flags.REVEAL_LEFT
        configSplash.setRevealFlagY(Flags.REVEAL_BOTTOM); //or Flags.REVEAL_TOP

        //Choose LOGO OR PATH; if you don't provide String value for path it's logo by default

        //Customize Logo
//        configSplash.setLogoSplash(R.mipmap.ic_launcher); //or any other drawable
//        configSplash.setAnimLogoSplashDuration(2000); //int ms
//        configSplash.setAnimLogoSplashTechnique(Techniques.Bounce); //choose one form Techniques (ref: https://github.com/daimajia/AndroidViewAnimations)


        //Customize Path
        configSplash.setPathSplash(Paths.BOOK); //set path String
        configSplash.setOriginalHeight(400); //in relation to your svg (path) resource
        configSplash.setOriginalWidth(400); //in relation to your svg (path) resource
        configSplash.setAnimPathStrokeDrawingDuration(1500);
        configSplash.setPathSplashStrokeSize(3); //I advise value be <5
        configSplash.setPathSplashStrokeColor(R.color.colorAccent); //any color you want form colors.xml
        configSplash.setAnimPathFillingDuration(1500);
        configSplash.setPathSplashFillColor(R.color.strokeColor); //path object filling color


        //Customize Title
        configSplash.setTitleSplash("NCTB Free Books");
        configSplash.setTitleTextColor(R.color.appName);
        configSplash.setTitleTextSize(30f); //float value
        configSplash.setAnimTitleDuration(1000);
        configSplash.setAnimTitleTechnique(Techniques.FlipInX);
        configSplash.setTitleFont("font/Ubuntu-R.ttf"); //provide string to your font located in assets/fonts/

    }

    @Override
    public void animationsFinished() {

        realm = Realm.getDefaultInstance();
        if (realm.where(Book.class).count() < 1) {

            if (new NetworkAvailable().isNetworkAvailable(this)){
                loadData(realm);
            }else {
                new ShowNetworkError(this);
            }


        }else {
            SimpleActivityTransition.goToNextActivity(this, HomeActivity.class);
            finish();
        }


    }



    private void loadData(final Realm realm) {

        final ProgressDialog progressDialog = new ProgressDialog(SplashActivity.this);
        progressDialog.setMessage("Please wait..");
        progressDialog.show();

        databaseReference = FirebaseDatabase.getInstance().getReference("Books");

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot datasnap : dataSnapshot.getChildren()) {

                    final Book book = datasnap.getValue(Book.class);
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.copyToRealm(book);
                        }
                    });

                    Log.d("idfg", book.getId());

                }

                progressDialog.dismiss();
                SimpleActivityTransition.goToNextActivity(SplashActivity.this, HomeActivity.class);
                finish();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        databaseReference.addValueEventListener(valueEventListener);


    }

    @Override
    protected void onPause() {

        if (valueEventListener != null && databaseReference != null) {
            databaseReference.removeEventListener(valueEventListener);
        }

        if (realm!=null && !realm.isClosed()) realm.close();

        super.onPause();

    }

    @Override
    protected void onStop() {
        if (valueEventListener != null && databaseReference != null) {
            databaseReference.removeEventListener(valueEventListener);
        }
        if (realm!=null && !realm.isClosed()) realm.close();
        super.onStop();
    }
}
