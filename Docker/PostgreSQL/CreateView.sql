DROP VIEW IF EXISTS user_profile_view;
DROP VIEW IF EXISTS book_inventory_view;
DROP VIEW IF EXISTS book_copy_loan_count_view;

-- 用户信息视图
CREATE VIEW user_profile_view AS
SELECT
    u.uuid, u.username, u.email, u.status, u.created_at,
    s.college, s.major, s.student_id, s.grade,
    s.admission_year, s.class_name, s.degree_type,
    b.max_borrow_limit, b.current_loans
FROM
    users u
LEFT JOIN
    student_info s ON u.uuid = s.user_id
LEFT JOIN
    borrower_info b ON u.uuid = b.user_id;

-- 创建库存视图
CREATE VIEW book_inventory_view AS
SELECT
    b.isbn,
    COUNT(bc.copy_id) AS current_copy_count,
    SUM(CASE WHEN bc.status = 'AVAILABLE' THEN 1 ELSE 0 END) AS available_count
FROM
    book b
        LEFT JOIN
    book_copy bc ON b.isbn = bc.isbn
GROUP BY
    b.isbn;

-- 图书副本借阅计数视图
CREATE VIEW book_copy_loan_count_view AS
SELECT bc.copy_id,
       COUNT(lr.record_id) AS calculated_loan_count
FROM book_copy bc
         LEFT JOIN
     loan_record lr ON bc.copy_id = lr.copy_id
         AND lr.status IN ('BORROWED', 'OVERDUE')
GROUP BY bc.copy_id;