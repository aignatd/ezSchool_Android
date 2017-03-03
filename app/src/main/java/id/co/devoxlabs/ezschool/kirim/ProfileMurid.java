package id.co.devoxlabs.ezschool.kirim;

import id.co.devoxlabs.ezschool.data.DeviceData;
import id.co.devoxlabs.ezschool.data.MuridData;
import id.co.devoxlabs.ezschool.data.UserData;

/**
 * Dibuat oleh : ignat
 * Tanggal : 30-Jan-17
 * HP/WA : 0857 7070 6 777
 */
public class ProfileMurid
{
  private UserData DataUser;
  private MuridData DataProfile;
  private DeviceData DataDevice;

  public ProfileMurid(UserData dataUser, MuridData dataProfile, DeviceData dataDevice)
  {
    DataUser = dataUser;
    DataProfile = dataProfile;
    DataDevice = dataDevice;
  }
}
