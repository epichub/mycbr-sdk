/*
 * myCBR License 2.0
 *
 * Copyright (c) 2009
 * Thomas Roth-Berghofer, Armin Stahl & Deutsches Forschungszentrum f&uuml;r K&uuml;nstliche Intelligenz DFKI GmbH
 * Further contributors: myCBR Team (see http://mycbr-project.net/contact.html for further information
 * about the myCBR Team).
 * All rights reserved.
 *
 * myCBR is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * Since myCBR uses some modules, you should be aware of their licenses for
 * which you should have received a copy along with this program, too.
 *
 * endOfLic */

package junittest;

import de.dfki.mycbr.core.Project;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Runs Project Loading Stress Tests
 *
 * @author myCBR Team
 */
public class StressTestsLoading {

    @After
    public void removeTmp(){
        try {
            File directory = new File(System.getProperty("user.dir") + "/src/test/projects/StressTest/tmp");
            File[] files = directory.listFiles();
            for (File file : files) {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testIncreasingAttributesOnly (){
        int cases = 5;
        int maxAttributes = 100;
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("h:mm:ss");

        FileWriter fw = null;

        try {
        fw = new FileWriter(System.getProperty("user.dir") + "/src/test/projects/StressTest/testIncreasingAttributesOnly.csv");

        PrintWriter pw = new PrintWriter(fw);
        pw.print("noAtts");
        pw.print(",");
        pw.print("noCases");
        pw.print(",");
        pw.print("duration");
        pw.print("\n");

        for (int noAtts = 1 ; noAtts <= maxAttributes ; noAtts++){
            Long duration = loadProject(noAtts,cases);

            double dur = ((double) duration) / 1000;
            DecimalFormat decFormat = new DecimalFormat("###,###,##0.000");
            decFormat.format(dur);
            pw.print(noAtts);
            pw.print(",");
            pw.print(cases);
            pw.print(",");
            pw.print(dur);
            pw.print("\n");
        }
        pw.flush();
        pw.close();
        fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testIncreasingCasesOnly (){
        int noAtts = 5;
        int maxCases = 100;
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("h:mm:ss");

        FileWriter fw = null;

        try {
            fw = new FileWriter(System.getProperty("user.dir") + "/src/test/projects/StressTest/testIncreasingCasesOnly.csv");

            PrintWriter pw = new PrintWriter(fw);
            pw.print("noAtts");
            pw.print(",");
            pw.print("noCases");
            pw.print(",");
            pw.print("duration");
            pw.print("\n");

            for (int cases = 1 ; cases <= maxCases ; cases++){
                Long duration = loadProject(noAtts,cases);

                double dur = ((double) duration) / 1000;
                DecimalFormat decFormat = new DecimalFormat("###,###,##0.000");
                decFormat.format(dur);
                pw.print(noAtts);
                pw.print(",");
                pw.print(cases);
                pw.print(",");
                pw.print(dur);
                pw.print("\n");
            }
            pw.flush();
            pw.close();
            fw.close();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Attributes grow exponentially
    @Test
    public void testIncreasingCasesAndAttributes (){
        int noAtts = 4;
        int cases = 50;
        int maxAtts = 50;
        int maxCases = 200;
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("h:mm:ss");

        FileWriter fw = null;

        try {
            fw = new FileWriter(System.getProperty("user.dir") + "/src/test/projects/StressTest/testIncreasingCasesAndAttributes.csv");

            PrintWriter pw = new PrintWriter(fw);
            pw.print("noAtts");
            pw.print(",");
            pw.print("noCases");
            pw.print(",");
            pw.print("duration");
            pw.print("\n");

            do{
                Long duration = loadProject(noAtts,cases);

                double dur = ((double) duration) / 1000;
                DecimalFormat decFormat = new DecimalFormat("###,###,##0.000");
                decFormat.format(dur);
                pw.print(noAtts);
                pw.print(",");
                pw.print(cases);
                pw.print(",");
                pw.print(dur);
                pw.print("\n");
                noAtts = noAtts + Math.abs(noAtts / 4);
                cases = cases + Math.abs(cases/2);;
            } while (cases < maxCases && noAtts < maxAtts);
            pw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Attributes grow exponentially
    @Test
    public void testIncreasingAttributesAndCases (){
        int cases = 4;
        int noAtts = 50;
        int maxAtts = 200;
        int maxCases = 50;
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("h:mm:ss");

        FileWriter fw = null;

        try {
            fw = new FileWriter(System.getProperty("user.dir") + "/src/test/projects/StressTest/testIncreasingAttributesAndCases.csv");

            PrintWriter pw = new PrintWriter(fw);
            pw.print("noAtts");
            pw.print(",");
            pw.print("noCases");
            pw.print(",");
            pw.print("duration");
            pw.print("\n");

            do{
                Long duration = loadProject(noAtts,cases);

                double dur = ((double) duration) / 1000;
                DecimalFormat decFormat = new DecimalFormat("###,###,##0.000");
                decFormat.format(dur);
                pw.print(noAtts);
                pw.print(",");
                pw.print(cases);
                pw.print(",");
                pw.print(dur);
                pw.print("\n");
                cases = cases + Math.abs(cases/4);
                noAtts = noAtts + Math.abs(noAtts/2);;
            } while (noAtts < maxAtts && cases < maxCases);
            pw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Attributes grow exponentially
    @Test
    public void testSymbolsIncreasingAttributesAndCases (){
        int cases = 0;
        int noAtts = 4;
        int values = 10;
        int subValues = 10;
        int maxValues = 15;
        int maxSubValues = 15;
        int maxAtts = 100;
        int maxCases = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        dateFormat.applyPattern("h:mm:ss");

        FileWriter fw = null;

        try {
            fw = new FileWriter(System.getProperty("user.dir") + "/src/test/projects/StressTest/testSymbolsIncreasingAttributesAndCases.csv");

            PrintWriter pw = new PrintWriter(fw);
            pw.print("noAtts");
            pw.print(",");
            pw.print("noCases");
            pw.print(",");
            pw.print("noValues");
            pw.print(",");
            pw.print("noSubValues");
            pw.print(",");
            pw.print("duration");
            pw.print("\n");

            do{
                Long duration = loadSymbolsInProject(noAtts, cases, values, subValues);

                double dur = ((double) duration) / 1000;
                DecimalFormat decFormat = new DecimalFormat("###,###,##0.000");
                decFormat.format(dur);
                pw.print(noAtts);
                pw.print(",");
                pw.print(cases);
                pw.print(",");
                pw.print(values);
                pw.print(",");
                pw.print(subValues);
                pw.print(",");
                pw.print(dur);
                pw.print("\n");
                values++;
                subValues++;
                noAtts++;
            } while (noAtts < maxAtts && values < maxValues && subValues < maxSubValues);
            pw.flush();
            pw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long loadProject(int noOfAttributes, int noOfCases) {
        long duration = 0L;
        try {
            StressTestFramework stf = new StressTestFramework();
            String projectPath = stf.initStressTestFramework(noOfAttributes, noOfCases);

            Date start = new Date();
            Project p = new Project(projectPath);
            while (p.isImporting()){
                Thread.sleep(1);
            }
            Date end = new Date();
            duration = end.getTime() - start.getTime();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return duration;

    }

    public long loadSymbolsInProject(int attributes, int cases, int values, int subValues) {
        long duration = 0L;
        try {
            StressTestFramework stf = new StressTestFramework();
            String projectPath = stf.initSymbolTestFramework(attributes, cases, values, subValues);

            Date start = new Date();
            Project p = new Project(projectPath);
            while (p.isImporting()){
                Thread.sleep(1);
            }
            Date end = new Date();
            duration = end.getTime() - start.getTime();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return duration;

    }

}
