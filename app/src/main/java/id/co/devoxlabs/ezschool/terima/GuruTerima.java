package id.co.devoxlabs.ezschool.terima;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import id.co.devoxlabs.ezschool.model.CoreResponse;
import id.co.devoxlabs.ezschool.model.GuruResponse;
import id.co.devoxlabs.ezschool.model.WaliResponse;

/**
 * Dibuat oleh : ignat
 * Tanggal : 07-Feb-17
 * HP/WA : 0857 7070 6 777
 */
public class GuruTerima
{
  @SerializedName("CoreResponse")
  @Expose
  private CoreResponse coreResponse;
  @SerializedName("GuruResponse")
  @Expose
  private GuruResponse guruResponse;

  public CoreResponse getCoreResponse() {
    return coreResponse;
  }

  public void setCoreResponse(CoreResponse coreResponse) {
    this.coreResponse = coreResponse;
  }

  public GuruResponse getGuruResponse()
  {
    return guruResponse;
  }

  public void setGuruResponse(GuruResponse guruResponse)
  {
    this.guruResponse = guruResponse;
  }
}
