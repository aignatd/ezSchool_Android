package id.co.devoxlabs.ezschool.data;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;

/**
 * Dibuat oleh : ignat
 * Tanggal : 06-Dec-16
 * HP/WA : 0857 7070 6 777
 */
public class UserEmail
{
  private FirebaseUser fbuUser;

  public UserEmail(FirebaseUser fbuUser)
  {
    this.fbuUser = fbuUser;
  }

  public FirebaseUser getFbuUser()
  {
    return fbuUser;
  }

  /*
  private static FirebaseUser fbuUser;
  private static GoogleSignInAccount gsiUser;

  public InternetData()
  {
  }

  public static FirebaseUser getFbuUser(){return fbuUser;}

  public static void setFbuUser(FirebaseUser fbuUser){InternetData.fbuUser = fbuUser;}

  public static GoogleSignInAccount getGsiUser(){return gsiUser;}

  public static void setGsiUser(GoogleSignInAccount gsiUser){InternetData.gsiUser = gsiUser;}
*/
}
