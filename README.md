# Cryptocurrency API
This API is a test project where I learn, implement and test some features of Java, Spring Boot and so on.
<br>For now, the technology stack is:
- Java 18
- Spring Boot v3.0.1
- Spring Data MongoDB v4.0.0
- Log4j v2.19.0
- Junit v5.9.1
- Mockito
<p> Project was created on Linux Ubuntu v22.04.1.

## Configuration
#### Configuring Data Base
First of all, you should prepare your database to be used. As database, I use MongoDB Atlas. So to make program work, you should have your own Atlas Cluster.
<br>Here is a guide on [how to start with Atlas](https://www.mongodb.com/docs/atlas/getting-started/).

As soon as you get you cluster, you should create a collection in it called `pairs`, where all the information about currency pairs will be stored.
<br> From now our Atlas MongoDB is ready to use!
#### Application properties
Now we should configure the application properties file. Here you should set the `spring.data.mongodb.uri` property ehich you can get from your Atlas.
<br>From now all the properties are set and the project can be run!
## Working with API 
<br>There are 3 available currencies for now:
- BTC:USDT
- ETH:USDT
- XRP:USDT
<p>Here is a list of endpoint, their URLs, supported methods and descriptions:

Endpoint| HTTP Method |Description
---|----|---
`/api/v1/pairs/{pair1}/{pair2}`|POST|Insert the result of request to CEX.IO with information about crypto pairs currency.
`/api/v1/pairs/{pair1}`|GET|Get the information about crypto
`/api/v1/cryptocurrencies/minprice?name=[currency_name]`| GET |Get min currency of crypto pairs
`/api/v1/cryptocurrencies/maxprice?name=[currency_name]`| GET |Get max currency of crypto pairs
`/api/v1/cryptocurrencies?name=[currency_name]&page=[page_number]&size=[page_size]`| GET |Return a selected page with selected number of elements and default sorting is by price from lowest to highest. For example, if page=0&size=10, then first 10 elements from database will be returned, sorted by price from lowest to highest. [page_number] and [page_size] request parameters are optional. If they are missing then the default values will be set: page=0, size=10.
`/api/v1/cryptocurrencies/csv`| GET |**IN WORK!** Report should contain the following fields: Cryptocurrency Name, Min Price, Max Price. So there should be only three records in that report, because we have three different cryptocurrencies. Feel free to use any available library for generating csv files.

## API responses
URL: `pairs/{pair1}/{pair2}`
<br>Method: `POST`

This endpoint makes a request to the CEX.IO API, retrieve data based on the pairs you've provided and insert this data into your Atlas collection.
<br>In case of successful insertion, you will get `Data was recorded!`
***
URL: `pairs/{pair1}`
<br>Method: `GET`

This endpoint returns the information about the specified crypto.
<br>**For now it returns an array of pairs of the specified type in text format, but it will be changed!**
***
URL: `cryptocurrencies/minprice?name=[currency_name]`
<br>Method: `GET`

This endpoint return the minimal price of the specified crypto.
<br>The output will be in format: `Min price of BTC: 22640`
***
URL: `cryptocurrencies/maxprice?name=[currency_name]`
<br>Method: `GET`

This endpoint return the maximum price of the specified crypto.
<br>The output will be in format: `Max price of BTC: 23207.4`
***
URL: `/api/v1/cryptocurrencies?name=[currency_name]&page=[page_number]&size=[page_size]`
<br>Method: `GET`

This endpoint returns a selected page with selected number of elements and default sorting is by price from lowest to highest. For example, if page=0&size=10, then first 10 elements from database will be returned, sorted by price from lowest to highest. [page_number] and [page_size] request parameters are optional. If they are missing then the default values will be set: page=0, size=10.
<br>**The output for now is in text format but in future will be changed to JSON.**
<br>Output example:
![Screenshot from 2023-01-31 20-36-47.png](img/Screenshot%20from%202023-01-31%2020-36-47.png)
