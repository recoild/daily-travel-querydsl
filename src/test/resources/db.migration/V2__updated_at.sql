ALTER TABLE users
MODIFY updated_at DATE DEFAULT SYSDATE;

ALTER TABLE post
MODIFY updated_at DATE DEFAULT SYSDATE;

ALTER TABLE comments
MODIFY updated_at DATE DEFAULT SYSDATE;
