package com.disruption.miwokproject;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Movie;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.disruption.miwokproject.databinding.ListItemBinding;

import java.util.ArrayList;

/**
 * {@link WordAdapter} is an {@link ArrayAdapter} that creates the layout of the list based
 * on the data source. In this case, the source is a list of {@link Word} objects
 */
public class WordAdapter extends ArrayAdapter<Word> {

    /*Declare a resource ID for the background color of a particular layout*/
    private int mBackgroundColorResourceId;

    /**
     * @param context                   current context to inflate the layout file
     * @param words                     the list of Word objects to display
     * @param backgroundColorResourceId is the background color for a particular activity or category
     */
    WordAdapter(@NonNull Activity context, ArrayList<Word> words, int backgroundColorResourceId) {
        super(context, 0, words);

        mBackgroundColorResourceId = backgroundColorResourceId;
    }

    /**
     * Provides a list for an AdapterView like a ListView or GridView
     *
     * @param position    is the position in the list of data that should be displayed in the list view
     * @param convertView is the recycled view to be populated
     * @param parent      the parent viewGroup to be used for inflation purposes
     * @return the view for the position in the adapter View
     */
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        ListItemBinding mainBinding = null;
        //Check existing view if its being used. Otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            mainBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(getContext()),
                    R.layout.list_item, parent, false);
            listItemView = mainBinding.getRoot();
        }


        // Get the Word object located in this position of the list
        Word currentWord = getItem(position);

        //Get the miwok word from the Word object and set this text on the view above
        if (currentWord != null) {
            if (mainBinding != null) {
                mainBinding.miwokTextView.setText(currentWord.getMiwokTranslation());
            }
        }

        //Get the default word from the Word object and set the word to the TextView above
        if (currentWord != null) {
            if (mainBinding != null) {
                mainBinding.defaultTextView.setText(currentWord.getDefaultTranslation());
            }
        }

        //Get the image resource ID from the current Word object and set that image to the ImageView above
        //if the word object has an image and explicitly state its visibility
        if (currentWord != null && currentWord.hasImage() && mainBinding != null) {
            mainBinding.translationHelperImage.setImageResource(currentWord.getImageResourceId());
            mainBinding.translationHelperImage.setVisibility(View.VISIBLE);
        } else {
            //Otherwise completely hide the imageView
            if (mainBinding != null) {
                mainBinding.translationHelperImage.setVisibility(View.GONE);
            }
        }
        //Find the color that is represented by the current resource ID
        int color = ContextCompat.getColor(getContext(), mBackgroundColorResourceId);

        //Set the background color of the container View that has the texts
        if (mainBinding != null) {
            mainBinding.textContainer.setBackgroundColor(color);
        }

        //Set the associated Tag
        listItemView.setTag(mainBinding);

        //Return the entire listView with the views
        return listItemView;
    }
}
