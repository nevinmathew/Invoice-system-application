# Invoice System

The Invoice System is a Java-based Spring Boot application designed to manage and generate invoices for products bought by users. It utilizes CockroachDB for database management, Redis for caching, and integrates GraphQL for querying data.

## Table of Contents

1. [Languages and Tools](#languages-and-tools)
2. [Dependencies](#dependencies)
   - [Spring Boot](#spring-boot)
   - [CockroachDB](#cockroachdb)
   - [Redis](#redis)
   - [GraphQL](#graphql)
   - [Lombok](#lombok)
   - [PDF Generation Library (OpenPDF)](#pdf-generation-library-openpdf)
3. [CockroachDB Installation](#cockroachdb-installation)
4. [Redis Installation](#redis-installation)
5. [GraphQL](#graphql-1)
6. [Docker Setup](#docker-setup)
7. [PDF Generation Library (OpenPDF)](#pdf-generation-library-openpdf-1)
   - [Key Features](#key-features)
   - [Integration in Invoice System](#integration-in-invoice-system)
8. [Configuration](#configuration)
   - [application.properties](#applicationproperties)
9. [License](#license)

<h3 align="left">Languages and Tools:</h3>
<table>
    <tbody>
        <tr valign="top">
            <td> <p align="left"> <a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/java/java-icon.svg" title="Java" alt="java" width="40" height="40"/> </a> </td> 
            <td> <a href="https://spring.io/" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/springio/springio-icon.svg" alt="spring" width="40" height="40"/> </a> </td>
            <td> <a href="https://www.cockroachlabs.com/product/cockroachdb/" target="_blank" rel="noreferrer"> <img src="https://cdn.worldvectorlogo.com/logos/cockroachdb.svg" alt="cockroachdb" width="40" height="40"/> </a> </td>
            <td> <a href="https://graphql.org" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/graphql/graphql-icon.svg" alt="graphql" width="40" height="40"/> </a> </td>
            <td> <a href="https://redis.io" target="_blank" rel="noreferrer"> <img src="https://cdn4.iconfinder.com/data/icons/redis-2/1451/Untitled-2-1024.png" alt="redis" width="40" height="40"/> </a> </td>
            <td> <a href="https://www.docker.com" target="_blank" rel="noreferrer"> <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/docker/docker-original.svg" alt="docker" width="40" height="40"/> </a> </td> </p>
        </tr>
    </tbody>
</table>


## Dependencies

- Spring Boot
- CockroachDB
- Redis
- GraphQL
- Lombok
- PDF generation library (OpenPDF)

### CockroachDB: 

Install CockroachDB from the following link:- https://binaries.cockroachdb.com/cockroach-v20.1.17.windows-6.2-amd64.zip

Open powershell and run the following command - 

```powershell
C:\Users\Admin\Downloads> cockroach start-single-node --insecure `
>> --listen-addr=localhost:26257 `
>> --http-addr=localhost:8080
```

### Redis:

Install WSL, enable the necessary features, and restart the system.

Install Ubuntu distros for windows.

Launch Ubuntu and install the Redis server

Run the following command to run redis server:

```bash
redis-server
```

Run redis-cli and run the command ‘ping’. If you get the response ’pong’ then redis is running.

To view the data in Redis, install RedisInsight in your windows system from the following link:- https://download.redisinsight.redis.com/latest/RedisInsight-v2-win-installer.exe/

### GraphQL:

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

### Docker Setup:

The project includes Dockerfiles and a docker-compose.yml file for easy deployment using Docker. Follow the steps in the Docker Setup section above.

To download the images from the docker repository:

```bash
docker pull nevin1m/invoice-system -a
```

### PDF Generation Library (OpenPDF)

The Invoice System leverages [OpenPDF](https://github.com/LibrePDF/OpenPDF), an open-source PDF generation library for Java, to dynamically create and manage PDF documents. OpenPDF simplifies the process of generating professional-looking invoices.

#### Key Features:

- **Text Formatting:** Precise control over text formatting ensures polished and professional PDF content.

- **Image Embedding:** Supports embedding images into PDFs, facilitating the inclusion of logos and graphical elements.

- **Page Manipulation:** Functionality for manipulating pages enhances organization and structure within generated documents.

#### Integration in Invoice System:

OpenPDF seamlessly integrates with the Spring Boot application, enhancing user experience by delivering well-designed and visually appealing invoices.

## Configuration

    application.properties: Configure database connections, GraphQL, and other application properties.

## License

This project is licensed under the MPL License - see the [LICENSE.md](LICENSE.md) file for details.
