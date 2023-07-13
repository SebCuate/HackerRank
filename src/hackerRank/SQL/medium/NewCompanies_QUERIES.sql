/**
 * NAME  BST
 * Column	TYPE
 * N		Integer
 * P		Integer
 * 
1 2
2 4
3 2
4 15
5 6
6 4
7 6
8 9
9 11
10 9
11 15
12 13
13 11
14 13
15 NULL
 * 
 */

/*Querie para
 * Amber's conglomerate corporation just acquired some new companies. Each of the companies follows this hierarchy: 
 * Founder -> Lead Manager -> Senior Manager -> Manager -> Employee
 * Given the table schemas below, write a query to print the company_code, founder name, total number of lead managers, 
 * total number of senior managers, total number of managers, and total number of employees. Order your output by ascending company_code.
 * 
 * Note:
 * The tables may contain duplicate records.
 * The company_code is string, so the sorting should not be numeric. 
 * For example, if the company_codes are C_1, C_2, and C_10, then the ascending company_codes will be C_1, C_10, and C_2.
 * 
 * 
 * Input Format
 * The following tables contain company data:
 * 
 * Company
 * company_code			String
 * founder				String
 * 
 * Lead_Manager
 * lead_manager_code	String
 * company_code			String
 * 
 * Senior_Manager
 * senior_manager_code	String
 * lead_manager_code	String
 * company_code			String
 * 
 * Manager
 * manager_code			String
 * senior_manager_code	String
 * lead_manager_code	String
 * company_code			String
 * 
 * Employee
 * employee_code		String
 * manager_code			String
 * senior_manager_code	String
 * lead_manager_code	String
 * company_code			String
 * 
 * */
SELECT DISTINCT
    co.company_code, 
    co.founder, 
    COUNT(distinct lm.lead_manager_code), 
    COUNT(distinct sm.senior_manager_code), 
    COUNT(distinct m.manager_code), 
    COUNT(distinct em.employee_code) 
FROM 
    Company co 
JOIN 
    lead_manager lm on co.company_code = lm.company_code 
JOIN 
    senior_manager sm on lm.company_code = sm.company_code 
JOIN 
    manager m on lm.lead_manager_code = m.lead_manager_code 
JOIN 
    employee em on m.manager_code = em.manager_code 
GROUP BY 
    co.company_code, co.founder 
ORDER BY
    co.company_code