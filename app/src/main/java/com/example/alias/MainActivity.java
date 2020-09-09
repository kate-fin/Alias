package com.example.alias;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.MotionEventCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import com.google.gson.Gson;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.gson.Gson;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity{
    ViewPager pager;
    PagerAdapter pagerAdapter;
    static ArrayList<Word> dictionary;
    int skipped_word = 0;
    TextView SkipppedWord;
    int guessed_word = 0;
    TextView GuessedWord;
    private int lastPosition = 0;

    static final String TAG = "myLogs";
    int PAGE_COUNT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dictionary = new ArrayList<>();
        try {
            dictionary = JsonParser.importFromJSON(this);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        PAGE_COUNT = dictionary.size();
        SkipppedWord = (TextView) findViewById(R.id.skipped_word);
        GuessedWord = (TextView) findViewById(R.id.guessed_word);
        SkipppedWord.setText(String.valueOf(skipped_word));
        GuessedWord.setText(String.valueOf(guessed_word));
        pager = (ViewPager) findViewById(R.id.pager);
        pagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());//Метод возвращает объект FragmentManager, который управляет фрагментами
        pager.setAdapter(pagerAdapter);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected, position = " + position);
                if (lastPosition > position) {
                    Log.d(TAG,"Left");
                    skipped_word++;
                    SkipppedWord.setText(String.valueOf(skipped_word));
                }else if (lastPosition < position) {
                    Log.d(TAG,"Right");
                    guessed_word++;
                    GuessedWord.setText(String.valueOf(guessed_word));
                }
                lastPosition = position;
            }

            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                if (state == ViewPager.SCROLL_STATE_IDLE) {
//                    if (!pager.canScrollHorizontally(1))//1 - если нельзя крутить вправо
//                        pager.setCurrentItem(1, false);
//                    else if (!pager.canScrollHorizontally(-1))//-1 - если нельзя крутить влево
//                        pager.setCurrentItem(pager.getCurrentItem() - 1);
////                    , false);
//                }
            }
        });

    }

    boolean flagBack = true;
    public void onImageClicked(View view){
        Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.half_flip_1);
        Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.half_flip_2);
        ViewFlipper flipper = (ViewFlipper) view.findViewById(R.id.view_flipper);
        flipper.setOutAnimation(animation1);
        flipper.setInAnimation(animation2);
        if(flagBack)
        {
            flipper.showNext();
        }
        else
        {
            flipper.showPrevious();
        }
        flagBack = !flagBack;
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            PageFragment pageFragment = new PageFragment();
            Bundle arguments = new Bundle();
//            if(position == 0)
//                position = 2;
//            else if(position == PageFragment.getCount() - 1)
//                position =
            if (dictionary != null) {
                arguments.putString(PageFragment.ENG_WORD, dictionary.get(position).getEng());
                arguments.putString(PageFragment.TRANS, dictionary.get(position).getTrans());
                arguments.putString(PageFragment.RUS_WORD, dictionary.get(position).getRus());
                pageFragment.setArguments(arguments);
            }
            return pageFragment;
        }

        @Override
        public int getCount() {
//            return PAGE_COUNT;
            if (PageFragment.dictionary != null)
                PAGE_COUNT = PageFragment.dictionary.size();
            return PAGE_COUNT;
        }

    }


}
