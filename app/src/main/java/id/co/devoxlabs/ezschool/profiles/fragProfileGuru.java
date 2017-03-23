package id.co.devoxlabs.ezschool.profiles;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import id.co.devoxlabs.ezschool.R;
import id.co.devoxlabs.ezschool.data.DeviceData;
import id.co.devoxlabs.ezschool.data.GuruData;
import id.co.devoxlabs.ezschool.data.UserData;
import id.co.devoxlabs.ezschool.kirim.ProfileGuru;
import id.co.devoxlabs.ezschool.service.ProsesData;
import id.co.devoxlabs.ezschool.utils.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragProfileGuru extends Fragment implements View.OnClickListener, FragLifecycle
{
  private String TAG = "[ProfileGuru]";
  private PesanPopup pesan = new PesanPopup();
  List<EditText> lstInput = new ArrayList<>();
  List<String> lstMsg = new ArrayList<>();
  List<String> lstProfile = new ArrayList<>();
  private ProgressDialog progressDialog;

  private EditText etAlamatGuru, etPropGuru, etKotaGuru, etTmpLahirGuru, etTglLahirGuru, etNIGN;
  private EditText etCamatGuru, etRTGuru, etRWGuru, etKPosGuru, etAreaGuru, etNoGuru;

  private RadioGroup rgSeks, rgPendidikan;
  private Spinner spAgama, spStatus;
  Calendar dateandtime;

  public fragProfileGuru()
  {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    // Inflate the layout for this fragment
    View root = inflater.inflate(R.layout.lay_fragprofileguru, container, false);

    dateandtime = Calendar.getInstance(Locale.US);
    BindingView(root);
    return root;
  }

  public void BindingView(View root)
  {
    root.findViewById(R.id.btnSimpanGuru).setOnClickListener(this);

    etAlamatGuru = (EditText) root.findViewById(R.id.etAlamatGuru);
    etPropGuru = (EditText) root.findViewById(R.id.etPropGuru);
    etKotaGuru = (EditText) root.findViewById(R.id.etKotaGuru);
    etNIGN = (EditText) root.findViewById(R.id.etNIGN);

    etCamatGuru = (EditText) root.findViewById(R.id.etCamatGuru);
    etRTGuru = (EditText) root.findViewById(R.id.etRTGuru);
    etRWGuru = (EditText) root.findViewById(R.id.etRWGuru);
    etKPosGuru = (EditText) root.findViewById(R.id.etKPosGuru);
    etAreaGuru = (EditText) root.findViewById(R.id.etAreaGuru);
    etNoGuru = (EditText) root.findViewById(R.id.etNoGuru);

    rgSeks = (RadioGroup) root.findViewById(R.id.rgSeksGuru);
    rgPendidikan = (RadioGroup) root.findViewById(R.id.rgPendidikanGuru);

    spAgama = (Spinner) root.findViewById(R.id.spAgamaGuru);
    spStatus = (Spinner) root.findViewById(R.id.spStatusGuru);

    etTmpLahirGuru = (EditText) root.findViewById(R.id.etTmpLahirGuru);
    etTglLahirGuru = (EditText) root.findViewById(R.id.etTglLahirGuru);
    etTglLahirGuru.setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
      public void onFocusChange(View v, boolean hasFocus)
      {
        if(hasFocus)
        {
          myDatePickerDialog dp = new myDatePickerDialog(getContext(), dateandtime, new myDatePickerDialog.DatePickerListner()
          {
            @Override
            public void OnDoneButton(Dialog datedialog, Calendar c)
            {
              datedialog.dismiss();
              dateandtime.set(Calendar.YEAR, c.get(Calendar.YEAR));
              dateandtime.set(Calendar.MONTH, c.get(Calendar.MONTH));
              dateandtime.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH));
              etTglLahirGuru.setText(new SimpleDateFormat("dd-MM-yyyy").format(c.getTime()));
              etTmpLahirGuru.requestFocus();
            }

            @Override
            public void OnCancelButton(Dialog datedialog)
            {
              datedialog.dismiss();
              etTmpLahirGuru.requestFocus();
            }
          });

          dp.show();
        }
      }
    });
  }

  @Override
  public void onClick(View view)
  {
    switch (view.getId())
    {
      case R.id.btnSimpanGuru:
        lstInput.clear();
        lstInput.add(etAlamatGuru);
        lstInput.add(etPropGuru);
        lstInput.add(etKotaGuru);
        lstInput.add(etTmpLahirGuru);
        lstInput.add(etTglLahirGuru);
        lstInput.add(etNIGN);
        lstMsg.add(getResources().getString(R.string.srtAlamat));
        lstMsg.add(getResources().getString(R.string.srtPropinsi));
        lstMsg.add(getResources().getString(R.string.srtKota));
        lstMsg.add(getResources().getString(R.string.srtTmpLahir));
        lstMsg.add(getResources().getString(R.string.srtTglLahir));
        lstMsg.add(getResources().getString(R.string.srtNIGN));

        if(fungsi.CekInput(lstInput, lstMsg, getContext()))
        {
          UserData.initUserData();
          UserData.getInstance().setHandphone(((ProfileMain) getActivity()).getStrHandphone());
          UserData.getInstance().setEmail(((ProfileMain) getActivity()).getStrEmail());
          UserData.getInstance().setIdxKomponen(((ProfileMain) getActivity()).getIdxKomponen());
          UserData.getInstance().setKomponen(((ProfileMain) getActivity()).getStrKomponen());
          UserData.getInstance().setLoginID(fungsi.getIntFromSharedPref(getContext(), Preference.PrefJenisLogin));
          UserData.getInstance().setNama(((ProfileMain) getActivity()).getStrNama());

          DeviceData.initDeviceData();
          DeviceData.getInstance().setDeviceID(fungsi.DeviceInfo(getContext(), 0));
          DeviceData.getInstance().setNama(fungsi.DeviceName());
          DeviceData.getInstance().setDeviceType(fungsi.DeviceTipe(getContext()));
          DeviceData.getInstance().setDeviceOS(fungsi.AndroidVersion());

          GuruData.initGuruData();
          GuruData.getInstance().setNAMAGURU(((ProfileMain) getActivity()).getStrNama());
          GuruData.getInstance().setALAMAT(etAlamatGuru.getText().toString().trim());
          GuruData.getInstance().setPROPINSI(etPropGuru.getText().toString().trim());
          GuruData.getInstance().setKecamatan(etCamatGuru.getText().toString().trim());
          GuruData.getInstance().setKOTA(etKotaGuru.getText().toString().trim());
          GuruData.getInstance().setRT(etRTGuru.getText().toString().trim());
          GuruData.getInstance().setRW(etRWGuru.getText().toString().trim());
          GuruData.getInstance().setKODEPOS(etKPosGuru.getText().toString().trim());
          GuruData.getInstance().setTMPTLAHIR(etTmpLahirGuru.getText().toString().trim());
          GuruData.getInstance().setTGLLAHIR(etTglLahirGuru.getText().toString().trim());
          GuruData.getInstance().setArea(etAreaGuru.getText().toString().trim());
          GuruData.getInstance().setTelpon(etNoGuru.getText().toString().trim());
          GuruData.getInstance().setNIG(etNIGN.getText().toString().trim());
          GuruData.getInstance().setIdxSeks(fungsi.idxRadioGroup(rgSeks));
          GuruData.getInstance().setJENISKELAMIN(fungsi.CekRadioGroup(rgSeks));
          GuruData.getInstance().setPENDIDIKANTERAKHIR(fungsi.CekRadioGroup(rgPendidikan));
          GuruData.getInstance().setIdxDidik(fungsi.idxRadioGroup(rgPendidikan));
          GuruData.getInstance().setStatus(spStatus.getSelectedItem().toString());
          GuruData.getInstance().setIdxStatus(spStatus.getSelectedItemPosition());
          GuruData.getInstance().setAgama(spAgama.getSelectedItem().toString());
          GuruData.getInstance().setIdxAgama(spAgama.getSelectedItemPosition());

          ProsesData.UpdateDataProfile(getActivity(), getContext(),1);
        }
      break;
    }
  }

  @Override
  public void onResumeFragment()
  {
    UserData.initUserData();
    UserData.getInstance().setHandphone(((ProfileMain) getActivity()).getStrHandphone());

    ProfileGuru profGuru = new ProfileGuru(UserData.getInstance(), null, null);
    ProsesData.UpdateProfileGuru(getActivity(), getContext(), UserData.getInstance(), profGuru);
  }

  @Override
  public void onResumeMuridBaru()
  {

  }
}
