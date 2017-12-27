package com.simmorsal.recolor_project;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A library that tries to do cool things with colors.
 *
 * @see ReColor#getColorList
 * @see ReColor#setMenuIconColor
 * @see ReColor#setTextViewColor
 * @see ReColor#setImageButtonColorFilter
 * @see ReColor#setImageViewColorFilter
 * @see ReColor#setViewBackgroundColor
 * @see ReColor#setCardViewColor
 * @see ReColor#pulseNavigationBar
 * @see ReColor#pulseStatusBar
 * @see ReColor#setNavigationBarColor
 * @see ReColor#setStatusBarColor
 * @see ReColor#setOnReColorFinish
 * @see ReColor#stop()
 */
public class ReColor {

    private Activity context;
    private final Handler timerHandler = new Handler();
    private OnReColorFinish mOnReColorFinish;
    private View viewBackground;
    private CardView cardViewBackground;
    private ImageView imageView;
    private TextView textView;
    private MenuItem menuItem;
    private Drawable menuItemIcon;
    private ImageButton imageButton;

    private String startingColor, endingColor, lastShownColor;
    private int stepCount;
    private List<String> colorArray;
    private int stepsPassed = 0;

    // default color transition speed in milliseconds
    private int colorChangeSpeed = 16;

    public ReColor(Context context) {
        this.context = (Activity) context;

        // getting the screen's refresh rate and setting color transition based on the speed
        // for smooth animation
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            colorChangeSpeed = (int) (1000 / windowManager.getDefaultDisplay().getRefreshRate());
        }
    }


    /////////////////////////////////////////////////////////
    ////    PUBLIC METHODS
    /////////////////////////////////////////////////////////


    /**
     * This method returns an ArrayList of colors between two colors.
     * The logic behind this is for the colors to be set one after the other every 10ms to
     * visualize a smooth color transition.
     */
    public List<String> getColorList(String startingColor, String endingColor, int listLength) {

        try {
            if (listLength < 3)
                throw new ReColorException("\n \n      length must at least be 3 \n ");
            this.startingColor = getValidColor(startingColor, "start");
            this.endingColor = getValidColor(endingColor, "end");
            if (this.startingColor != null && this.endingColor != null) {
                colorArray = getColorArray(this.startingColor, this.endingColor, listLength - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return colorArray;
    }

    /**
     * this method changes the icon color of a MenuItem.
     */
    public ReColor setMenuIconColor(MenuItem menuItem, String startingColor, String endingColor, int duration) {

        try {
            if (duration < 16)
                throw new ReColorException("\n \n      duration must at least be 16ms \n ");
            this.startingColor = getValidColor(startingColor, "start");
            this.endingColor = getValidColor(endingColor, "end");
            if (this.startingColor != null && this.endingColor != null) {
                this.menuItem = menuItem;
                menuItemIcon = menuItem.getIcon();
                if (menuItemIcon == null)
                    throw new ReColorException("\n \n      menuItem doesn't have an icon \n ");
                stepCount = duration / colorChangeSpeed;
                colorArray = getColorArray(this.startingColor, this.endingColor, stepCount);
                timerHandler.postDelayed(menuIconColorTimerRunnable, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * this changes the color filter on an ImageButton
     */
    public ReColor setImageButtonColorFilter(ImageButton imageButton, String startingColor, String endingColor, int duration) {


        try {
            if (duration < 16)
                throw new ReColorException("\n \n      duration must at least be 16ms \n ");
            this.startingColor = getValidColor(startingColor, "start");
            this.endingColor = getValidColor(endingColor, "end");
            if (this.startingColor != null && this.endingColor != null) {
                this.imageButton = imageButton;
                stepCount = duration / colorChangeSpeed;
                colorArray = getColorArray(this.startingColor, this.endingColor, stepCount);
                timerHandler.postDelayed(imageButtonTimerRunnable, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * this method changes the color of a TextView
     */
    public ReColor setTextViewColor(TextView textView, String startingColor, String endingColor, int duration) {
        try {
            if (duration < 16)
                throw new ReColorException("\n \n      duration must at least be 16ms \n ");
            this.startingColor = getValidColor(startingColor, "start");
            this.endingColor = getValidColor(endingColor, "end");
            if (this.startingColor != null && this.endingColor != null) {
                this.textView = textView;
                stepCount = duration / colorChangeSpeed;
                colorArray = getColorArray(this.startingColor, this.endingColor, stepCount);
                stepsPassed = 0;
                timerHandler.removeCallbacksAndMessages(null);
                timerHandler.postDelayed(textColorTimerRunnable, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * this method changes the color filter of an ImageView
     */
    public ReColor setImageViewColorFilter(ImageView imageView, String startingColor, String endingColor, int duration) {
        try {
            if (duration < 16)
                throw new ReColorException("\n \n      duration must at least be 16ms \n ");
            this.startingColor = getValidColor(startingColor, "start");
            this.endingColor = getValidColor(endingColor, "end");
            if (this.startingColor != null && this.endingColor != null) {
                this.imageView = imageView;
                stepCount = duration / colorChangeSpeed;
                colorArray = getColorArray(this.startingColor, this.endingColor, stepCount);
                stepsPassed = 0;
                timerHandler.removeCallbacksAndMessages(null);
                timerHandler.postDelayed(imageViewColorFilterTimerRunnable, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * This method pulses the Navigation Bar. Useful to grab attention.
     */
    public ReColor pulseNavigationBar(String pulseColor, int pulseSpeed, int pulseCount) {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                startingColor = String.format("#%06X", (0xFFFFFF & context.getWindow().getNavigationBarColor()));
                if (pulseSpeed < 10)
                    throw new ReColorException("\n \n      duration must at least be 16ms \n ");
                this.startingColor = getValidColor(startingColor, "start");
                this.endingColor = getValidColor(pulseColor, "end");
                if (this.startingColor != null && this.endingColor != null) {
                    stepCount = pulseSpeed / colorChangeSpeed;
                    colorArray = getColorArray(this.startingColor, this.endingColor, stepCount);

                    String middleOfColorArray = getValidColor(colorArray.get((int) (colorArray.size() / 2)), "end");
                    List<String> colors;
                    for (int i = 0; i < pulseCount; i++) {
                        colors = getColorArray(this.endingColor, middleOfColorArray, stepCount);
                        for (String s : colors) colorArray.add(s);
                        colors = getColorArray(middleOfColorArray, this.endingColor, stepCount);
                        for (String s : colors) colorArray.add(s);
                    }

                    colors = getColorArray(this.endingColor, this.startingColor, (int) (stepCount * 2));
                    for (String s : colors) colorArray.add(s);

                    stepsPassed = 0;
                    timerHandler.removeCallbacksAndMessages(null);
                    timerHandler.postDelayed(navigationBarColorTimerRunnable, 0);
                }
            } else throw new ReColorException("\n \n      only LOLLIPOP and up \n ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * This method pulses the Status Bar. Useful to grab attention.
     */
    public ReColor pulseStatusBar(String pulseColor, int pulseSpeed, int pulseCount) {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                startingColor = String.format("#%06X", (0xFFFFFF & context.getWindow().getStatusBarColor()));
                if (pulseSpeed < 10)
                    throw new ReColorException("\n \n      duration must at least be 16ms \n ");
                this.startingColor = getValidColor(startingColor, "start");
                this.endingColor = getValidColor(pulseColor, "end");
                if (this.startingColor != null && this.endingColor != null) {
                    stepCount = pulseSpeed / colorChangeSpeed;
                    colorArray = getColorArray(this.startingColor, this.endingColor, stepCount);

                    String middleOfColorArray = getValidColor(colorArray.get((int) (colorArray.size() / 2)), "end");
                    List<String> colors;
                    for (int i = 0; i < pulseCount; i++) {
                        colors = getColorArray(this.endingColor, middleOfColorArray, stepCount);
                        for (String s : colors) colorArray.add(s);
                        colors = getColorArray(middleOfColorArray, this.endingColor, stepCount);
                        for (String s : colors) colorArray.add(s);
                    }

                    colors = getColorArray(this.endingColor, this.startingColor, (int) (stepCount * 2));
                    for (String s : colors) colorArray.add(s);

                    stepsPassed = 0;
                    timerHandler.removeCallbacksAndMessages(null);
                    timerHandler.postDelayed(statusBarColorTimerRunnable, 0);
                }
            } else throw new ReColorException("\n \n      only LOLLIPOP and up \n ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * This method changes the Navigation Bar Color.
     *
     * @param startingColor can be null, if so, color will be automatically retrieved
     */
    public ReColor setNavigationBarColor(String startingColor, String endingColor, int duration) {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                if (startingColor == null)
                    startingColor = String.format("#%06X", (0xFFFFFF & context.getWindow().getNavigationBarColor()));

                if (duration < 16)
                    throw new ReColorException("\n \n      duration must at least be 16ms \n ");
                this.startingColor = getValidColor(startingColor, "start");
                this.endingColor = getValidColor(endingColor, "end");
                if (this.startingColor != null && this.endingColor != null) {
                    this.context = context;
                    stepCount = duration / colorChangeSpeed;
                    colorArray = getColorArray(this.startingColor, this.endingColor, stepCount);
                    stepsPassed = 0;
                    timerHandler.removeCallbacksAndMessages(null);
                    timerHandler.postDelayed(navigationBarColorTimerRunnable, 0);
                }
            } else throw new ReColorException("\n \n      only LOLLIPOP and up \n ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * This method changes the Status Bar Color.
     *
     * @param startingColor can be null, if so, color will be automatically retrieved
     */
    public ReColor setStatusBarColor(String startingColor, String endingColor, int duration) {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                if (startingColor == null)
                    startingColor = String.format("#%06X", (0xFFFFFF & context.getWindow().getStatusBarColor()));

                if (duration < 16)
                    throw new ReColorException("\n \n      duration must at least be 16ms \n ");
                this.startingColor = getValidColor(startingColor, "start");
                this.endingColor = getValidColor(endingColor, "end");
                if (this.startingColor != null && this.endingColor != null) {
                    this.context = context;
                    stepCount = duration / colorChangeSpeed;
                    colorArray = getColorArray(this.startingColor, this.endingColor, stepCount);
                    stepsPassed = 0;
                    timerHandler.removeCallbacksAndMessages(null);
                    timerHandler.postDelayed(statusBarColorTimerRunnable, 0);
                }
            } else throw new ReColorException("\n \n      only LOLLIPOP and up \n ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this;
    }

    /**
     * This method changes the background color of a View. This can be used for any View
     */
    public ReColor setViewBackgroundColor(View view, String startingColor, String endingColor, int duration) {
        try {
            if (duration < 16)
                throw new ReColorException("\n \n      duration must at least be 16ms \n ");
            this.startingColor = getValidColor(startingColor, "start");
            this.endingColor = getValidColor(endingColor, "end");
            if (this.startingColor != null && this.endingColor != null) {
                viewBackground = view;
                stepCount = duration / colorChangeSpeed;
                colorArray = getColorArray(this.startingColor, this.endingColor, stepCount);
                stepsPassed = 0;
                timerHandler.removeCallbacksAndMessages(null);
                timerHandler.postDelayed(viewBackgroundTimerRunnable, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * This method changes the background color of a CardView.
     */
    public ReColor setCardViewColor(CardView cardView, String startingColor, String endingColor, int duration) {

        try {
            if (duration < 16)
                throw new ReColorException("\n \n      duration must at least be 10 \n ");
            this.startingColor = getValidColor(startingColor, "start");
            this.endingColor = getValidColor(endingColor, "end");
            if (this.startingColor != null && this.endingColor != null) {
                cardViewBackground = cardView;
                stepCount = duration / colorChangeSpeed;
                colorArray = getColorArray(this.startingColor, this.endingColor, stepCount);
                stepsPassed = 0;
                timerHandler.removeCallbacksAndMessages(null);
                timerHandler.postDelayed(cardViewBackgroundTimerRunnable, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * When used on a ReColor() object, it stops the ongoing color transition, and returns
     * the last color that is set on the View, CardView, Status bar, or NavigationBar,
     * that the object tried to change the color of.
     *
     * @return String lastSetColor
     */
    public String stop() {
        timerHandler.removeCallbacksAndMessages(null);
        return lastShownColor;
    }

    /**
     * Used for setting a callback on a ReColor() object, for when color transition is finished.
     *
     * @param l (new OnReColorFinish)
     */
    public void setOnReColorFinish(OnReColorFinish l) {
        mOnReColorFinish = l;
    }

    /////////////////////////////////////////////////////////
    ////    RUNNABLE
    /////////////////////////////////////////////////////////

    private Runnable menuIconColorTimerRunnable = new Runnable() {

        @Override
        public void run() {

            stepsPassed++;
            try {
                lastShownColor = colorArray.get(stepsPassed);
                menuItemIcon.setColorFilter(Color.parseColor(lastShownColor), PorterDuff.Mode.SRC_IN);
                menuItem.setIcon(menuItemIcon);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

            timerHandler.postDelayed(this, 10);
            if (stepsPassed == colorArray.size() - 1) {
                if (mOnReColorFinish != null) mOnReColorFinish.onFinish();
                timerHandler.removeCallbacksAndMessages(null);
            }
        }
    };
    private Runnable imageButtonTimerRunnable = new Runnable() {

        @Override
        public void run() {

            stepsPassed++;
            try {
                lastShownColor = colorArray.get(stepsPassed);
                imageButton.setColorFilter(Color.parseColor(lastShownColor));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

            timerHandler.postDelayed(this, 10);
            if (stepsPassed == colorArray.size() - 1) {
                if (mOnReColorFinish != null) mOnReColorFinish.onFinish();
                timerHandler.removeCallbacksAndMessages(null);
            }
        }
    };
    private Runnable navigationBarColorTimerRunnable = new Runnable() {

        @Override
        public void run() {

            stepsPassed++;
            try {
                lastShownColor = colorArray.get(stepsPassed);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    context.getWindow().setNavigationBarColor(Color.parseColor(lastShownColor));
                } else timerHandler.removeCallbacksAndMessages(null);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

            timerHandler.postDelayed(this, colorChangeSpeed);
            if (stepsPassed == colorArray.size() - 1) {
                if (mOnReColorFinish != null) mOnReColorFinish.onFinish();
                timerHandler.removeCallbacksAndMessages(null);
            }
        }
    };
    private Runnable statusBarColorTimerRunnable = new Runnable() {

        @Override
        public void run() {

            stepsPassed++;
            try {
                lastShownColor = colorArray.get(stepsPassed);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    context.getWindow().setStatusBarColor(Color.parseColor(lastShownColor));
                } else timerHandler.removeCallbacksAndMessages(null);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

            timerHandler.postDelayed(this, colorChangeSpeed);
            if (stepsPassed == colorArray.size() - 1) {
                if (mOnReColorFinish != null) mOnReColorFinish.onFinish();
                timerHandler.removeCallbacksAndMessages(null);
            }
        }
    };

    private Runnable viewBackgroundTimerRunnable = new Runnable() {

        @Override
        public void run() {

            stepsPassed++;
            try {
                lastShownColor = colorArray.get(stepsPassed);
                viewBackground.setBackgroundColor(Color.parseColor(lastShownColor));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

            timerHandler.postDelayed(this, colorChangeSpeed);
            if (stepsPassed == colorArray.size() - 1) {
                if (mOnReColorFinish != null) mOnReColorFinish.onFinish();
                timerHandler.removeCallbacksAndMessages(null);
            }
        }
    };

    private Runnable imageViewColorFilterTimerRunnable = new Runnable() {

        @Override
        public void run() {

            stepsPassed++;
            try {
                lastShownColor = colorArray.get(stepsPassed);
                imageView.setColorFilter(Color.parseColor(lastShownColor));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

            timerHandler.postDelayed(this, colorChangeSpeed);
            if (stepsPassed == colorArray.size() - 1) {
                if (mOnReColorFinish != null) mOnReColorFinish.onFinish();
                timerHandler.removeCallbacksAndMessages(null);
            }
        }
    };
    private Runnable textColorTimerRunnable = new Runnable() {

        @Override
        public void run() {

            stepsPassed++;
            try {
                lastShownColor = colorArray.get(stepsPassed);
                textView.setTextColor(Color.parseColor(lastShownColor));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

            timerHandler.postDelayed(this, colorChangeSpeed);
            if (stepsPassed == colorArray.size() - 1) {
                if (mOnReColorFinish != null) mOnReColorFinish.onFinish();
                timerHandler.removeCallbacksAndMessages(null);
            }
        }
    };


    private Runnable cardViewBackgroundTimerRunnable = new Runnable() {
        @Override
        public void run() {

            stepsPassed++;

            try {
                lastShownColor = colorArray.get(stepsPassed);
                cardViewBackground.setCardBackgroundColor(Color.parseColor(lastShownColor));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }

//            Log.i(stepsPassed + "", colorArray.get(stepsPassed));

            timerHandler.postDelayed(this, colorChangeSpeed);
            if (stepsPassed == colorArray.size() - 1) {
                if (mOnReColorFinish != null) mOnReColorFinish.onFinish();
                timerHandler.removeCallbacksAndMessages(null);
            }
        }
    };


    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////////////////
    private List<String> getColorArray(String startingColor, String endingColor, int stepCount) {
        List<String> colorArray = new ArrayList<>();
        List<String> startColorArray = getStringParts(startingColor);
        List<String> endColorArray = getStringParts(endingColor);

        int[] startColorInIntArray = new int[4];
        int[] endColorInIntArray = new int[4];

        //Converting hex to decimal
        startColorInIntArray[0] = Integer.parseInt(startColorArray.get(0), 16);
        startColorInIntArray[1] = Integer.parseInt(startColorArray.get(1), 16);
        startColorInIntArray[2] = Integer.parseInt(startColorArray.get(2), 16);
        startColorInIntArray[3] = Integer.parseInt(startColorArray.get(3), 16);
        endColorInIntArray[0] = Integer.parseInt(endColorArray.get(0), 16);
        endColorInIntArray[1] = Integer.parseInt(endColorArray.get(1), 16);
        endColorInIntArray[2] = Integer.parseInt(endColorArray.get(2), 16);
        endColorInIntArray[3] = Integer.parseInt(endColorArray.get(3), 16);

        float[] stepSize = new float[4];
        stepSize[0] = (endColorInIntArray[0] - startColorInIntArray[0]) / (float) stepCount;
        stepSize[1] = (endColorInIntArray[1] - startColorInIntArray[1]) / (float) stepCount;
        stepSize[2] = (endColorInIntArray[2] - startColorInIntArray[2]) / (float) stepCount;
        stepSize[3] = (endColorInIntArray[3] - startColorInIntArray[3]) / (float) stepCount;


        //////////////////////
        /// CREATING LIST
        colorArray.add("#" + startingColor);

        for (int i = 1; i < stepCount; i++) {

            int c1 = (int) (startColorInIntArray[0] + (stepSize[0] * i));
            int c2 = (int) (startColorInIntArray[1] + (stepSize[1] * i));
            int c3 = (int) (startColorInIntArray[2] + (stepSize[2] * i));
            int c4 = (int) (startColorInIntArray[3] + (stepSize[3] * i));

            // checking for integers out of bound
            if (c1 > 255) c1 = 255;
            else if (c1 < 0) c1 = 0;
            if (c2 > 255) c2 = 255;
            else if (c2 < 0) c2 = 0;
            if (c3 > 255) c3 = 255;
            else if (c3 < 0) c3 = 0;
            if (c4 > 255) c4 = 255;
            else if (c4 < 0) c4 = 0;

            // turning into hex
            String color1 = Integer.toHexString(c1);
            String color2 = Integer.toHexString(c2);
            String color3 = Integer.toHexString(c3);
            String color4 = Integer.toHexString(c4);

            // turning possible one character to two
            if (color1.length() == 1) color1 = "0" + color1;
            if (color2.length() == 1) color2 = "0" + color2;
            if (color3.length() == 1) color3 = "0" + color3;
            if (color4.length() == 1) color4 = "0" + color4;

            colorArray.add("#" + color1 + color2 + color3 + color4);
        }

        colorArray.add("#" + endingColor);

        return colorArray;
    }


    private static List<String> getStringParts(String string) {
        int partitionSize = 2;
        List<String> parts = new ArrayList<>();
        int len = string.length();
        for (int i = 0; i < len; i += partitionSize) {
            parts.add(string.substring(i, Math.min(len, i + partitionSize)));
        }
        return parts;
    }


    private String getValidColor(String color, String s) {
        if (color.length() >= 6 && color.length() <= 9) {

            if (color.length() == 6 && !color.contains("#")) {
                try {
                    Color.parseColor("#" + color);
                } catch (IllegalArgumentException e) {
                    throwException(s);
                    return null;
                }
                return "FF" + color;
            } else if (color.length() == 7 && color.startsWith("#")) {

                try {
                    Color.parseColor(color);
                } catch (IllegalArgumentException e) {
                    throwException(s);
                    return null;
                }
                return "FF" + color.replace("#", "");
            } else if (color.length() == 8 && !color.contains("#")) {
                try {
                    Color.parseColor("#" + color);
                } catch (IllegalArgumentException e) {
                    throwException(s);
                    return null;
                }
                return color;
            } else if (color.length() == 9 && color.startsWith("#")) {
                try {
                    Color.parseColor(color);
                } catch (IllegalArgumentException e) {
                    throwException(s);
                    return null;
                }
                return color.replace("#", "");
            } else {
                throwException(s);
            }

        } else {
            throwException(s);
        }

        return null;
    }

    private void throwException(String s) {
        try {
            if (s.equals("start"))
                throw new ReColorException("\n \n      starting color is invalid \n ");
            else if (s.equals("end"))
                throw new ReColorException("\n \n      ending color is invalid \n ");
        } catch (ReColorException e) {
            e.printStackTrace();
        }
    }
}


class ReColorException extends Exception {
    ReColorException(String message) {
        super(message);
    }
}

