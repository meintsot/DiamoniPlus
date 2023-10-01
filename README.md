## Requirements

To work with this project, please ensure you have the following software versions:

- Maven 3.8.2+ (3.9.3 recommended)
- Java 17 (Open JDK + JRE)
- Nginx (v1.24.0)
- Node.js v18.18.0 and npm v9.8.1 (will be installed through maven command later)
- MySQL (8.0 recommended)

# Diamoni Plus Project

  This project contains 3 modules:
- Parent Module: `diamoni-plus-parent`
- Quarkus REST Backend Module: `diamoni-plus-rest-api`
- Angular UI Module: `diamoni-plus-angular`

## Building the Project

  To build the project and its modules simultaneously, run the following commands in the parent module directory:

```shell
mvn clean install -pl :diamoni-plus-angular
```

This command will trigger the build process for both the Angular module.

```shell
mvn clean package -pl :diamoni-plus-rest-api
```

## Project setup

This command will trigger the build process for both the Quarkus REST backend module.

Then inside diamoni-plus-angular/src/main/resources/diamoni-plus-angular/dist copy the diamoni-plus-angular folder
and move it inside the relevant user directory (eg. C:\Users\<username>\diamoni-plus-angular)

Similarly inside diamoni-plus-rest-api/target/ copy the quarkus-app to the relevant user directory 
(eg. C:\Users\<username>\quarkus-app)

Next download the Ngnix (for Windows the link is here: http://nginx.org/en/download.html) extract the compressed file and
move the folder in the relevant user directory. The path to the nginx executable should look like this:
C:\Users\<username>\nginx-1.24.0\nginx-1.24.0

Next inside the Ngnix folder in the conf replace the nginx.conf file with the one inside the project 
(diamoni-plus-rest-api/src/main/resources/nginx.conf) and replace the username with the actual username of the (windows)
user. In the case of Linux or other environments, use relevant custom paths and you can configure them manually.

Inside diamoni-plus-rest-api/src/main/resources copy also private-key.key and certificate.crt and move them inside
C:/Users/<username>/nginx-1.24.0/nginx-1.24.0/ssl/ or any other path you wish but make sure to update nginx.conf respectively.

## Create the Admin User for Quarkus to handle the Database

Connect to mysql with your root account and execute the code included for creating the admin:
diamoni-plus-rest-api/src/main/resources/db_admin.sql 

You can change username and password as you wish but you
need to change in diamoni-plus-rest-api/src/main/resources/application.properties the following properties:

quarkus.datasource.username
quarkus.datasource.password

# Create the Database

Connect to MySQL (with any admin user) and execute the script: diamoni-plus-rest-api/src/main/resources/create_database.sql

# Run Quarkus Application

Navigate inside quarkus-app with cmd or terminal (previous setup eg. eg. C:\Users\<username>\quarkus-app) and execute:

```shell
java -jar quarkus-run.jar
```

This will not only start the application but initially will create the database tables for the application.

# Create the Admin User for the Application

After running initally the quarkus application connect to MySQL (with any admin user) and execute the script: 
diamoni-plus-rest-api/src/main/resources/application_admin.sql

This will create the admin for the application.

# Run the Angular Application

Since Quarkus Application is running, navigate inside Nginx path again (after configuring it in previous steps) and
inside C:\Users\<username>\nginx-1.24.0\nginx-1.24.0 in cmd or terminal execute the command:

```shell
start nginx
```

To stop the server use 

```shell
nginx -s stop
```

# Install the Certificate in Chrome (Development Purposes)

1.1 Open the Chrome Settings:
- Go to chrome://settings/.

1.2 Navigate to Privacy and Security:
- Under “Privacy and Security”, click on “Security”.

1.3 Manage Certificates:
- Scroll down to the “Advanced” section and click on “Manage certificates”.

1.4 Import the Certificate:
- Go to the “Trusted Root Certification Authorities” tab and click on “Import”.
- Follow the wizard to import the certificate.crt file you created earlier.

2 Restart Chrome:
Close all Chrome windows and re-open Chrome. Your self-signed certificate should now be trusted by Chrome.