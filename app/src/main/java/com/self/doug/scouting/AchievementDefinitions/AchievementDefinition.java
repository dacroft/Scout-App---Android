package com.self.doug.scouting.AchievementDefinitions;

import com.parse.ParseObject;
import com.self.doug.scouting.ParseWrapper.ParseObjectWrapper;

/**
 * Created by Doug on 2/26/15.
 */


public class AchievementDefinition extends ParseObjectWrapper {

    // Object Keys
    public static String t_tablename = "AchievementDefinition";
    public static String PARCELABLE_ACHIEVEMENT_DEFINITION_ID = "AchievementDefinitionId";

    // Field Keys
    public static String f_title = "title";

    // Constructors
    public AchievementDefinition()
    {
        super(t_tablename);
    }

    public AchievementDefinition(String title)
    {
        super(t_tablename);
        setTitle(title);
    }

    public AchievementDefinition(ParseObject parseObject)
    {
        super(parseObject);
    }

    public AchievementDefinition(ParseObjectWrapper parseObjectWrapper)
    {
        super(parseObjectWrapper);
    }

    // Accessors
    public String getTitle()
    {

        return po.getString(f_title);
    }
    public void setTitle(String title)
    {

        po.put(f_title,title);
    }
}
