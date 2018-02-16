# ReColor

An Android library that tries to do cool stuff with colors.

![gif_part1](https://user-images.githubusercontent.com/24822099/34370937-e381c3d4-eadd-11e7-8d1f-12af76a90500.gif)
![gif_part2](https://user-images.githubusercontent.com/24822099/34370991-556ae99e-eade-11e7-9eb1-7ae7772271c6.gif)

# Usage

Add this to your gradle file:

```gradle
    implementation 'com.simmorsal.recolor:recolor:1.1.0'
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
> * getColorIntArray()
> * getColorHEXList()
> * setOnReColorFinish()
>
> experimental:
> * setMenuIconColor()
> ```

#

#### Explanations:

* ##### `setViewBackgroundColor()`:
changes the background color of any `View` you give it:

```java
setViewBackground(view, startingColor, endingColor, duration);

// usage:
ReColor reColor = new ReColor(context);
reColor.setViewBackgroundColor(view, "FFFFFF", "#AA000000", 400);

// or like this:
new ReColor(context).setViewBackgroundColor(view, "#FFFFFF", "AA000000", 400);
```

* ##### `setCardViewColor()`: 
changes `CardView` color:

```java
new ReColor(context).setCardViewColor(cardView, startingColor, endingColor, duration);

// usage:
new ReColor(context).setCardViewColor(cardView, "FFFFFF", "000000", 300);
```

* ##### `setTextViewColor()`: 
changes `TextView` color:

```java
new ReColor(context).setTextViewColor(textView, startingColor, endingColor, duration);

// usage:
new ReColor(context).setTextViewColor(textView, "FFFFFF", "000000", 300);
```

* ##### `setImageButtonColorFilter()`: 
changes `ImageButton`'s color filter:

```java
new ReColor(context).setImageButtonColorFilter(imageButton, startingColor, endingColor, duration);

// usage:
new ReColor(context).setImageButtonColorFilter(imageButton, "FFFFFF", "000000", 300);
```


* ##### `setImageViewColorFilter()`: 
changes `ImageView`'s color filter:

```java
new ReColor(context).setImageViewColorFilter(imageView, startingColor, endingColor, duration);

// usage:
new ReColor(context).setImageViewColorFilter(imageView, "FFFFFF", "000000", 300);
```

* ##### `setStatusBarColor()`: 
changes `Status Bar` color. you can pass `null` as
`startingColor` so it would be automatically retrieved from status bar itself:

```java
new ReColor(context).setStatusBarColor(startingColor, endingColor, duration);

// usage:
new ReColor(context).setStatusBarColor(null, "000000", 300);

// or:

new ReColor(context).setStatusBarColor("FFFFFF", "000000", 300);
```

* ##### `setNavigationBarColor()`: 
changes `Navigation Bar` color. like the status bar,
you can pass `null` as `startingColor` so it would be retrieved automatically:

```java
new ReColor(context).setNavigationBarColor(startingColor, endingColor, duration);

// usage:
new ReColor(context).setNavigationBarColor(null, "000000", 300);

// or

new ReColor(context).setNavigationBarColor("FFFFFF", "000000", 300);
```

* ##### `pulseStatusBar()`: 
this method pulses the status bar color to the `pulseColor`,
`pulseCount` times, each pulse taking `pulseSpeed` milliseconds:

```java
new ReColor(context).pulseStatusBar(pulseColor, pulseSpeed, pulseCount);

//usage:
new ReColor(context).pulseStatusBar("FFFFFF", 100, 4);
```

* ##### `pulseNavigationBar()`: 
just like `pulseStatusBar()`, but on Navigation Bar:

```java
new ReColor(context).pulseNavigationBar(pulseColor, pulseSpeed, palseCount);

//usage:
new ReColor(context).pulseNavigationBar("FFFFFF", 150, 5);
```

* ##### `stop()`: 
this method stops a reColoring on any of the above methods,
and also returns the last color set
by that particular ReColor object, in any of the above methods:

```java
ReColor reColor = new ReColor(context);
reColor.Stop();

// usage:
// Consider a LinearLayout's background being reColored
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

* ##### `getColorHEXList()`: 
returns a `List<String>` of HEX color values between `startingColor`
 and `endingColor` with a List length of `listLength`, so you can use it in your code:

```java
new ReColor(context).getColorHEXList(startingColor, endingColor, listLength);

// usage:
List<String> colorList = new ReColor(context)
    .getColorHEXList("FFFFFF", "000000", 100);
```

* ##### `getColorIntArray()`: 
returns an `int[]` of color-int values between `startingColor`
 and `endingColor` with a List length of `listLength`, so you can use it in your code:

```java
new ReColor(context).getColorIntArray(startingColor, endingColor, listLength);

// usage:
int[] colorList = new ReColor(context)
    .getColorIntArray("FFFFFF", "000000", 100);
```


 * ##### `setOnReColorFinish()`: 
 you can implement this on a ReColor object
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

// or

new ReColor(context)
    .setViewBackgroundColor(view, "FFFFFF", "000000", 1000)
    .setOnReColorFinish(new OnReColorFinish() {
                                    @Override
                                    public void onFinish() {
                                        Log.i(TAG, "reColoring finished");
                                    }
                                });
```

#

#### Experimental methods:

* ##### `setMenuIconColor()`: 
changes `MenuIcon` of a `MenuItem` color:

```java
    new ReColor(context).setMenuIconColor(menuItem, startingColor, endingColor, duration);
```

this method is experimental because while it's reColoring
 a `MenuIcon`, the menu becomes unresponsive to touch. So the best way to use it
 is to give it a duration of 200 or less. and if you're simulating pulsing, wait
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


