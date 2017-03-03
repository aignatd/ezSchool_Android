package id.co.devoxlabs.ezschool.data;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Dibuat oleh : ignat
 * Tanggal : 06-Dec-16
 * HP/WA : 0857 7070 6 777
 */
public class UserGoogle
{
  private static GoogleSignInAccount gsiUser;

  public static GoogleSignInAccount getGsiUser(){return gsiUser;}

  public static void setGsiUser(GoogleSignInAccount gsiUser){UserGoogle.gsiUser = gsiUser;}

  /*
  private static FirebaseUser fbuUser;

  public InternetData()
  {
  }

  public static FirebaseUser getFbuUser(){return fbuUser;}

  public static void setFbuUser(FirebaseUser fbuUser){InternetData.fbuUser = fbuUser;}
*/
}
