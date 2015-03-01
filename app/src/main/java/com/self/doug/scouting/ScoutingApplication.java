package com.self.doug.scouting;

/**
 * Created by Doug on 2/22/15.
 */

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.self.doug.scouting.AchievementDefinitions.AchievementDefinition;

public class ScoutingApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Allow saving via Parse
        Parse.enableLocalDatastore(this);
        ParseObject.registerSubclass(AchievementDefinition.class);
        Parse.initialize(this, "8ExTEoeHhV5ANaA9wBHJ8ZAgBpJYIQvbKQ8m4P87", "218c1A4anSh9cAgiyJjBIS8wPfKEUXkF2za754lf");

        // By default, all users are allowed to modify all objects.
        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        defaultACL.setPublicReadAccess(true);
        ParseACL.setDefaultACL(defaultACL, true);
    }
    public static ParseObject tempObject;
}