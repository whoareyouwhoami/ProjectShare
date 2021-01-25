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