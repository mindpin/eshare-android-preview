package com.eshare_android_preview.http.api;

import com.eshare_android_preview.http.base.EshareHttpRequest;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import java.io.InputStream;

/**
 * Created by Administrator on 14-1-7.
 */
public class BaseHttpApi {
    public static InputStream download_image(String image_url) {
        try {
          HttpGet httpget = new HttpGet(image_url);
          HttpResponse response = EshareHttpRequest.get_httpclient_instance().execute(httpget);
          int status_code = response.getStatusLine().getStatusCode();
          if (HttpStatus.SC_OK == status_code) {
            return response.getEntity().getContent();
          } else {
            return null;
          }
        } catch (Exception e) {
          e.printStackTrace();
          return null;
        }
      }
}
