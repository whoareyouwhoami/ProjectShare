CREATE DATABASE IF NOT EXISTS ShareDB;

USE ShareDB;


CREATE TABLE IF NOT EXISTS User (
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


CREATE TABLE IF NOT EXISTS Role (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(45),
  PRIMARY KEY (id)
);

INSERT INTO Role(name) VALUES("USER");


CREATE TABLE IF NOT EXISTS UserRole (
  user_id INT NOT NULL,
  role_id INT NOT NULL,

  PRIMARY KEY (user_id, role_id),
  CONSTRAINT fk_user_role_role_id FOREIGN KEY (role_id) REFERENCES Role (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_user_role_user_id FOREIGN KEY (user_id) REFERENCES User (id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS Project (
    id INT NOT NULL AUTO_INCREMENT,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    member INT NOT NULL DEFAULT 0,
    dateStart DATE NOT NULL,
    dateEnd DATE NOT NULL,
    dateUpload TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    dateModified TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    status TINYINT(1) NOT NULL DEFAULT 0,
    author_id INT NOT NULL,

    PRIMARY KEY(id),
    CONSTRAINT fk_user_project_author_id FOREIGN KEY (author_id) REFERENCES User (id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS ProjectUser (
  member_id INT NOT NULL,
  project_id INT NOT NULL,
  joined TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (member_id, project_id),
  CONSTRAINT fk_user_project_member_id FOREIGN KEY (member_id) REFERENCES User (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_user_project_project_id FOREIGN KEY (project_id) REFERENCES Project (id) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS MessageChat (
  id INT NOT NULL AUTO_INCREMENT,
  project_id INT,
  user_id INT,
  name VARCHAR(255),
  emptyMessage BOOLEAN DEFAULT 0,
  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (id),
  CONSTRAINT fk_message_chat_project_id FOREIGN KEY (project_id) REFERENCES Project (id) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT fk_message_chat_user_id FOREIGN KEY (user_id) REFERENCES User (id) ON DELETE SET NULL ON UPDATE CASCADE
);


-- CHANGE content TYPE LATER
CREATE TABLE IF NOT EXISTS MessageChatDetail (
  id INT NOT NULL AUTO_INCREMENT,
  message_id INT NOT NULL,
  sender_id INT,
  content VARCHAR(255),
  sent TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (id),
  CONSTRAINT fk_message_detail_message_id FOREIGN KEY (message_id) REFERENCES MessageChat (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_message_detail_sender_id FOREIGN KEY (sender_id) REFERENCES User (id) ON DELETE SET NULL ON UPDATE CASCADE
);


CREATE TABLE IF NOT EXISTS MessageProject (
  id INT NOT NULL AUTO_INCREMENT,
  project_id INT NOT NULL,
  name VARCHAR(255),
  created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY(id),
  CONSTRAINT fk_message_project_project_id FOREIGN KEY (project_id) REFERENCES Project (id) ON DELETE CASCADE ON UPDATE CASCADE
);


-- CHANGE content TYPE LATER
CREATE TABLE IF NOT EXISTS MessageProjectDetail (
  id INT NOT NULL AUTO_INCREMENT,
  message_project_id INT NOT NULL,
  sender_id INT,
  content VARCHAR(255),
  sent TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (id),
  CONSTRAINT fk_message_project_detail_message_project_id FOREIGN KEY (message_project_id) REFERENCES MessageProject (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_message_project_detail_sender_id FOREIGN KEY (sender_id) REFERENCES User (id) ON DELETE SET NULL ON UPDATE CASCADE  
);


CREATE TABLE IF NOT EXISTS MessageProjectUser (
  user_id INT NOT NULL,
  message_project_id INT NOT NULL,
  joined TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

  PRIMARY KEY (user_id, message_project_id),
  CONSTRAINT fk_message_project_user_user_id FOREIGN KEY (user_id) REFERENCES User (id) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_message_project_user_message_project_id FOREIGN KEY (message_project_id) REFERENCES MessageProject (id) ON DELETE CASCADE ON UPDATE CASCADE
);