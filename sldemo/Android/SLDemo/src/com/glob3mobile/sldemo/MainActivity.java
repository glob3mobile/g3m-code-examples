

package com.glob3mobile.sldemo;

import org.glob3.mobile.generated.ILogger;
import org.glob3.mobile.specific.G3MBuilder_Android;
import org.glob3.mobile.specific.G3MWidget_Android;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.glob3mobile.sldemo.shared.SLBuilder;
import com.glob3mobile.sldemo.shared.SLListener;


public class MainActivity
         extends
            Activity
         implements
            SLListener {

   private G3MWidget_Android _g3mWidget;
   private RelativeLayout    _placeHolder;
   private Button            _changeLayer;


   @Override
   protected void onCreate(final Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      final G3MBuilder_Android builder = new G3MBuilder_Android(this);
      SLBuilder.buildSL(builder, this, SLBuilder.Platform.ANDROID);
      _g3mWidget = builder.createWidget();
      _placeHolder = (RelativeLayout) findViewById(R.id.g3mWidgetHolder);

      _changeLayer = (Button) findViewById(R.id.changeBaseLayer);
      _changeLayer.setOnClickListener(new OnClickListener() {

         @Override
         public void onClick(final View v) {
            _changeLayer.setBackgroundColor(getResources().getColor(R.color.bgbuttonclick));
            SLBuilder.changeBaseLayer();

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
               @Override
               public void run() {
                  _changeLayer.setBackgroundColor(getResources().getColor(R.color.bgbutton));
               }
            }, 200);


         }
      });

      _changeLayer.bringToFront();


      _placeHolder.addView(_g3mWidget);
   }


   @Override
   public void scenarioInitialized() {
      ILogger.instance().logInfo("The scenario has been initialized");

      runOnUiThread(new Runnable() {

         @Override
         public void run() {
            _placeHolder.setVisibility(View.VISIBLE);
            _changeLayer.setVisibility(View.VISIBLE);

            ((RelativeLayout) findViewById(R.id.g3mSplashHolder)).setVisibility(View.INVISIBLE);
         }
      });

   }


}
