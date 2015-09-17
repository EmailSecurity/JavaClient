# EmailSecurity
Standalone Java based email client.

#Group Members
Akash Singh
Abhijeet Ranadive

#Abstract
From list of groceries to next acquisi- tion of shares, most modern day com- munications relies on the service pro- vided by electronic mail. It’s almost given that service providers take ex- tensive care of communication layer security and safely storing user data. But, who protects the privacy of a user from the service provider itself? What if they are compromised? What if they decide to start going through your mail? Do we still consider that secure? Do we still have our privacy? Well, as absurd and far fetched as it sounds, most service providers have been caught or have openly admitted to doing so.
With this project we aim to provide a true sense of privacy where, nobody apart from the intended party can read and access the messages. This application will use modern encryption tools to patch a massive security hole in the most widely used communication platform.

#Problem Statement & Motivation
”When you upload, submit, store, send or receive content to or through our services, you give Google (and those we work with) a world- wide license to use, host, store,reproduce, modify, create derivative works (such as those resulting from translations, adaptations or other changes we make so that your content works better with our services), communicate, publish, publicly perform, publicly display and distribute such content.” -Google[1]
Widespread usage of e-mail services with such absurd privacy policies has been our biggest motivation for developing a solution to protect the privacy of users. If the most trusted and used email provider is guilty of partaking in such privacy breaches, how can a user be comfortable sending sensitive/private data without a better solution. Even though till now there has been no such reported incidents where a provider like Google was guilty of any malicious practice, we can never be sure that this wouldn’t change. And it’s not only a privacy breach when such a service provider is hacked or decides to sell user information, but also when it participates in programs like PRISM, which is used for mass surveillance of users by the government itself.

#Solution
We will develop a fully functional email client in Java, that will utilize the PGPHelper class, along with RSA keys for encrypting and de- crypting emails. This client will also imple- ment HMAC based data integrity and authen- tication scheme. The client will maintain a database for storing contacts along with their public keys, which will be used for establish- ing a secure communication. The client will require initial setup/handshaking for acquir- ing the public keys of both parties.

#Implementation Details

Platform
Java Virtual Machine (JVM)
Cross platform since JVM is supported on Linux, Unix, Windows and Android Operating Systems

Language
• Java Programming Language
• Structured Query Language 3.3 Components

Components
• Java Cryptographic Libraries • RSA key pairs
• mySQL Database
