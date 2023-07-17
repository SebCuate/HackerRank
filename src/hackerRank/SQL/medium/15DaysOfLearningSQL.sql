/**
 * 
 * Julia conducted a  days of learning SQL contest. The start date of the contest was March 01, 2016 and the end date was March 15, 2016.
 * Write a query to print total number of unique hackers who made at least 1 submission each day (starting on the first day of the contest), 
 * and find the hacker_id and name of the hacker who made maximum number of submissions each day. 
 * 
 * If more than one such hacker has a maximum number of submissions, print the lowest hacker_id. 
 * The query should print this information for each day of the contest, sorted by the date.
 * 
 * Tables
 * Hackers: 
 * 			hacker_id is the id of the hacker, 
 * 			name is the name of the hacker.
 * Submissions: 
 * 			submission_date is the date of the submission, 
 * 			submission_id is the id of the submission, 
 * 			hacker_id is the id of the hacker who made the submission, 
 * 			and score is the score
 * 
 * If the End_Date of the tasks are consecutive, then they are part of the same project. Samantha is interested in finding the total number of different projects completed.
 * Write a query to output the start and end dates of projects listed by the number of days it took to complete the project in ascending order.
 * If there is more than one project that have the same number of completion days, then order by the start date of the project.
 * 
 * Input DATA
1	2015-10-01	2015-10-02
2	2015-10-03	2015-10-04
3	2015-10-11	2015-10-12
9	2015-11-01	2015-11-02
4	2015-10-15	2015-10-16
8	2015-10-29	2015-10-30
5	2015-10-19	2015-10-20
7	2015-10-27	2015-10-28
6	2015-10-25	2015-10-26
23	2015-10-04	2015-10-05
24	2015-10-02	2015-10-03
20	2015-10-21	2015-10-22
19	2015-10-26	2015-10-27
21	2015-10-17	2015-10-18
18	2015-10-28	2015-10-29
22	2015-10-12	2015-10-13
17	2015-10-30	2015-10-31
16	2015-11-04	2015-11-05
10	2015-11-07	2015-11-08
15	2015-11-06	2015-11-07
11	2015-11-05	2015-11-06
14	2015-11-11	2015-11-12
12	2015-11-12	2015-11-13
13	2015-11-17	2015-11-18
 * 
 * 
 * 
 */
/*Debemos de colocar un contador para contar el numero de días consecutivos que llevo un proyecto*/
set @count:=1;

/*Solo nos importa la fecha inicial y la fecha fianl*/
SELECT
    MIN(start_date),
    MAX(end_date)
FROM
    (SELECT 
        *,
        CASE
            WHEN resecutive_day =1 then @count
            --CASO 2
			--when resecutive_day =0 then @count
            else (@count:=@count+1)
        end as group_task
    FROM
    	/*Primero consigo los dias entre las fechas finales*/
        (select 
            *, 
            /*La funcion LAG(End_Date) nos devuelve el valor del renglon anterior*/
            /*La funcion OVER() sirve para indicar la ventana de renglones, en este caso, utiiza toda la tabla*/
            END_DATE - (LAG(End_Date) OVER ()) AS resecutive_day
            --Tambien funciona con
            --Start_date-(LAG(Start_Date) OVER ()) AS resecutive_day 
            --Tambien funciona con CASO 2
            --start_date-(LAG(END_Date) OVER ()) AS resecutive_day
        from
            projects
        order by 
            start_date) as raw_data1
    ) as raw_data2
group by group_task
order by count(*), min(start_date)
