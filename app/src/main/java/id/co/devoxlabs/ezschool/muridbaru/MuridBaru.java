package id.co.devoxlabs.ezschool.muridbaru;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.co.devoxlabs.ezschool.R;
import id.co.devoxlabs.ezschool.Utama;
import id.co.devoxlabs.ezschool.utils.FragLifecycle;
import id.co.devoxlabs.ezschool.utils.PesanPopup;
import id.co.devoxlabs.ezschool.utils.Preference;

import java.util.Locale;

public class MuridBaru extends AppCompatActivity implements View.OnClickListener
{
  @BindView(R.id.tvHeader) TextView tvHeader;
  @BindView(R.id.ivNextIcon) ImageView ivNextIcon;

  SectionsMuridBaru paMuridBaru;
  ViewPager vpMuridBaru;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.lay_muridbaru);
    ButterKnife.bind(this);

    BindingView();
  }

  public void BindingView()
  {
    tvHeader.setVisibility(View.VISIBLE);
    tvHeader.setText(getResources().getString(R.string.txtHeadMuridBaru));

    ivNextIcon.setImageResource(R.drawable.addmuridbaru);
    ivNextIcon.setVisibility(View.VISIBLE);

    paMuridBaru = new SectionsMuridBaru(getSupportFragmentManager());
    vpMuridBaru = (ViewPager) findViewById(R.id.cvpMuridBaru);
    vpMuridBaru.setAdapter(paMuridBaru);
    vpMuridBaru.setCurrentItem(0);

    vpMuridBaru.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
    {
      @Override
      public void onPageScrollStateChanged(int state)
      {
      }

      @Override
      public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
      {
      }

      @Override
      public void onPageSelected(int position)
      {
        FragLifecycle fragmentToShow = (FragLifecycle) paMuridBaru.instantiateItem(vpMuridBaru, position);
        fragmentToShow.onResumeMuridBaru();
      }
    });
  }

  @OnClick(R.id.ivBackIcon)
  public void onClick(View view)
  {
    switch(view.getId())
    {
      case R.id.ivBackIcon:
        BackActivity();
      break;
    }
  }

  @Override
  public void onBackPressed()
  {
    BackActivity();
  }

  private void BackActivity()
  {
    Intent MuridBaruIntent = new Intent(MuridBaru.this, Utama.class);
    startActivity(MuridBaruIntent);
    finish();
  }

  public class SectionsMuridBaru extends FragmentPagerAdapter
  {
    // END_INCLUDE (fragment_pager_adapter)

    public SectionsMuridBaru(FragmentManager fm)
    {
      super(fm);
    }

    // BEGIN_INCLUDE (fragment_pager_adapter_getitem)

    /**
     * Get fragment corresponding to a specific position. This will be used to populate the
     * contents of the {@link ViewPager}.
     *
     * @param position Position to fetch fragment for.
     * @return Fragment for specified position.
     */
    @Override
    public Fragment getItem(int position)
    {
      // getItem is called to instantiate the fragment for the given page.
      // Return a DummySectionFragment (defined as a static inner class
      // below) with the page number as its lone argument.
      Fragment fragment = new Fragment();
      FragmentManager fragmentManager = getSupportFragmentManager();

      switch(position)
      {
        case 0:
          fragment = isFragmentAdded(fragmentManager, Preference.PrefListMuridBaru);
          if(fragment == null) fragment = new fragMuridBaru();
          break;
        case 1:
          fragment = isFragmentAdded(fragmentManager, Preference.PrefDataWali);
          if(fragment == null) fragment = new fragDataWali();
          break;
        case 2:
          fragment = isFragmentAdded(fragmentManager, Preference.PrefDataMurid);
          if(fragment == null) fragment = new fragDataMurid();
          break;
      }

      return fragment;
    }
    // END_INCLUDE (fragment_pager_adapter_getitem)

    // BEGIN_INCLUDE (fragment_pager_adapter_getcount)

    /**
     * Get number of pages the {@link ViewPager} should render.
     *
     * @return Number of fragments to be rendered as pages.
     */
    @Override
    public int getCount()
    {
      return 3;
    }
    // END_INCLUDE (fragment_pager_adapter_getcount)

    // BEGIN_INCLUDE (fragment_pager_adapter_getpagetitle)

    /**
     * Get title for each of the pages. This will be displayed on each of the tabs.
     *
     * @param position Page to fetch title for.
     * @return Title for specified page.
     */
    @Override
    public CharSequence getPageTitle(int position)
    {
      Locale l = Locale.getDefault();

      switch(position)
      {
        case 0:
          return getString(R.string.txtHeadMuridBaru).toUpperCase(l);
        case 1:
          return getString(R.string.txtDataWali).toUpperCase(l);
        case 2:
          return getString(R.string.txtDataMurid).toUpperCase(l);
      }

      return null;
    }

    // END_INCLUDE (fragment_pager_adapter_getpagetitle)
  }

  private Fragment isFragmentAdded(FragmentManager fragmentManager, String tag)
  {
    Fragment f = fragmentManager.findFragmentByTag(tag);

    if(f == null)
      return null;
    else
      return f;
  }

  public ViewPager getMuridBaruPager()
  {
    if(null == vpMuridBaru)
      vpMuridBaru = (ViewPager) findViewById(R.id.cvpMuridBaru);

    return vpMuridBaru;
  }
}
