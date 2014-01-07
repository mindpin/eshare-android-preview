package com.eshare_android_preview.base.http;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpDelete;

public abstract class EshareDeleteRequest<TResult> extends EshareHttpRequest<TResult> {
    public EshareDeleteRequest(final String request_path, final NameValuePair... nv_pairs) {
        String request_url = EshareHttpRequest.SITE + request_path + build_params_string(nv_pairs);
        this.http_uri_request = new HttpDelete(request_url);
        this.http_uri_request.setHeader("User-Agent", "android");
    }
}
