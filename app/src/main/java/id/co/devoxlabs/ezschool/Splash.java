package id.co.devoxlabs.ezschool;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import id.co.devoxlabs.ezschool.utils.FixValue;
import id.co.devoxlabs.ezschool.utils.Preference;
import id.co.devoxlabs.ezschool.utils.fungsi;

public class Splash extends AppCompatActivity
{
	private TextView tvVersion;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lay_splash);

		tvVersion = (TextView) findViewById(R.id.tvVersion);
		String myVersionName = "Unknown";
		final Context context = getApplicationContext(); // or activity.getApplicationContext()
		PackageManager packageManager = context.getPackageManager();
		String packageName = context.getPackageName();

		try
		{
			myVersionName = packageManager.getPackageInfo(packageName, 0).versionName;
		}
		catch (PackageManager.NameNotFoundException e)
		{
			e.printStackTrace();
		}

		tvVersion.setText("Versi " + myVersionName);

		new Handler().postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(FixValue.strNamaPref, null);
				fungsi.storeToSharedPref(context, 0, Preference.PrefJenisLogin);
				Intent mainIntent = new Intent(Splash.this, Login.class);
				startActivity(mainIntent);
				finish();
			}
		}, FixValue.SPLASH_DISPLAY_LENGHT);
	}
}
