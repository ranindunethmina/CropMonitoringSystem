# Crop Monitoring System - Green Shadow (Pvt) Ltd.

This project is a comprehensive crop monitoring system developed for Green Shadow (Pvt) Ltd., a mid-scale farm specializing in root crops and cereals. The system leverages the Spring Boot framework to provide services for managing field operations, crop monitoring, resource allocation, and data analysis. The application is designed to support CRUD operations across various entities such as fields, crops, staff, vehicles, and equipment.

## Table of Contents

- [Project Overview](#project-overview)
- [System Requirements](#system-requirements)
- [Key Features](#key-features)
- [Installation](#installation)
- [Technologies Used](#technologies-used)
- [Entity Models](#entity-models)
- [Project Structure](#project-structure)
- [Endpoints](#endpoints)
- [Future Enhancements](#future-enhancements)

---

## Project Overview

Green Shadow (Pvt) Ltd. has expanded their production facilities and requires a digital solution to manage crop and asset data effectively. This application provides an enterprise-level solution using Spring Boot and MySQL, enabling administrators, managers, and scientists to oversee field activities, crop growth, and resource allocations.

## System Requirements

1. **Java** - JDK 21 or above
2. **MySQL** - For relational database management
3. **Spring Boot** - Application framework
4. **Spring Security & OAuth2** - For authentication and authorization
5. **Git** - For version control
6. **Maven** - For dependency management

## Key Features

- **User Access Control**: Supports user roles (MANAGER, ADMINISTRATIVE, SCIENTIST) with varying permissions.
- **CRUD Operations**: Allows CRUD operations on fields, crops, staff, vehicles, and equipment.
- **Monitoring Logs**: Logs crop observations and field activities for effective monitoring.
- **Data Analysis**: Provides relational and spatial analysis features.
- **Secure Authentication**: Implements Spring Security with OAuth 2.0 for secure access.

## Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/ranindunethmina/CropMonitoringSystem.git
    cd crop-monitoring-system
    ```

2. Configure the application properties:

   Update `src/main/resources/application.properties` with your MySQL database credentials:

    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/crop_monitoring
    spring.datasource.username=your-username
    spring.datasource.password=your-password
    ```

3. Build and run the project:

    ```bash
    mvn clean install
    mvn spring-boot:run
    ```

4. Access the application on `http://localhost:5055`.

## Technologies Used

- **Front End**: HTML, CSS, JavaScript, jQuery, AJAX
- **Back End**: Spring Boot, Spring Data, Spring Web MVC, Spring Validation, Spring Security, MySQL
- **Security**: JWT, OAuth 2.0, Spring Security
- **Other**: Lombok, Model Mapper, Jackson

## Entity Models

### Main Entities

- **Field**: Represents areas for cultivation, with crop and staff assignments.
- **Crop**: Information on crop types, growth stages, and field allocations.
- **Staff**: Details on employees, their roles, and assigned tasks.
- **Vehicle**: Vehicles assigned to staff for field support.
- **Equipment**: Agricultural equipment used for field operations.
- **Monitoring Log**: Observations and activity logs for fields and crops.
- **User**: Authentication and role management for users.

### Relationships

- **Field** ↔ **Crop**
- **Field** ↔ **Staff**
- **MonitoringLog** ↔ **Field**, **Crop**, **Staff**
- **Staff** ↔ **Vehicle** and **Equipment**

## Project Structure

```plaintext
crop-monitoring-system/
├── src/main/java/com/greenshadow
│   ├── controller/     # API controllers
│   ├── repository/     # JPA repositories
│   ├── service/        # Service layer
│   ├── config/         # Security and configuration classes
│   └── Application.java
├── src/main/resources/
│   ├── application.properties
│   ├── static/         # Static resources (HTML, CSS)
│   └── templates/      # Thymeleaf templates
└── pom.xml             # Project dependencies
```

## Endpoints

- **Auth Service**
    - `POST /auth/login` - Login a user
    - `POST /auth/register` - Register a new user

- **Field Service**
    - `GET /fields` - Retrieve all fields
    - `POST /fields` - Create a new field
    - `PUT /fields/{id}` - Update a field
    - `DELETE /fields/{id}` - Delete a field

- **Crop Service**
    - `GET /crops` - Retrieve all crops
    - `POST /crops` - Create a new crop
    - `PUT /crops/{id}` - Update a crop
    - `DELETE /crops/{id}` - Delete a crop

- **Staff Service**
    - `GET /staff` - Retrieve all staff
    - `POST /staff` - Add a new staff member
    - `PUT /staff/{id}` - Update staff details
    - `DELETE /staff/{id}` - Delete a staff member

- **Vehicle and Equipment Services**
    - Similar CRUD endpoints for managing vehicles and equipment.

## Future Enhancements

1. **Advanced Reporting**: Introduce additional reports for better data visualization and analysis.
2. **Mobile Access**: Develop a mobile application for field staff to access information remotely.
3. **IoT Integration**: Integrate sensors for real-time monitoring of crop health and environmental conditions.
4. **Automated Alerts**: Provide alerts for crop health or equipment maintenance based on predefined thresholds.

---

## Contributing

Contributions are welcome! Please fork this repository and submit a pull request with your changes.

## License

This project is licensed under the MIT License. See the [LICENSE](License) file for more details.
