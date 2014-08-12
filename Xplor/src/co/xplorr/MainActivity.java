package co.xplorr;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sromku.simple.fb.SimpleFacebook;
import com.sromku.simple.fb.entities.Profile;
import com.sromku.simple.fb.entities.Profile.Properties;
import com.sromku.simple.fb.listeners.OnLogoutListener;
import com.sromku.simple.fb.listeners.OnProfileListener;
import com.sromku.simple.fb.utils.Attributes;
import com.sromku.simple.fb.utils.PictureAttributes;
import com.sromku.simple.fb.utils.PictureAttributes.PictureType;




public class MainActivity extends Activity {

	private static final String TAG = "MainActivity";

	MenuItem item;
	private TextView mResult;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		View view = findViewById(R.id.user_name);
		mResult = (TextView) view.findViewById(R.id.user_name);
		
		setProfile();

	}

	private void setProfile() {
		PictureAttributes attributes = Attributes.createPictureAttributes();
		attributes.setHeight(500); 
		attributes.setWidth(500);
		attributes.setType(PictureType.NORMAL);

		Profile.Properties properties = new Properties.Builder()
		.add(Properties.ID)
		.add(Properties.FIRST_NAME)
		.add(Properties.LAST_NAME)
		.add(Properties.PICTURE, attributes)
		.add(Properties.COVER)
		.add(Properties.WORK)
		.add(Properties.EDUCATION)
		.build();

		OnProfileListener onProfileListener = new OnProfileListener() {

			@Override
			public void onComplete(Profile profile) {
				
				Log.i(TAG, "My profile id = " + profile.getId());
				String str = toHtml(profile);
				mResult.setText(profile.getFirstName() + profile.getLastName());
			}
			
		};

		SimpleFacebook.getInstance().getProfile(properties, onProfileListener);
	}

	public static String toHtml(Object object) {
		StringBuilder stringBuilder = new StringBuilder(256);
		try {
			for (Field field : object.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				Object val = field.get(object);
				stringBuilder.append("<b>");
				stringBuilder.append(field.getName().substring(1, field.getName().length()));
				stringBuilder.append(": ");
				stringBuilder.append("</b>");
				stringBuilder.append(val);
				stringBuilder.append("<br>");
			}
		} catch (Exception e) {
			// Do nothing
		}
		return stringBuilder.toString();
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