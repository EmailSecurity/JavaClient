# EmailSecurity
Standalone Java based secure email client.

#Authors
Akash Singh
Abhijeet Ranadive


#Motivation
”When you upload, submit, store, send or receive content to or through our services, you give Google (and those we work with) a world- wide license to use, host, store,reproduce, modify, create derivative works (such as those resulting from translations, adaptations or other changes we make so that your content works better with our services), communicate, publish, publicly perform, publicly display and distribute such content.” -Google[1]
Widespread usage of e-mail services with such absurd privacy policies has been our biggest motivation for developing a solution to protect the privacy of users. If the most trusted and used email provider is guilty of partaking in such privacy breaches, how can a user be comfortable sending sensitive/private data without a better solution. Even though till now there has been no such reported incidents where a provider like Google was guilty of any malicious practice, we can never be sure that this wouldn’t change. And it’s not only a privacy breach when such a service provider is hacked or decides to sell user information, but also when it participates in programs like PRISM, which is used for mass surveillance of users by the government itself.

#Solution
We have developed a fully functional email client in Java, that will utilize the PGP structure, i.e. use RSA keys for establishing secure channel and then using AES and SHA256 for ecryption and data integration. The client will share the RSA keys via email service provider by ecrypting them with a password based key (using PBKDF), thus eliminating the possibility of the email service provider acting as a man in the middle (active adversary).

#How to use
• Download the Final_Distributable.zip
• Extract the contents
• Open the accountDetailsFile.txt and edit the file in the following manner- 
<hr>
<sender's email address><br>
<sender's user name><br>
<sender's password><br>

Platform <br>
Java Virtual Machine (JVM)
Cross platform since JVM is supported on Linux, Unix, Windows and Android Operating Systems

Language <br>
• Java Programming Language<br>
• Structured Query Language 3.3 Components<br>

Components <br>
• Java Cryptographic Libraries<br> • RSA key pairs<br>
• mySQL Database
