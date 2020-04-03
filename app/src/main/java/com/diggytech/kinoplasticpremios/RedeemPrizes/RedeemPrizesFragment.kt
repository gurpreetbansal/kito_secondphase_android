package com.diggytech.kinoplasticpremios.RedeemPrizes


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.R
import kotlinx.android.synthetic.main.fragment_redeem_prizes.view.*


class RedeemPrizesFragment : Fragment(), RedeemPrizesContract.View {
    private lateinit var mPresenter: RedeemPrizesPresenter
    private lateinit var recyclerview: androidx.recyclerview.widget.RecyclerView
    lateinit var v: View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_redeem_prizes, container, false)
        val mToolbar = v.findViewById<Toolbar>(R.id.toolbar)
        val mTitle = mToolbar.findViewById<TextView>(R.id.toolbar_title)
        mTitle!!.text = getString(R.string.redeem_prizes)

        recyclerview = v.findViewById(R.id.recyclerview)
        mPresenter = RedeemPrizesPresenter(this)
        mPresenter.callgetVouchersApi()
        return v
    }

    override fun setAdapter(list: MutableList<ModelRedeemPrize>) {
        if (activity != null && isAdded) {
            recyclerview.layoutManager = androidx.recyclerview.widget.GridLayoutManager(
                activity,
                2,
                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                false
            )
            val adapter = RedeemPrizesAdapter(activity!!, list)
            recyclerview.adapter = adapter
        }
    }

    override fun getUserId(): String {
        if (activity != null && isAdded) {
            return Constants.getPrefs(activity!!)!!.getString(Constants.USER_ID, "")!!
        }
        return ""
    }

    override fun getAuthToken(): String {
        if (activity != null && isAdded) {
            return Constants.getPrefs(activity!!)!!.getString(Constants.TOKEN, "")!!
        }
        return ""
    }

    override fun showLoader() {
        if (activity != null && isAdded) {
            v.progressBar.visibility = View.VISIBLE
            Constants.setNonTouchableFlags(activity!!)
        }
    }

    override fun hideLoader() {
        if (activity != null && isAdded) {
            v.progressBar.visibility = View.GONE
            Constants.clearNonTouchableFlags(activity!!)
        }
    }

    override fun showError(message: String) {
        if (activity != null && isAdded) {
            Constants.showAlert(activity!!, message)
        }
    }

    override fun showErrorMessage(message: String?) {
        if (activity != null && isAdded) {
            Constants.showAlert(activity!!, message!!)
        }
    }
}
