# Currency adapter
## Start database:
    docker-compose -f ./docker-postgres.yml -p exchange up -d
## Add your api key to application.yaml property <your-api-key>
    In order to get it use the doc here https://currencylayer.com/documentation

# API description:
### GET currencies list: GET /api/v1/currencies 
### ADD currency to list : POST /api/v1/currencies 
    body : 
        {
        "name" : "USD"
        }
### GET currency rate for currency codeName: GET /api/v1/currency-rates?source=EUR
