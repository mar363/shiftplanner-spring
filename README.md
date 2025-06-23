SuperMarket Shift Planner (Console & REST API)

Description
This project is a Java Spring Boot application that manages shift plannig for
employees

key features: 
-a default ADMIN user exists(username: admin, password:admin123) seeded at startup
- ADMIN can create EMPLOYEE users via REST endpoint
- EMPLOYEES can submit shift preferences in wishbook for a specific dates
   - EARLY 7:00-15:30
   - LATE 11:30-20:00
-ADMIN can plan the daily schedule based on employee preferences - two diff
  employees per day, one per shift
- both ADMIN and EMPLOYEE can view the schedule for a specific date via REST endpoints
- data is persisted in H2 database (file-based) with automatic schema creation
-no FE included, API endpoinds can be tested via Postman

Required:
- Java 11+ installed
- Maven instaled
- Postman for API testing

1. Setup and clone
 git clone https://github.com/mar363/shiftplanner-spring.git
 cd shiftplanner-spring
2. Configure application properties: By default the app uses H2
file-based database. In src/main/resources/application.properties

spring.datasource.url=jdbc:h2:file:./data/shiftplanner;AUTO_SERVER=TRUE
spring.datasource.username=sa
spring.datasource.password=
#spring.datasource.driverClassName=org.h2.Driver

if you prefer in mem you can change url with: jdbc:h2:mem:shiftplanner;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE

3. Build and run
mvn clean install
mvn spring-boot:run
4. Access H2 Console
Open browser: http://localhost:8080/h2-console
JDBC URL: use the same url as in application,properties
User: sa
Password: 
You can inspect tables: users, wishes, assignments

API Documentation
In this demo use:
ADMIN: 'admin' / 'admin123'
EMPLOYEE: 'user1' / 'user123'

1 Create Employee
ONLY ADMIN - Basic Auth admin/admin123
Enpoint: POST /api/employees
Description: Create new employee
Request Body(JSON)
{
"adminId": 1,
"username": userTest,
"password": userTest123
}
adminId must refer to an existing ADMIN user. Default ADMIN is admin
password: admin123
there is no public endpoint to create another ADMIN, only EMPLOYEE 
creation provided

response:
{
"id": 7,
"username": "userTest",
"role": "EMPLOYEE"
}
2. Submit shift preferences (wish) by employee
Endpoint: POST /api/wishes
Description: EMPLOYEE submits their preferred shift for a date
Request Body: 
{
"employeeId": 7,
"date": "2025-07-01",
"shift":  "EARLY"
}
Response:
   {
   "id": 4,
   "employeeId": 7,
   "date": "2025-07-01",
   "shift": "LATE"
   }

Validation: employeesId must exists and have role EMPLOYEE
date format YYYY-MM-DD
shift EARLY or LATE
3. Get Wishes by Date(all shifts)
Enpoints: GET /api/wishes?date=YYYY-MM-DD
Description: Returns all wishes for the given date, both EARLY and LATE
exemple:  curl -i "http://localhost:8080/api/wishes?date=2025-06-30"
response: [
   {
   "id": 4,
   "employeeId": 7,
   "date": "2025-07-01",
   "shift": "LATE"
   }
   ]
or: Enpoints: GET /api/wishes?date=YYYY-MM-DD&shift={EARLY|LATE}
    Description: Returns all wishes for the given date, just for one type of shift
curl -i  http://localhost:8080/api/wishes?date=2025-06-30&shift=LATE
 response:  [
   {
   "id": 2,
   "employeeId": 5,
   "date": "2025-06-30",
   "shift": "LATE"
   }
   ]
4. Plan Schedule for a date (ADMIN only)
Enpoint POST /api/planning
Description: Generate assignments for the given date based on wihses
Request Body
_{
"adminId":1,
"date": "2025-07-01"
}_
Response:
   [
   {
   "id": 9,
   "employeeId": 2,
   "date": "2025-07-01",
   "shift": "EARLY"
   },
   {
   "id": 10,
   "employeeId": 7,
   "date": "2025-07-01",
   "shift": "LATE"
   }
   ]
Logic: 
-Deletes existing assignments for the date
-Fetches all EMPLOYEES users. Must be two
-Fetches wishes for EARLY and LATE
-Chooses EARLY: first wish or first available
-Chooses LATE: excludes EARLY choise, picks first wish or next available
-Save assignments in one transaction, on error, rollback
5. View Schedule for a date
Enpoint GET /api/schedule?date=YYYY-MM-DD
Description: Returns assignments for the given date
Example: curl -i "http://localhost:8080/api/schedule?date=2025-07-01"
Response
   [
   {
   "id": 9,
   "employeeId": 2,
   "date": "2025-07-01",
   "shift": "EARLY"
   },
   {
   "id": 10,
   "employeeId": 7,
   "date": "2025-07-01",
   "shift": "LATE"
   }
   ]
Testing with Postman
1. Import collection. You can manually create request in Postman matching the endpoints
above.
2. Enviroment variables
 - Set {{baseUrl}} =http://localhost:8080
 - Set {{adminId}}=1, {{employeeId}} as needed
   3. Requests
   -Create EMPLOYEE: POST {{baseUrl}}/api/employees 
    body:
      {
      "adminId": 1,
      "username": ,
      "password": 
      }
   -Add Wish: POST {{baseUrl}}/api/wishes
      body
      {
      "employeeId": ,
      "date": "",
      "shift": " "
      }
   -Get Wishes: GET {{baseUrl}}/api/wishes?date=2025-06-30
                GET {{baseUrl}}/api/wishes?date=2025-06-30&shift=LATE
   -Plan: POST {{baseUrl}}/api/planning
   body
      _{
      "adminId":1,
      "date": ""
      }
   - View schedule: GET {{baseUrl}}/api/schedule

Run and validate: Send requests in order
 