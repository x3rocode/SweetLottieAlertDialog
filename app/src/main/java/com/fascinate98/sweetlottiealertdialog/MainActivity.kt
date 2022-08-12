package com.fascinate98.sweetlottiealertdialog

import android.animation.Animator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieCompositionFactory
import com.airbnb.lottie.LottieDrawable
import com.fascinate98.library.SweetLottieAlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import java.security.AccessController.getContext


class MainActivity : AppCompatActivity() {

    private lateinit var sd : SweetLottieAlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        testlte.repeatCount = 0
        testlte.setAnimation(R.raw.ok_btn_lottie)

        testlte.addAnimatorListener(object : Animator.AnimatorListener {

            override fun onAnimationStart(animation: Animator) {

            }
            override fun onAnimationEnd(animation: Animator) {
                sd.dismissWithAnimation()
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }
        })

        show_btn.setOnClickListener {
            openDialog()
        }

    }

    private fun openDialog(){

        when(radioGroup.checkedRadioButtonId){
            lottie_radio.id -> {
                sd = SweetLottieAlertDialog(this, SweetLottieAlertDialog.CUSTOM_IMAGE_TYPE)
                sd.setTitleText("this is lottie!")
                sd.setContentText("WOW")
                sd.setLottieDrawble(testlte.drawable)
                sd.setCancelable(true)
                sd.setConfirmText("OK")
                sd.setPopupLottieAnimation(R.raw.lottie_falling, false)
                sd.setCanceledOnTouchOutside(true)
                sd.setPopupLottieAnimation(R.raw.lottie_congratulation, false)
                sd.show()
                testlte.playAnimation()
            }
            lottiebyid_radio.id -> {
                sd = SweetLottieAlertDialog(this, SweetLottieAlertDialog.LOTTIE_ID_TYPE)
                sd.setTitleText("this is lottie!")
                sd.setContentText("WOW")
                sd.setLottieImagebyId(R.raw.lottie_falling, true)
                sd.setCancelable(true)
                sd.setConfirmText("OK")
                sd.setPopupLottieAnimation(R.raw.lottie_falling, false)
                sd.setCanceledOnTouchOutside(true)
                sd.setCustomButtonStyle(R.drawable.custom_button_style)
                sd.show()
            }

        }
    }
}