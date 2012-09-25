# Learning Objectives

* Simple dependency injection
* Event-driven program execution
* State dependence in applications
* Mapping the model-view-adapter architecture to Android (and the command-line)
* Automated unit and integration testing with JUnit
* Testcase Superclass pattern for xUnit testing
* Simple dependency injection
* Automated system testing by interacting with the GUI
* Automated GUI testing in Android

# Running the Application

* Android: as usual through Eclipse
* CLI:
	1. open a terminal
	1. cd to the project root, e.g., ~/workspace/clickcounter-android-java
	1. java -cp bin/classes edu.luc.etl.cs313.android.clickcounter.cli.Main

# Running the Tests

To run the tests through Eclipse, make sure you have both this test
project and the subject-under-test (original project) open in the
current workspace.

* Non-Android test of the bounded counter: right-click on `src/edu...model` > Run As... > JUnit Test
  (if prompted for configuration-specific options, choose the Android JUnit test runner)
* Android test of the activity: right-click on the project root > Run As... > Android JUnit Test
