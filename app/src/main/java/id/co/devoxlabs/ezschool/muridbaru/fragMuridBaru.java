package id.co.devoxlabs.ezschool.muridbaru;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.co.devoxlabs.ezschool.R;
import id.co.devoxlabs.ezschool.utils.FragLifecycle;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragMuridBaru extends Fragment implements View.OnClickListener, FragLifecycle
{


  public fragMuridBaru()
  {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState)
  {
    // Inflate the layout for this fragment
    return inflater.inflate(R.layout.lay_fragmuridbaru, container, false);
  }

  @Override
  public void onClick(View view)
  {

  }

  @Override
  public void onResumeFragment()
  {

  }

  @Override
  public void onResumeMuridBaru()
  {

  }
}
