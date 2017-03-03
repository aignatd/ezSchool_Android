package id.co.devoxlabs.ezschool;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;
import id.co.devoxlabs.ezschool.popup.InputManual;
import id.co.devoxlabs.ezschool.utils.CameraManager;

import net.sourceforge.zbar.Config;
import net.sourceforge.zbar.Image;
import net.sourceforge.zbar.ImageScanner;
import net.sourceforge.zbar.Symbol;
import net.sourceforge.zbar.SymbolSet;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class AbsenQR extends AppCompatActivity implements View.OnClickListener, SurfaceHolder.Callback
{
  private SurfaceView cameraPreview;
  private CameraManager cameraManager;
  private TextView txt_flash;

  private ImageScanner mScanner;
  private boolean hasSurface;
  private boolean flashOn = false;
  private boolean isInitCameraProcess = false;

  @Override
  protected void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.lay_absenqr);

    BindingView();
  }

  private void BindingView()
  {
    findViewById(R.id.tvManual).setOnClickListener(this);
    findViewById(R.id.btn_flash).setOnClickListener(this);
    findViewById(R.id.btn_close).setOnClickListener(this);

    txt_flash = (TextView) findViewById(R.id.txt_flash);
    cameraPreview = (SurfaceView) findViewById(R.id.cameraPreview);

    cameraManager = new CameraManager(this, barcodeCallback);
    hasSurface = false;
  }

  @Override
  public void onClick(View view)
  {
    switch (view.getId())
    {
      case R.id.tvManual:
        InputManual cdManual = new InputManual(AbsenQR.this);
        cdManual.show();
      break;
      case R.id.btn_flash:
        if(hasFlash())
          switchFlashlight();
      break;
      case R.id.btn_close:
        BackActivity();
      break;
    }
  }

  private boolean hasFlash()
  {
    return getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
  }

  private void switchFlashlight()
  {
    if (!flashOn)
    {
      cameraManager.setTorch(true);
      txt_flash.setText(getString(R.string.strFlashOn));
    }
    else
    {
      cameraManager.setTorch(false);
      txt_flash.setText(getString(R.string.strFlashOff));
    }

    flashOn = !flashOn;
  }

  private Camera.PreviewCallback barcodeCallback = new Camera.PreviewCallback()
  {
    public void onPreviewFrame(byte[] data, Camera camera)
    {
      Camera.Parameters parameters = camera.getParameters();
      Camera.Size size = parameters.getPreviewSize();

      Image barcode = new Image(size.width, size.height, "Y800");
      barcode.setData(data);

      if (mScanner == null) {
        return;
      }

      int result = mScanner.scanImage(barcode);

      if (result != 0)
      {
        onPause();
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(200);

        String barcodeText = "";
        SymbolSet symbols = mScanner.getResults();
        for (Symbol sym : symbols)
        {
          barcodeText = sym.getData();
        }

        Log.d("[AbsenQR]", "Hasil QR -> " + barcodeText);
      }
    }
  };

  @Override
  protected void onResume()
  {
    super.onResume();
    isInitCameraProcess = true;
    Timer timer = new Timer();
    timer.schedule(new TimerTask()
    {
      @Override
      public void run()
      {
        runOnUiThread(new Runnable()
        {
          @Override
          public void run()
          {
            SurfaceHolder surfaceHolder = cameraPreview.getHolder();
            if (hasSurface)
            {
              // The activity was paused but not stopped, so the surface still exists.
              // Therefore surfaceCreated() won't be called, so init the camera here.
              initCamera(cameraPreview, true);
            }
            else
            {
              // Install the callback and wait for surfaceCreated() to init the camera.
              surfaceHolder.addCallback(AbsenQR.this);
              surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
              initCamera(cameraPreview, true);
            }
          }
        });
      }
    }, 500);

  }

  @Override
  protected void onPause(){
    super.onPause();
    try
    {
      cameraManager.stopPreview();
      cameraManager.closeDriver();

      if (!hasSurface)
      {
        SurfaceHolder surfaceHolder = cameraPreview.getHolder();
        surfaceHolder.removeCallback(this);
      }
    }
    catch (RuntimeException e)
    {
      // Can be already released
    }
  }

  @Override
  protected void onDestroy()
  {
    super.onDestroy();
    cleanCameraInstance();
  }

  @Override
  public void surfaceCreated(SurfaceHolder surfaceHolder)
  {
    if (!hasSurface) hasSurface = true;
  }

  @Override
  public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2)
  {

  }

  @Override
  public void surfaceDestroyed(SurfaceHolder surfaceHolder)
  {
    hasSurface = false;
  }

  private void cleanCameraInstance()
  {
    if (cameraManager.isOpen())
    {
      cameraManager.stopPreview();
      cameraManager.closeDriver();
      if (!hasSurface)
      {
        SurfaceHolder surfaceHolder = cameraPreview.getHolder();
        surfaceHolder.removeCallback(this);
      }
    }
  }

  private void initCamera(SurfaceView surfaceView, boolean oneTime) {
    if (surfaceView == null || surfaceView.getHolder() == null)
      throw new IllegalStateException("No SurfaceHolder provided");

    if (cameraManager.isOpen()) return;

    try
    {
      surfaceView.setBackgroundColor(getResources().getColor(android.R.color.transparent));

      cameraManager.openDriver(surfaceView);
      // Creating the handler starts the preview, which can also throw a
      // RuntimeException.
      cameraManager.startPreview();
    }
    catch (IOException ioe)
    {
      ioe.printStackTrace();
    }
    catch (RuntimeException e)
    {
      if(null != cameraManager) cameraManager.closeDriver();

      if (oneTime) initCamera(surfaceView, false);
      else return;
    }

    mScanner = new ImageScanner();
    mScanner.setConfig(Symbol.NONE, Config.ENABLE, 0);
    mScanner.setConfig(Symbol.QRCODE, Config.ENABLE, 1);
    mScanner.setConfig(Symbol.NONE, Config.X_DENSITY, 3);
    mScanner.setConfig(Symbol.NONE, Config.Y_DENSITY, 3);

    surfaceView.setBackgroundColor(Color.TRANSPARENT);
    isInitCameraProcess = false;
  }

  private void BackActivity()
  {
    Intent UtamaIntent = new Intent(AbsenQR.this, Utama.class);
    startActivity(UtamaIntent);
    finish();
  }

  @Override
  public void onBackPressed()
  {
    BackActivity();
  }
}
