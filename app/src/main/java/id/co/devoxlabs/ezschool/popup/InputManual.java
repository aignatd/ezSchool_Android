package id.co.devoxlabs.ezschool.popup;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import id.co.devoxlabs.ezschool.R;

/**
 * Dibuat oleh : ignat
 * Tanggal : 08-Dec-16
 * HP/WA : 0857 7070 6 777
 */
public class InputManual extends Dialog implements View.OnClickListener
{
  public Activity ParentAct;

  public InputManual(Activity parentAct)
  {
    super(parentAct);
    this.ParentAct = parentAct;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    setContentView(R.layout.lay_pilihmanual);

    findViewById(R.id.btnPilihBatal).setOnClickListener(this);
  }

  @Override
  public void onClick(View v)
  {
    switch(v.getId())
    {
      case R.id.btnPilihBatal:
        dismiss();
      break;
    }

    dismiss();
  }
}

