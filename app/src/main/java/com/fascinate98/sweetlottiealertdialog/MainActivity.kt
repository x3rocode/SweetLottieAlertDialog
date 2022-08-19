package com.fascinate98.sweetlottiealertdialog

import android.animation.Animator
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.fascinate98.library.Constants
import com.fascinate98.library.SweetLottieAlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*


class MainActivity : AppCompatActivity(), View.OnClickListener  {

    private lateinit var sd : SweetLottieAlertDialog
    private var i = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnIds = intArrayOf(
            basic_lottie_test.id, basic_lottie_by_id_test.id,
            basic_lottie_popup_test.id, popup_lottie_behind_test.id,
            basic_test.id, styled_text_and_stroke.id,
            basic_test_without_buttons.id,
            under_text_test.id,
            error_text_test.id,
            success_text_test.id,
            warning_confirm_test.id,
            warning_cancel_test.id,
            custom_img_test.id,
            progress_dialog.id,
            neutral_btn_test.id,
            disabled_btn_test.id,
            dark_style.id,
            custom_view_test.id,
            custom_btn_colors_test.id
        )
        for (id in btnIds) {
            findViewById<Button>(id).setOnClickListener ( this )
            findViewById<Button>(id).setOnTouchListener(Constants.FOCUS_TOUCH_LISTENER)
        }

    }


    override fun onClick(v: View) {
        when (v.id) {
            
            basic_lottie_test.id -> {
                val lsd =  SweetLottieAlertDialog(this, SweetLottieAlertDialog.CUSTOM_IMAGE_TYPE)
                lottie_test.addAnimatorListener(object : Animator.AnimatorListener {

                    override fun onAnimationStart(animation: Animator) {

                    }
                    override fun onAnimationEnd(animation: Animator) {
                        lsd.dismissWithAnimation()
                    }

                    override fun onAnimationCancel(animation: Animator) {
                    }

                    override fun onAnimationRepeat(animation: Animator) {
                    }
                })

                lsd.contentText = "Success!"
                lsd.setLottieDrawble(lottie_test.drawable)
                lsd.setCancelable(true)
                lsd.setButtonTextFont(R.font.rix_font)
                lsd.setCanceledOnTouchOutside(true)
                lsd.show()
                lottie_test.playAnimation()

            }
            basic_lottie_by_id_test.id -> {
                val lsd2 = SweetLottieAlertDialog(this, SweetLottieAlertDialog.LOTTIE_ID_TYPE)
                lsd2.contentText = "Try Again!"
                lsd2.setLottieImagebyId(R.raw.lottie_cryingface, true, null)
                lsd2.setCancelable(true)
                lsd2.confirmText = "OK"
                lsd2.setCanceledOnTouchOutside(true)
                lsd2.setButtonTextFont(R.font.lexend_deca)
                lsd2.show()
            }
            basic_lottie_popup_test.id -> {
                val lsd2 = SweetLottieAlertDialog(this, SweetLottieAlertDialog.LOTTIE_ID_TYPE)
                lsd2.contentText = "Congratulation!"
                lsd2.setLottieImagebyId(R.raw.lottie_cryingface, true, null)
                lsd2.setCancelable(true)
                lsd2.setPopupLottieAnimation(R.raw.lottie_congratulation, false, 999f)
                lsd2.setCanceledOnTouchOutside(true)
                lsd2.setButtonTextFont(R.font.lexend_deca)
                lsd2.show()
            }

            popup_lottie_behind_test.id -> {
                val lsd2 = SweetLottieAlertDialog(this, SweetLottieAlertDialog.LOTTIE_ID_TYPE)
                lsd2.contentText = "Lottie is behind the dialog"
                lsd2.setLottieImagebyId(R.raw.lottie_cryingface, true, null)
                lsd2.setCancelable(true)
                lsd2.setPopupLottieAnimation(R.raw.lottie_money, true, 0f)
                lsd2.setCanceledOnTouchOutside(true)
                lsd2.setButtonTextFont(R.font.lexend_deca)
                lsd2.show()
            }
            basic_test.id -> {
                val sd = SweetLottieAlertDialog(this)
                sd.setCancelable(true)
                sd.setCanceledOnTouchOutside(true)
                sd.setContentText("Here's a message")
                sd.show()
            }
            basic_test_without_buttons.id -> {
                val sd2 = SweetLottieAlertDialog(this)
                sd2.setCancelable(true)
                sd2.setCanceledOnTouchOutside(true)
                sd2.setContentText("Here's a message")
                sd2.hideConfirmButton()
                sd2.show()
            }
            under_text_test.id -> SweetLottieAlertDialog(this, SweetLottieAlertDialog.NORMAL_TYPE)
                .setTitleText("Title")
                .setContentText("It's pretty, isn't it?")
                .show()
            styled_text_and_stroke.id -> SweetLottieAlertDialog(this, SweetLottieAlertDialog.NORMAL_TYPE)
                .setTitleText("<font color='red'>Red</font> title")
                .setContentText("Big <font color='green'>green </font><b><i> bold</i></b>")
                .setContentTextSize(21)
                .setStrokeWidth(2.0f)
                .show()
            error_text_test.id -> SweetLottieAlertDialog(this, SweetLottieAlertDialog.ERROR_TYPE)
                .setTitleText("Oops...")
                .setContentText("Something went wrong!")
                .show()
            success_text_test.id -> SweetLottieAlertDialog(this, SweetLottieAlertDialog.SUCCESS_TYPE)
                .setTitleText("Good job!")
                .setContentText("You clicked the button!")
                .show()
            warning_confirm_test.id -> SweetLottieAlertDialog(this, SweetLottieAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setCancelButton("Yes, delete it!"
                ) { sweetAlertDialog -> // reuse previous dialog instance
                    sweetAlertDialog.setTitleText("Deleted!")
                        .setContentText("Your imaginary file has been deleted!")
                        .setConfirmClickListener(null)
                        .changeAlertType(SweetLottieAlertDialog.SUCCESS_TYPE)
                }
                .show()
            warning_cancel_test.id -> SweetLottieAlertDialog(this, SweetLottieAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setCancelText("No, cancel pls!")
                .setConfirmText("Yes, delete it!")
                .showCancelButton(true)
                .setCancelClickListener { sDialog -> // reuse previous dialog instance, keep widget user state, reset them if you need
                    sDialog.setTitleText("Cancelled!")
                        .setContentText("Your imaginary file is safe :)")
                        .setConfirmText("OK")
                        .showCancelButton(false)
                        .setCancelClickListener(null)
                        .setConfirmClickListener(null)
                        .changeAlertType(SweetLottieAlertDialog.ERROR_TYPE)

                    // or you can new a SweetAlertDialog to show
                    /* sDialog.dismiss();
                                            new SweetAlertDialog(SampleActivity.this, SweetAlertDialog.ERROR_TYPE)
                                                    .setTitleText("Cancelled!")
                                                    .setContentText("Your imaginary file is safe :)")
                                                    .setConfirmText("OK")
                                                    .show();*/
                }
                .setConfirmClickListener { sDialog ->
                    sDialog.setTitleText("Deleted!")
                        .setContentText("Your imaginary file has been deleted!")
                        .setConfirmText("OK")
                        .showCancelButton(false)
                        .setCancelClickListener(null)
                        .setConfirmClickListener(null)
                        .changeAlertType(SweetLottieAlertDialog.SUCCESS_TYPE)
                }
                .show()
            custom_img_test.id -> SweetLottieAlertDialog(this, SweetLottieAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText("Sweet!")
                .setContentText("Here's a custom image.")
                .setCustomImage(R.drawable.custom_img)
                .show()
            progress_dialog.id -> {
                val pDialog: SweetLottieAlertDialog =
                    SweetLottieAlertDialog(this, SweetLottieAlertDialog.PROGRESS_TYPE)
                        .setTitleText("Loading")
                pDialog.show()
                pDialog.setCancelable(false)
                object : CountDownTimer(800 * 7, 800) {
                    override fun onTick(millisUntilFinished: Long) {
                        // you can change the progress bar color by ProgressHelper every 800 millis
                        i++
                        when (i) {
                            0 -> pDialog.getProgressHelper()
                                .setBarColor(com.fascinate98.library.R.color.blue_btn_bg_color)
                            1 -> pDialog.getProgressHelper()
                                .setBarColor(com.fascinate98.library.R.color.material_deep_teal_50)
                            2 -> pDialog.getProgressHelper()
                                .setBarColor(com.fascinate98.library.R.color.success_stroke_color)
                            3 -> pDialog.getProgressHelper()
                                .setBarColor(com.fascinate98.library.R.color.material_deep_teal_20)
                            4 -> pDialog.getProgressHelper()
                                .setBarColor(com.fascinate98.library.R.color.material_blue_grey_80)
                            5 -> pDialog.getProgressHelper()
                                .setBarColor(com.fascinate98.library.R.color.warning_stroke_color)
                            6 -> pDialog.getProgressHelper()
                                .setBarColor(com.fascinate98.library.R.color.success_stroke_color)
                        }
                    }

                    override fun onFinish() {
                        i = -1
                        pDialog.setTitleText("Success!")
                            .setConfirmText("OK")
                            .changeAlertType(SweetLottieAlertDialog.SUCCESS_TYPE)
                    }
                }.start()
            }
            neutral_btn_test.id -> SweetLottieAlertDialog(this, SweetLottieAlertDialog.NORMAL_TYPE)
                .setTitleText("Title")
                .setContentText("Three buttons dialog")
                .setConfirmText("Confirm")
                .setCancelText("Cancel")
                .setCustomText("Neutral")
                .show()
            disabled_btn_test.id -> {
                val disabledBtnDialog: SweetLottieAlertDialog =
                    SweetLottieAlertDialog(this, SweetLottieAlertDialog.NORMAL_TYPE)
                        .setTitleText("Title")
                        .setContentText("Disabled button dialog")
                        .setConfirmText("OK")
                        .setCancelText("Cancel")
                        .setCustomText("Neutral")
                disabledBtnDialog.setOnShowListener(DialogInterface.OnShowListener {
                    disabledBtnDialog.getButton(
                        SweetLottieAlertDialog.BUTTON_CONFIRM
                    ).setEnabled(false)
                })
                disabledBtnDialog.show()
            }
            dark_style.id -> SweetLottieAlertDialog.DARK_STYLE = (v as CheckBox).isChecked
            custom_view_test.id -> {
                val editText = EditText(this)
                val checkBox = CheckBox(this)
                editText.setText("Some edit text")
                checkBox.isChecked = true
                checkBox.text = "Some checkbox"
                if (SweetLottieAlertDialog.DARK_STYLE) {
                    editText.setTextColor(Color.WHITE)
                    checkBox.setTextColor(Color.WHITE)
                }
                val linearLayout = LinearLayout(applicationContext)
                linearLayout.orientation = LinearLayout.VERTICAL
                linearLayout.addView(editText)
                linearLayout.addView(checkBox)
                val dialog: SweetLottieAlertDialog = SweetLottieAlertDialog(this, SweetLottieAlertDialog.NORMAL_TYPE)
                    .setTitleText("Custom view")
                    .hideConfirmButton()
                dialog.setCustomView(linearLayout)
                dialog.show()
            }
            custom_btn_colors_test.id -> SweetLottieAlertDialog(this, SweetLottieAlertDialog.NORMAL_TYPE)
                .setTitleText("Custom view")
                .setCancelButton("red", null)
                .setCancelButtonBackgroundColor(Color.RED)
                .setCustomButton("cyan", null)
                .setNeutralButtonBackgroundColor(Color.CYAN)
                .setConfirmButton("blue", null)
                .setConfirmButtonBackgroundColor(Color.BLUE)
                .show()



        }


    }
}