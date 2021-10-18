CREATE TABLE IF NOT EXISTS country(
name VARCHAR(200) NOT NULL UNIQUE,
id INT AUTO_INCREMENT PRIMARY KEY,
capital VARCHAR(200) NOT NULL,
region VARCHAR(200) NOT NULL,
sub_region VARCHAR(200) NOT NULL,
area DOUBLE NOT NULL
)