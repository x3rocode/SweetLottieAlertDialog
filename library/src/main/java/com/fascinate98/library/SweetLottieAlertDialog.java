package com.fascinate98.library;




import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.airbnb.lottie.Lottie;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.List;

public class SweetLottieAlertDialog extends Dialog implements View.OnClickListener {
    private View mDialogView;
    private AnimationSet mModalInAnim;
    private AnimationSet mModalOutAnim;
    private Animation mOverlayOutAnim;
    private Animation mErrorInAnim;
    private AnimationSet mErrorXInAnim;
    private AnimationSet mSuccessLayoutAnimSet;
    private Animation mSuccessBowAnim;
    private TextView mTitleTextView;
    private View mCustomView;
    private TextView mContentTextView;
    private String mTitleText;
    private String mContentText;
    private boolean mShowCancel;
    private LinearLayout mButtonsContainer;
    private boolean mShowContent;
    private boolean mShowCustom;
    private FrameLayout mCustomViewContainer;
    private String mCancelText;
    private String mConfirmText;
    private String mCustomText;
    private int mAlertType;
    private FrameLayout mErrorFrame;
    private FrameLayout mSuccessFrame;
    private FrameLayout mProgressFrame;
    private SuccessTickView mSuccessTick;
    private ImageView mErrorX;
    private View mSuccessLeftMask;
    private View mSuccessRightMask;
    private Drawable mCustomImgDrawable;
    private ImageView mCustomImage;
    private LottieAnimationView mLottieAnimationView;
    private LottieAnimationView mPopupLottieAnimationView;

    private Button mConfirmButton;
    private Button mCancelButton;
    private Button mCustomButton;
    private boolean mHideConfirmButton = false;
    private Integer fontResource;
    private Integer mConfirmButtonBackgroundColor;
    private Integer mConfirmButtonTextColor;
    private Integer mNeutralButtonBackgroundColor;
    private Integer mNeutralButtonTextColor;
    private Integer mCancelButtonBackgroundColor;
    private Integer mCancelButtonTextColor;
    private ProgressHelper mProgressHelper;
    private FrameLayout mWarningFrame;
    private OnSweetClickListener mCancelClickListener;
    private OnSweetClickListener mConfirmClickListener;
    private OnSweetClickListener mCustomClickListener;
    private boolean mHideKeyBoardOnDismiss = true;
    private int contentTextSize = 0;
    private int mLottieRes;
    private int mPopupLottieRes;
    private boolean mLottieIsLoop;
    private boolean mPopupLottieIsLoop;
    private boolean mCloseFromCancel;
    private float mPopupLottieElevation;
    //aliases
    public final static int BUTTON_CONFIRM = DialogInterface.BUTTON_POSITIVE;
    public final static int BUTTON_CANCEL = DialogInterface.BUTTON_NEGATIVE;

    private final float defStrokeWidth;
    private float strokeWidth = 0;

    public static final int NORMAL_TYPE = 0;
    public static final int ERROR_TYPE = 1;
    public static final int SUCCESS_TYPE = 2;
    public static final int WARNING_TYPE = 3;
    public static final int CUSTOM_IMAGE_TYPE = 4;
    public static final int PROGRESS_TYPE = 5;
    public static final int LOTTIE_ID_TYPE = 6;


    public static boolean DARK_STYLE = false;




    public interface OnSweetClickListener {
        void onClick(SweetLottieAlertDialog sweetLottieAlertDialog);
    }

    public SweetLottieAlertDialog(Context context) {
        this(context, NORMAL_TYPE);
    }

    public SweetLottieAlertDialog hideConfirmButton() {
        this.mHideConfirmButton = true;
        return this;
    }



    public SweetLottieAlertDialog(Context context, int alertType) {
        super(context, DARK_STYLE ? R.style.alert_dialog_dark : R.style.alert_dialog_light);
        setCancelable(true);
        setCanceledOnTouchOutside(false);



        defStrokeWidth = getContext().getResources().getDimension(R.dimen.buttons_stroke_width);
        strokeWidth = defStrokeWidth;

        mProgressHelper = new ProgressHelper(context);
        mAlertType = alertType;
        mErrorInAnim = OptAnimationLoader.loadAnimation(getContext(), R.anim.error_frame_in);
        mErrorXInAnim = (AnimationSet)OptAnimationLoader.loadAnimation(getContext(), R.anim.error_x_in);
        // 2.3.x system don't support alpha-animation on layer-list drawable
        // remove it from animation set
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            List<Animation> childAnims = mErrorXInAnim.getAnimations();
            int idx = 0;
            for (;idx < childAnims.size();idx++) {
                if (childAnims.get(idx) instanceof AlphaAnimation) {
                    break;
                }
            }
            if (idx < childAnims.size()) {
                childAnims.remove(idx);
            }
        }
        mSuccessBowAnim = OptAnimationLoader.loadAnimation(getContext(), R.anim.success_bow_roate);
        mSuccessLayoutAnimSet = (AnimationSet)OptAnimationLoader.loadAnimation(getContext(), R.anim.success_mask_layout);
        mModalInAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.modal_in);
        mModalOutAnim = (AnimationSet) OptAnimationLoader.loadAnimation(getContext(), R.anim.modal_out);
        mModalOutAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mDialogView.setVisibility(View.GONE);
                mDialogView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (mCloseFromCancel) {
                            SweetLottieAlertDialog.super.cancel();
                        } else {
                            SweetLottieAlertDialog.super.dismiss();
                        }
                    }
                });
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        // dialog overlay fade out
        mOverlayOutAnim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                WindowManager.LayoutParams wlp = getWindow().getAttributes();
                wlp.alpha = 1 - interpolatedTime;
                getWindow().setAttributes(wlp);
            }
        };
        mOverlayOutAnim.setDuration(120);

    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_dialog);

        mDialogView = getWindow().getDecorView().findViewById(android.R.id.content);
        mTitleTextView = (TextView)findViewById(R.id.title_text);
        mContentTextView = (TextView)findViewById(R.id.content_text);
        mCustomViewContainer = findViewById(R.id.custom_view_container);
        mErrorFrame = (FrameLayout)findViewById(R.id.error_frame);
        mErrorX = (ImageView)mErrorFrame.findViewById(R.id.error_x);
        mSuccessFrame = (FrameLayout)findViewById(R.id.success_frame);
        mProgressFrame = (FrameLayout)findViewById(R.id.progress_dialog);
        mSuccessTick = (SuccessTickView)mSuccessFrame.findViewById(R.id.success_tick);
        mSuccessLeftMask = mSuccessFrame.findViewById(R.id.mask_left);
        mSuccessRightMask = mSuccessFrame.findViewById(R.id.mask_right);
        mCustomImage = (ImageView)findViewById(R.id.custom_image);
        mWarningFrame = (FrameLayout)findViewById(R.id.warning_frame);
        mConfirmButton = (Button)findViewById(R.id.confirm_button);
        mCancelButton = (Button)findViewById(R.id.cancel_button);
        mCustomButton = (Button)findViewById(R.id.custom_button);
        mLottieAnimationView = (LottieAnimationView)findViewById(R.id.custom_lottie_view);
        mProgressHelper.setProgressWheel((ProgressWheel)findViewById(R.id.progressWheel));
        mPopupLottieAnimationView = (LottieAnimationView)findViewById(R.id.popup_lottie_view);
        mButtonsContainer = findViewById(R.id.buttons_container);
        mConfirmButton.setOnClickListener(this);
        mCancelButton.setOnClickListener(this);
        mCustomButton.setOnClickListener(this);


        setTitleText(mTitleText);
        setContentText(mContentText);
        setCancelText(mCancelText);
        setConfirmText(mConfirmText);
        setCustomView(mCustomView);
        setCustomText(mCustomText);
        changeAlertType(mAlertType, true);
        setPopupLottieAnimation(mPopupLottieRes, mPopupLottieIsLoop, mPopupLottieElevation);
        applyStroke();
        setConfirmButtonBackgroundColor(mConfirmButtonBackgroundColor);
        setConfirmButtonTextColor(mConfirmButtonTextColor);
        setCancelButtonBackgroundColor(mCancelButtonBackgroundColor);
        setCancelButtonTextColor(mCancelButtonTextColor);
        setNeutralButtonBackgroundColor(mNeutralButtonBackgroundColor);
        setNeutralButtonTextColor(mNeutralButtonTextColor);
        changeAlertType(mAlertType, true);
    }

    private void restore () {
        mCustomImage.setVisibility(View.GONE);
        mErrorFrame.setVisibility(View.GONE);
        mSuccessFrame.setVisibility(View.GONE);
        mWarningFrame.setVisibility(View.GONE);
        mProgressFrame.setVisibility(View.GONE);

        mConfirmButton.setVisibility(mHideConfirmButton ? View.GONE : View.VISIBLE);

        adjustButtonContainerVisibility();

        mConfirmButton.setBackgroundResource(R.drawable.blue_button_background);
        mErrorFrame.clearAnimation();
        mErrorX.clearAnimation();
        mSuccessTick.clearAnimation();
        mSuccessLeftMask.clearAnimation();
        mSuccessRightMask.clearAnimation();
    }

    /**
     * Hides buttons container if all buttons are invisible or gone.
     * This deletes useless margins
     */
    private void adjustButtonContainerVisibility() {
        boolean showButtonsContainer = false;
        for (int i = 0; i < mButtonsContainer.getChildCount(); i++) {
            View view = mButtonsContainer.getChildAt(i);
            if (view instanceof Button && view.getVisibility() == View.VISIBLE) {
                showButtonsContainer = true;
                break;
            }
        }
        mButtonsContainer.setVisibility(View.VISIBLE);
       // mButtonsContainer.setVisibility(showButtonsContainer ? View.VISIBLE : View.GONE);
    }

    private void playAnimation () {
        if (mAlertType == ERROR_TYPE) {
            mErrorFrame.startAnimation(mErrorInAnim);
            mErrorX.startAnimation(mErrorXInAnim);
        } else if (mAlertType == SUCCESS_TYPE) {
            mSuccessTick.startTickAnim();
            mSuccessRightMask.startAnimation(mSuccessBowAnim);
        }
    }

    private void changeAlertType(int alertType, boolean fromCreate) {
        mAlertType = alertType;
        // call after created views
        if (mDialogView != null) {
            if (!fromCreate) {
                // restore all of views state before switching alert type
                restore();
            }
            mConfirmButton.setVisibility(mHideConfirmButton ? View.GONE : View.VISIBLE);
            switch (mAlertType) {
                case ERROR_TYPE:
                    mErrorFrame.setVisibility(View.VISIBLE);
                    break;
                case SUCCESS_TYPE:
                    mSuccessFrame.setVisibility(View.VISIBLE);
                    // initial rotate layout of success mask
                    mSuccessLeftMask.startAnimation(mSuccessLayoutAnimSet.getAnimations().get(0));
                    mSuccessRightMask.startAnimation(mSuccessLayoutAnimSet.getAnimations().get(1));
                    break;
                case WARNING_TYPE:
                    //mConfirmButton.setBackgroundResource(R.drawable.red_button_background);
                    mWarningFrame.setVisibility(View.VISIBLE);
                    break;
                case CUSTOM_IMAGE_TYPE:
                    mLottieAnimationView.setVisibility(View.GONE);
                    mCustomImage.setVisibility(View.VISIBLE);
                    setCustomImage(mCustomImgDrawable);
                    break;
                case PROGRESS_TYPE:
                    mProgressFrame.setVisibility(View.VISIBLE);
                    mConfirmButton.setVisibility(View.GONE);
                    break;
                case LOTTIE_ID_TYPE:
                    mLottieAnimationView.setVisibility(View.VISIBLE);
                    mCustomImage.setVisibility(View.GONE);
                    setLottieImagebyId(mLottieRes, mLottieIsLoop);
            }
            if (!fromCreate) {
                playAnimation();
            }
        }
    }

    public int getAlerType () {
        return mAlertType;
    }

    public void changeAlertType(int alertType) {
        changeAlertType(alertType, false);
    }


    public String getTitleText () {
        return mTitleText;
    }

    public SweetLottieAlertDialog setTitleText (String text) {
        mTitleText = text;
        if (mTitleTextView != null && mTitleText != null) {
            mTitleTextView.setText(mTitleText);
        }
        return this;
    }



    public String getContentText () {
        return mContentText;
    }

    public SweetLottieAlertDialog setContentText (String text) {
        mContentText = text;
        if (mContentTextView != null && mContentText != null) {
            showContentText(true);
            mContentTextView.setText(mContentText);
        }
        return this;
    }


    public static int spToPx(float sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    /**
     * @param width in SP
     */
    public SweetLottieAlertDialog setStrokeWidth(float width) {
        this.strokeWidth = spToPx(width, getContext());
        return this;
    }

    private void applyStroke() {
        if (Float.compare(defStrokeWidth, strokeWidth) != 0) {
            Resources r = getContext().getResources();
            setButtonBackgroundColor(mConfirmButton, r.getColor(R.color.main_green_color));
            setButtonBackgroundColor(mCustomButton, r.getColor(R.color.main_disabled_color));
            setButtonBackgroundColor(mCancelButton, r.getColor(R.color.red_btn_bg_color));
        }
    }


    public boolean isShowCancelButton () {
        return mShowCancel;
    }

    public SweetLottieAlertDialog showCancelButton (boolean isShow) {
        mShowCancel = isShow;
        if (mCancelButton != null) {
            mCancelButton.setVisibility(mShowCancel ? View.VISIBLE : View.GONE);
        }
        return this;
    }

    public boolean isShowContentText () {
        return mShowContent;
    }

    public SweetLottieAlertDialog showContentText (boolean isShow) {
        mShowContent = isShow;
        if (mContentTextView != null) {
            mContentTextView.setVisibility(mShowContent ? View.VISIBLE : View.GONE);
        }
        return this;
    }


    public String getCancelText () {
        return mCancelText;
    }

    public SweetLottieAlertDialog setCancelText (String text) {
        mCancelText = text;
        if (mCancelButton != null && mCancelText != null) {
            showCancelButton(true);
            mCancelButton.setText(mCancelText);
        }
        return this;
    }

    public String getConfirmText () {
        return mConfirmText;
    }

    public SweetLottieAlertDialog setConfirmText (String text) {
        mConfirmText = text;
        if (mConfirmButton != null && mConfirmText != null) {
            mConfirmButton.setText(mConfirmText);
        }
        return this;
    }

    public String getCustomText () {
        return mCustomText;
    }

    public SweetLottieAlertDialog setCustomText (String text) {
        mCustomText = text;
        if (mCustomButton != null && mCustomText != null) {
            mCustomButton.setText(mCustomText);
        }
        return this;
    }

//button sett
    public SweetLottieAlertDialog setConfirmButtonBackgroundColor(Integer color) {
        mConfirmButtonBackgroundColor = color;
        setButtonBackgroundColor(mConfirmButton, color);
        return this;
}

    public Integer getConfirmButtonBackgroundColor() {
        return mConfirmButtonBackgroundColor;
    }

    public SweetLottieAlertDialog setNeutralButtonBackgroundColor(Integer color) {
        mNeutralButtonBackgroundColor = color;
        setButtonBackgroundColor(mCustomButton, color);
        return this;
    }

    public Integer getNeutralButtonBackgroundColor() {
        return mNeutralButtonBackgroundColor;
    }

    public SweetLottieAlertDialog setCancelButtonBackgroundColor(Integer color) {
        mCancelButtonBackgroundColor = color;
        setButtonBackgroundColor(mCancelButton, color);
        return this;
    }

    public Integer getCancelButtonBackgroundColor() {
        return mCancelButtonBackgroundColor;
    }

    public SweetLottieAlertDialog setButtonTextFont(Integer font){
        if(fontResource != null){
            fontResource = font;
            Typeface typeface = getContext().getResources().getFont(fontResource);
            mConfirmButton.setTypeface(typeface);

        }

        return this;
    }

    private void setButtonBackgroundColor(Button btn, Integer color) {
        if (btn != null && color != null) {
            Drawable[] drawableItems = ViewUtils.getDrawable(btn);
            if (drawableItems != null) {
                GradientDrawable gradientDrawableUnChecked = (GradientDrawable) drawableItems[1];
                //solid color
                gradientDrawableUnChecked.setColor(color);
                //stroke
                gradientDrawableUnChecked.setStroke((int) strokeWidth, genStrokeColor(color));
            }
        }
    }

    private int genStrokeColor(int color) {
        float hsv[] = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.7f; // decrease value component
        return Color.HSVToColor(hsv);
    }

    public SweetLottieAlertDialog setConfirmButtonTextColor(Integer color) {
        mConfirmButtonTextColor = color;
        if (mConfirmButton != null && color != null) {
            mConfirmButton.setTextColor(mConfirmButtonTextColor);
        }
        return this;
    }

    public Integer getConfirmButtonTextColor() {
        return mConfirmButtonTextColor;
    }

    public SweetLottieAlertDialog setNeutralButtonTextColor(Integer color) {
        mNeutralButtonTextColor = color;
        if (mCustomButton != null && color != null) {
            mCustomButton.setTextColor(mNeutralButtonTextColor);
        }
        return this;
    }

    public Integer getNeutralButtonTextColor() {
        return mNeutralButtonTextColor;
    }

    public SweetLottieAlertDialog setCancelButtonTextColor(Integer color) {
        mCancelButtonTextColor = color;
        if (mCancelButton != null && color != null) {
            mCancelButton.setTextColor(mCancelButtonTextColor);
        }
        return this;
    }

    public Integer getCancelButtonTextColor() {
        return mCancelButtonTextColor;
    }



    public SweetLottieAlertDialog setCancelClickListener (OnSweetClickListener listener) {
        mCancelClickListener = listener;
        return this;
    }

    public SweetLottieAlertDialog setConfirmClickListener (OnSweetClickListener listener) {
        mConfirmClickListener = listener;
        return this;
    }

    public SweetLottieAlertDialog setCustomClickListener (OnSweetClickListener listener) {
        mCustomClickListener = listener;
        return this;
    }


    public SweetLottieAlertDialog setCustomImage (Drawable drawable) {
        mCustomImgDrawable = drawable;
        if (mCustomImage != null && mCustomImgDrawable != null) {
            mCustomImage.setVisibility(View.VISIBLE);
            mCustomImage.setImageDrawable(mCustomImgDrawable);
        }
        return this;
    }

    public SweetLottieAlertDialog setLottieDrawble (Drawable drawable) {
        mCustomImgDrawable = drawable;
        if (mCustomImage != null && mCustomImgDrawable != null) {
            mCustomImage.setVisibility(View.VISIBLE);
            mCustomImage.setImageDrawable(mCustomImgDrawable);
        }
        return this;
    }

    public SweetLottieAlertDialog setCustomImage (int resourceId) {
        return setCustomImage(getContext().getResources().getDrawable(resourceId));
    }

    public SweetLottieAlertDialog setLottieImagebyId (int lottieRes, boolean isLoop) {
        mLottieRes = lottieRes;
        mLottieIsLoop = isLoop;

        if (mLottieAnimationView != null ) {

            mLottieAnimationView.setVisibility(View.VISIBLE);

            mLottieAnimationView.setAnimation(mLottieRes);
            mLottieAnimationView.playAnimation();
            mLottieAnimationView.loop(mLottieIsLoop);
        }
        return this;
    }

    public SweetLottieAlertDialog setPopupLottieAnimation (int lottieRes, boolean isLoop, float popupLottieElevation) {
        mPopupLottieRes = lottieRes;
        mPopupLottieIsLoop = isLoop;
        mPopupLottieElevation = popupLottieElevation;

        if (mPopupLottieRes != 0 && mPopupLottieAnimationView != null) {

            mPopupLottieAnimationView.setVisibility(View.VISIBLE);
            mPopupLottieAnimationView.setAnimation(mPopupLottieRes);
            mPopupLottieAnimationView.playAnimation();
            mPopupLottieAnimationView.setElevation(mPopupLottieElevation);
            mPopupLottieAnimationView.loop(mPopupLottieIsLoop);
        }
        return this;
    }





    public Button getButton(int buttonType) {
        switch (buttonType) {
            default:
            case BUTTON_CONFIRM:
                return mConfirmButton;
            case BUTTON_CANCEL:
                return mCancelButton;
            case BUTTON_NEUTRAL:
                return mCustomButton;
        }
    }

    public SweetLottieAlertDialog setConfirmButton(String text, OnSweetClickListener listener) {
        this.setConfirmText(text);
        this.setConfirmClickListener(listener);
        return this;
    }

    public SweetLottieAlertDialog setConfirmButton(int resId, OnSweetClickListener listener) {
        String text = getContext().getResources().getString(resId);
        setConfirmButton(text, listener);
        return this;
    }


    public SweetLottieAlertDialog setCancelButton(String text, OnSweetClickListener listener) {
        this.setCancelText(text);
        this.setCancelClickListener(listener);
        return this;
    }

    public SweetLottieAlertDialog setCancelButton(int resId, OnSweetClickListener listener) {
        String text = getContext().getResources().getString(resId);
        setCancelButton(text, listener);
        return this;
    }

    public SweetLottieAlertDialog setCustomButton(String text, OnSweetClickListener listener) {
        this.setCustomText(text);
        this.setCustomClickListener(listener);
        return this;
    }

    public SweetLottieAlertDialog setCustomButton(int resId, OnSweetClickListener listener) {
        String text = getContext().getResources().getString(resId);
        setCustomButton(text, listener);
        return this;
    }



    /**
     * Set content text size
     *
     * @param value text size in sp
     */
    public SweetLottieAlertDialog setContentTextSize(int value) {
        this.contentTextSize = value;
        return this;
    }

    public int getContentTextSize() {
        return contentTextSize;
    }


    protected void onStart() {
        mDialogView.startAnimation(mModalInAnim);
        playAnimation();
    }
    public SweetLottieAlertDialog setCustomView(View view) {
        mCustomView = view;
        if (mCustomView != null && mCustomViewContainer != null) {
            mCustomViewContainer.addView(view);
            mCustomViewContainer.setVisibility(View.VISIBLE);
            mContentTextView.setVisibility(View.GONE);
        }
        return this;
    }
    /**
     * The real Dialog.cancel() will be invoked async-ly after the animation finishes.
     */
    @Override
    public void cancel() {
        dismissWithAnimation(true);
    }

    /**
     * The real Dialog.dismiss() will be invoked async-ly after the animation finishes.
     */
    public void dismissWithAnimation() {
        dismissWithAnimation(false);
    }

    private void dismissWithAnimation(boolean fromCancel) {
        mCloseFromCancel = fromCancel;
        mConfirmButton.startAnimation(mOverlayOutAnim);
        mCustomButton.startAnimation(mOverlayOutAnim);
        mDialogView.startAnimation(mModalOutAnim);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cancel_button) {
            if (mCancelClickListener != null) {
                mCancelClickListener.onClick(SweetLottieAlertDialog.this);
            } else {
                dismissWithAnimation();
            }
        } else if (v.getId() == R.id.confirm_button) {
            if (mConfirmClickListener != null) {
                mConfirmClickListener.onClick(SweetLottieAlertDialog.this);
            } else {
                dismissWithAnimation();
            }
        } else if (v.getId() == R.id.custom_button) {
            if (mConfirmClickListener != null) {
                mConfirmClickListener.onClick(SweetLottieAlertDialog.this);
            } else {
                dismissWithAnimation();
            }
        }
    }

    public ProgressHelper getProgressHelper () {
        return mProgressHelper;
    }
}