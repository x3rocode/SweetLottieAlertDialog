# SweetLottieAlertDialog :sparkles:
:sparkles: SweetAlertDialog with lottie! :sparkles:

SweetAlert for Android, a beautiful and clever alert dialog with lottie animation effect!

Origin repo : https://github.com/pedant/sweet-alert-dialog

![ezgif-2-374eab3d3c](https://user-images.githubusercontent.com/51151970/193203319-2942fc6a-e401-41cb-a39f-69ffbe1599a1.gif)

## feature
You can setup Lottie animation in 2parts.

|NO|Explanation|Example|
|----|:----:|:----:|
|**1. Lottie Icon in alert**|<img src="https://user-images.githubusercontent.com/51151970/193205966-136c656d-c1a3-4946-9392-39412e145a8a.jpg" width="250" height="490"/>|**loop**<br><img src="https://user-images.githubusercontent.com/51151970/193729848-29c1930b-c278-4fe9-bfc5-78807da05984.gif" width="400"/><br>**no loop**<br><img src="https://user-images.githubusercontent.com/51151970/193729862-e8f6373e-bae1-41cb-b8c9-4c7407ea4cc5.gif" width="400"/>|
|**2. Full Screen Lottie**|<img src="https://user-images.githubusercontent.com/51151970/193205964-0ff567d0-d0a6-4cb0-8373-338814ee9420.jpg" width="250" height="490"/><br><img src="https://user-images.githubusercontent.com/51151970/193205958-21d91020-4072-457c-b231-7df427365f9b.jpg" width="250" height="490"/>|**Lottie behind alert**<br><img src="https://user-images.githubusercontent.com/51151970/193729856-366f9296-ab5a-46e2-9d4d-50fd0876013d.gif" width="270" height="490"/><br>**Lottie cover alert**<br><img src="https://user-images.githubusercontent.com/51151970/193729851-65aac580-30ce-4bea-8051-94b4a65b601f.gif" width="270" height="490"/>|
 

## Using Gradle :rocket:
In your top-level `build.gradle` file:
```gradle
repositories {
  ...
  mavenCentral()
}
```

In your app `build.gradle` file:
```groovy
dependencies {
    implementation 'com.github.fascinate98:SweetLottieAlertDialog:v1.0.0'
}
```

## How to Use :zap:

Check here [BASIC USAGE](https://github.com/pedant/sweet-alert-dialog) 

1. Go https://lottiefiles.com/ 

2. Search Lottie animation you want.

3. Download lottie json file.
<img src="https://user-images.githubusercontent.com/51151970/193731050-82994876-42df-4df8-b894-b8af88a28e4e.PNG" width="500"/>

4. (option) Change the downloaded file name.
<img src="https://user-images.githubusercontent.com/51151970/193731053-171cd923-b24b-4da4-9812-db5b0c117c4c.PNG" width="500"/>
<img src="https://user-images.githubusercontent.com/51151970/193731055-c9ef44f9-0795-4928-b648-94b988b5caa4.PNG" width="500"/>

5. Make res -> New -> Android Resource Directory
<img src="https://user-images.githubusercontent.com/51151970/193731056-c87135fc-a180-4cce-94a7-4d5a79739d50.png"/>

6. Make raw folder. (Resource type -> raw)
<img src="https://user-images.githubusercontent.com/51151970/193731058-12397a99-6a52-4062-b63d-cdbc27cd7555.png"/>

7. Move json file downloaded to raw folder.
<img src="https://user-images.githubusercontent.com/51151970/193731061-2930d835-ddba-4f85-93ab-8e5bcda2591b.png"/>

8. In your Code

Make simple button for example.

In your xml file:

```xml
<Button
    android:id="@+id/button1"
    android:layout_margin="10dp"
    android:text="Try me!" />
```


**How to set lottie Icon in alert**

In your Activity file:
```kotlin
//lottie icon in alert
 button1.setOnClickListener {
                //1. you have to set type SweetLottieAlertDialog.LOTTIE_ID_TYPE.
                val lsd2 = SweetLottieAlertDialog(this, SweetLottieAlertDialog.LOTTIE_ID_TYPE)
                //2. setLottieImagebyId(your_lottie_res, isLoop(boolean), listner)
                lsd2.setLottieImagebyId(R.raw.lottie_cryingface, true, null)
                lsd2.contentText = "Try Again!"
                lsd2.show()
            }
```

<img src="https://user-images.githubusercontent.com/51151970/193729848-29c1930b-c278-4fe9-bfc5-78807da05984.gif" width="400"/>


If you want set **listner**, 

```kotlin
//lottie icon in alert
button1.setOnClickListener {
                val lsd2 = SweetLottieAlertDialog(this, SweetLottieAlertDialog.LOTTIE_ID_TYPE)
                //isLoop is false!
                lsd2.setLottieImagebyId(R.raw.ok_btn_lottie, false, object : Animator.AnimatorListener {

                    override fun onAnimationStart(animation: Animator) {

                    }

                    override fun onAnimationEnd(animation: Animator) {
                    //this will be executed when lottie animation end.
                        lsd2.dismissWithAnimation()
                    }

                    override fun onAnimationCancel(animation: Animator) {
                    }

                    override fun onAnimationRepeat(animation: Animator) {
                    }
                })
                lsd2.contentText = "Success!"
                lsd2.setCancelable(true)
                lsd2.setCanceledOnTouchOutside(true)
                lsd2.show()
            }
```
<img src="https://user-images.githubusercontent.com/51151970/193729862-e8f6373e-bae1-41cb-b8c9-4c7407ea4cc5.gif" width="400"/>
<br>

**How to set Full screen lottie**

```kotlin
//full screen - Lottie cover alert
 button1.setOnClickListener {
            val lsd = SweetLottieAlertDialog(this, SweetLottieAlertDialog.NORMAL_TYPE)
            //text in alert
            lsd.contentText = "Congratulation!"
            //set full screen lottie. It will cover your alert.  
            //setPopupLottieAnimation(your_lottie_res, isLoop(boolean), elevation);
            lsd.setPopupLottieAnimation(R.raw.bg_lottie, false, 999f)
            lsd.show()
        }
```
<img src="https://user-images.githubusercontent.com/51151970/193729851-65aac580-30ce-4bea-8051-94b4a65b601f.gif" width="270" height="490"/>

 if you want **lottie behind alert**,

```kotlin
//full screen - Lottie behind alert
 button1.setOnClickListener {
            val lsd = SweetLottieAlertDialog(this, SweetLottieAlertDialog.NORMAL_TYPE)
            //text in alert
            lsd.contentText = "Congratulation!"
            //set full screen lottie behind alert.
            //set elevation 0f.                                 v here!
            lsd.setPopupLottieAnimation(R.raw.bg_lottie, false, 0f)
            lsd.show()
        }
```
<img src="https://user-images.githubusercontent.com/51151970/193205958-21d91020-4072-457c-b231-7df427365f9b.jpg" width="250" height="490"/>

## License

    The MIT License (MIT)

    Copyright (c) 2014 Pedant(http://pedant.cn)

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.



