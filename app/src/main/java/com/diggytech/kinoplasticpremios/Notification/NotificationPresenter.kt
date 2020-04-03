package com.diggytech.kinoplasticpremios.Notification

import com.diggytech.kinoplasticpremios.R

class NotificationPresenter(val view: NotificationContract.View) {
    fun setDataToRecycler() {
        val list = mutableListOf<ModelNotification>()
        var model = ModelNotification()
        model.title = "<b>Adam</b> likes your picture."
        model.time = "5 hours ago"
        model.img = R.drawable.john
        list.add(model)

        model = ModelNotification()
        model.title = "<b>Reyan</b> likes your picture."
        model.time = "1 day ago"
        model.img = R.drawable.john
        list.add(model)

        model = ModelNotification()
        model.title = "<b>John</b> commented on your picture."
        model.time = "2 days ago"
        model.img = R.drawable.john
        list.add(model)


        view.setAdapter(list)
    }

}
