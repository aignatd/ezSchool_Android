package id.co.devoxlabs.ezschool.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Dibuat oleh : ignat
 * Tanggal : 07-Feb-17
 * HP/WA : 0857 7070 6 777
 */
public class WaliResponse
{
  @SerializedName("Photo")
  @Expose
  private String Photo;
  @SerializedName("WALIMURID")
  @Expose
  private String WALIMURID;
  @SerializedName("ALAMAT")
  @Expose
  private String ALAMAT;
  @SerializedName("PROPINSI")
  @Expose
  private String PROPINSI;
  @SerializedName("Kecamatan")
  @Expose
  private String Kecamatan;
  @SerializedName("KOTA")
  @Expose
  private String KOTA;
  @SerializedName("RT")
  @Expose
  private String RT;
  @SerializedName("RW")
  @Expose
  private String RW;
  @SerializedName("KODEPOS")
  @Expose
  private String KODEPOS;
  @SerializedName("TMPTLAHIR")
  @Expose
  private String TMPTLAHIR;
  @SerializedName("TGLLAHIR")
  @Expose
  private String TGLLAHIR;
  @SerializedName("Area")
  @Expose
  private String Area;
  @SerializedName("Telpon")
  @Expose
  private String Telpon;
  @SerializedName("IdxSeks")
  @Expose
  private Integer IdxSeks;
  @SerializedName("JENISKELAMIN")
  @Expose
  private String JENISKELAMIN;
  @SerializedName("Agama")
  @Expose
  private String Agama;
  @SerializedName("IdxAgama")
  @Expose
  private Integer IdxAgama;
  @SerializedName("PENDIDIKANTERAKHIR")
  @Expose
  private String PENDIDIKANTERAKHIR;
  @SerializedName("IdxDidik")
  @Expose
  private Integer IdxDidik;
  @SerializedName("Status")
  @Expose
  private String Status;
  @SerializedName("IdxStatus")
  @Expose
  private Integer IdxStatus;
  @SerializedName("Pekerjaan")
  @Expose
  private String Pekerjaan;
  @SerializedName("IdxKerja")
  @Expose
  private Integer IdxKerja;
  @SerializedName("Jabatan")
  @Expose
  private String Jabatan;

  public String getPhoto()
  {
    return Photo;
  }

  public void setPhoto(String photo)
  {
    Photo = photo;
  }

  public String getWALIMURID()
  {
    return WALIMURID;
  }

  public void setWALIMURID(String WALIMURID)
  {
    this.WALIMURID = WALIMURID;
  }

  public String getALAMAT()
  {
    return ALAMAT;
  }

  public void setALAMAT(String ALAMAT)
  {
    this.ALAMAT = ALAMAT;
  }

  public String getPROPINSI()
  {
    return PROPINSI;
  }

  public void setPROPINSI(String PROPINSI)
  {
    this.PROPINSI = PROPINSI;
  }

  public String getKecamatan()
  {
    return Kecamatan;
  }

  public void setKecamatan(String kecamatan)
  {
    Kecamatan = kecamatan;
  }

  public String getKOTA()
  {
    return KOTA;
  }

  public void setKOTA(String KOTA)
  {
    this.KOTA = KOTA;
  }

  public String getRT()
  {
    return RT;
  }

  public void setRT(String RT)
  {
    this.RT = RT;
  }

  public String getRW()
  {
    return RW;
  }

  public void setRW(String RW)
  {
    this.RW = RW;
  }

  public String getKODEPOS()
  {
    return KODEPOS;
  }

  public void setKODEPOS(String KODEPOS)
  {
    this.KODEPOS = KODEPOS;
  }

  public String getTMPTLAHIR()
  {
    return TMPTLAHIR;
  }

  public void setTMPTLAHIR(String TMPTLAHIR)
  {
    this.TMPTLAHIR = TMPTLAHIR;
  }

  public String getTGLLAHIR()
  {
    return TGLLAHIR;
  }

  public void setTGLLAHIR(String TGLLAHIR)
  {
    this.TGLLAHIR = TGLLAHIR;
  }

  public String getArea()
  {
    return Area;
  }

  public void setArea(String area)
  {
    Area = area;
  }

  public String getTelpon()
  {
    return Telpon;
  }

  public void setTelpon(String telpon)
  {
    Telpon = telpon;
  }

  public Integer getIdxSeks()
  {
    return IdxSeks;
  }

  public void setIdxSeks(Integer idxSeks)
  {
    IdxSeks = idxSeks;
  }

  public String getJENISKELAMIN()
  {
    return JENISKELAMIN;
  }

  public void setJENISKELAMIN(String JENISKELAMIN)
  {
    this.JENISKELAMIN = JENISKELAMIN;
  }

  public String getAgama()
  {
    return Agama;
  }

  public void setAgama(String agama)
  {
    Agama = agama;
  }

  public Integer getIdxAgama()
  {
    return IdxAgama;
  }

  public void setIdxAgama(Integer idxAgama)
  {
    IdxAgama = idxAgama;
  }

  public String getPENDIDIKANTERAKHIR()
  {
    return PENDIDIKANTERAKHIR;
  }

  public void setPENDIDIKANTERAKHIR(String PENDIDIKANTERAKHIR)
  {
    this.PENDIDIKANTERAKHIR = PENDIDIKANTERAKHIR;
  }

  public Integer getIdxDidik()
  {
    return IdxDidik;
  }

  public void setIdxDidik(Integer idxDidik)
  {
    IdxDidik = idxDidik;
  }

  public String getStatus()
  {
    return Status;
  }

  public void setStatus(String status)
  {
    Status = status;
  }

  public Integer getIdxStatus()
  {
    return IdxStatus;
  }

  public void setIdxStatus(Integer idxStatus)
  {
    IdxStatus = idxStatus;
  }

  public String getPekerjaan()
  {
    return Pekerjaan;
  }

  public void setPekerjaan(String pekerjaan)
  {
    Pekerjaan = pekerjaan;
  }

  public Integer getIdxKerja()
  {
    return IdxKerja;
  }

  public void setIdxKerja(Integer idxKerja)
  {
    IdxKerja = idxKerja;
  }

  public String getJabatan()
  {
    return Jabatan;
  }

  public void setJabatan(String jabatan)
  {
    Jabatan = jabatan;
  }
}
