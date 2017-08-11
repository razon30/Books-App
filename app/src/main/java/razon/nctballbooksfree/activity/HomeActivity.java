package razon.nctballbooksfree.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.codemybrainsout.ratingdialog.RatingDialog;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yarolegovich.slidingrootnav.SlideGravity;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import razon.nctballbooksfree.R;
import razon.nctballbooksfree.fragment.BookListFragment;
import razon.nctballbooksfree.fragment.HomeFragment;
import razon.nctballbooksfree.utils.ClassificationNode;
import razon.nctballbooksfree.utils.SharePreferenceSingleton;

public class HomeActivity extends AppCompatActivity {

    MaterialRippleLayout classWiseBooks;
    MaterialRippleLayout allBooks;
    MaterialRippleLayout rateTheApp;
    MaterialRippleLayout otherApps;

    ImageView share;

    public static InterstitialAd mInterstitialAd;
    private AdView mAdView;

    SlidingRootNavBuilder slidingRootNavBuilder;
    Toolbar toolbar;

    public static String CurrentPage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initialization();
        worksOnAds();
        worksOnNavbar();

        classWiseBooks.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        showCustomRateMeDialog();

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "You can get all NCTB books for free\nhttps://play.google.com/store/apps/details?id="+getPackageName());
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share with"));

            }
        });

    }

    private void worksOnNavbar() {

        classWiseBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classWiseBooks.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                allBooks.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
                rateTheApp.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
                otherApps.setBackgroundColor(getResources().getColor(R.color.backgroundColor));

                if (CurrentPage.equals(ClassificationNode.BOOK_LIST) || CurrentPage.equals(ClassificationNode.PDF_VIew) ){
                    HomeFragment fragment = new HomeFragment();
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.slide_in_from_left, R.anim.fade_out);
                    transaction.replace(R.id.fragment_container, fragment);
                    transaction.commit();
                }else {

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
                }

            }
        });
        allBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classWiseBooks.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
                allBooks.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                rateTheApp.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
                otherApps.setBackgroundColor(getResources().getColor(R.color.backgroundColor));


                BookListFragment bookListFragment = new BookListFragment();
                Bundle bundle = new Bundle();
                bundle.putString("id", "all");
                bookListFragment.setArguments(bundle);
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.slide_in_from_right, R.anim.fade_out);
                transaction.replace(R.id.fragment_container, bookListFragment);
                transaction.commit();

            }
        });
        rateTheApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rateTheApp.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                allBooks.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
                classWiseBooks.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
                otherApps.setBackgroundColor(getResources().getColor(R.color.backgroundColor));

                showCustomRateMeDialogClick();

            }
        });
        otherApps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otherApps.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                allBooks.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
                rateTheApp.setBackgroundColor(getResources().getColor(R.color.backgroundColor));
                classWiseBooks.setBackgroundColor(getResources().getColor(R.color.backgroundColor));

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/developer?id=Md.+Razon+Hossain")));

            }
        });

    }

    private void initialization() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        slidingRootNavBuilder = new SlidingRootNavBuilder(this);
        slidingRootNavBuilder.withToolbarMenuToggle(toolbar)
                .withMenuLayout(R.layout.layout_drawer)
                .withDragDistance(140) //Horizontal translation of a view. Default == 180dp
                .withRootViewScale(0.7f) //Content view's scale will be interpolated between 1f and 0.7f. Default == 0.65f;
                .withRootViewElevation(10) //Content view's elevation will be interpolated between 0 and 10dp. Default == 8.
                .withRootViewYTranslation(4) //Cont
                .withGravity(SlideGravity.LEFT)
                .inject();

        classWiseBooks = (MaterialRippleLayout) findViewById(R.id.classWiseBooks);
        allBooks = (MaterialRippleLayout) findViewById(R.id.allBooks);
        rateTheApp = (MaterialRippleLayout) findViewById(R.id.rateTheApp);
        otherApps = (MaterialRippleLayout) findViewById(R.id.otherApp);
        share = (ImageView) findViewById(R.id.share);

    }

    private void worksOnAds() {

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                mAdView.setVisibility(View.GONE);
            }
        });

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

    }

    public void showInterstitialAd() {
        if (mInterstitialAd.isLoaded()) {
            int count = SharePreferenceSingleton.getInstance(HomeActivity.this).getInt("count");
            if (count%2==0){
             mInterstitialAd.show();
         }else {

                count++;
                SharePreferenceSingleton.getInstance(HomeActivity.this).saveInt("count",count);

         }


        } else {
            mInterstitialAd.loadAd(new AdRequest.Builder().build());
        }
    }

    private void showCustomRateMeDialogClick() {

        final RatingDialog ratingDialog = new RatingDialog.Builder(this)
                .icon(getResources().getDrawable(R.drawable.logo))
             //   .session(7)
                .threshold(3)
                .ratingBarBackgroundColor(R.color.backgroundColor)
                .title("How was your experience with us?")
                .titleTextColor(R.color.black)
                .positiveButtonText("Not Now")
                .negativeButtonText("Never")
                .positiveButtonTextColor(R.color.colorPrimary)
                .negativeButtonTextColor(R.color.grey_500)
                .formTitle("Submit Feedback")
                .formHint("Tell us where we can improve")
                .formSubmitText("Submit")
                .formCancelText("Cancel")
                .ratingBarColor(R.color.strokeColor)
                .playstoreUrl("market://details?id=razon.nctballbooksfree")
                .onRatingChanged(new RatingDialog.Builder.RatingDialogListener() {
                    @Override
                    public void onRatingSelected(float rating, boolean thresholdCleared) {

                    }
                })
                .onRatingBarFormSumbit(new RatingDialog.Builder.RatingDialogFormListener() {
                    @Override
                    public void onFormSubmitted(String feedback) {

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("feedback");
                        databaseReference.push().setValue(feedback);

                    }
                }).build();

        ratingDialog.show();

    }

    private void showCustomRateMeDialog() {
        final RatingDialog ratingDialog = new RatingDialog.Builder(this)
                .icon(getResources().getDrawable(R.drawable.logo))
                .session(7)
                .threshold(3)
                .ratingBarBackgroundColor(R.color.backgroundColor)
                .title("How was your experience with us?")
                .titleTextColor(R.color.black)
                .positiveButtonText("Not Now")
                .negativeButtonText("Never")
                .positiveButtonTextColor(R.color.colorPrimary)
                .negativeButtonTextColor(R.color.grey_500)
                .formTitle("Submit Feedback")
                .formHint("Tell us where we can improve")
                .formSubmitText("Submit")
                .formCancelText("Cancel")
                .ratingBarColor(R.color.strokeColor)
                .playstoreUrl("market://details?id=razon.nctballbooksfree")
                .onRatingChanged(new RatingDialog.Builder.RatingDialogListener() {
                    @Override
                    public void onRatingSelected(float rating, boolean thresholdCleared) {

                    }
                })
                .onRatingBarFormSumbit(new RatingDialog.Builder.RatingDialogFormListener() {
                    @Override
                    public void onFormSubmitted(String feedback) {

                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("feedback");
                        databaseReference.push().setValue(feedback);

                    }
                }).build();

        ratingDialog.show();
    }

    @Override
    public void onBackPressed() {
        if (CurrentPage.equals(ClassificationNode.BOOK_LIST)){
            HomeFragment fragment = new HomeFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.setCustomAnimations(R.anim.slide_in_from_left, R.anim.fade_out);
            transaction.replace(R.id.fragment_container, fragment);
            transaction.commit();
            showInterstitialAd();
        }else if (CurrentPage.equals(ClassificationNode.HOME)){
           finish();
        }else {
            getSupportFragmentManager().popBackStack();
            showInterstitialAd();
        }

    }
}
