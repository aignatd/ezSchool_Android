package id.co.devoxlabs.ezschool.data;

import java.util.List;

/**
 * Dibuat oleh : ignat
 * Tanggal : 06-Feb-17
 * HP/WA : 0857 7070 6 777
 */
public class GuruData
{
  private String Photo;
  private String NAMAGURU;
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
  private String NIG;
  private Integer IdxSeks;
  private String JENISKELAMIN;
  private String Agama;
  private Integer IdxAgama;
  private String PENDIDIKANTERAKHIR;
  private Integer IdxDidik;
  private String Status;
  private Integer IdxStatus;

  public String getPhoto()
  {
    return Photo;
  }

  public void setPhoto(String photo)
  {
    Photo = photo;
  }

  public String getNAMAGURU()
  {
    return NAMAGURU;
  }

  public void setNAMAGURU(String NAMAGURU)
  {
    this.NAMAGURU = NAMAGURU;
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

  public String getNIG()
  {
    return NIG;
  }

  public void setNIG(String NIG)
  {
    this.NIG = NIG;
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

  private static GuruData holder;
  public static GuruData getInstance() {return holder;}

  private GuruData()
  {
  }

  public static void initGuruData()
  {
    holder = new GuruData();
  }
}
