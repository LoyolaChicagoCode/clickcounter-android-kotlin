# Learning Objectives

* Simple dependency injection
* Event-driven program execution
* State dependence in applications
* Mapping the model-view-adapter architecture to Android (and the command-line)
* Android application life cycle management (including rotation and back button)
* Playing a notification sound in Android
* Automated unit and integration testing with JUnit
* Testcase Superclass pattern for xUnit testing
* Simple dependency injection
* Automated system testing by interacting with the GUI
* Automated GUI testing in Android

# Setting up the Environment

* Create the file main/local.properties with the line

	`sdk.dir=<root folder of your Android SDK installation>`
	
  (alternatively, set the environment variable ANDROID_HOME in the same folder and restart Eclipse)
* Set up Android 4.2 as an Eclipse user library `Android 4.2` with the external jars
    * `sdk.dir/platforms/android-17/android.jar`
    * `sdk.dir/add-ons/addon-google_apis-google-17/libs/maps.jar`
* Download [Robolectric 2.0-alpha](https://oss.sonatype.org/service/local/artifact/maven/redirect?r=releases&g=org.robolectric&a=robolectric&v=2.0-alpha-1&e=jar&c=jar-with-dependencies "download jar") (jar with all dependencies) and set it up as an Eclipse user library `Robolectric 2.0`

# Running the Application

* Android: as usual through Eclipse
* CLI:
	1. open a terminal
	1. cd to the project root, e.g., `~/workspace/clickcounter-android-java`
	1. `java -cp bin/classes edu.luc.etl.cs313.android.clickcounter.cli.Main`

# Running the Tests

To run the tests through Eclipse, make sure you have both this test
project and the subject-under-test (original project) open in the
current workspace.

* Test of the bounded counter with JUnit: in the main project, right-click on `src/edu...model` > Run As... > JUnit Test
  (if prompted for configuration-specific options, choose the Android JUnit test runner)
* Test of the activity with Robolectric: in the robolectric project, right-click on `robolectric.launch` > Run As... > `robolectric`
* Test of the activity in the Android emulator: right-click on the project root > Run As... > Android JUnit Test
