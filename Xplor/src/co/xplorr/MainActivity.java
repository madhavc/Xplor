package co.xplorr;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;



public class MainActivity extends Activity {
	
	private static final String TAG = "MainActivity";
	
	MenuItem item;
	
	//private SimpleFacebook mSimpleFacebook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*if(!login.mSimpleFacebook.isLogin()){
        	Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        	startActivity(intent);
        }*/
        setContentView(R.layout.activity_main);
        
    }
    
    @Override
	public void onResume() {
        super.onResume();
        //mSimpleFacebook = SimpleFacebook.getInstance(this);
    }
    
    
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.fb_logout){
        }
        return super.onOptionsItemSelected(item);
    }
}