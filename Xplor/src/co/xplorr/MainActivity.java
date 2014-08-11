package co.xplorr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.listeners.OnLogoutListener;



public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";

	MenuItem item;

	//private SimpleFacebook mSimpleFacebook;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}



	@Override
	public void onResume() {
		super.onResume();
		LoginActivity.mSimpleFacebook = SimpleFacebook.getInstance(this);
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
			final OnLogoutListener onLogoutListener = new OnLogoutListener() {

				@Override
				public void onThinking() {
					// TODO Auto-generated method stub

				}

				@Override
				public void onException(Throwable throwable) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onFail(String reason) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onLogout() {
					// TODO Auto-generated method stub

				}

			};

			LoginActivity.mSimpleFacebook.logout(onLogoutListener);
			Intent intent = new Intent(MainActivity.this, LoginActivity.class);
			startActivity(intent);
			this.finish();
			return true;
			
		}
		return super.onOptionsItemSelected(item);	
	}
}