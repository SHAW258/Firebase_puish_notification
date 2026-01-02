package com.firebase.firebasepuishnotification;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
// This annotation tells the test runner to use AndroidJUnit4 to run the tests in this class.
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    // This annotation marks this method as a test to be run.
    @Test
    public void useAppContext() {
        // This gets the context of the app being tested. The context provides access to system services and resources.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        // This assertion checks if the package name of the app is what we expect it to be. This is a simple way to verify that the correct app is being tested.
        assertEquals("com.firebase.firebasepuishnotification", appContext.getPackageName());
    }
}
