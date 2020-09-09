package com.example.alias;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import android.content.Context;
import java.util.List;
import java.util.Random;


public class PageFragment extends Fragment{
    static final String ARGUMENT_PAGE_NUMBER = "arg_page_number";
    static final String ENG_WORD = "eng_word";
    static final String TRANS = "trans";
    final static String RUS_WORD = "rus_word";

    int pageNumber;
    int backColor;
    TextView engText, transText, rusText;
    int iter = 0;
    int k = 0;
    static List<Word> dictionary;
    public ViewFlipper flipper;

    Animation animFlipInForward;
    Animation animFlipOutForward;
    Animation animFlipInBackward;
    Animation animFlipOutBackward;

    public static PageFragment newInstance(int page) {
        PageFragment pageFragment = new PageFragment();
        Bundle arguments = new Bundle();
        arguments.putString(ENG_WORD, dictionary.get(page).getEng());
        arguments.putString(TRANS, dictionary.get(page).getTrans());
        arguments.putString(RUS_WORD, dictionary.get(page).getRus());
        pageFragment.setArguments(arguments);
        return pageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dictionary = MainActivity.dictionary;
        animFlipInForward = AnimationUtils.loadAnimation(getContext(), R.anim.swipe_in);
        animFlipOutForward = AnimationUtils.loadAnimation(getContext(), R.anim.swipe_out);
        animFlipInBackward = AnimationUtils.loadAnimation(getContext(), R.anim.swipe_in_rev);
        animFlipOutBackward = AnimationUtils.loadAnimation(getContext(), R.anim.swipe_out_rev);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, null);
        Bundle arguments = getArguments();
        if (arguments != null) {
            String engWord = arguments.getString(ENG_WORD);
            String trans = arguments.getString(TRANS);
            String rusWord = arguments.getString(RUS_WORD);

            engText = (TextView) view.findViewById(R.id.eng);
            transText = (TextView) view.findViewById(R.id.trans);
            rusText = (TextView) view.findViewById(R.id.rus);
            engText.setText(engWord);
            transText.setText(trans);
            rusText.setText(rusWord);
        }
        return view;
    }

    public static int getCount(){
        return dictionary.size();
    }


        private void SwipeLeft() {
        flipper.setInAnimation(animFlipInBackward);
        flipper.setOutAnimation(animFlipOutBackward);
        if(iter == 0){
            iter = dictionary.size() - 1;
        }
        else {
            iter--;
        }
        if(k == 1 || k == 3){
            flipper.showPrevious();
            k--;
        }
        else {
            flipper.showNext();
            k++;
        }
        engText.setText(dictionary.get(iter).getEng());
        transText.setText(dictionary.get(iter).getTrans());
        rusText.setText(dictionary.get(iter).getRus());
    }

    private void SwipeRight() {
        flipper.setInAnimation(animFlipInForward);
        flipper.setOutAnimation(animFlipOutForward);
        if(iter == dictionary.size() - 1){
            iter = 0;
        }
        else {
            iter++;
        }
        if(k == 1 || k == 3){
            flipper.showPrevious();
            k--;
        }
        else {
            flipper.showNext();
            k++;
        }
        engText.setText("");
        transText.setText("");
        rusText.setText("");
        engText.setText(dictionary.get(iter).getEng());
        transText.setText(dictionary.get(iter).getTrans());
        rusText.setText(dictionary.get(iter).getRus());
    }
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        return gestureDetector.onTouchEvent(event);
//    }
//
//    GestureDetector.SimpleOnGestureListener simpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
//        @Override
//        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
//                               float velocityY) {
//
//            float sensitvity = 150;
//            if ((e1.getX() - e2.getX()) > sensitvity) {
//                SwipeLeft();
//                return true;
//            } else if ((e2.getX() - e1.getX()) > sensitvity) {
//                SwipeRight();
//                return true;
//            }
//            return false;
//        }
//        @Override
//    public boolean onSingleTapUp(MotionEvent event){
//            onImageClicked();
//        return true;
//               }
//    };
//    GestureDetector gestureDetector = new GestureDetector(getContext(),
//            simpleOnGestureListener);
}
