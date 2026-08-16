 1. Anonymous Attacker vs Malicious Insider vs Trusted Attacker

**Anonymous Attacker**

- Non-trusted cloud service consumer — has **no permissions** in the cloud at all.
- Exists as an _external_ program, attacks over public networks.
- Since they usually have limited info on the target's security policies/defenses, they tend to go for things like bypassing user accounts or stealing credentials, using methods that keep them anonymous or hard to trace/prosecute.

**Trusted Attacker** (aka malicious tenant)

- Shares IT resources in the **same cloud environment** as the victim (i.e. they're a legit tenant).
- Unlike the anonymous attacker, they attack **from within** the cloud's trust boundary, abusing their own legitimate credentials or misusing sensitive/confidential info they already have access to.
- So basically — they're "trusted" by the system, but abuse that trust.

**Malicious Insider**

- A **human** (not a software agent) attempting to abuse access privileges to the cloud premises — e.g. an employee or customer seeking revenge or personal gain.
- Attack originates from a workstation (the notation for this even has an optional human symbol, since it's about a person's actions, not just a program).

**Comparison summary:**

|                    | Origin                       | Trust Level                    | Nature                                           |
| ------------------ | ---------------------------- | ------------------------------ | ------------------------------------------------ |
| Anonymous Attacker | Outside the cloud            | Non-trusted, no access         | Software agent, network-level attacks            |
| Trusted Attacker   | Inside the cloud (co-tenant) | Trusted, has legit credentials | Software/ consumer agent misusing legit access   |
| Malicious Insider  | Inside the org/ premises     | Trusted employee/ insider      | Human, abuses physical/ system access privileges |


---

 2. The threat/vulnerability/risk diagram (Figure 6.3)

This diagram shows how **security policies and mechanisms are used to counter threats, vulnerabilities, and risks caused by threat agents.** Breaking down the flow:

- The **cloud service owner** (could be the cloud consumer or the cloud provider) is the one who **wants to protect** their assets (IT resources and data).
- To do that, they **establish security policies**, which in turn **regulate countermeasures** (security mechanisms) — this is how they **want to reduce** risk.
- On the threat side: a **threat agent** **poses threats**. Threats **exploit vulnerabilities** (weaknesses in the system).
- Vulnerabilities **lead to risks**, and threats also directly **increase risk**.
- Risks point **to the assets** — meaning if realized, they cause harm to the IT resources/data.
- Countermeasures act to **reduce vulnerabilities**, closing the gap threats could exploit.
- The threat agent's motive is that it **wants to abuse or cause loss to** the assets.

So essentially it's a cause-effect chain: 
**Threat agent → poses Threat → exploits Vulnerability → leads to Risk → affects Assets**, while the owner's defense chain is: **Owner → establishes Policy → regulates Countermeasures → reduces Vulnerability/Risk → protects Assets.**

---

 3. DOS vs DDOS + diagram questions

**What is a DOS Attack, and how's it different from DDOS?**

- A **Denial of Service (DOS)** attack occurs when a targeted IT resource is **overloaded with requests**, in an attempt to cripple it or make it unavailable to legitimate users — this comes from a **single** source/attacker.
- A **Distributed** Denial of Service (**DDOS**) attack is the same idea, but the flood of requests comes from **multiple sources simultaneously** (e.g. a botnet of many compromised machines), rather than one attacker. This makes it harder to block (you can't just block one IP) and generates way more traffic volume.

**a. What type of attack is depicted in the diagram? Justify.**  
This is a **DOS (Denial of Service) attack** — specifically only **one** attacker (Cloud Service Consumer A) is shown sending multiple messages. Consumer A floods the cloud service on Virtual Server A with requests, which **overloads** the underlying physical server. This matches the slide's DOS description exactly: _"Cloud Service Consumer A sends multiple messages to a cloud service hosted on Virtual Server A. This overloads the capacity of the underlying physical server, which causes outages with Virtual Servers A and B."_ Since there's only a single source of attack (not multiple distributed attackers), it's DOS, not DDOS.

**b. Which information security concepts are violated?**

- **Availability** — the main one. Legitimate Cloud Service Consumer B can no longer communicate with cloud services on Virtual Servers A or B (its requests get the "X" / blocked in the diagram), because the resource has been exhausted by the attack.
- Depending on how you read it, you could also argue **Integrity** is at risk if the overload causes the system to behave unpredictably, but the core violation illustrated is **availability** — the service becomes unavailable to legitimate consumers.

**c. Countermeasures against such an attack**  
From the slides' Counter Measures section, relevant ones include:

- **Monitoring** — to detect abnormal traffic spikes early.
- **Access control management / Identity Management (IDM)** — to authenticate and restrict who can send requests.
- **Minimize multi-tenancy risk** — isolate resources so one consumer's overload doesn't take down others sharing the same physical server (this is exactly what happened to Virtual Server B in the diagram, even though it wasn't the target).
- Rate limiting / request throttling and resource capping policies (mentioned under vulnerabilities — "no policies for resource capping" is listed as a weakness that enables this kind of attack).
- Utilizing different/multiple clouds to distribute load and reduce single points of failure.