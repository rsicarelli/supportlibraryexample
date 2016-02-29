/*
 * Copyright 2015, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.rsicarelli.supportlibraryexample;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import com.elo7.cappuccino.Cappuccino;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.rsicarelli.supportlibraryexample.home.HomeActivity;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class HomeScreenTest {

    @Rule
    public ActivityTestRule<HomeActivity> mActivityRule =
            new ActivityTestRule<>(HomeActivity.class);
    private Instrumentation.ActivityMonitor mMonitor;


    public Instrumentation getInstrumentation() {
        return InstrumentationRegistry.getInstrumentation();
    }

    public void registerMonitor(Class<? extends Activity> activityClass) {
        removeMonitor();
        mMonitor = new Instrumentation.ActivityMonitor(activityClass.getName(), null, false);
        getInstrumentation().addMonitor(mMonitor);
    }

    private void removeMonitor() {
        if (mMonitor != null) {
            getInstrumentation().removeMonitor(mMonitor);
        }
    }

    @Before
    public void setUp() {
        registerMonitor(DumbActivity.class);
    }

    @Test
    public void dumbTest() {
        Cappuccino.actionHasText(mActivityRule.getActivity().getString(R.string.a_image_view_with_srccompat_icon)).perform();
    }


}