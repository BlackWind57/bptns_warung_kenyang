# bptns_warung_kenyang

bptns_warung_kenyang is a solution based on the following problem in Technical_Test_BTPNS_21072021.pdf

## Background
This project only contains the backend of the rest-api-app, which consists of the app itself that handles 
GET and POST REST method and the database mySQL to store the orders from the customer.

This project simulates the system and the communication between the waiter and the kitchen after the customer
has ordered.

## Setup
Make sure you have docker installed on your computer
and under the root directory, you can run 
```
docker-compose up --build
```
to fully launch the rest-api-app.

You can test the rest-api-app using Postman or other applications to send a GET/POST request to the
running system.

## Request payload Setup
The address that accepts the incoming request is
```
localhost:5000/api/v1/orders
```
You can directly send a GET request to the running system on that address.

But the system assumes a JSON payload body when submitting a POST request to the system.
Below is an example of the request payload,
```
{
    "tableNumber": 3,
    "orderItems": [
        {
            "quantity": 2,
            "menuItem": {
                "name": "Juice",
                "type": "drink"
            }
        },
        {
            "quantity": 1,
            "menuItem": {
                "name": "Fried Chicken with Rice",
                "type": "food"
            }
        },
        {
            "quantity": 1,
            "menuItem": {
                "name": "Ice Coffee",
                "type": "drink"
            }
        },
        {
            "quantity": 1,
            "menuItem": {
                "name": "Lamb satay with Rice",
                "type": "food"
            }
        }
    ]
}
```

Based on the example above, 
the request payload format is 
```
{
    <Table Number>,
    <Order Items Array > 
        <Order Item>
            <Quantity of the item>,
            <Menu Item>
                <Item name> (curry ,ice tea...)
                <Type of Item> (food, drink)
        </Order Item>,
        ...
    </Order Items Array>
}
```

Invalid/Incorrect/Missing placement of the key/value pairs will result in a 400 Bad Request exception

## Running unit tests

To run unit test, please head to application.properties at the src/main/resources
and update the database to H2 from mySQL.

I have provided with Run All Tests at the Run configuration on Eclipse if it is neccessary to see all the test cases running.


## Troubleshooting

If you encountered an issue while running docker-compose especially if it is a 
connection with the server to download files, please redo the docker-compose command.

If you encountered with an error of "Duplicate entry ...", please comment out the
initial sql insertions code at src/main/resources/data.sql

If you get an invalid menu item 400 BAD REQUEST and you entered the menu name correctly, please double check whether the menu items are there in the sql database. This can be done by uncomment the insertions code at src/main/resources/data.sql.


## Tips

Every time you made changes to the files of the rest-api-app, please rebuild the app by using the rest-api-app Maven Build on the Run Configurations and then run the docker-compose command

