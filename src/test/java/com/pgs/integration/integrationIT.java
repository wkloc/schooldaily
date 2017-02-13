package com.pgs.integration;

import com.pgs.One;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by wkloc on 2017-02-10.
 */
public class integrationIT {

    @Test
    public void testFoo() throws Exception {
        One one = new One();
        assertEquals("foo", one.foo());
    }

    @Test
    public void testBoth() throws Exception {
        One one = new One();
        assertEquals("toto", one.toto());
        assertEquals("foo", one.foo());
    }
}
