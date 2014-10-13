CREATE TABLE UserTab
(
  UserID 	SERIAL NOT NULL PRIMARY KEY,
  EMail 	varchar(40) NOT NULL,
  Name 		varchar(40) NOT NULL,
  LastName	varchar(40) NOT NULL

) 
WITHOUT OIDS;

CREATE TABLE ShippingTab
(
  ShipID 	SERIAL NOT NULL PRIMARY KEY,
  Price 	float NOT NULL,  
  Shipping 	varchar(40) NOT NULL,

  CONSTRAINT pos_Price CHECK (Price >= 0.00::double precision)
) 
WITHOUT OIDS;

CREATE TABLE PaymentTab
(
  PayID 	SERIAL NOT NULL PRIMARY KEY,
  Price 	float NOT NULL,  
  Payment 	varchar(40) NOT NULL,

  CONSTRAINT pos_Price CHECK (Price >= 0.00::double precision)
) 
WITHOUT OIDS;

CREATE TABLE ShippingAddressTab
(
  ShippingID	SERIAL NOT NULL PRIMARY KEY,
  Street	varchar(40) NOT NULL,
  PLZ	        int NOT NULL, 
  Place		varchar(40) NOT NULL,
  Country	varchar(40)

) 
WITHOUT OIDS;

CREATE TABLE OrderTab
(
  OrderID      	SERIAL NOT NULL PRIMARY KEY,
  UserID	int NOT NULL,
  ShipID	int NOT NULL,
  PayID		int NOT NULL,
  TotalPrice 	float NOT NULL,  
  AddressID	int,
  Date		DATE DEFAULT 'now()',
  Sent		boolean NOT NULL DEFAULT 'false',
  ShippingID 	int DEFAULT 0,

  CONSTRAINT pos_Price CHECK (TotalPrice >= 0.00::double precision),

  CONSTRAINT foreignKey FOREIGN KEY (UserID)
      REFERENCES UserTab (UserID) MATCH SIMPLE,

  CONSTRAINT foreignKeyShipping FOREIGN KEY (ShippingID)
      REFERENCES ShippingAddressTab (ShippingID) MATCH SIMPLE,

  CONSTRAINT foreignKeyShip FOREIGN KEY (ShipID)
      REFERENCES ShippingTab (ShipID) MATCH SIMPLE,

  CONSTRAINT foreignKeyPayment FOREIGN KEY (PayID)
      REFERENCES PaymentTab (PayID) MATCH SIMPLE

)
WITHOUT OIDS;

CREATE TABLE ManufacturerTab
(
  
  ManufacturerID	SERIAL NOT NULL PRIMARY KEY,
  Manufacturer		varchar(40) NOT NULL

) 
WITHOUT OIDS;

CREATE TABLE CategoryTab

(
  CategoryID 	SERIAL NOT NULL PRIMARY KEY,
  Category 	varchar(40),
  Subcategory	varchar(40),
  Attribut1	varchar(40),
  Attribut2	varchar(40),
  Attribut3	varchar(40),
  Attribut4	varchar(40),
  Attribut5	varchar(40),
  Attribut6	varchar(40),
  Attribut7	varchar(40)

) 
WITHOUT OIDS;

CREATE TABLE ArticleTab
(
  
  ArtID 		SERIAL NOT NULL PRIMARY KEY,
  ArtName		varchar(240),
  Price			float NOT NULL DEFAULT 1000,
  CategoryID   		int,
  Description		TEXT,
  Quantity		int,
  ManufacturerID	int,	
  Liked			int,
  Offer			boolean DEFAULT 'false',
  Discount		float,
  

  CONSTRAINT pos_Price CHECK (Price >= 0.00::double precision),

  CONSTRAINT foreignKeyCategory FOREIGN KEY (CategoryID)
      REFERENCES CategoryTab (CategoryID) MATCH SIMPLE,

  CONSTRAINT foreignKeyManufacturer FOREIGN KEY (ManufacturerID)
      REFERENCES ManufacturerTab (ManufacturerID) MATCH SIMPLE
) 
WITHOUT OIDS;

CREATE TABLE OrdertArticleTab
(
  ArtID 	int NOT NULL PRIMARY KEY,
  Number 	int,


  CONSTRAINT foreignKeyArticle FOREIGN KEY (ArtID)
      REFERENCES ArticleTab (ArtID) MATCH SIMPLE

) 
WITHOUT OIDS;

CREATE TABLE AGBTab
(
  
  UserID	int NOT NULL PRIMARY KEY,
  AGBaccepted 	boolean NOT NULL DEFAULT 'false',

  CONSTRAINT foreignKey FOREIGN KEY (UserID)
      REFERENCES UserTab (UserID) MATCH SIMPLE

) 
WITHOUT OIDS;

CREATE TABLE AddressTab
(
  
  UserID	int PRIMARY KEY,
  Street	varchar(40) NOT NULL,
  PLZ		int NOT NULL,
  Place		varchar(40) NOT NULL,
  Country	varchar(40),

  CONSTRAINT foreignKey FOREIGN KEY (UserID)
      REFERENCES UserTab (UserID) MATCH SIMPLE

) 
WITHOUT OIDS;

CREATE TABLE AuthTab
(
  UserID	int NOT NULL PRIMARY KEY,
  PW	 	varchar NOT NULL,


  CONSTRAINT foreignKey FOREIGN KEY (UserID)
      REFERENCES UserTab (UserID) MATCH SIMPLE

) 
WITHOUT OIDS;

CREATE TABLE RightsTab
(
  
  UserID	int PRIMARY KEY,
  AdminRight 	boolean NOT NULL DEFAULT 'false',
  Supporter  	boolean NOT NULL DEFAULT 'false',


  CONSTRAINT foreignKey FOREIGN KEY (UserID)
      REFERENCES UserTab (UserID) MATCH SIMPLE

) 
WITHOUT OIDS;

CREATE TABLE SpecTab
(
  ArtID 	int NOT NULL PRIMARY KEY,
  Value1	varchar(280),
  Value2	varchar(280),
  Value3	varchar(280),
  Value4	varchar(280),
  Value5	varchar(280),
  Value6	varchar(280),
  Value7	varchar(280),

  CONSTRAINT foreignKeyArticle FOREIGN KEY (ArtId)
      REFERENCES ArticleTab (ArtId) MATCH SIMPLE
) 
WITHOUT OIDS;