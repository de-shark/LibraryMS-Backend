DROP TABLE IF EXISTS loan_record;
DROP TABLE IF EXISTS book_copy;
DROP TABLE IF EXISTS book_category_mapping;
DROP TABLE IF EXISTS book_category;
DROP TABLE IF EXISTS book;
DROP TABLE IF EXISTS borrower_info;
DROP TABLE IF EXISTS student_info;
DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS users;

DROP TYPE IF EXISTS user_status_type;
DROP TYPE IF EXISTS user_role_type;
DROP TYPE IF EXISTS copy_status_type;
DROP TYPE IF EXISTS loan_status_type;

-- Users表
CREATE TYPE user_status_type AS ENUM ('UNVERIFIED', 'ACTIVE', 'BANNED');
CREATE TABLE "users"
(
    uuid          UUID PRIMARY KEY,
    username      VARCHAR(50)      NOT NULL UNIQUE,
    password_hash VARCHAR(255)     NOT NULL,
    email         VARCHAR(100)     NOT NULL UNIQUE,
    status        user_status_type NOT NULL DEFAULT 'ACTIVE',
    created_at    TIMESTAMPTZ               DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMPTZ               DEFAULT CURRENT_TIMESTAMP
);
COMMENT ON COLUMN users.uuid IS '用户唯一标识';
COMMENT ON COLUMN users.username IS '登录用户名';
COMMENT ON COLUMN users.password_hash IS '密码哈希值';
COMMENT ON COLUMN users.email IS '邮箱';
COMMENT ON COLUMN users.created_at IS '账户创建时间';
COMMENT ON COLUMN users.updated_at IS '最后更新时间';

-- UserRole表
CREATE TYPE user_role_type AS ENUM ('USER', 'LIBRARIAN', 'ADMIN');
CREATE TABLE user_role
(
    user_role_id UUID PRIMARY KEY,
    user_id      UUID           NOT NULL,
    role         user_role_type NOT NULL DEFAULT 'USER',
    assigned_at  TIMESTAMPTZ             DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_user_role_user
        FOREIGN KEY (user_id) REFERENCES users (uuid)
            ON DELETE CASCADE
);
COMMENT ON COLUMN user_role.assigned_at IS '授权时间';

CREATE TABLE student_info
(
    user_id        UUID PRIMARY KEY,
    college        VARCHAR(100) NOT NULL,
    major          VARCHAR(100) NOT NULL,
    student_id     VARCHAR(20)  NOT NULL UNIQUE,
    grade          VARCHAR(10),
    admission_year INT          NOT NULL,
    class_name     VARCHAR(50),
    degree_type    VARCHAR(20)  NOT NULL CHECK (degree_type IN ('本科', '硕士', '博士')),

    CONSTRAINT fk_student_info_user
        FOREIGN KEY (user_id) REFERENCES users(uuid)
            ON DELETE CASCADE
);
COMMENT ON COLUMN student_info.college IS '学院';
COMMENT ON COLUMN student_info.major IS '专业';
COMMENT ON COLUMN student_info.student_id IS '学号';
COMMENT ON COLUMN student_info.grade IS '年级';
COMMENT ON COLUMN student_info.admission_year IS '入学年份';
COMMENT ON COLUMN student_info.class_name IS '班级信息';
COMMENT ON COLUMN student_info.degree_type IS '学位类型';

-- 普通用户扩展表
CREATE TABLE borrower_info
(
    user_id          UUID PRIMARY KEY,
    max_borrow_limit INT            DEFAULT 5,
    current_loans    INT NOT NULL   DEFAULT 0,

    CONSTRAINT fk_borrower_info_user
        FOREIGN KEY (user_id) REFERENCES users (uuid)
            ON DELETE CASCADE
);

-- 图书信息表
CREATE TABLE book
(
    isbn           CHAR(13) PRIMARY KEY,
    isbn_10        CHAR(10) UNIQUE,
    title          VARCHAR(255) NOT NULL,
    author         VARCHAR(255) NOT NULL,
    publisher      VARCHAR(100) NOT NULL,
    published_year SMALLINT,
    language       VARCHAR(50),
    page_count     INT,
    cover_image    TEXT,
    description    TEXT,
    created_at     TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN book.isbn IS '国际标准书号(ISBN-13)';
COMMENT ON COLUMN book.title IS '图书标题';
COMMENT ON COLUMN book.author IS '作者';
COMMENT ON COLUMN book.publisher IS '出版社';
COMMENT ON COLUMN book.published_year IS '出版日期';
COMMENT ON COLUMN book.language IS '语言';
COMMENT ON COLUMN book.page_count IS '页数';
COMMENT ON COLUMN book.isbn_10 IS 'ISBN-10';
COMMENT ON COLUMN book.cover_image IS '封面图URL或Base64编码';
COMMENT ON COLUMN book.description IS '图书描述';
COMMENT ON COLUMN book.created_at IS '录入时间';

-- 图书分类表
CREATE TABLE book_category (
    category_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255)
);

-- 图书-分类关联表
CREATE TABLE book_category_mapping (
    isbn CHAR(13) NOT NULL,
    category_id INT NOT NULL,
    PRIMARY KEY (isbn, category_id),
    CONSTRAINT fk_book_category_mapping_book
        FOREIGN KEY (isbn) REFERENCES book(isbn)
        ON DELETE CASCADE,
    CONSTRAINT fk_book_category_mapping_category
        FOREIGN KEY (category_id) REFERENCES book_category(category_id)
        ON DELETE CASCADE
);

-- 图书副本表
CREATE TYPE copy_status_type AS ENUM ('AVAILABLE', 'BORROWED');
CREATE TABLE book_copy
(
    copy_id          UUID PRIMARY KEY,
    isbn             CHAR(13)         NOT NULL,
    location         VARCHAR(100),
    status           copy_status_type NOT NULL DEFAULT 'AVAILABLE',
    acquisition_date DATE,

    CONSTRAINT fk_book_copy_bookinfo
        FOREIGN KEY (isbn) REFERENCES book (isbn)
            ON DELETE CASCADE
);
COMMENT ON COLUMN book_copy.copy_id IS '副本唯一标识';
COMMENT ON COLUMN book_copy.location IS '馆藏位置';
COMMENT ON COLUMN book_copy.status IS '当前状态';
COMMENT ON COLUMN book_copy.acquisition_date IS '购入日期';

-- 借阅记录表
CREATE TYPE loan_status_type AS ENUM ('BORROWED', 'RETURNED', 'OVERDUE');
CREATE TABLE loan_record
(
    record_id   UUID PRIMARY KEY,
    copy_id     UUID             NOT NULL,
    user_id     UUID             NOT NULL,
    loan_date   TIMESTAMPTZ               DEFAULT CURRENT_TIMESTAMP,
    due_date    TIMESTAMPTZ      NOT NULL,
    return_date TIMESTAMPTZ,
    status      loan_status_type NOT NULL DEFAULT 'BORROWED',

    CONSTRAINT fk_loan_record_book_copy
        FOREIGN KEY (copy_id) REFERENCES book_copy (copy_id)
            ON DELETE CASCADE,
    CONSTRAINT fk_loan_record_user
        FOREIGN KEY (user_id) REFERENCES users (uuid)
            ON DELETE CASCADE
);
COMMENT ON COLUMN loan_record.copy_id IS '关联书籍副本';
COMMENT ON COLUMN loan_record.user_id IS '关联借阅者';
COMMENT ON COLUMN loan_record.loan_date IS '借出日期';
COMMENT ON COLUMN loan_record.due_date IS '应归还日期';
COMMENT ON COLUMN loan_record.return_date IS '实际归还日期';
COMMENT ON COLUMN loan_record.status IS '记录状态';
