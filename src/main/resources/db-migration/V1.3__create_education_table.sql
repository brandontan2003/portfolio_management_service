CREATE TABLE IF NOT EXISTS education_table (
    education_id INT AUTO_INCREMENT PRIMARY KEY,
    school VARCHAR(1000) NOT NULL,
    degree VARCHAR(500) NOT NULL,
    field_of_studies VARCHAR(2000),
    start_date DATE NOT NULL,
    end_date DATE,
    grade VARCHAR(255),
    activities_and_societies VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS education_skill_table (
    education_skill_id INT AUTO_INCREMENT PRIMARY KEY,
    education_id INT NOT NULL,
    skills VARCHAR(255) NOT NULL,
    FOREIGN KEY (education_id) REFERENCES education_table(education_id)
);

CREATE TABLE IF NOT EXISTS education_description_table (
    education_description_id INT AUTO_INCREMENT PRIMARY KEY,
    education_id INT NOT NULL,
    description MEDIUMTEXT NOT NULL,
    FOREIGN KEY (education_id) REFERENCES education_table(education_id)
);