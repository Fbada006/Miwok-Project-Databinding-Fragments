package com.disruption.miwokproject;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.disruption.miwokproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    /*//Layout for the image icons to be used with the tabs
    private int[] imageResId = {
            R.drawable.one, R.drawable.two, R.drawable.three, R.drawable.three};*/

    /*Data Biding instance*/
    ActivityMainBinding mActivityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the content of the activity to use the activity_main.xml layout file
        mActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        //Find the viewPager in the activity_main.xml that will allow swiping
        ViewPager viewPager = findViewById(R.id.viewpager);

        // Create an adapter that knows which fragment should be shown on each page
        CategoryAdapter categoryAdapter = new CategoryAdapter(getSupportFragmentManager(),this);

        // Set the adapter onto the view pager
        viewPager.setAdapter(categoryAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

        /*//Testing how to add icons to the tabs as well as text
        for (int i = 0; i < imageResId.length; i++) {
            tabLayout.getTabAt(i).setIcon(imageResId[i]);
        }*/
    }
}
