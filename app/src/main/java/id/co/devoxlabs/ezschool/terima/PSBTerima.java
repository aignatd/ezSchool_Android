package id.co.devoxlabs.ezschool.terima;

/**
 * Dibuat oleh : ignat
 * Tanggal : 07-Mar-17
 * HP/WA : 0857 7070 6 777
 */

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import id.co.devoxlabs.ezschool.model.CoreResponse;
import id.co.devoxlabs.ezschool.model.MuridResponse;
import id.co.devoxlabs.ezschool.model.WaliResponse;

public class PSBTerima
{
  @SerializedName("CoreResponse")
  @Expose
  private CoreResponse coreResponse;
  @SerializedName("MuridResponse")
  @Expose
  private List<MuridResponse> muridResponse = null;
  @SerializedName("WaliResponse")
  @Expose
  private WaliResponse waliResponse;

  public CoreResponse getCoreResponse() {
    return coreResponse;
  }

  public void setCoreResponse(CoreResponse coreResponse) {
    this.coreResponse = coreResponse;
  }

  public List<MuridResponse> getMuridResponse() {
    return muridResponse;
  }

  public void setMuridResponse(List<MuridResponse> muridResponse) {
    this.muridResponse = muridResponse;
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
