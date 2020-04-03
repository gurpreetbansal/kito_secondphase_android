package com.diggytech.kinoplasticpremios.RedeemPrizes.GiftCards

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.R
import com.diggytech.kinoplasticpremios.RedeemPrizes.ModelRedeemPrize
import kotlinx.android.synthetic.main.activity_gift_cards.*

class GiftCardsActivity : AppCompatActivity(), GiftcardContract.View {


    private lateinit var mPresenter: GiftCardPresenter
    var model = ModelRedeemPrize()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gift_cards)
        mPresenter = GiftCardPresenter(this)
        setToolBar()

        try {
            model = intent.getParcelableExtra("model")
            mPresenter.setData(model)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        btnRedeem.setOnClickListener {
            mPresenter.redeemPoints()
        }
    }

    private fun setToolBar() {
        val mToolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(mToolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        val mTitle = mToolbar.findViewById<TextView>(R.id.toolbar_title)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_home_white)
        mTitle!!.text = getString(R.string.gift_cards)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setGiftdata(model: ModelRedeemPrize) {
        ivImage.setImageURI(model.image)
        tvHeading.text = model.title
        tvTime.text = model.validity_period
        tvDesc.text = model.description
        tvPrice.text = model.price_range
    }


    override fun getVoucherId(): String {
        return model.id
    }

    override fun getRequestedPoints(): String {
        return etPoints.text.toString().trim()
    }

    override fun showError(message: String) {
        Constants.showAlert(this, message)
    }

    override fun getUserId(): String {
        return Constants.getPrefs(this)!!.getString(Constants.USER_ID, "")!!
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

    override fun showErrorMessage(message: String?) {
        Constants.showAlert(this, message!!)
    }

    override fun showSuccessMessage() {
        Constants.showFinishingAlert(this, getString(R.string.redeem_success_message))
    }
}
