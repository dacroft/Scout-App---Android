package com.self.doug.scouting.AchievementDefinitions;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Doug on 2/26/15.
 */

@ParseClassName("AchievementDefinition")
public class AchievementDefinition extends ParseObject {


    // Field Keys
    public static String f_title = "title";

    // Constructors
    public AchievementDefinition()
    {
    }

    public AchievementDefinition(String title)
    {
        setTitle(title);
    }

    // Accessors
    public String getTitle()
    {

        return getString(f_title);
    }
    public void setTitle(String title)
    {

        put(f_title, title);
    }
}
