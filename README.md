DB structure

CREATE TABLE gym_users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(100),
    dob DATE,
    gender VARCHAR(10),
    phone VARCHAR(15),
    weight INT,
    height INT,
    profile_pic VARCHAR(255) DEFAULT 'default.png',
    doj DATE,
    address VARCHAR(255)
);

CREATE TABLE payments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    amount INT,
    payment_id VARCHAR(100),
    order_id VARCHAR(100),
    status VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, FOREIGN KEY (user_id) REFERENCES gym_users(id)
);


CREATE TABLE membership (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    plan_name VARCHAR(50),
    start_date DATE,
    end_date DATE,
    status VARCHAR(20),FOREIGN KEY (user_id) REFERENCES gym_users(id)
);
