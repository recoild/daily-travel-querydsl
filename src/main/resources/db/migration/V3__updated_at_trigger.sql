
CREATE OR REPLACE TRIGGER users_before_update
BEFORE UPDATE ON users
FOR EACH ROW
BEGIN
    :NEW.updated_at := SYSDATE;
END;

CREATE OR REPLACE TRIGGER post_before_update
BEFORE UPDATE ON post
FOR EACH ROW
BEGIN
    :NEW.updated_at := SYSDATE;
END;

CREATE OR REPLACE TRIGGER comments_before_update
BEFORE UPDATE ON comments
FOR EACH ROW
BEGIN
    :NEW.updated_at := SYSDATE;
END;


