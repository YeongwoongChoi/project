create database termProject default character set UTF8;
use termProject;

create table vendor (
	vendorCode varchar(6) primary key,
	vendorName varchar(35) not null,
    ingredients varchar(30),
    price decimal(7, 2) not null,
    constraint CHK_price
    check (price > 0.00)
);

create table owner (
	RRN char(15) primary key,
    ownerName varchar(35) not null,
    phoneNumber char(12),
    
    check (RRN like '%-%'),
    constraint CHK_numberType
    check (phoneNumber like '%-%-%')
);

create table restaurant (
	restaurantCode varchar(10) primary key,
    typeOfDishes varchar(35),
    restaurantName varchar(50) not null,
    location varchar(150) not null,
    rating decimal(3, 2),
    phoneNumber char(12),
    ownerRRN char(15) not null,
    
    foreign key (ownerRRN) references owner(RRN),
    constraint CHK_phoneNumber
    check (phoneNumber like '%-%-%'),
    constraint CHK_rating
    check (rating >= 0.0 and rating <= 5.0)
);

create table supply (
	restaurantCode varchar(10) not null,
    vendorCode varchar(6) not null,
	suppliedDate date not null,
    suppliedQuantities int not null,
    
    constraint CHK_suppliedQuantities
    check (suppliedQuantities > 0),
    
    primary key (restaurantCode, vendorCode),
    
    foreign key (restaurantCode) references restaurant(restaurantCode),
    foreign key (vendorCode) references vendor(vendorCode)
);

create table dish (
	dishIdentifier varchar(10) primary key,
    dishName varchar(35) not null,
    dishPrice decimal(10, 2) not null
);

create table serve (
	restaurantCode varchar(10) not null,
    dishIdentifier varchar(10) not null,
    
    primary key (restaurantCode, dishIdentifier),
    
    foreign key (restaurantCode) references restaurant(restaurantCode),
    foreign key (dishIdentifier) references dish(dishIdentifier)
);

create table style (
	styleName varchar(30) primary key,
    description varchar(200)
);

create table classify (
	styleName varchar(30) not null,
    dishIdentifier varchar(10) not null,
    
    primary key (styleName, dishIdentifier),
    
    foreign key (styleName) references style(styleName),
    foreign key (dishIdentifier) references dish(dishIdentifier)
);

create table customer (
	customerIdentifier varchar(15) primary key,
    customerName varchar(30) not null,
    customerSex char(7) not null,
    customerAge int,
    earnedPoint int not null
);

create table prefer (
	customerIdentifier varchar(15) not null,
    styleName varchar(30) not null,
    
    primary key (customerIdentifier, styleName),
    
    foreign key (customerIdentifier) references customer(customerIdentifier),
    foreign key (styleName) references style(styleName)
    on delete cascade
);

create table customerContact (
	customerIdentifier varchar(15) not null,
    phoneNumber char(12) not null,
    
    primary key (customerIdentifier, phoneNumber),
    
    constraint CHK_phoneNumberType
    check (phoneNumber like '%-%-%'),
    
    foreign key (customerIdentifier) references customer(customerIdentifier)
    on delete cascade
);

create table customerOrder (
	customerIdentifier varchar(15) not null,
    restaurantCode varchar(10) not null,
    dishIdentifier varchar(10) not null,
    totalDishes int not null,
	
    primary key (customerIdentifier, restaurantCode, dishIdentifier),
    
    foreign key (customerIdentifier) references customer(customerIdentifier),
    foreign key (restaurantCode) references restaurant(restaurantCode),
    foreign key (dishIdentifier) references dish(dishIdentifier),
    
    constraint CHK_totalDishes
    check (totalDishes > 0)
);

create table reserve (
	customerIdentifier varchar(15) not null,
    restaurantCode varchar(10) not null,
    reservedTime datetime not null,
    numberOfPeople int,
    
    constraint UNQ_time unique(reservedTime),
    primary key (customerIdentifier, restaurantCode, reservedTime),
    
    foreign key (customerIdentifier) references customer(customerIdentifier),
    foreign key (restaurantCode) references restaurant(restaurantCode)
);

create table broadcast (
	name varchar(50) primary key,
    broadcastingCompany varchar(20)
);

create table televise (
	broadcastName varchar(50) not null,
    restaurantCode varchar(10) not null,
    televisedDate date not null,
    
    primary key (broadcastName, restaurantCode),
    
    foreign key (broadcastName) references broadcast(name),
    foreign key (restaurantCode) references restaurant(restaurantCode)
);

create table watch (
	customerIdentifier varchar(15) not null,
    broadcastName varchar(50) not null,
    
    primary key (customerIdentifier, broadcastName),
    
    foreign key (customerIdentifier) references customer(customerIdentifier),
    foreign key (broadcastName) references broadcast(name)
);

commit;