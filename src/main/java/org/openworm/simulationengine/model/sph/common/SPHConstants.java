package org.openworm.simulationengine.model.sph.common;

public class SPHConstants {
	public static final int NEIGHBOR_COUNT = 32;
	
	public static final float XMIN = 0;
	public static final float XMAX = 11.69f; //120.24f;
	public static final float YMIN = 0;
	public static final float YMAX = 11.69f; //80.16f;
	public static final float ZMIN = 0;
	public static final float ZMAX = 11.69f; //180.36f;
	
	public static final float LIQUID_TYPE = 1.1f;
	public static final float ELASTIC_TYPE = 2.1f;
	public static final float BOUNDARY_TYPE = 3.1f;
	
	public static final int DECIMAL_ROUNDING_FACTOR = 1000000;
}
