package com.diggytech.kinoplasticpremios;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.diggytech.kinoplasticpremios.Campaign.ProgressDialogClass;
import com.google.android.gms.common.api.Api;

public class StartMissionActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar progressBar;
    ProgressDialog progressDialog;
    private ImageView img_back_terms;
    private Api apiInterface;
    private TextView toolbar_title;
    private ImageView backImage;

    boolean clicked;

    //String url = "https://joootvio1.typeform.com/to/q26dMO";
    String url = "";
    private String user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_mission);

        //user_id = Constants.getPrefs(this)!!.getString(Constants.USER_ID, "")


        try {
            url = getIntent().getStringExtra("form_source");
            user_id = getIntent().getStringExtra("user_id");
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!url.contains("?user_id=")) {
            url = url + "?user_id=" + user_id;
        } else {
            String separator = "?";
            int sepPos = url.lastIndexOf(separator);
            url = url.substring(0, sepPos);
            url = url + "?user_id=" + user_id;
        }


        webView = findViewById(R.id.startMission_webView);
        toolbar_title = findViewById(R.id.toolbar_title);
        backImage = findViewById(R.id.startMission_backImage);

        toolbar_title.setText(getString(R.string.app_name));

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        showWebPage();


        progressDialog = new ProgressDialog(StartMissionActivity.this);
        progressDialog.setMessage("Loading Page........");
        progressDialog.setCanceledOnTouchOutside(false);
        webView.setWebViewClient(new ProgressDialogClass(progressDialog, getApplicationContext()));
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.loadUrl(url);


    }

//    private void showWebPage(){
//
//        progressDialog = new ProgressDialog(StartMissionActivity.this);
//        progressDialog.setMessage("Loading website........");
//        progressDialog.setCanceledOnTouchOutside(false);
//        webView.setWebViewClient(new ProgressDialogClass(progressDialog,getApplicationContext()));
//        webView.getSettings().setLoadsImagesAutomatically(true);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        webView.loadUrl(url);
//        progressDialog.dismiss();
//
//
//    }

    public void showDialog() {

        if (progressDialog != null && !progressDialog.isShowing())
            progressDialog.show();
    }

    public void hideDialog() {

        if (progressDialog != null && progressDialog.isShowing())
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
