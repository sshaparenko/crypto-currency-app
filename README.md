# Cryptocurrency API
This API is a test project where I learn, implement and test some features of Java, Spring Boot and so on.
<br>For now, the technology stack is:
- Java 18
- Spring Boot v3.0.1
- Spring Data MongoDB v4.0.0
- Log4j v2.19.0
- Junit v5.9.1
- Mockito

## Configuration
#### Configuring Data Base
First of all, you should prepare your database to be used. As database, I use MongoDB Atlas. So to make program work, you should have your own Atlas Cluster.
<br>Here is a guide on [how to start with Atlas](https://www.mongodb.com/docs/atlas/getting-started/).

As soon as you get you cluster, you should create a collection in it called `pairs`, where all the information about currency pairs will be stored.
<br> From now our Atlas MongoDB is ready to use!
#### Application properties


Available currencies:
- BTC
- ETH
- XRP

Endpoint|HTTP Method|Description
---|---|---
`/addPairs/{pair1}/{pair2}`|GET|insert the result of request to CEX.IO with information about crypto pairs currency.
`/cryptocurrencies/minprice?name=[currency_name]`|GET|get min currency of crypto pairs
`/cryptocurrencies/maxprice?name=[currency_name]`|GET|get max currency of crypto pairs
`/cryptocurrencies?name=[currency_name]&page=[page_number]&size=[page_size]`|GET|return a selected page with selected number of elements and default sorting should be by price from lowest to highest. For example, if page=0&size=10, then you should return first 10 elements from database, sorted by price from lowest to highest. [page_number] and [page_size] request parameters should be optional, so if they are missing then you should set them default values page=0, size=10.
`/cryptocurrencies/csv`|GET|Report should contain the following fields: Cryptocurrency Name, Min Price, Max Price. So there should be only three records in that report, because we have three different cryptocurrencies. Feel free to use any available library for generating csv files.