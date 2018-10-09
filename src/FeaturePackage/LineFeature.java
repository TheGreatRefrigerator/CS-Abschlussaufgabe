package FeaturePackage;

import Exceptions.WKTRepresentationException;
import Exceptions.WktInvalidException;
import GeometryFactory.GeometryFactory;
import GeometryFactory.Line;
import GeometryFactory.Point;
import GeometryFactory.Polygon;

public class LineFeature implements Feature {
	private String name;
	private Line geometry;
	private String info;

	public LineFeature(String name, String wkt) throws WktInvalidException {
		this(name, wkt, "This is a line");
	}

	public LineFeature(String name, String wkt, String info) throws WktInvalidException {
		geometry = GeometryFactory.createLine(wkt);
		this.name = name;
		this.info = info;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Line getGeometry() {
		return geometry;
	}

	public void setGeometry(Line geometry) {
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