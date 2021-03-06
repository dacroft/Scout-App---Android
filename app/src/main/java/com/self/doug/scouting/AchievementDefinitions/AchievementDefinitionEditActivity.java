package com.self.doug.scouting.AchievementDefinitions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseException;
import com.parse.SaveCallback;
import com.self.doug.scouting.R;
import com.self.doug.scouting.ScoutingApplication;

public class AchievementDefinitionEditActivity extends ActionBarActivity {

    private EditText achievementDefinitionTitleEditText;
    private AchievementDefinition achievementDefinition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_definition_creation);

        // Obtain the Achievement Definition that was sent in
        this.achievementDefinition = (AchievementDefinition)ScoutingApplication.tempObject;

        // Add the title from the definition to the edit text
        this.achievementDefinitionTitleEditText = (EditText)this.findViewById(R.id.achievementDefinitionTitleEditText);
        this.achievementDefinitionTitleEditText.setText(this.achievementDefinition.getTitle());

        // Handle click of the accept button
        Button acceptNewAchievementDefinitionButton = (Button)this.findViewById(R.id.acceptNewAchievementDefinition);
        acceptNewAchievementDefinitionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // progressBarVisible
                // Send the Result
                Intent output = new Intent();
                AchievementDefinitionEditActivity.this.achievementDefinition.setTitle(AchievementDefinitionEditActivity.this.achievementDefinitionTitleEditText.getText().toString());
                AchievementDefinitionEditActivity.this.achievementDefinition.pinInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        AchievementDefinitionEditActivity.this.achievementDefinition.saveEventually();
                        //progressBarInvisible
                    }
                });
                ScoutingApplication.tempObject = AchievementDefinitionEditActivity.this.achievementDefinition;
                AchievementDefinitionEditActivity.this.setResult(RESULT_OK);


                // End this activity
                AchievementDefinitionEditActivity.this.finish();
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_achievement_definition_creation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
