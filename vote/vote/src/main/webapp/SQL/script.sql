create table TBL_VOTE_202005 (
	V_JUMIN char(13) not null primary key,
	V_NAME varchar2(20),
	M_NO char(1),
	V_TIME char(4),
	V_AREA char(20),
	V_CONFIRM char(1)
);

SELECT M.M_NO, M.M_NAME, COUNT(V.M_NO) vote_count
FROM TBL_VOTE_202005 V
JOIN TBL_MEMBER_202005 M ON V.M_NO = M.M_NO
GROUP BY M.M_NO, M.M_NAME
ORDER BY vote_count DESC;


select * from TBL_VOTE_202005;
INSERT INTO TBL_VOTE_202005 (V_JUMIN, V_NAME, M_NO, V_TIME, V_AREA, V_CONFIRM) 
VALUES ('990101100011', '김유권', '1', '0930', '제1투표장', 'N');

INSERT INTO TBL_VOTE_202005 (V_JUMIN, V_NAME, M_NO, V_TIME, V_AREA, V_CONFIRM) 
VALUES ('890101200021', '이유권', '2', '0930', '제1투표장', 'N');

INSERT INTO TBL_VOTE_202005 (V_JUMIN, V_NAME, M_NO, V_TIME, V_AREA, V_CONFIRM) 
VALUES ('690101100031', '박유권', '3', '0930', '제1투표장', 'Y');

INSERT INTO TBL_VOTE_202005 (V_JUMIN, V_NAME, M_NO, V_TIME, V_AREA, V_CONFIRM) 
VALUES ('590101200041', '홍유권', '4', '0930', '제1투표장', 'Y');

INSERT INTO TBL_VOTE_202005 (V_JUMIN, V_NAME, M_NO, V_TIME, V_AREA, V_CONFIRM) 
VALUES ('790101100051', '조유권', '5', '0930', '제1투표장', 'Y');

INSERT INTO TBL_VOTE_202005 (V_JUMIN, V_NAME, M_NO, V_TIME, V_AREA, V_CONFIRM) 
VALUES ('890101200061', '최유권', '1', '0930', '제1투표장', 'Y');

INSERT INTO TBL_VOTE_202005 (V_JUMIN, V_NAME, M_NO, V_TIME, V_AREA, V_CONFIRM) 
VALUES ('590101100071', '지유권', '1', '0930', '제1투표장', 'Y');

INSERT INTO TBL_VOTE_202005 (V_JUMIN, V_NAME, M_NO, V_TIME, V_AREA, V_CONFIRM) 
VALUES ('490101200081', '장유권', '3', '0930', '제1투표장', 'Y');

INSERT INTO TBL_VOTE_202005 (V_JUMIN, V_NAME, M_NO, V_TIME, V_AREA, V_CONFIRM) 
VALUES ('790101100091', '정유권', '3', '0930', '제1투표장', 'Y');

INSERT INTO TBL_VOTE_202005 (V_JUMIN, V_NAME, M_NO, V_TIME, V_AREA, V_CONFIRM) 
VALUES ('890101200101', '강유권', '4', '0930', '제1투표장', 'Y');

INSERT INTO TBL_VOTE_202005 (V_JUMIN, V_NAME, M_NO, V_TIME, V_AREA, V_CONFIRM) 
VALUES ('990101100111', '신유권', '5', '0930', '제1투표장', 'Y');

INSERT INTO TBL_VOTE_202005 (V_JUMIN, V_NAME, M_NO, V_TIME, V_AREA, V_CONFIRM) 
VALUES ('790101200121', '오유권', '1', '1330', '제1투표장', 'Y');

INSERT INTO TBL_VOTE_202005 (V_JUMIN, V_NAME, M_NO, V_TIME, V_AREA, V_CONFIRM) 
VALUES ('690101100131', '현유권', '4', '1330', '제2투표장', 'Y');

INSERT INTO TBL_VOTE_202005 (V_JUMIN, V_NAME, M_NO, V_TIME, V_AREA, V_CONFIRM) 
VALUES ('890101100141', '왕유권', '2', '1330', '제2투표장', 'Y');

INSERT INTO TBL_VOTE_202005 (V_JUMIN, V_NAME, M_NO, V_TIME, V_AREA, V_CONFIRM) 
VALUES ('990101100151', '유유권', '3', '1330', '제2투표장', 'Y');

INSERT INTO TBL_VOTE_202005 (V_JUMIN, V_NAME, M_NO, V_TIME, V_AREA, V_CONFIRM) 
VALUES ('790101100161', '한유권', '2', '1330', '제2투표장', 'Y');

INSERT INTO TBL_VOTE_202005 (V_JUMIN, V_NAME, M_NO, V_TIME, V_AREA, V_CONFIRM) 
VALUES ('890101100171', '문유권', '4', '1330', '제2투표장', 'Y');

INSERT INTO TBL_VOTE_202005 (V_JUMIN, V_NAME, M_NO, V_TIME, V_AREA, V_CONFIRM) 
VALUES ('990101100181', '양유권', '2', '1330', '제2투표장', 'Y');

INSERT INTO TBL_VOTE_202005 (V_JUMIN, V_NAME, M_NO, V_TIME, V_AREA, V_CONFIRM) 
VALUES ('990101100191', '구유권', '4', '1330', '제2투표장', 'Y');

INSERT INTO TBL_VOTE_202005 (V_JUMIN, V_NAME, M_NO, V_TIME, V_AREA, V_CONFIRM) 
VALUES ('790101100201', '황유권', '5', '1330', '제2투표장', 'Y');

INSERT INTO TBL_VOTE_202005 (V_JUMIN, V_NAME, M_NO, V_TIME, V_AREA, V_CONFIRM) 
VALUES ('690101100211', '배유권', '3', '1330', '제2투표장', 'Y');

INSERT INTO TBL_VOTE_202005 (V_JUMIN, V_NAME, M_NO, V_TIME, V_AREA, V_CONFIRM) 
VALUES ('790101100221', '전유권', '3', '1330', '제2투표장', 'Y');

INSERT INTO TBL_VOTE_202005 (V_JUMIN, V_NAME, M_NO, V_TIME, V_AREA, V_CONFIRM) 
VALUES ('990101100231', '고유권', '1', '1330', '제2투표장', 'Y');

INSERT INTO TBL_VOTE_202005 (V_JUMIN, V_NAME, M_NO, V_TIME, V_AREA, V_CONFIRM) 
VALUES ('590101100241', '권유권', '3', '1330', '제2투표장', 'Y');




create table TBL_MEMBER_202005 (
	M_NO char(1) not null primary key,
	M_NAME varchar2(20),
	P_CODE char(2),
	P_SCHOOL char(1),
	M_JUMIN char(13),
	M_CITY varchar2(20)
);

select * from TBL_MEMBER_202005;

insert into TBL_MEMBER_202005 values ('1', '김후보', 'P1', '1', '6603011999991', '수선화동');
insert into TBL_MEMBER_202005 values ('2', '이후보', 'P2', '3', '5503011999992', '민들래동');
insert into TBL_MEMBER_202005 values ('3', '박후보', 'P3', '2', '7703011999993', '나팔꽃동');
insert into TBL_MEMBER_202005 values ('4', '조후보', 'P4', '2', '8803011999994', '진달래동');
insert into TBL_MEMBER_202005 values ('5', '최후보', 'P5', '3', '9903011999995', '개나리동');

create table TBL_PARTY_202005 (
	P_CODE char(2) not null primary key,
	P_NAME varchar(20),
	P_IDATE date,
	p_READER varchar2(20),
	P_TEL1 char(3),
	P_TEL2 char(4),
	P_TEL3 char(4)
);

select * from TBL_PARTY_202005;

insert into TBL_PARTY_202005 values('P1', 'A정당', '2010-01-01', '위대표', '02', '1111', '0001');
insert into TBL_PARTY_202005 values('P2', 'B정당', '2010-02-01', '명대표', '02', '1111', '0002');
insert into TBL_PARTY_202005 values('P3', 'C정당', '2010-03-01', '기대표', '02', '1111', '0003');
insert into TBL_PARTY_202005 values('P4', 'D정당', '2010-04-01', '옥대표', '02', '1111', '0004');
insert into TBL_PARTY_202005 values('P5', 'E정당', '2010-05-01', '임대표', '02', '1111', '0005');

select M.M_NO, M.M_NAME, P.P_NAME, 
	case M.P_SCHOOL 
		when '1' then '고졸' 
		when '2' then '학사'
		when '3' then '석사'
		when '4' then '박사'
	end P_SCHOOL,
	SUBSTR(M.M_JUMIN,1,6) || '-' || SUBSTR(M.M_JUMIN,7) M_JUMIN,
	M.M_CITY,
	P.P_TEL1 || '-' || P.P_TEL2 || '-' || P.P_TEL3 P_TEL
from TBL_MEMBER_202005 M, TBL_PARTY_202005 P
where M.P_CODE = P.P_CODE;

SELECT 
    V_NAME, 
    CASE
        WHEN SUBSTR(V_JUMIN, 7, 1) IN ('1', '3') THEN '19' || SUBSTR(V_JUMIN, 1, 2) || '년' || SUBSTR(V_JUMIN, 3, 2) || '월' || SUBSTR(V_JUMIN, 5, 2) || '일'
        WHEN SUBSTR(V_JUMIN, 7, 1) IN ('2', '4') THEN '20' || SUBSTR(V_JUMIN, 1, 2) || '년' || SUBSTR(V_JUMIN, 3, 2) || '월' || SUBSTR(V_JUMIN, 5, 2) || '일'
    END AS birth_date,
    '만' || TRUNC(MONTHS_BETWEEN(SYSDATE, 
        TO_DATE(
            CASE
                WHEN SUBSTR(V_JUMIN, 7, 1) IN ('1', '2') THEN '19' || SUBSTR(V_JUMIN, 1, 2) || SUBSTR(V_JUMIN, 3, 2) || SUBSTR(V_JUMIN, 5, 2)
                WHEN SUBSTR(V_JUMIN, 7, 1) IN ('3', '4') THEN '20' || SUBSTR(V_JUMIN, 1, 2) || SUBSTR(V_JUMIN, 3, 2) || SUBSTR(V_JUMIN, 5, 2)
            END, 
            'YYYYMMDD'
        )
    ) / 12) || '세' AS age,
    CASE
        WHEN SUBSTR(V_JUMIN, 7, 1) IN ('1', '3') THEN '남'
        WHEN SUBSTR(V_JUMIN, 7, 1) IN ('2', '4') THEN '여'
    END AS gender,
    M_NO,
    SUBSTR(V_TIME, 1, 2) || ':' || SUBSTR(V_TIME, 3, 2) AS V_TIME,
    CASE
        WHEN V_CONFIRM = 'Y' THEN '확인'
        WHEN V_CONFIRM = 'N' THEN '미확인'
    END AS V_CONFIRM
FROM TBL_VOTE_202005;
	



	   
