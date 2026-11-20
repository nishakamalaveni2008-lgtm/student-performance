USE student_performance;
-- Student-wise report
SELECT s.student_id,s.name,sub.subject_name,m.total_marks,m.average,m.grade,m.status
FROM marks m JOIN students s ON s.student_id=m.student_id JOIN subjects sub ON sub.subject_id=m.subject_id
ORDER BY s.student_id,sub.subject_name;
-- Subject-wise pass percentage
SELECT sub.subject_name,COUNT(*) AS appeared,
 SUM(m.status='PASS') AS passed,
 ROUND(100.0*SUM(m.status='PASS')/COUNT(*),2) AS pass_percentage
FROM marks m JOIN subjects sub ON sub.subject_id=m.subject_id GROUP BY sub.subject_id,sub.subject_name;
-- Department-wise average
SELECT s.department,ROUND(AVG(m.average),2) AS department_average,COUNT(DISTINCT s.student_id) AS students
FROM students s JOIN marks m ON m.student_id=s.student_id GROUP BY s.department;
-- Students who failed in at least one subject
SELECT DISTINCT s.student_id,s.name FROM students s JOIN marks m ON m.student_id=s.student_id WHERE m.status='FAIL';
-- Update marks (example)
-- UPDATE marks SET internal_marks=26, exam_marks=65, total_marks=91, average=91, grade='O', status='PASS' WHERE student_id='25IT001' AND subject_id='IT301';
-- Delete a result record (example)
-- DELETE FROM marks WHERE student_id='25CS001' AND subject_id='IT301';
