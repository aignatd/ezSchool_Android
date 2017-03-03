package id.co.devoxlabs.ezschool.kirim;

import id.co.devoxlabs.ezschool.data.DeviceData;
import id.co.devoxlabs.ezschool.data.GuruData;
import id.co.devoxlabs.ezschool.data.UserData;

/**
 * Dibuat oleh : ignat
 * Tanggal : 06-Feb-17
 * HP/WA : 0857 7070 6 777
 */
public class ProfileGuru
{
  private UserData DataUser;
  private GuruData DataProfile;
  private DeviceData DataDevice;

  public ProfileGuru(UserData dataUser, GuruData dataProfile, DeviceData dataDevice)
  {
    DataUser = dataUser;
    DataProfile = dataProfile;
    DataDevice = dataDevice;
  }
}
