-- 触发器
CREATE TRIGGER trigger_update_user_modified_time
    BEFORE UPDATE
    ON users
    FOR EACH ROW
EXECUTE FUNCTION update_user_modified_time();