package co.xplorr;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.sromku.simple.fb.Permission;
import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.SimpleFacebookConfiguration;
import com.sromku.simple.fb.listeners.OnLoginListener;
import com.sromku.simple.fb.listeners.OnLogoutListener;



public class LoginActivity extends Activity {

	private static final String TAG = "LoginActivity";

	public SimpleFacebook mSimpleFacebook;

	MenuItem item;

	Button mLoginButton;

	private boolean loggedIn;
	private boolean loggedOut;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(TAG, "In onCreate method");
		super.onCreate(savedInstanceState);
		mSimpleFacebook = SimpleFacebook.getInstance(this);

		Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.login_background);
		BitmapDrawable bitmapDrawable = new BitmapDrawable(bmp);
		bitmapDrawable.setTileModeXY(Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
		LinearLayout layout = new LinearLayout(this);
		layout.setBackgroundDrawable(bitmapDrawable);

		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);

		//Setting permissions and configuring facebook using SimpleFacebook SDK.
		Permission[] permissions = new Permission[] {
				Permission.USER_PHOTOS,
				Permission.EMAIL,
				Permission.PUBLISH_ACTION
		};
		SimpleFacebookConfiguration configuration = new SimpleFacebookConfiguration.Builder()
		.setAppId("1447558658853769")
		.setNamespace("Xplorr")
		.setPermissions(permissions)
		.build();

		SimpleFacebook.setConfiguration(configuration);
		if(mSimpleFacebook.isLogin()){
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(intent);
			this.finish();
		}


		setContentView(R.layout.activity_login);		
		mLoginButton =  (Button) findViewById(R.id.fb_login_button);
		//Need to create logout button that will only appear after the user has logged in.
		//mLogoutButton = (Button) findViewById(R.id.fb_logout_button);

		setLogin();
		setLogout();
		setUIState();
	}

	/**
	 * Below class is used to set the login listener 
	 * using the SimpleFacebook SDK. 
	 */
	private void setLogin() {
		// Login listener
		final OnLoginListener onLoginListener = new OnLoginListener() {

			@Override
			public void onFail(String reason) {
				//mTextStatus.setText(reason);
				Log.w(TAG, "Failed to login");
			}

			@Override
			public void onException(Throwable throwable) {
				//mTextStatus.setText("Exception: " + throwable.getMessage());
				Log.e(TAG, "Bad thing happened", throwable);
			}

			@Override
			public void onThinking() {
				// show progress bar or something to the user while login is
				// happening
				//mTextStatus.setText("Thinking...");
				setProgressBarIndeterminateVisibility(true);			
			}

			@Override
			public void onLogin() {
				setProgressBarIndeterminateVisibility(false);			
				// change the state of the button or do whatever you want
				//mTextStatus.setText("Logged in");
				loggedInUIState();
				Intent intent = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
				
			}

			@Override
			public void onNotAcceptingPermissions(Permission.Type type) {
				//				toast(String.format("You didn't accept %s permissions", type.name()));
			}
		};

		mLoginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mSimpleFacebook.login(onLoginListener);
			}
		});
	}


	/**
	 * Below class is used to set the logout listener 
	 * using the SimpleFacebook SDK.
	 */
	private void setLogout() {
		final OnLogoutListener onLogoutListener = new OnLogoutListener() {

			@Override
			public void onFail(String reason) {
				//mTextStatus.setText(reason);
				Log.w(TAG, "Failed to login");
			}

			@Override
			public void onException(Throwable throwable) {
				//mTextStatus.setText("Exception: " + throwable.getMessage());
				Log.e(TAG, "Bad thing happened", throwable);
			}

			@Override
			public void onThinking() {
				// show progress bar or something to the user while login is
				// happening
				//mTextStatus.setText("Thinking...");
				setProgressBarIndeterminateVisibility(true);
			}

			@Override
			public void onLogout() {
				setProgressBarIndeterminateVisibility(false);
				// change the state of the button or do whatever you want
				//mTextStatus.setText("Logged out");
				loggedOutUIState();
			}

		};

		/*if(item.getItemId() == R.id.fb_logout)
			mSimpleFacebook.logout(onLogoutListener);*/
	}

	private void setUIState() {
		if (mSimpleFacebook.isLogin()) {
			loggedInUIState();
		}
		else {
			loggedOutUIState();
		}
	}

	private void loggedInUIState() {
		loggedIn = true;
		loggedOut = false;
		//mTextStatus.setText("Logged in");
	}

	private void loggedOutUIState() {
		loggedIn = false;
		loggedOut = true;
		//mTextStatus.setText("Logged out");
	}

	@Override
	public void onResume() {
		super.onResume();
		mSimpleFacebook = SimpleFacebook.getInstance(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		mSimpleFacebook.onActivityResult(this, requestCode, resultCode, data); 
		super.onActivityResult(requestCode, resultCode, data);
	} 


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
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
		return super.onOptionsItemSelected(item);
	}

}
