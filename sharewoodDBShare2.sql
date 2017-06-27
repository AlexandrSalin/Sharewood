DROP DATABASE IF EXISTS sharewoodDB;

CREATE DATABASE sharewoodDB DEFAULT CHARACTER SET 'utf8'
  DEFAULT COLLATE 'utf8_unicode_ci';

USE sharewoodDB;


CREATE TABLE photo(
    id        	BIGINT NOT NULL AUTO_INCREMENT,
    title  	VARCHAR(20) NULL,
    username    VARCHAR(20) NOT NULL,
    shared	BOOLEAN NOT NULL, 	
    PRIMARY KEY (id)
) ENGINE = InnoDB;


CREATE TABLE UserPrincipal (
  UserId BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  Username VARCHAR(30) NOT NULL,
  HashedPassword BINARY(60) NOT NULL,
  AccountNonExpired BOOLEAN NOT NULL,
  AccountNonLocked BOOLEAN NOT NULL,
  CredentialsNonExpired BOOLEAN NOT NULL,
  Enabled BOOLEAN NOT NULL,
  CONSTRAINT user_unique UNIQUE (Username)
) ENGINE = InnoDB;

CREATE TABLE UserPrincipal_Authority (
  UserId BIGINT UNSIGNED NOT NULL,
  Authority VARCHAR(100) NOT NULL,
  UNIQUE KEY UserPrincipal_Authority_User_Authority (UserId, Authority),
  CONSTRAINT UserPrincipal_Authority_UserId FOREIGN KEY (UserId)
    REFERENCES UserPrincipal (UserId) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE WebServiceClient (
  WebServiceClientId BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  ClientId VARCHAR(50) NOT NULL,
  ClientSecret VARCHAR(60) NOT NULL,
  UNIQUE KEY WebServiceClient_ClientId (ClientId)
);

CREATE TABLE WebServiceClient_Scope (
  WebServiceClientId BIGINT UNSIGNED NOT NULL,
  Scope VARCHAR(100) NOT NULL,
  UNIQUE KEY WebServiceClient_Scopes_Client_Scope (WebServiceClientId, Scope),
  CONSTRAINT WebServiceClient_Scopes_ClientId FOREIGN KEY (WebServiceClientId)
    REFERENCES WebServiceClient (WebServiceClientId) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE WebServiceClient_Grant (
  WebServiceClientId BIGINT UNSIGNED NOT NULL,
  GrantName VARCHAR(100) NOT NULL,
  UNIQUE KEY WebServiceClient_Grants_Client_Grant (WebServiceClientId, GrantName),
  CONSTRAINT WebServiceClient_Grants_ClientId FOREIGN KEY (WebServiceClientId)
    REFERENCES WebServiceClient (WebServiceClientId) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE WebServiceClient_RedirectUri (
  WebServiceClientId BIGINT UNSIGNED NOT NULL,
  Uri VARCHAR(1024) NOT NULL,
  CONSTRAINT WebServiceClient_Uris_ClientId FOREIGN KEY (WebServiceClientId)
      REFERENCES WebServiceClient (WebServiceClientId) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE OAuthAccessToken (
  OAuthAccessTokenId BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  TokenKey VARCHAR(50) NOT NULL,
  Value VARCHAR(50) NOT NULL,
  Expiration TIMESTAMP NULL,
  Authentication BLOB NOT NULL,
  UNIQUE KEY OAuthAccessToken_TokenKey (TokenKey),
  UNIQUE KEY OAuthAccessToken_Value (Value)
) ENGINE = InnoDB;

CREATE TABLE OAuthAccessToken_Scope (
  OAuthAccessTokenId BIGINT UNSIGNED NOT NULL,
  Scope VARCHAR(100) NOT NULL,
  UNIQUE KEY OAuthAccessToken_Scopes_Token_Scope (OAuthAccessTokenId, Scope),
  CONSTRAINT OAuthAccessToken_Scopes_TokenId FOREIGN KEY (OAuthAccessTokenId)
    REFERENCES OAuthAccessToken (OAuthAccessTokenId) ON DELETE CASCADE
) ENGINE = InnoDB;

CREATE TABLE OAuthNonce (
  OAuthNonceId BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  Value VARCHAR(50),
  NonceTimestamp BIGINT NOT NULL,
  UNIQUE KEY OAuthNonce_Value_Timestamp (Value, NonceTimestamp)
) ENGINE = InnoDB;


INSERT INTO photo VALUES (
  '1', 'photo1', 'bill', 'false'
);

INSERT INTO photo VALUES (
  '2', 'photo2', 'bob', 'false'
);

INSERT INTO photo VALUES (
  '3', 'photo3', 'bill', 'false'
);

INSERT INTO photo VALUES (
  '4', 'photo4', 'bob', 'false'
);

INSERT INTO photo VALUES (
  '5', 'photo5', 'bill', 'false'
);

INSERT INTO photo VALUES (
  '6', 'photo6', 'bob', 'false'
);



INSERT INTO UserPrincipal (Username, HashedPassword, AccountNonExpired,
                           AccountNonLocked, CredentialsNonExpired, Enabled)
VALUES ( -- bill123
  'bill','$2a$10$6ibaTI8w860/9lcc8yVKGOZdzqOXtAmY/9NA3IpHEfSu65Rux4Vci',
  TRUE, TRUE, TRUE, TRUE
);

INSERT INTO UserPrincipal (Username, HashedPassword, AccountNonExpired,
                           AccountNonLocked, CredentialsNonExpired, Enabled)
VALUES ( -- bob123
  'bob', '$2a$10$2vLvVG9o2Bar8ufCWHUITeDS98FGNcra/JVjmOTaLCsxa3JNmAHkC',
  TRUE, TRUE, TRUE, TRUE
);

INSERT INTO UserPrincipal (Username, HashedPassword, AccountNonExpired,
                           AccountNonLocked, CredentialsNonExpired, Enabled)
VALUES ( -- root123
  'admin', '$2a$10$6ibaTI8w860/9lcc8yVKGOriVZA218m0s9JPXmgOLr/iIoW0GFN.O',
  TRUE, TRUE, TRUE, TRUE
);

INSERT INTO UserPrincipal_Authority (UserId, Authority)
  VALUES (1, 'ROLE_USER');

INSERT INTO UserPrincipal_Authority (UserId, Authority)
  VALUES (2, 'ROLE_USER');

INSERT INTO UserPrincipal_Authority (UserId, Authority)
  VALUES (3, 'ROLE_ADMIN');


INSERT INTO WebServiceClient (ClientId, ClientSecret) VALUES ( -- y471l12D2y55U5558rd2
    'Fleetwood', '$2a$10$elDBcfb/ZKyuNgOPK5.70Oi4gN2EuhU2yONPsoF3avx9.Hd/b8BTa'
);

INSERT INTO WebServiceClient_Scope (WebServiceClientId, Scope)
  VALUES (1, 'READ'), (1, 'WRITE'), (1, 'TRUST');

INSERT INTO WebServiceClient_Grant (WebServiceClientId, GrantName)
  VALUES (1, 'authorization_code');

INSERT INTO WebServiceClient_RedirectUri (WebServiceClientId, Uri)
  VALUES (1, 'http://www.dominique-ubersfeld.com:8090/fleetwood/sharewood'),
 	 (1, 'http://www.dominique-ubersfeld.com:8090/fleetwood/enclume'),
  	 (1, 'http://www.dominique-ubersfeld.com:8090/fleetwood/create');






