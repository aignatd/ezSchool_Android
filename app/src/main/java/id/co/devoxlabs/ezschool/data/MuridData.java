package id.co.devoxlabs.ezschool.data;

import java.util.List;

/**
 * Dibuat oleh : ignat
 * Tanggal : 30-Jan-17
 * HP/WA : 0857 7070 6 777
 */
public class MuridData
{
  private String Photo;
  private String NAMASISWA;
  private String ALAMAT;
  private String PROPINSI;
  private String Kecamatan;
  private String KOTA;
  private String RT;
  private String RW;
  private String KODEPOS;
  private String TMPTLAHIR;
  private String TGLLAHIR;
  private String Area;
  private String Telpon;
  private String NIS;
  private Integer IdxSeks;
  private String JENISKELAMIN;
  private String Agama;
  private Integer IdxAgama;

  public String getPhoto()
  {
    return Photo;
  }

  public void setPhoto(String photo)
  {
    Photo = photo;
  }

  public String getNAMASISWA()
  {
    return NAMASISWA;
  }

  public void setNAMASISWA(String NAMASISWA)
  {
    this.NAMASISWA = NAMASISWA;
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

  public String getNIS()
  {
    return NIS;
  }

  public void setNIS(String NIS)
  {
    this.NIS = NIS;
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

  private static MuridData holder;
  public static MuridData getInstance() {return holder;}

  private MuridData()
  {
  }

  public static void initMuridData()
  {
    holder = new MuridData();
  }
}
