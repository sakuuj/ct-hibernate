-- Insert data into car_showrooms
INSERT INTO car_showrooms (id, name, address) VALUES
('550e8400-e29b-41d4-a716-446655440000', 'AutoWorld', '123 Main Street, Springfield'),
('550e8400-e29b-41d4-a716-446655440001', 'Luxury Motors', '456 Elm Street, Springfield'),
('550e8400-e29b-41d4-a716-446655440002', 'Economy Cars', '789 Oak Street, Springfield');

-- Insert data into categories
INSERT INTO categories (name) VALUES
('Sedan'),
('SUV'),
('Truck'),
('Electric');

-- Insert data into cars
INSERT INTO cars (id, make, model, production_year, price, car_showroom_id, category_id) VALUES
('650e8400-e29b-41d4-a716-446655440000', 'Toyota', 'Camry', 2020, 25000.00, '550e8400-e29b-41d4-a716-446655440000', 1),
('650e8400-e29b-41d4-a716-446655440001', 'Ford', 'Explorer', 2021, 35000.00, '550e8400-e29b-41d4-a716-446655440001', 2),
('650e8400-e29b-41d4-a716-446655440002', 'Tesla', 'Model 3', 2022, 50000.00, '550e8400-e29b-41d4-a716-446655440002', 4),
('650e8400-e29b-41d4-a716-446655440003', 'Chevrolet', 'Silverado', 2021, 40000.00, '550e8400-e29b-41d4-a716-446655440001', 3);

-- Insert data into clients
INSERT INTO clients (id, name, registration_date) VALUES
('750e8400-e29b-41d4-a716-446655440000', 'John Doe', '2024-01-01 10:00:00'),
('750e8400-e29b-41d4-a716-446655440001', 'Jane Smith', '2024-02-01 11:30:00'),
('750e8400-e29b-41d4-a716-446655440002', 'Alice Johnson', '2024-03-01 09:15:00');

-- Insert data into client_contacts
INSERT INTO client_contacts (content, client_id) VALUES
('john.doe@example.com', '750e8400-e29b-41d4-a716-446655440000'),
('123-456-7890', '750e8400-e29b-41d4-a716-446655440000'),
('jane.smith@example.com', '750e8400-e29b-41d4-a716-446655440001'),
('987-654-3210', '750e8400-e29b-41d4-a716-446655440001'),
('alice.johnson@example.com', '750e8400-e29b-41d4-a716-446655440002');

-- Insert data into clients_to_cars
INSERT INTO clients_to_cars (client_id, car_id) VALUES
('750e8400-e29b-41d4-a716-446655440000', '650e8400-e29b-41d4-a716-446655440000'),
('750e8400-e29b-41d4-a716-446655440001', '650e8400-e29b-41d4-a716-446655440001'),
('750e8400-e29b-41d4-a716-446655440002', '650e8400-e29b-41d4-a716-446655440002');

-- Insert data into reviews
INSERT INTO reviews (id, content, rating, client_id, car_id) VALUES
('850e8400-e29b-41d4-a716-446655440000', 'Great car, very comfortable!', 5, '750e8400-e29b-41d4-a716-446655440000', '650e8400-e29b-41d4-a716-446655440000'),
('850e8400-e29b-41d4-a716-446655440001', 'Good value for money.', 4, '750e8400-e29b-41d4-a716-446655440001', '650e8400-e29b-41d4-a716-446655440001'),
('850e8400-e29b-41d4-a716-446655440002', 'Best electric car I have owned.', 5, '750e8400-e29b-41d4-a716-446655440002', '650e8400-e29b-41d4-a716-446655440002');