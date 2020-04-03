package com.diggytech.kinoplasticpremios.Support


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.EditText
import android.widget.TextView
import com.diggytech.kinoplasticpremios.Constants
import com.diggytech.kinoplasticpremios.LocationsModel
import com.diggytech.kinoplasticpremios.R
import kotlinx.android.synthetic.main.fragment_support.view.*


class SupportFragment : Fragment(), SupportContract.View {
    private lateinit var mPresenter: SupportPresenter

    lateinit var v: View
    var support_id = ""
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_support, container, false)
        mPresenter = SupportPresenter(this)
        mPresenter.getQuestionsList()

        val tvCancel = v.findViewById<TextView>(R.id.tvCancel)
        val etComment = v.findViewById<EditText>(R.id.etComment)
        val mToolbar = v.findViewById<Toolbar>(R.id.toolbar)
        val mTitle = mToolbar.findViewById<TextView>(R.id.toolbar_title)
        mTitle!!.text = getString(R.string.support)


        if (activity != null && isAdded) {
            activity!!.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        }

        tvCancel.setOnClickListener {
            etComment.setText("")
            etComment.clearFocus()
        }
        v.btnSubmit.setOnClickListener {
            mPresenter.setSupportApi()
        }
        return v
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

    override fun showSuccessMessage(message: String?) {
        if (activity != null && isAdded) {
            Constants.showAlert(activity!!, message!!)
            v.etComment.setText("")
        }
    }

    override fun showErrorMessage(message: String?) {
        if (activity != null && isAdded) {
            Constants.showAlert(activity!!, message!!)
        }
    }

    override fun setQuestions(titles: MutableList<LocationsModel>) {
        if (activity != null && isAdded) {
            Constants.callSpinner(activity!!, v.spinner, titles, 0)

            v.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {}
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    support_id = titles[position].id

                }
            }
        }
    }

    override fun getSupportId(): String {
        return support_id
    }

    override fun getRemarks(): String {
        return v.etComment.text.toString().trim()
    }
}
