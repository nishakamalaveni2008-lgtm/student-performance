# Software Requirements Specification (SRS)
## 1. Introduction
**Purpose:** Build a web-based Student Academic Performance and Result Analysis System for maintaining student, subject and assessment records and producing result reports.

**Scope:** The system supports student and subject registration, mark entry/update, automatic result calculation, student reports, and Python-based performance analysis.

## 2. Overall Description
**Users:** Department staff / administrator.  
**Operating environment:** Java 17, Spring Boot, Maven, MySQL 8+, Python 3.10+.  
**Assumptions:** Each student-subject pair has one consolidated result record. Internal and exam marks together cannot exceed the subject maximum.

## 3. Functional Requirements
- FR1: Add and maintain student ID, name, department and year.
- FR2: Add subjects with maximum and pass marks.
- FR3: Record or update internal and examination marks.
- FR4: Validate marks and calculate total, percentage, grade and status.
- FR5: Display student-wise result reports and a result table.
- FR6: Provide result data through JSON endpoints for integration.
- FR7: Retrieve and preprocess records in Python and create charts.

## 4. Non-Functional Requirements
- Usability: responsive browser interface with clear forms and feedback.
- Reliability: database constraints prevent duplicate student-subject results.
- Performance: indexed primary/foreign keys support normal classroom-scale queries.
- Maintainability: layered Java model/repository/service/controller structure.
- Security: production deployment must add authentication, authorization, CSRF protection and secrets management.

## 5. Business Rules
- Total = Internal marks + Exam marks.
- Percentage = Total / Subject maximum × 100.
- PASS if total >= subject pass mark; otherwise FAIL.
- Grade: O >=90, A+ >=80, A >=70, B+ >=60, B >=50, C >=40, RA <40.
- These are sample configurable academic rules; replace them with the institution's approved regulations.

## 6. Inputs and Outputs
Inputs: student data, subject data, internal marks and exam marks.  
Outputs: calculated total, percentage, grade, pass/fail status, student result report and departmental/subject analysis charts.

## 7. Constraints
Requires MySQL connectivity and a Java 17 runtime. Python analysis requires a separately installed Python environment and dependencies.
