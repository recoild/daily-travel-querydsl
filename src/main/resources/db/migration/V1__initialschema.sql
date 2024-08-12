CREATE TABLE users (
    users_id NUMBER PRIMARY KEY,
    uuid VARCHAR2(255) NOT NULL,
    email VARCHAR2(255) NOT NULL,
    nickname VARCHAR2(255) NOT NULL,
    profile_image_path VARCHAR2(255),
    created_at DATE DEFAULT SYSDATE NOT NULL,
    updated_at DATE,
    is_deleted NUMBER(1) DEFAULT 0
);

CREATE TABLE post (
    post_id NUMBER PRIMARY KEY,
    post_title VARCHAR2(255) NOT NULL,
    post_content VARCHAR2(4000) NOT NULL,
    place_name VARCHAR2(255),
    likes_count NUMBER DEFAULT 0,
    thumbnail VARCHAR2(255),
    latitude NUMBER(9,6),
    longitude NUMBER(9,6),
    created_at DATE DEFAULT SYSDATE NOT NULL,
    updated_at DATE,
    users_id NUMBER NOT NULL,
    CONSTRAINT fk_post_users FOREIGN KEY (users_id)
        REFERENCES users(users_id)
);


CREATE TABLE comments (
    comments_id NUMBER PRIMARY KEY,
    post_id NUMBER NOT NULL,
    comments_content VARCHAR2(255) NOT NULL,
    created_at DATE DEFAULT SYSDATE NOT NULL,
    updated_at DATE,
    users_id NUMBER NOT NULL,
    CONSTRAINT fk_comments_post FOREIGN KEY (post_id)
        REFERENCES post(post_id),
    CONSTRAINT fk_comments_users FOREIGN KEY (users_id)
        REFERENCES users(users_id)
);

CREATE TABLE image (
    image_id NUMBER PRIMARY KEY,
    post_id NUMBER NOT NULL,
    image_path VARCHAR2(255) NOT NULL,
    CONSTRAINT fk_image_post FOREIGN KEY (post_id)
        REFERENCES post(post_id)
);

CREATE TABLE likes (
    likes_id NUMBER PRIMARY KEY,
    post_id NUMBER NOT NULL,
    users_id NUMBER NOT NULL,
    CONSTRAINT fk_likes_post FOREIGN KEY (post_id)
        REFERENCES post(post_id),
    CONSTRAINT fk_likes_users FOREIGN KEY (users_id)
        REFERENCES users(users_id)
);

CREATE TABLE hashtag (
    hashtag_id NUMBER PRIMARY KEY,
    hashtag_name VARCHAR2(255) NOT NULL
);

CREATE TABLE post_hashtag (
    post_hashtag_id NUMBER PRIMARY KEY,
    post_id NUMBER NOT NULL,
    hashtag_id NUMBER NOT NULL,
    CONSTRAINT fk_post_hashtag_post FOREIGN KEY (post_id)
        REFERENCES post(post_id),
    CONSTRAINT fk_post_hashtag_hashtag FOREIGN KEY (hashtag_id)
        REFERENCES hashtag(hashtag_id)
);

CREATE INDEX idx_post_hashtag_post_id ON post_hashtag(post_id);
CREATE INDEX idx_post_hashtag_hashtag_id ON post_hashtag(hashtag_id);

CREATE SEQUENCE users_seq
START WITH 1
INCREMENT BY 1
NOCACHE
NOCYCLE;

CREATE SEQUENCE post_seq
START WITH 1
INCREMENT BY 1
NOCACHE
NOCYCLE;

CREATE SEQUENCE comments_seq
START WITH 1
INCREMENT BY 1
NOCACHE
NOCYCLE;

CREATE SEQUENCE image_seq
START WITH 1
INCREMENT BY 1
NOCACHE
NOCYCLE;

CREATE SEQUENCE likes_seq
START WITH 1
INCREMENT BY 1
NOCACHE
NOCYCLE;

CREATE SEQUENCE hashtag_seq
START WITH 1
INCREMENT BY 1
NOCACHE
NOCYCLE;

CREATE SEQUENCE post_hashtag_seq
START WITH 1
INCREMENT BY 1
NOCACHE
NOCYCLE;