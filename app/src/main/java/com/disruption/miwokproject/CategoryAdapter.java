package com.disruption.miwokproject;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * {@link CategoryAdapter} is a {@link FragmentPagerAdapter} that can provide the layout for each list
 * item based on a data source which is a list of {@link Word} objects
 */
public class CategoryAdapter extends FragmentPagerAdapter {
    /*Create a variable for number of pages*/
    private final int PAGE_COUNT = 4;

    /**
     * Context of the app
     */
    private Context mContext;

    /**
     * Create a new {@link CategoryAdapter} object.
     *
     * @param fragmentManager is the fragment manager that will keep each fragment's state in the adapter
     *                        across swipes.
     */
    CategoryAdapter(FragmentManager fragmentManager, Context context) {
        super(fragmentManager);
        mContext = context;
    }

    /**
     * Return the {@link Fragment} that should be displayed for the given page number.
     */
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new NumbersFragment();
            case 1:
                return new FamilyFragment();
            case 2:
                return new ColorsFragment();
            case 3:
                return new PhrasesFragment();
            default:
                return null;
        }
    }

    /**
     * Return the total number of pages.
     */
    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return mContext.getString(R.string.category_numbers);
            case 1:
                return mContext.getString(R.string.category_family);
            case 2:
                return mContext.getString(R.string.category_colors);
            case 3:
                return mContext.getString(R.string.category_phrases);
            default:
                return null;
        }
    }
}
