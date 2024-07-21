# Banking-System

## DATABASE
  ### Table: 
    customer:
      - id
      - first_name
      - last_name
      - birth
      - phone_number
      - username
      - password
      - roles (USER<default>, ADMIN)
    account:
      - id
      - account_number
      - balance
      - customer_id
  ### Relationship
    customer – account relationship
      One to Many Relationship: One customer is allowed to create many accounts.
      Foreign Key: customer_id in account Table is referencing id in customer Table.

## Service
  ### Authentication
  ### Authorization
    Roles-based 
    - "USER": add customer, update customer's information
    - "ADMIN": /customers/** 
