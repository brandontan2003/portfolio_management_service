CREATE TABLE IF NOT EXISTS homepage_content_table (
    homepage_content_id INT AUTO_INCREMENT PRIMARY KEY,
    homepage_section VARCHAR(255) NOT NULL,
    homepage_value LONGTEXT NOT NULL
);
