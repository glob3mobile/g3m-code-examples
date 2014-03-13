

package org.glob3.mobile.client;

import java.util.ArrayList;

import org.glob3.mobile.generated.AltitudeMode;
import org.glob3.mobile.generated.BoxShape;
import org.glob3.mobile.generated.Color;
import org.glob3.mobile.generated.GEO2DLineRasterStyle;
import org.glob3.mobile.generated.GEO2DLineStringGeometry;
import org.glob3.mobile.generated.GEO2DMultiLineStringGeometry;
import org.glob3.mobile.generated.GEO2DMultiPolygonGeometry;
import org.glob3.mobile.generated.GEO2DPointGeometry;
import org.glob3.mobile.generated.GEO2DPolygonData;
import org.glob3.mobile.generated.GEO2DPolygonGeometry;
import org.glob3.mobile.generated.GEO2DSurfaceRasterStyle;
import org.glob3.mobile.generated.GEOGeometry;
import org.glob3.mobile.generated.GEOLineRasterSymbol;
import org.glob3.mobile.generated.GEOMarkSymbol;
import org.glob3.mobile.generated.GEOMultiLineRasterSymbol;
import org.glob3.mobile.generated.GEOPolygonRasterSymbol;
import org.glob3.mobile.generated.GEOShapeSymbol;
import org.glob3.mobile.generated.GEOSymbol;
import org.glob3.mobile.generated.GEOSymbolizer;
import org.glob3.mobile.generated.Geodetic2D;
import org.glob3.mobile.generated.Geodetic3D;
import org.glob3.mobile.generated.JSONNumber;
import org.glob3.mobile.generated.JSONObject;
import org.glob3.mobile.generated.Mark;
import org.glob3.mobile.generated.StrokeCap;
import org.glob3.mobile.generated.StrokeJoin;
import org.glob3.mobile.generated.Vector3D;


public class Symbology {

   static GEOSymbolizer linesDrainSymbolizer = new GEOSymbolizer() {

                                                @Override
                                                public ArrayList<GEOSymbol> createSymbols(final GEO2DMultiPolygonGeometry geometry) {
                                                   // TODO Auto-generated method stub
                                                   return null;
                                                }


                                                @Override
                                                public ArrayList<GEOSymbol> createSymbols(final GEO2DPolygonGeometry geometry) {
                                                   // TODO Auto-generated method stub
                                                   return null;
                                                }


                                                @Override
                                                public ArrayList<GEOSymbol> createSymbols(final GEO2DMultiLineStringGeometry geometry) {
                                                   // TODO Auto-generated method stub
                                                   return null;
                                                }


                                                @Override
                                                public ArrayList<GEOSymbol> createSymbols(final GEO2DLineStringGeometry geometry) {
                                                   final ArrayList<GEOSymbol> symbols = new ArrayList<GEOSymbol>();
                                                   symbols.add(new GEOLineRasterSymbol(geometry.getCoordinates(),
                                                            createLineRasterStyle(geometry)));
                                                   return symbols;
                                                }


                                                @Override
                                                public ArrayList<GEOSymbol> createSymbols(final GEO2DPointGeometry geometry) {
                                                   final ArrayList<GEOSymbol> result = new ArrayList<GEOSymbol>();
                                                   final double boxExtent = 50000;

                                                   final BoxShape bs = new BoxShape(new Geodetic3D(geometry.getPosition(), 0),
                                                            AltitudeMode.RELATIVE_TO_GROUND, new Vector3D(boxExtent / 4,
                                                                     boxExtent / 4, 0), 2, Color.fromRGBA255(252, 205, 21, 125),
                                                            Color.fromRGBA255(252, 205, 21, 125), false);

                                                   final ArrayList<Geodetic2D> polygon = new ArrayList<Geodetic2D>();

                                                   //                                                   result.add(new GEORasterPolygonSymbol(new GEO2DPolygonData(polygon),null)
                                                   //                                                            createLineRasterStyle(geometry),
                                                   //                                                            createPolygonSurfaceRasterStyle(geometry), 5, -1));

                                                   return result;
                                                }
                                             };
   static GEOSymbolizer Symbolizer           = new GEOSymbolizer() {

                                                @Override
                                                public ArrayList<GEOSymbol> createSymbols(final GEO2DMultiPolygonGeometry geometry) {
                                                   // TODO Auto-generated method stub
                                                   return null;
                                                }


                                                @Override
                                                public ArrayList<GEOSymbol> createSymbols(final GEO2DPolygonGeometry geometry) {
                                                   final ArrayList<GEOSymbol> symbols = new ArrayList<GEOSymbol>();
                                                   symbols.add(new GEOPolygonRasterSymbol(geometry.getPolygonData(),
                                                            createPolygonLineRasterStyle(geometry),
                                                            createPolygonSurfaceRasterStyle(geometry)));

                                                   return symbols;
                                                }


                                                @Override
                                                public ArrayList<GEOSymbol> createSymbols(final GEO2DMultiLineStringGeometry geometry) {
                                                   // TODO Auto-generated method stub
                                                   return null;
                                                }


                                                @Override
                                                public ArrayList<GEOSymbol> createSymbols(final GEO2DLineStringGeometry geometry) {
                                                   final ArrayList<GEOSymbol> symbols = new ArrayList<GEOSymbol>();
                                                   symbols.add(new GEOLineRasterSymbol(geometry.getCoordinates(),
                                                            createLineRasterStyle(geometry)));
                                                   return symbols;
                                                }


                                                @Override
                                                public ArrayList<GEOSymbol> createSymbols(final GEO2DPointGeometry geometry) {

                                                   final ArrayList<GEOSymbol> result = new ArrayList<GEOSymbol>();
                                                   final double boxExtent = 50000;

                                                   final BoxShape bs = new BoxShape(new Geodetic3D(geometry.getPosition(), 0),
                                                            AltitudeMode.RELATIVE_TO_GROUND, new Vector3D(boxExtent / 4,
                                                                     boxExtent / 4, 0), 2, Color.fromRGBA255(252, 205, 21, 125),
                                                            Color.fromRGBA255(252, 205, 21, 125), false);

                                                   result.add(new GEOShapeSymbol(bs));

                                                   return result;

                                                }
                                             };


   public static GEO2DLineRasterStyle createLineRasterStyle(final GEOGeometry geometry) {
      final JSONObject properties = geometry.getFeature().getProperties();

      final String type = properties.getAsString("type", "");


      // final float dashLengths[] = { 1, 12 };
      final float dashLengths[] = { 0, 0 };
      final int dashCount = 2;

      final JSONNumber s = properties.getAsNumber("wmo_wind");

      final double windValue = s.value();

      if (windValue < 20) {
         return new GEO2DLineRasterStyle( //
                  Color.fromRGBA255(235, 240, 1, 255), //
                  5, //
                  StrokeCap.CAP_ROUND, //
                  StrokeJoin.JOIN_ROUND, //
                  1, dashLengths, //
                  dashCount, //
                  0);

      }
      else if ((windValue > 20) && (windValue <= 40)) {

         return new GEO2DLineRasterStyle( //
                  Color.fromRGBA255(68, 172, 75, 255), //
                  5, //
                  StrokeCap.CAP_ROUND, //
                  StrokeJoin.JOIN_ROUND, //
                  1, dashLengths, //
                  dashCount, //
                  0);
      }
      else if ((windValue > 40) && (windValue <= 60)) {

         return new GEO2DLineRasterStyle( //
                  Color.fromRGBA255(247, 130, 37, 255), //
                  5, //
                  StrokeCap.CAP_ROUND, //
                  StrokeJoin.JOIN_ROUND, //
                  1, dashLengths, //
                  dashCount, //
                  0);
      }
      else if ((windValue > 60) && (windValue <= 80)) {

         return new GEO2DLineRasterStyle( //
                  Color.fromRGBA255(56, 162, 220, 255), //
                  5, //
                  StrokeCap.CAP_ROUND, //
                  StrokeJoin.JOIN_ROUND, //
                  1, dashLengths, //
                  dashCount, //
                  0);
      }
      else if ((windValue > 80)) {

         return new GEO2DLineRasterStyle( //
                  Color.fromRGBA255(211, 32, 35, 255), //
                  5, //
                  StrokeCap.CAP_ROUND, //
                  StrokeJoin.JOIN_ROUND, //
                  1, dashLengths, //
                  dashCount, //
                  0);
      }

      return new GEO2DLineRasterStyle( //
               Color.red().darker().darker().muchDarker(), //
               5, //
               StrokeCap.CAP_ROUND, //
               StrokeJoin.JOIN_ROUND, //
               1, dashLengths, //
               dashCount, //
               0);

   }


   public static GEO2DLineRasterStyle createPolygonLineRasterStyle(final GEOGeometry geometry) {
      @SuppressWarnings("unused")
      final JSONObject properties = geometry.getFeature().getProperties();

      // final int colorIndex = (int) properties.getAsNumber("mapcolor7", 0);
      final int colorIndex = 5;
      final Color color = Color.fromRGBA(0.7f, 0, 0, 0.5f).wheelStep(7, colorIndex).muchLighter().muchLighter();

      final float dashLengths[] = {};
      final int dashCount = 0;

      return new GEO2DLineRasterStyle(color, 2, StrokeCap.CAP_ROUND, StrokeJoin.JOIN_ROUND, 1, dashLengths, dashCount, 0);
   }


   public static GEO2DSurfaceRasterStyle createPolygonSurfaceRasterStyle(final GEOGeometry geometry) {
      @SuppressWarnings("unused")
      final JSONObject properties = geometry.getFeature().getProperties();

      // final int colorIndex = (int) properties.getAsNumber("mapcolor7", 0);
      final int colorIndex = 10;
      final Color color = Color.fromRGBA(0.7f, 0, 0, 0.5f).wheelStep(7, colorIndex);

      return new GEO2DSurfaceRasterStyle(color);
   }

   static GEOSymbolizer defaultSymbolizer  = new GEOSymbolizer() {
                                              @Override
                                              public ArrayList<GEOSymbol> createSymbols(final GEO2DPointGeometry geometry) {
                                                 return new ArrayList<GEOSymbol>(0);
                                              }


                                              @Override
                                              public ArrayList<GEOSymbol> createSymbols(final GEO2DLineStringGeometry geometry) {
                                                 final ArrayList<GEOSymbol> symbols = new ArrayList<GEOSymbol>();
                                                 //                                                 symbols.add(new GEOPolygonRasterSymbol(geometry.getCoordinates(),
                                                 //                                                          createLineRasterStyle(geometry)));
                                                 return symbols;
                                              }


                                              @Override
                                              public ArrayList<GEOSymbol> createSymbols(final GEO2DMultiLineStringGeometry geometry) {
                                                 final ArrayList<GEOSymbol> symbols = new ArrayList<GEOSymbol>();
                                                 symbols.add(new GEOMultiLineRasterSymbol(geometry.getCoordinatesArray(),
                                                          createLineRasterStyle(geometry)));
                                                 return symbols;
                                              }


                                              private GEO2DLineRasterStyle createPolygonLineRasterStyle(final GEOGeometry geometry) {
                                                 final JSONObject properties = geometry.getFeature().getProperties();

                                                 final int colorIndex = (int) properties.getAsNumber("mapcolor7", 0);

                                                 final Color color = Color.fromRGBA(0.7f, 0, 0, 0.5f).wheelStep(7, colorIndex).muchLighter().muchLighter();

                                                 final float dashLengths[] = {};
                                                 final int dashCount = 0;

                                                 return new GEO2DLineRasterStyle(color, 2, StrokeCap.CAP_ROUND,
                                                          StrokeJoin.JOIN_ROUND, 1, dashLengths, dashCount, 0);
                                              }


                                              private GEO2DSurfaceRasterStyle createPolygonSurfaceRasterStyle(final GEOGeometry geometry) {
                                                 final JSONObject properties = geometry.getFeature().getProperties();

                                                 final int colorIndex = (int) properties.getAsNumber("mapcolor7", 0);

                                                 final Color color = Color.fromRGBA(0.7f, 0, 0, 0.5f).wheelStep(7, colorIndex);

                                                 return new GEO2DSurfaceRasterStyle(color);
                                              }


                                              @Override
                                              public ArrayList<GEOSymbol> createSymbols(final GEO2DPolygonGeometry geometry) {
                                                 final ArrayList<GEOSymbol> symbols = new ArrayList<GEOSymbol>(0);

                                                 symbols.add(new GEOPolygonRasterSymbol(geometry.getPolygonData(),
                                                          createPolygonLineRasterStyle(geometry),
                                                          createPolygonSurfaceRasterStyle(geometry)));

                                                 return symbols;
                                              }


                                              @Override
                                              public ArrayList<GEOSymbol> createSymbols(final GEO2DMultiPolygonGeometry geometry) {
                                                 final ArrayList<GEOSymbol> symbols = new ArrayList<GEOSymbol>(0);

                                                 final GEO2DLineRasterStyle lineStyle = createPolygonLineRasterStyle(geometry);
                                                 final GEO2DSurfaceRasterStyle surfaceStyle = createPolygonSurfaceRasterStyle(geometry);

                                                 final ArrayList<GEO2DPolygonData> polygonsData = geometry.getPolygonsData();
                                                 final int polygonsDataSize = polygonsData.size();

                                                 for (int i = 0; i < polygonsDataSize; i++) {
                                                    final GEO2DPolygonData polygonData = polygonsData.get(i);
                                                    symbols.add(new GEOPolygonRasterSymbol(polygonData, lineStyle, surfaceStyle));

                                                 }

                                                 return symbols;
                                              }
                                           };
   static GEOSymbolizer USCitiesSymbolizer = new GEOSymbolizer() {

                                              @Override
                                              public ArrayList<GEOSymbol> createSymbols(final GEO2DMultiPolygonGeometry geometry) {
                                                 // TODO Auto-generated method stub
                                                 return null;
                                              }


                                              @Override
                                              public ArrayList<GEOSymbol> createSymbols(final GEO2DPolygonGeometry geometry) {
                                                 final ArrayList<GEOSymbol> symbols = new ArrayList<GEOSymbol>();
                                                 symbols.add(new GEOPolygonRasterSymbol(geometry.getPolygonData(),
                                                          createPolygonLineRasterStyle(geometry),
                                                          createPolygonSurfaceRasterStyle(geometry)));

                                                 return symbols;
                                              }


                                              @Override
                                              public ArrayList<GEOSymbol> createSymbols(final GEO2DMultiLineStringGeometry geometry) {
                                                 // TODO Auto-generated method stub
                                                 return null;
                                              }


                                              @Override
                                              public ArrayList<GEOSymbol> createSymbols(final GEO2DLineStringGeometry geometry) {
                                                 final ArrayList<GEOSymbol> symbols = new ArrayList<GEOSymbol>();
                                                 symbols.add(new GEOLineRasterSymbol(geometry.getCoordinates(),
                                                          createLineRasterStyle(geometry)));
                                                 return symbols;
                                              }


                                              @Override
                                              public ArrayList<GEOSymbol> createSymbols(final GEO2DPointGeometry geometry) {

                                                 final ArrayList<GEOSymbol> result = new ArrayList<GEOSymbol>();

                                                 final JSONObject properties = geometry.getFeature().getProperties();

                                                 final double popMax = properties.getAsNumber("POP_MAX", 0);


                                                 final double boxExtent = 50000;
                                                 final double baseArea = boxExtent * boxExtent;
                                                 final double volume = popMax * boxExtent * 3500;
                                                 final double height = volume / baseArea;

                                                 final BoxShape bs = new BoxShape(new Geodetic3D(geometry.getPosition(), 0),
                                                          AltitudeMode.RELATIVE_TO_GROUND, new Vector3D(boxExtent / 4,
                                                                   boxExtent / 4, height / 2), 2, Color.fromRGBA255(252, 205, 21,
                                                                   255), Color.fromRGBA255(252, 205, 21, 255).muchDarker(), false);


                                                 final long popFormated = Math.round(popMax / 1000);
                                                 final Mark m = new Mark("" + popFormated, new Geodetic3D(geometry.getPosition(),
                                                          height / 5), AltitudeMode.RELATIVE_TO_GROUND);

                                                 result.add(new GEOShapeSymbol(bs));
                                                 result.add(new GEOMarkSymbol(m));
                                                 return result;

                                              }
                                           };


}
