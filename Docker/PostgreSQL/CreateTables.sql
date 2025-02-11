-- 创建 ENUM 类型
CREATE TYPE user_role AS ENUM ('reader', 'librarian', 'admin');
CREATE TYPE user_status AS ENUM ('unverified', 'activated', 'banned');

CREATE TABLE "users" (
    id UUID PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    role user_role NOT NULL DEFAULT 'reader',
    status user_status NOT NULL DEFAULT 'unverified'
)