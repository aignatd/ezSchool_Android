package id.co.devoxlabs.ezschool.data;

/**
 * Dibuat oleh : ignat
 * Tanggal : 22-Feb-17
 * HP/WA : 0857 7070 6 777
 */
public class UserData
{
  private String Handphone;
  private String Password;
  private String Email;
  private Integer IdxKomponen;
  private String Komponen;
  private Integer LoginID;
  private String Nama;
  private String Passbaru;

  public String getHandphone()
  {
    return Handphone;
  }

  public void setHandphone(String handphone)
  {
    Handphone = handphone;
  }

  public String getPassword()
  {
    return Password;
  }

  public void setPassword(String password)
  {
    Password = password;
  }

  public String getEmail()
  {
    return Email;
  }

  public void setEmail(String email)
  {
    Email = email;
  }

  public Integer getIdxKomponen()
  {
    return IdxKomponen;
  }

  public void setIdxKomponen(Integer idxKomponen)
  {
    IdxKomponen = idxKomponen;
  }

  public String getKomponen()
  {
    return Komponen;
  }

  public void setKomponen(String komponen)
  {
    Komponen = komponen;
  }

  public Integer getLoginID()
  {
    return LoginID;
  }

  public void setLoginID(Integer loginID)
  {
    LoginID = loginID;
  }

  public String getNama()
  {
    return Nama;
  }

  public void setNama(String nama)
  {
    Nama = nama;
  }

  public String getPassbaru()
  {
    return Passbaru;
  }

  public void setPassbaru(String passbaru)
  {
    Passbaru = passbaru;
  }

  private static UserData holder;
  public static UserData getInstance()
  {
    return holder;
  }

  private UserData()
  {
  }

  public static void initUserData()
  {
    holder = new UserData();
  }
}
