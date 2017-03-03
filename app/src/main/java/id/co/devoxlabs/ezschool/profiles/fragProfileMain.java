package id.co.devoxlabs.ezschool.profiles;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.view.SupportMenuInflater;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.view.menu.MenuPopupHelper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import id.co.devoxlabs.ezschool.R;
import id.co.devoxlabs.ezschool.data.CariProfile;
import id.co.devoxlabs.ezschool.kirim.ProsesData;
import id.co.devoxlabs.ezschool.selfie;
import id.co.devoxlabs.ezschool.utils.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class fragProfileMain extends Fragment implements View.OnClickListener, FragLifecycle
{
  private String TAG = "[ProfileMain]";
  private ProgressDialog progressDialog;
  private PesanPopup pesan = new PesanPopup();
  List<EditText> lstInput = new ArrayList<>();
  List<String> lstMsg = new ArrayList<>();

  private EditText etNoHP, etEmailAddr, etNama;
  private RadioGroup rgKomponen;
  private RadioButton rbMurid, rbGuru, rbWali;
  private ImageView ivPhotoProfile;

  public fragProfileMain()
  {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState)
  {
    // Inflate the layout for this fragment
    View root = inflater.inflate(R.layout.lay_fragprofilemain, container, false);

    BindingView(root);
    CekDataProfile();
    return root;
  }

  public void BindingView(View root)
  {
    root.findViewById(R.id.btnLanjutProfile).setOnClickListener(this);

    ivPhotoProfile = (ImageView) root.findViewById(R.id.ivPhotoProfile);
    ivPhotoProfile.setOnClickListener(this);

    rbMurid = (RadioButton) root.findViewById(R.id.rbMurid);
    rbGuru = (RadioButton) root.findViewById(R.id.rbGuru);
    rbWali = (RadioButton) root.findViewById(R.id.rbWali);

    etNama = (EditText) root.findViewById(R.id.etNama);
    etNoHP = (EditText) root.findViewById(R.id.etNoHP);
    etEmailAddr = (EditText) root.findViewById(R.id.etEmailAddr);
    rgKomponen = (RadioGroup) root.findViewById(R.id.rgKomponen);

    etNoHP.setEnabled(true);
    etEmailAddr.setEnabled(true);
    rbMurid.setClickable(true);
    rbGuru.setClickable(true);
    rbWali.setClickable(true);

    Intent intent = getActivity().getIntent();
    Bundle bd = intent.getExtras();

    if(bd != null)
    {
      String strProfile = bd.get("StatusProfile").toString();
      etNama.setText(bd.get("StatusNama").toString());

      if(fungsi.getIntFromSharedPref(getContext(), Preference.PrefJenisLogin) >= 2)
      {
        etNoHP.setText("");
        etEmailAddr.setText(strProfile);
        etEmailAddr.setEnabled(false);
      }
      else
      {
        etNoHP.setText(strProfile);
        etNoHP.setEnabled(false);
        etEmailAddr.setText("");
      }
    }
    else
    {
      etNama.setText(fungsi.getStringFromSharedPref(getContext(), Preference.PrefProfNama));
      etNoHP.setText(fungsi.getStringFromSharedPref(getContext(), Preference.PrefProfHP));
      etEmailAddr.setText(fungsi.getStringFromSharedPref(getContext(), Preference.PrefProfEmail));
      fungsi.PilihRadioGroup(rgKomponen, fungsi.getIntFromSharedPref(getContext(), Preference.PrefProfIdxKomponen));

      if(fungsi.getIntFromSharedPref(getContext(), Preference.PrefJenisLogin) >= 2)
        etEmailAddr.setEnabled(false);
      else
        etNoHP.setEnabled(false);
    }

    if(!etNoHP.getText().toString().trim().matches(""))
    {
      String strGambar = etNoHP.getText().toString().trim() + ".jpg";
      File fGambar = new File(fungsi.FolderAplikasi() + "/" + strGambar);

      if(fGambar.exists())
        ivPhotoProfile.setImageBitmap(fungsi.BukaGambar(fGambar));
    }
  }

  @Override
  public void onClick(View view)
  {
    switch (view.getId())
    {
      case R.id.ivPhotoProfile:
        PhotoProfile();
      break;
      case R.id.btnLanjutProfile:
        lstInput.clear();
        lstInput.add(etNama);
        lstInput.add(etNoHP);
        lstMsg.add(getResources().getString(R.string.srtNamaLengkap));
        lstMsg.add(getResources().getString(R.string.txtHPKosong));

        if(fungsi.CekInput(lstInput, lstMsg, getContext()))
        {
          String strEmail = etEmailAddr.getText().toString().trim();
          if(!strEmail.matches(""))
          {
            if(fungsi.ValidEmail(strEmail) == false)
            {
              pesan.TampilPesan1(getContext(), getResources().getString(R.string.txtEmailSalah));
              return;
            }
          }

          String strTemp = fungsi.CekRadioGroup(rgKomponen);

          ((ProfileMain) getActivity()).setStrNama(etNama.getText().toString().trim());
          ((ProfileMain) getActivity()).setStrHandphone(etNoHP.getText().toString().trim());
          ((ProfileMain) getActivity()).setStrEmail(etEmailAddr.getText().toString().trim());
          ((ProfileMain) getActivity()).setStrKomponen(strTemp);
          ((ProfileMain) getActivity()).setIdxKomponen(fungsi.idxRadioGroup(rgKomponen));

          if(strTemp.matches(getResources().getString(R.string.txtMurid)))
            ((ProfileMain) getActivity()).getProfilePager().setCurrentItem(1);
          else
          if(strTemp.matches(getResources().getString(R.string.txtGuru)))
            ((ProfileMain) getActivity()).getProfilePager().setCurrentItem(2);
          else
          if(strTemp.matches(getResources().getString(R.string.txtWali)))
            ((ProfileMain) getActivity()).getProfilePager().setCurrentItem(3);
        }
      break;
    }
  }

  private void PhotoProfile()
  {
    MenuBuilder menuBuilder = new MenuBuilder(getContext());
    new SupportMenuInflater(getContext()).inflate(R.menu.menu_popupprofile, menuBuilder);

    menuBuilder.setCallback(new MenuBuilder.Callback()
    {
      @Override
      public boolean onMenuItemSelected(MenuBuilder menu, MenuItem menuItem)
      {
        switch (menuItem.getItemId())
        {
          case R.id.mnuGallery:
            PhotoGallery();
          return true;
          case R.id.mnuKamera:
            if(!etNoHP.getText().toString().trim().matches(""))
            {
              fungsi.storeToSharedPref(getContext(), etNama.getText().toString().trim(), Preference.PrefProfNama);
              fungsi.storeToSharedPref(getContext(), etNoHP.getText().toString().trim(), Preference.PrefProfHP);
              fungsi.storeToSharedPref(getContext(), etEmailAddr.getText().toString().trim(), Preference.PrefProfEmail);
              fungsi.storeToSharedPref(getContext(), fungsi.CekRadioGroup(rgKomponen), Preference.PrefProfKomponen);
              fungsi.storeToSharedPref(getContext(), fungsi.idxRadioGroup(rgKomponen), Preference.PrefProfIdxKomponen);

              Intent SelfieIntent = new Intent(getActivity(), selfie.class);
              SelfieIntent.putExtra("FileGambar", etNoHP.getText().toString());
              startActivity(SelfieIntent);
              getActivity().finish();
            }
            else
              pesan.TampilPesan1(getContext(), getResources().getString(R.string.txtHPKosong));

            return true;
        }

        return false;
      }

      @Override
      public void onMenuModeChange(MenuBuilder menu) {
      }
    });

    MenuPopupHelper menuHelper = new MenuPopupHelper(getContext(), menuBuilder, ivPhotoProfile);
    menuHelper.setForceShowIcon(true); // show icons!!!!!!!!
    menuHelper.show();
  }

  @Override
  public void onResumeFragment()
  {
  }

  @Override
  public void onResumeMuridBaru()
  {

  }

  private void CekDataProfile()
  {
    String strCari = fungsi.getStringFromSharedPref(getContext(), Preference.PrefCariProfile);

    CariProfile.initCariProfile();
    CariProfile.getInstance().setCariProfile(strCari);
    CariProfile.getInstance().setKodeDevice(fungsi.DeviceInfo(getContext(), 0));
    CariProfile.getInstance().setParamID(1);

    if(fungsi.getIntFromSharedPref(getContext(), Preference.PrefPhotoSelfie) == 1)
    {
      File image = fungsi.FolderAplikasi();
      File FileProfile = new File(image.getAbsolutePath() + "/" + etNoHP.getText().toString() + ".jpg");

      if(FileProfile.exists())
        ivPhotoProfile.setImageBitmap(fungsi.BukaGambar(FileProfile));
      else
        ProsesData.ProsesCekProfile(getActivity(), getContext(), 1);
    }
    else
      ProsesData.ProsesCekProfile(getActivity(), getContext(), 1);
  }

  private void PhotoGallery()
  {
    Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
    intent.setType("image/*.jpg");
    startActivityForResult(Intent.createChooser(intent, getActivity().getResources().getString(R.string.txtPilihPhoto)), FixValue.PilihGambar);
  }

  public void onActivityResult(int requestCode, int resultCode, Intent data)
  {
    super.onActivityResult(requestCode, resultCode, data);
    if ((resultCode == getActivity().RESULT_OK) && (requestCode == FixValue.PilihGambar) && (data != null))
    {
      try
      {
        File image = fungsi.FolderAplikasi();
        String FileProfile = image.getAbsolutePath() + "/" + etNoHP.getText().toString() + ".jpg";
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
        ivPhotoProfile.setImageBitmap(fungsi.SimpanGambar(bitmap, FileProfile));
        fungsi.storeToSharedPref(getContext(), 1, Preference.PrefPhotoSelfie);
      }
      catch (IOException e)
      {
        e.printStackTrace();
      }
    }
    else if (resultCode == getActivity().RESULT_CANCELED)
      Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.txtBatalPhoto), Toast.LENGTH_SHORT).show();
  }
}
