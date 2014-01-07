package com.eshare_android_preview.base.http;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.mime.MultipartEntity;

public abstract class EsharePutRequest<TResult> extends EshareHttpRequest<TResult> {
  
  public EsharePutRequest(final String request_path, final HttpParam... param) {
    HttpEntity entity = build_entity(param);
    this.http_uri_request = build_http_put(entity, request_path);
  }

  private HttpEntity build_entity(HttpParam... param) {
      MultipartEntity entity = new MultipartEntity();
      for (HttpParam param_file : param) {
          entity.addPart(param_file.get_name(), param_file.get_body());
      }
      return entity;
  }

  private HttpPut build_http_put(HttpEntity entity, String request_path) {
      HttpPut http_post = new HttpPut(EshareHttpRequest.SITE + request_path);
      http_post.setHeader("User-Agent", "android");
      http_post.setEntity(entity);
      return http_post;
  }
}
