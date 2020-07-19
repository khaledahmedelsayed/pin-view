# PinView - A customizable Pin Code entry view for Android
## This is a ***Kotlin*** library that will integrate a view that can be used as a user login authentication method in your project.

<img src="all_keys_preview.gif">

# Installation

in project's build.gradle , add jitPack.io as following
1.```
	allprojects {
	   repositories {
	      jcenter()
	      maven { url "https://jitpack.io" }
	   }
	}
	```
in module's build.gradle , add the implementation as following
2. ``` implementation 'com.github.khaled2252:pin-view:1.0.0' ```

# Usage

1. Add PinView to your acivity/fragment.xml

```xml
 <com.khaledahmedelsayed.pinview.PinView
        android:id="@+id/pinView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:errorMessageText="Invalid Pin Code"
        app:dotProgressColor="@android:color/holo_purple"
        app:titleName="Enter Pin Code">
    </com.khaledahmedelsayed.pinview.PinView>
```

2. Reference it in your activity/fragment class

    **Kotlin**
    ```kotlin
    pinView.clearPin()
    pinView.deleteLastPin()
    ```
    ### Methods
    `pinView.clearPin()`  -> clears current pin progress
    `pinView.deletePin()` -> deletes last pin code number
    `pinView.showError(true)` -> displays an error message
    `pinView.showError(false)` -> hides the error message
    `pinView.isErrorVisible()` -> returns true if the error message is visible

    ### Listeners
    There are 2 listeners that can be used as following :-

    1. OnCompletedListener() which is called when user enters the 4th pin number
    ```Kotlin
    pinView.setOnCompletedListener = { pinCode ->

            if(pinCode == "1234")
                startActivity(Intent(this,HomeActivity::class.java))

            else
                pinView.showError(true)

            pinView.clearPin()
        }
    ```
    <img src="error_preview.gif">

    2. onPinKeyClickedListener() which is called when user clicks on any key in the pin keyboard (except for the 4th pin number)

    ```kotlin
        pinView.setOnPinKeyClickListener = { keyPressed ->
            Toast.makeText(this,"Key pressed was $keyPressed",Toast.LENGTH_SHORT).show()
        }
      ```
      <img src="key_pressed_preview.gif">

# Customization
    You can customize PinView's attributes using app namespace in xml as following :-

`app:titleTextColor=""`
`app:titleTextSize=""`
`app:dotProgressColor=""`
`app:dotUnProgressColor=""`
`app:dotRadius=""`
`app:numbersTextColor=""`
`app:numbersTextSize=""`
`app:deleteButtonColor=""`
`app:clearButtonColor=""`
`app:errorMessageText=""`
`app:errorMessageTextSize=""`
`app:errorMessageColor=""`

