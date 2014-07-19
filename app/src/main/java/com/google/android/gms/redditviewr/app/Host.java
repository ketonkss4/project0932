package com.google.android.gms.redditviewr.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.widget.Toast;

import ImageLoader.RedditIconTask;
import Listeners.OnSelectionListener;
import Tasks.RedditApiTask;

public class Host extends FragmentActivity implements OnSelectionListener {
    private RedditApiTask apiTask;
    private RedditIconTask getImg;
    private MainFragment mainFragment;
    private DetailsView  detailsFragment;
    private FragmentManager mFragmentManager;
    private final static String TAG_FRAGMENT = "TAG_FRAGMENT";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);

        this.mainFragment = new MainFragment();

        if (!isInTwoPaneMode()) {
            mainFragment = new MainFragment();
            // add the MainFragment to the fragment_container
            mFragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment_container, mainFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else {
            //Save a reference to the
            detailsFragment = (DetailsView)getSupportFragmentManager().findFragmentById(R.id.detials_frag);
        }
    }

    private boolean isInTwoPaneMode() {

        return findViewById(R.id.fragment_container) == null;

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    protected void onPause() {
        super.onPause();
        apiTask.cancel(true);
        getImg.stopImage(true);

    }
    //attempts to restart Image download when activity is resumed
    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void OnSelectionListener(String img, String comments) {
        Fragment detailsView = new DetailsView();
        Bundle args = new Bundle();
        args.putString("img",img);
        args.putString("comments",comments);
        detailsView.setArguments(args);

        mFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, detailsView, TAG_FRAGMENT);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();



    }

    @Override
    public void onBackPressed() {
        final DetailsView detailsView = (DetailsView)getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);
        if(detailsView.allowBackPressed()) {


            super.onBackPressed();
        }


    }
}
