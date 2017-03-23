package id.co.devoxlabs.ezschool.muridbaru;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import id.co.devoxlabs.ezschool.R;
import id.co.devoxlabs.ezschool.data.CariProfile;
import id.co.devoxlabs.ezschool.service.ProsesData;
import id.co.devoxlabs.ezschool.utils.FragLifecycle;
import id.co.devoxlabs.ezschool.utils.Preference;
import id.co.devoxlabs.ezschool.utils.fungsi;

/**
 * A simple {@link Fragment} subclass.
 */
public class fragMuridBaru extends Fragment implements FragLifecycle
{
  public fragMuridBaru()
  {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState)
  {
    View root = inflater.inflate(R.layout.lay_fragmuridbaru, container, false);

    CariProfile.initCariProfile();
    CariProfile.getInstance().setCariProfile(fungsi.getStringFromSharedPref(getContext(), Preference.PrefUserHP));
    CariProfile.getInstance().setKodeDevice(fungsi.DeviceInfo(getContext(), 0));
    CariProfile.getInstance().setParamID(2);

    ListView lvPSB = (ListView) root.findViewById(R.id.lvMuridBaru);

    lvPSB.setOnItemClickListener(new AdapterView.OnItemClickListener()
    {
      @Override
      public void onItemClick(AdapterView<?> adapter, View view, int position, long arg3)
      {
        ImageView ivTemp = (ImageView) view.findViewById(R.id.ivPhotoMurid);
        ((MuridBaru) getActivity()).setStrPhoto(ivTemp.getTag().toString());
        ((MuridBaru) getActivity()).getMuridBaruPager().setCurrentItem(2);
      }
    });

    ProsesData.ListDataPSB(lvPSB, getContext(), getActivity());
    return root;
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
