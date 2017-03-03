package id.co.devoxlabs.ezschool;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import id.co.devoxlabs.ezschool.profiles.ProfileMain;
import id.co.devoxlabs.ezschool.utils.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@SuppressWarnings("deprecation")
public class selfie extends AppCompatActivity implements View.OnClickListener, SurfaceHolder.Callback
{
	Camera camera; // camera class variable
	SurfaceHolder surfaceHolder; // variable to hold surface for surfaceView which means display
  boolean camCondition = false;  // conditional variable for camera preview checking and set to false
  boolean TakePicture = false;

  private Context context = this;
	private PesanPopup pesan = new PesanPopup();
	private ProgressDialog progressDialog;

	private ImageView ivTake;
	private ImageView ivReset;

	protected String FileProfile;
	protected String NamaFile;

  private SurfaceView svOCR;
	private int CameraID;
	private TextView tvHeader;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.lay_selfie);

		Intent intent = getIntent();
		Bundle bd = intent.getExtras();

		if(bd != null)
			NamaFile = bd.get("FileGambar").toString();

		File image = fungsi.FolderAplikasi();
		FileProfile = image.getAbsolutePath() + "/" + NamaFile + ".jpg";
		BindingView();
	}

	public void BindingView()
	{
		tvHeader = (TextView) findViewById(R.id.tvHeader);
		tvHeader.setVisibility(View.VISIBLE);
		tvHeader.setText(R.string.txtProfilePhoto);

		svOCR = (SurfaceView) findViewById(R.id.svOCR);
    getWindow().setFormat(PixelFormat.UNKNOWN);
    surfaceHolder = svOCR.getHolder();
    surfaceHolder.addCallback(this);

    ivTake = (ImageView) findViewById(R.id.ivTake);
    ivTake.setOnClickListener(this);
    ivTake.setImageResource(R.drawable.ic_camera);

		ivReset = (ImageView) findViewById(R.id.ivReset);
		ivReset.setOnClickListener(this);
		ivReset.setImageResource(R.drawable.ic_refresh_disable);
		ivReset.setEnabled(false);

    findViewById(R.id.ivBackIcon).setOnClickListener(this);
	}

  @Override
  public void onClick(View v)
  {
    switch (v.getId())
    {
			case R.id.ivTake:
				if(CekCamera())
				{
          ivReset.setEnabled(true);
          ivReset.setImageResource(R.drawable.ic_refresh_white);
          ivTake.setEnabled(false);
          ivTake.setImageResource(R.drawable.ic_camera_disable);

					camera.takePicture(null, null, null, mPictureCallback);
				}
				else
					pesan.TampilPesan1(context, getResources().getString(R.string.NoCamera));
			break;
      case R.id.ivReset:
        RefreshCamera();
      break;
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

  public void BackActivity()
  {
    Intent BuatAkunIntent = new Intent(selfie.this, ProfileMain.class);
    startActivity(BuatAkunIntent);
    finish();
  }

	private boolean CekCamera()
	{
		if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
			return true;
		else
			return false;
	}

  PictureCallback mPictureCallback = new PictureCallback()
  {
    public void onPictureTaken(byte[] data, Camera c)
    {
      FileOutputStream outStream = null;
      File picFile = new File(FileProfile);

      try
      {
        outStream = new FileOutputStream(picFile);
        outStream.write(data);
        outStream.close();
      }
      catch (FileNotFoundException e)
      {
				return;
      }
      catch (IOException e)
      {
				return;
      }

			fungsi.SetRotateImage(fungsi.BukaGambar(picFile), FileProfile);
      fungsi.storeToSharedPref(context, 1, Preference.PrefPhotoSelfie);
    }
  };

  private void RefreshCamera()
  {
    if(camCondition)
    {
      camera.stopPreview(); // stop preview using stopPreview() method
      camCondition = false; // setting camera condition to false means stop
    }
    // condition to check whether your device have camera or not
    if (camera != null)
    {
      try
      {
        camera.setPreviewDisplay(surfaceHolder); // setting preview of camera
        camera.startPreview();  // starting camera preview
        new AutoFocus(context, camera);

        camCondition = true; // setting camera to true which means having camera
        ivReset.setEnabled(false);
        ivReset.setImageResource(R.drawable.ic_refresh_disable);
        ivTake.setEnabled(true);
        ivTake.setImageResource(R.drawable.ic_camera);
      }
      catch (IOException e)
      {
        // TODO Auto-generated catch block
      }
    }
  }

	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		boolean boolCamera;

    try
    {
      CameraID = fungsi.getFacingCameraId(CameraInfo.CAMERA_FACING_FRONT);
      camera = Camera.open(CameraID);   // opening camera
			boolCamera = true;
    }
    catch(Exception ex)
    {
      boolCamera = false;
    }

    if(boolCamera == false)
		{
			try
			{
				CameraID = fungsi.getFacingCameraId(CameraInfo.CAMERA_FACING_BACK);
				camera = Camera.open(CameraID);   // opening camera
			}
			catch(Exception ex)
			{
				Toast.makeText(context, getResources().getString(R.string.NeedPermiss), Toast.LENGTH_LONG).show();
				return;
			}
		}

    TakePicture = false;
    camCondition = true;
    surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);
    surfaceHolder.setKeepScreenOn(true);
		Point bestPict = fungsi.getBestPictureSize(camera);
		fungsi.setCameraParameter(camera, bestPict.x, bestPict.y, "portrait");
    fungsi.setCameraDisplayOrientation(camera, context, CameraID);
    RefreshCamera();
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
		if(camera != null)
		{
			ivTake.setImageResource(R.drawable.ic_camera);

			Point bestPict = fungsi.getBestPictureSize(camera);
			fungsi.setCameraParameter(camera, bestPict.x, bestPict.y, "portrait");
			fungsi.setCameraDisplayOrientation(camera, context, CameraID);
			RefreshCamera();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		if(camera != null)
		{
      Log.d("", "");
			camera.stopPreview();  // stopping camera preview
			camera.release();       // releasing camera
			camera = null;          // setting camera to null when left
			camCondition = false;   // setting camera condition to false also when exit from application
		}
	}

	@Override
	protected void onPause()
	{
		super.onPause();

		if(camera != null)
		{
			camera.stopPreview();  // stopping camera preview
			camera.release();       // releasing camera
			camera = null;          // setting camera to null when left
			camCondition = false;   // setting camera condition to false also when exit from application
		}
	}
}
