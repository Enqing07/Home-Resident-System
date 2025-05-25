# Home Resident System
## Overview
This project is a JavaFX-based GUI application developed using Scene Builder (FXML). It is designed to enhance residential community security and convenience through two core functionalities: **Parking Pre-registration** and **Visitor Management**. The system includes role-based access for residents, security guards, and admin staff, enabling streamlined guest registration and visitor tracking.

## Main Features
- Visitor Management
  - Residents can pre-register their guests by entering visitor details.
  - Admins oversee all visitor entries and logs for security and reporting purposes.
  - Security guards check the visitor list for verification during entry.
  - Visitor activity is recorded for tracking and safety compliance.
- Parking Pre-registration
  - Residents can reserve parking for the visitor.
  - Security guards use the parking list to verify visitor access and parking allocation.

## User Panels & Key Features Overview
### Resident Panel 
- Add a visitor by entering their name and contact number.
- Remove a visitor from the list when not needed.
- Schedule a visit by selecting the visitor, date, time, and purpose.
![Resident Panel2](https://github.com/user-attachments/assets/0621efea-10a7-41ff-96bc-fc75731557ce)
- Reserve parking by choosing a scheduled visitor, entering car plate number, and setting stay duration.
<p align="center">
  <img src="https://github.com/user-attachments/assets/f6f1e19b-a423-4027-9509-622e45937122" alt="Resident Panel2" width="330"/>
</p>

### Admin Panel
- View the list of registered residents.
- View the list of scheduled visitors.

### Security Guard Panel
- View resident information for verification.
- View visitor list to validate entry and check visit details.

## Installation 
1. Run `git clone "Repository Link"`
2. Add the following `.jar` files to your project's Reference Library:
   - [fontawesomefx-8.2.jar](fontawesomefx-8.2.jar)
   - mysql-connector-java.jar
     🔗 [Download MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/))
3. Set up MySQL database connection:
   - Make sure your MySQL server is running.
   - Update `AdminPageController.java` with your own:
     - database name
     - username
     - password
5. Run the Application</br>
   Launch using `Client.java`</br></br>
   **Login Credentials** (for Testing)</br>
   To login as Resident => ID: `1001` Pass: `may123`</br>
   To login as Admin => ID: `1001` Pass: `ava123`</br>
   To login as Security Guard => ID: `1001` Pass: `jack123`

## Tools Used
[![Java](https://img.shields.io/badge/Java-%23ED8B00.svg?logo=openjdk&logoColor=white)](#)
[![MySQL](https://img.shields.io/badge/MySQL-4479A1?logo=mysql&logoColor=fff)](#)
![FXML](https://img.shields.io/badge/FXML-XML-blue?style=flat)
