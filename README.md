
# Loan Management System

Spring Boot application for managing loan approvals, generating monthly repayment plans, exporting amortization schedules to PDF, and notifying clients of overdue installments via email.

## Authors

- [@mirzapijuk](https://www.github.com/mirzapijuk)


##  🚀 Features

- 👤 **Customer Management**
  - Create, update, delete and list customers
- 💳 **Loan Processing**
  - Create and manage loans
  - Automatically open associated loan parties
- 📅 **Repayment Plan Generation**
  - Generate amortization schedule (annuity method)
  - Monthly installment calculation (principal + interest)
- 📄 **PDF Export**
  - Export repayment plan to PDF file
  - Download or send as email attachment to the customer
- 📩 **Email Notification**
  - Automatically send the repayment plan to customer via email
  - Daily scheduled check for overdue payments (5+ days late)
  - Sends warning emails to customers with delayed payments
- 💰 **Installment Payment Processing**
  - Process monthly loan repayments
  - Reduce remaining debt and track payment progress
- 🕒 **Automated Scheduler**
  - Daily cron job (9 AM) checks for unpaid installments and notifies clients
 - 🔐 **User Authentication & Roles**
   - Login system with Spring Security
   - Two roles: `ADMIN` and `USER`
   - Access restrictions based on role (RBAC)
 



## Tech Stack

Java 17

Spring Boot

Spring Data JPA (Hibernate)

Spring Security (Basic Authentication, Role-based Access Control)

Spring Mail (Email sending)

Spring Scheduling (Scheduled tasks / Cron jobs)

PostgreSQL

Apache PDFBox (PDF generation)

Maven (build and dependency management)




## API Reference

#### Get all customers

```http
  GET /customer
```



#### Get customer by ID

```http
  GET /customer/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `Song` | **Required**. Id of customer to fetch |

```http
  GET /customer/registration-no
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `registration-no`      | `String` | **Required**. Registration of customer to fetch |

```http
  POST /customer
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `CustomerRequest`      | `JSON` | **Required**. Customer data

```http
  PUT /customer/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. Id of customer to fetch
`CustomerRequest`      | `JSON` | **Required**. Updated customer data

```http
  GET /customer/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. Id of customer to fetch

```http
  GET /loan
```

```http
  GET /loan/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `long` | **Required**. Id of loan to fetch

```http
  POST /loan
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `LoanRequest`      | `JSON` | **Required**. Loan data

```http
  POST /loan/amortization
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `partyNumer`      | `long` | **Required**. Loan party number

```http
  GET /loan/schedule/pdf
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `partyNumer`      | `long` | **Required**. Loan party number

```http
  GET /loan/send-installment-to-customer
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `partyNumer`      | `long` | **Required**. Loan party number


```http
  GET /payment
```
```http
  POST /PAYMENT
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `PaymentRequest`      | `JSON` | **Required**. Payment data