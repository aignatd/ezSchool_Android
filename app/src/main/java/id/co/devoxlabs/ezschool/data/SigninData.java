package id.co.devoxlabs.ezschool.data;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Dibuat oleh : ignat
 * Tanggal : 06-Dec-16
 * HP/WA : 0857 7070 6 777
 */
public class SigninData
{
  private static GoogleApiClient gacData;

  public static GoogleApiClient getGacData(){return gacData;}
  public static void setGacData(GoogleApiClient gacData){SigninData.gacData = gacData;}
}
