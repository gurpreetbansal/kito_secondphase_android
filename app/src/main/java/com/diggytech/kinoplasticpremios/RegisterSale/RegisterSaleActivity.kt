package com.diggytech.kinoplasticpremios.RegisterSale

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.R
import com.google.zxing.Result
import kotlinx.android.synthetic.main.activity_register_sale.*
import me.dm7.barcodescanner.zxing.ZXingScannerView


class RegisterSaleActivity : AppCompatActivity(), ZXingScannerView.ResultHandler, RegisterSaleContract.View {
    private lateinit var mPresenter: RegisterSalePresenter
    private lateinit var mScannerView: ZXingScannerView
    var QRCode = ""
    var campaignID = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_sale)
        setToolBar()

        mPresenter = RegisterSalePresenter(this)

        val contentFrame = findViewById<FrameLayout>(R.id.content_frame)
        mScannerView = ZXingScannerView(this)
        contentFrame.addView(mScannerView)
        // this paramter will make your HUAWEI phone work great!
        mScannerView.setAspectTolerance(0.5f)
        try {
            campaignID = intent.getStringExtra("id")
        } catch (e: Exception) {

        }

        btnSubmit.setOnClickListener {
            QRCode = etWriteManually.text.toString().trim()
            mPresenter.setRegisterSale()
        }

    }

    private fun setToolBar() {
        val mToolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val mTitle = mToolbar.findViewById<TextView>(R.id.toolbar_title)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_home_white)
        mTitle!!.text = getString(R.string.regster_sale)
    }

    public override fun onResume() {
        super.onResume()
        mScannerView.setResultHandler(this)
        mScannerView.startCamera()
    }

    public override fun onPause() {
        super.onPause()
        mScannerView.stopCamera()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun handleResult(rawResult: Result) {
        /* Toast.makeText(
             this, "Contents = " + rawResult.text +
                     ", Format = " + rawResult.barcodeFormat.toString(), Toast.LENGTH_SHORT
         ).show()*/

        // Note:
      /*  Toast.makeText(
            this, "Format = " + rawResult.barcodeFormat.toString(), Toast.LENGTH_SHORT
        ).show()*/
        QRCode = rawResult.text
        QRCode = QRCode.replace("\"", "")
        mPresenter.setRegisterSale()
       // Constants.showAlert(this, "Code scanned successfully")

        // Note:
        // * Wait 2 seconds to resume the preview.
        // * On older devices continuously stopping and resuming camera preview can result in freezing the app.
        // * I don't know why this is the case but I don't have the time to figure out.
        val handler = Handler()
        handler.postDelayed(Runnable { mScannerView.resumeCameraPreview(this@RegisterSaleActivity) }, 2000)
    }

    override fun getUserId(): String {
        return Constants.getPrefs(this)!!.getString(Constants.USER_ID, "")!!
    }

    override fun getAddress(): String {
        return Constants.getPrefs(this)!!.getString(Constants.ADDRESS, "")!!
    }

    override fun getAuthToken(): String {
        return Constants.getPrefs(this)!!.getString(Constants.TOKEN, "")!!
    }

    override fun showLoader() {
        progressBar.visibility = View.VISIBLE
        Constants.setNonTouchableFlags(this)
    }

    override fun hideLoader() {
        progressBar.visibility = View.GONE
        Constants.clearNonTouchableFlags(this)
    }

    override fun showError(message: String) {
        Constants.showAlert(this, message)
    }

    override fun showErrorMessage(message: String?) {
        Constants.showAlert(this, message!!)
    }


    override fun getQrCode(): String {
        return QRCode
    }

    override fun getCampaignId(): String {
        return campaignID
    }

    override fun showSuccessMessage(message: String?) {
        Constants.showFinishingAlert(this, message!!)
    }
}
