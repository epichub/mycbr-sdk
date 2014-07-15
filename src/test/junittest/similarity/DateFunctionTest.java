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
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

            // Add new DateDescription
            DateDesc newDate = new DateDesc(frame.carDesc, "newDate", df.parse("1950-01-01 00:00:00"),  df.parse("2099-01-01 00:00:00"), df);

            // Add new DateFunction
            DateFct f = newDate.addDateFct("DateFct", true, DateFct.DateFunctionPrecision.Second);

            // Start testing

            // Equaltest
            DateAttribute date1 = newDate.getDateAttribute(df.parse("2014-04-15 00:00:00"));
            DateAttribute date2 = newDate.getDateAttribute(df.parse("2014-04-15 00:00:00"));
            Similarity sim = f.calculateSimilarity(date1, date2);
            //assertTrue("Equaltest failed result: " + sim.getRoundedValue(), sim.getValue() == 1);

            // Seconds Test

            date1 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:30"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0.5);
            
            date1 = newDate.getDateAttribute(df.parse("2014-04-16 13:01:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:30"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0);

            date1 = newDate.getDateAttribute(df.parse("2014-04-16 14:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:30"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0);

            date1 = newDate.getDateAttribute(df.parse("2014-04-17 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:30"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0);

            date1 = newDate.getDateAttribute(df.parse("2014-05-16 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:30"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0);

            date1 = newDate.getDateAttribute(df.parse("2015-04-16 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:30"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0);
            
            // Minutes Test
            ((DateFct)newDate.getFct("DateFct")).setPrecision(DateFct.DateFunctionPrecision.Minute);
            
            date1 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:30:00"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(round(sim.getValue()) == 0.5);

            date1 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:00"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(round(sim.getValue()) == 1);

            date1 = newDate.getDateAttribute(df.parse("2014-04-16 13:31:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:31:00"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(round(sim.getValue()) == 1);

            date1 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:59:00"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(round(sim.getValue()) == 0.01667);

            date1 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:45:00"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(round(sim.getValue()) == 0.25);

            date1 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 14:45:00"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(round(sim.getValue()) == 0);

            date1 = newDate.getDateAttribute(df.parse("2014-04-16 14:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:30"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0);

            date1 = newDate.getDateAttribute(df.parse("2014-04-17 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:30"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0);

            date1 = newDate.getDateAttribute(df.parse("2014-05-16 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:30"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0);

            date1 = newDate.getDateAttribute(df.parse("2015-04-16 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:30"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0);
            
            // Hour Tests
            ((DateFct)newDate.getFct("DateFct")).setPrecision(DateFct.DateFunctionPrecision.Hour);
            
            date1 = newDate.getDateAttribute(df.parse("2014-04-16 01:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 23:59:00"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(round(sim.getValue()) == 0.08333);

            date1 = newDate.getDateAttribute(df.parse("2014-04-16 01:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 01:50:00"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(round(sim.getValue()) == 1);

            date1 = newDate.getDateAttribute(df.parse("2014-04-16 01:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 02:00:00"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(round(sim.getValue()) == 0.95833);

            date1 = newDate.getDateAttribute(df.parse("2014-04-16 00:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 12:00:00"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(round(sim.getValue()) == 0.5);

            date1 = newDate.getDateAttribute(df.parse("2014-04-17 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:30"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0);

            date1 = newDate.getDateAttribute(df.parse("2014-05-16 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:30"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0);

            date1 = newDate.getDateAttribute(df.parse("2015-04-16 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:30"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0);

            // Days Test
            ((DateFct)newDate.getFct("DateFct")).setPrecision(DateFct.DateFunctionPrecision.Day);
            
            date1 = newDate.getDateAttribute(df.parse("2014-05-16 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-05-16 13:00:00"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 1);
            
            date1 = newDate.getDateAttribute(df.parse("2014-05-15 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-05-31 13:00:00"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(round(sim.getValue()) == 0.48387);
            
            date1 = newDate.getDateAttribute(df.parse("2014-05-16 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:30"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0);

            date1 = newDate.getDateAttribute(df.parse("2015-04-16 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-04-16 13:00:30"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0);
            
            // Month Test
        	((DateFct)newDate.getFct("DateFct")).setPrecision(DateFct.DateFunctionPrecision.Month);
            
            date1 = newDate.getDateAttribute(df.parse("2014-05-16 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-05-16 13:00:00"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 1);
            
            date1 = newDate.getDateAttribute(df.parse("2014-12-16 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-06-16 13:00:00"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0.5);
            
            date1 = newDate.getDateAttribute(df.parse("2014-05-16 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2015-05-16 13:00:00"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 0);
            
            // Year Test
        	((DateFct)newDate.getFct("DateFct")).setPrecision(DateFct.DateFunctionPrecision.Year);
            
            date1 = newDate.getDateAttribute(df.parse("2014-05-16 13:00:00"));
            date2 = newDate.getDateAttribute(df.parse("2014-05-16 13:00:00"));
            sim = f.calculateSimilarity(date1, date2);
            assertTrue(sim.getValue() == 1);
            

        } catch (Exception e) {
            assertTrue("Excpetion in testCalculateSimylarityHOUR",false);
        }

    }

    private double round(double d) {
        return Math.round(d * 100000)/100000.0;
    }
}
