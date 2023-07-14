/**
 * 
 * https://www.hackerrank.com/challenges/placements/problem
 * 
 * You are given three tables: Students, Friends and Packages. 
 * Students contains two columns: ID and Name. 
 * Friends contains two columns: ID and Friend_ID (ID of the ONLY best friend). 
 * Packages contains two columns: ID and Salary (offered salary in $ thousands per month).
 * 
 * 
 * 
id  Name	  fried	salary
1	Samantha 	14	15.5
2	Julia 		15	15.6
3	Britney 	18	16.7
4	Kristeen 	19	18.8
5	Dyana 		20	31.5
6	Jenny 		5	45
7	Christene 	6	47
8	Meera 		7	46
9	Priya 		8	39
10	Priyanka	1	11.1
11	Paige		2	12.1
12	Jane		3	13.1
13	Belvet		4	14.1
14	Scarlet		9	15.1
15	Salma		10	17.1
16	Amanda		11	21.1
17	Maria		12	31.1
18	Stuart		13	13.15
19	Aamina		16	33.33
20	Amina		17	22.16
 * 
 * Write a query to output the names of those students whose best friends got offered a higher salary than them. Names must be ordered by the salary amount offered to the best friends. 
 * It is guaranteed that no two students got same salary offer.
 * 
 */
/*Solucion 1*/
select 
    s.name 
from 
    Students s 
join Friends f 
    on f.id = s.id 
join Packages p 
    on s.id = p.id 
join Packages p2 
    on p2.id = f.Friend_id 
where
    p2.salary > p.salary
order by 
    p2.salary

/*Solucion 2*/
WITH 
    students_salary AS ( 
            SELECT s.id, s.name, p.salary 
            FROM students s LEFT JOIN packages p ON s.id = p.id ), 
    friends_salary AS ( 
            SELECT f.friend_id, f.id, p.salary 
            FROM friends f LEFT JOIN packages p ON f.friend_id = p.id ) 
SELECT 
    s.name
FROM students_salary s 
JOIN friends_salary f 
    ON s.id =f.id AND s.salary < f.salary 
ORDER BY f.salary;

/*Solucion 3*/
select 
    name 
from ( 
    select 
        s.name as name, 
        p.salary as salary, 
        p2.salary as friend_salary 
    from students s 
    join friends f 
        on s.id = f.id 
    join students s2 
        on f.friend_id = s2.id 
    join packages p 
        on s.id = p.id 
    join packages p2 on f.friend_id = p2.id 
    )s 
where 
    friend_salary > salary 
order by 
    friend_salary