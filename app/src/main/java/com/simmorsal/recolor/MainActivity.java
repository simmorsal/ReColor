package com.simmorsal.recolor;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

//     TODO add some comments here

    Activity context;
    LinearLayout rootView, linReColorBackground;
    LinearLayout linRecolorStatusBar, linRecolorNavigationBar, linRecolorBoth;
    LinearLayout linPulseStatusBar, linPulseNavigationBar, linPulseBoth;
    CardView theCardView;
    ImageView imageView;
    TextView textView;

    int imageViewColor = 0;
    int textViewColor = 0;
    boolean isRootViewColorChanged = false;
    boolean isCardColorChanged = false;
    boolean isStatusBarColorChanged = false;
    boolean isNavigationBarColorChanged = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializer();
        onClicks();
        logAListOfColors();
    }

    private void initializer() {
        context = MainActivity.this;
        rootView = (LinearLayout) findViewById(R.id.rootView);
        linReColorBackground = (LinearLayout) findViewById(R.id.linReColorBackground);
        theCardView = (CardView) findViewById(R.id.theCardView);
        linRecolorStatusBar = (LinearLayout) findViewById(R.id.linRecolorStatusBar);
        linRecolorNavigationBar = (LinearLayout) findViewById(R.id.linRecolorNavigationBar);
        linRecolorBoth = (LinearLayout) findViewById(R.id.linRecolorBoth);
        linPulseStatusBar = (LinearLayout) findViewById(R.id.linPulseStatusBar);
        linPulseNavigationBar = (LinearLayout) findViewById(R.id.linPulseNavigationBar);
        linPulseBoth = (LinearLayout) findViewById(R.id.linPulseBoth);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView);
    }

    private void logAListOfColors() {
        List<String> colorList = new ReColor(context).getColorList("E91E63", "1E88E5", 20);
        for (int i = 0; i < colorList.size(); i++) Log.i("Color #" + i, colorList.get(i));
    }

    private void onClicks() {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (imageViewColor) {
                    case 0:
                        new ReColor(context).setImageViewColorFilter(imageView, "ffffff", "388E3C", 300);
                        imageViewColor = 1;
                        break;
                    case 1:
                        new ReColor(context).setImageViewColorFilter(imageView, "388E3C", "00838F", 300);
                        imageViewColor = 2;
                        break;
                    case 2:
                        new ReColor(context).setImageViewColorFilter(imageView, "00838F", "F4511E", 300);
                        imageViewColor = 3;
                        break;
                    case 3:
                        new ReColor(context).setImageViewColorFilter(imageView, "F4511E", "FFFFFF", 300);
                        imageViewColor = 0;
                        break;
                }
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (textViewColor) {
                    case 0:
                        new ReColor(context).setTextColor(textView, "ffffff", "81D4FA", 300);
                        textViewColor = 1;
                        break;
                    case 1:
                        new ReColor(context).setTextColor(textView, "81D4FA", "ef9a9a", 300);
                        textViewColor = 2;
                        break;
                    case 2:
                        new ReColor(context).setTextColor(textView, "ef9a9a", "FFAB91", 300);
                        textViewColor = 3;
                        break;
                    case 3:
                        new ReColor(context).setTextColor(textView, "FFAB91", "ffffff", 300);
                        textViewColor = 0;
                        break;
                }
            }
        });
        // recoloring the background
        linReColorBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRootViewColorChanged) {
                    new ReColor(context).setViewBackgroundColor(rootView, "004D40", "#880E4F", 10000);
                    isRootViewColorChanged = !isRootViewColorChanged;
                } else {
                    new ReColor(context).setViewBackgroundColor(rootView, "880E4F", "#004D40", 10000);
                    isRootViewColorChanged = !isRootViewColorChanged;
                }
            }
        });


        // recoloring the card view, by creating a new object of ReColor class, and implementing a
        // callback on it, to print "It listens" whenever the last color is set on the CardView.
        final ReColor reColorCardView = new ReColor(context);
        reColorCardView.setOnReColorFinish(new OnReColorFinish() {
            @Override
            public void onFinish() {
                Log.i("onReColorFinishCallBack", "It listens");
            }
        });
        theCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCardColorChanged) {
                    reColorCardView.setCardViewColor(theCardView, "1E88E5", "f44336", 3000);
                    isCardColorChanged = !isCardColorChanged;
                } else {
                    reColorCardView.setCardViewColor(theCardView, "f44336", "1E88E5", 3000);
                    isCardColorChanged = !isCardColorChanged;
                }
            }
        });


        linRecolorStatusBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isStatusBarColorChanged) {
                    // if starting color is null, color will be automatically retrieved from status bar
                    // same is true for navigation bar
                    new ReColor(context).setStatusBarColor(null, "880E4F", 2000);
                    isStatusBarColorChanged = !isStatusBarColorChanged;
                } else {
                    new ReColor(context).setStatusBarColor(null, "004D40", 2000);
                    isStatusBarColorChanged = !isStatusBarColorChanged;
                }
            }
        });

        linRecolorNavigationBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isNavigationBarColorChanged) {
                    new ReColor(context).setNavigationBarColor(null, "880E4F", 2000);
                    isNavigationBarColorChanged = !isNavigationBarColorChanged;
                } else {
                    new ReColor(context).setNavigationBarColor(null, "000000", 2000);
                    isNavigationBarColorChanged = !isNavigationBarColorChanged;
                }
            }
        });

        linRecolorBoth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isStatusBarColorChanged) {
                    new ReColor(context).setStatusBarColor(null, "880E4F", 2000);
                    isStatusBarColorChanged = !isStatusBarColorChanged;
                } else {
                    new ReColor(context).setStatusBarColor(null, "004D40", 2000);
                    isStatusBarColorChanged = !isStatusBarColorChanged;
                }

                if (!isNavigationBarColorChanged) {
                    new ReColor(context).setNavigationBarColor(null, "880E4F", 2000);
                    isNavigationBarColorChanged = !isNavigationBarColorChanged;
                } else {
                    new ReColor(context).setNavigationBarColor(null, "000000", 2000);
                    isNavigationBarColorChanged = !isNavigationBarColorChanged;
                }
            }
        });

        linPulseStatusBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ReColor(context).pulseStatusBar("E64A19", 200, 2);
            }
        });

        linPulseNavigationBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ReColor(context).pulseNavigationBar("e64a19", 200, 2);
            }
        });

        linPulseBoth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new ReColor(context).pulseStatusBar("E64A19", 80, 5);
                new ReColor(context).pulseNavigationBar("e64a19", 80, 5);
            }
        });
    }
}
