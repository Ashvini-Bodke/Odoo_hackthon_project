# HRMS Demo Script

## 1. Start Application
mvn spring-boot:run

## 2. Admin Login
curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d '{"email":"admin@hrms.com","password":"Admin@123"}'
→ Returns: admin@hrms.com, role: HR

## 3. Admin Dashboard
curl -X GET http://localhost:8080/api/admin/dashboard -H "Authorization: Bearer <token>"
→ Shows: 3 employees, 2 present, 1 absent

## 4. Employee Login (John Doe)
curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d '{"email":"john.doe@company.com","password":"Temp@123320"}'
→ Returns: john.doe@company.com, role: EMPLOYEE

## 5. Check-in
curl -X POST http://localhost:8080/api/attendance/checkin -H "Authorization: Bearer <token>"
→ Message: "Checked in successfully"

## 6. View Profile
curl -X GET http://localhost:8080/api/profile/me -H "Authorization: Bearer <token>"
→ Shows: John Doe, Mumbai, Java/Spring/React

## 7. Apply Leave
curl -X POST http://localhost:8080/api/leaves/apply -H "Authorization: Bearer <token>" -H "Content-Type: application/json" -d '{"type":"PAID","startDate":"2026-07-10","endDate":"2026-07-12","remarks":"Family function"}'
→ Status: PENDING

## 8. View Payroll
curl -X GET http://localhost:8080/api/payroll/me -H "Authorization: Bearer <token>"
→ Shows: Basic 37,500, HRA 15,000, Total 47,800
