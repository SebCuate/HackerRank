--Resta de dos contadores en la misma tabla
SELECT 
    COUNT(CITY) - COUNT(DISTINCT CITY) 
FROM 
    STATION ;
    
    
--Union de dos distintos queries en una sola salida
(SELECT
    CITY, CHAR_LENGTH(CITY)
FROM
    STATION
ORDER BY
    CHAR_LENGTH(CITY) DESC, CITY DESC --ordena por longitud, luego alfabeticamente
LIMIT 1 )
UNION ALL
(SELECT
    CITY, CHAR_LENGTH(CITY)
FROM
    STATION
ORDER BY 
    CHAR_LENGTH(CITY), CITY --ordena por longitud, luego alfabeticamente
LIMIT 1)