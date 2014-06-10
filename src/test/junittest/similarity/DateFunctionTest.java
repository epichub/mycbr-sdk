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

package test.junittest.similarity;

import de.dfki.mycbr.core.casebase.DateAttribute;
import de.dfki.mycbr.core.model.DateDesc;
import de.dfki.mycbr.core.similarity.DateFct;
import de.dfki.mycbr.core.similarity.Similarity;
import junit.framework.TestCase;
import org.junit.Test;
import test.junittest.TestFramework;

import java.text.SimpleDateFormat;

/**
 * Created by Marcel on 15.04.2014.
 */
public class DateFunctionTest extends TestCase {

    @Test
    public void testCalculateSimylarityALL() {

        try {

            TestFramework frame = new TestFramework();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm");

            // Add new DateDescription
            DateDesc newDate = new DateDesc(frame.carDesc, "newDate", df.parse("1950-01-01 00:00"),  df.parse("2099-01-01 00:00"), df);

            // Add new DateFunction
            DateFct f = newDate.addDateFct("DateFct", true, DateFct.DateFunctionPrecision.Hour);

            // Start testing

            // Equaltest
            DateAttribute date1 = newDate.getDateAttribute(df.parse("2014-04-15 00:00"));
            DateAttribute date2 = newDate.getDateAttribute(df.parse("2014-04-15 00:00"));
            Similarity sim = f.calculateSimilarity(date1, date2);
            //assertTrue("Equaltest failed result: " + sim.getRoundedValue(), sim.getValue() == 1);

            // Hour Tests
            date1 = newDate.getDateAttribute(df.parse("2014-04-16 01:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 23:59"));
            sim = f.calculateSimilarity(date1, date2);
            System.out.println("Similarity Hour [00:01 - 23:59]: " + sim.getValue());
            assertTrue(round(sim.getValue()) == 0.08333);

            date1 = newDate.getDateAttribute(df.parse("2014-04-16 01:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 01:50"));
            sim = f.calculateSimilarity(date1, date2);
            System.out.println("Similarity Hour [01:00 - 01:50]: " + sim.getValue());
            assertTrue(round(sim.getValue()) == 1);

            date1 = newDate.getDateAttribute(df.parse("2014-04-16 01:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 02:00"));
            sim = f.calculateSimilarity(date1, date2);
            System.out.println("Similarity Hour [01:00 - 02:00]: " + sim.getValue());
            assertTrue(round(sim.getValue()) == 0.95833);

            date1 = newDate.getDateAttribute(df.parse("2014-04-16 00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 12:00"));
            sim = f.calculateSimilarity(date1, date2);
            System.out.println("Similarity Hour [00:00 - 12:00]: " + sim.getValue());
            assertTrue(round(sim.getValue()) == 0.5);

            System.out.println("\r\n=== Hour Loop Test || Start");
            for (int i = 0; i < 24; i++) {
                String hour = "";
                if (i < 10) {
                    hour = "0";
                }
                hour += i;
                date1 = newDate.getDateAttribute(df.parse("2014-04-16 " + hour + ":03"));
                date2 = newDate.getDateAttribute(df.parse("2014-04-16 00:03"));
                sim = f.calculateSimilarity(date1, date2);
                System.out.println("Similarity Hour [" + hour + ":03 - 00:03]: " + sim.getValue());
            }
            System.out.println("=== Hour Loop Test || End\n");

            df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

            date1 = newDate.getDateAttribute(df.parse("2014-04-17 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:30"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0);
            System.out.println("Similarity Hours [2014-04-17 13:00:00 - 2014-04-16 13:00:30]: " + sim.getValue());

            date1 = newDate.getDateAttribute(df.parse("2014-05-16 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:30"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0);
            System.out.println("Similarity Hours [2014-05-16 13:00:00 - 2014-04-16 13:00:30]: " + sim.getValue());

            date1 = newDate.getDateAttribute(df.parse("2015-04-16 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:30"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0);
            System.out.println("Similarity Hours [2015-04-16 13:00:00 - 2014-04-16 13:00:30]: " + sim.getValue());

            // Minute Test
            df = new SimpleDateFormat("yyyy-MM-dd kk:mm");
            ((DateFct)newDate.getFct("DateFct")).setPrecision(DateFct.DateFunctionPrecision.Minute);
            date1 = newDate.getDateAttribute(df.parse("2014-04-16 13:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:30"));
            sim = f.calculateSimilarity(date1, date2);
            System.out.println("Similarity Minute [13:00 - 13:30]: " + sim.getValue());
            assertTrue(round(sim.getValue()) == 0.5);

            date1 = newDate.getDateAttribute(df.parse("2014-04-16 13:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00"));
            sim = f.calculateSimilarity(date1, date2);
            System.out.println("Similarity Minute [13:00 - 13:00]: " + sim.getValue());
            assertTrue(round(sim.getValue()) == 1);

            date1 = newDate.getDateAttribute(df.parse("2014-04-16 13:31"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:31"));
            sim = f.calculateSimilarity(date1, date2);
            System.out.println("Similarity Minute [13:31 - 13:31]: " + sim.getValue());
            assertTrue(round(sim.getValue()) == 1);

            date1 = newDate.getDateAttribute(df.parse("2014-04-16 13:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:59"));
            sim = f.calculateSimilarity(date1, date2);
            System.out.println("Similarity Minute [13:00 - 13:59]: " + sim.getValue());
            assertTrue(round(sim.getValue()) == 0.01667);

            date1 = newDate.getDateAttribute(df.parse("2014-04-16 13:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:45"));
            sim = f.calculateSimilarity(date1, date2);
            System.out.println("Similarity Minute [13:00 - 13:45]: " + sim.getValue());
            assertTrue(round(sim.getValue()) == 0.25);

            date1 = newDate.getDateAttribute(df.parse("2014-04-16 13:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 14:45"));
            sim = f.calculateSimilarity(date1, date2);
            System.out.println("XXX Similarity Minute [13:00 - 14:45]: " + sim.getValue());
            assertTrue(round(sim.getValue()) == 0);

            System.out.println("\n=== Minute Loop Test || Start");
            for (int i = 0; i < 60; i++) {
                String minute = "";
                if (i < 10) {
                    minute = "0";
                }
                minute += i;
                date1 = newDate.getDateAttribute(df.parse("2014-04-16 13:" + i));
                date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00"));
                sim = f.calculateSimilarity(date1, date2);
                System.out.println("Similarity Minute [13:00 - 13:" + minute + "]: " + sim.getValue());
            }
            System.out.println("=== Minute Loop Test || End\n");

            df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

            date1 = newDate.getDateAttribute(df.parse("2014-04-16 14:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:30"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0);
            System.out.println("Similarity Minutes [2014-04-16 14:00:00 - 2014-04-16 13:00:30]: " + sim.getValue());

            date1 = newDate.getDateAttribute(df.parse("2014-04-17 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:30"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0);
            System.out.println("Similarity Minutes [2014-04-17 13:00:00 - 2014-04-16 13:00:30]: " + sim.getValue());

            date1 = newDate.getDateAttribute(df.parse("2014-05-16 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:30"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0);
            System.out.println("Similarity Minutes [2014-05-16 13:00:00 - 2014-04-16 13:00:30]: " + sim.getValue());

            date1 = newDate.getDateAttribute(df.parse("2015-04-16 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:30"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0);
            System.out.println("Similarity Minutes [2015-04-16 13:00:00 - 2014-04-16 13:00:30]: " + sim.getValue());

            // Seconds Test
            ((DateFct)newDate.getFct("DateFct")).setPrecision(DateFct.DateFunctionPrecision.Second);
            df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

            System.out.println("\n=== Seconds Loop Test || Start");
            for (int i = 0; i < 60; i++) {
                String second = "";
                if (i < 10) {
                    second = "0";
                }
                second += i;
                date1 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:" + i));
                date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:00"));
                sim = f.calculateSimilarity(date1, date2);
                System.out.println("Similarity Seconds [13:00:00 - 13:00:" + second + "]: " + sim.getValue());
            }
            System.out.println("=== Second Loop Test || End\n");

            date1 = newDate.getDateAttribute(df.parse("2014-04-16 13:01:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:30"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0);
            System.out.println("Similarity Seconds [13:01:00 - 13:00:30]: " + sim.getValue());

            date1 = newDate.getDateAttribute(df.parse("2014-04-16 14:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:30"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0);
            System.out.println("Similarity Seconds [14:00:00 - 13:00:30]: " + sim.getValue());

            date1 = newDate.getDateAttribute(df.parse("2014-04-17 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:30"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0);
            System.out.println("Similarity Seconds [2014-04-17 13:00:00 - 2014-04-16 13:00:30]: " + sim.getValue());

            date1 = newDate.getDateAttribute(df.parse("2014-05-16 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:30"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0);
            System.out.println("Similarity Seconds [2014-05-16 13:00:00 - 2014-04-16 13:00:30]: " + sim.getValue());

            date1 = newDate.getDateAttribute(df.parse("2015-04-16 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:30"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0);
            System.out.println("Similarity Seconds [2015-04-16 13:00:00 - 2014-04-16 13:00:30]: " + sim.getValue());

            // Days Test
            ((DateFct)newDate.getFct("DateFct")).setPrecision(DateFct.DateFunctionPrecision.Day);
            df = new SimpleDateFormat("yyyy-MM-dd");

            System.out.println("\n=== Days Loop Test || Start");
            for (int i = 1; i <= 31; i++) {
                String day = "";
                if (i < 10) {
                    day = "0";
                }
                day += i;
                date1 = newDate.getDateAttribute(df.parse("2014-04-" + day));
                date2 = newDate.getDateAttribute(df.parse("2014-04-01"));
                sim = f.calculateSimilarity(date1, date2);
                System.out.println("Similarity Days [2014-04-01 | 2014-04-" + day + "]: " + sim.getValue());
            }
            System.out.println("=== Days Loop Test || End\n");

            df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

            date1 = newDate.getDateAttribute(df.parse("2014-05-16 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:30"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0);
            System.out.println("Similarity Days [2014-05-16 13:00:00 - 2014-04-16 13:00:30]: " + sim.getValue());

            date1 = newDate.getDateAttribute(df.parse("2015-04-16 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:30"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0);
            System.out.println("Similarity Days [2015-04-16 13:00:00 - 2014-04-16 13:00:30]: " + sim.getValue());

        } catch (Exception e) {
            assertTrue("Excpetion in testCalculateSimylarityHOUR",false);
        }

    }

    private double round(double d) {
        return Math.round(d * 100000)/100000.0;
    }
}
