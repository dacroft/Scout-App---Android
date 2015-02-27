package com.self.doug.scouting;

/**
 * Created by Doug on 2/26/15.
 */
public class RequirementListItem {
    private String itemTitle;

    public String getItemTitle()
    {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle)
    {
        this.itemTitle = itemTitle;
    }

    public RequirementListItem(String title)
    {
        this.itemTitle = title;
    }
}
