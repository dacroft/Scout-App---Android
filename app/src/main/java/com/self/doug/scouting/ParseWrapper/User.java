package com.self.doug.scouting.ParseWrapper;

import android.os.Parcel;
import android.os.Parcelable;

import com.parse.ParseUser;


public class User
        implements Parcelable
{
    //Add more field types later
    public String userid;
    public String username;

    public User(String inuserid, String inusername)
    {
        userid = inuserid;
        username = inusername;
    }
    public static User fromParseUser(ParseUser parseUser)
    {
        if (parseUser == null)
        {
            return getAnnonymousUser();
        }
        return new User(parseUser.getObjectId(),parseUser.getUsername());
    }
    public static User getAnnonymousUser()
    {
        return new User("O","Annonymous");
    }
    public static final Parcelable.Creator<User> CREATOR
            = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    }; //end of creator

    //
    @Override
    public int describeContents() {
        return 0;
    }

    public User(Parcel in)
    {
        userid = in.readString();
        username = in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(userid);
        dest.writeString(username);
    }
}