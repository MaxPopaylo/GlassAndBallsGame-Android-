package com.example.randomglassgame.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.example.randomglassgame.R
import com.example.randomglassgame.contracts.router
import com.example.randomglassgame.databinding.FragmentLoseDialogBinding
import com.example.randomglassgame.entity.GameInfo
import com.example.randomglassgame.entity.Settings
import java.util.zip.Inflater

class LoseDialogFragment : DialogFragment() {

    private lateinit var info: GameInfo

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        info = getInfo()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val binding = FragmentLoseDialogBinding.inflate(LayoutInflater.from(context))

        with(binding) {
            tvScore.text = info.score.toString()
            tvCoins.text = info.coins.toString()

            btnExitLoseAlert.setOnClickListener { onExitClickListener() }
            btnAgainLoseAlert.setOnClickListener { onAgainClickListener() }
        }

        val dialog = Dialog(requireContext())
            .apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(false)
            setContentView(binding.root)
        }

        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            setGravity(Gravity.CENTER);
        }

        return dialog
    }

    private fun onExitClickListener() {
        router().publishResult(info)
        router().goBack()

        dialog?.dismiss()
    }

    private fun onAgainClickListener() {
        router().publishResult(info)
        router().goBack()

        dialog?.dismiss()
        router().showGameScreen(Settings.DEFAULT_STATE)
    }


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun getInfo() : GameInfo = requireArguments().getParcelable(ARG_INFO, GameInfo::class.java)!!

    companion object {
        fun newInstance(info: GameInfo): LoseDialogFragment {
            val args = Bundle().apply {
                putParcelable(ARG_INFO, info)
            }

            val fragment = LoseDialogFragment()
            fragment.arguments = args
            return fragment
        }

        @JvmStatic val ARG_INFO = "ARG_INFO"
    }

}