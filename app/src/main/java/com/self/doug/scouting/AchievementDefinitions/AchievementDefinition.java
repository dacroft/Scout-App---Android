package com.self.doug.scouting.AchievementDefinitions;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by Doug on 2/26/15.
 */


@ParseClassName("AchievementDefinition")
public class AchievementDefinition extends ParseObject {

    public String getTitle()
    {

        return getString("title");
    }
    public void setTitle(String title)
    {

        put("title",title);
    }

    public AchievementDefinition(String title)
    {

        setTitle(title);
    }
    public AchievementDefinition()
    {

    }
}
