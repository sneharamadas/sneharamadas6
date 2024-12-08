# SER531-Intellihealth: A knowledge-network-based prediction Agent


Intellihealth has been developed and tested on mac in Intellij.


Steps to run the app.

1-> Make sure you have Fuseki Downloaded Fuseki version [4.10.0](https://jena.apache.org/download/).

2-> Head into the downloaded Fuseki directory and run `chmod 777 fuseki-server` and `./fuseki-server`, the Fuseki server should start at the port 3030, head to the [fuseki-server](http://localhost:3030/#/)

3-> You can see the option of adding a dataset, create a data set name it hello-world-dataset (you can name something else, but please replace the name in [application.properties](https://github.com/mnakawe/SER531-Intellihealth/blob/main/Intellihealth/src/main/resources/application.properties) file).

4-> After creating the data set, we need to import the data, so click add-data and select files, add the [rdf](https://drive.google.com/file/d/1tRUk5pX0IEUmjQNCXoyaGTZY3QRIjojh/view?usp=drive_link).

5-> Make sure you have [maven](https://maven.apache.org/install.html) installed. If you are using intellij you can just install the plugin.

6-> Install [spring-boot](https://docs.spring.io/spring-boot/docs/1.0.0.RC5/reference/html/getting-started-installing-spring-boot.html) (this also tells how to install maven). Confirm the installation. 

7-> Make sure you have tomcat 10.1.6 installed, if not install [tomcat](https://tomcat.apache.org/download-10.cgi)

8-> Now head to the project folder [Intellihealth](https://github.com/mnakawe/SER531-Intellihealth/tree/main/Intellihealth) and run the command `mvn clean install` (where the pom.xml is).

9-> Run the command `mvn spring-boot:run` to start the Intellihealth server. (or just run it the app through intellij). You should see the server running on port `8081` and the tomcat should have also started. 

10-> Now you should be able to access the webapp at `http://localhost:8081`.

11-> You can see the video demonstration [here](https://drive.google.com/file/d/1tqKqu6lRSDQe4uC9kMBB-iaReyWXozuw/view?usp=drive_link)

12-> Link to the [repo](https://github.com/mnakawe/SER531-Intellihealth)
 
