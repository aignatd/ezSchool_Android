package id.co.devoxlabs.ezschool.terima;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import id.co.devoxlabs.ezschool.model.CoreResponse;
import id.co.devoxlabs.ezschool.model.MuridResponse;

/**
 * Dibuat oleh : ignat
 * Tanggal : 07-Feb-17
 * HP/WA : 0857 7070 6 777
 */
public class MuridTerima
{
  @SerializedName("CoreResponse")
  @Expose
  private CoreResponse coreResponse;
  @SerializedName("MuridResponse")
  @Expose
  private MuridResponse muridResponse;

  public CoreResponse getCoreResponse() {
    return coreResponse;
  }

  public void setCoreResponse(CoreResponse coreResponse) {
    this.coreResponse = coreResponse;
  }

  public MuridResponse getMuridResponse()
  {
    return muridResponse;
  }

  public void setMuridResponse(MuridResponse muridResponse)
  {
    this.muridResponse = muridResponse;
  }
}
