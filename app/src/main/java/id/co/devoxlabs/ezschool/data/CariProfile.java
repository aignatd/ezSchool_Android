package id.co.devoxlabs.ezschool.data;

/**
 * Dibuat oleh : ignat
 * Tanggal : 27-Nov-16
 * HP/WA : 0857 7070 6 777
 */
public class CariProfile
{
  private String CariProfile;
  private String KodeDevice;
  private Integer ParamID;

  public String getCariProfile()
  {
    return CariProfile;
  }

  public void setCariProfile(String cariProfile)
  {
    CariProfile = cariProfile;
  }

  public String getKodeDevice()
  {
    return KodeDevice;
  }

  public void setKodeDevice(String kodeDevice)
  {
    KodeDevice = kodeDevice;
  }

  public Integer getParamID()
  {
    return ParamID;
  }

  public void setParamID(Integer paramID)
  {
    ParamID = paramID;
  }

  private static CariProfile holder;
  public static CariProfile getInstance() {return holder;}

  private CariProfile()
  {
  }

  public static void initCariProfile()
  {
    holder = new CariProfile();
  }
}
