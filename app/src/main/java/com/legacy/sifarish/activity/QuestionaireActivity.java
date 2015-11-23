package com.legacy.sifarish.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.legacy.sifarish.R;
import com.legacy.sifarish.fragments.Question1;
import com.legacy.sifarish.fragments.Question2;
import com.legacy.sifarish.fragments.Question3;
import com.legacy.sifarish.fragments.Question4;
import com.legacy.sifarish.fragments.Question5;
import com.legacy.sifarish.interfaces.IQuestionCallback;
import com.legacy.sifarish.util.Constants;

import java.util.Map;

public class QuestionaireActivity extends AppCompatActivity {

    /**
     * The number of questions
     */
    private static final int NUM_QUES = 5;
    private ViewPager mPager;
    private QuestionaireActivityAdapter mPagerAdapter;
    private IQuestionCallback question;

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
                int currFragNumb = mPager.getCurrentItem();
                question = (IQuestionCallback)mPagerAdapter.getRegisteredFragment(currFragNumb);
                Snackbar.make(view, "Awesome!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                SharedPreferences.Editor editor = getSharedPreferences(Constants.PREF_CONST, MODE_PRIVATE).edit();
                    editor.putString("answer_" + currFragNumb, question.getAnswer());
                    if(currFragNumb == 4) {
                        editor.putString("all_done", "true");
                        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREF_CONST, MODE_PRIVATE);
                        Map<String, ?> allEntries = sharedPreferences.getAll();
                        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
                        }
                    }
                editor.commit();

                if(mPager.getCurrentItem() < NUM_QUES)
                    mPager.setCurrentItem(currFragNumb + 1);
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

        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return  new Question1();
                case 1:
                    return  new Question2();
                case 2:
                    return  new Question3();
                case 3:
                    return  new Question4();
                case 4:
                    return  new Question5();
                default:
                    return  new Question1();
            }

        }

        @Override
        public int getCount() {
             return NUM_QUES;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }

        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }
    }
}
