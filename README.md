# crypto-currency-app

Available currencies:
- BTC
- ETH
- XRP

Endpoint|HTTP Method|Description
---|---|---
`/api/addPairs/{pair1}/{pair2}`|GET|insert the result of request to CEX.IO with information about crypto pairs currency.
`/cryptocurrencies/minprice?name=[currency_name]`|GET|get min currency of crypto pairs
`/cryptocurrencies?name=[currency_name]&page=[page_number]&size=[page_size]`|GET|return a selected page with selected number of elements and default sorting should be by price from lowest to highest. For example, if page=0&size=10, then you should return first 10 elements from database, sorted by price from lowest to highest. [page_number] and [page_size] request parameters should be optional, so if they are missing then you should set them default values page=0, size=10.
`/cryptocurrencies/csv`|GET|Report should contain the following fields: Cryptocurrency Name, Min Price, Max Price. So there should be only three records in that report, because we have three different cryptocurrencies. Feel free to use any available library for generating csv files.