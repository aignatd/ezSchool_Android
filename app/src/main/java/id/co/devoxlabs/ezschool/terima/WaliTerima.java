package id.co.devoxlabs.ezschool.terima;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import id.co.devoxlabs.ezschool.model.CoreResponse;
import id.co.devoxlabs.ezschool.model.WaliResponse;

/**
 * Dibuat oleh : ignat
 * Tanggal : 07-Feb-17
 * HP/WA : 0857 7070 6 777
 */
public class WaliTerima
{
  @SerializedName("CoreResponse")
  @Expose
  private CoreResponse coreResponse;
  @SerializedName("WaliResponse")
  @Expose
  private WaliResponse waliResponse;

  public CoreResponse getCoreResponse() {
    return coreResponse;
  }

  public void setCoreResponse(CoreResponse coreResponse) {
    this.coreResponse = coreResponse;
  }

  public WaliResponse getWaliResponse()
  {
    return waliResponse;
  }

  public void setWaliResponse(WaliResponse waliResponse)
  {
    this.waliResponse = waliResponse;
  }
}
