package com.eshare_android_preview.base.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;

public class CameraLogic {
	  public final static File IMAGE_CAPTURE_TEMP_FILE = new File(FileDirs.MX_CAPTURE_TEMP_DIR, "IMG_TEMP.jpg");
      public final static String HAS_IMAGE_CAPTURE = "has_image_capture";
	  // 调用系统的照相机
	  public static void call_system_camera(Activity activity,int requestCode) {
	    Intent intent = get_photo_capture_intent();
	    Uri uri = Uri.fromFile(IMAGE_CAPTURE_TEMP_FILE);
	    System.out.println(IMAGE_CAPTURE_TEMP_FILE.getPath());
	    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri); 	 	
	    activity.startActivityForResult(intent, requestCode);
	  }
	  public static Intent get_photo_capture_intent() {
	    return new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	  }

}
