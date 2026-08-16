1. **What are some cloud enabling technologies? Provide examples where necessary** 

Cloud enabling technologies are the technologies that make cloud computing possible and efficient. The main cloud enabling technologies are: 

- Broadband networks and Internet architectures
which allows users to access cloud services over the Internet. For example, ISP networks and routers connect users to cloud providers. 

- Data center technologies 
which provides the physical infrastructure needed to host cloud services, such as servers, storage devices and networking equipment. Microsoft Azure data centers are an example 

- Virtualization technology 
which allows multiple virtual machines to run on a single physical server, improving resource utilization, for example Apache VirtualBox

- Web technology
such as HTTP, HTML, XML and web browsers which allow users to access cloud services using the web

- Multitenant technology
which enables multiple customers to share the same application while keeping their data isolated from one another, for example Microsoft 365

---

2. **Explain connectionless packet switching, router-based interconnectivity and transport and application layer protocols**

**Connectionless packet switching** is a method where data is broken into small packets and each packet is sent independently across the network. The packets may travel through different routes before being reassembled at the destination

**Router-based interconnectivity** refers to the use of routers to connect multiple networks and forward packets between them. Routers examine the source and destination addresses of packers and choose the most efficient path for delivery

**Transport layer protocols** are responsible for delivering data between devices and ensuring reliable communication. Common examples include TCP and UDP

**Application layer protocols** enable communication between applications over the Internet. Example include HTTP for web browsing, HTTPS for secure communication, and FTP for file transfers 

---

3. **What are VDIs and how is a Guest OS different from a Host OS?**

A **virtual Disk Image (VDI)** is a file that stores the contents of a virtual machine, including the operating system, applications and data. It allows virtual machines to be copied, moved and backed up easily

A **Host OS** is the OS installed directly on the physical computer that supports the virtualization software. A Guest OS is the OS running inside the virtual machine. The guest OS uses virtualized resources provided by the host system and hypervisor

**For example, a computer running Windows 11 can be host OS, while Ubuntu Linux running inside VirtualBox is the guest OS**

---

4. **Explain network bandwidth and latency issues common to Internet Architecture**

**Bandwidth** refers to the amount of data that can be transmitted through a network in a given period of time. Applications that transfer large amounts of data require high bandwidth for  good performance

**Latency** is the time taken for data to travel from the source to the destinations. Lower latency means faster response times

Common issues include network congestion, shared communication links and multiple intermediary routers. These factors can reduce bandwidth and increases latency. For cloud applications, bandwidth is important for transferring large files while latency is important for applications requiring responses such as online transactions and real-time systems

---

5. **Describe data center technology and its relevance to modern-day cloud computing** 

A data center is a specialized facility that houses IT resources such as servers, storage systems, databases, software and networking equipment. These resources are centralized to improve efficiency, management and accessibility 

Modern data centers use technologies such as virtualization, a automation, remote management and high-availability systems to support cloud services

Data center technology is important to cloud computing because it provides the infrastructure needed to deliver cloud services. It enables resource sharing, scalability, security and on-demand  access to computing resources for cloud users around the world

---

6. **Differentiate between OS and hardware-based virtualization**

**OS-based virtualization (Type 2)** runs on top of an existing OS. The virtualization software is installed like a normal application and relies on the host OS to access hardware resources. This method is easy to set up but can experience performance overhead because requests must pass through the host operating system

**Hardware-based virtualization (Type 1 hypervisor)** runs directly on the physical hardware without requiring a host OS. Since virtual machines communicate more directly with the hardware, this approach provides better performance and efficiency. It is commonly used in cloud data centers. 

In summary, OS-based virtualization offers simplicity and flexibility while hardware-based virtualization offers better performance and resource utilization

---

7. **What are KVMs and hypervisors?** 

A hypervisor (also known as a Virtual Machine Monitor or VMM) is a software that creates, runs and manages virtual machines. It allows multiple virtual machines to share the same physical hardware while remaining isolated from one another. 

A KVM(Kernel-Based Virtual Machine) is a virtualization technology built into the Linux kernel that allows Linux systems to function as a hypervisor. It enables multiple virtual machines. each with its own operating system, to run on a single physical server.

In simple terms, a hypervisor is the software that manages virtual machines, while KVM is a specific type of hypervisor used in Linux environments


