package com.diggytech.kinoplasticpremios.Settings.TermsConditions

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import com.diggytech.kinoplasticpremios.R

class TermsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_terms)
        setToolBar()


        val intent = intent
        val urlParam = intent.getStringExtra("web_url")

        val myWebView = findViewById<WebView>(R.id.webview)
        val webSettings = myWebView.settings
        webSettings.javaScriptEnabled = true
        myWebView.loadUrl(urlParam)
        val dialog = ProgressDialog(this@TermsActivity)
        myWebView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                dialog.setMessage("Loading Page...")
                dialog.show()

            }

            override fun shouldOverrideUrlLoading(webView: WebView?, url: String?): Boolean {
                val intent1 = Intent(Intent.ACTION_VIEW)
                intent1.data = Uri.parse(url)
                setResult(Activity.RESULT_OK, intent1)
                finish()
                return true

            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                dialog.dismiss()
            }
        }


    }

    private fun setToolBar() {
        val mToolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val mTitle = mToolbar.findViewById<TextView>(R.id.toolbar_title)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_home_white)
        mTitle!!.text = getString(R.string.terms_conditions)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}
