DROP TABLE IF EXISTS shop;

CREATE TABLE shop(
    shop_id SERIAL PRIMARY KEY,
    shop_name VARCHAR(50) NOT NULL
);

INSERT INTO shop(shop_name)
VALUES('Subaru Official Dealer'),
      ('Chevrolet Official Dealer'),
      ('Kia Official Dealer'),
      ('Trade-In Dealer');