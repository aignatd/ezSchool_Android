package id.co.devoxlabs.ezschool.muridbaru;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import id.co.devoxlabs.ezschool.R;
import id.co.devoxlabs.ezschool.data.DeviceData;
import id.co.devoxlabs.ezschool.data.UserData;
import id.co.devoxlabs.ezschool.data.WaliData;
import id.co.devoxlabs.ezschool.kirim.ProfileWali;
import id.co.devoxlabs.ezschool.service.ProsesData;
import id.co.devoxlabs.ezschool.profiles.ProfileMain;
import id.co.devoxlabs.ezschool.utils.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragDataWali extends Fragment implements View.OnClickListener, FragLifecycle
{
  private String TAG = "[ProfileWali]";
  private PesanPopup pesan = new PesanPopup();
  List<EditText> lstInput = new ArrayList<>();
  List<String> lstMsg = new ArrayList<>();
  List<String> lstProfile = new ArrayList<>();
  private ProgressDialog progressDialog;

  private EditText etAlamatWali, etPropWali, etKotaWali, etTmpLahirWali, etTglLahirWali, etJabatan;
  private EditText etCamatWali, etRTWali, etRWWali, etKPosWali, etAreaWali, etNoWali;

  private RadioGroup rgSeks, rgPendidikan, rgPekerjaan;
  private Spinner spAgama, spStatus;

  Calendar dateandtimeWali;

  public fragDataWali()
  {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState)
  {
    // Inflate the layout for this fragment
    View root = inflater.inflate(R.layout.lay_fragdatawali, container, false);

    dateandtimeWali = Calendar.getInstance(Locale.US);
    BindingView(root);
    return root;
  }

  public void BindingView(View root)
  {
    root.findViewById(R.id.btnSimpanWali).setOnClickListener(this);

    etAlamatWali = (EditText) root.findViewById(R.id.etAlamatWali);
    etPropWali = (EditText) root.findViewById(R.id.etPropWali);
    etKotaWali = (EditText) root.findViewById(R.id.etKotaWali);
    etJabatan = (EditText) root.findViewById(R.id.etJabatan);

    etCamatWali = (EditText) root.findViewById(R.id.etCamatWali);
    etRTWali = (EditText) root.findViewById(R.id.etRTWali);
    etRWWali = (EditText) root.findViewById(R.id.etRWWali);
    etKPosWali = (EditText) root.findViewById(R.id.etKPosWali);
    etAreaWali = (EditText) root.findViewById(R.id.etAreaWali);
    etNoWali = (EditText) root.findViewById(R.id.etNoWali);

    rgSeks = (RadioGroup) root.findViewById(R.id.rgSeksWali);
    rgPendidikan = (RadioGroup) root.findViewById(R.id.rgPendidikan);
    rgPekerjaan = (RadioGroup) root.findViewById(R.id.rgPekerjaan);

    spAgama = (Spinner) root.findViewById(R.id.spAgamaWali);
    spStatus = (Spinner) root.findViewById(R.id.spStatusWali);

    etTmpLahirWali = (EditText) root.findViewById(R.id.etTmpLahirWali);
    etTglLahirWali = (EditText) root.findViewById(R.id.etTglLahirWali);
    etTglLahirWali.setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
      public void onFocusChange(View v, boolean hasFocus)
      {
        if(hasFocus)
        {
          myDatePickerDialog dpWali = new myDatePickerDialog(getContext(), dateandtimeWali, new myDatePickerDialog.DatePickerListner()
          {
            @Override
            public void OnCancelButton(Dialog datedialog)
            {
              datedialog.dismiss();
              etTmpLahirWali.requestFocus();
            }

            @Override
            public void OnDoneButton(Dialog datedialog, Calendar c)
            {
              Log.d("","");

              datedialog.dismiss();
              dateandtimeWali.set(Calendar.YEAR, c.get(Calendar.YEAR));
              dateandtimeWali.set(Calendar.MONTH, c.get(Calendar.MONTH));
              dateandtimeWali.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH));
              etTglLahirWali.setText(new SimpleDateFormat("dd-MM-yyyy").format(c.getTime()));
              etTmpLahirWali.requestFocus();
            }
          });

          dpWali.show();
        }
      }
    });
  }

  @Override
  public void onClick(View view)
  {
    switch (view.getId())
    {
      case R.id.btnSimpanWali:
        lstInput.clear();
        lstInput.add(etAlamatWali);
        lstInput.add(etPropWali);
        lstInput.add(etKotaWali);
        lstInput.add(etTmpLahirWali);
        lstInput.add(etTglLahirWali);
        lstMsg.add(getResources().getString(R.string.srtAlamat));
        lstMsg.add(getResources().getString(R.string.srtPropinsi));
        lstMsg.add(getResources().getString(R.string.srtKota));
        lstMsg.add(getResources().getString(R.string.srtTmpLahir));
        lstMsg.add(getResources().getString(R.string.srtTglLahir));

        if(fungsi.CekInput(lstInput, lstMsg, getContext()))
        {
          Log.d("", "");

          UserData.initUserData();
          UserData.getInstance().setHandphone(((ProfileMain) getActivity()).getStrHandphone());
          UserData.getInstance().setEmail(((ProfileMain) getActivity()).getStrEmail());
          UserData.getInstance().setKomponen(((ProfileMain) getActivity()).getStrKomponen());
          UserData.getInstance().setLoginID(fungsi.getIntFromSharedPref(getContext(), Preference.PrefJenisLogin));
          UserData.getInstance().setIdxKomponen(((ProfileMain) getActivity()).getIdxKomponen());
          UserData.getInstance().setNama(((ProfileMain) getActivity()).getStrNama());

          DeviceData.initDeviceData();
          DeviceData.getInstance().setDeviceID(fungsi.DeviceInfo(getContext(), 0));
          DeviceData.getInstance().setNama(fungsi.DeviceName());
          DeviceData.getInstance().setDeviceType(fungsi.DeviceTipe(getContext()));
          DeviceData.getInstance().setDeviceOS(fungsi.AndroidVersion());

          WaliData.initWaliData();
          WaliData.getInstance().setWALIMURID(((ProfileMain) getActivity()).getStrNama());
          WaliData.getInstance().setALAMAT(etAlamatWali.getText().toString().trim());
          WaliData.getInstance().setPROPINSI(etPropWali.getText().toString().trim());
          WaliData.getInstance().setKecamatan(etCamatWali.getText().toString().trim());
          WaliData.getInstance().setKOTA(etKotaWali.getText().toString().trim());
          WaliData.getInstance().setRT(etRTWali.getText().toString().trim());
          WaliData.getInstance().setRW(etRWWali.getText().toString().trim());
          WaliData.getInstance().setKODEPOS(etKPosWali.getText().toString().trim());
          WaliData.getInstance().setTMPTLAHIR(etTmpLahirWali.getText().toString().trim());
          WaliData.getInstance().setTGLLAHIR(etTglLahirWali.getText().toString().trim());
          WaliData.getInstance().setArea(etAreaWali.getText().toString().trim());
          WaliData.getInstance().setTelpon(etNoWali.getText().toString().trim());
          WaliData.getInstance().setIdxSeks(fungsi.idxRadioGroup(rgSeks));
          WaliData.getInstance().setJENISKELAMIN(fungsi.CekRadioGroup(rgSeks));
          WaliData.getInstance().setAgama(spAgama.getSelectedItem().toString());
          WaliData.getInstance().setIdxAgama(spAgama.getSelectedItemPosition());
          WaliData.getInstance().setPENDIDIKANTERAKHIR(fungsi.CekRadioGroup(rgPendidikan));
          WaliData.getInstance().setIdxDidik(fungsi.idxRadioGroup(rgPendidikan));
          WaliData.getInstance().setStatus(spStatus.getSelectedItem().toString());
          WaliData.getInstance().setIdxStatus(spStatus.getSelectedItemPosition());
          WaliData.getInstance().setPekerjaan(fungsi.CekRadioGroup(rgPekerjaan));
          WaliData.getInstance().setIdxKerja(fungsi.idxRadioGroup(rgPekerjaan));
          WaliData.getInstance().setJabatan(etJabatan.getText().toString().trim());

          ProsesData.UpdateDataProfile(getActivity(), getContext(), 2);
        }
      break;
    }
  }

  @Override
  public void onResumeFragment()
  {
    UserData.initUserData();
    UserData.getInstance().setHandphone(((ProfileMain) getActivity()).getStrHandphone());

    ProfileWali profWali = new ProfileWali(UserData.getInstance(), null, null);
    ProsesData.UpdateProfileWali(getActivity(), getContext(), UserData.getInstance(), profWali);
  }

  @Override
  public void onResumeMuridBaru()
  {
    
  }
}
