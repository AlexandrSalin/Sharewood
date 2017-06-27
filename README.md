# Sharewood
Java based demo photo sharing server that handles both HTTP and REST requests. The HTTP server is protected by Spring Security. The REST server is protected by Spring OAuth2.

All HTTP request are mapped to the root "/"
All REST requests are mapped to "/services/Rest/*"

The REST server is protected by Spring OAuth2 using an Authorization Code Grant.

A MySql database is used to store all photos and all tables that belong to the authentication and authorization mecahanisms.
A sample creation SQL file is provided. It creates only on REST client named Fleetwood. 
