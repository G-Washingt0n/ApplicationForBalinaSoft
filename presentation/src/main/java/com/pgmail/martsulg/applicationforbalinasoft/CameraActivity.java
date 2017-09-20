package com.pgmail.martsulg.applicationforbalinasoft;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.pgmail.martsulg.domain.entity.PutPictureModel;
import com.pgmail.martsulg.domain.entity.ResponsePutPictureModel;
import com.pgmail.martsulg.domain.interactions.PutPictureUseCase;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by lenovo on 17.09.2017.
 */

public class CameraActivity extends Activity implements SurfaceHolder.Callback, View.OnClickListener, Camera.PictureCallback {

    private Camera camera;
    private SurfaceHolder surfaceHolder;
    private SurfaceView preview;
    private Button shotButton;
    LocationManager locationManager;
    public double lat = 53.55;
    public double lng = 27.33;
    public long value;
    public String str = "";

    public PutPictureUseCase putPictureUseCase = new PutPictureUseCase();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);

        preview = (SurfaceView) findViewById(R.id.cameraSurfaceView);

        surfaceHolder = preview.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        shotButton = (Button) findViewById(R.id.cameraShotButton);
        shotButton.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        camera = Camera.open();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (camera != null) {
            camera.setPreviewCallback(null);
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Camera.Parameters parameters = camera.getParameters();

        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.setPreviewCallback((Camera.PreviewCallback) this);

        } catch (Exception e) {
            e.printStackTrace();
        }


        Calendar calendar = new GregorianCalendar();
        value = Long.valueOf(calendar.getTimeInMillis() / 1000);
        Log.e("Current time: ", String.valueOf(value));

        /* parameters.removeGpsData();
        parameters.setGpsTimestamp(System.currentTimeMillis() / 1000);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        lat = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLatitude();
        lon = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER).getLongitude();
*/
        Camera.Size previewSize = parameters.getPreviewSize();
        Camera.Size pictureSize = getSmallestPictureSize(parameters);

        if (previewSize != null && pictureSize != null) {
            parameters.setPreviewSize(previewSize.width, previewSize.height);
            parameters.setPictureSize(pictureSize.width,
                    pictureSize.height);
            parameters.setPictureFormat(ImageFormat.JPEG);
            camera.setParameters(parameters);
        }
        float aspect = (float) previewSize.width / previewSize.height;

        int previewSurfaceWidth = preview.getWidth();
        int previewSurfaceHeight = preview.getHeight();

        ViewGroup.LayoutParams lp = preview.getLayoutParams();

        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            camera.setDisplayOrientation(90);
            lp.height = previewSurfaceHeight;
            lp.width = (int) (previewSurfaceHeight / aspect);
            ;
        } else {
            camera.setDisplayOrientation(0);
            lp.width = previewSurfaceWidth;
            lp.height = (int) (previewSurfaceWidth / aspect);
        }

        preview.setLayoutParams(lp);
        camera.startPreview();


    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void onClick(View view) {
        if (view == shotButton)
            camera.takePicture(null, null, null, this);
    }

    @Override
    public void onPictureTaken(byte[] bytes, Camera camera) {
        try {
            Log.e("Picture Lat:", String.valueOf(lat));
            Log.e("Picture Long:", String.valueOf(lng));
            str = new String(bytes, "UTF-8");
            CameraViewModel viewModel = new CameraViewModel(str, value, lat, lng);

            File saveDir = new File("/sdcard/CameraExample/");

            if (!saveDir.exists()) {
                saveDir.mkdirs();
            }

            FileOutputStream os = new FileOutputStream(String.format("/sdcard/CameraExample/%d.jpg", System.currentTimeMillis()));
            os.write(bytes);
            os.close();
        } catch (Exception e) {
            Log.e("ОШИБКИ!!!", "не вышло");
        }

        camera.startPreview();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        letsSendPicture(str, value, lat, lng);

        Intent intent = new Intent(CameraActivity.this, ContentActivity.class);
        startActivity(intent);
    }

    private Camera.Size getSmallestPictureSize(Camera.Parameters parameters) {
        Camera.Size result = null;

        for (Camera.Size size : parameters.getSupportedPictureSizes()) {
            if (result == null) {
                result = size;
            } else {
                int resultArea = result.width * result.height;
                int newArea = size.width * size.height;

                if (newArea < resultArea) {
                    result = size;
                }
            }
        }

        return (result);
    }



    public void letsSendPicture(final String imageStr, final long dat, final double latt, final double lngt) {

         final String imageString=imageStr;
        final long date = dat;
        final double lat = latt;
        final double lng = lngt;

        /*Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {*/

        Log.e("Otpravliaem date: ", String.valueOf(date));
        Log.e("Otpravliaem: lat: ", String.valueOf(lat));
        Log.e("Otpravliaem: token: ", String.valueOf(lat));

        PutPictureModel putPictureModel = new PutPictureModel();
        putPictureModel.setImageString(imageString);
        putPictureModel.setDate(date);
        putPictureModel.setLat(lat);
        putPictureModel.setLng(lng);
        putPictureModel.setTOKEN(EntryActivity.preferences.getString("Token",null));

        Log.e("Otpravliaem: token: ", putPictureModel.getTOKEN());
        putPictureUseCase.execute(putPictureModel, new DisposableObserver<ResponsePutPictureModel>() {
            @Override
            public void onNext(@NonNull ResponsePutPictureModel responsePutPictureModel) {

                long receiveDate = responsePutPictureModel.getDate();
                int receiveId = responsePutPictureModel.getId();
                double receiveLat = responsePutPictureModel.getLat();
                double receiveLng = responsePutPictureModel.getLng();
                String receiveUrl = responsePutPictureModel.getUrl();


                Log.e("receiveDate: ", String.valueOf(receiveDate));
                Log.e("receiveId: ", String.valueOf(receiveId));
                Log.e("receiveLat: ", String.valueOf(receiveLat));
                Log.e("receiveLng: ", String.valueOf(receiveLng));
                Log.e("receiveUrl: ", receiveUrl);

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


          /*  }
        });*/

    }
}
