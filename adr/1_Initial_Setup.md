# ADR: Adoption of Quarkus for Cloud-Native Development

## Context and Problem Statement
We need to select a technology stack for developing a new application with a focus on cloud-native principles. The application needs to be lightweight and optimized for deployment in a cloud environment. Our existing stack is based on the Spring Framework, which, while robust, doesn't fully align with our goals for cloud-native and lightweight development.

## Decision Drivers
- **Cloud-Native Development**: The project requires a technology stack optimized for cloud deployments.
- **Lightweight Framework**: We aim for a solution that minimizes resource usage and startup time.
- **Differentiation from Spring**: We are looking for an alternative to our current Spring-based approach to better meet our objectives.
- **Cost Efficiency**: The software needs to be constantly available but only incurs costs when it's in active use.
- **Scalability and Performance**: Given our serverless approach, we need a database solution that scales effectively with demand.
- **Architectural Pattern**: A Hexagonal architecture approach is desired, emphasizing Domain-Driven Design (DDD).
- **Cloud Provider**: AWS is selected based on its comprehensive suite of tools to support our serverless architecture and scalability needs.

## Considered Options
- Quarkus and a serverless architecture with a NoSQL database (AWS DynamoDB) on AWS
- Spring Boot with a relational database on another cloud provider
- Micronaut with a NoSQL database on AWS

## Decision Outcome
We have chosen to adopt Quarkus as the primary framework for our application development. Additionally, we will implement a serverless architecture using AWS DynamoDB as our NoSQL database solution, leveraging AWS as the cloud provider to ensure high availability, scalability, and cost efficiency. The application will follow a Hexagonal architecture with Domain-Driven Design principles to ensure high modularity and flexibility.

## Pros and Cons of the Options
### Quarkus with AWS DynamoDB on AWS and Hexagonal Architecture
- **Pros:**
    - Optimized for cloud-native environments with fast startup times and low memory footprint.
    - AWS provides robust tools for serverless computing like AWS Lambda and DynamoDB.
    - Seamless integration with other AWS services for comprehensive cloud-native capabilities.
    - Quarkus's HTTP functionality allows for the transformation of JAX-RS endpoints into efficient AWS Lambdas.
    - Hexagonal architecture facilitates a clean separation of concerns, promoting high adaptability and easier testing.
    - Domain-Driven Design (DDD) ensures that the core domain logic remains central and independent of technical details.

- **Cons:**
    - Potential learning curve for the development team to adapt to NoSQL data modeling, AWS services, and Hexagonal architecture.

### Spring Boot with a relational database on another cloud provider (not chosen)
- **Pros:**
    - Strong ecosystem and familiar relational data model.

- **Cons:**
    - Increased resource usage and longer startup times.
    - Relational databases may require substantial workarounds to scale dynamically in a serverless environment.
    - Could result in integration challenges with another cloud provider's tools.

### Micronaut with a NoSQL database on AWS (not chosen)
- **Pros:**
    - Lightweight microservices framework.
    - Reflective-less architecture that complements AWS Lambda and DynamoDB's serverless nature.

- **Cons:**
    - Smaller community compared to alternatives affecting resource availability and support.

## Detailed Design
AWS will serve as the cloud provider, supporting a serverless architecture. By utilizing AWS DynamoDB, the application benefits from a fully managed, serverless NoSQL database service that automatically scales and operates according to the demands of the application workload. Quarkus's HTTP capabilities facilitate transforming JAX-RS endpoints into AWS Lambdas. The adoption of Hexagonal architecture driven by Domain-Driven Design principles will ensure a robust and flexible design structure that promotes separation of concerns and ease of maintenance.

## Consequences
- Development teams must incorporate AWS best practices and understand its array of services.
- The implementation of Hexagonal architecture and DDD will require sufficient training to maximize benefits from modularization and domain logic centralization.
- This architecture will support scalability, cost-efficiency, and robustness, leveraging AWS's extensive cloud ecosystem and ensuring the application remains adaptable and maintainable.

## Related Decisions
- [Additional ADRs here if relevant, e.g., overall cloud platform strategy]

## Confirmation
- Enterprise Architect and Project Owner: [Berten De Schutter](https://github.com/BertenDS)