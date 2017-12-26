# ReColor

An Android library that tries to do cool stuff with colors.

 ---- image here ----

# Usage

Add this to your gradle file:

```gradle
    implementation 'com.simmorsal.recolor:recolor:1.0.0'
```


### methods

>``` java
> stable:
> * setViewBackgroundColor()
> * setCardViewColor()
> * setTextViewColor()
> * setImageButtonColorFilter()
> * setImageViewColorFilter()
> * setStatusBarColor()
> * setNavigationBarColor()
> * pulseStatusBar()
> * pulseNavigationBar()
> * stop()
> * getColorList()
> * setOnReColorFinish()
>
> experimental:
> * setMenuIconColor()
> ```

#

#### Explanations:

* `setViewBackgroundColor()`:
changes the background color of any `View` you give it:

```java
setViewBackground(view, startingColor, endingColor, duration);

// usage:
ReColor reColor = new ReColor(context);
reColor.setViewBackgroundColor(view, "FFFFFF", "#AA000000", 400);

// or like this:
new ReColor(context).setViewBackgroundColor(view, "#FFFFFF", "AA000000", 400);
```

* `setCardViewColor()`: changes `CardView` color:

```java
new ReColor(context).setCardViewColor(cardView, "FFFFFF", "000000", 300);
```

* `setTextViewColor()`: changes `TextView` color:

```java
new ReColor(context).setTextViewColor(textView, "FFFFFF", "000000", 300);
```

* `setImageButtonColorFilter()`: changes `ImageButton`'s color filter:

```java
new ReColor(context).setImageButtonColorFilter(imageButton, "FFFFFF", "000000", 300);
```


* `setImageViewColorFilter()`: changes `ImageView`'s color filter:

```java
new ReColor(context).setImageViewColorFilter(imageView, "FFFFFF", "000000", 300);
```

* `setStatusBarColor()`: changes `Status Bar` color. you can pass `null` as
`startingColor` so it would be automatically retrieved from status bar itself:

```java
new ReColor(context).setStatusBarColor(null, "000000", 300);

// or:

new ReColor(context).setStatusBarColor("FFFFFF", "000000", 300);
```

* `setNavigationBarColor()`: changes `Navigation Bar` color. like the status bar,
you can pass `null` as `startingColor` so it would be retrieved automatically:

```java
new ReColor(context).setNavigationBarColor(null, "000000", 300);

// or

new ReColor(context).setNavigationBarColor("FFFFFF", "000000", 300);
```

* `pulseStatusBar()`: this method pulses the status bar color to the `pulseColor`,
`pulseCount` times, each pulse taking `pulseSpeed` milliseconds:

```java
new ReColor(context).pulseStatusBar(pulseColor, pulseSpeed, pulseCount);

//usage:
new ReColor(context).pulseStatusBar("FFFFFF", 100, 4);
```

* `pulseNavigationBar()`: just like `pulseStatusBar()`, but on Navigation Bar:

```java
new ReColor(context).pulseNavigationBar(pulseColor, pulseSpeed, palseCount);

//usage:
new ReColor(context).pulseNavigationBar("FFFFFF", 150, 5);
```

* `stop()`: this method stops a reColoring on any of the above methods,
and also returns the last color set
by that particular ReColor object, in any of the above methods:

```java
// For example consider a LinearLayout's background being reColored
ReColor reColor = new ReColor(context)
    .setViewBackgroundColor(linearLayout, "FFFFFF", "000000", 2000);

// now you want to stop reColoring when a button is clicked
// (i also store the returned value)
@OnClick{
    String lastColor = reColor.stop();

    // now i want to use the returned color to start reColoring to a new color
    reColor.setViewBackgroundColor(linearLayout, lastColor, "9C27B0", 500);
}
```

* `getColorList()`: returns a `List<String>` of colors between `startingColor`
 and `endingColor` with a List length of `listLength`, so you can use it in your code:

```java
new ReColor(context).getColorList(startingColor, endingColor, listLength);

// usage:
List<String> colorList = new ReColor(context)
    .getColorList("FFFFFF", "000000", 100);
```


 * `setOnReColorFinish()`: you can implement this on a ReColor object
 to get notified when reColoring finishes:

```java
ReColor reColor = new ReColor(context);
reColor.setViewBackgroundColor(view, "FFFFFF", "000000", 1000);
reColor.setOnReColorFinish(new OnReColorFinish() {
                                    @Override
                                    public void onFinish() {
                                        Log.i(TAG, "reColoring finished");
                                    }
                                });
```

#

#### Experimental methods:

* `setMenuIconColor()`: changes `MenuIcon` of a `MenuItem` color:

```java
    new ReColor(context).setMenuIconColor(menuItem, startingColor, endingColor, duration);
```

this method is experimental because while it's reColoring
 a `MenuIcon`, the menu becomes unresponsive to touch. So the best way to use it
 is to give it a duration of 100 or less. and if you're simulating pulsing, wait
 a bit before the next pulse begins. this is my implementation of pulsing:

```java
MenuItem menuItem = navigationBarMenu.getItem(0);
final String startColor = "c60055", endColor = "40c4ff";

Handler handler = new Handler().post(new Runnable() {
             @Override
             public void run() {

                 new ReColor().setMenuIconColor(menuItem, startColor, endColor, 100)
                         .setOnReColorFinish(new OnReColorFinish() {
                             @Override
                             public void onFinish() {

                                 new ReColor().setMenuIconColor(menuItem, endColor, startColor, 100)
                                         .setOnReColorFinish(new OnReColorFinish() {
                                             @Override
                                             public void onFinish() {
                                                 handler.postDelayed(this, 1500);
                                             }
                                         });
                             }
                         });
             }
         };
}
```





## Important notes

* **ONLY** reColor one widget per object of ReColor class.
In other words, **DO NOT** create one object of ReColor class and use it to
reColor multiple widgets - at least at the same time.

* you can pass color values in any of the following formats:
`"RRGGBB"` - `"AARRGGBB"` - `"#RRGGBB"` - `"#AARRGGBB"`


## Thanks
Special thanks to
[Alireza Rajei](https://www.linkedin.com/comm/in/alireza-rajei-414b99142?midToken=AQGDUmCR2BblHg&trk=eml-email_pymk_01-header-108-profile&trkEmail=eml-email_pymk_01-header-108-profile-null-9mn0gg~jbfnzkei~g3-null-neptune%2Fprofile~vanity%2Eview&lipi=urn%3Ali%3Apage%3Aemail_email_pymk_01%3BnXVdn9X8QZyr210NjJLivg%3D%3D)
who thought me all about android and put me in the right direction.




## Donations



> My bitcoin address: 15NkTxJMdZinU2rUnENfjt62uHtxXCcpEU

> ![btc_address](https://user-images.githubusercontent.com/24822099/33515691-ec31ec7c-d77b-11e7-8b72-9e2060894859.png)
