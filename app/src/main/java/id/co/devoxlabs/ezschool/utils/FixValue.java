package id.co.devoxlabs.ezschool.utils;

/**
 * Created by ignat on 16-Jun-16.
 */
public class FixValue
{
	public static final int SPLASH_DISPLAY_LENGHT = 2500;

	public static final String strNamaPref = "id.co.devoxlabs.ezhelper.pref";
	public static final String TitleFCM = "School Application";
	public static final String FolderDoc = "/ezSchool";

	public static final String Host = "http://192.168.16.2:31013/api/v1/";
//	public static final String Host = "http://192.168.30.40:31013/api/v1/";
//	public static final String Host = "http://192.168.137.1:31013/api/v1/";
//	public static final String Host = "http://192.168.191.2:31013/api/v1/";
//	public static final String Host = "http://172.19.1.71:31013/api/v1/";

	public static final String ParamLogin = "users/login";
	public static final String ParamBuatAkun = "users/registrasi";
	public static final String ParamLogout = "users/logout";
	public static final String ParamProfile = "users/profile";
	public static final String ProfileUser = "users/rubah";
	public static final String ProfilePass = "users/password";
	public static final String ParaProWali = "wali/profile";
	public static final String ParaProGuru = "guru/profile";
	public static final String ParaProMurid = "murid/profile";
	public static final String UploadPhoto = "users/upload";
	public static final String DownloadPhoto = "users/download";

	public static final int TimeoutKoneksi = 45000;
  public static final int TYPE_NONE = 0;
  public static final int TYPE_WIFI = 1;
  public static final int TYPE_MOBILE = 2;

  public static final String TWITTER_KEY = "ha9W0fbnWN4fsK1yjlWVZHTxQ";
  public static final String TWITTER_SECRET = "GGbsgUJWpTLWw7HWPQMnoVetzwBDZNFhz7PwqXeNZDSwYMuVG2";

	// Awal, Variabel untuk timeout aplikasi
	public static final int LogoutState = 0;
	public static final int LoginState = 1;
	public static final String LastState = "current";
	public static final long Timeout = 2 * 60000000000L; // satuan nano second
	public static final String SessionStatus = "status";
	// Akhir, Variabel untuk timeout aplikasi

  public static final String ErrorUser = "-1";
	public static final String NoProfile = "Kosong";
	public static final Integer PilihGambar = 100;
}
