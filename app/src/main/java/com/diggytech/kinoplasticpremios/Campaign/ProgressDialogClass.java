package com.diggytech.kinoplasticpremios.Campaign;

import android.app.ProgressDialog;
import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class ProgressDialogClass extends WebViewClient {

    ProgressDialog progressDialog;
    Context context;

    public ProgressDialogClass(ProgressDialog progressDialog, Context context){
        this.progressDialog=progressDialog;
        this.context=context;
        progressDialog.show();
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }

    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        Toast.makeText(context, "Error:" + description, Toast.LENGTH_SHORT).show();
    }
}
