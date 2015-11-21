package com.legacy.sifarish.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.legacy.sifarish.R;
import com.legacy.sifarish.fragments.Question1;
import com.legacy.sifarish.fragments.Question2;
import com.legacy.sifarish.fragments.Question3;
import com.legacy.sifarish.fragments.Question4;
import com.legacy.sifarish.fragments.Question5;

public class QuestionaireActivity extends AppCompatActivity {

    /**
     * The number of questions
     */
    private static final int NUM_QUES = 5;

    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionaire);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new QuestionaireActivityAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Snackbar.make(view, "Awesome!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if(mPager.getCurrentItem() < NUM_QUES)
                    mPager.setCurrentItem(mPager.getCurrentItem() + 1);
            }
        });
    }


    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_questionaire, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class QuestionaireActivityAdapter extends FragmentStatePagerAdapter {
        public QuestionaireActivityAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new Question1();
                case 1:
                    return new Question2();
                case 2:
                    return new Question3();
                case 3:
                    return new Question4();
                case 4:
                    return new Question5();
                default:
                    return new Question1();
            }
        }

        @Override
        public int getCount() {
             return NUM_QUES;
        }
    }
}
