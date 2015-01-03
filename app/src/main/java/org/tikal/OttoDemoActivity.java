package org.tikal;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class OttoDemoActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

   @Override
   protected void onResume(){
       super.onResume();
       Intent wakeServiceIntent = new Intent(this,EventGeneratorService.class);
       startService(wakeServiceIntent);
   }
}
