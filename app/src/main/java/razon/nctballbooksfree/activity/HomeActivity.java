package razon.nctballbooksfree.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import razon.nctballbooksfree.HomeFragment;
import razon.nctballbooksfree.R;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

    }
}
