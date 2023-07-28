create table if not exists  product(
    id varchar(255) primary key,
    product_name varchar(255),
    description text,
    image_url varchar(255),
    unit_price decimal,
    internal_id varchar(255)
)