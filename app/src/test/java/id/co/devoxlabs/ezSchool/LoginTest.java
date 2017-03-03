package id.co.devoxlabs.ezSchool;

import android.app.Activity;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;
import android.widget.LinearLayout;
import id.co.devoxlabs.ezschool.BuatAkun;
import id.co.devoxlabs.ezschool.Login;
import id.co.devoxlabs.ezschool.R;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Dibuat oleh : ignat
 * Tanggal : 25-Feb-17
 * HP/WA : 0857 7070 6 777
 */

public class LoginTest
{
  private EditText etNoHP;
  private EditText etPass;
  private EditText etVerPass;

  @Rule public final ActivityRule<BuatAkun> BuatAkunRule = new ActivityRule<>(BuatAkun.class);

  @Before
  public void setUp()
  {
    Activity activity = Robolectric.setupActivity(BuatAkun.class);
    etNoHP = (EditText) activity.findViewById(R.id.etNoHP);
    etPass = (EditText) activity.findViewById(R.id.etPass);
    etVerPass = (EditText) activity.findViewById(R.id.etVerPass);
  }

  @Test
  public void LaunchLoginMainScreen()
  {
    onView(withText("085770706777")).check(ViewAssertions.matches(isDisplayed()));
  }
}
