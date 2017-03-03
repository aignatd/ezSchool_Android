package id.co.devoxlabs.ezschool.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Dibuat oleh : ignat
 * Tanggal : 07-Feb-17
 * HP/WA : 0857 7070 6 777
 */
public class UserResponse
{
  @SerializedName("Handphone")
  @Expose
  private String handphone;
  @SerializedName("Email")
  @Expose
  private String email;
  @SerializedName("IdxKomponen")
  @Expose
  private Integer idxKomponen;
  @SerializedName("Komponen")
  @Expose
  private String komponen;
  @SerializedName("LoginID")
  @Expose
  private String loginID;
  @SerializedName("Status")
  @Expose
  private String status;
  @SerializedName("Profile")
  @Expose
  private String profile;
  @SerializedName("Nama")
  @Expose
  private String nama;
  @SerializedName("PhotoURL")
  @Expose
  private String PhotoURL;

  public String getHandphone() {
    return handphone;
  }

  public void setHandphone(String handphone) {
    this.handphone = handphone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getIdxKomponen() {
    return idxKomponen;
  }

  public void setIdxKomponen(Integer idxKomponen) {
    this.idxKomponen = idxKomponen;
  }

  public String getKomponen() {
    return komponen;
  }

  public void setKomponen(String komponen) {
    this.komponen = komponen;
  }

  public String getLoginID() {
    return loginID;
  }

  public void setLoginID(String loginID) {
    this.loginID = loginID;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getProfile() {
    return profile;
  }

  public void setProfile(String profile) {
    this.profile = profile;
  }

  public String getNama() {
    return nama;
  }

  public void setNama(String nama) {
    this.nama = nama;
  }

  public String getPhotoURL()
  {
    return PhotoURL;
  }

  public void setPhotoURL(String photoURL)
  {
    PhotoURL = photoURL;
  }
}
