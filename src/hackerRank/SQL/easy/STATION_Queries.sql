/*TODOS ESTOS EJERCICIOS SON PARA EL MANEJADOR MySQL*/

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

--Query para buscar por nombres que
SELECT DISTINCT
    CITY
FROM 
    STATION
WHERE 1=1
    AND SUBSTR(CITY,1,1) in ('a','e','i','o','u') --INICIAN CON VOCAL
    AND SUBSTR(CITY,CHAR_LENGTH(CITY),1) in ('a','e','i','o','u') --TERMINAN CON VOCAL
    ----OPCION CON LEFT RIGHT
    AND LEFT(CITY,1) in ('a','e','i','o','u') --INICIAN CON VOCAL
    AND RIGHT(CITY,1) in ('a','e','i','o','u') --TERMINAN CON VOCAL
    
