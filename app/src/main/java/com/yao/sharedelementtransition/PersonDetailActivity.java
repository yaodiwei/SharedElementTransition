package com.yao.sharedelementtransition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yao.sharedelementtransition.data.Person;
import com.yao.sharedelementtransition.data.SharedElement;
import com.yao.sharedelementtransition.util.ScreenUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.animation.ObjectAnimator.ofFloat;


public class PersonDetailActivity extends AppCompatActivity {

    private static final String TAG = "PersonDetailActivity";
    public static final String EXTRA_PERSON = "extra_person";
    public static final String EXTRA_ELEMENTS = "extra_elements";

    @BindView(R.id.iv_avatar)
    ImageView mIvAvatar;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_desc)
    TextView mTvDesc;
    @BindView(R.id.fl_background)
    FrameLayout mFlBackground;
    @BindView(R.id.nsv_content)
    NestedScrollView mNsvContent;

    private int duration = 10000;
    private ArrayList<SharedElement> mElements;
    private boolean animating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail_page);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Person person = getIntent().getParcelableExtra(EXTRA_PERSON);
        mElements = getIntent().getParcelableArrayListExtra(EXTRA_ELEMENTS);

        if (TextUtils.isEmpty(person.avatar)) {
            Glide.with(PersonDetailActivity.this).load(R.drawable.default_avatar_1).into(mIvAvatar);
        } else {
            Glide.with(PersonDetailActivity.this).load(person.avatar).into(mIvAvatar);
        }
        mTvName.setText(person.name);
        mTvDesc.setText(person.desc);

        animating = true;
        for (int i = 0; i< mElements.size(); i++){
            final int j = i;
            final SharedElement element = mElements.get(i);
            final View view = findViewById(element.resId);

            view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    int []location = new int[2];
                    view.getLocationInWindow(location);
                    int x = location[0];
                    int y = location[1];
                    PropertyValuesHolder pvh1 = PropertyValuesHolder.ofFloat("translationX", element.x-x, 0);
                    PropertyValuesHolder pvh2 = PropertyValuesHolder.ofFloat("translationY", element.y-y, 0);
                    PropertyValuesHolder pvh3 = PropertyValuesHolder.ofFloat("scaleX", element.width*1.0f/view.getWidth(), 1f);
                    PropertyValuesHolder pvh4 = PropertyValuesHolder.ofFloat("scaleY", element.height*1.0f/view.getHeight(), 1f);
                    view.setPivotX(0);
                    view.setPivotY(0);
                    ObjectAnimator.ofPropertyValuesHolder(view, pvh1, pvh2, pvh3, pvh4).setDuration(duration).start();
                }
            });
        }
        mFlBackground.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mFlBackground.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                TypedValue tv = new TypedValue();
                getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
                int actionBarHeight = getResources().getDimensionPixelSize(tv.resourceId);
                ofFloat(mFlBackground, "translationY", -mFlBackground.getHeight()+actionBarHeight, 0).setDuration(duration).start();
            }
        });
        mNsvContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mNsvContent.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int []location = new int[2];
                mNsvContent.getLocationInWindow(location);
                int y = location[1];
                ObjectAnimator animator = ObjectAnimator.ofFloat(mNsvContent, "translationY", ScreenUtil.getScreenHeight(PersonDetailActivity.this)-y, 0).setDuration(duration);
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animating = false;
                    }
                });
                animator.start();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (animating == true) {
            return;
        }
        animating = true;
        for (int i=0; i<mElements.size(); i++){
            final int j = i;
            final SharedElement element = mElements.get(i);
            final View view = findViewById(element.resId);

            int []location = new int[2];
            view.getLocationInWindow(location);
            int x = location[0];
            int y = location[1];
            PropertyValuesHolder pvh1 = PropertyValuesHolder.ofFloat("translationX", 0, element.x-x);
            PropertyValuesHolder pvh2 = PropertyValuesHolder.ofFloat("translationY", 0, element.y-y);
            PropertyValuesHolder pvh3 = PropertyValuesHolder.ofFloat("scaleX", 1f, element.width*1.0f/view.getWidth());
            PropertyValuesHolder pvh4 = PropertyValuesHolder.ofFloat("scaleY", 1f, element.height*1.0f/view.getHeight());
            ObjectAnimator.ofPropertyValuesHolder(view, pvh1, pvh2, pvh3, pvh4).setDuration(duration).start();
        }

        TypedValue tv = new TypedValue();
        getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
        int actionBarHeight = getResources().getDimensionPixelSize(tv.resourceId);
        ofFloat(mFlBackground, "translationY", 0, -mFlBackground.getHeight()+actionBarHeight).setDuration(duration).start();

        int []location = new int[2];
        mNsvContent.getLocationInWindow(location);
        int y = location[1];
        ObjectAnimator animator = ofFloat(mNsvContent, "translationY", 0, ScreenUtil.getScreenHeight(PersonDetailActivity.this)-y).setDuration(duration);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                PersonDetailActivity.super.onBackPressed();
                overridePendingTransition(0, 0);
            }
        });
        animator.start();
    }
}