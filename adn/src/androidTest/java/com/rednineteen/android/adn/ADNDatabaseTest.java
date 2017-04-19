package com.rednineteen.android.adn;

import android.os.Build;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

/**
 * Created on 12/04/2017 by Juan Velasquez - email:  juan@rednineteen.com.
 *
 * Copyright 2017 Juan Velasquez - Rednineteen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class ADNDatabaseTest {

    @Test
    public void getDeviceName() throws Exception {

        String fallback = "fallback";
        // init lib
        ADNDatabase.init(InstrumentationRegistry.getTargetContext());
        String test1 = ADNDatabase.getDeviceName("Alba 10''", null, "", false);   // Test ignoring single quotes.
        String test2 = ADNDatabase.getDeviceName("alba 7", null, "", false);      // Test case insensitive.
        String test3 = ADNDatabase.getDeviceName("ASUS_Z00LD", "ASUS_Z00L_63A", "", false);      // Test method with codename.
        String test4 = ADNDatabase.getDeviceName("DUMMY_should not exists on DB", null, fallback, false);      // Test fallback return.
        String test5 = ADNDatabase.getDeviceName("Smarttab_9701", null, fallback, true);      // Test fallback return.
        String test6 = ADNDatabase.getDeviceName("Vancouver_Orange", null, fallback, false);  // Test market name field empty.
        String test7 = ADNDatabase.getDeviceName("5017X", null, fallback, false);   // Test tab character.
        String test8 = ADNDatabase.getDeviceName(Build.MODEL, Build.DEVICE, fallback, false);   // Test current device market name is not null.
        String test9 = ADNDatabase.getDeviceName("5017O", null, fallback, false);   // Test backslash on market name

        assertEquals("Single quotes test failed", "Alba 10", test1);
        assertEquals("Case ignore test failed", "Alba 7", test2);
        assertEquals("Codename search test failed", "ZenFone 2 Laser (ZE550KL)", test3);
        assertEquals("Fallback test failed", fallback, test4);
        assertEquals("Device name with brand test failed", "Sourcing Creation KeyTAB 1001", test5);
        assertEquals("Fallback when market name emtpy test failed", fallback, test6);
        assertEquals("Tab character on name test", "ALCATEL ONETOUCH PIXI 3 (4.5)", test7.trim());
        assertNotEquals("Current device market name test failed", null, test8);
        assertEquals("Backslash on market name test failed", "ALCATEL ONETOUCH PIXIxe2x84xa2 3 (4.5)", test9);
    }

}