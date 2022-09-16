DROP TABLE IF EXISTS client;

CREATE TABLE client(
    client_id SERIAL PRIMARY KEY,
    client_name VARCHAR(20) NOT NULL,
    city VARCHAR(20) NOT NULL,
    car_id INT NOT NULL,

    FOREIGN KEY (car_id) REFERENCES car (car_id) ON DELETE CASCADE
);

INSERT INTO client(client_name, city, car_id) VALUES ('Василий Ф.И.', 'Москва', 1),
                                              ('Надежда К.Ю.', 'Самара', 3),
                                              ('Леонид М.В.', 'Санкт-Петербург', 5),
                                              ('Вероника Л.К.', 'Тольятти', 7);