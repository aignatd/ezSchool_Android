/*
 * Copyright (c) 2016 oleh Agustinus Ignat Deswanto
 *
 *  Dilarang menyalah gunakan aplikasi ini terutama untuk tindak kejahatan.
 *  Jika ada pertanyaan seputar aplikasi ini silakan menghubungi :
 *
 *  Agustinus Ignat Deswanto
 *  Permata Depok Nilam F5a No. 5
 *  Citayam - Depok 16430
 *  Jawa Barat - Indonesia
 *  HP/WA : 085770706777
 *  Email : aignatd@gmail.com
 *
 */

package id.co.devoxlabs.ezschool.service;

import id.co.devoxlabs.ezschool.data.CariProfile;
import id.co.devoxlabs.ezschool.kirim.LoginHolder;
import id.co.devoxlabs.ezschool.kirim.ProfileGuru;
import id.co.devoxlabs.ezschool.kirim.ProfileMurid;
import id.co.devoxlabs.ezschool.kirim.ProfileWali;
import id.co.devoxlabs.ezschool.terima.*;
import id.co.devoxlabs.ezschool.utils.FixValue;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Dibuat oleh : ignat
 * Tanggal : 26-Nov-16
 * HP/WA : 0857 7070 6 777
 */
public interface DataService
{
  @POST(FixValue.ParamLogin)
  Call<UserTerima> LoginService(@Body LoginHolder ld);

  @POST(FixValue.ParamBuatAkun)
  Call<UserTerima> BuatAkunService(@Body LoginHolder ld);

  @POST(FixValue.ParamLogout)
  Call<UserTerima> LogoutService(@Body LoginHolder uData);

  @POST(FixValue.ProfilePass)
  Call<UserTerima> GantiPassService(@Body LoginHolder uData);

  @POST(FixValue.ParamProfile)
  Call<UserTerima> CekProfileService(@Body CariProfile hpData);

  @POST(FixValue.ProfileUser)
  Call<UserTerima> UpdateMuridService(@Body ProfileMurid pmData);

  @POST(FixValue.ProfileUser)
  Call<UserTerima> UpdateGuruService(@Body ProfileGuru pgData);

  @POST(FixValue.ProfileUser)
  Call<UserTerima> UpdateWaliService(@Body ProfileWali pwData);

  @POST(FixValue.ParamProWali)
  Call<WaliTerima> CekProfileWali(@Body ProfileWali cpwData);

  @POST(FixValue.ParamProGuru)
  Call<GuruTerima> CekProfileGuru(@Body ProfileGuru cpgData);

  @POST(FixValue.ParamProMurid)
  Call<MuridTerima> CekProfileMurid(@Body ProfileMurid cpmData);

  @Multipart
  @POST(FixValue.UploadPhotoProfile)
  Call<UserTerima> UploadPhotoService(@Part("Komponen") RequestBody rbKomponen, @Part("Handphone") RequestBody rbNoHP,
                                      @Part MultipartBody.Part Photo, @Part("KodeDevice") RequestBody rbKodeDevice);

  @POST(FixValue.ParamProfile)
  Call<PSBTerima> AmbilDataPSB(@Body CariProfile cpmDataPSB);
}

