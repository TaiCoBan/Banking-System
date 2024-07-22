﻿# Banking-System

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
    Basic Authentication
### Authorization
    Roles-based 
    - "USER": 
        "/customers/get",
        "/customers/update/{cusId}",
        "/accounts/create",
        "/accounts/get/{accId}",
        "/accounts/get-all/{cusId}",
        "/account/update/{accId}",
        "/accounts/delete/{accId}"
    - "ADMIN": 
        "/customers/get-all",
        "/customers/delete/{cusId}",
        "/accounts/create",
        "/accounts/get/{accId}",
        "/accounts/get-all/{cusId}",
        "/account/update/{accId}",
        "/accounts/delete/{accId}"
### Endpoints:
    - "/customers/add": register a new customer
    - "/customers/get": get logged in customer's information
    - "/customers/get-all": get information of all customer
    - "/customers/update/{cusId}": update logged in customer's information
    - "/customers/delete/{cusId}": delete customer with specific id

    - "/accounts/create": create new account
    - "/accounts/get/{accId}": get customer's information with specific id
    - "/accounts/get-all/{cusId}": create all account of logged in customer
    - "/accounts/update/{accId}": update account
    - "/accounts/delete/{accId}": delete account
    
    
      
