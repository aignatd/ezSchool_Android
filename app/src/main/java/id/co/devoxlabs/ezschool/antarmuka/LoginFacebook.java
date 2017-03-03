package id.co.devoxlabs.ezschool.antarmuka;

import android.content.Context;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.List;

/**
 * Dibuat oleh : ignat
 * Tanggal : 19-Dec-16
 * HP/WA : 0857 7070 6 777
 */
public class LoginFacebook extends LoginButton
{
  public LoginFacebook(Context context)
  {
    super(context);
  }

  @Override
  public void setReadPermissions(List<String> permissions)
  {
    super.setReadPermissions(permissions);
  }

  @Override
  public void registerCallback(CallbackManager callbackManager, FacebookCallback<LoginResult> callback)
  {
    super.registerCallback(callbackManager, callback);
  }


}
