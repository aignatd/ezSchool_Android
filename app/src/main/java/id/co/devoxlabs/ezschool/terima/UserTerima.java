package id.co.devoxlabs.ezschool.terima;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import id.co.devoxlabs.ezschool.model.CoreResponse;
import id.co.devoxlabs.ezschool.model.UserResponse;

/**
 * Dibuat oleh : ignat
 * Tanggal : 07-Feb-17
 * HP/WA : 0857 7070 6 777
 */
public class UserTerima
{
  @SerializedName("CoreResponse")
  @Expose
  private CoreResponse coreResponse;
  @SerializedName("UserResponse")
  @Expose
  private UserResponse userResponse;

  public CoreResponse getCoreResponse() {
    return coreResponse;
  }

  public void setCoreResponse(CoreResponse coreResponse) {
    this.coreResponse = coreResponse;
  }

  public UserResponse getUserResponse() {
    return userResponse;
  }

  public void setUserResponse(UserResponse userResponse) {
    this.userResponse = userResponse;
  }
}
