package id.co.devoxlabs.ezschool;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.facebook.*;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.*;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import id.co.devoxlabs.ezschool.data.*;
import id.co.devoxlabs.ezschool.kirim.ProsesData;
import id.co.devoxlabs.ezschool.login.EmailLogin;
import id.co.devoxlabs.ezschool.utils.FixValue;
import id.co.devoxlabs.ezschool.utils.PesanPopup;
import id.co.devoxlabs.ezschool.utils.Preference;
import id.co.devoxlabs.ezschool.utils.fungsi;
import io.fabric.sdk.android.Fabric;
import com.google.android.gms.tasks.Task;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.*;

public class Login extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener
{
  private String TAG = "[Login]";
  private ProgressDialog progressDialog;
  private PesanPopup pesan = new PesanPopup();
  private Context context = this;
  List<EditText> lstInput = new ArrayList<>();
  List<String> lstMsg = new ArrayList<>();

  private FirebaseAuth mAuth;
  private FirebaseAuth.AuthStateListener mAuthListener;
  private CallbackManager mCallbackManager;

  private static final int RC_SIGN_IN = 100;
  private GoogleApiClient mGoogleApiClient;

  private Activity activity = this;
  private boolean boolInitGmail = false;
  private boolean boolInitFacebook = false;
  private boolean boolInitTwitter = false;
  TwitterAuthClient mTwitterAuthClient;

  private List<String> lstLogin = new ArrayList<>();;
  private EditText etHPEmail;
  private EditText etPass;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);

    FacebookSdk.sdkInitialize(getApplicationContext());
    TwitterAuthConfig authConfig = new TwitterAuthConfig(FixValue.TWITTER_KEY, FixValue.TWITTER_SECRET);
    Fabric.with(this, new Twitter(authConfig));

    setContentView(R.layout.lay_login);

    if(fungsi.CheckPermission(this) == false)
      fungsi.RequestPermission(this, activity);

    BindingView();
    CekLoginCreate();
  }

  private void BindingView()
  {
    etHPEmail = (EditText) findViewById(R.id.etHPEmail);
    etHPEmail.setText(fungsi.getStringFromSharedPref(context, Preference.PrefUserHP));
    etPass = (EditText) findViewById(R.id.etPass);

    findViewById(R.id.tvBuatAkun).setOnClickListener(this);
    findViewById(R.id.btnMasuk).setOnClickListener(this);
    findViewById(R.id.ivEmail).setOnClickListener(this);
    findViewById(R.id.ivGoogle).setOnClickListener(this);
    findViewById(R.id.ivFacebook).setOnClickListener(this);
    findViewById(R.id.ivTwitter).setOnClickListener(this);

    mCallbackManager = CallbackManager.Factory.create();
    LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback< LoginResult >()
        {
          @Override
          public void onSuccess(LoginResult loginResult) {
            handleFacebookAccessToken(loginResult.getAccessToken());
          }

          @Override
          public void onCancel()
          {
            Toast.makeText(Login.this, "Login with Facebook cancel", Toast.LENGTH_LONG).show();
          }

          @Override
          public void onError(FacebookException exception) {
            Toast.makeText(Login.this, "Login with Facebook failure", Toast.LENGTH_LONG).show();
          }
        }
    );

    mTwitterAuthClient = new TwitterAuthClient();
  }

  @Override
  public void onClick(View view)
  {
    switch (view.getId())
    {
      case R.id.ivGoogle:
        if((fungsi.getIntFromSharedPref(this, Preference.PrefJenisLogin) == 0) && (boolInitGmail == false))
        {
          InitGoogle();
          InitEmailFacebook();
          boolInitGmail = true;
        }

        fungsi.storeToSharedPref(this, 3, Preference.PrefJenisLogin);
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
      break;
      case R.id.tvBuatAkun:
        Intent BuatAkunIntent = new Intent(Login.this, BuatAkun.class);
        startActivity(BuatAkunIntent);
        finish();
      break;
      case R.id.ivEmail:
        Intent LoginEmailIntent = new Intent(Login.this, EmailLogin.class);
        startActivity(LoginEmailIntent);
        finish();
      break;
      case R.id.btnMasuk:
        lstInput.clear();
        lstMsg.clear();
        lstInput.add(etHPEmail);
        lstInput.add(etPass);
        lstMsg.add(getResources().getString(R.string.txtHPKosong));
        lstMsg.add(getResources().getString(R.string.PasswordKosong));

        if(fungsi.CekInput(lstInput, lstMsg, context))
        {
          UserData.initUserData();
          UserData.getInstance().setHandphone(etHPEmail.getText().toString().trim());
          UserData.getInstance().setPassword(new String(Hex.encodeHex(DigestUtils.md5(etPass.getText().toString().trim()))));

          DeviceData.initDeviceData();
          DeviceData.getInstance().setDeviceID(fungsi.DeviceInfo(context, 0));

          ProsesData.ProsesUser(Login.this, context, 0);
        }
      break;
      case R.id.ivFacebook:
        if((fungsi.getIntFromSharedPref(this, Preference.PrefJenisLogin) == 0) && (boolInitFacebook == false))
        {
          InitEmailFacebook();
          boolInitFacebook = true;
        }

        fungsi.storeToSharedPref(context, 4, Preference.PrefJenisLogin);
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("user_photos", "email", "user_birthday", "public_profile"));
      break;
      case R.id.ivTwitter:
        if((fungsi.getIntFromSharedPref(this, Preference.PrefJenisLogin) == 0) && (boolInitTwitter == false))
        {
          InitEmailFacebook();
          boolInitTwitter = true;
        }

        fungsi.storeToSharedPref(context, 5, Preference.PrefJenisLogin);
        mTwitterAuthClient.authorize(this, new com.twitter.sdk.android.core.Callback<TwitterSession>()
        {
          @Override
          public void success(Result<TwitterSession> twitterSessionResult) {
            handleTwitterSession(twitterSessionResult.data);
          }

          @Override
          public void failure(TwitterException e) {
            Toast.makeText(Login.this, "Login with Twitter failure", Toast.LENGTH_LONG).show();
          }
        });
      break;
    }
  }

  public void CekLoginCreate()
  {
    switch(fungsi.getIntFromSharedPref(this, Preference.PrefJenisLogin))
    {
      case 3:
        InitGoogle();
      case 2:
      case 4:
      case 5:
        InitEmailFacebook();
      break;
    }
  }

  private void InitEmailFacebook()
  {
    mAuth = FirebaseAuth.getInstance();
    mAuthListener = new FirebaseAuth.AuthStateListener()
    {
      @Override
      public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth)
      {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null)
        {
          InternetData.setFbuUser(user);
          fungsi.storeToSharedPref(context, Preference.PrefUsername, user.getDisplayName().toString());
          fungsi.storeToSharedPref(context, Preference.PrefUserHP, user.getEmail().toString());

          Intent LoginIntent = new Intent(Login.this, Utama.class);
          startActivity(LoginIntent);
          finish();
        }
      }
    };
  }

  private void InitGoogle()
  {
    GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(getString(R.string.default_web_client_id))
        .requestEmail()
        .build();

    mGoogleApiClient = new GoogleApiClient.Builder(this)
        .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
        .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
        .build();
  }

  @Override
  public void onStart()
  {
    super.onStart();
    CekLoginStart();
  }

  @Override
  public void onStop()
  {
    super.onStop();
    CekLoginStop();
  }

  @Override
  protected void onPause()
  {
    super.onPause();
    CekLoginStop();
  }

  private void CekLoginStart()
  {
    switch(fungsi.getIntFromSharedPref(this, Preference.PrefJenisLogin))
    {
      case 2:
      case 3:
      case 4:
      case 5:
        mAuth.addAuthStateListener(mAuthListener);
      break;
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data)
  {
    super.onActivityResult(requestCode, resultCode, data);

    switch(fungsi.getIntFromSharedPref(this, Preference.PrefJenisLogin))
    {
      case 3:
        if (requestCode == RC_SIGN_IN)
        {
          GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
          if (result.isSuccess())
          {
            // Google Sign In was successful, authenticate with Firebase
            GoogleSignInAccount account = result.getSignInAccount();
            firebaseAuthWithGoogle(account);
          }
          else
          {
            // Google Sign In failed, update UI appropriately
          }
        }
      break;
      case 4:
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
      break;
      case 5:
        mTwitterAuthClient.onActivityResult(requestCode, resultCode, data);
      break;
    }
  }

  private void CekLoginStop()
  {
    switch(fungsi.getIntFromSharedPref(this, Preference.PrefJenisLogin))
    {
      case 3:
      case 4:
      case 5:
        if(mAuthListener != null)
          mAuth.removeAuthStateListener(mAuthListener);
      break;
    }
  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult)
  {
    pesan.TampilPesan1(this, getResources().getString(R.string.KoneksiGmailGagal));
  }

  @Override
  public void onBackPressed()
  {
    moveTaskToBack(true);
  }

  private void handleFacebookAccessToken(AccessToken token)
  {
    progressDialog = ProgressDialog.show(this, getResources().getString(R.string.txtTunggu), getResources().getString(R.string.txtProsesFacebook));
    progressDialog.setCancelable(false);
    fungsi.storeToSharedPref(this, 0, Preference.PrefJenisLogin);

    AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
    mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
    {
      @Override
      public void onComplete(@NonNull Task<AuthResult> task)
      {
        Log.d("[Facebook]","");

        if (task.isSuccessful())
          CekAllState(0, "", 4);
        else
          CekAllState(1, task.getException().toString(), 0);

        progressDialog.dismiss();
      }
    });
  }

  private void handleTwitterSession(TwitterSession session)
  {
    progressDialog = ProgressDialog.show(this, getResources().getString(R.string.txtTunggu), getResources().getString(R.string.txtProsesTwitter));
    progressDialog.setCancelable(false);
    fungsi.storeToSharedPref(this, 0, Preference.PrefJenisLogin);

    AuthCredential credential = TwitterAuthProvider.getCredential(session.getAuthToken().token, session.getAuthToken().secret);
    mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
    {
      @Override
      public void onComplete(@NonNull Task<AuthResult> task)
      {
        Log.d("[Twitter]","");

        if (task.isSuccessful())
          CekAllState(0, "", 5);
        else
          CekAllState(1, task.getException().toString(), 0);

        progressDialog.dismiss();
      }
    });
  }

  private void firebaseAuthWithGoogle(GoogleSignInAccount acct)
  {
    progressDialog = ProgressDialog.show(this, getResources().getString(R.string.txtTunggu), getResources().getString(R.string.txtProsesGoogle));
    progressDialog.setCancelable(false);
    fungsi.storeToSharedPref(this, 0, Preference.PrefJenisLogin);

    AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
    mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
    {
      @Override
      public void onComplete(@NonNull Task<AuthResult> task)
      {
        Log.d("[Google]", "");

        if (task.isSuccessful())
          CekAllState(0, "", 3);
        else
          CekAllState(1, task.getException().toString(), 0);

        progressDialog.dismiss();
      }
    });
  }

  private void CekAllState(int intState, String strState, int intLoginID)
  {
    fungsi.storeToSharedPref(this, intLoginID, Preference.PrefJenisLogin);
    boolInitGmail = false;
    boolInitFacebook = false;
    boolInitTwitter = false;

    if(intState == 0)
      mAuth.addAuthStateListener(mAuthListener);
    else
      Toast.makeText(Login.this, strState, Toast.LENGTH_LONG).show();
  }
}
