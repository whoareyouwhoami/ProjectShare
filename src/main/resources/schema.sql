CREATE TABLE User (
	id INT NOT NULL AUTO_INCREMENT,
	firstName VARCHAR(255) NOT NULL,
	lastName VARCHAR(255) NOT NULL,
	gender VARCHAR(10) NOT NULL,
	email VARCHAR(255) UNIQUE NOT NULL,
	password VARCHAR(255) NOT NULL,
	dateOfBirth DATE NOT NULL,
	register TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

	accountNonExpired TINYINT(1),
	accountNonLocked TINYINT(1),
	credentialsNonExpired TINYINT(1),
	enabled TINYINT(1),

	PRIMARY KEY(id)
);

CREATE TABLE Role (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45),
  PRIMARY KEY (id)
);

CREATE TABLE UserRole (
  user_id INT NOT NULL,
  role_id INT NOT NULL,

  PRIMARY KEY (user_id, role_id),
  CONSTRAINT fk_user_role_roleid FOREIGN KEY (role_id) REFERENCES Role (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_user_role_userid FOREIGN KEY (user_id) REFERENCES User (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Project (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    member INT NOT NULL DEFAULT 0,
    dateStart DATE NOT NULL,
    dateEnd DATE NOT NULL,
    dateUpload TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    dateModified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    owner_id INT NOT NULL,
    visibility TINYINT(1) NOT NULL DEFAULT 0,
    status TINYINT(1) NOT NULL DEFAULT 0,

    PRIMARY KEY(id),
    CONSTRAINT fk_user_project_ownerid FOREIGN KEY (owner_id) REFERENCES User (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE ProjectUser (
  user_id INT NOT NULL,
  project_id INT NOT NULL,

  PRIMARY KEY (user_id, project_id),
  CONSTRAINT fk_user_project_userid FOREIGN KEY (user_id) REFERENCES User (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_user_project_projectid FOREIGN KEY (project_id) REFERENCES Project (id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE ChatMessageDetail (
  id INT NOT NULL AUTO_INCREMENT,
  projectId INT,
  userOne INT DEFAULT NULL,
  userTwo INT DEFAULT NULL,
  roomNumber BIGINT DEFAULT NULL UNIQUE,

  PRIMARY KEY (id),
  CONSTRAINT fk_message_project_id FOREIGN KEY (projectId) REFERENCES Project (id) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE ChatMessage (
  id INT NOT NULL AUTO_INCREMENT,
  messageId INT NOT NULL,
  fromUser VARCHAR(255) DEFAULT NULL,
  toUser VARCHAR(255) DEFAULT NULL,
  content VARCHAR(255) DEFAULT NULL,
  sentTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (id),
  CONSTRAINT fk_message_id FOREIGN KEY (messageId) REFERENCES ChatMessageDetail (id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE MessageChat (
  id INT NOT NULL AUTO_INCREMENT,
  project_id INT,
  user_id INT,
  name VARCHAR(255),
  emptyMessage BOOLEAN DEFAULT 0,
  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (id),
  CONSTRAINT fk_message_chat_project_id FOREIGN KEY (project_id) REFERENCES Project (id) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT fk_message_chat_user_id FOREIGN KEY (user_id) REFERENCES Project (id) ON DELETE SET NULL ON UPDATE CASCADE
);


CREATE TABLE MessageChatUser (
  project_id INT,
  user_id INT,
  messageRoom BIGINT DEFAULT NULL UNIQUE,

  PRIMARY KEY(project_id, user_id),
  CONSTRAINT fk_message_chat_user_project_id FOREIGN KEY (project_id) REFERENCES Project (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_message_chat_user_user_id FOREIGN KEY (user_id) REFERENCES Project (id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- CHANGE content TYPE LATER
CREATE TABLE MessageDetail (
  id INT NOT NULL AUTO_INCREMENT,
  message_id INT NOT NULL,
  sender_id INT,
  content VARCHAR(255),
  sent TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (id),
  CONSTRAINT fk_message_detail_message_id FOREIGN KEY (message_id) REFERENCES MessageChat (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_message_detail_sender_id FOREIGN KEY (sender_id) REFERENCES User (id) ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE MessageProject (
  id INT NOT NULL AUTO_INCREMENT,
  project_id INT NOT NULL,
  name VARCHAR(255),
  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY(id),
  CONSTRAINT fk_message_project_project_id FOREIGN KEY (project_id) REFERENCES Project (id) ON DELETE CASCADE ON UPDATE CASCADE
);

-- CHANGE content TYPE LATER
CREATE TABLE MessageProjectDetail (
  id INT NOT NULL AUTO_INCREMENT,
  message_project_id INT NOT NULL,
  sender_id INT,
  content VARCHAR(255),
  sent TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (id),
  CONSTRAINT fk_message_project_detail_message_project_id FOREIGN KEY (message_project_id) REFERENCES MessageProject (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_message_project_detail_sender_id FOREIGN KEY (sender_id) REFERENCES User (id) ON DELETE SET NULL ON UPDATE CASCADE  
);


CREATE TABLE MessageProjectUser (
  id INT NOT NULL AUTO_INCREMENT,
  user_id INT NOT NULL,
  message_project_id INT NOT NULL,
  joined TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (id),
  CONSTRAINT fk_message_project_user_user_id FOREIGN KEY (user_id) REFERENCES User (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_message_project_user_message_project_id FOREIGN KEY (message_project_id) REFERENCES MessageProject (id) ON DELETE CASCADE ON UPDATE CASCADE
);