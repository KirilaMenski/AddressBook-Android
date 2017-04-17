package by.softteco.addressbook.api.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by kirila on 17.4.17.
 */

public class Distance implements Serializable {

    @SerializedName("text")
    private String mText;
    @SerializedName("value")
    private int mValue;

    public Distance() {

    }

    public Distance(String text, int value) {
        mText = text;
        mValue = value;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public int getValue() {
        return mValue;
    }

    public void setValue(int value) {
        mValue = value;
    }
}
