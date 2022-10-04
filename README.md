# SweetLottieAlertDialog :sparkles:
:sparkles: SweetAlertDialog with lottie! :sparkles:

SweetAlert for Android, a beautiful and clever alert dialog with lottie animation effect!

Origin repo : https://github.com/pedant/sweet-alert-dialog

![ezgif-2-374eab3d3c](https://user-images.githubusercontent.com/51151970/193203319-2942fc6a-e401-41cb-a39f-69ffbe1599a1.gif)

## feature
You can setup Lottie animation in 2parts.

|NO|Explanation|Example|
|----|:----:|:----:|
|**1. Icon in alert**|<img src="https://user-images.githubusercontent.com/51151970/193205966-136c656d-c1a3-4946-9392-39412e145a8a.jpg" width="250" height="490"/>|**loop**<br><img src="https://user-images.githubusercontent.com/51151970/193729848-29c1930b-c278-4fe9-bfc5-78807da05984.gif" width="400"/><br>**no loop**<br><img src="https://user-images.githubusercontent.com/51151970/193729862-e8f6373e-bae1-41cb-b8c9-4c7407ea4cc5.gif" width="400"/>|
|**2. Full Screen**|<img src="https://user-images.githubusercontent.com/51151970/193205964-0ff567d0-d0a6-4cb0-8373-338814ee9420.jpg" width="250" height="490"/><br><img src="https://user-images.githubusercontent.com/51151970/193205958-21d91020-4072-457c-b231-7df427365f9b.jpg" width="250" height="490"/>|**Lottie behind alert**<br><img src="https://user-images.githubusercontent.com/51151970/193729856-366f9296-ab5a-46e2-9d4d-50fd0876013d.gif" width="270" height="490"/><br>**Lottie cover alert**<br><img src="https://user-images.githubusercontent.com/51151970/193729851-65aac580-30ce-4bea-8051-94b4a65b601f.gif" width="270" height="490"/>|
 

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

1. https://lottiefiles.com/ 에 접속합니다.

2. 원하는 lottie 검색

3. lottie json 파일 다운로드
<img src="https://user-images.githubusercontent.com/51151970/193731050-82994876-42df-4df8-b894-b8af88a28e4e.PNG" width="500"/>

4. (옵션)다운로드한 파일 이름을 변경합니다.
<img src="https://user-images.githubusercontent.com/51151970/193731053-171cd923-b24b-4da4-9812-db5b0c117c4c.PNG" width="500"/>
<img src="https://user-images.githubusercontent.com/51151970/193731055-c9ef44f9-0795-4928-b648-94b988b5caa4.PNG" width="500"/>

5. res -> New -> Android Resource Directory 를 클릭합니다.
<img src="https://user-images.githubusercontent.com/51151970/193731056-c87135fc-a180-4cce-94a7-4d5a79739d50.png"/>

6. Resource type -> raw 선택하여 raw폴더를 만듭니다.
<img src="https://user-images.githubusercontent.com/51151970/193731058-12397a99-6a52-4062-b63d-cdbc27cd7555.png"/>

7. 다운로드 받았던 json 파일을 raw폴더로 이동합니다.
<img src="https://user-images.githubusercontent.com/51151970/193731061-2930d835-ddba-4f85-93ab-8e5bcda2591b.png"/>

8. 코드 입력

In your xml file:
```xml
<Button
    android:id="@+id/button1"
    android:layout_margin="10dp"
    android:text="Try me!" />
```

In your Activity file:
```kotlin
 button1.setOnClickListener {
            val lsd = SweetLottieAlertDialog(this, SweetLottieAlertDialog.NORMAL_TYPE)
            //text in alert
            lsd.contentText = "Congratulation!"
            //set full screen lottie.  
            //setPopupLottieAnimation(your_lottie_res, isLoop(boolean), elevation);
            lsd.setPopupLottieAnimation(R.raw.bg_lottie, false, 999f)
            lsd.show()
        }
```

lottie will cover your alert. if you want lottie behind alert,

```kotlin
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
