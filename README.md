# Invoice System

<h3 align="left">Languages and Tools:</h3>
<table>
    <tbody>
        <tr valign="top">
            <td> <p align="left"> <a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/> </a> </td> 
            <td> <a href="https://spring.io/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="40" height="40"/> </a> </td>
            <td> <a href="https://www.cockroachlabs.com/product/cockroachdb/" target="_blank" rel="noreferrer"> <img src="https://cdn.worldvectorlogo.com/logos/cockroachdb.svg" alt="cockroachdb" width="40" height="40"/> </a> </td>
            <td> <a href="https://graphql.org" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/graphql/graphql-icon.svg" alt="graphql" width="40" height="40"/> </a> </td>
            <td> <a href="https://redis.io" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/redis/redis-original-wordmark.svg" alt="redis" width="40" height="40"/> </a> </td>
            <td> <a href="https://www.docker.com" target="_blank" rel="noreferrer"> <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/docker/docker-original.svg" alt="docker" width="40" height="40"/> </a> </td> </p>
        </tr>
    </tbody>
</table>

This is a Spring application that generates the invoice for the products purchased by an user. It uses Redis for caching, cockroachDB database, GraphQL APIs and Docker.

## CockroachDB: 

Install CockroachDB from the following link:- https://binaries.cockroachdb.com/cockroach-v20.1.17.windows-6.2-amd64.zip

Open powershell and run the following command - 

```powershell
C:\Users\Admin\Downloads> cockroach start-single-node --insecure `
>> --listen-addr=localhost:26257 `
>> --http-addr=localhost:8080
```

## Redis:

Install WSL, enable the necessary features, and restart the system.

Install Ubuntu distros for windows.

Launch Ubuntu and install the Redis server

Run the following command to run redis server:

```bash
redis-server
```

Run redis-cli and run the command ‘ping’. If you get the response ’pong’ then redis is running.

To view the data in Redis, install RedisInsight in your windows system from the following link:- https://download.redisinsight.redis.com/latest/RedisInsight-v2-win-installer.exe/

## GraphQL:

Url: http://localhost:8080/api/graphql

The GraphQL queries for the APIs in the application:

```graphql
{
    getAllInvoices
    {
        products{
            id
            name
            price
            quantity
            totalPrice
            transactionId
        }
        paymentStatus
        amount
    }
}
```
```graphql
{
    getInvoice(id: "3f31fda3-9e28-4798-8766-2b6e40634dcb")
    {
        products{
            id
            name
            price
            quantity
            totalPrice
            transactionId
        }
        paymentStatus
        amount
    }
}
```
```graphql
mutation{
    saveInvoice(prods: [{name:" ",price: 14.0,quantity:4,totalPrice: null,transactionId: id"}],payment: true)
    {
        id
        products{
            id
            name
            price
            quantity
            totalPrice
            transactionId
        }
        createdDate
        createdTime
        paymentStatus
        amount
    }
}
```
```graphql
mutation{
    updateInvoice(prods: [{id:"id",name:" ",price: 14.0,quantity:4,totalPrice: 56.0,transactionId: "id"}],id: "id",payment: true)
    {
        id
        products{
            id
            name
            price
            quantity
            totalPrice
            transactionId
        }
        createdDate
        createdTime
        paymentStatus
        amount
    }
}
```
```graphql
mutation{
    deleteInvoice(id: "d4ce58dc-bd3d-4205-b929-43b1835498e4")
}
```

## Docker image:

To download the images from the docker repository:

```bash
docker pull nevin1m/invoice-system -a
```

## License:

Distributed under the MPL License
