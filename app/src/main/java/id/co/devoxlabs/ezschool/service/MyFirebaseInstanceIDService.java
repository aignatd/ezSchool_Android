package id.co.devoxlabs.ezschool.service;

import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by aignatd on 30-Oct-16.
 */
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService
{
	private static final String TAG = "[InstIDService]";

	@Override
	public void onTokenRefresh()
	{

		//Getting registration token
		String refreshedToken = FirebaseInstanceId.getInstance().getToken();

		//Displaying token on logcat
		Log.d(TAG, "Refreshed token -> " + refreshedToken);
	}

	private void sendRegistrationToServer(String token)
	{
		//You can implement this method to store the token on your server
		//Not required for current project
	}
}
