package com.llw.guidepagedemo.viewpager;

import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.llw.guidepagedemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * author : Ls
 */
public class ViewpagerActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    List<Fragment> fragmentList = new ArrayList<>();
    MainFragmentPagerAdapter adapter;
    ViewPager viewPager;
    RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_viewpager);
        //初始化控件
        initView();
        //绑定RadioButton
        initViewPager();
    }

    private void initView() {
        viewPager = findViewById(R.id.viewPager);
        rg = findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(this);
        rg.getChildAt(0).performClick();
    }

    private void initViewPager() {
        //添加碎片
        fragmentList.add(new OneFragment());
        fragmentList.add(new TwoFragment());
        fragmentList.add(new ThreeFragment());
        fragmentList.add(new FourFragment());
        fragmentList.add(new FiveFragment());

        adapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        rg.check(R.id.rb_wallet);
                        break;
                    case 1:
                        rg.check(R.id.rb_treasure);
                        break;
                    case 2:
                        rg.check(R.id.rb_home);
                        break;
                    case 3:
                        rg.check(R.id.rb_otc);
                        break;
                    case 4:
                        rg.check(R.id.rb_my);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.rb_wallet:
                viewPager.setCurrentItem(0);
                break;
            case R.id.rb_treasure:
                viewPager.setCurrentItem(1);
                break;
            case R.id.rb_home:
                viewPager.setCurrentItem(2);
                break;
            case R.id.rb_otc:
                viewPager.setCurrentItem(3);
                break;
            case R.id.rb_my:
                viewPager.setCurrentItem(4);
                break;

        }
    }
}
