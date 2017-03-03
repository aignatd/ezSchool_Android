package id.co.devoxlabs.ezschool.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import id.co.devoxlabs.ezschool.Login;
import id.co.devoxlabs.ezschool.R;
import id.co.devoxlabs.ezschool.Utama;
import id.co.devoxlabs.ezschool.data.InternetData;
import id.co.devoxlabs.ezschool.utils.Preference;
import id.co.devoxlabs.ezschool.utils.fungsi;

import java.util.ArrayList;
import java.util.List;

public class EmailLogin extends AppCompatActivity implements View.OnClickListener
{
  private String TAG = "Email Login";
  private ProgressDialog progressDialog;
  List<EditText> lstInput = new ArrayList<>();
  List<String> lstMsg = new ArrayList<>();

  private FirebaseAuth mAuth;
  private FirebaseAuth.AuthStateListener mAuthListener;

  private EditText etEmailAddr;
  private EditText etEmailPass;

  private TextView tvHeader;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lay_emaillogin);

		BindingView();
	}

	private void BindingView()
	{
    tvHeader = (TextView) findViewById(R.id.tvHeader);
    tvHeader.setVisibility(View.VISIBLE);
    tvHeader.setText(R.string.srtEmailLogin);

		findViewById(R.id.rlHeadEmail).setOnClickListener(this);
		findViewById(R.id.btnEmailMasuk).setOnClickListener(this);
    findViewById(R.id.btnEmailBuat).setOnClickListener(this);

    etEmailAddr = (EditText) findViewById(R.id.etEmailAddr);
    etEmailPass = (EditText) findViewById(R.id.etEmailPass);

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
          fungsi.storeToSharedPref(EmailLogin.this, 2, Preference.PrefJenisLogin);
          Intent LoginIntent = new Intent(EmailLogin.this, Utama.class);
          startActivity(LoginIntent);
          finish();
        }
      }
    };
	}

	@Override
	public void onClick(View view)
	{
		switch (view.getId())
		{
			case R.id.rlHeadEmail:
			  BackActivity();
			break;
			case R.id.btnEmailMasuk:
			  SignIn(etEmailAddr.getText().toString().trim(), etEmailPass.getText().toString().trim());
			break;
      case R.id.btnEmailBuat:
        BuatLoginEmail(etEmailAddr.getText().toString().trim(), etEmailPass.getText().toString().trim());
      break;
		}
	}

	@Override
	public void onBackPressed()
	{
		BackActivity();
	}

  private void BackActivity()
  {
    Intent LoginIntent = new Intent(EmailLogin.this, Login.class);
    startActivity(LoginIntent);
    finish();
  }

  private void SignIn(String email, String password)
  {
    CekInput();

    if(fungsi.CekInput(lstInput, lstMsg, EmailLogin.this))
    {
      progressDialog = ProgressDialog.show(this, getResources().getString(R.string.txtTunggu), getResources().getString(R.string.txtLoginEmail));
      progressDialog.setCancelable(false);

      mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
      {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task)
        {
          progressDialog.dismiss();

          if(!task.isSuccessful())
            Toast.makeText(EmailLogin.this, R.string.LoginEmailGagal, Toast.LENGTH_SHORT).show();
        }
      });
    }
  }

  private void BuatLoginEmail(String email, String password)
  {
    CekInput();

    if(fungsi.CekInput(lstInput, lstMsg, EmailLogin.this))
    {
      progressDialog = ProgressDialog.show(this, getResources().getString(R.string.txtTunggu), getResources().getString(R.string.txtLoginEmail));
      progressDialog.setCancelable(false);

      mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
      {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task)
        {
          progressDialog.dismiss();

          if (!task.isSuccessful())
            Toast.makeText(EmailLogin.this, R.string.BuatEmailGagal, Toast.LENGTH_SHORT).show();
        }
      });
    }
  }

  private void CekInput()
  {
    lstInput.clear();
    lstMsg.clear();
    lstInput.add(etEmailAddr);
    lstInput.add(etEmailPass);
    lstMsg.add(getResources().getString(R.string.EmailKosong));
    lstMsg.add(getResources().getString(R.string.PasswordKosong));
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

  @Override
  public void onStart()
  {
    super.onStart();
    mAuth.addAuthStateListener(mAuthListener);
  }

  private void CekLoginStop()
  {
    if(mAuthListener != null)
      mAuth.removeAuthStateListener(mAuthListener);
  }
}
