package main;

import Exceptions.WKTRepresentationException;
import FeaturePackage.PointFeature;
import GeometryFactory.GeometryFactory;
import GeometryFactory.Point;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.StandardCharsets;

class CsvTest {
    static void csvTest() {
        System.out.println();
        System.out.println("---Test CSV---");
        String workingDir = System.getProperty("user.dir");
        System.out.println("Current working directory : " + workingDir);
        try {
            // --- handle CSV ---
            // set file paths
            File inFile = new File(workingDir + "//cities.csv");
            File csvFile = new File(workingDir + "//wkt_cities.csv");

            // create input and output streams
            FileInputStream inputStream = new FileInputStream(inFile);
            FileOutputStream csvStream = new FileOutputStream(csvFile);

            // create stream reader and writer
            BufferedReader csvReader= new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            CSVParser csvParser = CSVFormat.DEFAULT.withDelimiter(';').withHeader("X", "Y", "population", "name").parse(csvReader);
            OutputStreamWriter csvWriter = new OutputStreamWriter(csvStream, StandardCharsets.UTF_8);

            // use CSV printer for output file writing
            CSVPrinter csvPrinter = new CSVPrinter(csvWriter, CSVFormat.DEFAULT.withHeader("X", "Y", "population", "name", "WKT"));

            // read Header line without parsing it
            csvReader.readLine();

            // loop through csv lines
            for (CSVRecord record : csvParser.getRecords()) {
                // prepare feature info for csv
                String info = "population: " + record.get("population");
                double[] coords = {
                        Double.parseDouble(record.get("X")),
                        Double.parseDouble(record.get("Y"))
                };
                double pop = Double.parseDouble(record.get("population"));
                Point featurePoint = GeometryFactory.createPointM(pop, coords);

                // create point feature
                PointFeature pf = new PointFeature(record.get("name"), info, featurePoint);
                csvPrinter.printRecord(
                        pf.getGeometry().getX(),
                        pf.getGeometry().getY(),
                        pf.getGeometry().hasLrsValue() ? (int) pf.getGeometry().getLrsValue() : record.get("population"),
                        pf.getName(),
                        pf.getWKT()
                );
            }

            // write possibly cached content
            csvPrinter.flush();

            // close all streams
            csvPrinter.close();
            inputStream.close();
            csvStream.close();
            csvReader.close();
            csvParser.close();
            csvWriter.close();
        } catch (WKTRepresentationException | IOException e) {
            e.printStackTrace();
        }
    }
}
