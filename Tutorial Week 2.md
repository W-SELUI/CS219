1. Describe the emergence of cloud computing from a combination of business drivers and technology innovations 

Cloud computing emerged as a result of both business drivers and technological innovations. Organizations needed more efficient, flexible and cost-effective ways to manage IT resources while technological advancements made it possible to deliver computing services over the Internet 

Business Drivers =  Business Needs or Reasons 
These are the problems or goals that business have, which push them to adopt cloud computing. "Why did companies want cloud computing? " 
- From a business perspective, companies wanted to reduce the high costs of purchasing and maintaining hardware and software. They also required greater scalability to handle changing workloads and improved accessibility for users working from different locations. Cloud computing addressed these needs through a pay-as-you-go model, allowing business to access computing resources on demand and pay only for what they use 

Technology Innovations = Technological Advancements
These are the new technologies that made cloud computing possible. "What technologies enabled cloud computing to exist? "
- Several technological innovations enabled the development of cloud computing. Such as Virtualization technology allowing multiple virtual machines to run on a single physical server, improving resource utilization and reducing costs. Advances in high-speed Internet connectivity made remote access to applications and data practical and reliable. Furthermore, developments in distributed computing, data center technologies and web services provided the foundation for delivering computing resources as online servers. 

In conclusion, cloud computing emerged through the combination of business demands for cost savings, flexibility and scalability along with technological innovations such as virtualization, broadband networking and distributed computing. Together, these factors transformed the delivery and consumption of IT services. 


2. Compare and Contrast between Internet Computing, Network Computing, Grid Computing and Cloud Computing 
Internet Computing, Network Computing, Grid Computing and Cloud computing are all computing approaches that use interconnected systems, but they differ in purpose and resource management. 

Internet Computing refers to delivering services and applications over the Internet using web technologies. Users access applications through a web browser, and resources are provided through Internet connectivity. An example is a web application 

Network Computing involves computers connected through a network to share resources such as files, printers and applications. The focus is on communication and resource sharing among connected devices within a network 

Grid Computing combines resources from multiple distributed computers to solve large computational problems. The computers work together as a virtual supercomputer, sharing processing power to perform complex tasks more efficiently. 

Cloud Computing is the delivery of hosted services over the Internet, providing on-demand access to scalable computing resources. It uses technologies such as virtualization and resource pooling to offer services through a pay-per-use model. Cloud computing provides characteristics such as on-demand usage, elasticity, measured usage and resource pooling. 

In summary, Internet Computing focuses on web-based access, Network Computing focuses on resource sharing across networks, Grid Computing focuses on distributed processing power, whereas Cloud Computing provides scalable and virtualized services on demand over the Internet 

3. Identifying the type of scaling (Horizontal or Vertical) 

Horizontal Scaling : scaling out and scaling in by allocating or releasing IT resources of the same type. It is the most common form of scaling in cloud environment  | Add more machines / instances to distribute load

Vertical Scaling: Scaling up and scaling down by replacing an existing IT resource with one that has higher or lower capacity | Increases resources of a single machine (CPU, RAM, disk)

- For small projects or legacy systems, vertical scaling is easier and faster
- For modern cloud-native apps (like Netflix, Google, etc...), horizontal scaling is the standard because it ensures high availability, resilience and unlimited growth potential


| Description                                                                                                                                                                           | Type of Scaling    |
| ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------------ |
| Increasing a single machine's capacity with rising resources in the same logical server or unit                                                                                       | Vertical Scaling   |
| The server performance starts degrading with an increase in concurrency and multithreaded applications. The load is managed by scaling resources like CPUs, RAM, disk capacity, etc.. | Vertical Scaling   |
| Scaling up from 8GB RAM and 4 Processors to 512 GB RAM with 64 Processors                                                                                                             | Vertical Scaling   |
| An approach to enhance the performance of the server node by adding new instances of the server to the existing servers to distribute the workload equally                            | Horizontal Scaling |
| Load balancing, clustering and distributed file system are crucial strategies for this type of Scaling                                                                                | Horizontal Scaling |
| It is based on partitioning where each node contains a single part of data                                                                                                            | Horizontal Scaling |
| Adding resources like processing power, storage and memory to the existing hardware or software, enhancing the system's capacity                                                      | Vertical Scaling   |


4. How is multi-tenancy & resiliency related to cloud computing? Give examples where necessary

Both multi-tenancy and resiliency are important characteristics of cloud computing that enable cloud services to be cost-effective, scalable, reliable and highly available. 

**Multi-tenancy :** it is the characteristics that allows a single instance of software or a shared pool of IT resources to serve multiple customers (tenants) while keeping each tenant isolated from the others. Cloud providers use multi-tenancy and resource pooling to efficiently serve many users with the same infrastructure 

How it related to cloud computing: 
- Enables resource sharing among multiple customers
- Reduces infrastructure and operational costs
- Allows cloud providers to dynamically allocate resources according to user demand
- Supports the cloud's pay-per-use model

Example: 
- Google Workspace (Gmail) hosts millions of users in shared infrastructure. Each user has a separate account and data, but they all use the same underlying cloud platform

**Resiliency :**  Resiliency is the ability of a cloud system to continue operating even when failures occur. It is achieved by distributing redundant IT resources across different physical locations so that if one resource fails, another can automatically take over

How it related to cloud computing: 
- Increases reliability and availability of cloud services
- Provides automatic failover during hardware or network failures
- Minimizes downtime and service interruptions
- Ensures business continuity for cloud consumers

Example: 
- Cloud-based banking systems often replicate data across multiple servers so customers can continue accessing servers even if one server crashes

Conclusion

Multi-tenancy makes cloud computing efficient and cost-effective by allowing multiple customers to share the same infrastructure while remaining isolated from one another. Resiliency makes cloud computing reliable by providing redundant resources and automatic failover mechanisms to ensure continuous service availability. Together, they help deliver the scalability, affordability and reliability expected from modern cloud computing platforms

- Multi-tenancy : Multiple tenants share the same cloud resources while maintaining data isolation and security
- Resiliency : Cloud systems use redundant resources and failover mechanisms to maintain availability during failures


5. What are the different types of cloud computing services? 

- Infrastructure as a Service (Iaas)
- Platform as a Service (PaaS)
- Software as a Service (SaaS)

a. Infrastructure as a Service 

IaaS provides basic computing infrastructure such as servers, storage devices, networks ad other hardware resources over the cloud. Users are responsible for managing the operating system and applications installed on the infrastructure 

Characteristics
- Resources are provided as a service
- Uses a pay-as-you-use pricing model
- Multiple users can share the same hardware

Examples
- MS Azure Virtual Machines

Example Scenario
A startup rents virtual servers from AWS instead of purchasing physical servers, reducing upfront cost

b. Platform as a Service 

PaaS provides a complete platform for developing, testing, deploying and maintaining applications. The cloud provider manages the infrastructure, OS and development environment, while as Application Software, Integrations etc... users develop that themselves 

Characteristics: 
- Supports application development and deployment
- Build-in scalability and load balancing
- Integration with databases and web services
- Facilitates collaboration among developers

Example
- Google App Engine

Example Scenario: 
A team of developers uses Google App Engine to build and deploy a web application without managing servers 

c. Software as a Service 

Saas delivers ready-to-use software applications over the Internet. Users access the software through a web browser and do not need to install, maintain or update it themselves

Characteristics
- Accessible through web
- Supports integration through APIs
- Centrally managed by the provider

Examples
- Microsoft 365
- Google Workspace

Example Scenario
Students use Microsoft 365 Online to create and edit documents without installing Microsoft Office on their computers

Summary Table

| Service Model | What the Providers Offers                     | Example                          |
| ------------- | --------------------------------------------- | -------------------------------- |
| IaaS          | Infrastructure (servers, storage, networking) | Azure VM                         |
| PaaS          | Development platform and tools                | Google App Engine                |
| SaaS          | Ready-to-use software applications            | Google Workspace / Microsoft 365 |

The three main types of cloud computing services are Infrastructure as a Service (IaaS), Platform as a Service(PaaS) and Software as a Service(SaaS). IaaS provides computing infrastructure, PaaS provides a platform for application development and deployment and SaaS provides ready-to-use software application over the Internet. 


6. Which service delivery model deals with servers, storage, networks and operating system? 

While servers, storage and networks are typically associated with IaaS, the inclusion of OS may suggest PaaS, since the platform includes and manages the OS. However, the standard answer is usually IaaS as the question is a bit ambiguous. 

7. Classifying the following as either IaaS, PaaS or SaaS

a. Microsoft 365 - SaaS
b. Amazon Web Services - IaaS
c. Google App Engine - PaaS
d. VMware - IaaS
e. Microsoft Azure - IaaS 
f. Dropbox - SaaS
g. Google Compute Engine - IaaS 

(double check Azure and AWS)

8. What are cloud deployment models. Discuss

Cloud deployment models describe how a cloud environment is deployed, who owns it and who can access it. The main cloud deployment models are 

- Private Cloud
- Public Cloud
- Hybrid Cloud

1. Private Cloud
A Private Cloud is a cloud infrastructure that is used exclusively by a single organization. The resources are not shared with other organizations, giving greater control, security and privacy

Advantages
- Higher security and privacy
- Greater control over resources
- Easier compliance with regulations

Disadvantages
- Higher setup and maintenance costs
- Requires dedicated infrastructure and management

Example
A bank operating its own cloud infrastructure to store sensitive customer financial data 

2. Public Cloud
A Public Cloud is owned and operated by a third-party cloud provider. Resources are shared among multiple customers and accessed over the internet 

Advantages
- Lower cost
- Highly scalable
- No need to purchase or maintain hardware

Disadvantages
- Less control over infrastructure
- Potential security and privacy concerns

Examples
- Amazon Web Services (AWS)
- Microsoft Azure

3. Hybrid Cloud

 A Hybrid Cloud combines both private and public cloud environments. Organizations can keep sensitive applications and data in a private cloud while utilizing public cloud resources for less critical workloads or when additional capacity is needed. 

Advantages
- Flexibility
- Better scalability
- Improved cost efficiency
- Greater control over sensitive data

Disadvantages
- More complex to manage
- Can be difficult to integrate and secure

Example
A university stores student records in a private cloud but hosts its public website on AWS. During enrollment periods, additional cloud resources can be obtained from the public cloud to handle increased traffic. 

Summary Table

| Deployment Model | Ownership                         | Access                        | Example                                        |
| ---------------- | --------------------------------- | ----------------------------- | ---------------------------------------------- |
| Private Cloud    | Single Organization               | Internal users only           | Bank's internal cloud                          |
| Public Cloud     | Third-party provider              | General public / customers    | AWS, Azure, Google Cloud                       |
| Hybrid Cloud     | Combination of private and public | Internal + external resources | University using both private and public cloud |

Cloud deployment models are Private Cloud, Public Cloud and Hybrid Cloud. A Private Cloud is dedicated to one organization and offers greater security and control. A Public Cloud is owned by a cloud provider and shared among multiple customers over the Internet. A Hybrid Cloud combines both models, allowing organization to keep sensitive data in a private cloud while using public cloud resources for scalability and cost efficiency

---

Lab Week 2

Screenshot result and submit on Dropbox

a. IP address of your current machine 
``ipconfig
IPv6 Address or IPv4 Address

b. Test connectivity to www.usp.ac.fj
``ping www.usp.ac.fj

c. List down the IP address of www.usp.ac.fj
``nslookup www.usp.ac.fj

d. Identify the number of hops to test connectivity to www.usp.ac.fj
``tracert www.usp.ac.fj

e. Computer Name of your current machine
``hostname

f. DNS & DHCP information of your current machine
``ipconfig /all

g. MAC Address of your current machine
``getmac

h. Renew DHCP Client IP Address
``ipconfig /renew



---

Mahara

https://eport.usp.ac.fj/view/view.php?t=28bcbb7ff052cb07d4e8
