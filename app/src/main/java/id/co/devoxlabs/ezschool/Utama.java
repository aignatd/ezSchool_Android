package id.co.devoxlabs.ezschool;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import id.co.devoxlabs.ezschool.data.*;
import id.co.devoxlabs.ezschool.kirim.ProsesData;
import id.co.devoxlabs.ezschool.muridbaru.MuridBaru;
import id.co.devoxlabs.ezschool.popup.GantiPassword;
import id.co.devoxlabs.ezschool.profiles.ProfileMain;
import id.co.devoxlabs.ezschool.utils.FixValue;
import id.co.devoxlabs.ezschool.utils.PesanPopup;
import id.co.devoxlabs.ezschool.utils.Preference;
import id.co.devoxlabs.ezschool.utils.fungsi;

import java.util.ArrayList;
import java.util.List;

public class Utama extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener
{
  private String TAG = "[Utama]";
  private ProgressDialog progressDialog;
  private Context context = this;
  private PesanPopup pesan = new PesanPopup();
  private Activity activity = this;
  private List<String> lstLogin = new ArrayList<>();;

  List<String> lstLogout;

  private ImageView ivBackIcon, ivNextIcon, ivProUtama;
  private TextView tvHeader, tvNoHpUtama, tvNamaUtama;

  private GoogleApiClient mGoogleApiClient;
  private String strKomponen = "";

  private ViewFlipper vfAbsen;
  private ViewFlipper vfPSB;

  int[] imgAbsen = {R.drawable.qrcodelands, R.drawable.qrcodeteks};
  int[] imgPSB = {R.drawable.psb, R.drawable.psbteks};

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lay_utama);

    lstLogout = new ArrayList<>();
		BindingView();
    BindingSlide();
	}

  private void BindingView()
  {
    findViewById(R.id.tvAbsen).setOnClickListener(this);
    findViewById(R.id.tvPSB).setOnClickListener(this);
    findViewById(R.id.rlProfile).setOnClickListener(this);
    findViewById(R.id.llProfile).setOnClickListener(this);

    ivBackIcon = (ImageView) findViewById(R.id.ivBackIcon);
    ivBackIcon.setImageResource(R.drawable.ic_home_orange);

    tvHeader = (TextView) findViewById(R.id.tvHeader);
    tvHeader.setVisibility(View.VISIBLE);
    tvHeader.setText(R.string.srtDashboard);

    ivNextIcon = (ImageView) findViewById(R.id.ivNextIcon);
    ivNextIcon.setVisibility(View.VISIBLE);
    ivNextIcon.setImageResource(R.drawable.ic_list_menu);
    ivNextIcon.setOnClickListener(this);

    ivProUtama = (ImageView) findViewById(R.id.ivProUtama);
    tvNamaUtama = (TextView) findViewById(R.id.tvNamaUtama);
    tvNoHpUtama = (TextView) findViewById(R.id.tvNoHpUtama);
  }

  private void BindingSlide()
  {
    vfAbsen = (ViewFlipper) findViewById(R.id.vfAbsen);
    vfAbsen.setOnClickListener(this);

    for (int i=0; i<imgAbsen.length; i++)
    {
      ImageView ivFlipper = new ImageView(Utama.this);
      ivFlipper.setImageResource(imgAbsen[i]);
      ivFlipper.setScaleType(ImageView.ScaleType.FIT_XY);
      vfAbsen.addView(ivFlipper);
    }

    vfAbsen.setFlipInterval(15000);
    vfAbsen.startFlipping();

    vfPSB = (ViewFlipper) findViewById(R.id.vfPSB);
    vfPSB.setOnClickListener(this);

    for (int j=0; j<imgPSB.length; j++)
    {
      Log.d("","");

      ImageView ivFlipper = new ImageView(Utama.this);
      ivFlipper.setImageResource(imgPSB[j]);
      ivFlipper.setScaleType(ImageView.ScaleType.FIT_XY);
      vfPSB.addView(ivFlipper);
    }

    vfPSB.setFlipInterval(10000);
    vfPSB.startFlipping();
  }

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
      case R.id.ivNextIcon:
        MenuBuilder menuBuilder = new MenuBuilder(context);

        if(fungsi.getIntFromSharedPref(context, Preference.PrefJenisLogin) >= 2)
          new SupportMenuInflater(context).inflate(R.menu.menu_popuphome, menuBuilder);
        else
          new SupportMenuInflater(context).inflate(R.menu.menu_popupdefault, menuBuilder);

        menuBuilder.setCallback(new MenuBuilder.Callback()
        {
          @Override
          public boolean onMenuItemSelected(MenuBuilder menu, MenuItem menuItem)
          {
            switch (menuItem.getItemId())
            {
              case R.id.mnuProfile:
                EditProfile();
              return true;
              case R.id.mnuRubahPass:
                GantiPassword cdMenuHome = new GantiPassword(Utama.this);
                cdMenuHome.show();
              return true;
              case R.id.mnuLogout:
                switch(fungsi.getIntFromSharedPref(context, Preference.PrefJenisLogin))
                {
                  case 1:
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder
                        .setTitle(R.string.txtTitlePesan)
                        .setMessage(getResources().getString(R.string.AppLogout))
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setCancelable(false)
                        .setPositiveButton(R.string.strBtnOK, new DialogInterface.OnClickListener()
                        {
                          public void onClick(DialogInterface dialog, int which)
                          {
                            UserData.initUserData();
                            UserData.getInstance().setHandphone(fungsi.getStringFromSharedPref(context, Preference.PrefUserHP));

                            DeviceData.initDeviceData();
                            DeviceData.getInstance().setDeviceID(fungsi.DeviceInfo(context, 0));

                            ProsesData.ProsesUser(Utama.this, context, 2);
                          }
                        })
                        .setNegativeButton(R.string.strBtnBatal, new DialogInterface.OnClickListener()
                        {
                          public void onClick(DialogInterface dialog, int id)
                          {
                            dialog.dismiss();
                          }
                        }
                     );

                    AlertDialog alert = builder.create();
                    alert.show();
                  break;
                  case 2:
                  case 4:
                  case 5:
                    pesan.TampilPesan5(context, getResources().getString(R.string.AppLogout), Login.class);
                  break;
                  case 3:
                    LogoutGmail();
                  break;
                }

              return true;
            }

            return false;
          }

          @Override
          public void onMenuModeChange(MenuBuilder menu) {
          }
        });

        MenuPopupHelper menuHelper = new MenuPopupHelper(context, menuBuilder, ivNextIcon);
        menuHelper.setForceShowIcon(true); // show icons!!!!!!!!
        menuHelper.show();
       break;
      case R.id.vfAbsen:
      case R.id.tvAbsen:
        if(StatusProfile() && (strKomponen.matches(getResources().getString(R.string.txtMurid))))
        {
          Intent AbsenMuridIntent = new Intent(Utama.this, AbsenQR.class);
          startActivity(AbsenMuridIntent);
          finish();
        }
      break;
      case R.id.rlProfile:
      case R.id.llProfile:
        EditProfile();
      break;
      case R.id.vfPSB:
      case R.id.tvPSB:
        if(StatusProfile())
        {
          if(!strKomponen.matches(getResources().getString(R.string.txtMurid)))
          {
            Intent MuridBaruIntent = new Intent(Utama.this, MuridBaru.class);
            startActivity(MuridBaruIntent);
            finish();
          }
        }
		}
	}

  @Override
  public void onBackPressed()
  {
    moveTaskToBack(true);
  }

  private void EditProfile()
  {
    fungsi.storeToSharedPref(context, 0, Preference.PrefPhotoSelfie);
    Intent ProfileIntent = new Intent(Utama.this, ProfileMain.class);
    ProfileIntent.putExtra("StatusProfile", tvNoHpUtama.getText().toString());
    ProfileIntent.putExtra("StatusNama", tvNamaUtama.getText().toString());
    startActivity(ProfileIntent);
    finish();
  }

  private void LogoutGmail()
  {
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.default_web_client_id))
        .requestEmail()
        .build();

    mGoogleApiClient = new GoogleApiClient.Builder(this)
        .enableAutoManage(this, this)
        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
        .build();

    SigninData.setGacData(mGoogleApiClient);
    pesan.TampilPesan5(context, getResources().getString(R.string.AppLogout), Login.class);
  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
  {
    pesan.TampilPesan1(this, getResources().getString(R.string.KoneksiGmailGagal));
  }

  @Override
  protected void onResume()
  {
    super.onResume();

    strKomponen = fungsi.getStringFromSharedPref(context, Preference.PrefKomponen).toString().trim();

    String name;
    String email;
    Uri photoUrl;
    String userID;
    String strCari = "";

    if(fungsi.getIntFromSharedPref(this, Preference.PrefJenisLogin) >= 2)
    {
      FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
      if (user != null)
      {
        name = user.getDisplayName();
        email = user.getEmail();
        photoUrl = user.getPhotoUrl();
        userID = user.getUid();

        tvNamaUtama.setText(name);
        tvNoHpUtama.setText(email);

        // Check if user's email is verified
        boolean emailVerified = user.isEmailVerified();

        Picasso.with(context).load(photoUrl.toString()).networkPolicy(NetworkPolicy.NO_CACHE)
          .memoryPolicy(MemoryPolicy.NO_CACHE)
          .placeholder(R.drawable.prefuser)
          .into(ivProUtama);

        strCari = email;
      }
    }
    else
    {
      tvNamaUtama.setText(fungsi.getStringFromSharedPref(context, Preference.PrefUsername));
      tvNoHpUtama.setText(fungsi.getStringFromSharedPref(context, Preference.PrefUserHP));
      strCari = tvNoHpUtama.getText().toString();
    }

    if(!strCari.matches(""))
    {
      fungsi.storeToSharedPref(context, strCari, Preference.PrefCariProfile);

      CariProfile.initCariProfile();
      CariProfile.getInstance().setCariProfile(strCari);
      CariProfile.getInstance().setKodeDevice(fungsi.DeviceInfo(context, 0));
      CariProfile.getInstance().setParamID(0);

      ProsesData.ProsesCekProfile(Utama.this, context, 0);
    }
  }

  private boolean StatusProfile()
  {
    if(fungsi.getStringFromSharedPref(context, Preference.PrefCekProfile).matches(FixValue.NoProfile))
    {
      pesan.TampilPesan1(context, getResources().getString(R.string.txtNoProfile));
      return false;
    }

    return true;
  }
}
