package com.self.doug.scouting.Achievements;

/**
 * Created by Doug on 2/26/15.
 */
public class AchievementListItem {
    private String itemTitle;

    public String getItemTitle()
    {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle)
    {
        this.itemTitle = itemTitle;
    }

    public AchievementListItem(String title)
    {
        this.itemTitle = title;
    }
}
