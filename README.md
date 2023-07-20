## Requirements

To work with this project, please ensure you have the following software versions:

- Maven 3.8.2+ (3.9.3 recommended)
- Node.js version 18.17.0
- npm version 9.6.7 (for Angular)

# Diamoni Plus Project

  This project contains 3 modules:
- Parent Module: `diamoni-plus-parent`
- Quarkus REST Backend Module: `diamoni-plus-rest-api`
- Angular UI Module: `diamoni-plus-angular`

## Building the Project

  To build the project and its modules simultaneously, run the following command in the parent module directory:

```shell
mvn -T 1C clean install
```

  This command will trigger the build process for both the Angular module and the Quarkus REST backend module.

### Building Native Quarkus Application

  To produce a native Quarkus REST backend application for your environment machine, you can add the "native" profile using the `-Pnative` flag. However, building a native executable requires using a distribution of GraalVM. There are three distributions available: Oracle GraalVM Community Edition (CE), Oracle GraalVM Enterprise Edition (EE), and Mandrel.

  Please follow the instructions below to set up GraalVM and build the native application:

1. Download and install GraalVM from one of the available distributions mentioned above.
2. Set up the `GRAALVM_HOME` environment variable to point to your GraalVM installation directory.
3. Run the following command to build the native application:

```shell
mvn clean install -pl :diamoni-plus-rest-api -am -Pnative
```

  This command will build the native Quarkus REST backend application.

## Angular Module

  The Maven module for the Angular UI is configured to perform the following steps during a clean install build:

1. Run `npm install` to install the necessary dependencies for the Angular application.
2. Build the Angular application and put the output in the `target` folder.

  To run the Angular application, you can use the following command in the project's root directory:

```shell
mvn generate-resources -pl :diamoni-plus-angular -P run-angular
```

  This command will run the Angular application using the `run-angular` profile.

