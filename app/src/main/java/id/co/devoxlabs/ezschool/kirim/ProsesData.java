package id.co.devoxlabs.ezschool.kirim;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.*;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import id.co.devoxlabs.ezschool.Login;
import id.co.devoxlabs.ezschool.R;
import id.co.devoxlabs.ezschool.Utama;
import id.co.devoxlabs.ezschool.data.*;
import id.co.devoxlabs.ezschool.profiles.ProfileMain;
import id.co.devoxlabs.ezschool.service.DataService;
import id.co.devoxlabs.ezschool.terima.GuruTerima;
import id.co.devoxlabs.ezschool.terima.MuridTerima;
import id.co.devoxlabs.ezschool.terima.UserTerima;
import id.co.devoxlabs.ezschool.terima.WaliTerima;
import id.co.devoxlabs.ezschool.utils.FixValue;
import id.co.devoxlabs.ezschool.utils.PesanPopup;
import id.co.devoxlabs.ezschool.utils.Preference;
import id.co.devoxlabs.ezschool.utils.fungsi;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Dibuat oleh : ignat
 * Tanggal : 27-Nov-16
 * HP/WA : 0857 7070 6 777
 */
public class ProsesData extends AppCompatActivity
{
  static ProgressDialog progressDialog;

  public static DataService BindingData()
  {
    OkHttpClient okClient = new OkHttpClient();

    okClient.newBuilder().connectTimeout(FixValue.TimeoutKoneksi, TimeUnit.SECONDS).
        readTimeout(FixValue.TimeoutKoneksi, TimeUnit.SECONDS).
        writeTimeout(FixValue.TimeoutKoneksi, TimeUnit.SECONDS).build();

    Retrofit retBindingData = new Retrofit.Builder().baseUrl(FixValue.Host).
        addConverterFactory(GsonConverterFactory.create()).
        client(okClient).build();

    return retBindingData.create(DataService.class);
  }

  private static void ShowPesan(Activity activity, Class<?> cls, Context context, String strPesan)
  {
    PesanPopup pesan = new PesanPopup();

    if((activity == null) && (strPesan == null))
      pesan.TampilPesan0(context, cls);
    else
    if((activity == null) && (cls == null))
      pesan.TampilPesan1(context, strPesan);
    else
    if(activity == null)
      pesan.TampilPesan6(context, strPesan, cls);
    else
    if(cls == null)
      pesan.TampilPesan3(context, strPesan, activity);
    else
      pesan.TampilPesan4(context, strPesan, activity, cls);
  }

  public static void ProsesCekProfile(final Activity actParent, final Context context, final int intPilihan)
  {
    DataService glData = BindingData();

    progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.txtTunggu), context.getResources().getString(R.string.txtProsesProfile));
    progressDialog.setCancelable(false);

    if(fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE)
    {
      progressDialog.dismiss();
      ShowPesan(null, null, context, actParent.getResources().getString(R.string.txtNoKoneksi));
      return;
    }

    final Call<UserTerima> cld = glData.CekProfileService(CariProfile.getInstance());
    cld.enqueue(new Callback<UserTerima>()
    {
      @Override
      public void onResponse(Call<UserTerima> call, Response<UserTerima> response)
      {
        progressDialog.dismiss();

        if(response.isSuccessful())
        {
          if(response.body().getCoreResponse().getKode().matches(FixValue.ErrorUser))
            fungsi.storeToSharedPref(context, response.body().getCoreResponse().getPesan(), Preference.PrefCekProfile);
          else
          {
            String strTemp = response.body().getUserResponse().getProfile();
            fungsi.storeToSharedPref(context, strTemp, Preference.PrefCekProfile);

            if(!strTemp.matches(FixValue.NoProfile))
            {
              TextView tvNamaTemp = null;
              ImageView ivPhotoTemp;
              int ph;

              if(intPilihan == 0)
              {
                tvNamaTemp = (TextView) actParent.findViewById(R.id.tvNamaUtama);
                ivPhotoTemp = (ImageView) actParent.findViewById(R.id.ivProUtama);
                ph = R.drawable.prefuser;
              }
              else
              {
                tvNamaTemp = (TextView) actParent.findViewById(R.id.etNama);
                ivPhotoTemp = (ImageView) actParent.findViewById(R.id.ivPhotoProfile);
                ph = R.drawable.avatar;

                EditText etNoHPTemp = (EditText) actParent.findViewById(R.id.etNoHP);
                EditText etEmailTemp = (EditText) actParent.findViewById(R.id.etEmailAddr);
                RadioGroup rgKomponenTemp = (RadioGroup) actParent.findViewById(R.id.rgKomponen);
                RadioButton rbMuridTemp = (RadioButton) actParent.findViewById(R.id.rbMurid);
                RadioButton rbGuruTemp = (RadioButton) actParent.findViewById(R.id.rbGuru);
                RadioButton rbWaliTemp = (RadioButton) actParent.findViewById(R.id.rbWali);

                etNoHPTemp.setText(response.body().getUserResponse().getHandphone());
                etEmailTemp.setText(response.body().getUserResponse().getEmail());
                fungsi.PilihRadioGroup(rgKomponenTemp, response.body().getUserResponse().getIdxKomponen());

                ((ProfileMain) actParent).setLoginID(response.body().getUserResponse().getLoginID());

                etNoHPTemp.setEnabled(false);
                etEmailTemp.setEnabled(false);
                rbMuridTemp.setClickable(false);
                rbGuruTemp.setClickable(false);
                rbWaliTemp.setClickable(false);
              }

              tvNamaTemp.setText(response.body().getUserResponse().getNama());

              Picasso.with(context).load(response.body().getUserResponse().getPhotoURL()).networkPolicy(NetworkPolicy.NO_CACHE)
                  .memoryPolicy(MemoryPolicy.NO_CACHE)
                  .placeholder(ph)
                  .into(ivPhotoTemp);

              fungsi.storeToSharedPref(context, response.body().getUserResponse().getKomponen(), Preference.PrefKomponen);
            }
          }
        }
        else
          fungsi.storeToSharedPref(context, FixValue.NoProfile, Preference.PrefCekProfile);
      }

      @Override
      public void onFailure(Call<UserTerima> call, Throwable t)
      {
        progressDialog.dismiss();
        fungsi.storeToSharedPref(context, FixValue.NoProfile, Preference.PrefCekProfile);
      }
    });
  }

  public static void UpdateDataProfile(final Activity act, final Context context, int intProses)
  {
    progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.txtTunggu), context.getResources().getString(R.string.txtProsesProfile));
    progressDialog.setCancelable(false);

    DataService glData = BindingData();
    Call<UserTerima> tmp = null;

    if(intProses == 0)
    {
      ProfileMurid ld = new ProfileMurid(UserData.getInstance(), MuridData.getInstance(), DeviceData.getInstance());
      tmp = glData.UpdateMuridService(ld);
    }
    else
    if(intProses == 1)
    {
      ProfileGuru ld = new ProfileGuru(UserData.getInstance(), GuruData.getInstance(), DeviceData.getInstance());
      tmp = glData.UpdateGuruService(ld);
    }
    else
    if(intProses == 2)
    {
      ProfileWali ld = new ProfileWali(UserData.getInstance(), WaliData.getInstance(), DeviceData.getInstance());
      tmp = glData.UpdateWaliService(ld);
    }

    if(fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE)
    {
      progressDialog.dismiss();
      ShowPesan(null, null, context, act.getResources().getString(R.string.txtNoKoneksi));
      return;
    }

    final Call<UserTerima> cld = tmp;
    cld.enqueue(new Callback<UserTerima>()
    {
      @Override
      public void onResponse(Call<UserTerima> call, Response<UserTerima> response)
      {
        progressDialog.dismiss();

        if(response.isSuccessful())
        {
          if(response.body().getCoreResponse().getKode().matches(FixValue.ErrorUser))
            ShowPesan(null, null, context, response.body().getCoreResponse().getPesan());
          else
          {
            fungsi.storeToSharedPref(context, ((ProfileMain) act).getStrKomponen(), Preference.PrefKomponen);
            UpdatePhotoProfile(act, context, ((ProfileMain) act).getStrHandphone(), ((ProfileMain) act).getStrKomponen());
          }
        }
        else
          ShowPesan(null, null, context, context.getResources().getString(R.string.txtResponGagal));
      }

      @Override
      public void onFailure(Call<UserTerima> call, Throwable t)
      {
        progressDialog.dismiss();
        ShowPesan(null, null, context, context.getResources().getString(R.string.txtNoRespon));
      }
    });
  }

  private static void UpdatePhotoProfile(Activity actParent, final Context context, String strNoHP, final String strKomponen)
  {
    progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.txtTunggu), context.getResources().getString(R.string.txtProsesProfile));
    progressDialog.setCancelable(false);

    String strNFile = strNoHP + ".jpg";
    File image = fungsi.FolderAplikasi();
    File FileProfile = new File(image.getAbsolutePath() + "/" + strNFile);

    if(fungsi.getIntFromSharedPref(context, Preference.PrefPhotoSelfie) == 0)
    {
      progressDialog.dismiss();
      ShowPesan(null, Utama.class, context, null);
      return;
    }

    if(!FileProfile.exists())
    {
      progressDialog.dismiss();
      ShowPesan(null, Utama.class, context, null);
      return;
    }

    if(fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE)
    {
      progressDialog.dismiss();
      ShowPesan(null, null, context, actParent.getResources().getString(R.string.txtNoKoneksi));
      return;
    }

    RequestBody rbFile = RequestBody.create(MediaType.parse("image/jpeg"), FileProfile);
    MultipartBody.Part Namafile = MultipartBody.Part.createFormData("Photo", strNFile, rbFile);
    RequestBody rbNoHP = RequestBody.create(MediaType.parse("text/plain"), strNoHP);
    RequestBody rbKomponen = RequestBody.create(MediaType.parse("text/plain"), strKomponen);
    RequestBody rbKodeDevice = RequestBody.create(MediaType.parse("text/plain"), fungsi.DeviceInfo(context, 0));

    DataService glData = BindingData();
    final Call<UserTerima> cld = glData.UploadPhotoService(rbKomponen, rbNoHP, Namafile, rbKodeDevice);
    cld.enqueue(new Callback<UserTerima>()
    {
      @Override
      public void onResponse(Call<UserTerima> call, Response<UserTerima> response)
      {
        progressDialog.dismiss();

        if(response.isSuccessful())
        {
          if(response.body().getCoreResponse().getKode().matches(FixValue.ErrorUser))
            ShowPesan(null, null, context, response.body().getCoreResponse().getPesan());
          else
          {
            fungsi.storeToSharedPref(context, strKomponen, Preference.PrefKomponen);
            fungsi.storeToSharedPref(context, 0, Preference.PrefPhotoSelfie);
            ShowPesan(null, Utama.class, context, null);
          }
        }
        else
          ShowPesan(null, null, context, context.getResources().getString(R.string.txtResponGagal));
      }

      @Override
      public void onFailure(Call<UserTerima> call, Throwable t)
      {
        progressDialog.dismiss();
        ShowPesan(null, null, context, context.getResources().getString(R.string.txtNoRespon));
      }
    });
  }

  public static void UpdateProfileMurid(final Activity actParent, final Context context, UserData uData, ProfileMurid profMurid)
  {
    progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.txtTunggu), context.getResources().getString(R.string.txtProsesProfile));
    progressDialog.setCancelable(false);

    if(fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE)
    {
      progressDialog.dismiss();
      ShowPesan(null, null, context, actParent.getResources().getString(R.string.txtNoKoneksi));
      return;
    }

    DataService glData = BindingData();

    final Call<MuridTerima> cld = glData.CekProfileMurid(profMurid);
    cld.enqueue(new Callback<MuridTerima>()
    {
      @Override
      public void onResponse(Call<MuridTerima> call, Response<MuridTerima> response)
      {
        progressDialog.dismiss();

        if(response.isSuccessful())
        {
          if(!response.body().getCoreResponse().getKode().matches(FixValue.ErrorUser))
          {
            EditText etAlamatMurid = (EditText) actParent.findViewById(R.id.etAlamatMurid);
            EditText etPropinsiMurid = (EditText) actParent.findViewById(R.id.etPropinsiMurid);
            EditText etKotaMurid = (EditText) actParent.findViewById(R.id.etKotaMurid);
            EditText etNISN = (EditText) actParent.findViewById(R.id.etNISN);

            EditText etCamatMurid = (EditText) actParent.findViewById(R.id.etCamatMurid);
            EditText etRTMurid = (EditText) actParent.findViewById(R.id.etRTMurid);
            EditText etRWMurid = (EditText) actParent.findViewById(R.id.etRWMurid);
            EditText etKPosMurid = (EditText) actParent.findViewById(R.id.etKPosMurid);
            EditText etAreaMurid = (EditText) actParent.findViewById(R.id.etAreaMurid);
            EditText etNoMurid = (EditText) actParent.findViewById(R.id.etNoMurid);

            EditText etTmpLahirMurid = (EditText) actParent.findViewById(R.id.etTmpLahirMurid);
            EditText etTglLahirMurid = (EditText) actParent.findViewById(R.id.etTglLahirMurid);

            RadioGroup rgSeks = (RadioGroup) actParent.findViewById(R.id.rgSeksMurid);
            Spinner spAgama = (Spinner) actParent.findViewById(R.id.spAgamaMurid);

            etAlamatMurid.setText(response.body().getMuridResponse().getALAMAT());
            etPropinsiMurid.setText(response.body().getMuridResponse().getPROPINSI());
            etKotaMurid.setText(response.body().getMuridResponse().getKOTA());
            etNISN.setText(response.body().getMuridResponse().getNIS());

            etCamatMurid.setText(response.body().getMuridResponse().getKecamatan());
            etRTMurid.setText(response.body().getMuridResponse().getRT());
            etRWMurid.setText(response.body().getMuridResponse().getRW());
            etKPosMurid.setText(response.body().getMuridResponse().getKODEPOS());
            etAreaMurid.setText(response.body().getMuridResponse().getArea());
            etNoMurid.setText(response.body().getMuridResponse().getTelpon());

            fungsi.PilihRadioGroup(rgSeks, response.body().getMuridResponse().getIdxSeks());
            spAgama.setSelection(response.body().getMuridResponse().getIdxAgama(), true);

            etTmpLahirMurid.setText(response.body().getMuridResponse().getTMPTLAHIR());
            etTglLahirMurid.setText(response.body().getMuridResponse().getTGLLAHIR());
          }
        }
        else
          fungsi.storeToSharedPref(context, FixValue.NoProfile, Preference.PrefCekProfile);
      }

      @Override
      public void onFailure(Call<MuridTerima> call, Throwable t)
      {
        progressDialog.dismiss();
        fungsi.storeToSharedPref(context, FixValue.NoProfile, Preference.PrefCekProfile);
      }
    });
  }

  public static void UpdateProfileGuru(final Activity actParent, final Context context, UserData uData, ProfileGuru profGuru)
  {
    progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.txtTunggu), context.getResources().getString(R.string.txtProsesProfile));
    progressDialog.setCancelable(false);

    if(fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE)
    {
      progressDialog.dismiss();
      ShowPesan(null, null, context, actParent.getResources().getString(R.string.txtNoKoneksi));
      return;
    }

    DataService glData = BindingData();

    final Call<GuruTerima> cld = glData.CekProfileGuru(profGuru);
    cld.enqueue(new Callback<GuruTerima>()
    {
      @Override
      public void onResponse(Call<GuruTerima> call, Response<GuruTerima> response)
      {
        progressDialog.dismiss();

        if(response.isSuccessful())
        {
          if(!response.body().getCoreResponse().getKode().matches(FixValue.ErrorUser))
          {
            EditText etAlamatGuru = (EditText) actParent.findViewById(R.id.etAlamatGuru);
            EditText etPropGuru = (EditText) actParent.findViewById(R.id.etPropGuru);
            EditText etKotaGuru = (EditText) actParent.findViewById(R.id.etKotaGuru);
            EditText etNIGN = (EditText) actParent.findViewById(R.id.etNIGN);

            EditText etCamatGuru = (EditText) actParent.findViewById(R.id.etCamatGuru);
            EditText etRTGuru = (EditText) actParent.findViewById(R.id.etRTGuru);
            EditText etRWGuru = (EditText) actParent.findViewById(R.id.etRWGuru);
            EditText etKPosGuru = (EditText) actParent.findViewById(R.id.etKPosGuru);
            EditText etAreaGuru = (EditText) actParent.findViewById(R.id.etAreaGuru);
            EditText etNoGuru = (EditText) actParent.findViewById(R.id.etNoGuru);

            EditText etTmpLahirGuru = (EditText) actParent.findViewById(R.id.etTmpLahirGuru);
            EditText etTglLahirGuru = (EditText) actParent.findViewById(R.id.etTglLahirGuru);

            RadioGroup rgSeks = (RadioGroup) actParent.findViewById(R.id.rgSeksGuru);
            RadioGroup rgPendidikan = (RadioGroup) actParent.findViewById(R.id.rgPendidikanGuru);

            Spinner spAgama = (Spinner) actParent.findViewById(R.id.spAgamaGuru);
            Spinner spStatus = (Spinner) actParent.findViewById(R.id.spStatusGuru);

            etAlamatGuru.setText(response.body().getGuruResponse().getALAMAT());
            etPropGuru.setText(response.body().getGuruResponse().getPROPINSI());
            etKotaGuru.setText(response.body().getGuruResponse().getKOTA());
            etNIGN.setText(response.body().getGuruResponse().getNIG());

            etCamatGuru.setText(response.body().getGuruResponse().getKecamatan());
            etRTGuru.setText(response.body().getGuruResponse().getRT());
            etRWGuru.setText(response.body().getGuruResponse().getRW());
            etKPosGuru.setText(response.body().getGuruResponse().getKODEPOS());
            etAreaGuru.setText(response.body().getGuruResponse().getArea());
            etNoGuru.setText(response.body().getGuruResponse().getTelpon());

            fungsi.PilihRadioGroup(rgSeks, response.body().getGuruResponse().getIdxSeks());
            fungsi.PilihRadioGroup(rgPendidikan, response.body().getGuruResponse().getIdxDidik());

            spAgama.setSelection(response.body().getGuruResponse().getIdxAgama(), true);
            spStatus.setSelection(response.body().getGuruResponse().getIdxStatus(), true);

            etTmpLahirGuru.setText(response.body().getGuruResponse().getTMPTLAHIR());
            etTglLahirGuru.setText(response.body().getGuruResponse().getTGLLAHIR());
          }
        }
        else
          fungsi.storeToSharedPref(context, FixValue.NoProfile, Preference.PrefCekProfile);
      }

      @Override
      public void onFailure(Call<GuruTerima> call, Throwable t)
      {
        progressDialog.dismiss();
        fungsi.storeToSharedPref(context, FixValue.NoProfile, Preference.PrefCekProfile);
      }
    });
  }

  public static void UpdateProfileWali(final Activity actParent, final Context context, UserData uData, ProfileWali profWali)
  {
    progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.txtTunggu), context.getResources().getString(R.string.txtProsesProfile));
    progressDialog.setCancelable(false);

    if(fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE)
    {
      progressDialog.dismiss();
      ShowPesan(null, null, context, actParent.getResources().getString(R.string.txtNoKoneksi));
      return;
    }

    DataService glData = BindingData();

    final Call<WaliTerima> cld = glData.CekProfileWali(profWali);
    cld.enqueue(new Callback<WaliTerima>()
    {
      @Override
      public void onResponse(Call<WaliTerima> call, Response<WaliTerima> response)
      {
        progressDialog.dismiss();

        if(response.isSuccessful())
        {
          if(!response.body().getCoreResponse().getKode().matches(FixValue.ErrorUser))
          {
            EditText etAlamatWali = (EditText) actParent.findViewById(R.id.etAlamatWali);
            EditText etPropWali = (EditText) actParent.findViewById(R.id.etPropWali);
            EditText etKotaWali = (EditText) actParent.findViewById(R.id.etKotaWali);
            EditText etJabatan = (EditText) actParent.findViewById(R.id.etJabatan);

            EditText etCamatWali = (EditText) actParent.findViewById(R.id.etCamatWali);
            EditText etRTWali = (EditText) actParent.findViewById(R.id.etRTWali);
            EditText etRWWali = (EditText) actParent.findViewById(R.id.etRWWali);
            EditText etKPosWali = (EditText) actParent.findViewById(R.id.etKPosWali);
            EditText etAreaWali = (EditText) actParent.findViewById(R.id.etAreaWali);
            EditText etNoWali = (EditText) actParent.findViewById(R.id.etNoWali);

            EditText etTmpLahirWali = (EditText) actParent.findViewById(R.id.etTmpLahirWali);
            EditText etTglLahirWali = (EditText) actParent.findViewById(R.id.etTglLahirWali);

            RadioGroup rgSeks = (RadioGroup) actParent.findViewById(R.id.rgSeksWali);
            RadioGroup rgPendidikan = (RadioGroup) actParent.findViewById(R.id.rgPendidikanWali);
            RadioGroup rgPekerjaan = (RadioGroup) actParent.findViewById(R.id.rgPekerjaan);

            Spinner spAgama = (Spinner) actParent.findViewById(R.id.spAgamaWali);
            Spinner spStatus = (Spinner) actParent.findViewById(R.id.spStatusWali);

            etAlamatWali.setText(response.body().getWaliResponse().getALAMAT());
            etPropWali.setText(response.body().getWaliResponse().getPROPINSI());
            etKotaWali.setText(response.body().getWaliResponse().getKOTA());
            etJabatan.setText(response.body().getWaliResponse().getJabatan());

            etCamatWali.setText(response.body().getWaliResponse().getKecamatan());
            etRTWali.setText(response.body().getWaliResponse().getRT());
            etRWWali.setText(response.body().getWaliResponse().getRW());
            etKPosWali.setText(response.body().getWaliResponse().getKODEPOS());
            etAreaWali.setText(response.body().getWaliResponse().getArea());
            etNoWali.setText(response.body().getWaliResponse().getTelpon());

            fungsi.PilihRadioGroup(rgSeks, response.body().getWaliResponse().getIdxSeks());
            fungsi.PilihRadioGroup(rgPendidikan, response.body().getWaliResponse().getIdxDidik());
            fungsi.PilihRadioGroup(rgPekerjaan, response.body().getWaliResponse().getIdxKerja());

            spAgama.setSelection(response.body().getWaliResponse().getIdxAgama(), true);
            spStatus.setSelection(response.body().getWaliResponse().getIdxStatus(), true);

            etTmpLahirWali.setText(response.body().getWaliResponse().getTMPTLAHIR());
            etTglLahirWali.setText(response.body().getWaliResponse().getTGLLAHIR());
          }
        }
        else
          fungsi.storeToSharedPref(context, FixValue.NoProfile, Preference.PrefCekProfile);
      }

      @Override
      public void onFailure(Call<WaliTerima> call, Throwable t)
      {
        progressDialog.dismiss();
        fungsi.storeToSharedPref(context, FixValue.NoProfile, Preference.PrefCekProfile);
      }
    });
  }

  public static void ProsesUser(final Activity actParent, final Context context, int intService)
  {
    LoginHolder ld = new LoginHolder(UserData.getInstance(), DeviceData.getInstance());
    DataService glData = BindingData();
    Call<UserTerima> tmp = null;

    if(intService == 0)
    {
      tmp = glData.LoginService(ld);
      progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.txtTunggu), context.getResources().getString(R.string.txtProsesLogin));
    }
    else
    if(intService == 1)
    {
      tmp = glData.BuatAkunService(ld);
      progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.txtTunggu), context.getResources().getString(R.string.txtProsesBuat));
    }
    else
    if(intService == 2)
    {
      tmp = glData.LogoutService(ld);
      progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.txtTunggu), context.getResources().getString(R.string.txtProsesLogout));
    }
    else
    if(intService == 3)
    {
      tmp = glData.GantiPassService(ld);
      progressDialog = ProgressDialog.show(context, context.getResources().getString(R.string.txtTunggu), context.getResources().getString(R.string.txtProsesGantiPass));
    }

    progressDialog.setCancelable(false);

    if(fungsi.isNetworkAvailable(context) == FixValue.TYPE_NONE)
    {
      progressDialog.dismiss();
      ShowPesan(null, null, context, actParent.getResources().getString(R.string.txtNoKoneksi));
      return;
    }

    final Call<UserTerima> cld = tmp;
    final int intsvr = intService;

    cld.enqueue(new Callback<UserTerima>()
    {
      @Override
      public void onResponse(Call<UserTerima> call, Response<UserTerima> response)
      {
        progressDialog.dismiss();

        if (response.isSuccessful())
        {
          if(response.body().getCoreResponse().getKode().matches(FixValue.ErrorUser))
            ShowPesan(null, null, context, response.body().getCoreResponse().getPesan());
          else
          {
            if(intsvr == 3)
              ShowPesan(null, Utama.class, context, response.body().getCoreResponse().getPesan());
            else
            if(intsvr == 2)
            {
              fungsi.storeToSharedPref(context, 0, Preference.PrefJenisLogin);
              ShowPesan(null, Login.class, context, null);
            }
            else
            {
              int intTemp = fungsi.getIntFromSharedPref(context, Preference.PrefJenisLogin);

              if(intTemp >= 2)
                fungsi.storeToSharedPref(context, intTemp, Preference.PrefJenisLogin);
              else
                fungsi.storeToSharedPref(context, 1, Preference.PrefJenisLogin);

              fungsi.storeToSharedPref(context, response.body().getUserResponse().getHandphone(), Preference.PrefUserHP);
              ShowPesan(null, Utama.class, context, null);
            }
          }
        }
        else
          ShowPesan(null, null, context, context.getResources().getString(R.string.txtResponGagal));
      }

      @Override
      public void onFailure(Call<UserTerima> call, Throwable t)
      {
        progressDialog.dismiss();
        ShowPesan(null, null, context, context.getResources().getString(R.string.txtNoRespon));
      }
    });
  }
}

