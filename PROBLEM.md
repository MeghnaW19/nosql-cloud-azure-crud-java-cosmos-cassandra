## Problem Statement: Implementing CRUD operations on NoSQL Column store database in Azure Cosmos DB for Cassandra

In this assignment, base code for the HotelApplication has been provided. You need to setup Cosmos DB
database for cassandra api on Azure cloud and complete the functionality for performing CRUD operations in the given HotelApplication.
This assignment uses the Datastax java driver to access AWS keyspaces database. Manual of Datastax driver can be found
 [here](https://docs.datastax.com/en/developer/java-driver/4.6/manual/) 

### The following tasks needs to be completed as part of this assignment. 
  - Create a Cosmos DB for Cassandra API cluster on Cloud using the Azure portal. [Reference link](https://docs.microsoft.com/en-us/azure/cosmos-db/create-cassandra-java-v4)
  - Configure the values in the application.properties using the information from azure portal       
  - Implement the functionality based on the comments provided in base code
    We will be using DataStax Java Driver 4.6 for connecting to Azure Cosmos DB for Cassandra 
    Refer the links provided in the Reference section for manuals
  - Respective classes/files contain the **TODO** comments for the code/task to be completed
  - Ensure that all Test cases provided are successful for assignment completion
  
  **Note: This assignment uses the java `cacerts` truststore for SSL connection. Ensure that JAVA_HOME environment variable 
           is set correctly in your system**

## Reference 
   - [Datastax Mapper api for Mapping Entities with POJO and creating Dao interfaces](https://docs.datastax.com/en/developer/java-driver/4.6/manual/mapper/) 
   - [Datastax Schema builder api for creating keyspace and table](https://docs.datastax.com/en/developer/java-driver/4.6/manual/query_builder/schema/)
   - [Datastax core driver api](https://docs.datastax.com/en/developer/java-driver/4.6/manual/core/)
   
## Instructions
- Take care of whitespace/trailing whitespace
- Do not change the provided class/method names unless instructed
- Ensure your code compiles without any errors/warning/deprecations 
- Check for code smells using SonarLint plugin from STS/IntelliJ IDE
- Follow best practices while coding
