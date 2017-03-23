package id.co.devoxlabs.ezschool.profiles;

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
import id.co.devoxlabs.ezschool.data.MuridData;
import id.co.devoxlabs.ezschool.data.UserData;
import id.co.devoxlabs.ezschool.kirim.ProfileMurid;
import id.co.devoxlabs.ezschool.service.ProsesData;
import id.co.devoxlabs.ezschool.utils.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class fragProfileMurid extends Fragment implements View.OnClickListener, FragLifecycle
{
  private String TAG = "[ProfileMurid]";
  private PesanPopup pesan = new PesanPopup();
  List<EditText> lstInput = new ArrayList<>();
  List<String> lstMsg = new ArrayList<>();
  List<String> lstProfile = new ArrayList<>();
  private ProgressDialog progressDialog;

  private EditText etAlamatMurid, etPropinsiMurid, etKotaMurid, etTmpLahirMurid, etTglLahirMurid, etNISN;
  private EditText etCamatMurid, etRTMurid, etRWMurid, etKPosMurid, etAreaMurid, etNoMurid;

  private RadioGroup rgSeks;
  private Spinner spAgama;

  Calendar dateandtimeMurid;

  public fragProfileMurid()
  {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    // Inflate the layout for this fragment
    View root = inflater.inflate(R.layout.lay_fragprofilemurid, container, false);

    dateandtimeMurid = Calendar.getInstance(Locale.US);
    BindingView(root);
    return root;
  }

  public void BindingView(View root)
  {
    Log.d("", "");

    root.findViewById(R.id.btnSimpanMurid).setOnClickListener(this);

    etAlamatMurid = (EditText) root.findViewById(R.id.etAlamatMurid);
    etPropinsiMurid = (EditText) root.findViewById(R.id.etPropinsiMurid);
    etKotaMurid = (EditText) root.findViewById(R.id.etKotaMurid);
    etNISN = (EditText) root.findViewById(R.id.etNISN);

    etCamatMurid = (EditText) root.findViewById(R.id.etCamatMurid);
    etRTMurid = (EditText) root.findViewById(R.id.etRTMurid);
    etRWMurid = (EditText) root.findViewById(R.id.etRWMurid);
    etKPosMurid = (EditText) root.findViewById(R.id.etKPosMurid);
    etAreaMurid = (EditText) root.findViewById(R.id.etAreaMurid);
    etNoMurid = (EditText) root.findViewById(R.id.etNoMurid);
    rgSeks = (RadioGroup) root.findViewById(R.id.rgSeksMurid);

    spAgama = (Spinner) root.findViewById(R.id.spAgamaMurid);

    etTmpLahirMurid = (EditText) root.findViewById(R.id.etTmpLahirMurid);
    etTglLahirMurid = (EditText) root.findViewById(R.id.etTglLahirMurid);
    etTglLahirMurid.setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
      public void onFocusChange(View v, boolean hasFocus)
      {
        if(hasFocus)
        {
          Log.d("", "");

          myDatePickerDialog dpMurid = new myDatePickerDialog(getContext(), dateandtimeMurid, new myDatePickerDialog.DatePickerListner()
          {
            @Override
            public void OnCancelButton(Dialog datedialog)
            {
              Log.d("", "");

              datedialog.dismiss();
              etTmpLahirMurid.requestFocus();
            }

            @Override
            public void OnDoneButton(Dialog datedialog, Calendar c)
            {
              Log.d("","");

              datedialog.dismiss();
              dateandtimeMurid.set(Calendar.YEAR, c.get(Calendar.YEAR));
              dateandtimeMurid.set(Calendar.MONTH, c.get(Calendar.MONTH));
              dateandtimeMurid.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH));
              etTglLahirMurid.setText(new SimpleDateFormat("dd-MM-yyyy").format(c.getTime()));
              etTmpLahirMurid.requestFocus();

              Log.d("","");
            }
          });

          dpMurid.show();
        }
      }
    });
  }

  @Override
  public void onClick(View view)
  {
    switch (view.getId())
    {
      case R.id.btnSimpanMurid:
        lstInput.clear();
        lstInput.add(etAlamatMurid);
        lstInput.add(etPropinsiMurid);
        lstInput.add(etKotaMurid);
        lstInput.add(etTmpLahirMurid);
        lstInput.add(etTglLahirMurid);
        lstInput.add(etNISN);
        lstMsg.add(getResources().getString(R.string.srtAlamat));
        lstMsg.add(getResources().getString(R.string.srtPropinsi));
        lstMsg.add(getResources().getString(R.string.srtKota));
        lstMsg.add(getResources().getString(R.string.srtTmpLahir));
        lstMsg.add(getResources().getString(R.string.srtTglLahir));
        lstMsg.add(getResources().getString(R.string.srtNISN));

        if(fungsi.CekInput(lstInput, lstMsg, getContext()))
        {
          Log.d("", "");

          UserData.initUserData();
          UserData.getInstance().setHandphone(((ProfileMain) getActivity()).getStrHandphone());
          UserData.getInstance().setEmail(((ProfileMain) getActivity()).getStrEmail());
          UserData.getInstance().setIdxKomponen(((ProfileMain) getActivity()).getIdxKomponen());
          UserData.getInstance().setKomponen(((ProfileMain) getActivity()).getStrKomponen());
          UserData.getInstance().setLoginID(fungsi.getIntFromSharedPref(getContext(), Preference.PrefJenisLogin));
          UserData.getInstance().setNama(((ProfileMain) getActivity()).getStrNama());

          MuridData.initMuridData();
          MuridData.getInstance().setNAMASISWA(((ProfileMain) getActivity()).getStrNama());
          MuridData.getInstance().setALAMAT(etAlamatMurid.getText().toString().trim());
          MuridData.getInstance().setPROPINSI(etPropinsiMurid.getText().toString().trim());
          MuridData.getInstance().setKecamatan(etCamatMurid.getText().toString().trim());
          MuridData.getInstance().setKOTA(etKotaMurid.getText().toString().trim());
          MuridData.getInstance().setRT(etRTMurid.getText().toString().trim());
          MuridData.getInstance().setRW(etRWMurid.getText().toString().trim());
          MuridData.getInstance().setKODEPOS(etKPosMurid.getText().toString().trim());
          MuridData.getInstance().setTMPTLAHIR(etTmpLahirMurid.getText().toString().trim());
          MuridData.getInstance().setTGLLAHIR(etTglLahirMurid.getText().toString().trim());
          MuridData.getInstance().setArea(etAreaMurid.getText().toString().trim());
          MuridData.getInstance().setTelpon(etNoMurid.getText().toString().trim());
          MuridData.getInstance().setNIS(etNISN.getText().toString().trim());
          MuridData.getInstance().setIdxSeks(fungsi.idxRadioGroup(rgSeks));
          MuridData.getInstance().setJENISKELAMIN(fungsi.CekRadioGroup(rgSeks));
          MuridData.getInstance().setAgama(spAgama.getSelectedItem().toString());
          MuridData.getInstance().setIdxAgama(spAgama.getSelectedItemPosition());

          DeviceData.initDeviceData();
          DeviceData.getInstance().setDeviceID(fungsi.DeviceInfo(getContext(), 0));
          DeviceData.getInstance().setNama(fungsi.DeviceName());
          DeviceData.getInstance().setDeviceType(fungsi.DeviceTipe(getContext()));
          DeviceData.getInstance().setDeviceOS(fungsi.AndroidVersion());

          ProsesData.UpdateDataProfile(getActivity(), getContext(), 0);
        }
      break;
    }
  }

  @Override
  public void onResumeFragment()
  {
    UserData.initUserData();
    UserData.getInstance().setHandphone(((ProfileMain) getActivity()).getStrHandphone());

    ProfileMurid profMurid = new ProfileMurid(UserData.getInstance(), null, null);
    ProsesData.UpdateProfileMurid(getActivity(), getContext(), UserData.getInstance(), profMurid);
  }

  @Override
  public void onResumeMuridBaru()
  {

  }
}
