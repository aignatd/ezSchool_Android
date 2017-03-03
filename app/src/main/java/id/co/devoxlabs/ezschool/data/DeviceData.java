package id.co.devoxlabs.ezschool.data;

import java.util.List;

/**
 * Dibuat oleh : ignat
 * Tanggal : 14-Jan-17
 * HP/WA : 0857 7070 6 777
 */
public class DeviceData
{
  private String DeviceID;
  private String Nama;
  private String DeviceType;
  private String DeviceOS;

  public String getDeviceID()
  {
    return DeviceID;
  }

  public void setDeviceID(String deviceID)
  {
    DeviceID = deviceID;
  }

  public String getNama()
  {
    return Nama;
  }

  public void setNama(String nama)
  {
    Nama = nama;
  }

  public String getDeviceType()
  {
    return DeviceType;
  }

  public void setDeviceType(String deviceType)
  {
    DeviceType = deviceType;
  }

  public String getDeviceOS()
  {
    return DeviceOS;
  }

  public void setDeviceOS(String deviceOS)
  {
    DeviceOS = deviceOS;
  }

  private static DeviceData holder;
  public static DeviceData getInstance() {return holder;}

  private DeviceData()
  {
  }

  public static void initDeviceData()
  {
    holder = new DeviceData();
  }
}
