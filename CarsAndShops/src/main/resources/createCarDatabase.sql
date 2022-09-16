DROP TABLE IF EXISTS car;

CREATE TABLE car(
    car_id SERIAL PRIMARY KEY,
    shop_name VARCHAR(50) NOT NULL,
    brand VARCHAR(15) NOT NULL,
    model VARCHAR(15) NOT NULL,
    age_of_produce INT NOT NULL,
    price INT NOT NULL,

    FOREIGN KEY (shop_name) REFERENCES shop (shop_name) ON DELETE CASCADE
);

INSERT INTO car(shop_name, brand, model, age_of_produce, price) VALUES ('Subaru Official Dealer', 'Subaru', 'Outback', 2022, 6000000),
                                                                     ('Subaru Official Dealer', 'Subaru', 'Forester', 2021, 4000000),
                                                                     ('Chevrolet Official Dealer', 'Chevrolet', 'Camaro', 2014, 2000000),
                                                                     ('Chevrolet Official Dealer', 'Chevrolet', 'Lacetti', 2015, 400000),
                                                                     ('Kia Official Dealer', 'Kia', 'Rio', 2022, 1500000),
                                                                     ('Kia Official Dealer', 'Kia', 'Ceed', 2020, 1800000),
                                                                     ('Trade-In Dealer', 'Daewoo', 'Gentra', 2016, 250000),
                                                                     ('Trade-In Dealer', 'Nissan', 'Juke', 2014, 1000000);