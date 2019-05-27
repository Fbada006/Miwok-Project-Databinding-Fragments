package com.disruption.miwokproject;

/**
 * {@link Word} represents a vocabulary word containing the default translation and the miwok translation
 */
public class Word {

    /**
     * Default translation of the word depending on whatever the user wishes
     */
    private String mDefaultTranslation;

    /**
     * Miwok translation of the word
     */
    private String mMiwokTranslation;

    /*Add a variable to reference the image resource ID for every translation*/
    private int mImageResourceId  = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    /*Add a variable to reference the resource ID for audio files */
    private int mAudioResourceId;

    /**
     * @return the string representation of a {@link Word} object
     */
    @Override
    public String toString() {
        return "Word{" +
                "mDefaultTranslation='" + mDefaultTranslation + '\'' +
                ", mMiwokTranslation='" + mMiwokTranslation + '\'' +
                ", mImageResourceId=" + mImageResourceId +
                ", mAudioResourceId=" + mAudioResourceId +
                '}';
    }

    /**
     * This constructor will only be used in the PhrasesActivity since it takes two string inputs
     *
     * @param defaultTranslation is the word the user is already comfortable with
     * @param miwokTranslation   is the word in the miwok language
     */
    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResourceId;
    }

    /**
     * Create a second constructor that takes in three inputs for the other three activities
     * ie, Numbers, Family and Color Activities
     *
     * @param defaultTranslation is the word the user is already comfortable with
     * @param miwokTranslation   is the word in the miwok language
     * @param imageResourceId    is a helper image to aid in the translation
     */
    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    /**
     * Get the default translation of the word
     */
    public String getDefaultTranslation() {
        return mDefaultTranslation;
    }

    /**
     * Get the miwok translation of the word
     */
    public String getMiwokTranslation() {
        return mMiwokTranslation;
    }

    /*Get the image resource ID*/
    public int getImageResourceId() {
        return mImageResourceId;
    }

    /*Get the audio resource ID*/
    public int getaudioResourceId(){
        return mAudioResourceId;
    }

    /**
     * Checks whether or not there is an image associated with this word
     * NB: This method and all its accompanying variables is redundant. In newer APIs, you
     * don't need it. This is for academic purposes only.
     */
    public boolean hasImage() {
    return mImageResourceId != NO_IMAGE_PROVIDED;
    }

    public void setmDefaultTranslation(String mDefaultTranslation) {
        this.mDefaultTranslation = mDefaultTranslation;
    }

    public void setmMiwokTranslation(String mMiwokTranslation) {
        this.mMiwokTranslation = mMiwokTranslation;
    }

    public void setmImageResourceId(int mImageResourceId) {
        this.mImageResourceId = mImageResourceId;
    }

    public void setmAudioResourceId(int mAudioResourceId) {
        this.mAudioResourceId = mAudioResourceId;
    }
}
