package com.diggytech.kinoplasticpremios;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.diggytech.kinoplasticpremios.Campaign.ProgressDialogClass;
import com.diggytech.kinoplasticpremios.MySpace.MySpaceContract;
import com.google.android.gms.common.api.Api;

public class StartMissionActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;
    ProgressDialog progressDialog;
    private ImageView img_back_terms;
    private Api apiInterface;
    private TextView toolbar_title;
    private ImageView backImage;

    boolean check;

    String url = "https://joootvio1.typeform.com/to/q26dMO";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_start_mission);

//        getWindow().setFeatureInt(Window.FEATURE_PROGRESS, Window.PROGRESS_VISIBILITY_ON);

        webView = findViewById(R.id.startMission_webView);
        toolbar_title=findViewById(R.id.toolbar_title);
        backImage=findViewById(R.id.startMission_backImage);

        toolbar_title.setText(getString(R.string.questionnaire));

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        showWebPage();

    }

    private void showWebPage(){

        progressDialog = new ProgressDialog(StartMissionActivity.this);
        progressDialog.setMessage("Loading website........");
        progressDialog.setCanceledOnTouchOutside(false);
        webView.setWebViewClient(new ProgressDialogClass(progressDialog,getApplicationContext()));
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);
        progressDialog.dismiss();


    }

    public void showDialog() {

        if(progressDialog != null && !progressDialog.isShowing())
            progressDialog.show();
    }

    public void hideDialog() {

        if(progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        hideDialog();
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideDialog();
    }
}