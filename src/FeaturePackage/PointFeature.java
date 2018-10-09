package FeaturePackage;

import Exceptions.WKTRepresentationException;
import Exceptions.WktInvalidException;
import GeometryFactory.GeometryFactory;
import GeometryFactory.Point;
import GeometryFactory.Polygon;

public class PointFeature implements Feature {
	private String name;
    private Point geometry;
	private String info;

	public PointFeature(String name, String wkt) throws WktInvalidException {
		this(name, wkt, "This is a point");
	}

	public PointFeature(String name, String wkt, String info) throws WktInvalidException {
		geometry = GeometryFactory.createPoint(wkt);
		this.name = name;
		this.info = info;
    }

    public String getName() {
    	return name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public Point getGeometry() {
    	return geometry;
    }
    
    public void setGeometry(Point geometry) {
    	this.geometry = geometry;
    }

	@Override
	public String getInfo() {
		return info;
	}

	@Override
	public String getWKT() throws WKTRepresentationException {
		return geometry.getWKT();
	}
	
	@Override
	public Polygon buffer(double range) {
		return null;
	}

    
}
