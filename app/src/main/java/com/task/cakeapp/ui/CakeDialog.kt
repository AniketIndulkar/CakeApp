package com.task.cakeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.task.cakeapp.data.CakeModel
import com.task.cakeapp.databinding.CakeDetailDialogBinding

//Fragment dialog to show the Cake details
class CakeDialog(private val cake: CakeModel) : DialogFragment() {
    //Data binding
    private lateinit var binding :CakeDetailDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = CakeDetailDialogBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cake= cake// setting data to binding class
    }


    override fun onStart() {
        super.onStart()
        //Setting some window attributes to Dialog
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog!!.window!!.setBackgroundDrawableResource(android.R.color.transparent)
    }

}