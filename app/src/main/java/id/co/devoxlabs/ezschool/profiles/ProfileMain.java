package id.co.devoxlabs.ezschool.profiles;

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
import android.widget.TextView;
import id.co.devoxlabs.ezschool.R;
import id.co.devoxlabs.ezschool.Utama;
import id.co.devoxlabs.ezschool.utils.FragLifecycle;
import id.co.devoxlabs.ezschool.utils.PesanPopup;
import id.co.devoxlabs.ezschool.utils.Preference;

import java.util.Locale;

public class ProfileMain extends AppCompatActivity implements View.OnClickListener
{
  private ProgressDialog progressDialog;
  private PesanPopup pesan = new PesanPopup();
  private Context context = this;

  private String strNama;
  private String strHandphone;
  private String strEmail;
  private int idxKomponen;
  private String strKomponen;
  private String LoginID;

  private TextView tvHeader;

  SectionsProfile ProfilePagerAdapter;
  ViewPager mViewPager;

  public String getStrNama()
  {
    return strNama;
  }

  public void setStrNama(String strNama)
  {
    this.strNama = strNama;
  }

  public String getStrHandphone()
  {
    return strHandphone;
  }

  public void setStrHandphone(String strHandphone)
  {
    this.strHandphone = strHandphone;
  }

  public String getStrEmail()
  {
    return strEmail;
  }

  public void setStrEmail(String strEmail)
  {
    this.strEmail = strEmail;
  }

  public int getIdxKomponen()
  {
    return idxKomponen;
  }

  public void setIdxKomponen(int idxKomponen)
  {
    this.idxKomponen = idxKomponen;
  }

  public String getStrKomponen()
  {
    return strKomponen;
  }

  public void setStrKomponen(String strKomponen)
  {
    this.strKomponen = strKomponen;
  }

  public String getLoginID()
  {
    return LoginID;
  }

  public void setLoginID(String loginID)
  {
    LoginID = loginID;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.lay_profilemain);

    BindingView();
  }

  public void BindingView()
  {
    findViewById(R.id.ivBackIcon).setOnClickListener(this);

    tvHeader = (TextView) findViewById(R.id.tvHeader);
    tvHeader.setVisibility(View.VISIBLE);
    tvHeader.setText(getResources().getString(R.string.strProfile));

    ProfilePagerAdapter = new SectionsProfile(getSupportFragmentManager());
    mViewPager = (ViewPager) findViewById(R.id.pageProfile);
    mViewPager.setAdapter(ProfilePagerAdapter);
    mViewPager.setCurrentItem(0);

    mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
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
        FragLifecycle fragmentToShow = (FragLifecycle) ProfilePagerAdapter.instantiateItem(mViewPager, position);
        fragmentToShow.onResumeFragment();
      }
    });
  }

  @Override
  public void onClick(View view)
  {
    switch (view.getId())
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
    if (mViewPager.getCurrentItem() == 0)
    {
      Intent UtamaIntent = new Intent(ProfileMain.this, Utama.class);
      startActivity(UtamaIntent);
      finish();
    }
    else
      mViewPager.setCurrentItem(0);
  }

  public class SectionsProfile extends FragmentPagerAdapter
  {
    // END_INCLUDE (fragment_pager_adapter)

    public SectionsProfile(FragmentManager fm) {
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

      switch (position)
      {
        case 0 :
          fragment = isFragmentAdded(fragmentManager, Preference.fragProfileMain);
          if(fragment == null) fragment = new fragProfileMain();
        break;
        case 1 :
          fragment = isFragmentAdded(fragmentManager, Preference.fragProfileMurid);
          if(fragment == null) fragment = new fragProfileMurid();
        break;
        case 2 :
          fragment = isFragmentAdded(fragmentManager, Preference.fragProfileGuru);
          if(fragment == null) fragment = new fragProfileGuru();
        break;
        case 3 :
          fragment = isFragmentAdded(fragmentManager, Preference.fragProfileWali);
          if(fragment == null) fragment = new fragProfileWali();
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
      return 4;
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

      switch (position)
      {
        case 0:
          return getString(R.string.txtProfileMain).toUpperCase(l);
        case 1:
          return getString(R.string.txtProfileMurid).toUpperCase(l);
        case 2:
          return getString(R.string.txtProfileGuru).toUpperCase(l);
        case 3:
          return getString(R.string.txtProfileWali).toUpperCase(l);
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

  public ViewPager getProfilePager()
  {
    if (null == mViewPager)
      mViewPager = (ViewPager) findViewById(R.id.pageProfile);

    return mViewPager;
  }
}
