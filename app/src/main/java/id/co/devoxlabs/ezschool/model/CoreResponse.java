package id.co.devoxlabs.ezschool.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Dibuat oleh : ignat
 * Tanggal : 27-Nov-16
 * HP/WA : 0857 7070 6 777
 */

public class CoreResponse
{
  @SerializedName("Kode")
  @Expose
  private String kode;
  @SerializedName("Pesan")
  @Expose
  private String pesan;
  @SerializedName("Handphone")
  @Expose
  private String handphone;

  public String getKode() {
    return kode;
  }

  public void setKode(String kode) {
    this.kode = kode;
  }

  public String getPesan() {
    return pesan;
  }

  public void setPesan(String pesan) {
    this.pesan = pesan;
  }

  public String getHandphone()
  {
    return handphone;
  }

  public void setHandphone(String handphone)
  {
    this.handphone = handphone;
  }
}
