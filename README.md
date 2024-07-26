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
    transaction:
      - id
      - account_id
      - transaction_type
      - amount
      - content
      - timestamp
      - status
    atm:
      - id
      - cash_amount
      - location
      - installed_date
### Relationship
    customer – account relationship
      One to Many Relationship: One customer is allowed to create many accounts.
      Foreign Key: customer_id in account Table is referencing id in customer Table.

## Service
### Authentication
    JWT-based Authentication
### Authorization
    Roles-based 
    - "USER": 
        "/customers/get",
        "/customers/update/{cusId}",
        "/accounts/create",
        "/accounts/get/{accId}",
        "/accounts/get-all/{cusId}",
        "/account/update/{accId}",
        "/accounts/delete/{accId}",
        "/transactions/deposit/{accId}",
        "/transactions/withdraw/{accId}/{atmId}",
        "withdraw/{accId}/{atmId}"
    - "ADMIN": 
        "/customers/get-all",
        "/customers/delete/{cusId}",
        "/accounts/create",
        "/accounts/get/{accId}",
        "/accounts/get-all/{cusId}",
        "/account/update/{accId}",
        "/accounts/delete/{accId}",
        "/transactions/deposit/{accId}",
        "/transactions/withdraw/{accId}/{atmId}",
        "/ATMs/install",
        "ATMs/uninstall/{atmId}",
        "cash-to-atm/{atmId}"
        "withdraw/{accId}/{atmId}"
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
    
    - "/transactions/deposit/{accId}": deposit
    - "/transactions/withdraw/{accId}/{atmId}": withdraw from account
      
    - "/ATMs/install": install ATM
    - "/ATMs/cash-to-atm/{atmId}": send cash to ATM
    - "/ATMs/uninstall/{atmId}": uninstall ATM 
    - "/ATMs/withdraw/{accId}/{atmId}": withdraw cash from ATM   
    