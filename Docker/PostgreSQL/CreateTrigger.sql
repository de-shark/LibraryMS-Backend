-- 触发器
CREATE TRIGGER trigger_update_user_modified_time
    BEFORE UPDATE
    ON users
    FOR EACH ROW
EXECUTE FUNCTION update_user_modified_time();

-- 触发器函数
CREATE OR REPLACE FUNCTION update_user_modified_time()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;