package id.co.devoxlabs.ezschool;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.devoxlabs.ezschool.data.DeviceData;
import id.co.devoxlabs.ezschool.data.UserData;
import id.co.devoxlabs.ezschool.service.ProsesData;
import id.co.devoxlabs.ezschool.utils.PesanPopup;
import id.co.devoxlabs.ezschool.utils.fungsi;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.List;

public class BuatAkun extends AppCompatActivity implements View.OnClickListener
{
  @BindView(R.id.tvHeader) TextView tvHeader;
  @BindView(R.id.etNoHP) EditText etNoHP;
  @BindView(R.id.etPass) EditText etPass;
  @BindView(R.id.etVerPass) EditText etVerPass;

  private String TAG = "[BuatAkun]";
  private PesanPopup pesan = new PesanPopup();
  private Context context = this;
  List<EditText> lstInput = new ArrayList<>();
  List<String> lstMsg = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.lay_buatakun);
    ButterKnife.bind(this);

    tvHeader.setVisibility(View.VISIBLE);
    tvHeader.setText(getResources().getString(R.string.srtBuatAkun));
  }

  @Override
  public void onBackPressed()
  {
    BackActivity();
  }

  private void BackActivity()
  {
    Intent BuatAkunIntent = new Intent(BuatAkun.this, Login.class);
    startActivity(BuatAkunIntent);
    finish();
  }

  @Override
  @OnClick({R.id.ivBackIcon, R.id.btnKirim})
  public void onClick(View view)
  {
    switch(view.getId())
    {
      case R.id.ivBackIcon:
        BackActivity();
      break;
      case R.id.btnKirim:
        lstInput.clear();
        lstMsg.clear();
        lstInput.add(etNoHP);
        lstInput.add(etPass);
        lstMsg.add(getResources().getString(R.string.txtHPKosong));
        lstMsg.add(getResources().getString(R.string.PasswordKosong));

        if(fungsi.CekInput(lstInput, lstMsg, context))
        {
          if(!etPass.getText().toString().trim().matches(etVerPass.getText().toString().trim()))
            pesan.TampilPesan1(context, getResources().getString(R.string.txtPassBeda));
          else
          {
            UserData.initUserData();
            UserData.getInstance().setHandphone(etNoHP.getText().toString().trim());
            UserData.getInstance().setPassword(new String(Hex.encodeHex(DigestUtils.md5(etPass.getText().toString().trim()))));

            DeviceData.initDeviceData();
            DeviceData.getInstance().setDeviceID(fungsi.DeviceInfo(context, 0));
            DeviceData.getInstance().setNama(fungsi.DeviceName());
            DeviceData.getInstance().setDeviceType(fungsi.DeviceTipe(context));
            DeviceData.getInstance().setDeviceOS(fungsi.AndroidVersion());

            ProsesData.ProsesUser(BuatAkun.this, context, 1);
          }
        }
      break;
    }
  }
}
