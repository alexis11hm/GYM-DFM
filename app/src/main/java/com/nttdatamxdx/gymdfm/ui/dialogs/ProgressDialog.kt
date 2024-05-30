package com.nttdatamxdx.gymdfm.ui.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.nttdatamxdx.gymdfm.R

class ProgressDialog(context: Context) {

    private var dialog : AlertDialog
    private var message : TextView

    init {

        val view = LayoutInflater.from(context).inflate(R.layout.progress_dialog, null, false)

        message = view.findViewById(R.id.message_progress_bar)

        dialog = AlertDialog.Builder(context)
            .setView(view)
            .setCancelable(false)
            .create()
    }

    fun setMessage(message: String){
        this.message.text = message
    }

    fun show(){
        dialog.show()
    }

    fun dismiss(){
        dialog.dismiss()
    }


}