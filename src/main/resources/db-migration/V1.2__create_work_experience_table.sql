CREATE TABLE IF NOT EXISTS work_experience_table (
    work_experience_id INT AUTO_INCREMENT PRIMARY KEY,
    job_title VARCHAR(5000) NOT NULL,
    company VARCHAR(5000) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    location VARCHAR(255),
    location_type VARCHAR(10),
    currently_working_flag TINYINT(1) NOT NULL DEFAULT 1
);

CREATE TABLE IF NOT EXISTS work_experience_skill_table (
    work_experience_skill_id INT AUTO_INCREMENT PRIMARY KEY,
    work_experience_id INT NOT NULL,
    skills VARCHAR(255) NOT NULL,
    FOREIGN KEY (work_experience_id) REFERENCES work_experience_table(work_experience_id)
);

CREATE TABLE IF NOT EXISTS work_experience_description_table (
    work_experience_description_id INT AUTO_INCREMENT PRIMARY KEY,
    work_experience_id INT NOT NULL,
    description MEDIUMTEXT NOT NULL,
    FOREIGN KEY (work_experience_id) REFERENCES work_experience_table(work_experience_id)
);