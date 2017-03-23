package id.co.devoxlabs.ezschool.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import id.co.devoxlabs.ezschool.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ResultCallback;
import id.co.devoxlabs.ezschool.data.SigninData;

/**
 * Created by aignatd on 6/25/16.
 */
public class PesanPopup extends Activity
{
	public void TampilPesan0(final Context context, final Class<?> cls)
	{
		Intent Pesan0 = new Intent(context, cls);
		context.startActivity(Pesan0);
		finish();
	}

  public void TampilPesan1(final Context context, String strMsg)
  {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder
				.setTitle(R.string.txtTitlePesan)
				.setMessage(strMsg)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setCancelable(false)
				.setPositiveButton(R.string.strBtnOK, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						dialog.dismiss();
					}
				});
    AlertDialog alert = builder.create();
    alert.show();
  }

  public void TampilPesan2(final Context context, String strMsg, final Activity act, final Class<?> cls)
  {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder
				.setTitle(R.string.txtTitlePesan)
				.setMessage(strMsg)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setCancelable(false)
				.setPositiveButton(R.string.strBtnOK, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						Intent intent = new Intent(context, cls);
						context.startActivity(intent);
						act.finish();
					}
				})
				.setNegativeButton(R.string.strBtnBatal, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
						dialog.cancel();
					}
				});

    AlertDialog alert = builder.create();
    alert.show();
  }

  public void TampilPesan3(final Context context, String strMsg, final Activity cls)
  {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder
				.setTitle(R.string.txtTitlePesan)
				.setMessage(strMsg)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setCancelable(false)
				.setPositiveButton(R.string.strBtnOK, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						cls.finish();
					}
				});

    AlertDialog alert = builder.create();
    alert.show();
  }

  public void TampilPesan4(final Context context, String strMsg, final Activity act, final Class<?> cls)
  {
    AlertDialog.Builder builder = new AlertDialog.Builder(context);
    builder
				.setTitle(R.string.txtTitlePesan)
				.setMessage(strMsg)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setCancelable(false)
				.setPositiveButton(R.string.strBtnOK, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						Intent intent = new Intent(context, cls);
						context.startActivity(intent);
						act.finish();
					}
				});

    AlertDialog alert = builder.create();
    alert.show();
  }

	public void TampilPesan5(final Context context, String strMsg, final Class<?> cls)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder
				.setTitle(R.string.txtTitlePesan)
				.setMessage(strMsg)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setCancelable(false)
				.setPositiveButton(R.string.strBtnOK, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						CekLoginStart(context);
						Intent intent = new Intent(context, cls);
						context.startActivity(intent);
						finish();
					}
				})
				.setNegativeButton(R.string.strBtnBatal, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int id)
					{
						dialog.cancel();
					}
				});

		AlertDialog alert = builder.create();
		alert.show();
	}

	public void TampilPesan6(final Context context, String strMsg, final Class<?> cls)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder
				.setTitle(R.string.txtTitlePesan)
				.setMessage(strMsg)
				.setIcon(android.R.drawable.ic_dialog_alert)
				.setCancelable(false)
				.setPositiveButton(R.string.strBtnOK, new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						Intent intent = new Intent(context, cls);
						context.startActivity(intent);
						finish();
					}
				});

		AlertDialog alert = builder.create();
		alert.show();
	}

	private void CekLoginStart(final Context context)
	{
		switch(fungsi.getIntFromSharedPref(context, Preference.PrefJenisLogin))
		{
      case 1:
        fungsi.storeToSharedPref(context, 0, Preference.PrefJenisLogin);
      break;
			case 2:
			case 4:
			case 5:
				FirebaseAuth.getInstance().signOut();
        fungsi.storeToSharedPref(context, 0, Preference.PrefJenisLogin);
			break;
			case 3:
				FirebaseAuth.getInstance().signOut();
				Auth.GoogleSignInApi.signOut(SigninData.getGacData()).setResultCallback(new ResultCallback<Status>()
        {
          @Override
          public void onResult(Status status)
          {
            fungsi.storeToSharedPref(context, 0, Preference.PrefJenisLogin);
          }
        });
			break;
		}
	}
}
