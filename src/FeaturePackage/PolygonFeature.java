package FeaturePackage;

import Exceptions.WKTRepresentationException;
import Exceptions.WktInvalidException;
import GeometryFactory.GeometryFactory;
import GeometryFactory.Polygon;

public class PolygonFeature implements Feature {
	private String name;
	private Polygon geometry;
	private String info;

	public PolygonFeature(String name, String wkt) throws WktInvalidException {
		this(name, wkt, "This is a line");
	}

	public PolygonFeature(String name, String wkt, String info) throws WktInvalidException {
		geometry = GeometryFactory.createPolygon(wkt);
		this.name = name;
		this.info = info;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Polygon getGeometry() {
		return geometry;
	}

	public void setGeometry(Polygon geometry) {
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
