INSERT INTO TBL_VACCRESV_202108 VALUES(?,?,?,?,?,?);

SELECT 
    V.RESVNO, 
    J.MANE, 
    CASE 
        WHEN SUBSTR(J.JUMIN, 8, 1) IN ('1', '3') THEN '남'
        WHEN SUBSTR(J.JUMIN, 8, 1) IN ('2', '4') THEN '여'
    END AS GENDER,
    H.HOSPNAME,
    TO_CHAR(TO_DATE(V.RESVDATE, 'YYYYMMDD'), 'YYYY"년"MM"월"DD"일"') AS RESVDATE,
    TO_CHAR(TO_DATE(V.RESVTIME, 'HH24MI'), 'HH24:MI') AS RESVTIME,
    V.VCODE,
    (SELECT CITY_NAME FROM CITY_CODE_TBL_20 WHERE CITY = H.HOSPADDR) CITY_NAME
FROM TBL_JUMIN_202108 J
JOIN TBL_VACCRESV_202108 V ON J.JUMIN = V.JUMIN
JOIN TBL_HOSP_202108 H ON V.HOSPCODE = H.HOSPCODE
WHERE RESVNO = ?;

SELECT
    H.HOSPCODE,
    H.HOSPNAME,
    COUNT(V.HOSPCODE) AS RESERVATION_COUNT
FROM TBL_HOSP_202108 H
LEFT OUTER JOIN TBL_VACCRESV_202108 V ON V.HOSPCODE = H.HOSPCODE
GROUP BY H.HOSPCODE, H.HOSPNAME;

SELECT
	V.RESVNO, 
    J.MANE, 
    CASE 
        WHEN SUBSTR(J.JUMIN, 8, 1) IN ('1', '3') THEN '남'
        WHEN SUBSTR(J.JUMIN, 8, 1) IN ('2', '4') THEN '여'
    END AS GENDER,
    H.HOSPNAME,
    TO_CHAR(TO_DATE(V.RESVDATE, 'YYYYMMDD'), 'YYYY"년"MM"월"DD"일"') AS RESVDATE,
    TO_CHAR(TO_DATE(V.RESVTIME, 'HH24MI'), 'HH24:MI') AS RESVTIME,
    V.VCODE,
    -- 다른 테이블에서 가져와야함
    (SELECT CITY_NAME FROM CITY_CODE_TBL_20 WHERE CITY = H.HOSPADDR) CITY_NAME
FROM TBL_JUMIN_202108 J
JOIN TBL_VACCRESV_202108 V ON J.JUMIN = V.JUMIN
JOIN TBL_HOSP_202108 H ON V.HOSPCODE = H.HOSPCODE
order by V.RESVNO;