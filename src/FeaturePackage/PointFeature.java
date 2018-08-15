package FeaturePackage;

import GeometryFactory.Point;
import GeometryFactory.Polygon;

public class PointFeature implements Feature {
	private String name;
    private Point geometry;
    private String info;

    public PointFeature(String wkt) {
    	
        //TODO: create poits from multiple wkts and save in Point[] attribute

    }
    s
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
	public String getWKT() {
		
		return geometry.getWKT();
	}
	
	@Override
	public Polygon buffer(double range) {
		return null;
	}

    
}
