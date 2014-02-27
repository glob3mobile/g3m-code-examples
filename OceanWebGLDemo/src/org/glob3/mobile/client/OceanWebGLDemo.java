

package org.glob3.mobile.client;

import org.glob3.mobile.generated.GEORenderer;
import org.glob3.mobile.generated.LayerSet;
import org.glob3.mobile.generated.LevelTileCondition;
import org.glob3.mobile.generated.Planet;
import org.glob3.mobile.generated.Quality;
import org.glob3.mobile.generated.Sector;
import org.glob3.mobile.generated.TimeInterval;
import org.glob3.mobile.generated.URL;
import org.glob3.mobile.generated.URLTemplateLayer;
import org.glob3.mobile.generated.WMSLayer;
import org.glob3.mobile.generated.WMSServerVersion;
import org.glob3.mobile.specific.G3MBuilder_WebGL;
import org.glob3.mobile.specific.G3MWidget_WebGL;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;


public class OceanWebGLDemo
         implements
            EntryPoint {

   private final String    _g3mWidgetHolderId = "g3mWidgetHolder";
   private G3MWidget_WebGL _widget            = null;
   private GEORenderer     _vectorialRenderer;


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
      builder.setPlanet(Planet.createSphericalEarth());
      builder.getPlanetRendererBuilder().setQuality(Quality.QUALITY_LOW);
      final LayerSet layerSet = new LayerSet();

      //  final String pathHurricanes = "http://ocean.glob3mobile.com/assets/";

      // _vectorialRenderer = builder.createGEORenderer(Symbology.linesDrainSymbolizer);
      //      _vectorialRenderer.loadBSON(new URL(pathHurricanes + "last20.bson"));
      //       _vectorialRenderer.loadJSON(new URL(pathHurricanes + "2012l.geojson"));
      //       _vectorialRenderer.loadJSON(new URL(pathHurricanes + "2012p.geojson"));
      //      _vectorialRenderer.loadJSON(new URL(pathHurricanes + "2013l.geojson"));
      //      _vectorialRenderer.loadJSON(new URL(pathHurricanes + "2013p.geojson"));


      final WMSLayer gmrt = new WMSLayer("topo", new URL("http://www.marine-geo.org/exe/mapserv?", false),
               WMSServerVersion.WMS_1_1_0, Sector.fullSphere(), "image/jpeg", "EPSG:4326", "", false, new LevelTileCondition(0,
                        18), TimeInterval.fromDays(30), true);
      gmrt.setExtraParameter("http://www.marine-geo.org/exe/mapserv&map=/public/mgg/web/www.marine-geo.org/htdocs/services/ogc/wms.map");
      gmrt.setEnable(true);
      layerSet.addLayer(gmrt);

      //      final WMSLayer heatMap = new WMSLayer("IGO:storm-points", new URL("http://igosoftware.dyndns.org:8014/geoserver/IGO/wms?",
      //               false), WMSServerVersion.WMS_1_1_0, Sector.fullSphere(), "image/png", "EPSG:4326", "", true,
      //               new LevelTileCondition(0, 18), TimeInterval.fromDays(30), true);


      final URLTemplateLayer heatMap = URLTemplateLayer.newWGS84(
               "http://igosoftware.dyndns.org:8014/geoserver/gwc/service/tms/1.0.0/IGO:storm-points/{level}/{x}/{y2}.png",
               Sector.fullSphere(), true, 2, 18, TimeInterval.fromDays(30), true, new LevelTileCondition(0, 18));

      //      final URLTemplateLayer heatMap = URLTemplateLayer.newWGS84(
      //               "http://igosoftware.dyndns.org:8014/geoserver/gwc/service/tms/1.0.0/IGO:storm-points-heat-map/{level}/{x}/{y2}.png",
      //               Sector.fullSphere(), true, 2, 18, TimeInterval.fromDays(30), true, new LevelTileCondition(0, 18));


      layerSet.addLayer(heatMap);
      builder.getPlanetRendererBuilder().setLayerSet(layerSet);


      _widget = builder.createWidget();
   }
}
