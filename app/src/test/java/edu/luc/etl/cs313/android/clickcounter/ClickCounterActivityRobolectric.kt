//package edu.luc.etl.cs313.android.clickcounter
//
//import org.junit.Before
//import org.junit.runner.RunWith
//import org.robolectric.Robolectric
//import org.robolectric.RobolectricTestRunner
//import org.robolectric.annotation.Config
//
//import edu.luc.etl.cs313.android.clickcounter.ClickCounterActivity
//
///**
// * Concrete Robolectric test subclass. For the Gradle unitTest task to work,
// * the Robolectric dependency needs to be isolated here instead of being present in src/main.
//
// * @author laufer
// * *
// * @see http://pivotal.github.com/robolectric
// */
//@RunWith(RobolectricTestRunner::class)
//@Config(manifest = "src/main/AndroidManifest.xml", emulateSdk = 18)
//class ClickCounterActivityRobolectric : AbstractClickCounterActivityTest() {
//
//    override var activity: ClickCounterActivity = null
//        private set
//
//    @Before
//    fun setUp() {
//        activity = Robolectric.buildActivity(ClickCounterActivity::class.java).create().start().get()
//    }
//
//    companion object {
//
//        private val TAG = "clickcounter-android-activity-robolectric"
//    }
//}
