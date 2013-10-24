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

* Check out the project using Android Studio. This creates the `local.properties` file
  with the required line

	`sdk.dir=<root folder of Android Studio's Android SDK installation>`

# Running the Application

In Android Studio: `Run > Run ClickCounter`

# Running the Tests

## Unit tests including out-of-emulator system tests using Robolectric

    $ gradle --info unitTest

## Android instrumentation tests (in-emulator/device system tests)

In Android Studio, right-click on `ClickCounter/src/instrumentTest/java/.../ClickCounterActivityTest`,
then choose `Run ClickCounterActivity`
