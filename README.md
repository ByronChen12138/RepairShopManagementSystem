# Repairshop Management System
## ECSE 321 Project Group 11

Welcome to the ECSE 321 Group 11 project.

Please check the wiki for information on the architecture of the system.

***

## Before Running

If you have run the persistence layer test, you must create a RepairShopManagementSystem entity in the database first before using the website. 
You can create such entity via Postman.

Furthermore, the database for the backend service in "application.properties" is in heroku. You can change it local. The 
backend URL for the frontend inside "configuration.js" can be switched local if you are running the spring on your PC.

The frontend code does not contain any dependency, and thus you must set up the environment before execution. You can do
this via <code>npm install</code> at the frontend directory. Then, you can type <code>npm run serve</code> to deploy
the website. Again, whether using local or remote backend service, a backend service must be present for most 
functionalities.

***

## Scope of Project

This system application is designed to help an auto repair shop with its business. 
The application shall allow a mixture of owners, administrative assistants and customers to 
interact in order to accommodate the customers' needs for the appointments for their cars.
The scope of the project of the group of ECSE 321 is to develop the software system for a appointment booking app.
The project is organized in four Agile sprints. 
The final deliverable consists of an Android app and a website for this appointment booking app.
It should allow the owners to check the schedule with the appointments booked by the customers.
And the administrative assistants should be able to set the available time in the schedule for the customers to choose for their appointment time.
The project scope also includes a persistence layer to save and load all the necessary data into a database.

This application supports the scenarios described for every stakeholder. 
All functionality of the system needs to be accessible via the web frontend for respective stakeholders. 
In addition, a mobile (Android) frontend shall allow the execution of the most important functionality for the given stakeholder, i.e. it shall have both read and
write access to the backend via RESTful service calls. 
External systems or services are not required to be integrated. 

***

## Deliverable 1

The Meeting Minute can be found [here](https://github.com/McGill-ECSE321-Winter2021/project-group-11/wiki/Meeting-Minutes-for-Sprint-1)

| Team member | Total hours | Responsibility |
|------------------ |:-------------:| ---------------|
| Ao Shen    | 16 | System Requirements, Domain Model Design, Use Case and the diagram, Development of Persistence Layer and Testing, Build System | 
| Byron Chen | 16 | System Requirements, Domain Model Design, Use Case, Development of Persistence Layer and Testing, Wiki and README Design, Build System |
| Ing Tian   | 16 | System Requirements, Domain Model Design, Use Case, Development of Persistence Layer and Testing, Project Management, Build System |
| Kevin Li   | 16 | System Requirements, Domain Model Design, Use Case, Development of Persistence Layer and Testing, Project Record, Build System and Continuous Integration |
| Xiang Li   | 16 | System Requirements, Domain Model Design, Use Case, Development of Persistence Layer and Testing, Build System and Continuous Integration |

***

## Deliverable 2

The Meeting Minute can be found [here](https://github.com/McGill-ECSE321-Winter2021/project-group-11/wiki/Meeting-Minutes-for-Sprint-2)

| Team member | Total hours | Responsibility |
|------------------ |:-------------:| ---------------|
| Ao Shen    | 38 | RESTful services, Build system, Unit testing and RESTful Testing | 
| Byron Chen | 37 | RESTful services, Build system, Unit testing, RESTful Testing, Project Management and Wiki and README Design |
| Ing Tian   | 35 | RESTful services, Build system, Unit testing, RESTful Testing and Project Management |
| Kevin Li   | 38 | RESTful services, Build system, Unit testing, RESTful Testing, Project Record and Test Report |
| Xiang Li   | 33 | RESTful services, Build system, Unit testing and RESTful Testing |

***

## Deliverable 3

The Meeting Minute can be found [here](https://github.com/McGill-ECSE321-Winter2021/project-group-11/wiki/Meeting-Minutes-for-Sprint-3)

| Team member | Total hours | Responsibility |
|------------------ |:-------------:| ---------------|
| Ao Shen    | 65 | Pages design, Architecture model, RESTful Testing, Frontend Testing | 
| Byron Chen | 63 | Pages design, Architecture model, RESTful Testing, Frontend Testing, Wiki and README Design |
| Ing Tian   | 68 | Pages design, Architecture model, RESTful Testing, Frontend Testing, Project Management |
| Kevin Li   | 65 | Pages design, Architecture model, RESTful Testing, Frontend Testing, Project Record and User guide |
| Xiang Li   | 62 | Pages design, Architecture model, RESTful Testing, Frontend Testing |

***
