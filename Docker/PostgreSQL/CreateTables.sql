DROP TABLE IF EXISTS borrowing_records;
DROP TABLE IF EXISTS book_copy;
DROP TABLE IF EXISTS book_info;
DROP TABLE IF EXISTS users;

-- 用户表
DROP TYPE IF EXISTS user_role;
DROP TYPE IF EXISTS user_status;
CREATE TYPE user_role AS ENUM ('READER', 'LIBRARIAN', 'ADMIN');
CREATE TYPE user_status AS ENUM ('UNVERIFIED', 'ACTIVE', 'BANNED');
CREATE TABLE "users" (
    uuid UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    role user_role NOT NULL DEFAULT 'READER',
    status       user_status NOT NULL DEFAULT 'UNVERIFIED',
    credit_score INT         NOT NULL DEFAULT 100 CHECK (credit_score BETWEEN 0 AND 100),
    created_at   TIMESTAMPTZ          DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMPTZ          DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN users.uuid IS '用户唯一标识';
COMMENT ON COLUMN users.username IS '登录用户名';
COMMENT ON COLUMN users.password IS '密码哈希值';
COMMENT ON COLUMN users.email IS '邮箱';
COMMENT ON COLUMN users.role IS '用户角色';
COMMENT ON COLUMN users.status IS '账户状态';
COMMENT ON COLUMN users.credit_score IS '信用分';
COMMENT ON COLUMN users.created_at IS '账户创建时间';
COMMENT ON COLUMN users.updated_at IS '最后更新时间';


-- 图书信息表
CREATE TABLE book_catalog
(
    isbn         CHAR(13) PRIMARY KEY,
    title        VARCHAR(255) NOT NULL,
    author       VARCHAR(255) NOT NULL,
    publisher    VARCHAR(100),
    publish_year SMALLINT,
    description  TEXT,
    cover_url    VARCHAR(512),
    created_at   TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

COMMENT ON COLUMN book_catalog.isbn IS '国际标准书号';
COMMENT ON COLUMN book_catalog.title IS '图书标题';
COMMENT ON COLUMN book_catalog.author IS '作者';
COMMENT ON COLUMN book_catalog.publisher IS '出版社';
COMMENT ON COLUMN book_catalog.publish_year IS '出版年份';
COMMENT ON COLUMN book_catalog.description IS '图书简介';
COMMENT ON COLUMN book_catalog.cover_url IS '封面图URL';
COMMENT ON COLUMN book_catalog.created_at IS '录入时间';


-- 图书副本表
DROP TYPE IF EXISTS copy_status;
CREATE TYPE copy_status AS ENUM ('AVAILABLE', 'BORROWED', 'UNDER_MAINTENANCE');
CREATE TABLE book_copy
(
    copy_id          UUID PRIMARY KEY,
    isbn             CHAR(13)    NOT NULL,
    status           copy_status NOT NULL DEFAULT 'AVAILABLE',
    location         VARCHAR(50),
    acquisition_date DATE,
    condition_rating SMALLINT CHECK (condition_rating BETWEEN 1 AND 5),
    last_maintain    TIMESTAMPTZ,

    CONSTRAINT fk_book_copy_bookinfo
        FOREIGN KEY (isbn) REFERENCES book_catalog (isbn)
            ON DELETE RESTRICT
);

COMMENT ON COLUMN book_copy.copy_id IS '副本唯一标识';
COMMENT ON COLUMN book_copy.isbn IS '所属图书ISBN';
COMMENT ON COLUMN book_copy.status IS '当前状态';
COMMENT ON COLUMN book_copy.location IS '馆藏位置';
COMMENT ON COLUMN book_copy.acquisition_date IS '购入日期';
COMMENT ON COLUMN book_copy.condition_rating IS '保存状况评分（1-5）';
COMMENT ON COLUMN book_copy.last_maintain IS '最近维护时间';


-- 借阅记录表
DROP TYPE IF EXISTS borrow_status;
CREATE TYPE borrow_status AS ENUM ('BORROWED', 'RETURNED', 'OVERDUE');
CREATE TABLE borrowing_records
(
    record_id   UUID PRIMARY KEY,
    copy_id     UUID          NOT NULL,
    patron_id   UUID          NOT NULL,
    borrow_date TIMESTAMPTZ            DEFAULT CURRENT_TIMESTAMP,
    due_date    TIMESTAMPTZ   NOT NULL,
    return_date TIMESTAMPTZ,
    fine_amount NUMERIC(10, 2)         DEFAULT 0.00,
    status      borrow_status NOT NULL DEFAULT 'BORROWED',

    CONSTRAINT fk_borrow_record_copy
        FOREIGN KEY (copy_id) REFERENCES book_copy (copy_id)
            ON DELETE CASCADE,
    CONSTRAINT fk_borrow_record_user
        FOREIGN KEY (patron_id) REFERENCES users (uuid)
            ON DELETE CASCADE
);

COMMENT ON COLUMN borrowing_records.copy_id IS '关联书籍副本';
COMMENT ON COLUMN borrowing_records.patron_id IS '关联借阅者';
COMMENT ON COLUMN borrowing_records.due_date IS '应归还日期';
COMMENT ON COLUMN borrowing_records.fine_amount IS '累计罚款金额';


-- 触发器函数
CREATE OR REPLACE FUNCTION update_user_modified_time()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- 触发器
CREATE TRIGGER trigger_update_user_modified_time
    BEFORE UPDATE
    ON users
    FOR EACH ROW
EXECUTE FUNCTION update_user_modified_time();
