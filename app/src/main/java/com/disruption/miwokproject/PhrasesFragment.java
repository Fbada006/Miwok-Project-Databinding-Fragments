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
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhrasesFragment extends Fragment {
    //Declare a MediaPlayer varibale
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


    public PhrasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list,container,false);

        //Create and set up the AudioManager so that it requests the audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        //Create an ArrayList of words
        final ArrayList<Word> words = new ArrayList<>();
        words.add(new Word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I’m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I’m coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        words.add(new Word("I’m coming.", "әәnәm", R.raw.phrase_im_coming));
        words.add(new Word("Let’s go.", "yoowutis", R.raw.phrase_lets_go));
        words.add(new Word("Come here.", "әnni'nem", R.raw.phrase_come_here));

        //Create a WordAdapter whose source is a lost of words.
        WordAdapter wordItemsAdapter = new WordAdapter(getActivity(), words, R.color.category_phrases);

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

        //Release the media player resources because we also don't need them
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
        }
        //Regardless of whether the permission to the request was granted or not, abandon and
        //unregister the AudioFocusChangeListener to avoid further callbacks
        mAudioManager.abandonAudioFocus(mAudioFocusChangeListener);
    }

}
