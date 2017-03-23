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

package id.co.devoxlabs.ezschool.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import id.co.devoxlabs.ezschool.R;
import id.co.devoxlabs.ezschool.model.MuridResponse;

import java.util.List;

/**
 * Dibuat oleh : ignat
 * Tanggal : 25-Nov-16
 * HP/WA : 0857 7070 6 777
 */
public class DataPSBAdapter extends BaseAdapter
{
  private Context mContext;
  private List<MuridResponse> lstDataPSB;

  public DataPSBAdapter(Context mContext, List<MuridResponse> lstDataPSB)
  {
    this.mContext = mContext;
    this.lstDataPSB = lstDataPSB;
  }

  @Override
  public int getCount()
  {
    return lstDataPSB.size();
  }

  @Override
  public Object getItem(int i)
  {
    return lstDataPSB.get(i);
  }

  @Override
  public long getItemId(int i)
  {
    return 0;
  }

  @Override
  public View getView(int i, View view, ViewGroup viewGroup)
  {
    if(view == null)
    {
      LayoutInflater lInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
      view = lInflater.inflate(R.layout.lay_muridbarudetail, null);
    }

    ViewHolder vhView = new ViewHolder(view);
    vhView.tvNamaMurid.setText(mContext.getResources().getString(R.string.HintNama) + " " + lstDataPSB.get(i).getNAMASISWA());
    vhView.tvTglDaftar.setText(mContext.getResources().getString(R.string.HintTglDaftar) + " " + lstDataPSB.get(i).getTglDaftar());
    vhView.tvSeksMurid.setText(mContext.getResources().getString(R.string.HintSeks) + " " + lstDataPSB.get(i).getJENISKELAMIN());
    vhView.tvTglLahirMurid.setText(mContext.getResources().getString(R.string.HintTglLahir) + " " + lstDataPSB.get(i).getTGLLAHIR());
    vhView.tvKelas.setText(mContext.getResources().getString(R.string.HintKelas) + " " + lstDataPSB.get(i).getKelas());
    vhView.tvStatusMurid.setText(mContext.getResources().getString(R.string.HintStatus) + " " + lstDataPSB.get(i).getStatusPSB());

    vhView.ivPhotoMurid.setTag(lstDataPSB.get(i).getPhoto());
    Picasso.with(mContext).load(lstDataPSB.get(i).getPhotoURL()).networkPolicy(NetworkPolicy.NO_CACHE)
      .memoryPolicy(MemoryPolicy.NO_CACHE)
      .placeholder(R.drawable.muridbaru)
      .fit()
      .rotate(90f)
      .into(vhView.ivPhotoMurid);

    return view;
  }

  static class ViewHolder
  {
    @BindView(R.id.ivPhotoMurid) ImageView ivPhotoMurid;
    @BindView(R.id.tvNamaMurid) TextView tvNamaMurid;
    @BindView(R.id.tvTglLahirMurid) TextView tvTglLahirMurid;
    @BindView(R.id.tvSeksMurid) TextView tvSeksMurid;
    @BindView(R.id.tvKelas) TextView tvKelas;
    @BindView(R.id.tvTglDaftar) TextView tvTglDaftar;
    @BindView(R.id.tvStatusMurid) TextView tvStatusMurid;

    ViewHolder(View view)
    {
      ButterKnife.bind(this, view);
    }
  }
}
