CREATE TABLE users (
    id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO users (id, name, email, password, role)
VALUES (
    uuid_generate_v4(),
    'Vinicius Admin',
    'vinicius@conciliapay.com',
    '$2a$10$Y50UaMFOxteibQEYLrwuHeehHYfcoLocnN.Pt./Z.Bnbd0a5eBqfO',
    'ADMIN'
);