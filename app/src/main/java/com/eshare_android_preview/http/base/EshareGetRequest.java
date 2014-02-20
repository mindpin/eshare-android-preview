package com.eshare_android_preview.http.base;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;

public abstract class EshareGetRequest<TResult> extends EshareHttpRequest<TResult> {
    public EshareGetRequest(final String request_path, final NameValuePair... nv_pairs) {
        String request_url = EshareHttpRequest.SITE + request_path + build_params_string(nv_pairs);
        this.http_uri_request = new HttpGet(request_url);
        this.http_uri_request.setHeader("User-Agent", "android");
    }
}
