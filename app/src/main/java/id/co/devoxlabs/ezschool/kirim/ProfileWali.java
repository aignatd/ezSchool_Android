package id.co.devoxlabs.ezschool.kirim;

import id.co.devoxlabs.ezschool.data.DeviceData;
import id.co.devoxlabs.ezschool.data.UserData;
import id.co.devoxlabs.ezschool.data.WaliData;

/**
 * Dibuat oleh : ignat
 * Tanggal : 07-Feb-17
 * HP/WA : 0857 7070 6 777
 */
public class ProfileWali
{
  private UserData DataUser;
  private WaliData DataProfile;
  private DeviceData DataDevice;

  public ProfileWali(UserData dataUser, WaliData dataProfile, DeviceData dataDevice)
  {
    DataUser = dataUser;
    DataProfile = dataProfile;
    DataDevice = dataDevice;
  }
}
