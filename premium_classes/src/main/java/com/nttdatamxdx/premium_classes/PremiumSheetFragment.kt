package com.nttdatamxdx.premium_classes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PremiumSheetFragment(
    val onTapStart : () -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var button : CardView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_premium_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = false

        button = view.findViewById(R.id.start_button_card)

        button.setOnClickListener {
            dismiss()
            onTapStart()
        }
    }

}