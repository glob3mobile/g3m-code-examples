

package com.glob3mobile.client;


import org.glob3.mobile.specific.G3MBuilder_WebGL;
import org.glob3.mobile.specific.G3MWidget_WebGL;

import com.glob3mobile.sldemo.shared.SLBuilder;
import com.glob3mobile.sldemo.shared.SLListener;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class SLDemoWebGL
         implements
            EntryPoint,
            SLListener {

   private final String    _g3mWidgetHolderId = "g3mWidgetHolder";
   private G3MWidget_WebGL _widget            = null;


   /**
    * This is the entry point method.
    */
   @Override
   public void onModuleLoad() {
      // initialize a default widget by using a builder
      initDefaultWithBuilder();
      final Panel g3mWidgetHolder = RootPanel.get(_g3mWidgetHolderId);


      g3mWidgetHolder.add(_widget);


   }


   public void initDefaultWithBuilder() {
      final G3MBuilder_WebGL builder = new G3MBuilder_WebGL();
      SLBuilder.buildSL(builder, this, SLBuilder.Platform.WEB_GL);
      _widget = builder.createWidget();


      final Button changeLayer = new Button("Change Base Layer");
      changeLayer.setStyleName("buttonChangeLayer");
      changeLayer.addClickHandler(new ClickHandler() {

         @Override
         public void onClick(final ClickEvent event) {
            SLBuilder.changeBaseLayer();
         }
      });

      RootPanel.get().add(changeLayer);

   }


   @Override
   public void scenarioInitialized() {
      RootPanel.get("splash").getElement().getStyle().setProperty("visibility", "hidden");
   }
}
