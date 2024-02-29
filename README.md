# Timeline

Project planning scenario modelling tool

## Getting Started

### Requirements

Before we get cracking you'll need a Mac with the following spec:

- Mac OSX 10.6 or later
- 500MB of disk space
- 128MB of RAM

If you wish to fork this project you'll also require Java:

- Oracle JDK 1.8
- Java FX
- Maven 3 or later

### Installation

Prepackaged binaries can be downloaded for MacOS, Debian and Windows:

- [MacOS](http://apps.biggerconcept.com/dist/timeline/Timeline-1.0.0.dmg)
- [Windows](http://apps.biggerconcept.com/dist/timeline/Timeline-1.0.0.msi)
- [Debian](http://apps.biggerconcept.com/dist/timeline/Timeline-1.0.0.deb)

To install, download and run.

### Compiling the application

There are 2 projects included within this repo:

- app
- launcher

The launcher is a [fxlauncher](https://github.com/edvin/fxlauncher) class, which bootstraps the application, and downloads any updates from the remote server. __Do not place any code you wish to update in the launcher project__ as it will not be updated along with the app. The launcher works by maintaining an application manifest locally, and checking a manifest on the remote site for changes. See the fxlauncher documentation for more on how this works.

The app project is where all the magic happens.

To compile you'll first need to build the launcher:

```bash
cd launcher
mvn clean package
```

Once the launcher is built you can now build the application package:

```bash
cd app
mvn clean package
```

As part of the build, the surefire plugin will execute all JUnit tests (which are covered in detail below).

## Deploying the application

Timeline can be deployed by running the release bash script which is a wrapper around the AWS S3 CLI.

First a .env file with the AWS access credentials needs to be saved on the root directory of the project. See `.env.example` for the required variables.

Then the release script can be run:

To release the application jars:

```bash
./release jars
```

Release of the application jars is sufficient to ensure that all clients are updated when they started next. Platform specific installers only need be generated once when the project is first built. The release script can push those too:

To release the platform specific installers:

```bash
./release dmg # dmg image for macos
./release pkg # pkg installer for macos
./release deb # package for debian linux
./release msi # msi installer for windows
```

Please note, the application specific installer will need to be present in the `app/target/installer/bundles` directory for the release to work. That might mean that the script can only be run for the platform that the build has been done on.

## Tests

Unit tests are [Junit tests](http://junit.org/junit4/), and are kept in the `test/` package in both the `app` and `launcher` projects. The tests are executed post build, and so should be invoked from maven:

```bash
mvn build
```

There is a single suite of UI tests powered by Junit in kahoots with [TestFX](https://github.com/TestFX/TestFX). This test suite loads the UI, performs an action and evaluates the response. 

__NB:__ The UI tests are not executed as part of the mvn build, as there seems to be an issue with running them concurrently. For now you must invoke that test manually.

Describe and show how to run the tests with code examples.

## License

This project is covered by the [MIT licence](https://opensource.org/licenses/MIT)
