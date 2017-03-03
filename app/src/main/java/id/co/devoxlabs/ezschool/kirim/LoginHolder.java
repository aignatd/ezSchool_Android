package id.co.devoxlabs.ezschool.kirim;

import id.co.devoxlabs.ezschool.data.DeviceData;
import id.co.devoxlabs.ezschool.data.UserData;

/**
 * Dibuat oleh : ignat
 * Tanggal : 27-Nov-16
 * HP/WA : 0857 7070 6 777
 */
public class LoginHolder
{
  private UserData DataUser;
  private DeviceData DataDevice;

  public LoginHolder(UserData dataUser, DeviceData dataDevice)
  {
    DataUser = dataUser;
    DataDevice = dataDevice;
  }
}
