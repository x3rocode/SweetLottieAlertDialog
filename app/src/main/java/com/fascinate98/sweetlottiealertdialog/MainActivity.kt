package com.fascinate98.sweetlottiealertdialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.airbnb.lottie.LottieAnimationView
import com.fascinate98.library.SweetLottieAlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivity : AppCompatActivity() {
    private lateinit var sd: SweetLottieAlertDialog
    private lateinit var lottie: LottieAnimationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        lottie = LottieAnimationView(this)
        lottie.setAnimation(R.raw.lottie_cryingface)
        //lottie.setAnimation("lottie_cryingface.json")

        testbtn.setOnClickListener {
            sd = SweetLottieAlertDialog(this, SweetLottieAlertDialog.LOTTIE_ID_TYPE)
            sd.setTitleText("Congratulation!")
            sd.setContentText("구매에 성공했어요")
            //sd.setLottieImagebyId(com.fascinate98.sw)
            sd.setLottieImagebyId(R.raw.lottie_cryingface)
            //sd.setLottieAnimation(lottie!!)
            sd.setCancelable(true)
            sd.setConfirmText("OK")
            sd.setCanceledOnTouchOutside(true)

            sd.show()
        }


    }
}