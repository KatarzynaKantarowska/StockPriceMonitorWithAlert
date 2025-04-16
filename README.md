# Stock Price Monitor Application

## Overview
The **Stock Price Monitor** application is a web-based tool designed to monitor cryptocurrency prices and provide historical snapshots. It integrates with external APIs to fetch real-time cryptocurrency prices and exchange rates, allowing users to view current prices, historical data, and manage snapshots. The application is built using **Java**, **Spring Boot**, and **Vaadin** for the frontend.

### Features
- Display real-time cryptocurrency prices (e.g., BTC, ETH, XRP).
- Convert cryptocurrency prices to PLN using exchange rates from NBP.
- View historical snapshots of cryptocurrency prices.
- Manage snapshots (create, update, delete).
- REST API endpoints for interacting with cryptocurrency data.
- User management functionality.

---

## Technologies Used
- **Java 17**
- **Spring Boot 3.4.4**
- **Vaadin Flow** for UI
- **MySQL** for database
- **Gradle** for dependency management
- **Lombok** for reducing boilerplate code
- **Binance API** for cryptocurrency prices
- **NBP API** for PLN to USD exchange rates

---

## How to Set Up Locally

### Prerequisites
1. **Java Development Kit (JDK)**: Ensure you have JDK 17 or higher installed.
2. **MySQL Database**: Install and configure MySQL.
3. **Gradle**: Ensure Gradle is installed or use the Gradle wrapper provided in the project.
4. **IDE**: Use an IDE like IntelliJ IDEA for development.

### Steps to Set Up
1. **Clone the Repository**:
   ```bash
   https://github.com/KatarzynaKantarowska/StockPriceMonitorWithAlert
   ```

2. **Configure the Database**
   - Create a MySQL database named `crypto_db`.
   - Update the `src/main/resources/application.properties` file with your database credentials:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/crypto_db
     spring.datasource.username=<your_db_username>
     spring.datasource.password=<your_db_password>
     ```
3. **Build the Project**
   - Navigate to the project directory and run the following command to build the project:
     ```bash
     ./gradlew build
     ```
    - This will download the necessary dependencies and compile the project.
Run the Application: Start the application using:  

4. **Access the Application: Open your browser and navigate to**
   - The application will be available at: 
   - http://localhost:8080 or to http://localhost:8080/users