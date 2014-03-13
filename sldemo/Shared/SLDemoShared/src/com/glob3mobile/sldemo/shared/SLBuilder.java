

package com.glob3mobile.sldemo.shared;

import org.glob3.mobile.generated.AltitudeMode;
import org.glob3.mobile.generated.Angle;
import org.glob3.mobile.generated.ElevationDataProvider;
import org.glob3.mobile.generated.Geodetic2D;
import org.glob3.mobile.generated.Geodetic3D;
import org.glob3.mobile.generated.IG3MBuilder;
import org.glob3.mobile.generated.ILogger;
import org.glob3.mobile.generated.LayerSet;
import org.glob3.mobile.generated.LevelTileCondition;
import org.glob3.mobile.generated.MapBoxLayer;
import org.glob3.mobile.generated.SGShape;
import org.glob3.mobile.generated.Sector;
import org.glob3.mobile.generated.ShapeLoadListener;
import org.glob3.mobile.generated.ShapesRenderer;
import org.glob3.mobile.generated.SingleBilElevationDataProvider;
import org.glob3.mobile.generated.TimeInterval;
import org.glob3.mobile.generated.URL;
import org.glob3.mobile.generated.URLTemplateLayer;
import org.glob3.mobile.generated.Vector2I;
import org.glob3.mobile.generated.WMSLayer;
import org.glob3.mobile.generated.WMSServerVersion;


public class SLBuilder {

   private static String     androidBaseURL = "file:///";
   private static String     webglBaseURL   = "http://wb.glob3mobile.com/assets/";
   private static String     _baseURL;
   private static SLListener _listener;
   private static LayerSet   _layerset;
   private static int        _layerActive   = 0;


   private SLBuilder() {

   }

   public static enum Platform {
      ANDROID,
      WEB_GL,
   }


   public static void buildSL(final IG3MBuilder builder,
                              final SLListener listener,
                              final Platform platform) {

      _listener = listener;

      if (platform.equals(Platform.ANDROID)) {
         _baseURL = androidBaseURL;
      }
      else {
         _baseURL = webglBaseURL;
      }


      //UPPER LEFT X=81.6500000000
      //UPPER LEFT Y=7.7700000000
      //LOWER RIGHT X=81.8000000000
      //LOWER RIGHT Y=7.6600000000
      final Geodetic2D lower = new Geodetic2D( //
               Angle.fromDegrees(38.88248119755079), //
               Angle.fromDegrees(-77.01416015625));
      final Geodetic2D upper = new Geodetic2D( //
               Angle.fromDegrees(38.89103282648846), //
               Angle.fromDegrees(-77.003173828125));


      // Env[-77.01416015625 : -77.003173828125, 38.88248119755079 : 38.89103282648846]

      //  minlat="52.5164000" minlon="13.4016000" maxlat="52.5276000" maxlon="13.4235000"

      //      final Geodetic2D lower = new Geodetic2D( //
      //               Angle.fromDegrees(7.6600000000), //
      //               Angle.fromDegrees(81.6500000000));
      //      final Geodetic2D upper = new Geodetic2D( //
      //               Angle.fromDegrees(7.7700000000), //
      //               Angle.fromDegrees(81.8000000000));


      //  builder.setPlanet(Planet.createFlatEarth());


      final Sector initialSector = new Sector(lower, upper);

      createLayerSet(builder);
      // createElevationData(builder, initialSector);
      load3DModel(builder);
      // builder.setShownSector(initialSector.);

   }


   private static void load3DModel(final IG3MBuilder builder) {
      //   final URL modelURL = new URL(_baseURL + "island.bson");
      final URL modelURL = new URL(_baseURL + "DC.bson");

      //  <bounds minlat="7.7005000" minlon="81.6843000" maxlat="7.7175000" maxlon="81.7034000"/>
      // <bounds minlat="7.7077700" minlon="81.6932000" maxlat="7.7091200" maxlon="81.6946600"/>

      //Island model
      //      final Geodetic2D lower = new Geodetic2D( //
      //               Angle.fromDegrees(7.7005000), //
      //               Angle.fromDegrees(81.6843000));
      //      final Geodetic2D upper = new Geodetic2D( //
      //               Angle.fromDegrees(7.7175000), //
      //               Angle.fromDegrees(81.7034000));

      //Seville Model
      //      final Geodetic2D lower = new Geodetic2D( //
      //               Angle.fromDegrees(37.4315), //
      //               Angle.fromDegrees(-6.0262));
      //      final Geodetic2D upper = new Geodetic2D( //
      //               Angle.fromDegrees(37.3461), //
      //               Angle.fromDegrees(-5.9872));

      //Berlin Model
      //      final Geodetic2D lower = new Geodetic2D( //
      //               Angle.fromDegrees(52.5164000), //
      //               Angle.fromDegrees(13.4016000));
      //      final Geodetic2D upper = new Geodetic2D( //
      //               Angle.fromDegrees(52.5276000), //
      //               Angle.fromDegrees(13.4235000));


      //Berlin Madrid
      //      final Geodetic2D lower = new Geodetic2D( //
      //               Angle.fromDegrees(40.4456), //
      //               Angle.fromDegrees(-3.691));
      //      final Geodetic2D upper = new Geodetic2D( //
      //               Angle.fromDegrees(40.4548), //
      //               Angle.fromDegrees(-3.6898));

      //DC Tile
      final Geodetic2D lower = new Geodetic2D( //
               Angle.fromDegrees(38.88248119755079), //
               Angle.fromDegrees(-77.01416015625));
      final Geodetic2D upper = new Geodetic2D( //
               Angle.fromDegrees(38.89103282648846), //
               Angle.fromDegrees(-77.003173828125));


      //Small Model
      //      final Geodetic2D lower = new Geodetic2D( //
      //               Angle.fromDegrees(7.7077700), //
      //               Angle.fromDegrees(81.6932000));
      //      final Geodetic2D upper = new Geodetic2D( //
      //               Angle.fromDegrees(7.7091200), //
      //               Angle.fromDegrees(81.6946600));

      final Sector modelSector = new Sector(lower, upper);


      final ShapesRenderer cityRenderer = new ShapesRenderer();


      final ShapeLoadListener Plistener = new ShapeLoadListener() {
         @Override
         public void onBeforeAddShape(final SGShape shape) {
            // shape.setScale(1.2);
            //    shape.setRoll(Angle.fromDegrees());
            shape.setPitch(Angle.fromDegrees(90));
         }


         @Override
         public void onAfterAddShape(final SGShape shape) {


            ILogger.instance().logInfo("Downloaded Building");
            _listener.scenarioInitialized();

            final double fromDistance = 10000;
            final double toDistance = 1000;

            final Angle fromAzimuth = Angle.fromDegrees(-90);
            final Angle toAzimuth = Angle.fromDegrees(270);

            final Angle fromAltitude = Angle.fromDegrees(90);
            final Angle toAltitude = Angle.fromDegrees(15);

            shape.orbitCamera(TimeInterval.fromSeconds(5), fromDistance, toDistance, fromAzimuth, toAzimuth, fromAltitude,
                     toAltitude);


         }


         @Override
         public void dispose() {
            // TODO Auto-generated method stub

         }
      };


      cityRenderer.loadBSONSceneJS(modelURL, "", false, new Geodetic3D(modelSector._center._latitude,
               modelSector._center._longitude, 0), AltitudeMode.RELATIVE_TO_GROUND, Plistener);


      builder.addRenderer(cityRenderer);


   }


   private static void createLayerSet(final IG3MBuilder builder) {

      _layerset = new LayerSet();
      final MapBoxLayer mboxTerrainLayer = new MapBoxLayer("examples.map-qogxobv1", TimeInterval.fromDays(30), true, 2);
      mboxTerrainLayer.setTitle("Map Box Terrain");
      mboxTerrainLayer.setEnable(true);

      final WMSLayer osmWMS = new WMSLayer("osm_auto:all", new URL("http://129.206.228.72/cached/osm?", false),
               WMSServerVersion.WMS_1_1_0, Sector.fullSphere(), "image/jpeg", "EPSG:4326", "", false, new LevelTileCondition(0,
                        18), TimeInterval.fromDays(30), true);
      osmWMS.setTitle("OSM");
      osmWMS.setEnable(true);


      final MapBoxLayer mboxAerialLayer = new MapBoxLayer("examples.map-m0t0lrpu", TimeInterval.fromDays(30), true, 2);
      mboxAerialLayer.setTitle("Map Box Aerial");
      mboxAerialLayer.setEnable(false);

      final URLTemplateLayer noLayer = URLTemplateLayer.newMercator(_baseURL + "blue.jpg", //
               Sector.fullSphere(), //
               false, //
               2, //
               17, //
               TimeInterval.fromDays(30));


      // final OSMLayer osmlayer = new OSMLayer(TimeInterval.fromDays(30));
      _layerset.addLayer(mboxTerrainLayer);
      //      _layerset.addLayer(mboxAerialLayer);
      //      _layerset.addLayer(noLayer);

      //   _layerset.addLayer(osmWMS);

      builder.getPlanetRendererBuilder().setLayerSet(_layerset);
   }


   public static void changeBaseLayer() {
      _layerActive++;
      if (_layerActive > 2) {
         _layerActive = 0;
      }
      _layerset.disableAllLayers();
      _layerset.getLayer(_layerActive).setEnable(true);
   }


   private static void createElevationData(final IG3MBuilder builder,
                                           final Sector initialSector) {
      final URL bilURL = new URL(_baseURL + "srilanka.bil");

      //HDR_NROWS=397
      //HDR_NCOLS=541
      final ElevationDataProvider elevationDataProvider = new SingleBilElevationDataProvider( //
               bilURL, //
               initialSector, //
               new Vector2I(541, 397));
      builder.getPlanetRendererBuilder().setElevationDataProvider(elevationDataProvider);

   }

}
