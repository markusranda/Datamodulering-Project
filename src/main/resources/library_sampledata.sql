INSERT INTO `library_db`.`Branches`
(`idBranch`,
`name`,
`address`)
VALUES
(1,
"Granland Bibliotek",
"Granveien 3"), 
(2,
"Sausland Bibliotek",
"Sausveien 3"),
(3,
"Feskland Bibliotek",
"Feskveien 3"),
(4,
"Reinland Bibliotek",
"Reinveien 3"),
(5,
"Brødland Bibliotek",
"Brødveien 3"),
(6,
"Fjelland Bibliotek",
"Fjellveien 3");


INSERT INTO `library_db`.`Books`
(`idBook`,
`title`,
`publisher`,
`ISBN`,
image)
VALUES
(2,
"Grantyper",
"GranGeir AS",
"1231231",
Null),
(3,
"Saustyper",
"SausGeir AS",
"1231231",
Null),
(4,
"Fesktyper",
"FeskGeir AS",
"1231231",
Null),
(5,
"Reintyper",
"ReinGeir AS",
"1231231",
Null),
(6,
"Brødtyper",
"BrødGeir AS",
"1231231",
Null),
(7,
"Fjelltyper",
"FjellGeir AS",
"1231231",
Null),
(8,
"Lurtyper",
"LurGeir AS",
"1231231",
Null),
(9,
"Det siste verset",
"JBB AS",
"1231231",
Null),
(10,
"Jamaikansk kultur",
"JBB AS",
"1231231",
Null),
(11,
"Snømannen",
"JBB AS",
"1231231",
Null),
(12,
"Trailertips Med Roald",
"JBB AS",
"1231231",
Null),
(13,
"Kjøtt eller løk",
"JBB AS",
"1231231",
Null),
(14,
"Den komplette skuterguiden",
"JBB AS",
"1231231",
Null)
;

INSERT INTO `library_db`.`Book_Copies`
(`copies`,
`idBook`,
`idBranch`)
VALUES
(5,1,1),
(4,2,3),
(3,2,4),
(4,3,5),
(6,5,6),
(8,4,5),
(6,5,4),
(6,6,3),
(6,7,2),
(6,8,1),
(6,9,2),
(6,10,3),
(6,11,4),
(6,12,5),
(6,13,6),
(6,5,1);

INSERT INTO `library_db`.`Users`
(`idUser`,
`fname`,
`lname`,
`address`,
`phone`,
`email`,
`password`,
`usertype`)
VALUES
(1,
"Askgeir",
"Sjefen",
"Sjefsgata #1",
"11111111",
"sjefen.over@alle.sjefer",
"everybodyelsesucks",
"Sjef"),
(2,
"Geir",
"Gran",
"Granveien 69",
"123456776543",
"geir@gran.com",
"granlover",
"Customer"),
(3,
"Bidragsyter",
"Jon Billy",
"Storgata 40",
"2345234643",
"jon@bidragsyter.no",
"ærligogredeligarbeidskar",
"Customer");

INSERT INTO `library_db`.`Loans`
(`idLoans`,
`loanDate`,
`loanDue`,
`idBook`,
`idUser`)
VALUES
(1,
"1980-12-24",
"3080-02-24",
1,
2),
(1,
"1899-12-24",
"1910-02-24",
9,
2),
(1,
"1985-12-19",
"2015-10-21",
11,
3);


