CREATE TABLE parent (
  parentId   INT PRIMARY KEY AUTO_INCREMENT,
  parentName VARCHAR(255)
);
CREATE TABLE child (
  childId   INT PRIMARY KEY AUTO_INCREMENT,
  parentId  INT,
  childName VARCHAR(255)
);

INSERT INTO parent (parentName)
VALUES ('parent-init-1'),
       ('parent-init-2'),
       ('parent-init-3');
INSERT INTO child (childName, parentId)
VALUES ('child-init-1', 1),
       ('child-init-2', 1),
       ('child-init-3', 1);

CREATE TABLE order_detail(
  id   INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  sku  VARCHAR(255),
  c_time TIMESTAMP
);