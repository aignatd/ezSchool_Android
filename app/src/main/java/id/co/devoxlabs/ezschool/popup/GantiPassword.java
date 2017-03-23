package id.co.devoxlabs.ezschool.popup;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import id.co.devoxlabs.ezschool.R;
import id.co.devoxlabs.ezschool.data.DeviceData;
import id.co.devoxlabs.ezschool.data.UserData;
import id.co.devoxlabs.ezschool.service.ProsesData;
import id.co.devoxlabs.ezschool.utils.PesanPopup;
import id.co.devoxlabs.ezschool.utils.Preference;
import id.co.devoxlabs.ezschool.utils.fungsi;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Dibuat oleh : ignat
 * Tanggal : 08-Dec-16
 * HP/WA : 0857 7070 6 777
 */
public class GantiPassword extends Dialog implements View.OnClickListener
{
  private String TAG = "[Utama]";
  private ProgressDialog progressDialog;
  private PesanPopup pesan = new PesanPopup();
  List<EditText> lstInput = new ArrayList<>();
  List<String> lstMsg = new ArrayList<>();
  private List<String> lstLogin = new ArrayList<>();;

  public Activity ParentAct;

  private EditText etPassLama;
  private EditText etPassBaru;
  private EditText etKonfirPass;

  public GantiPassword(Activity parentAct)
  {
    super(parentAct);
    this.ParentAct = parentAct;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.lay_gantipassword);

    findViewById(R.id.btnGantiPass).setOnClickListener(this);
    findViewById(R.id.btnBatalPass).setOnClickListener(this);

    etPassLama = (EditText) findViewById(R.id.etPassLama);
    etPassBaru = (EditText) findViewById(R.id.etPassBaru);
    etKonfirPass = (EditText) findViewById(R.id.etKonfirPass);
  }

  @Override
  public void onClick(View v)
  {
    switch(v.getId())
    {
      case R.id.btnGantiPass:
        lstInput.clear();
        lstMsg.clear();
        lstInput.add(etPassLama);
        lstInput.add(etPassBaru);
        lstMsg.add(ParentAct.getResources().getString(R.string.PasswordLama));
        lstMsg.add(ParentAct.getResources().getString(R.string.PasswordKosong));

        if(fungsi.CekInput(lstInput, lstMsg, getContext()))
        {
          if(!etPassBaru.getText().toString().matches(etKonfirPass.getText().toString()))
          {
            pesan.TampilPesan1(getContext(), ParentAct.getResources().getString(R.string.txtPassBeda));
            etPassBaru.requestFocus();
          }
          else
          if(etPassLama.getText().toString().matches(etPassBaru.getText().toString()))
          {
            pesan.TampilPesan1(getContext(), ParentAct.getResources().getString(R.string.txtPassSama));
            etPassBaru.requestFocus();
          }
          else
            ProsesGantiPass();
        }
      break;
      case R.id.btnBatalPass:
        dismiss();
      break;
    }
  }

  private void ProsesGantiPass()
  {
    String strCari = fungsi.getStringFromSharedPref(getContext(), Preference.PrefCariProfile);

    UserData.getInstance().setHandphone(strCari);
    UserData.getInstance().setPassword(new String(Hex.encodeHex(DigestUtils.md5(etPassLama.getText().toString().trim()))));
    UserData.getInstance().setPassbaru(new String(Hex.encodeHex(DigestUtils.md5(etPassBaru.getText().toString().trim()))));

    DeviceData.initDeviceData();
    DeviceData.getInstance().setDeviceID(fungsi.DeviceInfo(getContext(), 0));

    ProsesData.ProsesUser(ParentAct, getContext(), 3);
  }
}

