package com.pgs.integration;

import com.pgs.AddClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by wkloc on 2017-02-10.
 */
public class integrationIT {

    private AddClass addClass = new AddClass();

    @Test
    public void add() throws Exception {
        int a[] = {3, 4, 5, 7, 9};
        int b[] = {2, 4, 2, 8, 7};
        for (int index = 0; index < a.length; index++) {
            assertTrue((a[index] + b[index]) == addClass.add(a[index], b[index]));
        }
    }
}
