CREATE TABLE IF NOT EXISTS project_table (
    project_id INT AUTO_INCREMENT PRIMARY KEY,
    project_name VARCHAR(255) NOT NULL,
    description TEXT,
    technologies_used VARCHAR(350) NOT NULL,
    STATUS VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS project_source_code_table (
    project_source_code_id INT AUTO_INCREMENT PRIMARY KEY,
    project_id INT NOT NULL,
    github_link TEXT,
    FOREIGN KEY (project_id) REFERENCES project_table(project_id)
);