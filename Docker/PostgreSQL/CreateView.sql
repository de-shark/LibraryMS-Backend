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