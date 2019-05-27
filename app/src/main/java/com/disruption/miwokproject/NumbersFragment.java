package com.disruption.miwokproject;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NumbersFragment extends Fragment {


    /*Create a MediaPlayer variable to play the audio files*/
    private MediaPlayer mMediaPlayer;

    /*Create an AudioManager variable to deal with changes in focus*/
    private AudioManager mAudioManager;

    /*Create a listener that gets triggered whenever the focus state has changed in the app*/
    private AudioManager.OnAudioFocusChangeListener mAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                /*Both of the above states mean that the app cannot play audio clearly. Even though
                 * it may duck, the audio file should be clear so the handling of either case will be similar*/

                //Pause the audio playback and reset the player to start playing the audio file from the beginning.
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);

            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                /*The above state means that the app has full focus audio playback should begin*/
                mMediaPlayer.start();

            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                /*Ths scenario means the app has lost focus and cannot play the audio
                 * The audio should stop in this case and resources released*/
                releaseMediaPlayer();
            }
        }
    };

    /*This is a listener that gets triggered when the media player has completed playing the
     * audio for pronunciation*/
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    public NumbersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        //Create and set up the AudioManager so that it requests the audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        //Create an ArrayList of words using the second constructor in the Word class
        final ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new Word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new Word("three", "tolookusu", R.drawable.number_three, R.raw.number_three));
        words.add(new Word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new Word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new Word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new Word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word("ten", "na'aacha", R.drawable.number_ten, R.raw.number_ten));

        //Create a WordAdapter whose source is a lost of words.
        WordAdapter wordItemsAdapter = new WordAdapter(getActivity(), words, R.color.category_numbers);

        //Get a reference to the listView to be used and then attach the relevant adapter
        ListView listView = rootView.findViewById(R.id.word_list);
        listView.setAdapter(wordItemsAdapter);

        //Set an OnItemClickListener for the listView/AdapterView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //Release the  media player resource if it exists because the file about to be played is different
                releaseMediaPlayer();

                // Get the current Word object that has been clicked on the list at the index position
                Word word = words.get(position);

                //Request for audio focus from the device and then use the AUDIOFOCUS_GAIN_TRANSIENT
                //since the audio files to be played are short
                int resultOfFocusRequest = mAudioManager.requestAudioFocus(mAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                //Using an if statement, set the proper action if request is granted
                if (resultOfFocusRequest == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //The above means the app has the audio focus.

                    //Get the ID for the correct audio file and the start the player.
                    //The first line can be inlined for brevity
                    int currentAudioResource = word.getaudioResourceId();
                    mMediaPlayer = MediaPlayer.create(getActivity(), currentAudioResource);
                    mMediaPlayer.start();

                    //Set up an OnCompletionListener on the media player so tat we can release its resources
                    //after finishing playing the audio file
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();

        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            //Regardless of whether the permission to the request was granted or not, abandon and
            //unregister the AudioFocusChangeListener to avoid further callbacks
            mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);
        }
    }
    /*TODO: Implement the requestAudioFocus (AudioFocusRequest focusRequest) instead of the deprecated
     * one we used here. Use an if statement to check for the API level*/

}
