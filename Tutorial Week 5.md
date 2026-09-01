
---


**1. Logical Network Perimeter** Establishes a _virtual_ network boundary that isolates a group of related, physically distributed cloud IT resources from the rest of the network — without physically separating them. It's used to isolate resources from non-authorized users, non-users, and even other cloud consumers, and to control bandwidth allocated to the isolated resources. Boundary is shown using a dashed-line notation, and is usually enforced with a virtual firewall (filters traffic) and virtual network/VLAN (isolates the segment).

**2. VPN** A VPN connects two separate logical network perimeters (e.g. cloud consumer's on-premise site and the cloud provider's environment) and secures the communication between them as it crosses the public network. This is achieved through **point-to-point encryption** of data packets between the two endpoints, so traffic stays protected in transit even over shared infrastructure. _(diagram: two dashed perimeter boxes linked by an encrypted VPN tunnel)_

**3. Virtual Server vs Physical Server** A virtual server is virtualization software that emulates a physical server, letting a provider host multiple isolated virtual server instances for different consumers on one physical machine. Difference: a physical server is one real machine; a virtual server is a software instance that can be rapidly created from an image file and customized independently by each consumer. It relies entirely on a virtualization environment — specifically the **hypervisor**, which runs on the physical host and generates/manages the virtual (guest) servers.

**4. Virtual Infrastructure Manager (VIM)** Coordinates server hardware so virtual server instances can be created from the most convenient physical server. Unlike a hypervisor (limited to one physical server), the VIM administers **multiple hypervisors across multiple physical servers**, giving centralized control over virtual server creation and management.

**5. Hypervisor Limitations**

- Limited to one physical server — can only create virtual images of that server.
- Can only assign generated virtual servers to resource pools on that same server.
- Limited management features (e.g. only basic capacity increase/shutdown, no broad admin functions).

**6. Cloud Usage Monitor** **Purpose:** lightweight, autonomous module that collects/processes IT resource usage data for billing, SLA tracking, and reporting. **Specialized variations (3 agent-based formats):**

- **Monitoring Agent** – event-driven, sits on the communication path, intercepts request messages to log usage (e.g. network traffic/messages) without touching the response.
- **Resource Agent** – collects usage via event-driven interaction with resource software, triggered by events like initiating, suspending, resuming, or scaling.
- **Polling Agent** – periodically polls resources to check status (e.g. uptime/downtime), logging changes after each polling cycle. All three can forward data to a log database for reporting.

**7. Why Resource Replication is Vital** Creates multiple instances of the same IT resource to boost **availability and performance** — replicas provide redundancy (failover if one instance fails/overloads) and support scalability (load spread across copies). Implemented via virtualization (hypervisor replicating virtual server instances from a stored image), so it's fast and on-demand — matching cloud's elasticity requirement.
