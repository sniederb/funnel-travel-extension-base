/*
 * Created on 03.10.2019
 */
package ch.want.funnel.extension.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DataUtilsTest {

    @Test
    public void getCountry() throws Exception {
        final String country = DataUtils.getCountry("BCN/ES");
        assertEquals("ES", country);
    }

    @Test
    public void getDestination() throws Exception {
        final String destination = DataUtils.getDestination("BCN/ES");
        assertEquals("BCN", destination);
    }
}
