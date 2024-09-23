-- 트리거 생성: users 테이블
CREATE OR REPLACE TRIGGER trg_users_updated_at
BEFORE UPDATE ON users
FOR EACH ROW
BEGIN
  :NEW.updated_at := CURRENT_TIMESTAMP;
END;
/
-- 트리거 생성: post 테이블
CREATE OR REPLACE TRIGGER trg_post_updated_at
BEFORE UPDATE ON post
FOR EACH ROW
BEGIN
  :NEW.updated_at := CURRENT_TIMESTAMP;
END;
/
-- 트리거 생성: comments 테이블
CREATE OR REPLACE TRIGGER trg_comments_updated_at
BEFORE UPDATE ON comments
FOR EACH ROW
BEGIN
  :NEW.updated_at := CURRENT_TIMESTAMP;
END;
/