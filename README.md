# SmartStudent

A comprehensive Spring Boot application designed to help students manage their finances effectively by tracking expenses, income, and budget allocations.

## 📋 Features

- **User Authentication & Authorization**: Secure JWT-based authentication with email verification
- **Expense Management**: Track and categorize daily expenses
- **Income Tracking**: Monitor various income sources
- **Category Management**: Organize transactions with custom categories
- **Dashboard Analytics**: Visualize spending patterns and financial summaries
- **Transaction Filtering**: Advanced filtering capabilities for financial data
- **Profile Management**: Customize user preferences and settings
- **Recent Transactions**: Quick access to latest financial activities

## 🛠️ Technology Stack

- **Backend Framework**: Spring Boot 3.5.5
- **Security**: Spring Security with JWT Authentication
- **Database**: MongoDB
- **Build Tool**: Maven
- **Java Version**: 17+
- **Image/Container**: Docker

## 🚀 Getting Started

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- MongoDB
- Docker (optional)

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/shihabhasan0161/SmartStudent-Backend.git
   cd StudentBudgetTracker/studentexpensetracker
    ```
2. **Configure application.properties**
    Update the `src/main/resources/application.properties` file with your MongoDB connection details and JWT secret key.
3. **Build the project**
   ```bash
   mvn clean install
   ```
4. **Run the application**
    ```bash
    mvn spring-boot:run
     ```
5. Using Docker (optional)
   - Build Docker image
     ```bash
     docker build -t smartstudent-backend .
     ```
   - Run Docker container
     ```bash
     docker run -d -p 8080:8080 --name smartstudent smartstudent-backend
     ```
     
## API Endpoints
Authentication:
- POST /api/v1/register - Register new user
- POST /api/v1/login - User login
- GET /api/v1/activate - Account activation via email

Expenses:
- GET /api/v1/expenses - Get all expenses
- POST /api/v1/expenses - Create new expense
- DELETE /api/v1/expenses/{id} - Delete expense

Income:
- GET /api/v1/incomes - Get all income entries
- POST /api/v1/incomes - Create new income
- DELETE /api/v1/income/{id} - Delete income

Categories:
- GET /api/v1/categories - Get all categories
- GET /api/v1/categories/{type} - Get category by expenses/incomes type
- POST /api/v1/categories - Create new category
- PUT /api/v1/categories/{categoryId} - Update category

Dashboard:
- GET /api/v1/dashboard - Get dashboard data

## Contributing:
1. Fork the repository
2. Create your feature branch (git checkout -b feature/AmazingFeature)
3. Commit your changes (git commit -m 'Add some AmazingFeature')
4. Push to the branch (git push origin feature/AmazingFeature)
5. Open a Pull Request
