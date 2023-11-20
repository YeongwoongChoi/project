use termproject;

# insert data into Vendor
insert into vendor values('V-001', 'Fresh & Cool', 'vegetables', 24.62);
insert into vendor values('V-002', 'Agricultural Cooperation', 'mushroom', 17.2);
insert into vendor values('V-003', 'Fresh Market', 'squid and octopus', 31.82);
insert into vendor values('V-004', 'James fresh', 'mackerel and belt fish', 18.2);
insert into vendor values('V-005', 'Angelo Poultry', 'poultry', 13.03);
insert into vendor values('V-006', 'Meat Market', 'pork and beef', 43.94);
insert into vendor values('V-007', 'Busan Fishcake', 'processed fish', 14.32);
insert into vendor values('V-008', 'Brady Poultry', 'poultry and eggs', 12.35);
insert into vendor values('V-009', 'Rice Mill', 'rice and millet', 25.45);
insert into vendor values('V-010', 'Herbal Garden', 'herbs', 14.7);
insert into vendor values('V-011', 'Another Rice Mill', 'rice and millet', 25.91);
insert into vendor values('V-012', 'Meet Meat', 'poultry and pork', 17.42);

/* if needed, I'll add some data */
commit;

# insert data into Owner
insert into owner(RRN, ownerName) values('840102-1234517', 'John Smith');
insert into owner values('000424-3012345', 'Yeongwoong Choi', '325-895-7178');
insert into owner values('930203-1123053', 'Dwayne Carpenter', '580-201-1888');
insert into owner values('700128-1945065', 'Alfonso Ortiz', '806-297-8828');
insert into owner values('871103-1193215', 'Michael Weiss', '901-143-4844');
insert into owner values('680810-2087392', 'Brenda Schultz', '260-634-5852');
insert into owner values('950129-2392911', 'Pearl Doyle', '725-079-6085');
insert into owner values('651005-2596043', 'Susanne Graham', '385-203-3508');

/* if needed, I will put more */
commit;

# insert data into Restaurant
/* NOTE: These names are NOT actually related with real restaurants. */
insert into restaurant values('R-0001', 'American', 'Cork & Pig Tavern', 'San Angelo', 3.9, '325-227-6988', '840102-1234517');
insert into restaurant values('R-0002', 'Korean', 'Kogi Korean Grill', 'San Antonio', 4.1, '210-558-2018', '000424-3012345');
insert into restaurant values('R-0003', 'Mexican', 'La Esperanza', 'San Angelo', 4.3, '325-223-0204', '700128-1945065');
insert into restaurant values('R-0004', 'Chinese', 'Dragonlicious', 'San Angelo', 3.9, '325-939-1888', '680810-2087392');
insert into restaurant values('R-0005', 'Korean', 'BCD Tofu house', 'Los Angeles', 4.3, '213-382-6677', '000424-3012345');
insert into restaurant values('R-0006', 'American', 'SkinnyFats', 'Las Vegas', 4.6, '702-577-3232', '950129-2392911');
insert into restaurant values('R-0007', 'Japanese', 'Sushi House', 'San Angelo', 4.5, '325-949-0800', '930203-1123053');
insert into restaurant values('R-0008', 'Mexican', 'The Original Mex', 'San Angelo', 4.4, '325-223-0171', '651005-2596043');

commit;

# insert data into Supply
insert into supply values('R-0001', 'V-005', str_to_date('2023/11/1', '%Y/%m/%e'), 50);
insert into supply values('R-0001', 'V-006', str_to_date('2023/10/4', '%Y/%m/%e'), 100);
insert into supply values('R-0001', 'V-008', str_to_date('2023/11/12', '%Y/%m/%e'), 140);
insert into supply values('R-0001', 'V-012', str_to_date('2023/11/18', '%Y/%m/%e'), 75);
insert into supply values('R-0002', 'V-003', str_to_date('2023/10/30', '%Y/%m/%e'), 28);
insert into supply values('R-0002', 'V-001', str_to_date('2023/10/31', '%Y/%m/%e'), 60);
insert into supply values('R-0002', 'V-006', str_to_date('2023/10/30', '%Y/%m/%e'), 20);
insert into supply values('R-0002', 'V-008', str_to_date('2023/11/13', '%Y/%m/%e'), 15);
insert into supply values('R-0002', 'V-009', str_to_date('2023/10/5', '%Y/%m/%e'), 30);
insert into supply values('R-0002', 'V-010', str_to_date('2023/10/7', '%Y/%m/%e'), 38);
insert into supply values('R-0002', 'V-012', str_to_date('2023/11/19', '%Y/%m/%e'), 10);
insert into supply values('R-0003', 'V-001', str_to_date('2023/10/30', '%Y/%m/%e'), 40);
insert into supply values('R-0003', 'V-006', str_to_date('2023/11/25', '%Y/%m/%e'), 80);
insert into supply values('R-0003', 'V-009', str_to_date('2023/11/13', '%Y/%m/%e'), 35);
insert into supply values('R-0004', 'V-001', str_to_date('2023/10/25', '%Y/%m/%e'), 41);
insert into supply values('R-0004', 'V-005', str_to_date('2023/11/2', '%Y/%m/%e'), 10);
insert into supply values('R-0004', 'V-006', str_to_date('2023/11/4', '%Y/%m/%e'), 33);
insert into supply values('R-0004', 'V-007', str_to_date('2023/11/17', '%Y/%m/%e'), 20);
insert into supply values('R-0004', 'V-009', str_to_date('2023/11/25', '%Y/%m/%e'), 33);
insert into supply values('R-0005', 'V-001', str_to_date('2023/11/9', '%Y/%m/%e'), 15);
insert into supply values('R-0005', 'V-002', str_to_date('2023/11/15', '%Y/%m/%e'), 10);
insert into supply values('R-0005', 'V-007', str_to_date('2023/11/23', '%Y/%m/%e'), 10);
insert into supply values('R-0005', 'V-003', str_to_date('2023/11/8', '%Y/%m/%e'), 15);
insert into supply values('R-0005', 'V-004', str_to_date('2023/11/5', '%Y/%m/%e'), 10);
insert into supply values('R-0005', 'V-005', str_to_date('2023/11/22', '%Y/%m/%e'), 10);
insert into supply values('R-0005', 'V-010', str_to_date('2023/10/27', '%Y/%m/%e'), 50);
insert into supply values('R-0005', 'V-011', str_to_date('2023/10/27', '%Y/%m/%e'), 40);
insert into supply values('R-0006', 'V-001', str_to_date('2023/11/16', '%Y/%m/%e'), 35);
insert into supply values('R-0006', 'V-002', str_to_date('2023/11/29', '%Y/%m/%e'), 20);
insert into supply values('R-0006', 'V-003', str_to_date('2023/11/3', '%Y/%m/%e'), 45);
insert into supply values('R-0006', 'V-005', str_to_date('2023/10/1', '%Y/%m/%e'), 20);
insert into supply values('R-0006', 'V-006', str_to_date('2023/11/26', '%Y/%m/%e'), 100);
insert into supply values('R-0006', 'V-007', str_to_date('2023/9/11', '%Y/%m/%e'), 50);
insert into supply values('R-0006', 'V-009', str_to_date('2023/9/16', '%Y/%m/%e'), 230);
insert into supply values('R-0006', 'V-012', str_to_date('2023/11/15', '%Y/%m/%e'), 20);
insert into supply values('R-0007', 'V-004', str_to_date('2023/11/20', '%Y/%m/%e'), 15);
insert into supply values('R-0007', 'V-009', str_to_date('2023/9/30', '%Y/%m/%e'), 50);
insert into supply values('R-0008', 'V-003', str_to_date('2023/11/14', '%Y/%m/%e'), 8);
insert into supply values('R-0008', 'V-007', str_to_date('2023/10/21', '%Y/%m/%e'), 30);
insert into supply values('R-0008', 'V-008', str_to_date('2023/11/6', '%Y/%m/%e'), 15);
insert into supply values('R-0008', 'V-009', str_to_date('2023/10/25', '%Y/%m/%e'), 30);

commit;

# insert data into Dish

insert into dish values('F-001', 'Pork ribs', 19.99);
insert into dish values('F-002', 'Porterhouse Steak', 34.99);
insert into dish values('F-003', 'Korean BBQ', 16.8);
insert into dish values('F-004', 'Pork Cutlet', 13.79);
insert into dish values('F-005', 'Bibimbap', 15.4);
insert into dish values('F-006', 'Spicy noodles', 13.5);
insert into dish values('F-007', 'Dumplings', 8.99);
insert into dish values('F-008', 'Salmon Roll Sushi', 15.99);
insert into dish values('F-009', 'Lo Mein', 14.7);
insert into dish values('F-010', 'Orange Chicken', 12.99);
insert into dish values('F-011', 'Chef\'s Special', 35.99);
insert into dish values('F-012', 'Spicy Tofu Soup', 16.99);
insert into dish values('F-013', 'Enchiladas', 10.4);
insert into dish values('F-014', 'Quesadilla', 12.5);
insert into dish values('F-015', 'Fajita', 16.69);
insert into dish values('F-016', 'California Roll Sushi', 9.99);
insert into dish values('F-017', 'Beef Stir Fry', 13.99);
insert into dish values('F-018', 'Mackerel Deep Fry', 15.2);
insert into dish values('F-019', 'Cheeseburger', 10.99);
insert into dish values('F-020', 'Ribeye Steak', 25.79);
commit;

# insert data into Serve

insert into serve values('R-0001', 'F-001');
insert into serve values('R-0001', 'F-002');
insert into serve values('R-0001', 'F-011');
insert into serve values('R-0001', 'F-019');
insert into serve values('R-0002', 'F-003');
insert into serve values('R-0002', 'F-004');
insert into serve values('R-0002', 'F-005');
insert into serve values('R-0002', 'F-006');
insert into serve values('R-0002', 'F-007');
insert into serve values('R-0002', 'F-012');
insert into serve values('R-0002', 'F-017');
insert into serve values('R-0003', 'F-011');
insert into serve values('R-0003', 'F-013');
insert into serve values('R-0003', 'F-014');
insert into serve values('R-0003', 'F-015');
insert into serve values('R-0004', 'F-006');
insert into serve values('R-0004', 'F-007');
insert into serve values('R-0004', 'F-009');
insert into serve values('R-0004', 'F-010');
insert into serve values('R-0004', 'F-011');
insert into serve values('R-0004', 'F-017');
insert into serve values('R-0005', 'F-003');
insert into serve values('R-0005', 'F-005');
insert into serve values('R-0005', 'F-006');
insert into serve values('R-0005', 'F-007');
insert into serve values('R-0005', 'F-012');
insert into serve values('R-0005', 'F-017');
insert into serve values('R-0005', 'F-018');
insert into serve values('R-0006', 'F-001');
insert into serve values('R-0006', 'F-002');
insert into serve values('R-0006', 'F-020');
insert into serve values('R-0006', 'F-011');
insert into serve values('R-0006', 'F-019');
insert into serve values('R-0007', 'F-007');
insert into serve values('R-0007', 'F-008');
insert into serve values('R-0007', 'F-011');
insert into serve values('R-0007', 'F-016');
insert into serve values('R-0008', 'F-011');
insert into serve values('R-0008', 'F-013');
insert into serve values('R-0008', 'F-014');
insert into serve values('R-0008', 'F-015');

commit;

# insert data into Style

insert into style values('American', 'including steak and burger');
insert into style values('Korean', 'including Korean BBQ and some dishes made with rice');
insert into style values('Mexican', 'including Mexican food like enchilada and quesadilla');
insert into style values('Chinese', 'including American-Chinese food like orange chicken');
insert into style values('Japanese', 'including some types of sushi');

commit;

# insert data into Classify

insert into classify values('American', 'F-001');
insert into classify values('American', 'F-002');
insert into classify values('American', 'F-011');
insert into classify values('American', 'F-019');
insert into classify values('American', 'F-020');
insert into classify values('Korean', 'F-003');
insert into classify values('Korean', 'F-004');
insert into classify values('Japanese', 'F-004');
insert into classify values('Korean', 'F-005');
insert into classify values('Korean', 'F-006');
insert into classify values('Chinese', 'F-006');
insert into classify values('Korean', 'F-007');
insert into classify values('Chinese', 'F-007');
insert into classify values('Japanese', 'F-007');
insert into classify values('Japanese', 'F-008');
insert into classify values('Chinese', 'F-009');
insert into classify values('Chinese', 'F-010');
insert into classify values('Chinese', 'F-011');
insert into classify values('Korean', 'F-011');
insert into classify values('Japanese', 'F-011');
insert into classify values('Mexican', 'F-011');
insert into classify values('Korean', 'F-012');
insert into classify values('Mexican', 'F-013');
insert into classify values('Mexican', 'F-014');
insert into classify values('Mexican', 'F-015');
insert into classify values('Japanese', 'F-016');
insert into classify values('Korean', 'F-017');
insert into classify values('Chinese', 'F-017');
insert into classify values('Korean', 'F-018');
insert into classify values('Japanese', 'F-018');

commit;

# insert data into Customer

insert into customer values('hero00', 'Yeongwoong Choi', 'Male', 23, 4700);
insert into customer values('pr3341', 'Pete Roy', 'Male', 35, 800);
insert into customer values('cb3376', 'Cecil Barnes', 'Male', 41, 1000);
insert into customer values('apotter12', 'Armando Potter', 'Male', 24, 230);
insert into customer values('clambert5639', 'Christian Lambert', 'Male', 34, 750);
insert into customer values('jm2005', 'Joey Mitchell', 'Male', 18, 300);
insert into customer values('snovak123', 'Scott Novak', 'Male', 51, 1350);
insert into customer values('rwolf8876', 'Rich Wolf', 'Male', 40, 560);
insert into customer values('cs1234', 'Christy Stokes', 'Female', 36, 1400);
insert into customer values('cathys2034', 'Cathy Stokes', 'Female', 20, 1330);
insert into customer values('sallysally11', 'Sally Bowers', 'Female', 47, 1200);
insert into customer values('ahuston83', 'Annie Huston', 'Female', 30, 1020);
insert into customer values('vvblevins3456', 'Vivian Blevins', 'Female', 38, 1450);
insert into customer values('mboyer9102', 'Marcy Boyer', 'Female', 27, 1200);
insert into customer values('amcc12345', 'Ann McConnell', 'Female', 35, 1000);
insert into customer values('sx1024', 'Stacie Cross', 'Female', 45, 860);

commit;

# insert data into customerContact

insert into customerContact values('hero00', '325-895-7178');
insert into customerContact values('hero00', '325-456-7890');
insert into customerContact values('pr3341', '203-085-6848');
insert into customerContact values('cb3376', '213-923-6591');
insert into customerContact values('apotter12', '352-247-9621');
insert into customerContact values('clambert5639', '862-888-3508');
insert into customerContact values('jm2005', '303-732-6540');
insert into customerContact values('snovak123', '512-926-6942');
insert into customerContact values('rwolf8876', '775-646-8702');
insert into customerContact values('cs1234', '936-733-7493');
insert into customerContact values('cathys2034', '430-114-3339');
insert into customerContact values('sallysally11', '304-382-0517');
insert into customerContact values('ahuston83', '915-674-0520');
insert into customerContact values('vvblevins3456', '773-504-6551');
insert into customerContact values('mboyer9102', '210-984-1498');
insert into customerContact values('amcc12345', '325-407-7310');
insert into customerContact values('sx1024', '623-614-3965');
commit;

# insert data into Prefer

insert into prefer values('hero00', 'American');
insert into prefer values('hero00', 'Japanese');
insert into prefer values('hero00', 'Chinese');
insert into prefer values('hero00', 'Korean');
insert into prefer values('hero00', 'Mexican');
insert into prefer values('pr3341', 'American');
insert into prefer values('pr3341', 'Mexican');
insert into prefer values('cb3376', 'American');
insert into prefer values('cb3376', 'Chinese');
insert into prefer values('apotter12', 'Japanese');
insert into prefer values('apotter12', 'Mexican');
insert into prefer values('clambert5639', 'American');
insert into prefer values('jm2005', 'Korean');
insert into prefer values('jm2005', 'Mexican');
insert into prefer values('snovak123', 'American');
insert into prefer values('rwolf8876', 'Mexican');
insert into prefer values('rwolf8876', 'Chinese');
insert into prefer values('cs1234', 'Japanese');
insert into prefer values('cs1234', 'Korean');
insert into prefer values('cathys2034', 'American');
insert into prefer values('sallysally11', 'American');
insert into prefer values('ahuston83', 'American');
insert into prefer values('ahuston83', 'Japanese');
insert into prefer values('vvblevins3456', 'Mexican');
insert into prefer values('mboyer9102', 'American');
insert into prefer values('mboyer9102', 'Mexican');
insert into prefer values('amcc12345', 'Korean');
insert into prefer values('sx1024', 'American');
insert into prefer values('sx1024', 'Chinese');

commit;

# insert data into customerOrder

insert into customerOrder values('hero00', 'R-0001', 'F-001', 2);
insert into customerOrder values('pr3341', 'R-0002', 'F-003', 1);
insert into customerOrder values('cb3376', 'R-0002', 'F-012', 2);
insert into customerOrder values('apotter12', 'R-0003', 'F-015', 3);
insert into customerOrder values('clambert5639', 'R-0004', 'F-010', 1);
insert into customerOrder values('jm2005', 'R-0007', 'F-016', 2);
insert into customerOrder values('snovak123', 'R-0008', 'F-013', 1);
insert into customerOrder values('rwolf8876', 'R-0005', 'F-018', 1);
insert into customerOrder values('cs1234', 'R-0005', 'F-012', 2);
insert into customerOrder values('cathys2034', 'R-0006', 'F-019', 4);
insert into customerOrder values('sallysally11', 'R-0007', 'F-008', 1);
insert into customerOrder values('ahuston83', 'R-0001', 'F-002', 2);
insert into customerOrder values('vvblevins3456', 'R-0003', 'F-014', 5);
insert into customerOrder values('mboyer9102', 'R-0006', 'F-020', 2);
insert into customerOrder values('amcc12345', 'R-0002', 'F-005', 1);
insert into customerOrder values('sx1024', 'R-0004', 'F-009', 1);
insert into customerOrder values('sx1024', 'R-0004', 'F-010', 1);
commit;

# insert data into Reserve

insert into reserve values('hero00', 'R-0005', str_to_date('11/21/2023 17:50', '%m/%e/%Y %k:%i'), 2);
insert into reserve values('hero00', 'R-0005', str_to_date('12/8/2023 11:30', '%m/%e/%Y %k:%i'), 5);
insert into reserve values('sx1024', 'R-0004', str_to_date('12/24/2023 20:40', '%m/%e/%Y %k:%i'), 4);
insert into reserve values('ahuston83', 'R-0001', str_to_date('12/24/2023 20:00', '%m/%e/%Y %k:%i'), 2);

commit;

# insert data into Broadcast

insert into broadcast values('Good morning', 'ABCD');
insert into broadcast values('Find the best restaurant', 'CNC');
insert into broadcast values('The Best Thing I Ever Ate', 'HelloTV');
insert into broadcast values('Chef\'s Table', 'FoodTV');
commit;

# insert data into Watch

insert into watch values('hero00', 'Good morning');
insert into watch values('hero00', 'The Best Thing I Ever Ate');
insert into watch values('sx1024', 'Chef\'s Table');
insert into watch values('jm2005', 'The Best Thing I Ever Ate');
insert into watch values('jm2005', 'Chef\'s Table');
insert into watch values('jm2005', 'Good morning');
insert into watch values('snovak123', 'The Best Thing I Ever Ate');

commit;

# insert data into Televise

insert into televise values('Good morning', 'R-0003', str_to_date('4/28/2019', '%m/%e/%Y'));
insert into televise values('Good morning', 'R-0005', str_to_date('10/26/2015', '%m/%e/%Y'));
insert into televise values('The Best Thing I Ever Ate', 'R-0005', str_to_date('6/25/2014', '%m/%e/%Y'));
insert into televise values('Find the best restaurant', 'R-0008', str_to_date('7/14/2023', '%m/%e/%Y'));
insert into televise values('Good morning', 'R-0001', str_to_date('1/18/2022', '%m/%e/%Y'));
insert into televise values('Chef\'s Table', 'R-0003', str_to_date('2/26/2023', '%m/%e/%Y'));
commit;
